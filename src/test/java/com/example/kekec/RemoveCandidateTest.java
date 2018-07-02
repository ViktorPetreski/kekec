package com.example.kekec;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveCandidateTest {
    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception{
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    //Brisenje na kandidat
    @Test
    public void test8removeCandidate() throws InterruptedException {
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
