package com.company.framework.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static String reportPath;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportPath = "reports/ExtentReport_" + timestamp + ".html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

            reporter.config().setDocumentTitle("Automation Test Report");
            reporter.config().setReportName("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }

        return extent;
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = getReportInstance().createTest("Test case name: " + testName);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void removeTest() {
        test.remove();
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = System.getProperty("user.dir") + "/screenshots/" + screenshotName + ".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
