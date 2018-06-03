package com.example.kekec;

import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {
    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);
        baseUrl = "http://localhost:8080/allCandidates";

        driver.get(baseUrl);

        driver.findElement(By.linkText("Додади кандидат")).click();
        driver.findElement(By.id("ssn")).click();
        driver.findElement(By.id("ssn")).clear();
        driver.findElement(By.id("ssn")).sendKeys("1111");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Test");
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Test Prezime");
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
    }


    @Test
    public void testStatusOfLicence() throws InterruptedException {
        driver.get(baseUrl);
        //driver.manage().window().maximize();
        WebElement test = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]")).findElement(By.name("status"));
        test.click();
        Thread.sleep(500);
        test = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]")).findElement(By.name("status"));
        Assert.assertNotNull(test.findElement(By.xpath("//i[@class='fa fa-check']")));
    }


    @Test
    public void testPayInstallmentPositive() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        candidate.findElement(By.id("inlineFormInputGroup")).click();
        candidate.findElement(By.id("inlineFormInputGroup")).clear();
        candidate.findElement(By.id("inlineFormInputGroup")).sendKeys("1200");
        candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String oldDebt = candidate.findElement(By.id("InDebtAllUsers")).getText();
        candidate.findElements(By.tagName("button")).get(0).click();
        Thread.sleep(1000);
        candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newDebt = candidate.findElement(By.id("InDebtAllUsers")).getText();

        assertNotEquals(oldDebt, newDebt);
    }

    @Test
    public void testNewAdditionalSpending(){
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
        List<WebElement> elements =
                candidate
                .findElement(By.id("aSCell"))
                .findElement(By.id("aSTable"))
                .findElement(By.id("aSRow"))
                .findElements(By.tagName("td"));

        assertTrue(elements.size() > 0);
        assertEquals(elements.get(0).findElement(By.id("aSDescText")).getText(), "Test description");
        assertEquals(elements.get(0).findElement(By.id("aSPriceText")).getText(), "Сума:1500.0 ден.");

    }

    @Test
    public void testInstructor() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.id("addInstructor")).click();

        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Test name");

        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("last name");

        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("071xxxxxx");

        driver.findElement(By.id("newInstructorButton")).click();

        Thread.sleep(1000);

        WebElement instructor = driver.findElement(By.xpath("//div[@id='instructors']/div[2]"));

        assertEquals("Test name last name", instructor.findElement(By.id("divName")).findElement(By.id("instructorName")).getText());

    }




    @After
    public void tearDown() throws Exception {
        driver.get("http://localhost:8080/allCandidates");
        driver
                .findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"))
                .findElement(By.id("updateCell"))
                .findElement(By.id("formDeleteButton"))
                .findElement(By.id("deleteButton"))
                .click();

        //driver.findElement(By.xpath("(//button[@onclick=\"return confirm('Дали сте сигурни дека сакате да го избришете кандидатот?')\"])[2]")).click();
        acceptNextAlert = true;
        assertTrue(closeAlertAndGetItsText().matches("^Дали сте сигурни дека сакате да го избришете кандидатот[\\s\\S]$"));

        Thread.sleep(2000);

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
        driver.quit();
    }

    private String closeAlertAndGetItsText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
