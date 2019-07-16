package ru.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Lesson4_Task7 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        // Log into http://localhost/litecart/admin
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Get the vertical menu list
        List<String> verticalMenuList = driver.findElements(By.cssSelector("div#box-apps-menu-wrapper > ul.list-vertical li")).stream().map(e -> e.getText()).collect(Collectors.toList());
        for (String item : verticalMenuList) {
            System.out.print("Menu '" + item + "'");
            driver.findElement(By.xpath("//span[text()='" + item + "']")).click();
            // Get the submenu list
            List<String> subMenuList = driver.findElements(By.cssSelector("li#app-.selected ul.docs li")).stream().map(e -> e.getText()).collect(Collectors.toList());
            System.out.print(" has " + subMenuList.size() + " subitems with the following header(s):\n");
            if (subMenuList.size() != 0) {
                // Check Header is displayed
                for (String subItemName : subMenuList) {
                    driver.findElement(By.xpath("//span[text()='" + subItemName + "']")).click();
                    System.out.print(driver.findElement(By.cssSelector("h1")).getText() + "\n");
                }
            } else System.out.print(driver.findElement(By.cssSelector("h1")).getText() + "\n");
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
