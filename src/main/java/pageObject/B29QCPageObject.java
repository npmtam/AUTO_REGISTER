package pageObject;

import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import commons.AbstractPage;
import commons.Constants;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import pageUI.B29QCUI;
import pageUI.Rio66UI;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class B29QCPageObject extends AbstractPage {
    private WebDriver driver;

    public B29QCPageObject(WebDriver driver){
        super(driver);
        this.driver = driver;
    }

    public void inputToUserName(String userName){
        waitToElementVisible(B29QCUI.USERNAME_TEXTBOX);
        sendKeyToElement(B29QCUI.USERNAME_TEXTBOX, userName);
    }

    public void inputToPassword(String password){
        waitToElementVisible(B29QCUI.PASSWORD_TEXTBOX);
        sendKeyToElement(B29QCUI.PASSWORD_TEXTBOX, password);
    }

    public void inputToConfirmPassword(String password){
        waitToElementVisible(B29QCUI.REPASSWORD_TEXTBOX);
        sendKeyToElement(B29QCUI.REPASSWORD_TEXTBOX, password);
    }

    public void clickToRegister(){
        waitToElementVisible(B29QCUI.REGISTER_BUTTON);
        clickToElement(B29QCUI.REGISTER_BUTTON);

        //If captcha is incorrect
        if(isElementDisplayed(B29QCUI.ERROR_MESSAGE)){
            if(getTextElement(B29QCUI.ERROR_MESSAGE).contains("Mã xác nhận không đúng")){
                sleepInSecond(4);
                inputToCaptcha(solveCaptcha(Constants.API_KEY));
                clickToElement(B29QCUI.REGISTER_BUTTON);
            }
        }
    }

    public String getCaptchaImgBase64() {
        waitToElementVisible(B29QCUI.CAPTCHA_IMAGE);
        captureElementScreenshot(B29QCUI.CAPTCHA_IMAGE);

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
        captcha.setMaxLen(3);

        //Send Post request
        try {
            solver.solve(captcha);
            System.out.println("Captcha solved: " + captcha.getCode());
            Constants.CAPTCHA_SOLVED = captcha.getCode();
        } catch (Exception e) {
            System.out.println("Error occured: " + e.getMessage());
            if (e.getMessage().equalsIgnoreCase("ERROR_CAPTCHA_UNSOLVABLE")) {
                solveCaptcha(apiKey);
            } else if (e.getMessage().equalsIgnoreCase("ERROR_ZERO_BALANCE")) {
                System.out.println("TÀI KHOẢN 2CAPTCHA ĐÃ HẾT TIỀN");
            }
        }
        return Constants.CAPTCHA_SOLVED;
    }

    public void inputToCaptcha(String captcha){
        waitToElementVisible(B29QCUI.CAPTCHA_TEXTBOX);
        sendKeyToElement(B29QCUI.CAPTCHA_TEXTBOX, captcha);
    }
}
