package com.company.tests;

import com.company.framework.base.BaseClass;
import com.company.framework.driver.DriverManager;
import com.company.framework.pages.NBABullsHomePage;
import com.company.framework.utils.UtilityMethods;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DP2Test extends BaseClass {

    private WebDriver driver;
    private UtilityMethods utilityMethods;
    private NBABullsHomePage nbaBullsHomePage;

    @BeforeMethod
    public void initializePages(){
        driver = DriverManager.getDriver();
        utilityMethods = new UtilityMethods(driver);
        nbaBullsHomePage = new NBABullsHomePage(driver);
    }

    @Test
    public void getAllFooterLinks(){
        List<String> links = nbaBullsHomePage.getAllFooterLinks();
        utilityMethods.writeToCSV(links, "reports/links.csv");
    }

}
