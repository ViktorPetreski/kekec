package com.example.kekec;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BadInputTest {

    public static WebDriver driver;
    public static String baseUrl;

    @BeforeClass
    public static void setUp() throws Exception {
//         System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);
        baseUrl = "http://localhost:8080/allCandidates";
    }

    @Test
    public void testAddCandidateWithMissingInput() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.linkText("Додади кандидат")).click();
        driver.findElement(By.id("ssn")).click();
        driver.findElement(By.id("ssn")).clear();
        driver.findElement(By.id("ssn")).sendKeys("2222");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Lodi");
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Dodevska");
//        driver.findElement(By.id("totalSum")).clear();
//        driver.findElement(By.id("totalSum")).sendKeys("15000");
        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("3");
//        driver.findElement(By.id("phone")).clear();
//        driver.findElement(By.id("phone")).sendKeys("0121212");
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[3]")).click();
        driver.findElement(By.id("drivingCategory")).clear();
        driver.findElement(By.id("drivingCategory")).sendKeys("B");
        driver.findElement(By.id("numberOfLessons")).click();
        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("36");
        driver.findElement(By.name("addCandidateButton")).click();

        Thread.sleep(3000);
        assertEquals("http://localhost:8080/addCandidate",driver.getCurrentUrl());

    }

    @Test
    public void testTableCheck() throws InterruptedException {
        //No.of rows
        List<WebElement> rows = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/td[1]"));
        int rowSize = rows.size(); //elements + header

        Thread.sleep(1000);
        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='allUsers']/table/tbody/tr"));
        boolean flag = false;
        for (int i = 1; i < rowSize+1; i++)
        {
            WebElement tmp = elements.get(i);
            String name = tmp.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
            String phone = tmp.findElement(By.id("phoneCell")).getText().trim();
            if (name.equals("Lodi Dodevska") && phone.equals("0121212"))
            {
                flag = true;
                break;
            }
        }

        assertFalse(flag);

    }


}
