package Sites;

import commons.AbstractTest;
import commons.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObject.VuaKingPageObject;

public class TestVuaKing extends AbstractTest {
    private WebDriver driver;
    private VuaKingPageObject vuakingPage;

    @BeforeClass
    public void beforeClass(){
        driver = getBrowserDriver("chrome");
        vuakingPage = new VuaKingPageObject(driver);

        driver.get("https://www.expressvpn.com/what-is-my-ip?");
    }

    @Test
    public void test(){
//        vuakingPage.closeAllWindowsWithoutParent();
//        String userName = vuakingPage.getFirstNameRandom() + vuakingPage.getLastNameRandom() + vuakingPage.getRandomNumber();
//        String password = vuakingPage.getLastNameRandom() + vuakingPage.getRandomNumber();
//
//        vuakingPage.inputToUserName(userName);
//        vuakingPage.inputToPassword(password);
//        vuakingPage.inputToConfirmPassword(password);
//        String captcha = vuakingPage.getCaptcha();
//        vuakingPage.inputToCaptcha(captcha);
//        String currentURL = vuakingPage.getCurrentURL();
//        vuakingPage.clickToRegister();
//        verifyTrue(vuakingPage.isChatButtonDisplay());
//        if(Constants.REGISTERED){
//            vuakingPage.writeDataToCsv(userName, password, currentURL);
//        }

        String IP = driver.findElement(By.xpath("//p[@class='ip-address']")).getText();
        System.out.println(IP);
    }
}
