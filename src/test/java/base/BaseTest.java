package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;
    
    @BeforeClass
    public void setupClass() {
        // Optional: You can add WebDriverManager setup here if using it
        // WebDriverManager.chromedriver().setup();
    }
    
    protected void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        // Add unique user data directory to prevent conflicts
        String uniqueUserDataDir = "/tmp/chrome-profile-" + UUID.randomUUID().toString();
        options.addArguments("--user-data-dir=" + uniqueUserDataDir);
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    // Optional: If you need to get the driver instance
    public WebDriver getDriver() {
        return driver;
    }
    
    // Optional: If you need to navigate to a URL
    public void navigateTo(String url) {
        driver.get(url);
    }
}
