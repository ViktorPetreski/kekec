package com.example.kekec;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.*;

public class DrivingLessonsTest {

    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @BeforeClass
    public static void setUp() throws Exception {
     System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
    //     System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
    //    chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
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
    }

    @Test
    public void testAddNewDrivingLesson(){
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        String oldLessons = candidate.findElement(By.id("lessonsCell")).getText();

        candidate
                .findElement(By.id("updateCell"))
                .findElement(By.id("addDrivingLessonButton"))
                .click();

        //odberi datum
        driver.findElement(By.id("calendarIconButton")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[3]/td[5]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li[2]/table/tbody/tr/td/a/span")).click();
        driver.findElement(By.id("calendarIconButton")).click();

        //odberi instruktor so indeks 1
        driver.findElement(By.id("instructorId")).click();
        new Select(driver.findElement(By.id("instructorId"))).selectByIndex(1);
        driver.findElement(By.id("instructorId")).click();

        //odberi tip na cas
        driver.findElement(By.id("lessonType")).click();
        new Select(driver.findElement(By.id("lessonType"))).selectByIndex(1);
        driver.findElement(By.id("lessonType")).click();
        driver.findElement(By.name("id")).click();

        candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));
        String newLessons = candidate.findElement(By.id("lessonsCell")).getText();

        assertEquals(2, Integer.valueOf(oldLessons) - Integer.valueOf(newLessons));

    }


    @Test
    public void testCandidateLessonOverview() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.xpath("//div[@id='allUsers']/table/tbody/tr[3]"));

        String numberOfLessons = candidate.findElement(By.id("lessonsCell")).getText();
        String candidateName = candidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");

        candidate
                .findElement(By.id("updateCell"))
                .findElement(By.id("lessonOverviewButton"))
                .click();

        String candidateNameLessonOverview = driver.findElement(By.id("candidateInfoInLessons")).getText().split(":")[1].trim();
        assertEquals(candidateName, candidateNameLessonOverview);

        List<WebElement> lessonTypes = driver.findElements(By.id("forEachDiv"));
        Thread.sleep(1500);

        int total = 0;
        for(WebElement lesson : lessonTypes){
            String number = lesson.findElement(By.id("lessonTypeText")).getText().trim().split(" ")[1];
            int k = 0;
            if(number.equalsIgnoreCase("Два_часа")){
                k = 2;
            }
            else if (number.equalsIgnoreCase("Еден_час")){
                k = 1;
            }
            total += k;
        }

        int drivenLessons = Integer.valueOf(numberOfLessons);
        assertEquals(total + drivenLessons, 36);
    }

    //za prviot instruktor
    @Test
    public void testInstructorTotalLessons(){

        driver.findElement(By.id("instructorsTab")).click();
        String Script = "javascript:document.getElementById('linkName').click();";
        ((JavascriptExecutor) driver).executeScript(Script);
        int total = Integer.valueOf(driver.findElement(By.id("totalLessonsInCategory")).
                getText().split(" ")[6]);

        assertEquals(1, total);
    }

    //prv instruktor, juni 2018
    @Test
    public void testSearchByMonth(){
        driver.findElement(By.id("instructorsTab")).click();
        String Script = "javascript:document.getElementById('linkName').click();";
        ((JavascriptExecutor) driver).executeScript(Script);

        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[3]/table/tbody/tr/td/span[6]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[2]/table/tbody/tr/td/span[6]")).click();
        driver.findElement(By.xpath("//div[@id='page-wrapper']/div/div/div/form/div/button/i")).click();

        List<WebElement> elements = driver.findElements(By.id("sortedLessons"));
        boolean flag = false;

        for (WebElement e:elements) {
            WebElement tmp = e;
            String date[] = tmp.findElement(By.id("lessonDate")).getText().split("/");
            String year = date[2].split(" ")[0];
            if(date[1].equals("Jun") && year.equals("2018")) {
                flag = true;
            }
            else{
                flag = false;
            }

        }

        assertTrue(flag);

    }

    //prv instruktor, maj 2020
    @Test
    public void testSearchByMonth2(){
        driver.get(baseUrl);
        driver.findElement(By.id("instructorsTab")).click();
        String Script = "javascript:document.getElementById('linkName').click();";
        ((JavascriptExecutor) driver).executeScript(Script);

        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[3]/table/tbody/tr/td/span[8]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[2]/table/tbody/tr/td/span[5]")).click();
        driver.findElement(By.xpath("//div[@id='page-wrapper']/div/div/div/form/div/button/i")).click();

        List<WebElement> elements = driver.findElements(By.id("sortedLessons"));
        boolean flag = false;

        for (WebElement e:elements) {
            WebElement tmp = e;
            String date[] = tmp.findElement(By.id("lessonDate")).getText().split("/");
            String year = date[2].split(" ")[0];
            if(date[1].equals("May") && year.equals("2018")) {
                flag = true;
            }
            else{
                flag = false;
            }

        }

        assertFalse(flag);

    }

    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
