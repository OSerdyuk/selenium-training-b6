package ru.selenium.tests;

import org.junit.Test;

public class UserCanAddProductsToCartAndThenDelete extends TestBase{

    @Test
    public void test() {
        app.fillCartWithProducts(3);
        app.openCartAndDeleteAllProducts();
    }
}