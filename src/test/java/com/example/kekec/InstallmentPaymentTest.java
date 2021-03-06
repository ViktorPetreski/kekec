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
public class InstallmentPaymentTest {
    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public static void setUp() throws Exception {
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }


    //Plakjanje na prva rata - dali stariot i noviot dolg se razlicni
    @Test
    public void test1PayInstallmentPositive() throws InterruptedException {
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
    public void test2CheckInstallmentSum() throws InterruptedException {
        driver.get(baseUrl);
        Thread.sleep(1000);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));

        String installment = candidate.findElement(By.id("installment0")).findElement(By.id("installmentPaidPrice0")).getText();
        assertEquals("Сума: 1200.0", installment);
    }


}
