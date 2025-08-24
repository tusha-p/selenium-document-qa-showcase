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
    
    // Create a unique temporary directory for each test
    String uniqueUserDataDir = System.getProperty("java.io.tmpdir") + 
                              "/chrome_profile_" + UUID.randomUUID();
    options.addArguments("--user-data-dir=" + uniqueUserDataDir);
    
    // Add other necessary options
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--headless"); // if running in headless mode
    
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver(options);
    }
    AfterEach
public void tearDown() {
    if (driver != null) {
        driver.quit();
        
        // Optional: Clean up the temporary directory
        try {
            File userDataDir = new File(options.getArguments()
                .stream()
                .filter(arg -> arg.startsWith("--user-data-dir="))
                .findFirst()
                .orElse("")
                .replace("--user-data-dir=", ""));
            
            if (userDataDir.exists()) {
                deleteDirectory(userDataDir);
            }
        } catch (Exception e) {
            // Handle cleanup exception if needed
        }
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
