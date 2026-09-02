package com.company.tests;

import com.company.framework.base.BaseClass;
import com.company.framework.driver.DriverManager;
import com.company.framework.pages.NBASixersHomePage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class DP1Test extends BaseClass {
    private WebDriver driver;
    private NBASixersHomePage nbaSixersHomePage;
    SoftAssert softAssert = new SoftAssert();

    @BeforeMethod
    public void initializePages(){
        driver = DriverManager.getDriver();
        nbaSixersHomePage = new NBASixersHomePage(driver);
    }

    @Test
    public void getContentVideoDurations(){
        nbaSixersHomePage.waitForElementToLoad();
        int slideCount = nbaSixersHomePage.getSlideCount();
        List<String> slideTitles = nbaSixersHomePage.getSlideTitle();
        softAssert.assertEquals(slideCount, slideTitles.size(), "Slide count and slide titles count do not match");
        List<Long> actualVideoDurations = nbaSixersHomePage.playContentVideos();
        softAssert.assertEquals(slideCount, actualVideoDurations.size(), "Video Count doesn't match");
        softAssert.assertAll();
    }
}
