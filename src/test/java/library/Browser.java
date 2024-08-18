package library;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Browser {
    private static WebDriver driver;
    private static final ConfigFile configFile = new ConfigFile();

    public static WebDriver StartBrowser(String browserName, String url) {
        // If the browser is Firefox
        if (browserName.equalsIgnoreCase("Firefox")) {
            // Use WebDriverManager to set up the driver
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        // If the browser is Chrome
        else if (browserName.equalsIgnoreCase("Chrome")) {
            // Use WebDriverManager to set up the driver
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--window-size=1440,768", "--disable-gpu");
            driver = new ChromeDriver(options);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }

        // If the browser is IE
        else if (browserName.equalsIgnoreCase("IE")) {
            // Use WebDriverManager to set up the driver
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }

        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }
}
