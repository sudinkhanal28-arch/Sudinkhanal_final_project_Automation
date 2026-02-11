package base;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    // ✅ Backward-compatible fields (so existing tests using `driver` still compile)
    protected WebDriver driver;
    protected WebDriverWait wait;

    // ✅ Advanced: still keep ThreadLocal for future-safe parallelization
    private static final ThreadLocal<WebDriver> DRIVER_TL = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> WAIT_TL = new ThreadLocal<>();

    protected String baseUrl;
    protected long timeoutSeconds;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        loadConfig();

        ChromeOptions options = new ChromeOptions();

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }
        options.addArguments("--start-maximized");

        WebDriver localDriver = new ChromeDriver(options);
        DRIVER_TL.set(localDriver);

        String timeoutRaw = System.getProperty("timeoutSeconds", String.valueOf(timeoutSeconds));
if (timeoutRaw == null || timeoutRaw.trim().isEmpty()) {
    timeoutSeconds = 8; // default fallback
} else {
    timeoutSeconds = Long.parseLong(timeoutRaw.trim());
}
        WebDriverWait localWait = new WebDriverWait(localDriver, Duration.ofSeconds(timeoutSeconds));
        WAIT_TL.set(localWait);

        // ✅ Set instance fields too (compatibility)
        this.driver = localDriver;
        this.wait = localWait;

        localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        localDriver.get(baseUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver localDriver = DRIVER_TL.get();
        if (localDriver != null) {
            localDriver.quit();
        }

        DRIVER_TL.remove();
        WAIT_TL.remove();

        // also clear instance refs
        driver = null;
        wait = null;
    }

    // Optional getters (use later when we refactor tests gradually)
    protected WebDriver getDriver() {
        return DRIVER_TL.get();
    }

    protected WebDriverWait getWait() {
        return WAIT_TL.get();
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream is = BaseTest.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }

        baseUrl = System.getProperty("baseUrl", props.getProperty("baseUrl", "https://www.saucedemo.com/"));
        timeoutSeconds = Long.parseLong(props.getProperty("timeoutSeconds", "8"));
    }
}