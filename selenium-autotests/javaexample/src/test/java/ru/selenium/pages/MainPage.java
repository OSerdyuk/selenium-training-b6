package ru.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends Page {
    private static final By FIRST_PRODUCT_LOCATOR = By.cssSelector("div#box-most-popular ul li");
    private static final By CHECKOUT_LINK_LOCATOR = By.xpath("//a[text()='Checkout »']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage open() {
        driver.get("http://litecart.stqa.ru/en/");
        return this;
    }

    public MainPage clickOnFirstProduct() {
        driver.findElement(FIRST_PRODUCT_LOCATOR).click();
        return this;
    }

    public MainPage clickCartChekoutLink() {
        driver.findElement(CHECKOUT_LINK_LOCATOR).click();
        return this;
    }
}
