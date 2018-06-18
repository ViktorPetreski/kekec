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
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    //Dodavanje na nov dodaten trosok - opcija "ostanato"
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

        //odberi "ostanato"
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

    //Dali zapisanata cena na noviot trosok e ista kako i vnesenata
    @Test
    public void test2NewAdditionalSpendingPrice() throws InterruptedException {
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

    //Plakjanje na dodatniot trosok
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

        for(WebElement spending : additionalSpendings){
            desc = spending.findElement(By.id("aSDescText")).getText();
            additionalSpendingSum = Double.valueOf(spending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            if (desc.equals("Полигон") && additionalSpendingSum == 1200){
                additionalSpending = spending;
                break;
            }
        }

        assertNotNull(additionalSpending);

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

    //Proverka na status na trosok posle plakjanje
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

        String desc = "";
        double additionalSpendingSum = -1;
        for(WebElement spending : additionalSpendings){
            desc = spending.findElement(By.id("aSDescText")).getText();
            additionalSpendingSum = Double.valueOf(spending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            if (desc.equals("Полигон") && additionalSpendingSum == 1200){
                additionalSpending = spending;
                break;
            }
        }
        assertNotNull(additionalSpending);
        assertTrue(additionalSpending.findElement(By.id("aSIsPaidText")).isDisplayed());
    }

    //Proverka na datumot na plakjanje
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

        String desc = "";
        double additionalSpendingSum = -1;
        for(WebElement spending : additionalSpendings){
            desc = spending.findElement(By.id("aSDescText")).getText();
            additionalSpendingSum = Double.valueOf(spending.findElement(By.id("aSPriceText")).getText().split(":")[1].trim().split(" ")[0].trim());
            if (desc.equals("Полигон") && additionalSpendingSum == 1200){
                additionalSpending = spending;
                break;
            }
        }

        assertNotNull(additionalSpending);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        String date = additionalSpending.findElement(By.id("aSdatePaidText")).getText().split(":")[1].trim();
        LocalDate datePaid = LocalDate.parse(date, formatter);
        LocalDate now = LocalDate.now();

        assertEquals(now, datePaid);
    }


}
