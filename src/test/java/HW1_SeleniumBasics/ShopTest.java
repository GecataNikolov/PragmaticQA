package HW1_SeleniumBasics;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ShopTest {
   private WebDriver driver;
   private String backEndURL= "http://shop.pragmatic.bg/admin";
   private String frontEndURL= "http://shop.pragmatic.bg/";


    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    /**
     * Logs user in the back end if the params are correct!
     * @param uname- Username credentials
     * @param password- Password credentials
     */
    private void logIn(String url, String uname, String password){
        driver.get(url);
        WebElement usernameField = driver.findElement(By.id("input-username"));
        WebElement passwordField = driver.findElement(By.id("input-password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button.btn-primary[type='submit']"));
        usernameField.sendKeys(uname);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    /**
     * Test that asserts the error message when trying to login with wrong credentials
     */
    @Test
    public void loginBackEndInvalidCredentials() {
       logIn(backEndURL,"admin", "greshnaParola");
       WebElement invalidLoginErrorMessage = driver.findElement(By.cssSelector("div.alert.alert-dismissible"));

        assertTrue(
                invalidLoginErrorMessage.getText().contains("No match for Username and/or Password."),
                "There is a problem with the message");

        assertTrue(invalidLoginErrorMessage.isDisplayed());

    }

    /**
     * Test that assert the user is logged in when using valid credentials
     */
    @Test
    public void successfulBackEndLogin(){
    logIn(backEndURL,"admin", "parola123!");

        WebElement logOutButton = driver.findElement(By.cssSelector("span.hidden-xs"));
        assertTrue(logOutButton.isDisplayed());
    }
    @Test
    public void orderTypesDropDown(){
        logIn(backEndURL,"admin", "parola123!");

        WebElement sideMenuSalesSection = driver.findElement(By.id("menu-sale"));
        sideMenuSalesSection.click();
        WebElement sideMenuOrdersMidSection = driver.findElement(By.xpath("//ul[@id='collapse26']/li[1]/a"));
        sideMenuOrdersMidSection.click();
        Select orderStatusDropDown = new Select(driver.findElement(By.id("input-order-status")));


        List<String> expectedOptions = Arrays.asList
                (new String[]{"","Missing Orders", "Canceled", "Canceled Reversal", "Chargeback",
                "Complete", "Denied", "Expired", "Failed", "Pending", "Processed", "Processing",
                "Refunded", "Reversed", "Shipped", "Voided"});
        List<String> actualOptions = new ArrayList();

        List<WebElement> ordersOptions = orderStatusDropDown.getOptions();

        for (WebElement options : ordersOptions) {
            actualOptions.add(options.getText());
        }

        assertEquals(actualOptions.toArray(), expectedOptions.toArray());


    }

    @AfterTest
    public void tearDown() { driver.quit();}

}

