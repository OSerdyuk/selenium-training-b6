package ru.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class Lesson7_Task13 {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions action;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        action = new Actions(driver);
    }

    @Test
    public void test() {
        // Open the shop page http://localhost/litecart
        driver.get("http://litecart.stqa.ru/en/");

        // Open the first product and add it to the Cart
        for (int i = 1; i < 4; i++) {
            driver.findElement(By.cssSelector("div#box-most-popular ul li")).click();
            if (driver.findElements(By.cssSelector("select[name='options[Size]']")).size() == 1) {
                new Select(driver.findElement(By.cssSelector("select[name='options[Size]']"))).selectByValue("Medium");
            }
            driver.findElement(By.cssSelector("button[name=add_cart_product]")).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector("div#cart a:nth-child(2) span.quantity"), "" + i));
            driver.navigate().back();
        }

        // Open the Cart and delete all products
        driver.findElement(By.xpath("//a[text()='Checkout »']")).click();

        driver.findElement(By.cssSelector("div#box-checkout-cart ul.shortcuts  li.shortcut:nth-child(1)")).click();
        List<String> eachProductCounts = driver.findElements(By.cssSelector(("div#box-checkout-cart ul li.item input[name=quantity]"))).stream().map(p -> p.getAttribute("value")).collect(Collectors.toList());
        int allProductsCount = 0;
        for (int j = 0; j < eachProductCounts.size(); j++)
            allProductsCount = allProductsCount + Integer.parseInt(eachProductCounts.get(j));

        while (allProductsCount > 0) {
            int productCount = Integer.parseInt(driver.findElement(By.cssSelector("div.viewport input[name=quantity]")).getAttribute("value"));
            allProductsCount = allProductsCount - productCount;
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.viewport button[name='remove_cart_item']"))).click();
            if (driver.findElements(By.cssSelector("div#checkout-summary-wrapper")).size() == 1) {
                driver.findElement(By.cssSelector("div.viewport button[name='update_cart_item']")).click();
                wait.until(ExpectedConditions.attributeContains(By.cssSelector("div#checkout-cart-wrapper"), "style", "opacity: 1;"));
            }
        }
        wait.until(ExpectedConditions.textToBe(By.cssSelector("div#checkout-cart-wrapper"),
                "There are no items in your cart.\n" +
                        "<< Back"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
