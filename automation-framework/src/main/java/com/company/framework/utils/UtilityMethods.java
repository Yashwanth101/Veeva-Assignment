package com.company.framework.utils;

import com.aventstack.extentreports.ExtentTest;
import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

public class UtilityMethods {
    private WebDriverWait wait;
    private WebDriver driver;

    public UtilityMethods(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(ConfigManager.getInt("explicitWait")));
    }


    public void moveToElement(WebElement targetElement) {
        Actions actions = new Actions(this.driver);
        actions.moveToElement(targetElement).build().perform();
    }

    public void click_using_Actions(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element)
                .pause(Duration.ofMillis(2000))
                .click()
                .perform();
    }

    public void switchToChildWindow() {
        String parent = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
            if (!window.equals(parent)) {
                driver.switchTo().window(window);
                break;
            }
        }
        wait.until(webDriver -> "complete".equals(
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForAttributeValue(WebElement element, String attribute, String booleanValue) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, booleanValue));

    }

    public void invisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public WebElement waitForElementVisible(WebElement element) {
        Log.info("Waiting for element to be visible: " + element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /*
    This method takes a file path and a list of string data, then writes the data to a text file.
    1. Creates a new file at the specified file path.
    2. Loops through each item in the data list and writes it on a new line.
    3. Logs the absolute file path once all items are written.
    4. Catches any file writing errors and returns the absolute path of the created file.
 */
    public String createFileAndWriteData(String filePath, List<String> data) {
        File file = new File(filePath);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String s : data) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
        Log.info("Details wre written to File, File Location is:"+ file.getAbsoluteFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file.getAbsolutePath();
    }

    /*
    This method adds a clickable link to the Extent Test Report pointing to the saved file.
    1. Takes the ExtentTest instance and the generated file path.
    2. Formats an HTML hyperlink containing the file path.
    3. Logs an Info step in the report so users can click or copy the path to view the file.
 */
    public void addFileToReport(ExtentTest extentTest, String path) {
        extentTest.info("PriceList of jackets are present in the file: "
                + "<a href='file:///" + path + "' target='_blank'>Copy the link and paste in browser to open the file</a>");
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }


    /*
        This Method will take List of Footer Links and a CSV Location to Store to.
        1. Loop through all the footer links
        2. If link is not null or empty, we will trim and add the URL to the Set or List based in Uniqueness.
        3. Then we will add them to the CSV
        4. Return the Map with Details
     */
    public Map<String, Integer> writeToCSV(List<String> footerLinks, String csvLocation) {
        Set<String> uniqueURL = new HashSet<>();
        List<String> duplicateURL = new ArrayList<>();

        try(FileWriter writer = new FileWriter(csvLocation)) {
            writer.append("URL,Status\n");

            for (String link:footerLinks) {
                if (link!=null && !link.isEmpty()) {
                    String trimmedURL = link.trim();
                    boolean isUnique = uniqueURL.add(trimmedURL);
                    String status = isUnique ? "UNIQUE" : "DUPLICATE";

                    if (!isUnique) {
                        duplicateURL.add(trimmedURL);
                    }

                    writer.append(String.format("%s, %s \n", trimmedURL, status));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to write to CSV");
        }

        return Map.of(
                "Total Links", footerLinks.size(),
                "Unique Links", uniqueURL.size(),
                "Duplicate Links", duplicateURL.size()
        );
    }

    public void clickElement(WebElement element) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                WebElement clickable = waitForElementToBeClickable(element);
                clickable.click();
                return;
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= 3) throw e;
            } catch (ElementClickInterceptedException e) {
                WebElement clickable = waitForElementToBeClickable(element);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);
                return;
            }
        }
    }
}
