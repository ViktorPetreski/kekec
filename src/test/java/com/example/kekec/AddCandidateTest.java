package com.example.kekec;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCandidateTest {
    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    @Test
    public void test1AddCandidate() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.linkText("Додади кандидат")).click();
        driver.findElement(By.id("ssn")).click();
        driver.findElement(By.id("ssn")).clear();
        driver.findElement(By.id("ssn")).sendKeys("2222");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Lodi");
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Dodevska");
        driver.findElement(By.id("totalSum")).clear();
        driver.findElement(By.id("totalSum")).sendKeys("15000");
        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("3");
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("0121212");
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[3]")).click();
        driver.findElement(By.id("drivingCategory")).clear();
        driver.findElement(By.id("drivingCategory")).sendKeys("B");
        driver.findElement(By.id("numberOfLessons")).click();
        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("36");
        driver.findElement(By.name("addCandidateButton")).click();

        //No. of cols
        List<WebElement> col = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/th"));
        int colSize = col.size();
        //No.of rows
        List<WebElement>  rows = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/td[1]"));
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

        assertTrue(flag);

    }
}