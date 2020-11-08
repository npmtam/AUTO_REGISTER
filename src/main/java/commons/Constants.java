package commons;

import java.util.ArrayList;
import java.util.List;

public class Constants {

    public static String ROOT_FOLDER = System.getProperty("user.dir");
    public static final String CSV_PATH = ROOT_FOLDER + "\\src\\main\\resources\\account.csv";
    public static final String COMMA_DELIMITER = ",";
    public static final String NEW_LINE_SEPARATOR = "\n";

    public static boolean REGISTERED = false;
    public static String IP_ADDRESS;


    public static List<Integer> ACCOUNTS_SUCCESS = new ArrayList<Integer>();

    public static final String VUAKING = "http://vuaking.net/";

    //Catpcha
    public static final String API_KEY = "b73d2cdf3ba11f3dbdb8b77d4eb06281";
    public static String CAPTCHA_IMAGE_PATH = ROOT_FOLDER + "\\src\\main\\resources\\catpcha\\captcha.png";
    public static String CAPTCHA_SOLVED;
}
