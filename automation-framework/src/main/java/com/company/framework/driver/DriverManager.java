package com.company.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class DriverManager {

    // Thread-safe driver
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /*
        setDriver will take Browser as a Param and based on the Option the required Browser will launch
     */
    public static WebDriver setDriver(String browser) {
        if (driver.get() == null) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-renderer-backgrounding");
                    chromeOptions.addArguments("--disable-background-timer-throttling");
                    chromeOptions.addArguments("--disable-backgrounding-occluded-windows");
                    chromeOptions.addArguments("--disable-ipc-flooding-protection");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    driver.set(new ChromeDriver(chromeOptions));
                    break;
                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
        return getDriver();
    }

    /*
        This Method will return the Current Browser Instance.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /*
        This Method will delete the Current Browser Instance.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

}
