public class BaseTest {
    protected WebDriver driver;

@BeforeEach
public void initializeDriver() {
    try {
        // 1. COMPLETELY DISABLE SELENIUM MANAGER - This is the key!
        System.setProperty("webdriver.selenium_manager", "false");
        
        // 2. Use explicit ChromeDriver path (make sure it exists)
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        
        // 3. Kill any existing Chrome processes
        Runtime.getRuntime().exec("pkill -f chrome");
        Runtime.getRuntime().exec("pkill -f chromedriver");
        Thread.sleep(2000); // Wait 2 seconds for cleanup
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        // 4. Use a unique user data directory with proper permissions
        String userDataDir = "/tmp/chrome-test-" + System.currentTimeMillis();
        options.addArguments("--user-data-dir=" + userDataDir);
        
        // 5. Create the directory first to avoid permission issues
        new File(userDataDir).mkdirs();
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
    } catch (Exception e) {
        throw new RuntimeException("Failed to initialize ChromeDriver", e);
    }
}
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
