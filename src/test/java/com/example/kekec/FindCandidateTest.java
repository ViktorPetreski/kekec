package com.example.kekec;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindCandidateTest {

    public static WebDriver driver;
    public static String baseUrl;
    private static boolean setUpIsDone = false;

    @Before
    public void setUp() throws Exception {

    if(setUpIsDone){
        return;
    }
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
    ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
    driver = new ChromeDriver(chromeOptions);
    baseUrl = "http://localhost:8080/allCandidates";

    driver.get(baseUrl);

    driver.findElement(By.linkText("Додади кандидат")).click();
    driver.findElement(By.id("ssn")).click();
    driver.findElement(By.id("ssn")).clear();
    driver.findElement(By.id("ssn")).sendKeys("1111");
    driver.findElement(By.id("firstName")).clear();
    driver.findElement(By.id("firstName")).sendKeys("Viktor");
    driver.findElement(By.id("lastName")).clear();
    driver.findElement(By.id("lastName")).sendKeys("Petreski");
    driver.findElement(By.id("totalSum")).clear();
    driver.findElement(By.id("totalSum")).sendKeys("12000");
    driver.findElement(By.id("numberOfInstallments")).clear();
    driver.findElement(By.id("numberOfInstallments")).sendKeys("3");
    driver.findElement(By.id("phone")).clear();
    driver.findElement(By.id("phone")).sendKeys("0121212");
    driver.findElement(By.xpath("//div[@id='datetimepicker1']/span/span")).click();
    driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[3]")).click();
    // driver.findElement(By.id("drivingCategory")).click();
    driver.findElement(By.id("drivingCategory")).clear();
    driver.findElement(By.id("drivingCategory")).sendKeys("B");
    driver.findElement(By.id("numberOfLessons")).click();
    driver.findElement(By.id("numberOfLessons")).clear();
    driver.findElement(By.id("numberOfLessons")).sendKeys("36");
    driver.findElement(By.name("id")).click();

    setUpIsDone = true;
    }

    @Test
    public void findNonExistingCandidateTest(){
        driver.get(baseUrl);
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys("ana");
        driver.findElement(By.id("searchButton")).click();

        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[2]")));
        driver.quit();
    }

    @Test
    public void findExistingCandidateTest(){
        driver.get(baseUrl);
        String queryString = "viktor";
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[2]"));
        String name = candidate.findElement(By.id("nameCell")).getText();
        String phone = candidate.findElement(By.id("phoneCell")).getText();

        boolean flag = name.split(" ")[0].equalsIgnoreCase(queryString) ||
                name.split(" ")[1].equalsIgnoreCase(queryString) || phone.equalsIgnoreCase(queryString);
        assertTrue(flag);

        driver.quit();
    }

    @Test
    public void findExistingCandidateByPhoneNumberTest(){
        driver.get(baseUrl);
        String queryString = "0121212";
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[2]"));
        String name = candidate.findElement(By.id("nameCell")).getText();
        String phone = candidate.findElement(By.id("phoneCell")).getText();

        boolean flag = name.split(" ")[0].equalsIgnoreCase(queryString) ||
                name.split(" ")[1].equalsIgnoreCase(queryString) || phone.equalsIgnoreCase(queryString);
        assertTrue(flag);

        driver.quit();
    }

}
