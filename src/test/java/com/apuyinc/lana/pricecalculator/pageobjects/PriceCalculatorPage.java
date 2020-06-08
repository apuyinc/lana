package com.apuyinc.lana.pricecalculator.pageobjects;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PriceCalculatorPage {

    private AndroidDriver driver;
    WebDriverWait wait;

    @FindBy(id = "pick_up_point")
    private WebElement pickUpPoint_spinner;

    @FindBy(id = "drop_off_point")
    private WebElement dropOffPoint_spinner;

    @FindBy(id = "liteCheckBox")
    private WebElement lite_checkbox;

    @FindBy(id = "executiveCheckBox")
    private WebElement executive_checkbox;

    @FindBy(id = "captcha_text")
    private WebElement captcha_text;

    @FindBy(id = "price_text")
    private WebElement price_text;

    @FindBy(id = "estimate_button")
    private WebElement estimate_button;

    @FindBy(id = "request_button")
    private WebElement request_button;

    @FindBy(id = "estimate_progress_bar")
    private WebElement estimate_progress_bar;

    public PriceCalculatorPage(AndroidDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        PageFactory.initElements(driver, this);
    }

    private void tickCheckBox(boolean ticked, WebElement checkBox) {
        Boolean status = checkBox.getAttribute("checked").equalsIgnoreCase("true");
        if (ticked != status) {
            checkBox.click();
        }
    }

    public void selectPickUpPoint(String pickUpPoint) {
        pickUpPoint_spinner.click();
        MobileElement el =
                (MobileElement) driver.findElement(
                        MobileBy.AndroidUIAutomator("new UiSelector().textContains(\""+pickUpPoint+"\")"));
        el.click();
    }

    public void selectDropOffPoint(String dropOffPoint) {
        dropOffPoint_spinner.click();
        MobileElement el =
                (MobileElement) driver.findElement(
                        MobileBy.AndroidUIAutomator("new UiSelector().textContains(\""+dropOffPoint+"\")"));
        el.click();
    }

    public void tickCarLite(boolean ticked) {
        tickCheckBox(ticked, lite_checkbox);
    }

    public void tickCarExecutive(boolean ticked) {
        tickCheckBox(ticked, executive_checkbox);
    }


    public void typeCaptcha(String captcha) {
        captcha_text.sendKeys(captcha);
    }

    public void estimate() {
        estimate_button.click();
    }

    public void requestCar() {
        WebElement captchaVisible = wait.until(ExpectedConditions.visibilityOf(request_button));
        request_button.click();
    }

    public String getEstimatedPrice() {
        WebElement captchaVisible = wait.until(ExpectedConditions.visibilityOf(price_text));
        return price_text.getText();
    }

    public void estimateJourney(String pickupPoint, String dropOffPoint, Boolean liteTypeOfCar, Boolean executeTypeOfCar,
                                        String captcha) {
        WebElement captchaVisible = wait.until(ExpectedConditions.visibilityOf(pickUpPoint_spinner));
        selectPickUpPoint("Atocha");
        selectDropOffPoint("Aeropuerto Madrid Barajas, T4");
        tickCarLite(liteTypeOfCar);
        tickCarExecutive(executeTypeOfCar);
        typeCaptcha(captcha);
        estimate();
    }
}
