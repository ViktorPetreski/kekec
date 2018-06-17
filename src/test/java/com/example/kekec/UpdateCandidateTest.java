package com.example.kekec;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateCandidateTest {
    public static WebDriver driver;
    public static String baseUrl;
    private static boolean setUpIsDone = false;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {

        if(setUpIsDone){
            return;
        }

        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;

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
        driver.findElement(By.id("totalSum")).sendKeys("15000");
        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("3");
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("076543909");
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[3]")).click();
        driver.findElement(By.id("drivingCategory")).clear();
        driver.findElement(By.id("drivingCategory")).sendKeys("B");
        driver.findElement(By.id("numberOfLessons")).click();
        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("36");
        driver.findElement(By.name("addCandidateButton")).click();
        Thread.sleep(1000);

        setUpIsDone = true;
    }

    @Test
    public void testUpdateCandidateFirstName() throws InterruptedException {
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));
        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        Thread.sleep(1000);

        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("NewName");
        driver.findElement(By.name("updateCandidateButton")).click();
        Thread.sleep(1000);

        WebElement updatedCandidate = driver.findElement(By.id("candidateInfoRow1"));
        String newFirstName = updatedCandidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ").split(" ")[0];
        Thread.sleep(1000);
        assertEquals("NewName", newFirstName);
    }

    @Test
    public void testUpdateCandidateLastName() throws InterruptedException {
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        Thread.sleep(1000);

        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("NewLastName");
        driver.findElement(By.name("updateCandidateButton")).click();
        Thread.sleep(1000);


        WebElement updatedCandidate = driver.findElement(By.id("candidateInfoRow1"));
        String newLastName = updatedCandidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ").split(" ")[1];
        Thread.sleep(1000);
        assertEquals("NewLastName", newLastName);
    }

    @Test
    public void testUpdateCandidatePhone() throws InterruptedException {
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();

        Thread.sleep(1000);

        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("075987333");
        driver.findElement(By.name("updateCandidateButton")).click();
        Thread.sleep(1000);

        WebElement updatedCandidate = driver.findElement(By.id("candidateInfoRow1"));
        String newPhone = updatedCandidate.findElement(By.id("phoneCell")).getText();
        Thread.sleep(1000);
        assertEquals("075987333", newPhone);
    }

    @Test
    public void testUpdateCandidateInstallments() throws InterruptedException {
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();
        Thread.sleep(1000);

        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("5");
        driver.findElement(By.name("updateCandidateButton")).click();
        Thread.sleep(1000);

        WebElement updatedCandidate = driver.findElement(By.id("candidateInfoRow1"));
        List<WebElement> numOfInstallments = updatedCandidate.findElements(By.xpath(".//*[@id='installmentTable']/table/tbody/tr/td"));
        Thread.sleep(1000);
        assertEquals(5,numOfInstallments.size());
    }

    @Test
    public void testUpdateCandidateTotalSum() throws InterruptedException {
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();
        Thread.sleep(1000);

        driver.findElement(By.id("totalSum")).clear();
        driver.findElement(By.id("totalSum")).sendKeys("30000");
        driver.findElement(By.name("updateCandidateButton")).click();
        Thread.sleep(1000);

        WebElement updatedCandidate = driver.findElement(By.id("candidateInfoRow1"));
        String newSum = updatedCandidate.findElement(By.id("sumCell")).getText();
        Thread.sleep(1000);
        assertEquals("30000.0 ден.", newSum);
    }

    @Test
    public void testUpdateCandidateLessons() throws InterruptedException {
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));

        candidate.
                findElement(By.id("updateCell")).
                findElement(By.id("updateButton")).
                click();
        Thread.sleep(1000);

        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("25");
        driver.findElement(By.name("updateCandidateButton")).click();
        Thread.sleep(1000);

        WebElement updatedCandidate = driver.findElement(By.id("candidateInfoRow1"));
        String newNumOfLessons = updatedCandidate.findElement(By.id("lessonsCell")).getText();
        Thread.sleep(1000);
        assertEquals("25", newNumOfLessons);
    }

    @AfterAll
    @Test
    public void testRemoveUpdatedCandidate() throws InterruptedException {

        //da go izbrise vtoriot t.e. toj so ke go update izatoa e index = 1
        assertTrue(TestUnit.removeCandidate(1).matches("^Дали сте сигурни дека сакате да го избришете кандидатот[\\s\\S]$"));

        Thread.sleep(2000);

        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }

    }


/*    private String removeCandidate() {

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
    }*/

}
