import com.google.common.io.Resources;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class homePageTest {

    static WebDriver driver;
    static Wait wait;
    private static final By searchFieldLocator = By.name("SearchInHeaderfield");
    private static final By bolanIcon = By.className("shb-cms-hero__on-page-navigation-large-item-image-container");
    private static final String startPage = "https://www.handelsbanken.se/";

    @BeforeClass
    public static void beforeClass() {

        URL path;

        if (System.getProperty("os.name").contentEquals("Mac OS X")) path = Resources.getResource("chromedriver");
        else path = Resources.getResource("chromedriver.exe");

        System.setProperty("webdriver.chrome.driver", path.toString().replace("file:", ""));
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);


    }


    @Disabled("Funkar inte just nu med sökfältet, knepigt element att interagera med")
    @Test
    public void testSearchField() throws InterruptedException {

        driver.get(startPage);

        wait.until(elementToBeClickable(searchFieldLocator));

        WebElement searchField = driver.findElement(searchFieldLocator);

        searchField.click();

        String inputText = "bolån";
        String js = "arguments[0].setAttribute('value','" + inputText + "')";
        ((JavascriptExecutor) driver).executeScript(js, searchField);
        searchField.sendKeys("bolån");
        searchField.submit();


        Thread.sleep(2000);
        assertThat(driver.getCurrentUrl(), is(containsString("sok")));

    }


    @Test
    public void testBolanFlow() {
        driver.get(startPage);

        wait.until(presenceOfElementLocated(bolanIcon));

        WebElement bolanButton = driver.findElement(bolanIcon);

        bolanButton.click();

        assertThat(driver.getCurrentUrl(), is(containsString("bolan")));
    }


}
