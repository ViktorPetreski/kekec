package com.example.kekec;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CandidateLessonsOverviewTest {

    public static WebDriver driver;
    public static String baseUrl;

    @BeforeClass
    public static void setUp() throws Exception {
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }


    //Proverka na statusot - dali e polozen ili nepolozen
    @Test
    public void test1StatusOfLicence() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0")).findElement(By.name("status"));
        Assert.assertNotNull(candidate.findElement(By.xpath("//i[@class='fa fa-times']")));
    }

    //Proverka na statusot otkako e promenet
    @Test
    public void test2ChangedStatusOfLicence() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0")).findElement(By.name("status"));
        candidate.click();
        Thread.sleep(500);
        candidate = driver.findElement(By.id("candidateInfoRow0")).findElement(By.name("status"));
        Assert.assertNotNull(candidate.findElement(By.xpath("//i[@class='fa fa-check']")));
    }

    //Dali se otvara pregled za odbraniot kandidat
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

    //Dali postoi instruktorot za cas #1 vo listata na instruktori
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

    //Dali zapisaniot datum e validen
    @Test
    public void testValidityOfDate(){
        driver.get(baseUrl);
        driver.
                findElement(By.id("candidateInfoRow0")).
                findElement(By.id("lessonOverviewButton")).
                click();

        WebElement instructor = driver.findElements(By.id("panelDiv")).get(0).findElement(By.id("date"));
        String dateTmp = instructor.getText().replace("Датум: ","").trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy HH:mm");
        LocalDateTime date = LocalDateTime.parse(dateTmp, formatter);

        assertTrue(date.getYear()==2018 && date.getMonth()== Month.JUNE && date.getDayOfMonth()==22);

    }

    //Redirect kon facebook page
    @Test
    public void testfacebookRedirect() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.id("fbLink")).click();
        Thread.sleep(1000);
        assertEquals("https://www.facebook.com/avtoskolakekec/", driver.getCurrentUrl());
    }

}
