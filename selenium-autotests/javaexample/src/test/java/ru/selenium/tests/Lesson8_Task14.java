package ru.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class Lesson8_Task14 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test1() {
        // Log into http://localhost/litecart/admin
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Edit the first country
        driver.findElement(By.cssSelector("table tr.row td:nth-child(7) a")).click();
        // Find all icons and click on them
        List<WebElement> iconsList = driver.findElements(By.cssSelector("i.fa-external-link"));
        for (WebElement icon : iconsList) {
            String originalWindow = driver.getWindowHandle();
            icon.click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            Set<String> existingWindows = driver.getWindowHandles();
            for (String handle : existingWindows) {
                if (!handle.equals(originalWindow)) {
                    driver.switchTo().window(handle);
                    driver.close();
                }
                driver.switchTo().window(originalWindow);
            }
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}