package com.example.kekec;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class CandidateLessonsOverviewTest {

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
    public void testCandidateName(){
        driver.get(baseUrl);
        String name = driver.findElement(By.id("candidateInfoRow0")).findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");

        driver.
                findElement(By.id("candidateInfoRow0")).
                findElement(By.id("lessonOverviewButton")).
                click();

        String candidateName = driver.findElement(By.id("candidateInfoInLessons")).getText();
        assertEquals("Кандидат: " + name, candidateName);
    }

    //dali postoi instruktorot za cas #1 vo listata na instruktori
    @Test
    public void testInstructorName() throws InterruptedException {
        driver.get(baseUrl);
        driver.
                findElement(By.id("candidateInfoRow0")).
                findElement(By.id("lessonOverviewButton")).
                click();

        WebElement instructor = driver.findElements(By.id("panelDiv")).get(0).findElement(By.id("instructorName"));
        String instructorName = instructor.getText().replaceFirst("Инструктор: ","");

        driver.get(baseUrl);
        driver.findElement(By.id("instructorsTab")).click();
        List<WebElement> instructors = driver.findElements(By.id("divName"));

        Thread.sleep(1000);
        boolean flag = false;
        for(WebElement ins : instructors) {
            WebElement tmp = driver.findElement(By.id("instructorName"));
            String tmpName = tmp.getText().replaceAll("\\r\\n|\\r|\\n", " ");;
            if (tmpName.equalsIgnoreCase(instructorName)){
                flag = true;
                break;
            }
        }

        assertTrue(flag);

    }

}
