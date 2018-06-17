package com.example.kekec;

import org.junit.*;
import org.junit.runners.MethodSorters;
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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DrivingLessonsTest {

    public static WebDriver driver;
    public static String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();


    @BeforeClass
    public static void setUp() throws Exception {
//     System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\skit\\selenium\\chromedriver.exe");
         System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);
        baseUrl = "http://localhost:8080/allCandidates";

    }

    //Dodavanje na nov instruktor
    @Test
    public void test1AddInstructor() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.id("addInstructor")).click();

        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Marko");

        driver.findElement(By.id("lastName")).click();
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Jordanovski");

        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("071345678");

        Thread.sleep(3000);

        driver.findElement(By.id("newInstructorButton")).click();
        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(2000);

        WebElement instructor = driver.findElement(By.id("instructor0"));
        assertEquals("Marko Jordanovski", instructor.findElement(By.id("divName")).findElement(By.id("instructorName")).getText());
    }


    //Dodavanje na casovi za vozenje - opcija "2 casa"
    @Test
    public void test2AddTwoDrivingLessons() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));

        String oldLessons = candidate.findElement(By.id("lessonsCell")).getText();

        candidate
                .findElement(By.id("updateCell"))
                .findElement(By.id("addDrivingLessonButton"))
                .click();

        //odberi datum
        driver.findElement(By.id("calendarIconButton")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[5]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li[2]/table/tbody/tr/td/a/span")).click();
        driver.findElement(By.id("calendarIconButton")).click();

        //odberi go prviot instruktor
        driver.findElement(By.id("instructorId")).click();
        new Select(driver.findElement(By.id("instructorId"))).selectByIndex(1);
        driver.findElement(By.id("instructorId")).click();

        //odberi tip na cas - 2 casa
        driver.findElement(By.id("lessonType")).click();
        new Select(driver.findElement(By.id("lessonType"))).selectByIndex(1);
        driver.findElement(By.id("lessonType")).click();

        Thread.sleep(3000);

        driver.findElement(By.name("newDrivingLessonButton")).click();

        candidate = driver.findElement(By.id("candidateInfoRow0"));
        String newLessons = candidate.findElement(By.id("lessonsCell")).getText();

        assertEquals(2, Integer.valueOf(oldLessons) - Integer.valueOf(newLessons));

    }

    //Dodavanje na casovi za vozenje - opcija "1 cas"
    @Test
    public void test3AddOneDrivingLesson() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));

        String oldLessons = candidate.findElement(By.id("lessonsCell")).getText();

        candidate
                .findElement(By.id("updateCell"))
                .findElement(By.id("addDrivingLessonButton"))
                .click();

        //odberi datum
        driver.findElement(By.id("calendarIconButton")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[6]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li[2]/table/tbody/tr/td/a/span")).click();
        driver.findElement(By.id("calendarIconButton")).click();

        //odberi go prviot instruktor
        driver.findElement(By.id("instructorId")).click();
        new Select(driver.findElement(By.id("instructorId"))).selectByIndex(1);
        driver.findElement(By.id("instructorId")).click();

        //odberi tip na cas - 1 cas
        driver.findElement(By.id("lessonType")).click();
        new Select(driver.findElement(By.id("lessonType"))).selectByIndex(2);
        driver.findElement(By.id("lessonType")).click();

        Thread.sleep(3000);

        driver.findElement(By.name("newDrivingLessonButton")).click();

        candidate = driver.findElement(By.id("candidateInfoRow0"));
        String newLessons = candidate.findElement(By.id("lessonsCell")).getText();

        assertEquals(1, Integer.valueOf(oldLessons) - Integer.valueOf(newLessons));

    }

    //Dali brojot na izvozeni i neizvozeni casovi se poklopuva so vkupniot broj na casovi
    @Test
    public void test4CandidateLessonOverview() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));

        String numberOfLessons = candidate.findElement(By.id("lessonsCell")).getText();
        String candidateName = candidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
        Thread.sleep(1000);

        candidate
                .findElement(By.id("updateCell"))
                .findElement(By.id("lessonOverviewButton"))
                .click();

        Thread.sleep(2000);

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

    //Broj na zakazani casovi kaj prviot instruktor, juni 2018
    @Test
    public void test5InstructorTotalLessonsInCategory() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("instructor0")).click();

        WebElement lessons = driver.findElement(By.id("tableWithLessons")).findElement(By.id("sortedLessons")).
                findElement(By.id("totalLessonsInCategory"));
        Thread.sleep(2000);

//   ne brisi
//        int total = Integer.valueOf(lessons.getText().split(" ")[6]);
//        assertEquals(2, total);
//
        String total = lessons.getText();
        assertEquals(total,"Број на часови во оваа категoрија: 2");
    }

    //Vkupen broj na zakazani casovi (sum(1 cas, 2 casa)) kaj prviot instruktor, juni 2018
    @Test
    public void test6InstructorTotalLessons() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("instructor0")).click();

        List<WebElement> lessons = driver.findElements(By.id("lessonType"));
        Thread.sleep(1500);

        int total = 0;
        for(WebElement lesson : lessons){
            String number = lesson.findElement(By.id("lessonType")).getText().trim().split(" ")[1];
            int k = 0;
            if(number.equalsIgnoreCase("Два_часа")){
                k = 2;
            }
            else if (number.equalsIgnoreCase("Еден_час")){
                k = 1;
            }
            total += k;
        }

        assertEquals(3,total);
    }

    //Prebaruvanje po mesec, prv instruktor, juni 2018
    @Test
    public void test7SearchByMonth() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("instructor0")).click();
//        String Script = "javascript:document.getElementById('instructor0').click();";
//        ((JavascriptExecutor) driver).executeScript(Script);

        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[3]/table/tbody/tr/td/span[6]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[2]/table/tbody/tr/td/span[6]")).click();
        driver.findElement(By.xpath("//div[@id='page-wrapper']/div/div/div/form/div/button/i")).click();

        List<WebElement> elements = driver.findElements(By.id("sortedLessons"));
        boolean flag = false;
        Thread.sleep(3000);

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

    //Prebaruvanje po mesec, prv instruktor, maj 2020
    @Test
    public void test8SearchByMonthInTheFuture() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.id("instructorsTab")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("instructor0")).click();

        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[3]/table/tbody/tr/td/span[8]")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div[2]/table/tbody/tr/td/span[5]")).click();
        driver.findElement(By.xpath("//div[@id='page-wrapper']/div/div/div/form/div/button/i")).click();

        List<WebElement> elements = driver.findElements(By.id("sortedLessons"));
        boolean flag = false;
        Thread.sleep(2000);

        for (WebElement e:elements) {
            WebElement tmp = e;
            String date[] = tmp.findElement(By.id("lessonDate")).getText().split("/");
            String year = date[2].split(" ")[0];
            if(date[1].equals("May") && year.equals("2018")) {
                flag = true;
                break;
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
