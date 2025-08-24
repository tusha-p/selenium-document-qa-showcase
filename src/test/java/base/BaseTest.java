package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;
    protected ChromeOptions options;
    protected String userDataDirPath;
    
    @BeforeMethod
    public void setUp() {
        initializeDriver();
    }
    
    protected void initializeDriver() {
        try {
            // Kill any existing Chrome processes first
            killChromeProcesses();
            
            options = new ChromeOptions();
            
            // Create a unique temporary directory for each test
            userDataDirPath = System.getProperty("java.io.tmpdir") + 
                              "chrome_profile_" + UUID.randomUUID();
            
            // Ensure the directory exists
            File userDataDir = new File(userDataDirPath);
            if (!userDataDir.exists()) {
                userDataDir.mkdirs();
            }
            
            options.addArguments("--user-data-dir=" + userDataDirPath);
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            
            // Add these to prevent sharing issues
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--disable-extensions");
            
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            
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
        
        // Clean up the temporary directory
        try {
            if (userDataDirPath != null) {
                File userDataDir = new File(userDataDirPath);
                if (userDataDir.exists()) {
                    deleteDirectory(userDataDir);
                }
            }
        } catch (Exception e) {
            System.out.println("Warning: Could not clean up user data directory: " + e.getMessage());
        }
        
        // Force kill any remaining Chrome processes
        try {
            killChromeProcesses();
        } catch (Exception e) {
            // Ignore exceptions
        }
    }
    
    // Helper method to delete directory recursively
    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
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
                // Linux/Mac
                Runtime.getRuntime().exec("pkill -f chrome");
                Runtime.getRuntime().exec("pkill -f chromedriver");
            }
            // Wait a bit for processes to terminate
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Warning: Could not kill Chrome processes: " + e.getMessage());
        }
    }
}
