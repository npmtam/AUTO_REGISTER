package Sites;

import commons.AbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.B29QCPageObject;

public class B29QCUI extends AbstractTest {
    private WebDriver driver;
    private B29QCPageObject b29cPage;
    private String userName, password, fullName;

    @Parameters({"browser", "url", "invocationCount", "sleepAfterTest"})
    @Test
    public void registerB29QC(String browserName, String url, int invocationCount, int sleepAfterTest){
        for(int i=1; i<=invocationCount; i++){
            driver = getBrowserDriver(browserName);
            b29cPage = new B29QCPageObject(driver);

            fakeIP();
            getMyIPAddress();

            userName = b29cPage.getLastNameRandom() + b29cPage.getFirstNameRandom() + b29cPage.getRandomNumber();
            password = b29cPage.getFirstNameRandom() + b29cPage.getRandomNumber();
            fullName = b29cPage.getLastNameRandom() + b29cPage.getFirstNameRandom() + b29cPage.getRandomNumber();

            log.info("Access URL");
            driver.get("http://" + url);

            log.info("B29QC - ");
        }
    }
}
