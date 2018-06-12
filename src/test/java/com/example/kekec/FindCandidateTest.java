package com.example.kekec;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FindCandidateTest {

    public static WebDriver driver;
    public static String baseUrl;

    @Before
    public void setUp() throws Exception {

//     System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
    System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
    ChromeOptions chromeOptions = new ChromeOptions();
//    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
    driver = new ChromeDriver(chromeOptions);
    baseUrl = "http://localhost:8080/allCandidates";

    }

    @Test
    public void findNonExistingCandidateTest() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys("ana");
        driver.findElement(By.id("searchButton")).click();
        Thread.sleep(4000);
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("candidateInfoRow0")));
    }

    @Test
    public void findExistingCandidateTest() throws InterruptedException {
        driver.get(baseUrl);
        String queryString = "Lodi";
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(4000);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        String name = candidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
        String phone = candidate.findElement(By.id("phoneCell")).getText();

        boolean flag = name.split(" ")[0].equalsIgnoreCase(queryString) ||
                name.split(" ")[1].equalsIgnoreCase(queryString) || phone.equalsIgnoreCase(queryString);
        assertTrue(flag);

    }

    @Test
    public void findExistingCandidateTest2() throws InterruptedException {
        driver.get(baseUrl);
        String queryString = "Lodi";
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(4000);
        List<WebElement>  rows = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/td[1]"));
        int rowSize = rows.size(); //elements + header

        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='allUsers']/table/tbody/tr"));
        boolean flag = true;
        for (int i = 1; i < rowSize+1; i++)
        {
            WebElement tmp = elements.get(i);
            String name = tmp.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
            String phone = tmp.findElement(By.id("phoneCell")).getText().trim();
            flag = flag && (name.split(" ")[0].equalsIgnoreCase(queryString) ||
                    name.split(" ")[1].equalsIgnoreCase(queryString) ||
                    phone.equalsIgnoreCase(queryString));
        }

        assertTrue(flag);

    }


    @Test
    public void findExistingCandidateByPhoneNumberTest() throws InterruptedException {
        driver.get(baseUrl);
        String queryString = "0121212";
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(4000);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        String name = candidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
        String phone = candidate.findElement(By.id("phoneCell")).getText();

        boolean flag = name.split(" ")[0].equalsIgnoreCase(queryString) ||
                name.split(" ")[1].equalsIgnoreCase(queryString) || phone.equalsIgnoreCase(queryString);
        assertTrue(flag);

    }

    @Test
    public void findExistingCandidateByPhoneNumberTest2() throws InterruptedException {
        driver.get(baseUrl);
        String queryString = "0121212";
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(4000);
        List<WebElement>  rows = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/td[1]"));
        int rowSize = rows.size(); //elements + header

        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='allUsers']/table/tbody/tr"));
        boolean flag = true;
        for (int i = 1; i < rowSize+1; i++)
        {
            WebElement tmp = elements.get(i);
            String name = tmp.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
            String phone = tmp.findElement(By.id("phoneCell")).getText().trim();
            flag = flag && (name.split(" ")[0].equalsIgnoreCase(queryString) ||
                    name.split(" ")[1].equalsIgnoreCase(queryString) ||
                    phone.equalsIgnoreCase(queryString));
        }

        assertTrue(flag);

    }


    @AfterClass
    public static void flush(){
        driver.quit();
    }

}
