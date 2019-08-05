package ru.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends Page {
    private static final By FIRST_PRODUCT_LOCATOR = By.cssSelector("div#box-checkout-cart ul.shortcuts  li.shortcut:nth-child(1)");
    private static final By PRODUCT_INFO_LOCATOR = By.cssSelector("div#checkout-cart-wrapper");
    private static final By PRODUCT_COUNTS_LOCATOR = By.cssSelector("div#box-checkout-cart ul li.item input[name=quantity]");
    private static final By UPDATE_BUTTON_LOCATOR = By.cssSelector("div.viewport button[name='update_cart_item']");
    private static final By REMOVE_BUTTON_LOCATOR = By.cssSelector("div.viewport button[name='remove_cart_item']");
    private static final By ORDER_SUMMARY_TABLE_LOCATOR = By.cssSelector("div#checkout-summary-wrapper");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public CartPage selectFirstProduct() {
        driver.findElement(FIRST_PRODUCT_LOCATOR).click();
        return this;
    }

    public CartPage deleteAllProducts () {
        List<String> eachProductCounts = driver.findElements(PRODUCT_COUNTS_LOCATOR).stream().map(p -> p.getAttribute("value")).collect(Collectors.toList());
        int allProductsCount = 0;
        for (int j = 0; j < eachProductCounts.size(); j++)
            allProductsCount = allProductsCount + Integer.parseInt(eachProductCounts.get(j));

        while (allProductsCount > 0) {
            int productCount = Integer.parseInt(driver.findElement(PRODUCT_COUNTS_LOCATOR).getAttribute("value"));
            allProductsCount = allProductsCount - productCount;
            wait.until(ExpectedConditions.elementToBeClickable(REMOVE_BUTTON_LOCATOR)).click();
            if (driver.findElements(ORDER_SUMMARY_TABLE_LOCATOR).size() == 1) {
                driver.findElement(UPDATE_BUTTON_LOCATOR).click();
                wait.until(ExpectedConditions.attributeContains(PRODUCT_INFO_LOCATOR, "style", "opacity: 1;"));
            }
        }
        wait.until(ExpectedConditions.textToBe(PRODUCT_INFO_LOCATOR,
                "There are no items in your cart.\n" +
                        "<< Back"));
        return this;
    }
}
