package pageObject;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUI.Ngoaihang88UI;

public class Ngoaihang88PageObject extends AbstractPage {
    private WebDriver driver;

    public Ngoaihang88PageObject(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    public void inputToUserName(String userName){
        waitToElementVisible(Ngoaihang88UI.USERNAME_TEXTBOX);
        sendKeyToElement(Ngoaihang88UI.USERNAME_TEXTBOX, userName);
    }

    public void inputToPassword(String password){
        waitToElementVisible(Ngoaihang88UI.PASSWORD_TEXTBOX);
        sendKeyToElement(Ngoaihang88UI.PASSWORD_TEXTBOX, password);
    }

    public void inputToEmail(String email){
        waitToElementVisible(Ngoaihang88UI.EMAIL_TEXTBOX);
        sendKeyToElement(Ngoaihang88UI.EMAIL_TEXTBOX, email);
    }

    public void inputToPhoneNumber(String phoneNumber){
        waitToElementVisible(Ngoaihang88UI.PHONE_NUMBER_TEXTBOX);
        sendKeyToElement(Ngoaihang88UI.PHONE_NUMBER_TEXTBOX, phoneNumber);
    }

    public void clickToRegisterButton(){
        waitToElementVisible(Ngoaihang88UI.REGISTER_BUTTON);
        clickToElement(Ngoaihang88UI.REGISTER_BUTTON);
    }

    public void clickToClosePopup(){
        boolean isBlocked = getAttributeValue(Ngoaihang88UI.ERROR_POPUP, "style").contains("block");
        if(isBlocked){
            clickToElement(Ngoaihang88UI.CLOSE_POPUP_BTN);
        }
    }
}
