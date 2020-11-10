package pageObject;

import commons.AbstractPage;
import commons.Constants;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageUI.VuaKingUI;

public class VuaKingPageObject extends AbstractPage {
    WebDriver driver;

    public VuaKingPageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void inputToUserName(String userName) {
        waitToElementVisible(VuaKingUI.USERNAME_TEXTBOX);
        sendKeyToElement(VuaKingUI.USERNAME_TEXTBOX, userName);
    }

    public void inputToPassword(String password) {
        waitToElementVisible(VuaKingUI.PASSWORD_TEXTBOX);
        sendKeyToElement(VuaKingUI.PASSWORD_TEXTBOX, password);
    }

    public void inputToConfirmPassword(String confirmPassword) {
        waitToElementVisible(VuaKingUI.CONFIRM_PASSWORD_TEXTBOX);
        sendKeyToElement(VuaKingUI.CONFIRM_PASSWORD_TEXTBOX, confirmPassword);
    }

    public String getCaptcha() {
        waitToElementVisible(VuaKingUI.CAPTCHA_BOX);
        String captcha = getAttributeValue(VuaKingUI.CAPTCHA_BOX, "value");
        return captcha;
    }

    public void inputToCaptcha(String captcha) {
        waitToElementVisible(VuaKingUI.CAPTCHA_TEXTBOX);
        sendKeyToElement(VuaKingUI.CAPTCHA_TEXTBOX, captcha);
    }

    public String getCurrentURL() {
        return getCurrentPageURL();
    }

    public void clickToRegister() {
        waitToElementClickable(VuaKingUI.REGISTER_BUTTON);
        clickToElement(VuaKingUI.REGISTER_BUTTON);
    }

    public boolean isLogoDisplayed() {
        boolean registered = isElementPresentInDOM(VuaKingUI.LOGO);
//        if (isElementPresentInDOM("//h1[text()='403 ERROR']")){
//            Constants.REGISTERED = false;
//        }
        if (registered) {
            Constants.REGISTERED = true;
        } else {
            Constants.REGISTERED = false;
        }
        System.out.println(Constants.REGISTERED);
        return registered;
    }

    public boolean isURLDifferent(String siteURl) {
        boolean isDifferent;
        if (!getCurrentURL().equals(siteURl)) {
            isDifferent = true;
            Constants.REGISTERED = true;
        } else {
            isDifferent = false;
            Constants.REGISTERED = false;
        }
        System.out.println(Constants.REGISTERED);
        System.out.println(isDifferent);
        System.out.println("current URL: " + getCurrentURL());
        System.out.println(siteURl);
        return isDifferent;
    }
}
