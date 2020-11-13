package Sites;

import com.github.javafaker.Faker;
import com.github.javafaker.PhoneNumber;
import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.Ngoaihang88PageObject;
import pageUI.Ngoaihang88UI;

import java.io.IOException;
import java.util.Locale;

public class NgoaiHang88 extends AbstractTest {

    private WebDriver driver;
    private Ngoaihang88PageObject ngoaihang88Page;
    private String userName, password, email;
    private String sdt;
    private Faker faker = new Faker(new Locale("vi"));

    @Parameters({"browser", "url", "invocationCount", "sleepAfterTest"})
    @Test
    public void Test(String browserName, String url, int invocationCount, int sleepAfterTest) {
        for (int i = 1; i <= invocationCount; i++) {
            driver = getBrowserDriver(browserName);
            ngoaihang88Page = new Ngoaihang88PageObject(driver);

            //Install VPN and fake IP
            fakeIP();

            //Get IP address
            getMyIPAddress();

            userName = ngoaihang88Page.getLastNameRandom() + ngoaihang88Page.getRandomNumber();
            password = ngoaihang88Page.getLastNameRandom() + ngoaihang88Page.getRandomNumber();
            email = ngoaihang88Page.getFirstNameRandom() + ngoaihang88Page.getRandomNumber() + "@mailsac.com";
            sdt = "93421" + faker.number().randomNumber(4, true);

            log.info("NgoaiHang88 - Access site");
            driver.get("http://" + url);

            log.info("NgoaiHang88 - Input username");
            ngoaihang88Page.inputToUserName(userName);

            String currentURL = ngoaihang88Page.getCurrentPageURL();

            log.info("NgoaiHang88 - Input passsword");
            ngoaihang88Page.inputToPassword(password);

            log.info("NgoaiHang88 - Input email");
            ngoaihang88Page.inputToEmail(email);

            log.info("NgoaiHang88 - Input phone number");
            ngoaihang88Page.inputToPhoneNumber(sdt);

            log.info("NgoaiHang88 - Click to register");
            ngoaihang88Page.clickToRegisterButton();

            //If user name error message appears
            if (ngoaihang88Page.isElementPresentInDOM(Ngoaihang88UI.USER_NAME_ERROR_MSG)) {
                log.info("NgoaiHang88 - Solve username invalid");
                ngoaihang88Page.clickToClosePopup();
                userName = ngoaihang88Page.getLastNameRandom() + ngoaihang88Page.getRandomNumber();
                ngoaihang88Page.inputToUserName(userName);
                ngoaihang88Page.clickToRegisterButton();
            }

            //If phone number incorrect
            if (ngoaihang88Page.isElementPresentInDOM(Ngoaihang88UI.PHONE_ERROR_MSG)) {
                log.info("NgoaiHang88 - Solve phone number invalid");
                ngoaihang88Page.clickToClosePopup();
                ngoaihang88Page.inputToPhoneNumber("92324" + faker.number().randomNumber(4, true));
                ngoaihang88Page.clickToRegisterButton();
            }

            //If password is not 6-20 characters
            if (ngoaihang88Page.isElementPresentInDOM(Ngoaihang88UI.PASSWORD_ERROR_MSG)) {
                log.info("NgoaiHang88 - Solve password invalid");
                ngoaihang88Page.clickToClosePopup();
                password = ngoaihang88Page.getLastNameRandom() + ngoaihang88Page.getRandomNumber();
                ngoaihang88Page.inputToPassword(password);
                ngoaihang88Page.clickToRegisterButton();
            }

            boolean isSuccess = !ngoaihang88Page.getCurrentPageURL().equals(currentURL);
            if(isSuccess){
                Constants.REGISTERED = true;
                log.info("NgoaiHang88 - Write data to file");
                try {
                    System.out.println(userName);
                    ngoaihang88Page.appendDataToExcel(userName, password, ngoaihang88Page.getCurrentPageURL(), Constants.IP_ADDRESS);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Constants.REGISTERED = false;
            }

            verifyTrue(isSuccess);

            log.info("NgoaiHang88 - Close browser");
            closeBrowserAndDriver(driver);
        }
    }
}
