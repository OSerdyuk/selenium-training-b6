package ru.selenium.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Lesson6_Task12 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        // Log into http://localhost/litecart/admin
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Add a new product
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Add New Product')]")).click();

        // 'General' tab
        driver.findElement(By.xpath("//label[contains(text(),'Enabled')]")).click();
        driver.findElement(By.cssSelector("input[name='name[en]']")).sendKeys("Rubber Frog");
        driver.findElement(By.cssSelector("input[name=code")).sendKeys("rf001");
        driver.findElement(By.cssSelector("input[name=quantity")).sendKeys("10");
        driver.findElement(By.cssSelector("input[name='new_images[]']")).sendKeys(new File("./src/test/java/ru/selenium/tests/images/GreenFrog.jpg").getAbsolutePath());
        driver.findElement(By.cssSelector("input[name=date_valid_from")).sendKeys("2019-07-23");
        driver.findElement(By.cssSelector("input[name=date_valid_from")).sendKeys("2019-08-09");

        // 'Information' tab
        driver.findElement(By.xpath("//a[contains(text(),'Information')]")).click();
        new Select(driver.findElement(By.cssSelector("select[name=manufacturer_id]"))).selectByValue("1");
        driver.findElement(By.cssSelector("input[name='short_description[en]']")).sendKeys("Various baby kids bath toy");
        driver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("A brand-new, unused, unopened and undamaged item in original retail packaging " +
                "(where packaging is applicable). If the item comes direct from a manufacturer, it may be delivered in non-retail packaging, such as a plain or unprinted " +
                "box or plastic bag. See the seller's listing for full details.");

        // 'Prices' tab
        driver.findElement(By.xpath("//a[contains(text(),'Prices')]")).click();
        driver.findElement(By.cssSelector("input[name=purchase_price]")).sendKeys("14,50");
        new Select(driver.findElement(By.cssSelector("select[name=purchase_price_currency_code]"))).selectByValue("USD");
        driver.findElement(By.cssSelector("input[name='prices[USD]']")).sendKeys("1.000");
        driver.findElement(By.cssSelector("button[name=save]")).click();

        // Check that this new product has been added
        driver.findElement(By.xpath("//span[text()='Catalog']")).click();
        Assert.assertTrue("The new product has not been added to the Catalog", driver.findElement(By.xpath("//form[@name='catalog_form']/descendant::table[@class='dataTable']/tbody/descendant::tr/td[3]/a[text()='Rubber Frog']")).isDisplayed());
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
