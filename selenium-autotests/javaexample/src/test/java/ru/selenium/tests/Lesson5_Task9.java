package ru.selenium.tests;

import com.google.common.collect.Ordering;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Lesson5_Task9 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test1() {
        // Log into http://localhost/litecart/admin
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Get list of all countries and check the names are in alphabetical order
        List<String> countriesList = driver.findElements(By.cssSelector("table tr.row td:nth-child(5)")).stream().map(e -> e.getText()).collect(Collectors.toList());
        checkListIsAlphabetized(countriesList);

        // Find countries with multiple zones and check these zones order
        for (String country : countriesList) {
            WebElement countryLink = driver.findElement(By.xpath("//a[text()=\"" + country + "\"]"));
            String zoneCount = countryLink.findElement(By.xpath("./../../td[6]")).getText();
            if (!zoneCount.equals("0")) {
                countryLink.click();
                List<String> zonesList = driver.findElements(By.xpath("//h2[text()='Zones']/table/tbody/tr/td[3]")).stream().map(e -> e.getText()).collect(Collectors.toList());
                checkListIsAlphabetized(zonesList);
                driver.navigate().back();
            }
        }
    }

    private void checkListIsAlphabetized(List<String> actualList) {
        List<String> sortedList = actualList;
        Collections.sort(sortedList);
        Assert.assertEquals(actualList, sortedList);
    }

    @Test
    public void test2() {
        // Log into http://localhost/litecart/admin
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Get list of the countries
        List<String> countriesList = driver.findElements(By.cssSelector("table tr.row td:nth-child(3)")).stream().map(e -> e.getText()).collect(Collectors.toList());

        // Check that zones are in alphabetical order
        for (String country : countriesList) {
            driver.findElement(By.xpath("//a[text()=\"" + country + "\"]")).click();
            List<String> zonesList = driver.findElements(By.xpath("//h2[text()='Zones']/following::table/tbody/tr/td[3]/select/option[@selected='selected']")).stream().map(e -> e.getText()).collect(Collectors.toList());
            checkListIsAlphabetized(zonesList);
            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
