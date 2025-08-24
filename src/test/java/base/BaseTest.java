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
            // Kill any existing Chrome processes first
            killChromeProcesses();
            
            ChromeOptions options = new ChromeOptions();
            
            // DO NOT use --user-data-dir argument
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            
            // Add these to ensure clean sessions
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-plugins");
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito"); // Use incognito mode instead of user-data-dir
            
            // Set language and other preferences
            options.addArguments("--lang=en-US");
            options.addArguments("--start-maximized");
            
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            
            // Set implicit wait
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize ChromeDriver", e);
        }
    }
    
    @AfterMethod
    public void tearDown() {
        // Quit driver first
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Ignore any exceptions during quit
            }
        }
        
        // Force kill any remaining Chrome processes
        try {
            killChromeProcesses();
        } catch (Exception e) {
            // Ignore exceptions
        }
    }
    
    // Method to kill Chrome processes
    private void killChromeProcesses() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("win")) {
                // Windows
                Runtime.getRuntime().exec("taskkill /f /im chrome.exe");
                Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
            } else {
                // Linux/Mac (Codespaces is Linux-based)
                Runtime.getRuntime().exec("pkill -9 -f chrome");
                Runtime.getRuntime().exec("pkill -9 -f chromedriver");
            }
            // Wait a bit for processes to terminate
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Warning: Could not kill Chrome processes: " + e.getMessage());
        }
    }
}
