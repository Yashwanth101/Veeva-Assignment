package com.company.framework.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.company.framework.driver.DriverManager;
import com.company.framework.reporting.ExtentReportManager;
import com.company.framework.utils.ConfigManager;
import com.company.framework.utils.Log;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseClass {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;

    /**
     * Initializes the reporting framework before any tests in the suite execute.
     * Loads the environment configuration settings and creates the single ExtentReports instance.
     */
    @BeforeSuite
    public void setupReport() {
        ConfigManager.loadConfig();
        extent = ExtentReportManager.getReportInstance();
    }

    /**
     * Flushes and saves the ExtentReports dashboard after all tests in the suite complete.
     * Ensures all logged steps, status indicators, and screenshots are written to the HTML file.
     */
    @AfterSuite
    public void teardownReport() {
        extent.flush();
    }

    /**
     * Prepares the execution environment before each @Test method runs.
     * Performs the following actions:
     * 1. Creates a new test entry in ExtentReports using the test method name.
     * 2. Initializes the ThreadLocal WebDriver instance based on the browser parameter.
     * 3. Navigates to the specified URL from ConfigManager.
     * 4. Maximizes the window, clears cookies, and configures timeouts (page load and implicit wait).
     * <
     */
    @BeforeMethod
    @Parameters({"browser", "url"})
    public void setUp(String browser, String url, Method method) {
        Log.info("URL is: " + ConfigManager.get(url));

        test = ExtentReportManager.createTest(method.getName());
        Log.info("Starting WebDriver...");

        driver = DriverManager.setDriver(browser);
        if (driver == null) {
            throw new IllegalStateException("WebDriver is NULL! Browser not initialized.");
        }

        driver.get(ConfigManager.get(url));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.getInt("pageLoadTimeout")));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getInt("implicitWait")));
    }


    /**
     * Handles cleanup and result processing after each @Test method completes.
     * Performs the following actions:
     * 1. Checks if the test failed; if so, captures a screenshot and attaches it to ExtentReports.
     * 2. Quits the current WebDriver instance to prevent memory leaks and isolated browser sessions.
     * 3. Cleans up ThreadLocal references to prevent cross-test contamination.
     */
    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = ExtentReportManager.captureScreenshot(
                        DriverManager.getDriver(), result.getName());
                test.fail("Test Failed.. Check Screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            }
        } finally {
            DriverManager.quitDriver();
            driver = null;
            ExtentReportManager.removeTest();
        }
    }
}
