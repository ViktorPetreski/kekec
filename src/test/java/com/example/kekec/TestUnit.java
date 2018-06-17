package com.example.kekec;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUnit {

    public static WebDriver driver;
    public static String baseUrl;

    public static void init() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Viktor\\Documents\\fax\\skit\\selenium\\chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Beta\\Application\\chrome.exe");
        driver = new ChromeDriver(chromeOptions);
        baseUrl = "http://localhost:8080/allCandidates";
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Lodi\\chromedriver_win32\\chromedriver.exe");
//        chromeOptions.setBinary("C:\\Program Files (x86)\\Google\\Chrome Dev\\Application\\chrome.exe");
    }


    static String removeCandidate(int index) {

        driver.get("http://localhost:8080/allCandidates");
        driver
                .findElement(By.id("candidateInfoRow" + index))
                .findElement(By.id("updateCell"))
                .findElement(By.id("formDeleteButton"))
                .findElement(By.id("deleteButton"))
                .click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();

        alert.accept();

        return alertText;
    }

    static void addCandidateWithDrivingCategory(String category){
        driver.get(baseUrl);
        driver.findElement(By.linkText("Додади кандидат")).click();
        driver.findElement(By.id("ssn")).click();
        driver.findElement(By.id("ssn")).clear();
        driver.findElement(By.id("ssn")).sendKeys("2222");
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Lodi");
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Dodevska");
        driver.findElement(By.id("totalSum")).clear();
        driver.findElement(By.id("totalSum")).sendKeys("15000");
        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("3");
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("0121212");
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr[4]/td[3]")).click();
        driver.findElement(By.id("drivingCategory")).clear();
        driver.findElement(By.id("drivingCategory")).sendKeys(category);
        driver.findElement(By.id("numberOfLessons")).click();
        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("36");
        driver.findElement(By.name("addCandidateButton")).click();
    }


    static void addDrivingLessons(int index) throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow" + index) );

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

        Thread.sleep(1000);

        driver.findElement(By.name("newDrivingLessonButton")).click();
    }
}
