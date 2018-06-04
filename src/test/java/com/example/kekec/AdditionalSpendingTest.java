package com.example.kekec;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdditionalSpendingTest {

    private static WebDriver driver;
    private static String baseUrl;
    private static boolean setUpIsDone = false;


    @Before
    public void setUp() {
        if(setUpIsDone){
            return;
        }
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
        // System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        //chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
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
    public void test1NewAdditionalSpending(){
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElement(By.id("aSAddButtonCell"))
                .findElement(By.id("aSAddButton"))
                .click();

        //descrtption input
        driver.findElement(By.id("desc")).click();
        driver.findElement(By.id("desc")).clear();
        driver.findElement(By.id("desc")).sendKeys("Test description");

        //price input
        driver.findElement(By.id("price")).click();
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("1500");

        driver.findElement(By.id("newSpendingButton")).click();

        candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        List<WebElement> elements = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.tagName("td"));

        assertTrue(elements.size() > 0);
        assertEquals(elements.get(0).findElement(By.id("aSDescText")).getText(), "Test description");
        assertEquals(elements.get(0).findElement(By.id("aSPriceText")).getText(), "Сума:1500.0 ден.");
    }

    @Test
    public void test2PayAdditionalSpending(){
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        List<WebElement> additionalSpendings = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.id("aSForEach"));

        WebElement additionalSpending = null;
        for (WebElement spending : additionalSpendings){
            try {
                spending.findElement(By.id("aSIsPaidText"));
            }
            catch (org.openqa.selenium.NoSuchElementException e) {
                additionalSpending = spending;
            }
        }

        assertNotNull(additionalSpending);

        double additionalSpendingSum = Double.valueOf(additionalSpending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
        double oldInDebt = Double.valueOf(candidate.findElement(By.id("InDebtAllUsers")).getText().trim().split(" ")[0].trim());

        additionalSpending
                .findElement(By.id("aSPayForm"))
                .findElement(By.id("aSPayButton"))
                .click();

        candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        double newInDebt = Double.valueOf(candidate.findElement(By.id("InDebtAllUsers")).getText().trim().split(" ")[0].trim());
        assertEquals(oldInDebt - newInDebt, additionalSpendingSum);

    }

    @Test
    public void test3DatePaid(){
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        List<WebElement> additionalSpendings = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.id("aSForEach"));

        WebElement additionalSpending = null;

        for (WebElement spending : additionalSpendings){
            try {
                spending.findElement(By.id("aSIsPaidText"));
                additionalSpending = spending;
            }
            catch (org.openqa.selenium.NoSuchElementException e) {
                e.printStackTrace();
            }
        }

        assertNotNull(additionalSpending);
        assertTrue(additionalSpending.findElement(By.id("aSIsPaidText")).isDisplayed());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        String date = additionalSpending.findElement(By.id("aSdatePaidText")).getText().split(":")[1].trim();
        LocalDate datePaid = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();

        assertEquals(now, datePaid);
    }


}
