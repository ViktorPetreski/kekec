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

import javax.validation.constraints.AssertTrue;
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
    public void testAddInstructor() throws InterruptedException {
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

        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(5000);

        WebElement instructor = driver.findElement(By.xpath("//div[@id='instructors']/div[1]"));
        assertEquals("Test name last name", instructor.findElement(By.id("divName")).findElement(By.id("instructorName")).getText());
    }


//    @Test
//    public void testRemoveCandidate(){
//        driver.get(baseUrl);
//        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[1]"));
//        acceptNextAlert = true;
//        driver.findElement(By.id("deleteButton")).click();
//        assertTrue(closeAlertAndGetItsText().matches("^Дали сте сигурни дека сакате да го избришете кандидатот[\\s\\S]$"));
//
//    }

    @Test
    public void testAddCandidate() throws InterruptedException {
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
        driver.findElement(By.name("id")).click();

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
            String name = tmp.findElement(By.id("nameCell")).getText();
            String phone = tmp.findElement(By.id("phoneCell")).getText();
            if (name.equals("Lodi Dodevska") && phone.equals("0121212"))
            {
                flag = true;
                break;
            }
        }

        assertTrue(flag);

    }

    @Test
    public void testUpdateCandidateFirstName() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("NewName");
        driver.findElement(By.name("id")).click();

        WebElement updatedCandidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newFirstName = updatedCandidate.findElement(By.id("nameCell")).getText().split(" ")[0];
        assertEquals("NewName", newFirstName);
    }

    @Test
    public void testUpdateCandidateLastName() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("NewLastName");
        driver.findElement(By.name("id")).click();

        WebElement updatedCandidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newLastName = updatedCandidate.findElement(By.id("nameCell")).getText().split(" ")[1];
        assertEquals("NewLastName", newLastName);
    }

    @Test
    public void testUpdateCandidatePhone() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("075987333");
        driver.findElement(By.name("id")).click();

        WebElement updatedCandidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newPhone = updatedCandidate.findElement(By.id("phoneCell")).getText();
        assertEquals("075987333", newPhone);
    }

    @Test
    public void testUpdateCandidateInstallments() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("5");
        driver.findElement(By.name("id")).click();

        WebElement updatedCandidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        List<WebElement>  numOfInstallments = updatedCandidate.findElements(By.xpath(".//*[@id='installmentTable']/table/tbody/tr/td"));
        assertEquals(5,numOfInstallments.size());
    }

    @Test
    public void testUpdateCandidateTotalSum() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        driver.findElement(By.id("totalSum")).clear();
        driver.findElement(By.id("totalSum")).sendKeys("30000");
        driver.findElement(By.name("id")).click();

        WebElement updatedCandidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newSum = updatedCandidate.findElement(By.id("sumCell")).getText();
        assertEquals("30000.0 ден.", newSum);
    }

    @Test
    public void testUpdateCandidateLessons() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("25");
        driver.findElement(By.name("id")).click();

        WebElement updatedCandidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newNumOfLessons = updatedCandidate.findElement(By.id("lessonsCell")).getText();
        assertEquals("25", newNumOfLessons);
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
