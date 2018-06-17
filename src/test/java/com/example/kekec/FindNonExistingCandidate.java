package com.example.kekec;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class FindNonExistingCandidate {

        public WebDriver driver;
        public String baseUrl;
        private String queryString;
        private boolean output;

        @Before
        public void setUp() throws Exception {
//     System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
//    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
            chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
            driver = new ChromeDriver(chromeOptions);
            baseUrl = "http://localhost:8080/allCandidates";
            driver.get(baseUrl);
        }

        //Prebaruvanje na kandidat spored ime sto go nema vo bazata
        @Test
        public void findNonExistingCandidateTest() throws InterruptedException {
            driver.get(baseUrl);
            driver.findElement(By.name("query")).click();
            driver.findElement(By.name("query")).clear();
            driver.findElement(By.name("query")).sendKeys("ana");
            driver.findElement(By.id("searchButton")).click();
            Thread.sleep(4000);
            assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("candidateInfoRow0")));
            driver.quit();
        }

        //Prebaruvanje na kandidat spored telefon sto go nema vo bazata
        @Test
        public void findNonExistingCandidateByPhoneTest() throws InterruptedException {
            driver.get(baseUrl);
            driver.findElement(By.name("query")).click();
            driver.findElement(By.name("query")).clear();
            driver.findElement(By.name("query")).sendKeys("076123123");
            driver.findElement(By.id("searchButton")).click();
            Thread.sleep(4000);
            assertThrows(NoSuchElementException.class, () -> driver.findElement(By.id("candidateInfoRow0")));
            driver.quit();
        }


    }





