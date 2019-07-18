package ru.selenium.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;

import java.util.concurrent.TimeUnit;

public class Lesson5_Task10 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        // Open the shop page http://localhost/litecart
        driver.get("http://localhost/litecart");

        // Save all comparing values from the main page
        WebElement productOnMainPage = driver.findElement(By.xpath("//h3[contains(text(), 'Campaigns')]/../descendant::li"));
        String productNameOnMainPage = productOnMainPage.findElement(By.cssSelector("div.name")).getText();
        WebElement regularPriceOnMainPage = productOnMainPage.findElement(By.cssSelector("div.price-wrapper s.regular-price"));
        String regularPriceValueOnMainPage = regularPriceOnMainPage.getText();
        WebElement promoPriceOnMainPage = productOnMainPage.findElement(By.cssSelector("div.price-wrapper strong.campaign-price"));
        String promoPriceValueOnMainPage = promoPriceOnMainPage.getText();

        // Check в) for the main page
        checkRegularPriceStyles(regularPriceOnMainPage);

        // Check г) for the main page
        checkPromoPriceStyles(promoPriceOnMainPage);

        // Check д) for the main page
        compareRegularAndPromoSizes(regularPriceOnMainPage, promoPriceOnMainPage);

        // Open the product page and compare
        productOnMainPage.click();
        String productName = driver.findElement(By.cssSelector("h1.title")).getText();
        WebElement regularPrice = driver.findElement(By.cssSelector("div#box-product s.regular-price"));
        WebElement promoPrice = driver.findElement(By.cssSelector("div#box-product strong.campaign-price"));

        // Check a)
        Assert.assertEquals(productNameOnMainPage, productName);

        // Check б)
        Assert.assertEquals(regularPriceValueOnMainPage, regularPrice.getText());
        Assert.assertEquals(promoPriceValueOnMainPage, promoPrice.getText());

        // Check в) for the product page
        checkRegularPriceStyles(regularPrice);

        // Check г) for the product page
        checkPromoPriceStyles(promoPrice);

        // Check д) for the main page
        compareRegularAndPromoSizes(regularPrice, promoPrice);
    }

    private void checkRegularPriceStyles(WebElement el) {
        Assert.assertEquals("Regular price is not strikethroughed.", "line-through", el.getCssValue("text-decoration-line"));
        Color color = Color.fromString(el.getCssValue("color"));
        Assert.assertEquals("Regular price is not grey.", color.getColor().getRed(), color.getColor().getGreen());
        Assert.assertEquals("Regular price is not grey.", color.getColor().getGreen(), color.getColor().getBlue());
    }

    private void checkPromoPriceStyles(WebElement el) {
        Assert.assertEquals("Promo price is not bold.", "700", el.getCssValue("font-weight"));
        Color color = Color.fromString(el.getCssValue("color"));
        Assert.assertNotEquals("Promo price is not red.", color.getColor().getRed(), color.getColor().getGreen());
        Assert.assertEquals("Promo price is not red.", color.getColor().getGreen(), color.getColor().getBlue());
    }

    private void compareRegularAndPromoSizes(WebElement regularPrice, WebElement promoPrice) {
        String regularSize = regularPrice.getCssValue("font-size");
        String promoSize = promoPrice.getCssValue("font-size");
        if (Float.parseFloat(regularSize.substring(0, regularSize.length() - 2)) < Float.parseFloat(promoSize.substring(0, promoSize.length() - 2)))
            System.out.print("Promo price font size is bigger than Regular price font size.");
        else Assert.fail("Promo price font size is smaller than Regular price font size.");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
