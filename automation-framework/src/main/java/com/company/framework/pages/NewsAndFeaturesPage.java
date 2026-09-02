package com.company.framework.pages;

import com.company.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsAndFeaturesPage extends BasePage {

    @FindBy(xpath = "//h3[text()='VIDEOS']/following-sibling::div//div[contains(@class,'sm:block') or contains(@class, 'TileArticle')]//time")
    private List<WebElement> videoCards;

    public NewsAndFeaturesPage(WebDriver driver) {
        super(driver);
    }

    public Map<String, Integer> countVideoFeedsOnly() {
        Map<String, Integer> videosData = new HashMap<>();
        int totalVideoFeeds = videoCards.size();
        int feedsGreaterOrEqualTo3Days = 0;

        for (WebElement timeElement : videoCards) {
            String ariaLabel = timeElement.getAttribute("aria-label");

            if (ariaLabel != null && !ariaLabel.isEmpty()) {
                ariaLabel = ariaLabel.toLowerCase();

                if (ariaLabel.contains("day")) {
                    int days = Integer.parseInt(ariaLabel.replaceAll("[^0-9]", ""));
                    if (days >= 3) {
                        feedsGreaterOrEqualTo3Days++;
                    }
                } else if (ariaLabel.contains("month") || ariaLabel.contains("year")) {
                    feedsGreaterOrEqualTo3Days++;
                }
            }
        }
        videosData.put("Total Videos", totalVideoFeeds);
        videosData.put("Video Feeds >= 3d", feedsGreaterOrEqualTo3Days);
        return videosData;
    }

}
