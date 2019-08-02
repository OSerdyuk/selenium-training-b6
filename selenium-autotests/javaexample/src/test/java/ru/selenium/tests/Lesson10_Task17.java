package ru.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Lesson10_Task17 {

    private EventFiringWebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        // Log into http://localhost/litecart/admin
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.cssSelector("button")).click();

        // Open each category with products and get a list of all products
        int categoriesCount = driver.findElements(By.cssSelector("strong a[href*=category_id]")).size();
        for (int i = 1; i <= categoriesCount; i++) {
            driver.findElement(By.cssSelector("a[href$='category_id=" + i + "']")).click();
        }
        // Open each product page and check the log
        int productsCount = driver.findElements(By.cssSelector("a[href*=product_id][title=Edit]")).size();
        for (int j = 0; j <= productsCount; j++) {
            List<String> productsLinks = driver.findElements(By.cssSelector("a[href*='product_id='][title=Edit]>i")).stream().map(l -> l.findElement(By.xpath("./parent::a")).getAttribute("href")).collect(Collectors.toList());
            productsLinks.forEach(l -> {
                checkBrowserLogs(l);
            });
        }
    }

    private void checkBrowserLogs(String link) {
        WebElement pencilProductLink = driver.findElement(By.cssSelector("a[href='" + link + "'][title=Edit]>i"));
        if (!pencilProductLink.findElement(By.xpath("./../../../td[1]/input")).isSelected()) {
            pencilProductLink.findElement(By.xpath("./../../../td[1]/input")).click();
            pencilProductLink.click();
            List<LogEntry> browserLogs = driver.manage().logs().get("browser").getAll();
            if (browserLogs.size() == 0)
                System.out.print("There are no logs in the browser\n");
            else System.out.print(browserLogs);
            driver.navigate().back();
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
