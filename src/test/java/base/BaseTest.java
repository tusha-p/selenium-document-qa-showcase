import java.io.File;
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;
    
    protected void initializeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        // Add unique user data directory
        String uniqueUserDataDir = "/tmp/chrome-profile-" + UUID.randomUUID().toString();
        options.addArguments("--user-data-dir=" + uniqueUserDataDir);
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
