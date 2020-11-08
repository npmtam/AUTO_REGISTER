package pageObject;

import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import commons.AbstractPage;
import commons.Constants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import pageUI.Rio66UI;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Rio66PageObject extends AbstractPage {
    private WebDriver driver;
    private String backupUsername;

    public Rio66PageObject(WebDriver driver) {
        super(driver);
        this.driver = driver;
        backupUsername = getLastNameRandom() + getRandomNumber();
    }

    public void inputToUserName(String userName) {
        waitToElementVisible(Rio66UI.USERNAME_TEXTBOX);
        sendKeyToElement(Rio66UI.USERNAME_TEXTBOX, userName);
    }

    public void inputToPassword(String password) {
        waitToElementVisible(Rio66UI.PASSWORD_TEXTBOX);
        sendKeyToElement(Rio66UI.PASSWORD_TEXTBOX, password);
    }

    public void inputToConfirmPassword(String repassword) {
        waitToElementVisible(Rio66UI.CONFIRM_PASSWORD_TEXTBOX);
        sendKeyToElement(Rio66UI.CONFIRM_PASSWORD_TEXTBOX, repassword);
    }

    public String getCaptchaImgBase64() {
        waitToElementVisible(Rio66UI.CAPTCHA_IMAGE);
        captureElementScreenshot(Rio66UI.CAPTCHA_IMAGE);

        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(new File(Constants.CAPTCHA_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

    public String solveCaptcha(String apiKey) {
        TwoCaptcha solver = new TwoCaptcha(apiKey);

        //Define type and specific convention for captcha
        Normal captcha = new Normal();
        captcha.setBase64(getCaptchaImgBase64());
        captcha.setLang("en");

        //Send Post request
        try {
            solver.solve(captcha);
            System.out.println("Captcha solved: " + captcha.getCode());
            Constants.CAPTCHA_SOLVED = captcha.getCode();
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
            if(e.getMessage().equalsIgnoreCase("ERROR_CAPTCHA_UNSOLVABLE")){
                solveCaptcha(apiKey);
            } else if(e.getMessage().equalsIgnoreCase("ERROR_ZERO_BALANCE")){
                System.out.println("TÀI KHOẢN 2CAPTCHA ĐÃ HẾT TIỀN");
            }
        }
        return Constants.CAPTCHA_SOLVED;
    }

    public void inputToCaptchaTextbox(String captchaSolved) {
        waitToElementVisible(Rio66UI.CAPTCHA_TEXTBOX);
        sendKeyToElement(Rio66UI.CAPTCHA_TEXTBOX, captchaSolved);
    }

    public void clickToRegisterButton() {
        waitToElementClickable(Rio66UI.REGISTER_BUTTON);
        clickToElement(Rio66UI.REGISTER_BUTTON);

        if (isElementPresentInDOM(Rio66UI.ERROR_CAPTCHA)) {
            //If incorrect captcha > re-solve captcha
            if (getTextElement(Rio66UI.ERROR_CAPTCHA).contains("Mã xác nhận không đúng")) {
                System.out.println(getTextElement(Rio66UI.ERROR_CAPTCHA));
                System.out.println("Incorrect captcha");
                getCaptchaImgBase64();
                sleepInSecond(5);
                inputToCaptchaTextbox(Constants.CAPTCHA_SOLVED);
                clickToRegisterButton();
            //If username invalid >
            } else if (getTextElement(Rio66UI.ERROR_CAPTCHA).contains("không hợp lệ")) {
                System.out.println("Username is invalid");
                inputToUserName(backupUsername);
                getCaptchaImgBase64();
                sleepInSecond(5);
                inputToCaptchaTextbox(Constants.CAPTCHA_SOLVED);
                clickToRegisterButton();
            }
        }
    }

    public void inputFullNameTextbox(String fullName) {
        waitToElementVisible(Rio66UI.FULLNAME_TEXTBOX);
        sendKeyToElement(Rio66UI.FULLNAME_TEXTBOX, fullName);
    }

    public void clickToConfirmRegister() {
        waitToElementClickable(Rio66UI.CONFIRM_REGISTER_BUTTON);
        clickToElement(Rio66UI.CONFIRM_REGISTER_BUTTON);
        while(isElementPresentInDOM(Rio66UI.ERROR_CAPTCHA)){
            inputFullNameTextbox(backupUsername);
            break;
        }
    }

    public boolean isTheWelcomeMsgDisplay() {
//        waitToElementVisible(Rio66UI.WELCOME_MSG);
//        return getTextElement(Rio66UI.WELCOME_MSG).contains("Cảm ơn bạn đã đăng ký");
        boolean isWelcomeMsgDisplay = isElementDisplayed(Rio66UI.WELCOME_MSG);
        if(isWelcomeMsgDisplay){
            Constants.REGISTERED = true;
        } else {
            Constants.REGISTERED = false;
        }
        return isWelcomeMsgDisplay;
    }
}
