package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        initializeDriver();
    }
    
    protected void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        // Use a different approach - set user data directory via preferences
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", System.getProperty("java.io.tmpdir"));
        options.setExperimentalOption("prefs", prefs);
        
        // OR try using incognito mode instead
        options.addArguments("--incognito");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignore any exceptions during quit
            }
        }
        
        // Force kill any remaining Chrome processes
        try {
            Runtime.getRuntime().exec("pkill -f chrome");
            Runtime.getRuntime().exec("pkill -f chromedriver");
        } catch (Exception e) {
            // Ignore exceptions
        }
    }
}
