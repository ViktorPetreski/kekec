package com.example.kekec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersInDebtTest {

    public static WebDriver driver;
    public static String baseUrl;


    @BeforeClass
    public static void setUp() {
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    //dodavanje na kandidat so neplatena rata
    @Test
    public void test1AddCandidateInDebt() throws InterruptedException {
        driver.get("http://localhost:8080/addCandidate");
        driver.findElement(By.id("ssn")).click();
        driver.findElement(By.id("ssn")).clear();
        driver.findElement(By.id("ssn")).sendKeys("1111");
        driver.findElement(By.id("firstName")).click();
        driver.findElement(By.id("firstName")).clear();
        driver.findElement(By.id("firstName")).sendKeys("Viktor");
        driver.findElement(By.id("lastName")).clear();
        driver.findElement(By.id("lastName")).sendKeys("Petreski");
        driver.findElement(By.id("totalSum")).click();
        driver.findElement(By.id("totalSum")).clear();
        driver.findElement(By.id("totalSum")).sendKeys("12000");
        driver.findElement(By.id("numberOfInstallments")).click();
        driver.findElement(By.id("numberOfInstallments")).clear();
        driver.findElement(By.id("numberOfInstallments")).sendKeys("2");
        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).clear();
        driver.findElement(By.id("phone")).sendKeys("71369555");
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/thead/tr/th/span")).click();
        driver.findElement(By.xpath("//div[@id='datetimepicker1']/div/ul/li/div/div/table/tbody/tr/td[4]")).click();
        driver.findElement(By.id("drivingCategory")).click();
        driver.findElement(By.id("drivingCategory")).clear();
        driver.findElement(By.id("drivingCategory")).sendKeys("V");
        driver.findElement(By.id("numberOfLessons")).click();
        driver.findElement(By.id("numberOfLessons")).clear();
        driver.findElement(By.id("numberOfLessons")).sendKeys("30");
        driver.findElement(By.name("addCandidateButton")).click();


        driver.findElement(By.linkText("Кандидати кои немаат платено рата")).click();

        WebElement candidate = driver.findElement(By.id("candidateInfoRowInDebt0"));

        assertNotNull(candidate);

    }

    //proverka dali navistina e pominat rokot za plakjanje na rata
    @Test
    public void test2InstallmentDate() throws InterruptedException {
        driver.get(baseUrl);

        driver.findElement(By.linkText("Кандидати кои немаат платено рата")).click();

        WebElement candidate = driver.findElement(By.id("candidateInfoRowInDebt0"));

        WebElement installmentTable = candidate.findElement(By.id("installmentTableInDebt"))
                .findElement(By.id("someTableInDebt"));

        List<WebElement> installments = installmentTable.findElements(By.tagName("td"));

        for (int i = 0; i < installments.size(); i++) {
//            String buttonId = "inDebtInstallmentPayButton" + i;
            WebElement firstNotPaid = installments.get(i).findElement(By.id("inDebtInstallmentPayButton" + i));
            Thread.sleep(500);
            boolean tmp = firstNotPaid.isDisplayed();
            Thread.sleep(500);
            if (tmp) {
                String dueDateString = installments.get(i).findElement(By.id("installmentDateInDebt" + i)).getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                LocalDate dueDate = LocalDate.parse(dueDateString.substring(dueDateString.indexOf(":") + 1, dueDateString.length()).trim(), formatter);
                assertTrue(dueDate.isBefore(LocalDate.now()));
                break;
            }
        }
    }


    //ako nema dolg no ima seuste neplateni rati
    @Test
    public void test3DebtPaid() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Кандидати кои немаат платено рата")).click();
        Thread.sleep(500);
        WebElement candidate = driver.findElement(By.id("candidateInfoRowInDebt0"));
        Thread.sleep(500);
        candidate.findElement(By.id("inDebtInstallmentPrice0")).click();
        candidate.findElement(By.id("inDebtInstallmentPrice0")).clear();
        candidate.findElement(By.id("inDebtInstallmentPrice0")).sendKeys("12000");
        candidate.findElement(By.id("inDebtInstallmentPayButton0")).click();
        Thread.sleep(500);
//        String debt = driver.findElement(By.id("candidateInfoRowInDebt0")).findElement(By.id("InDebtAllUsers")).getText();
        driver.get(baseUrl);
        String debt = driver.findElement(By.id("candidateInfoRow1")).findElement(By.id("InDebtAllUsers")).getText();

        assertEquals("0.0", debt);
    }

    //ako nema kandidati so neplateni rati
    @Test
    public void test4EmptyInDebtTable(){
        driver.get(baseUrl);
        driver.findElement(By.linkText("Кандидати кои немаат платено рата")).click();
        assertThrows(org.openqa.selenium.NoSuchElementException.class, () ->  driver.findElement(By.id("candidateInfoRowInDebt0")));
    }

    @Test
    public void test5InstallmentNotPaidButNotInDebt() throws InterruptedException {
        driver.get(baseUrl);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow1"));

        WebElement installmentTable = candidate.findElement(By.id("installmentTable"))
                .findElement(By.id("someTableId"));

        List<WebElement> installments = installmentTable.findElements(By.tagName("td"));

        for (int i = 0; i < installments.size(); i++) {
//            String buttonId = "inDebtInstallmentPayButton" + i;
            WebElement tmp;
            boolean displayed = false;

            //najdi ja prvata neplatena rata -- ne znam kao drukse a da ne koristam fiksni values da ja najdam ..

            try{
                tmp = installments.get(i).findElement(By.id("installmentPayButton" + i));
                Thread.sleep(500);
                displayed = tmp.isDisplayed();
                Thread.sleep(500);
            }catch (NoSuchElementException e){
                e.getMessage();
            }

            if(displayed) {
                String dueDateString = installments.get(i).findElement(By.id("installmentDate" + i)).getText();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
                LocalDate dueDate = LocalDate.parse(dueDateString.substring(dueDateString.indexOf(":") + 1, dueDateString.length()).trim(), formatter);
                assertTrue(dueDate.isBefore(LocalDate.now()));
                break;
            }
        }
    }

}
