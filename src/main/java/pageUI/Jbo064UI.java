package pageUI;

public class Jbo064UI {


    public static final String REGISTER_BUTTON = "//span[@class='RBRegister']//a";

    public static final String PHONE_TEXTBOX = "//span[text()='Số điện thoại']//parent::div//following-sibling::div//input";
    public static final String USERNAME_TEXTBOX = "//span[text()='Tên đăng nhập']//parent::div//following-sibling::div//input";
    public static final String PASSWORD_TEXTBOX = "//span[text()='Mật khẩu']//parent::div//following-sibling::div//input";
    public static final String CONFIRM_PASSWORD_TEXTBOX = "//span[text()='Xác nhận mật khẩu']//parent::div//following-sibling::div//input";
    public static final String TERM_RADIO_BUTTON = "//i[@class='label-icon icon-checked false']";

    public static final String CONFIRM_REGISTER_BUTTON = "//button[@class='ant-btn register-button']";

    public static final String WELCOME_MSG = "//div[@class='container' and contains(text(), 'Cảm ơn bạn')]";

}
