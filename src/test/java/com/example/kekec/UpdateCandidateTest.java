package com.example.kekec;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateCandidateTest {
    public static WebDriver driver;
    public static String baseUrl;
    private static boolean setUpIsDone = false;

    @Before
    public void setUp() throws Exception {

        if(setUpIsDone){
            return;
        }
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
         System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
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
        List<WebElement> numOfInstallments = updatedCandidate.findElements(By.xpath(".//*[@id='installmentTable']/table/tbody/tr/td"));
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


}
