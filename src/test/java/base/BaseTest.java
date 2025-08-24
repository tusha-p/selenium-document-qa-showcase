package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        initializeDriver();
    }
    
   /* protected void initializeDriver() {
        try {
            // Clean up any Firefox processes
            cleanUpFirefoxProcesses();
            
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
            options.addArguments("--disable-gpu");
            
            // Firefox doesn't have user data directory issues like Chrome
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
            
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize FirefoxDriver: " + e.getMessage(), e);
        }
    }*/

    protected void initializeDriver() {
    try {
       try {
  try {
    System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
    
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--headless=new");
    options.addArguments("--disable-gpu");
    options.addArguments("--window-size=1920,1080");
    
    // Use /tmp directory which has write permissions
    String userDataDir = "/tmp/chrome-user-data-" + java.util.UUID.randomUUID();
    options.addArguments("--user-data-dir=" + userDataDir);
    
    driver = new ChromeDriver(options);
    
} catch (Exception e) {
    throw new RuntimeException("Failed to initialize ChromeDriver", e);
}
}
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignore quit errors
            }
        }
        cleanUpFirefoxProcesses();
    }
    
    private void cleanUpFirefoxProcesses() {
        try {
            String[] commands = {
                "pkill -9 -f firefox",
                "pkill -9 -f geckodriver",
                "killall -9 firefox",
                "killall -9 geckodriver"
            };
            
            for (String command : commands) {
                try {
                    Runtime.getRuntime().exec(command);
                } catch (Exception e) {
                    // Ignore command failures
                }
            }
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Firefox cleanup warning: " + e.getMessage());
        }
    }
}
