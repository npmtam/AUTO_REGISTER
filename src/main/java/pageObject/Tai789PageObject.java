package pageObject;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUI.Tai789UI;

public class Tai789PageObject extends AbstractPage {
    private WebDriver driver;

    public Tai789PageObject(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    public void inputToUserName(String userName){
        waitToElementVisible(Tai789UI.USERNAME_TEXTBOX);
        sendKeyToElement(Tai789UI.USERNAME_TEXTBOX, userName);
    }

    public void inputToPassword(String password){
        waitToElementVisible(Tai789UI.PASSWORD_TEXTBOX);
        sendKeyToElement(Tai789UI.PASSWORD_TEXTBOX, password);
    }

    public void inputToRePassword(String repassword){
        waitToElementVisible(Tai789UI.CONFIRM_PASSWORD_TEXTBOX);
        sendKeyToElement(Tai789UI.CONFIRM_PASSWORD_TEXTBOX, repassword);
    }

    public void clickToRegisterButton(){
        waitToElementVisible(Tai789UI.REGISTER_BUTTON);
        clickToElement(Tai789UI.REGISTER_BUTTON);
    }

    public void inputToReName(String reName){
        waitToElementVisible(Tai789UI.RE_USERNAME_TEXTBOX);
        sendKeyToElement(Tai789UI.RE_USERNAME_TEXTBOX, reName);
    }

//    public void
}
