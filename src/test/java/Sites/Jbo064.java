package Sites;

import commons.AbstractTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObject.Jbo064PageObject;

public class Jbo064 extends AbstractTest {
    private WebDriver driver;
    private Jbo064PageObject jbo064Page;

    @BeforeClass
    public void beforeClass(){
        driver = getBrowserDriver("chrome");
        jbo064Page = new Jbo064PageObject(driver);

        driver.get("https://www.jbo064.com/vn/");
    }

    @Test
    public void test(){
        jbo064Page.closeAllWindowsWithoutParent();
        String phoneNumber = jbo064Page.getPhoneNumberRandom();
        String userName = jbo064Page.getLastNameRandomForJbo064() + jbo064Page.getRandomNumber();
        String password = jbo064Page.getLastNameRandom() + jbo064Page.getRandomNumber();

        jbo064Page.clickToRegisterButton();
        jbo064Page.inputToPhoneNumber(phoneNumber);
        jbo064Page.inputToUserName(userName);
        jbo064Page.inputToPassword(password);
        jbo064Page.inputToConfirmPassword(password);
        jbo064Page.clickToTermRadioButton();
        jbo064Page.clickToConfirmRegister();

    }
}
