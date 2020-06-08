package com.apuyinc.lana.pricecalculator.pageobjects;

import com.apuyinc.lana.pricecalculator.entities.RequestedJourney;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RequestCarPage {

    private AndroidDriver driver;
    WebDriverWait wait;

    @FindBy(id = "journeyList")
    private List<WebElement> requestedJourney_list;

    @FindBy(xpath = "//android.widget.TextView[@text='Past Journeys']")
    private WebElement pastJourneyTitle;

    public RequestCarPage(AndroidDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        PageFactory.initElements(driver, this);
    }

    public RequestedJourney getLastRequestedTravel() {
        wait.until(ExpectedConditions.visibilityOf(pastJourneyTitle));
        WebElement lastJourney = requestedJourney_list.get(requestedJourney_list.size()-1);
        String journeyTitle = lastJourney.findElement(By.id("journeyTitle")).getText();
        String journeyPrice = lastJourney.findElement(By.id("journeyPrice")).getText();
        return new RequestedJourney(journeyTitle, journeyPrice);
    }

}
