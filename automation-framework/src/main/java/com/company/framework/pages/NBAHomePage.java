package com.company.framework.pages;

import com.company.framework.base.BasePage;
import com.company.framework.utils.UtilityMethods;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBAHomePage extends BasePage {

    UtilityMethods utilityMethods;

    @FindBy(xpath = "//*[@role='dialog']")
    private WebElement popup;

    @FindBy(xpath = "//*[@role='dialog']/descendant::div//div[contains(@class,'hover:cursor-pointer')]")
    private WebElement closePopup;

    @FindBy(xpath = "//li[contains(@data-testid,'shop.warriors.com')]/child::a")
    private WebElement shopButton;

    @FindBy(xpath = "//nav[@id='nav-dropdown-desktop-1059653']/descendant::li//a[@title='Men''s']")
    private WebElement shopSubmenu;

    @FindBy(xpath = "//nav[@aria-label='header-secondary-menu']//li[@data-testid='nav-item-']")
    private WebElement menuItem;

    @FindBy(xpath = "//nav[@aria-label='submenu']//a[@title='News & Features']")
    private WebElement news;

    @FindBy(xpath = "//h3[text()='VIDEOS']/following-sibling::div//div[contains(@class,'sm:block') or contains(@class, 'TileArticle')]//time")
    private List<WebElement> videoCards;

    public NBAHomePage(WebDriver driver){
        super(driver);
        utilityMethods = new UtilityMethods(driver);
    }

    /*
        This Method will wait for Shop Button to be Clickable and will click the Element
     */
    public void goToShopSubMenuOption(){
        utilityMethods.waitForElementToBeClickable(shopButton);
        utilityMethods.clickElement(shopButton);
    }

    /*
       This Method will move to the Menu Item and Click the News and Features Button using Actions.
    */
    public void  goToNewsAndFeaturesPage() throws InterruptedException {
        Thread.sleep(4000); // Added this because, The Header is rendering twice after page load.
        utilityMethods.moveToElement(menuItem);
        utilityMethods.click_using_Actions(news);
    }

    /*
        This is a safe method added just to close the popup if it appears.
        This method will wait till Popup is displayed, If it is displayed, it will close or It will throw an Exception.
     */
    public void handlePopup() {
        try{
            if(popup.isDisplayed()){
                WebElement closeBtn = utilityMethods.waitForElementToBeClickable(closePopup);
                closeBtn.click();
                utilityMethods.invisibilityOfElement(popup);
            }
        } catch (TimeoutException e) {
            System.out.println("No popup appeared.");
        }
    }



}
