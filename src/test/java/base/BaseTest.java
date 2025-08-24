public class BaseTest {
    protected WebDriver driver;

   @BeforeEach
public void initializeDriver() {
    try {
         Runtime.getRuntime().exec("pkill -f chrome");
    Runtime.getRuntime().exec("pkill -f chromedriver");
    Thread.sleep(1000); // Wait for processes to termina
        // DISABLE Selenium Manager to avoid conflicts
        System.setProperty("webdriver.selenium_manager", "false");
        
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        
        // Add unique user data directory
        String userDataDir = "/tmp/chrome-data-" + java.util.UUID.randomUUID();
        options.addArguments("--user-data-dir=" + userDataDir);
        
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
