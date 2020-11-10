package commons;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AbstractTest {
    private WebDriver driver;
    String rootFolder = System.getProperty("user.dir");
    AbstractPage abstractPage;

    public final Log log;

    public AbstractTest() {
        log = LogFactory.getLog(getClass());
    }

    public WebDriver getBrowserDriver(String browserName) {
        switch (browserName) {
            case "chrome":
                File vpn = new File(rootFolder + "\\src\\main\\resources\\touchVPN.crx");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
//                chromeOptions.addArguments("--incognito");
                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                chromeOptions.addExtensions(vpn);
                chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                driver = new ChromeDriver(chromeOptions);
                break;
            case "chrome_headless":
                File vpn_headless = new File(rootFolder + "\\src\\main\\resources\\touchVPN.crx");
                WebDriverManager.chromedriver().setup();
                ChromeOptions options2 = new ChromeOptions();
                options2.addExtensions(vpn_headless);
                options2.setExperimentalOption("useAutomationExtension", false);
                options2.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
//                options2.addArguments("--incognito");
                options2.setHeadless(true);
                driver = new ChromeDriver(options2);
                break;
            case "chrome_no_vpn":
                WebDriverManager.chromedriver().setup();
                ChromeOptions option3 = new ChromeOptions();
                option3.addArguments("--disable-notifications");
                option3.setExperimentalOption("useAutomationExtension", false);
                option3.setExperimentalOption("useAutomationExtension", false);
                option3.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
                driver = new ChromeDriver(option3);
                break;
            default:
                System.out.println("Please input your browser name!");
                break;
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        abstractPage = new AbstractPage(driver);
        return driver;
    }

    public void closeBrowserAndDriver(WebDriver driver) {
        try {
            // get OS name and convert to lower case
            String osName = System.getProperty("os.name").toLowerCase();
            log.info("OS Name = " + osName);

            // Declare command line to execute
            String cmd = "";
            if (driver != null) {
                driver.quit();
            }

            if (driver.toString().toLowerCase().contains("chrome")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill chromedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                }
            } else if (driver.toString().toLowerCase().contains("internetexplorer")) {
                if (osName.toLowerCase().contains("window")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
                }
            } else if (driver.toString().toLowerCase().contains("firefox")) {
                if (osName.toLowerCase().contains("mac")) {
                    cmd = "pkill chromedriver";
                } else if (osName.toLowerCase().contains("windows")) {
                    cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
                }
            }

            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

            log.info("----------------QUIT BROWSER SUCCESS-----------------");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public boolean checkTrue(boolean condition) {
        boolean pass = true;
        try {
            if (condition == true) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertTrue(condition);
        } catch (Throwable e) {
            pass = false;

            // Add lỗi vào ReportNG
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
//		System.out.println("Status = " + pass);
        return pass;
    }

    public boolean verifyTrue(boolean condition) {
        return checkTrue(condition);
    }

    private boolean checkFailed(boolean condition) {
        boolean pass = true;
        try {
            if (condition == false) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertFalse(condition);
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyFalse(boolean condition) {
        return checkFailed(condition);
    }

    private boolean checkEquals(Object actual, Object expected) {
        boolean pass = true;
        boolean status;
        try {
            if (actual instanceof String && expected instanceof String) {
                actual = actual.toString().trim();
                log.info("Actual = " + actual);
                expected = expected.toString().trim();
                log.info("Expected = " + expected);
                status = (actual.equals(expected));
            } else {
                status = (actual == expected);
            }

            if (status) {
                log.info(" -------------------------- PASSED -------------------------- ");
            } else {
                log.info(" -------------------------- FAILED -------------------------- ");
            }
            Assert.assertEquals(actual, expected, "Value is not matching!");
        } catch (Throwable e) {
            pass = false;
            VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
            Reporter.getCurrentTestResult().setThrowable(e);
        }
        return pass;
    }

    protected boolean verifyEquals(Object actual, Object expected) {
        return checkEquals(actual, expected);
    }

    public void fakeIP() {
        abstractPage.sleepInSecond(2);
        String extension_Protocol = "chrome-extension";
        String extension_ID = "bihmplhobchoageeokmgbdihknkjbknd";
        String indexPage = extension_Protocol + "://" + extension_ID + "/panel/index.html";

        log.info("Access VPN control page");
        driver.get(indexPage);
        abstractPage.switchToWindowsByTitle("Panel");
        log.info("Check if VPN did not activated");
        if (!driver.getTitle().equals("Panel")) {
            abstractPage.switchToWindowsByTitle("Panel");
        }
        log.info("Connect VPN");
        driver.findElement(By.id("ConnectionButton")).click();
    }

    public void getMyIPAddress(){
        log.info("Get my IP address");
        driver.get("https://whatismyipaddress.com/");

        abstractPage.switchToWindowsByTitle("What Is My IP Address - See Your Public Address - IPv4 & IPv6");
        if (!driver.getTitle().equals("What Is My IP Address - See Your Public Address - IPv4 & IPv6")) {
            abstractPage.switchToWindowsByTitle("What Is My IP Address - See Your Public Address - IPv4 & IPv6");
        }
        String ipTitle = driver.findElement(By.xpath("//div[@id='ipv4']")).getText();
        if (ipTitle.equals("Not detected")){
            log.info("Can not detect IP");
            //Reload page
            driver.navigate().refresh();
        }
        String ipv4 = driver.findElement(By.xpath("//div[@id='ipv4']/a")).getText();
        String city = driver.findElement(By.xpath("//th[text()='City:']/following-sibling::td")).getText();
        System.out.println(ipv4);

        Constants.IP_ADDRESS = ipv4;
        System.out.println("City: " + city);
    }
}
