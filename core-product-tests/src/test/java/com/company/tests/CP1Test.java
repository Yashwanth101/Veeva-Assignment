package com.company.tests;

import com.company.framework.base.BaseClass;
import com.company.framework.driver.DriverManager;
import com.company.framework.pages.MensApparelPage;
import com.company.framework.pages.NBAHomePage;
import com.company.framework.reporting.ExtentReportManager;
import com.company.framework.utils.UtilityMethods;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CP1Test extends BaseClass {
    private WebDriver driver;
    private MensApparelPage mensApparelPage;
    private UtilityMethods utilityMethods;
    private NBAHomePage nbaHomePage;

    @BeforeMethod
    public void initializePages() {
        driver = DriverManager.getDriver();
        mensApparelPage = new MensApparelPage(driver);
        utilityMethods = new UtilityMethods(driver);
        nbaHomePage = new NBAHomePage(driver);
    }

    @Test
    public void getAllJacketsPricesWithTitles() {
        try{
            nbaHomePage.handlePopup();
            nbaHomePage.goToShopSubMenuOption();
            utilityMethods.switchToChildWindow();
            mensApparelPage.selectMenMenuOption();
            mensApparelPage.selectDepartment("men-jackets");
            List<String> productData = mensApparelPage.getAllProductsPricesWithTitles();
            String path = utilityMethods.createFileAndWriteData("reports/jackets.txt", productData);
            utilityMethods.addFileToReport(ExtentReportManager.getTest(), path);
        }catch (Exception e){
            throw new RuntimeException(e);

        }
    }
}
