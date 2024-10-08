package tests.ui;

import library.Browser;
import library.ConfigFile;
import library.DataRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginTest {
    WebDriver driver;
    XSSFSheet sheet;
    DataRepository dr;
    ConfigFile configFile = new ConfigFile();
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    LoginPage lp;
    HomePage hp;

    @BeforeTest
    public void browserLaunch() throws Exception {
        // Налаштування Log4j 2
        Configurator.initialize(null, "log4j2.xml");
        dr = new DataRepository(configFile.getDataRepositoryPath(), "LoginTestDataSheet");
        driver = Browser.StartBrowser(configFile.getBrowserType(), configFile.getURL());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(configFile.getWaitDuration()));
        lp = new LoginPage(driver, logger);
        hp = new HomePage(driver, logger);
        logger.info("---Starting LoginTest---");
    }

    // Login to Site
    @Test(priority = 1)
    public void Login() {
        lp.loginToSite(dr.getStringCellValue("Username"), dr.getStringCellValue("Password"));
        hp.verifyHomepage();

    }

    @AfterTest
    public void closeBrowser() {
        logger.info("---Ending LoginTest---");
        driver.quit();
    }
}
