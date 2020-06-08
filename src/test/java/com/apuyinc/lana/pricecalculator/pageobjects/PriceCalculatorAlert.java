package com.apuyinc.lana.pricecalculator.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PriceCalculatorAlert {
    private AndroidDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//android.widget.LinearLayout[@index='0']//android.widget.TextView[@index='0']")
    private WebElement alertTitle_text;

    @FindBy(xpath = "//android.widget.FrameLayout[@index='1']/android.widget.ScrollView[@index='0']" +
            "/android.widget.LinearLayout[@index='0']/android.widget.TextView[@index='0']")
    private WebElement message_text;

    public PriceCalculatorAlert(AndroidDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        PageFactory.initElements(driver, this);
    }

    public String getAlertTitle() {
        wait.until(ExpectedConditions.visibilityOf(alertTitle_text));
        return alertTitle_text.getText();
    }

    public String getAlertText() {
        wait.until(ExpectedConditions.visibilityOf(message_text));
        return message_text.getText();
    }
}
