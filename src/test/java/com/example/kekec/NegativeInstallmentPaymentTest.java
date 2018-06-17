package com.example.kekec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class NegativeInstallmentPaymentTest {

    public static WebDriver driver;
    public static String baseUrl;
    private String installment;

    @Before
    public void setUp() throws Exception {
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    public NegativeInstallmentPaymentTest(String installment){
        super();
        this.installment = installment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testCases() {
        return Arrays.asList(new Object[][]{
                {"-5000"}, // < 0
                {"money"},
                {"0"},
                {"5000000"} // > current debt
        });
    }

    //Proverka na poraka za pogresna suma (broj ili tekst)
    @Test
    public void testPayMoreThanDebtMessage() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        candidate.findElement(By.id("installmentPrice0")).click();
        candidate.findElement(By.id("installmentPrice0")).clear();
        candidate.findElement(By.id("installmentPrice0")).sendKeys(installment);
        candidate.findElement(By.id("installmentPayButton0")).click();

        String message = driver.
                findElement(By.id("candidateInfoRow0")).
                findElement(By.id("largeSumLabel0")).getText();
        Thread.sleep(2000);
        assertEquals("Погрешна сума!!",message);
        driver.quit();
    }

    //Dali se menuva dolgot pri pogresno plakjanje
    @Test
    public void testPayMoreThanDebt() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        String oldDebt = candidate.findElement(By.id("InDebtAllUsers")).getText();

        candidate.findElement(By.id("installmentPrice0")).click();
        candidate.findElement(By.id("installmentPrice0")).clear();
        candidate.findElement(By.id("installmentPrice0")).sendKeys(installment);
        candidate.findElement(By.id("installmentPayButton0")).click();

        Thread.sleep(1000);
        String newDebt = driver.findElement(By.id("candidateInfoRow0")).findElement(By.id("InDebtAllUsers")).getText();
        assertEquals(oldDebt,newDebt);
        driver.quit();

    }


}
