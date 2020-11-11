package Sites;

import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.Jbo064PageObject;
import pageObject.VuaKingPageObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Jbo064 extends AbstractTest {
    private WebDriver driver;
    private Jbo064PageObject jbo064Page;

    @BeforeClass
    public void beforeClass(){

    }


    @Parameters({"browser", "url", "invocationCount","sleepAfterTest"})
    @Test
    public void registerJbo(String browserName, String url, int invocationCount, int sleetAfterTest){

        for (int i = 1; i <= invocationCount; i++) {
            driver = getBrowserDriver(browserName);
            jbo064Page = new Jbo064PageObject(driver);

//            fakeIP();
//            getMyIPAddress();

            log.info("Access URL");
            driver.get("http://"+ url);

            log.info("Jbo064 - Close other tabs");
            jbo064Page.closeAllWindowsWithoutParent();

            String phoneNumber = jbo064Page.getPhoneNumberRandom();
            String userName = jbo064Page.getLastNameRandomForJbo064() + jbo064Page.getRandomNumber();
            String password = jbo064Page.getLastNameRandom() + jbo064Page.getRandomNumber();


            log.info("Jbo064 - Click Register button");
            jbo064Page.clickToRegisterButton();

            log.info("Jbo064 - Input PhoneNumber");
            jbo064Page.inputToPhoneNumber(phoneNumber);

            log.info("Jbo064 - Input user name");
            jbo064Page.inputToUserName(userName);

            log.info("Jbo064 - Input password and confirm password");
            jbo064Page.inputToPassword(password);
            jbo064Page.inputToConfirmPassword(password);

            log.info("Jbo064 - Click to radio button to confirm termOfUse");
            jbo064Page.clickToTermRadioButton();

            log.info("Jbo064 - Click to register");
            jbo064Page.clickToConfirmRegister();



            log.info("Jbo064 - Get current URL");
            String currentURL = jbo064Page.getCurrentURL();


//            log.info("Jbo064 - Check the registration successfully");
//            verifyTrue(vuakingPage.isLogoDisplayed());

            log.info("Jbo064 - Check registration and write data to csv");
            if(Constants.REGISTERED){
                jbo064Page.writeDataToCsv(userName, password, currentURL, Constants.IP_ADDRESS);
                Constants.ACCOUNTS_SUCCESS.add(i);
            }

            jbo064Page.sleepInSecond(sleetAfterTest);
            closeBrowserAndDriver(driver);
        }

    }


}
