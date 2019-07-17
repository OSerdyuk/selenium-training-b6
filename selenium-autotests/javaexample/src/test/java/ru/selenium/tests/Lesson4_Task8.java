package ru.selenium.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Lesson4_Task8 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        // Open the shop page http://localhost/litecart
        driver.get("http://localhost/litecart");

        // Find all ducks
        List<WebElement> productsList = driver.findElements(By.cssSelector("li.product"));
        for (WebElement product : productsList) {
            // Find all stickers for each product and check that every product has only one sticker
            List<WebElement> stickersList = product.findElements(By.cssSelector("div.sticker"));
            Assert.assertEquals(1, stickersList.size());
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

