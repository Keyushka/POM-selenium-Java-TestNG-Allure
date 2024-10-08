package pages;

import library.ConfigFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ConfigFile configFile = new ConfigFile();
    private Logger logger = LogManager.getLogger(HomePage.class);

    private final By productHeader = By.xpath("//span[contains(text(),'Products')]");
    private final By shoppingCartButton = By.xpath("//a[@class='shopping_cart_link']");
    private final String item = "//div[@class='inventory_list']/div/div[2]/div/a/div[contains(text(),'ITEM_NAME')]/parent::a/parent::div/following-sibling::div/button";

    public HomePage(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(configFile.getWaitDuration()));
    }

    public void verifyHomepage() {
        logger.info("Verifying homepage...");
        try {
            wait.until(ExpectedConditions.numberOfElementsToBe(productHeader, 1));
        } catch (Exception e) {
            logger.error(e);
        }
        Assert.assertTrue(driver.findElements(productHeader).size() > 0);
        logger.info("Successfully transferred to Homepage");
    }

    public void addItemToCart(String itemName) {
        logger.info("Adding item {} to cart", itemName);
        try {
            driver.findElement(By.xpath(item.replace("ITEM_NAME", itemName))).click();
        } catch (Exception e) {
            logger.error(e);
        }

    }

    public void clickShoppingCart() {
        logger.info("Clicking cart");
        try {
            driver.findElement(shoppingCartButton).click();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
