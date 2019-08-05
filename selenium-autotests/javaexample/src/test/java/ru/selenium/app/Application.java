package ru.selenium.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.selenium.pages.CartPage;
import ru.selenium.pages.MainPage;
import ru.selenium.pages.ProductPage;

public class Application {

    private WebDriver driver;

    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    public Application() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
        driver = null;
    }

    public void fillCartWithProducts(int productsCount) {
        for (int i = 1; i < productsCount + 1; i++) {
            mainPage
                    .open()
                    .clickOnFirstProduct();
            productPage.addProductToCart()
                    .waitCartUpdate(i)
                    .navigateBack();
        }
    }

    public void openCartAndDeleteAllProducts() {
        mainPage.clickCartChekoutLink();
        cartPage.selectFirstProduct().deleteAllProducts();
    }
}