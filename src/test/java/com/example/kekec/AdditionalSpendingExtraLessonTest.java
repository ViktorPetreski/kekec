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
public class AdditionalSpendingExtraLessonTest {

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

    //Dodavanje na nov dodaten trosok - opcija "dopolnitelen cas"
    @Test
    public void test1AddExtraLesson() throws InterruptedException {
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
        new Select(driver.findElement(By.id("aSType"))).selectByVisibleText("Дополнителен час");
        driver.findElement(By.id("aSType")).click();

        //dodadi broj na casovi
        driver.findElement(By.id("lessonNumber")).clear();
        driver.findElement(By.id("lessonNumber")).sendKeys("1");
        driver.findElement(By.id("lessonNumber")).click();

        //dodadi cena
        driver.findElement(By.id("price")).click();
        driver.findElement(By.id("price")).clear();
        driver.findElement(By.id("price")).sendKeys("1500");

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
        assertEquals(elements.get(0).findElement(By.id("aSDescText")).getText(), "1 часови");
    }

    //Dali zapisanata cena na noviot trosok e ista kako i vnesenata
    @Test
    public void test2NewExtraLessonPrice() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        List<WebElement> elements = candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.tagName("td"));

        Thread.sleep(2000);

        assertTrue(elements.size() > 0);
        assertEquals(elements.get(0).findElement(By.id("aSPriceText")).getText(), "Сума:1500.0 ден.");
    }

    //Plakjanje na dodatniot trosok
    @Test
    public void test3PayAdditionalSpendingExtraLesson() throws InterruptedException {
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
            if (desc.equals("1 часови") && additionalSpendingSum == 1500){
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
    public void test4ExtraLessonPaymentStatus() throws InterruptedException {
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
            if (desc.equals("1 часови") && additionalSpendingSum == 1500){
                additionalSpending = spending;
                break;
            }
        }
        assertNotNull(additionalSpending);
        assertTrue(additionalSpending.findElement(By.id("aSIsPaidText")).isDisplayed());
    }

    //Proverka na datumot na plakjanje
    @Test
    public void test5ExtraLessonDatePaid() throws InterruptedException {
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
            if (desc.equals("1 часови") && additionalSpendingSum == 1500){
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

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }

}
