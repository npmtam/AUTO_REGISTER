package pageUI;

public class Jbo064UI {


    public static final String REGISTER_BUTTON = "//span[@class='RBRegister']//a";

    public static final String PHONE_TEXTBOX = "//span[text()='Số điện thoại']//parent::div//following-sibling::div//input";
    public static final String USERNAME_TEXTBOX = "//span[text()='Tên đăng nhập']//parent::div//following-sibling::div//input";
    public static final String PASSWORD_TEXTBOX = "//span[text()='Mật khẩu']//parent::div//following-sibling::div//input";
    public static final String CONFIRM_PASSWORD_TEXTBOX = "//span[text()='Xác nhận mật khẩu']//parent::div//following-sibling::div//input";
    public static final String TERM_RADIO_BUTTON = "//i[@class='label-icon icon-checked false']";

    public static final String CONFIRM_REGISTER_BUTTON = "//button[@class='ant-btn register-button']";
    public static final String INVALID_PHONENUMBER_MESSAGE = "//div[@class='ant-message-notice-content']//span[text()='Số điện thoại phải có 9-10 số, vui lòng bỏ số “0” khi nhập thông tin.']";
    public static final String INVALID_USERNAME_MESSAGE = "//div[@class='ant-message-notice-content']//span[text()='Tên đăng nhập phải bao gồm ít nhất 6 kí tự, và không quá 14 kí tự.']";
    public static final String INVALID_PASSWORD_MESSAGE = "//div[@class='ant-message-notice-content']//span[text()='Mật khẩu không hợp lệ. Vui lòng điền lại.(Mật khẩu phải bao gồm từ 6 đến 16 chữ cái và số, không bao gồm ký hiệu, khoảng trống hay gạch dưới ở giữa)']";



    public static final String USERNAME_INFORMATION = "//a[@class='ant-dropdown-link ant-dropdown-trigger']";

}
