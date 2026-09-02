package com.company.framework.pages;

import com.company.framework.base.BasePage;
import com.company.framework.utils.UtilityMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class NBASixersHomePage extends BasePage {

    UtilityMethods utilityMethods;

    @FindBy(xpath = "//button//div[contains(@class, 'TileHeroStories_tileHeroStoriesButtonTitle')]")
    private List<WebElement> slideTitles;

    @FindBy(xpath = "//button[@data-testid='content-hero-navigation-button']")
    private List<WebElement> slideData;

    public NBASixersHomePage(WebDriver driver) {
        super(driver);
        utilityMethods = new UtilityMethods(driver);
    }

    public void waitForElementToLoad(){
        utilityMethods.waitForElementVisible(slideData.get(0));
    }

    public int getSlideCount(){
        return slideData.size();
    }

    public List<String> getSlideTitle(){
        return slideTitles.stream().map(element -> element.getText().replace("View Now", "").trim()).toList();
    }

    /**
     * Measures the playback duration (in seconds) of each video content slide.
     * Performs the following actions:
     * 1. Scrolls to the first slide and waits until it becomes active
     * 2. Iterates through all video slides in sequence.
     * 3. Calculates the duration each slide remains active by tracking timestamps when
     * 4. Converts the duration from milliseconds to seconds and appends it to a result list
     */
    public List<Long> playContentVideos() {
        List<Long> videoDurations = new ArrayList<>();
        if (slideData != null && !slideData.isEmpty()) {
            utilityMethods.scrollToElement(slideData.get(0));
            utilityMethods.waitForAttributeValue(slideData.get(0), "aria-selected", "true");
        }

        for (WebElement element : slideData) {
            utilityMethods.waitForAttributeValue(element, "aria-selected", "true");
            long startTime = System.currentTimeMillis();
            utilityMethods.waitForAttributeValue(element, "aria-selected", "false");
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            videoDurations.add(duration/1000);
        }
        System.out.println(videoDurations);
        return videoDurations;
    }

}
