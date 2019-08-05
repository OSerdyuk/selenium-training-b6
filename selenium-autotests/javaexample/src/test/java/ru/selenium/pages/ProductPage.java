package ru.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends Page {
    private static final By PRODUCT_SIZE_LOCATOR = By.cssSelector("select[name='options[Size]']");
    private static final By ADD_PRODUCT_BUTTON_LOCATOR = By.cssSelector("button[name=add_cart_product]");
    private static final By CART_PRODUCTS_COUNT_LOCATOR = By.cssSelector("div#cart a:nth-child(2) span.quantity");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage addProductToCart() {
        if(driver.findElements(PRODUCT_SIZE_LOCATOR).size() ==1)
            new Select(driver.findElement(PRODUCT_SIZE_LOCATOR)).selectByValue("Medium");
        driver.findElement(ADD_PRODUCT_BUTTON_LOCATOR).click();
        return this;
    }

    public ProductPage waitCartUpdate(int productsCount) {
        wait.until(ExpectedConditions.textToBe(CART_PRODUCTS_COUNT_LOCATOR, "" + productsCount));
        driver.findElement(ADD_PRODUCT_BUTTON_LOCATOR).click();
        return this;
    }
}