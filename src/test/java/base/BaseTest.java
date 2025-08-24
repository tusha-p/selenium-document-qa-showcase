package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    
    protected void initializeDriver() {
        try {
            // Force kill all Chrome processes first
            forceKillChromeProcesses();
            
            ChromeOptions options = new ChromeOptions();
            
            // EXPLICITLY set user data directory to a non-persistent location
            options.addArguments("--user-data-dir=/tmp/chrome-temp-" + System.currentTimeMillis());
            
            // Alternative: Use incognito mode which ignores user data dir
            // options.addArguments("--incognito");
            
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--headless=new"); // Use the new headless mode
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            
            // Add these to prevent any persistence issues
            options.addArguments("--disable-extensions");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--no-first-run");
            options.addArguments("--disable-background-networking");
            options.addArguments("--disable-sync");
            
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            
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
        forceKillChromeProcesses();
    }
    
    private void forceKillChromeProcesses() {
        try {
            // More aggressive process killing for Linux
            Runtime.getRuntime().exec("pkill -9 -f chrome");
            Runtime.getRuntime().exec("pkill -9 -f chromedriver");
            Runtime.getRuntime().exec("pkill -9 -f google-chrome");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Process cleanup warning: " + e.getMessage());
        }
    }
}
