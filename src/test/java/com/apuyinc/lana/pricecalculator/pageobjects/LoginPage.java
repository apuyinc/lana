package com.apuyinc.lana.pricecalculator.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private AndroidDriver driver;

    @FindBy(id = "user_name_edit_text")
    private WebElement username_edit_text;

    @FindBy(id = "password_edit_text")
    private WebElement password_edit_text;

    @FindBy(id = "login_button")
    private WebElement login_button;

    public LoginPage(AndroidDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        username_edit_text.sendKeys(username);
        password_edit_text.sendKeys(password);
        login_button.click();
    }
}

