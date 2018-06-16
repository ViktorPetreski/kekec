package com.example.kekec;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class FindCandidateParameterizedTest {

    public static WebDriver driver;
    public static String baseUrl;
    private String queryString;

    @Before
    public void setUp() throws Exception {

//     System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);
        baseUrl = "http://localhost:8080/allCandidates";

    }

    public FindCandidateParameterizedTest(String queryString){
        super();
        this.queryString = queryString;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"Lodi"},
                {"Dodevska"},
                {"Lodi Dodevska"},
                {"0121212"}
        });
    }

    @Test
    public void testQuery() throws InterruptedException {
        driver.get(baseUrl);
//        String queryString = input;
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(4000);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        String name = candidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
        String phone = candidate.findElement(By.id("phoneCell")).getText();

        boolean flag = name.split(" ")[0].equalsIgnoreCase(queryString) ||
                name.split(" ")[1].equalsIgnoreCase(queryString)
                || name.equalsIgnoreCase(queryString) || phone.equalsIgnoreCase(queryString);

        assertTrue(flag);
    }

    @AfterClass
    public static void flush(){
        driver.quit();
    }





}
