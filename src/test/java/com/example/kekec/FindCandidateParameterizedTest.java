package com.example.kekec;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(Parameterized.class)
public class FindCandidateParameterizedTest {

    public static WebDriver driver;
    public static String baseUrl;
    private String queryString;
    private boolean output;

    @Before
    public void setUp() throws Exception {
        TestUnit.init();
        driver = TestUnit.driver;
        baseUrl = TestUnit.baseUrl;
    }

    public FindCandidateParameterizedTest(String queryString, boolean output){
        super();
        this.queryString = queryString;
        this.output = output;
    }

    //Moze da se prebaruva samo spored ime, prezime i telefon
    @Parameterized.Parameters
    public static Collection<Object[]> queries() {
        return Arrays.asList(new Object[][]{
                {"Lodi", true},   //first name
                {"Dodevska", true}, //last name
                {"Lodi Dodevska", true}, // full name
                {"0121212", true} //phone
        });
    }

    //Prebaruvanje na kandidati, so proverka samo na prviot rezultat
    @Test
    public void testQuery() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(2000);
        WebElement candidate = driver.findElement(By.id("candidateInfoRow0"));
        String name = candidate.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
        String phone = candidate.findElement(By.id("phoneCell")).getText();

        boolean flag = name.split(" ")[0].equalsIgnoreCase(queryString) ||
                name.split(" ")[1].equalsIgnoreCase(queryString)
                || name.equalsIgnoreCase(queryString) || phone.equalsIgnoreCase(queryString);

        assertEquals(output,flag);
        driver.quit();
    }

    //Prebaruvanje na kandidati, so proverka na site rezultati
    @Test
    public void testQuery2() throws InterruptedException {
        driver.get(baseUrl);
        driver.findElement(By.name("query")).click();
        driver.findElement(By.name("query")).clear();
        driver.findElement(By.name("query")).sendKeys(queryString);
        driver.findElement(By.id("searchButton")).click();

        Thread.sleep(4000);
        List<WebElement> rows = driver.findElements(By.xpath(".//*[@id='allUsers']/table/tbody/tr/td[1]"));
        int rowSize = rows.size(); //elements + header

        List<WebElement> elements = driver.findElements(By.xpath("//div[@id='allUsers']/table/tbody/tr"));
        boolean flag = true;
        for (int i = 1; i < rowSize + 1; i++) {
            WebElement tmp = elements.get(i);
            String name = tmp.findElement(By.id("nameCell")).getText().replaceAll("\\r\\n|\\r|\\n", " ");
            String phone = tmp.findElement(By.id("phoneCell")).getText().trim();
            flag = flag && (name.split(" ")[0].equalsIgnoreCase(queryString) ||
                    name.split(" ")[1].equalsIgnoreCase(queryString) ||
                    name.equalsIgnoreCase(queryString) ||
                    phone.equalsIgnoreCase(queryString));
        }

        assertTrue(flag);
        driver.quit();
    }





}
