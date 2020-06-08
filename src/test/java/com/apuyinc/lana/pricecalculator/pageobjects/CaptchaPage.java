package com.apuyinc.lana.pricecalculator.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CaptchaPage {

    protected AndroidDriver driver;
    WebDriverWait wait;

    @FindBy(id = "captcha_button")
    private WebElement captcha_button;

    @FindBy(id = "captcha_text")
    private WebElement captcha_text;

    //Constructor
    public CaptchaPage(AndroidDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        PageFactory.initElements(driver, this);
    }

    public void next() {
        captcha_button.click();
    }

    public String getCaptcha() {
        WebElement captchaVisible = wait.until(ExpectedConditions.visibilityOf(captcha_text));
        return captcha_text.getText();
    }
}
