package ru.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Lesson6_Task11 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        String email = "BarbaraG" + new Random().nextInt(100) + "@email.com";
        String password = "1234567890";

        // Open the shop page http://localhost/litecart
        driver.get("http://localhost/litecart");

        // Register a new customer
        driver.findElement(By.xpath("//a[text()='New customers click here']")).click();

        driver.findElement(By.cssSelector("input[name=firstname]")).sendKeys("Barbara");
        driver.findElement(By.cssSelector("input[name=lastname]")).sendKeys("Gross");
        driver.findElement(By.cssSelector("input[name=address1]")).sendKeys("890 Hillview Court, Suite 300");
        driver.findElement(By.cssSelector("input[name=postcode]")).sendKeys("95035");
        driver.findElement(By.cssSelector("input[name=city]")).sendKeys("Milpitas");
        driver.findElement(By.cssSelector("span[role=presentation]")).click();
        driver.findElement(By.cssSelector("span[class$=dropdown] input[role=textbox]")).sendKeys("United States" + Keys.ENTER);
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=phone]")).sendKeys("+1 408 457 9777");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=create_account]")).click();
        new Select(driver.findElement(By.cssSelector("select[name=zone_code]"))).selectByValue("CA");
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("input[name=confirmed_password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=create_account]")).click();

        // Log out
        driver.findElement(By.xpath("//a[text()='Logout']")).click();

        // Log in under the new customer
        driver.findElement(By.cssSelector("input[name=email]")).sendKeys(email);
        driver.findElement(By.cssSelector("input[name=password]")).sendKeys(password);
        driver.findElement(By.cssSelector("button[name=login]")).click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
