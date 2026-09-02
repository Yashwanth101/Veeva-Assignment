package com.company.framework.pages;

import com.company.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NBABullsHomePage extends BasePage {

    @FindBy(xpath = "//footer//li[@data-testid='footer-list-item']//a")
    private List<WebElement> footerLinks;

    public NBABullsHomePage(WebDriver driver) {
        super(driver);
    }

    /*
        This method will get all the Footer Link from href attribute and will return the List
     */
    public List<String> getAllFooterLinks() {
        return footerLinks.stream().map(eachLink -> eachLink.getAttribute("href")).filter(eachHref -> eachHref != null && !eachHref.isEmpty()).toList();
    }

}
