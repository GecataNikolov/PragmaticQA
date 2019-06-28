package HW1_SeleniumBasics;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class BuildMyCarTests {
    private WebDriver driver;
    private String url = "http://pragmatic.bg/automation/lecture13/Config.html";


    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void multipleSelectColor(){
        driver.get(url);

        Actions action = new Actions(driver);

        WebElement redOption = driver.findElement(By.xpath("//option[@value='rd']"));
        WebElement silverOption = driver.findElement(By.xpath("//option[@value='sl']"));
        action.moveToElement(redOption)
                .click()
                .keyDown(Keys.CONTROL)
                .moveToElement(silverOption)
                .click()
                .keyUp(Keys.CONTROL)
                .perform();

        assertTrue(redOption.isSelected() && silverOption.isSelected());
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
