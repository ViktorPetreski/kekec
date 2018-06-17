package com.example.kekec;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveCandidateTest {
    public static WebDriver driver;
    public static String baseUrl;

    @BeforeClass
    public static void setUp() throws Exception{
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    @Test
    public void test8removeCandidate() throws InterruptedException {
        driver.get("http://localhost:8080/allCandidates");
        driver
                .findElement(By.id("candidateInfoRow0"))
                .findElement(By.id("updateCell"))
                .findElement(By.id("formDeleteButton"))
                .findElement(By.id("deleteButton"))
                .click();

        assertTrue(TestUnit.removeCandidate(0).matches("^Дали сте сигурни дека сакате да го избришете кандидатот[\\s\\S]$"));

        Thread.sleep(2000);
   }

   @AfterClass
    public static void tearDown(){
        driver.quit();
   }



}
