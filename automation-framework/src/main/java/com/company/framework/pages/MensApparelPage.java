package com.company.framework.pages;

import com.company.framework.base.BasePage;
import com.company.framework.utils.Log;
import com.company.framework.utils.UtilityMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class MensApparelPage extends BasePage {

    private UtilityMethods utilityMethods;

    @FindBy(xpath = "//a[@data-trk-id='topnav-group-ga-1_men']")
    private WebElement menOption;

    @FindBy(xpath = "//span[text()='All Departments']//ancestor::div[@data-talos='filterContainer']//li/a")
    private List<WebElement> departmentElements;

    @FindBy(css = ".pagination-navigation button[aria-label ='next page']")
    private WebElement nextPageButton;

    @FindBy(xpath = "//div[@class='layout-row product-grid']/descendant::div//div[@class='product-card row']/div[2]")
    private List<WebElement> productDataList;

    @FindBy(xpath = "//div[@class='layout-row product-grid']/descendant::div//div[@class='prices']/div[1]/descendant::span//span[@class='money-value'][1]")
    private List<WebElement> productPrice;

    @FindBy(xpath = "//div[@class='layout-row product-grid']/descendant::div//div[@class='product-card row']/div[2]/div[@class='product-card-title']/a")
    private List<WebElement> productName;

    @FindBy(xpath = "//button[@data-trk-id='modal-close-button']")
    private WebElement couponPopUp;

    public MensApparelPage(WebDriver driver){
        super(driver);
        utilityMethods = new UtilityMethods(driver);
    }

    public void selectMenMenuOption() {
        try {
            utilityMethods.waitForElementToBeClickable(couponPopUp);
        } catch (Exception e) {
            System.out.println(" No POPUP Appeared");
        }
        utilityMethods.waitForElementToBeClickable(menOption);
        utilityMethods.clickElement(menOption);
    }

    /*
        To Select Values in the Departments, We Need to send href partial text
        to select the Value
     */
    public void selectDepartment(String target){
        for(WebElement element: departmentElements){
            if(element.getAttribute("href").contains(target)){
                Log.info("Found Department: " + target);
                utilityMethods.waitForElementVisible(element).click();
                break;
            }
        }

    }

    /*
        This Method will Go through all the products and picks up the Products Price and Product Title and price
        and them to the List and will return the list.
     */
    public List<String> getAllProductsPricesWithTitles(){
        List<String> productData = new ArrayList<>();
        int jacketsCount = 0;
        while(true){
            Log.info("Getting Product Data...");
            utilityMethods.waitForElementVisible(productName.get(0));
            int productCount = productDataList.size();
            jacketsCount+=productCount;

            for(int i=0;i<productCount;i++){
                String price = productPrice.get(i).getText();
                String productTitle = productName.get(i).getText();
                productData.add("Product Title is ->  " + productTitle + " ------>>>>>> and the price is  " + price );
            }
            try {
                if (nextPageButton.isDisplayed() && nextPageButton.isEnabled()) {
                    nextPageButton.click();
                } else {
                    break;
                }
            } catch (Exception e) {
                break;
            }

        }
        Log.info("Product Count: " + jacketsCount);
        return productData;
    }
}
