package com.example.kekec;

import org.junit.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class OtherAdditionalSpendingTest {

    private static WebDriver driver;
    private static String baseUrl;

    @BeforeClass
    public static void setUp() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);
        baseUrl = "http://localhost:8080/allCandidates";
        driver.get(baseUrl);
    }

    @Test
    public void test1AddOtherAdditionalSpending() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));

        candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElement(By.id("aSAddButtonCell"))
                .findElement(By.id("aSAddButton"))
                .click();

        Thread.sleep(1000);

        //odberi dopolnitelen cas
        driver.findElement(By.id("aSType")).click();
        new Select(driver.findElement(By.id("aSType"))).selectByVisibleText("Останато");
        driver.findElement(By.id("aSType")).click();

        //dodadi opis
        driver.findElement(By.id("desc")).click();
        driver.findElement(By.id("desc")).clear();
        driver.findElement(By.id("desc")).sendKeys("Полигон");

        //dodadi cena
        driver.findElement(By.id("price")).click();
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("1200");

        Thread.sleep(3000);

        driver.findElement(By.id("newSpendingButton")).click();

        candidate = driver.findElement(By.id("candidateInfoRow0"));
        List<WebElement> elements = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.tagName("td"));

        Thread.sleep(1000);
        assertTrue(elements.size() > 0);
        assertEquals(elements.get(1).findElement(By.id("aSDescText")).getText(), "Полигон");
    }

    @Test
    public void test1NewAdditionalSpendingPrice() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        List<WebElement> elements = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.tagName("td"));

        Thread.sleep(2000);

        assertTrue(elements.size() > 0);
        assertEquals(elements.get(1).findElement(By.id("aSPriceText")).getText(), "Сума:1200.0 ден.");
    }

    @Test
    public void test3PayOtherAdditionalSpending() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        List<WebElement> additionalSpendings = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.id("aSForEach"));

        Thread.sleep(2000);

        String desc = "";
        double additionalSpendingSum = -1.0;

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

        for(WebElement spending : additionalSpendings){
            desc = spending.findElement(By.id("aSDescText")).getText();
            additionalSpendingSum = Double.valueOf(additionalSpending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            if (desc.equals("Полигон") && additionalSpendingSum == 1200){
                additionalSpending = spending;
                break;
            }
        }

        if (additionalSpendingSum != -1) {
//        double additionalSpendingSum = Double.valueOf(additionalSpending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            double oldInDebt = Double.valueOf(candidate.findElement(By.id("InDebtAllUsers")).getText().trim().split(" ")[0].trim());

            additionalSpending
                    .findElement(By.id("aSPayForm"))
                    .findElement(By.id("aSPayButton"))
                    .click();

            Thread.sleep(2000);

            candidate = driver.findElement(By.id("candidateInfoRow0"));
            double newInDebt = Double.valueOf(candidate.findElement(By.id("InDebtAllUsers")).getText().trim().split(" ")[0].trim());
            assertEquals(oldInDebt - newInDebt, additionalSpendingSum);
        }
    }

    @Test
    public void test4PaymentStatus() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        List<WebElement> additionalSpendings = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.id("aSForEach"));

        Thread.sleep(2000);

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

        Thread.sleep(1000);
        assertNotNull(additionalSpending);

        String desc = "";
        double additionalSpendingSum = -1;
        for(WebElement spending : additionalSpendings){
            desc = spending.findElement(By.id("aSDescText")).getText();
            additionalSpendingSum = Double.valueOf(additionalSpending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            if (desc.equals("Полигон") && additionalSpendingSum == 1200){
                additionalSpending = spending;
                break;
            }
        }
        assertTrue(additionalSpending.findElement(By.id("aSIsPaidText")).isDisplayed());
    }

    @Test
    public void test5DatePaid() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        List<WebElement> additionalSpendings = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.id("aSForEach"));

        Thread.sleep(2000);

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

        Thread.sleep(1000);
        assertNotNull(additionalSpending);

        String desc = "";
        double additionalSpendingSum = -1;
        for(WebElement spending : additionalSpendings){
            desc = spending.findElement(By.id("aSDescText")).getText();
            additionalSpendingSum = Double.valueOf(additionalSpending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            if (desc.equals("Полигон") && additionalSpendingSum == 1200){
                additionalSpending = spending;
                break;
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        String date = additionalSpending.findElement(By.id("aSdatePaidText")).getText().split(":")[1].trim();
        LocalDate datePaid = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();

        assertEquals(now, datePaid);
    }


    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
