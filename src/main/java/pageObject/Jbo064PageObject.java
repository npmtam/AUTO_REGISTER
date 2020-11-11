package pageObject;

import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import commons.AbstractPage;
import commons.Constants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageUI.Jbo064UI;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Jbo064PageObject extends AbstractPage {
    private WebDriver driver;
    private String backupUsername;

    public Jbo064PageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
        backupUsername = getLastNameRandom() + getRandomNumber();
    }


    public void clickToRegisterButton() {
        waitToElementClickable(Jbo064UI.REGISTER_BUTTON);
        clickToElementByJS(Jbo064UI.REGISTER_BUTTON);

    }


    public void inputToPhoneNumber(String phoneNumber) {
        waitToElementVisible(Jbo064UI.PHONE_TEXTBOX);
        driver.findElement(By.xpath(Jbo064UI.PHONE_TEXTBOX)).sendKeys(phoneNumber);
    }

    public void inputToUserName(String userName) {
        waitToElementVisible(Jbo064UI.USERNAME_TEXTBOX);
        sendKeyToElement(Jbo064UI.USERNAME_TEXTBOX, userName);
    }

    public void inputToPassword(String password) {
        waitToElementVisible(Jbo064UI.PASSWORD_TEXTBOX);
        sendKeyToElement(Jbo064UI.PASSWORD_TEXTBOX, password);
    }

    public void inputToConfirmPassword(String confirmPassword) {
        waitToElementVisible(Jbo064UI.CONFIRM_PASSWORD_TEXTBOX);
        sendKeyToElement(Jbo064UI.CONFIRM_PASSWORD_TEXTBOX, confirmPassword);
    }


    public void clickToTermRadioButton() {
        waitToElementVisible(Jbo064UI.TERM_RADIO_BUTTON);
        clickToElement(Jbo064UI.TERM_RADIO_BUTTON);
    }



    public void clickToConfirmRegister() {
        waitToElementClickable(Jbo064UI.CONFIRM_REGISTER_BUTTON);
        clickToElement(Jbo064UI.CONFIRM_REGISTER_BUTTON);
    }

    public String getCurrentURL() {
        return getCurrentPageURL();
    }

//    public boolean isTheWelcomeMsgDisplay() {
////        waitToElementVisible(Rio66UI.WELCOME_MSG);
////        return getTextElement(Rio66UI.WELCOME_MSG).contains("Cảm ơn bạn đã đăng ký");
//        boolean isWelcomeMsgDisplay = isElementDisplayed(Rio66UI.WELCOME_MSG);
//        if (isWelcomeMsgDisplay) {
//            Constants.REGISTERED = true;
//        } else {
//            Constants.REGISTERED = false;
//        }
//        return isWelcomeMsgDisplay;
//    }
}
