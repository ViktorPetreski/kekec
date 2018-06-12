package com.example.kekec;

import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.validation.constraints.AssertTrue;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitTest {
    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    public static boolean setUpIsDone = false;

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
        List<WebElement>  col = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/th"));
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

    @Test
    public void test2StatusOfLicence() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0")).findElement(By.name("status"));
        candidate.click();
        Thread.sleep(500);
        candidate = driver.findElement(By.id("candidateInfoRow0")).findElement(By.name("status"));
        Assert.assertNotNull(candidate.findElement(By.xpath("//i[@class='fa fa-check']")));
    }

    @Test
    public void test3AddInstructor() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.id("addInstructor")).click();

        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Marko");

        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Jordanovski");

        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("071345678");

        driver.findElement(By.id("newInstructorButton")).click();
        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(2000);

        WebElement instructor = driver.findElement(By.id("instructor0"));
        assertEquals("Marko Jordanovski", instructor.findElement(By.id("divName")).findElement(By.id("instructorName")).getText());
    }

    //Plakjanje na prva rata - dali stariot i noviot dolg se razlicni
    @Test
    public void test4PayInstallmentPositive() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        candidate.findElement(By.id("installmentPrice0")).click();
        candidate.findElement(By.id("installmentPrice0")).clear();
        candidate.findElement(By.id("installmentPrice0")).sendKeys("1200");
        candidate = driver.findElement(By.id("candidateInfoRow0"));

        String oldDebt = candidate.findElement(By.id("InDebtAllUsers")).getText();
        candidate.findElement(By.id("installmentPayButton0")).click();
        Thread.sleep(1000);
        candidate = driver.findElement(By.id("candidateInfoRow0"));
        String newDebt = candidate.findElement(By.id("InDebtAllUsers")).getText();

        assertNotEquals(oldDebt, newDebt);
    }

    //Plakjanje na prva rata - dali vneseniot iznos e ednakov so plateniot
    @Test
    public void test4PayInstallmentPositive2() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        String installment = candidate.findElement(By.id("installment0")).getText();
        assertEquals("Сума: 1200.0", installment);
    }

    @Test
    public void test5facebookRedirect() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.id("fbLink")).click();
        Thread.sleep(1000);
        assertEquals("https://www.facebook.com/avtoskolakekec/", driver.getCurrentUrl());
    }


    @Test
    public void test6removeCandidate() throws InterruptedException {
        driver.get("http://localhost:8080/allCandidates");
        driver
                .findElement(By.id("candidateInfoRow0"))
                .findElement(By.id("updateCell"))
                .findElement(By.id("formDeleteButton"))
                .findElement(By.id("deleteButton"))
                .click();

        acceptNextAlert = true;
        assertTrue(closeAlertAndGetItsText().matches("^Дали сте сигурни дека сакате да го избришете кандидатот[\\s\\S]$"));

        Thread.sleep(2000);

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }

    }


    @AfterClass
    public static void tearDown() throws Exception {
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
