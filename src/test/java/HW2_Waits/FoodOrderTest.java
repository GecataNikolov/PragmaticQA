package HW2_Waits;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class FoodOrderTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
//        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
//        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.takeaway.com/bg/");
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void findRestaurantTest() {
        // Find input field for location, clear the information and typing in the field
        WebElement locationInputField = driver.findElement(By.cssSelector("input[type='text']#imysearchstring"));
        locationInputField.click();
        locationInputField.clear();
        locationInputField.sendKeys("Pragmatic IT");

        // Wait until the location that is searching for is visible
        WebElement location = driver.findElement(By.xpath("//a[@class='item selected']/span"));
        wait.until(ExpectedConditions.visibilityOf(location));

        // Assert it is the Pragmatic IT Center and click on it (make the search).
        assertTrue(location.getText().contains("Pragmatic IT Center"));
        location.click();

        // Input in search field and click on search icon.
        WebElement restaurantSearchField = driver.findElement(By.cssSelector("input#irestaurantsearchstring-middle"));

        // Using clear(); so the execution can slow down. Fix for Firefox
        restaurantSearchField.click();
        restaurantSearchField.sendKeys("Sofiiska");


        // Get all the results in List<> and look for result which contains "Banitsa". Assert it is the 'Sofiska Banitsa' restaurant.
        List<WebElement> searchResult = driver.findElements(By.xpath("//div[@id='irestaurantlist']//div[@class='restaurant'][not(contains(@id, 'SingleRestaurantTemplateIdentifier'))]//a[@class='restaurantname']"));
        for (WebElement results : searchResult) {
            if (results.getText().contains("Banitsa")) {
                assertTrue(results.getText().contains("Sofiyska Banitsa"));
                break;
            }
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
