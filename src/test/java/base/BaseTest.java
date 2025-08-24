public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void initializeDriver() {
        try {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            
            // DO NOT use --user-data-dir argument at all
            // This prevents the directory conflict
            
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
