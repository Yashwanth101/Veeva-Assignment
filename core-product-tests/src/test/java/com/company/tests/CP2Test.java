package com.company.tests;

import com.company.framework.base.BaseClass;
import com.company.framework.driver.DriverManager;
import com.company.framework.pages.NBAHomePage;
import com.company.framework.pages.NewsAndFeaturesPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class CP2Test extends BaseClass {

    private WebDriver driver;
    private NBAHomePage nbaHomePage;
    private NewsAndFeaturesPage newsAndFeaturesPage;

    @BeforeMethod
    public void initializePages(){
        driver = DriverManager.getDriver();
        nbaHomePage = new NBAHomePage(driver);
        newsAndFeaturesPage = new NewsAndFeaturesPage(driver);
    }

    @Test
    public void getCountOfVideoFeedsMoreThanOrEqualTo3Days() throws InterruptedException {
        nbaHomePage.handlePopup();
        nbaHomePage.goToNewsAndFeaturesPage();
        Map<String, Integer> videoMap = newsAndFeaturesPage.countVideoFeedsOnly();
        System.out.println("Total Videos present in the Section are:" +videoMap.get("Total Videos"));
        System.out.println("Total Videos present Greater than 3 Days are:" +videoMap.get("Video Feeds >= 3d"));
    }




}
