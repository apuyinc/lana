package com.apuyinc.lana.pricecalculator.tests;

import com.apuyinc.lana.pricecalculator.entities.RequestedJourney;
import com.apuyinc.lana.pricecalculator.helpers.Converter;
import com.apuyinc.lana.pricecalculator.pageobjects.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

public class PriceCalculator {
    private static final String ATOCHA = "Atocha";
    private static final String BARAJAS_T4 = "Aeropuerto Madrid Barajas, T4";
    private static final String THIRTYFIVE_TWO = "35.2 €";
    public static final String ERROR_CAPTCHA_TEXT = "Captcha typed is wrong";
    public static final String ERROR_CAPTCHA_TITLE = "Error in captcha!";
    AndroidDriver<WebElement> driver;

    @BeforeMethod
    public void Setup() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName","Android");

        capabilities.setCapability("appPackage", "com.cabify.qabify");
        capabilities.setCapability("appActivity","com.cabify.qabify.MainActivity");

        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    private String loginAndCaptcha() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("user@qabify.com", "1234Abc");

        CaptchaPage captchaPage = new CaptchaPage(driver);
        String captchaText = captchaPage.getCaptcha();
        captchaPage.next();
        return captchaText;
    }

    @Test
    public void executivePriceIs10percentHigherThanLite() {
        BigDecimal TEN_PERCENT = BigDecimal.valueOf(1.10);
        PriceCalculatorPage priceCalculatorPage = new PriceCalculatorPage(driver);

        String captchaText = loginAndCaptcha();

        priceCalculatorPage.estimateJourney(ATOCHA, BARAJAS_T4,
                true, false, captchaText);
        BigDecimal litePrice = Converter.ConvertPriceStringToNumeric(
                priceCalculatorPage.getEstimatedPrice());

        priceCalculatorPage.tickCarExecutive(true);
        priceCalculatorPage.tickCarLite(false);
        priceCalculatorPage.estimate();
        String calculatedPriceExecutive = priceCalculatorPage.getEstimatedPrice();
        BigDecimal executivePrice = Converter.ConvertPriceStringToNumeric(calculatedPriceExecutive);

        BigDecimal expectedExecutivePrice = litePrice.multiply(TEN_PERCENT);
        assert executivePrice.equals(expectedExecutivePrice):
                "Actual value is : "+executivePrice+" did not match with expected value: " + expectedExecutivePrice;
    }

    @Test
    public void Should_ShowErrorMessage_When_WrongCaptcha() {
        String captchaText = loginAndCaptcha();

        PriceCalculatorPage priceCalculatorPage = new PriceCalculatorPage(driver);
        priceCalculatorPage.estimateJourney(ATOCHA, BARAJAS_T4,
                true, false, "000");

        PriceCalculatorAlert priceCalculatorAlert = new PriceCalculatorAlert(driver);

        String alertTitle = priceCalculatorAlert.getAlertTitle();
        String alertText = priceCalculatorAlert.getAlertText();

        assert alertTitle.equals(ERROR_CAPTCHA_TITLE):
                "Actual value is : "+alertTitle+" did not match with expected value: " + ERROR_CAPTCHA_TITLE;
        assert alertText.equals(ERROR_CAPTCHA_TEXT):
                "Actual value is : "+alertText+" did not match with expected value: " + ERROR_CAPTCHA_TEXT;
    }

    @Test
    public void Should_GetPrice_When_EstimatingJourney() {

        String captchaText = loginAndCaptcha();

        PriceCalculatorPage priceCalculatorPage = new PriceCalculatorPage(driver);
        priceCalculatorPage.estimateJourney(ATOCHA, BARAJAS_T4,
                true, false, captchaText);
        String estimatedPrice = priceCalculatorPage.getEstimatedPrice();

        assert estimatedPrice.equals(THIRTYFIVE_TWO):
                "Actual value is : "+estimatedPrice+" did not match with expected value:" + THIRTYFIVE_TWO;
    }

    @Test
    public void Should_ShowRequestedJourney_When_RequestingJourney() {

        String captchaText = loginAndCaptcha();

        PriceCalculatorPage priceCalculatorPage = new PriceCalculatorPage(driver);
        priceCalculatorPage.estimateJourney(ATOCHA, BARAJAS_T4,
                true, false, captchaText);
        String estimatedPrice = priceCalculatorPage.getEstimatedPrice();

        priceCalculatorPage.requestCar();
        RequestCarPage requestCarPage = new RequestCarPage(driver);
        RequestedJourney lastRequestedJourney = requestCarPage.getLastRequestedTravel();

        assert lastRequestedJourney.getJourneyPrice().equals(estimatedPrice):
                "Actual value is : "+lastRequestedJourney.getJourneyPrice()+" did not match with expected value:"+estimatedPrice;
        assert lastRequestedJourney.getJourneyTitle().equals(ATOCHA + " - " + BARAJAS_T4):
                "Actual value is: "+lastRequestedJourney.getJourneyTitle()+" did not match with expected value: " + ATOCHA + " - " + BARAJAS_T4;
    }

    @AfterMethod
    public void teardown(){
        //close the app
        driver.quit();
    }

}
