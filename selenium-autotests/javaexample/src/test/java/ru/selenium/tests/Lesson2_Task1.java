package ru.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Lesson2_Task1 {

    private WebDriver driver;

    @Before
    public void start() {
        System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void test1() {
        driver.get("http://www.google.com");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}