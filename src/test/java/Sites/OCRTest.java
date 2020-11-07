package Sites;

import com.twocaptcha.TwoCaptcha;
import com.twocaptcha.captcha.Normal;
import commons.AbstractPage;
import commons.AbstractTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OCRTest extends AbstractTest {
    WebDriver driver;
    private String imgPath;
    AbstractPage abstractPage;

    @BeforeClass
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        driver = getBrowserDriver("chrome");
        abstractPage = new AbstractPage(driver);

        fakeIP();
        getMyIPAddress();

        driver.get("http://rio66qc.club/");

        String rootFolder = System.getProperty("user.dir");
        imgPath = rootFolder + "\\src\\main\\resources\\captcha\\captcha.png";
    }

//    @Test
    public void ocrTest() throws IOException {
        WebElement element = driver.findElement(By.xpath("//img[@id='image']"));
        captureElementScreenshot(element);

        String imgPath = "C:\\Attachments\\screenshot.png";
        File image = new File(imgPath);
        // JNA Interface Mapping
        ITesseract instance = new Tesseract();
        try {
            String Textresult = instance.doOCR(image);
            // Print out the text results
            System.out.println(Textresult);

            // Verify to check the text is displayed
            System.out.println("**************");
            String ExpectedText = "Logs saved to sdcard/SysLog/";

            if (Textresult.contains(ExpectedText)) {
                System.out.println("Passed. The expected text is displayed!");
            } else {
                System.out.println("Failed. The text was not found!");
            }

        } catch (Exception e) {
            System.out.println("Failed. Could not read the text from image file!");
        }
    }

//    @Test
    public void doOCR() throws IOException, URISyntaxException, TesseractException {
//        WebElement element = driver.findElement(By.xpath("//img[@id='image']"));


        File tmpFolder = LoadLibs.extractTessResources("win32-64");
        System.setProperty("java.library.path", tmpFolder.getPath());

        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("eng");
        tesseract.setOcrEngineMode(1);

        Path dataDirectory = Paths.get(ClassLoader.getSystemResource("data").toURI());
        tesseract.setDatapath(dataDirectory.toString());

        BufferedImage image = ImageIO.read(new File(imgPath));
        String result = tesseract.doOCR(image);
        System.out.println(result);
    }

    @Test
    public void solve2Captcha() throws IOException {
        WebElement element = driver.findElement(By.xpath("//img[@id='image']"));
        captureElementScreenshot(element);

        TwoCaptcha solver = new TwoCaptcha("b73d2cdf3ba11f3dbdb8b77d4eb06281");

        Normal captcha = new Normal();
        captcha.setFile(imgPath);
        captcha.setMaxLen(3);

        try {
            solver.solve(captcha);
            System.out.println("Captcha solved: " + captcha.getCode());
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }


    public void captureElementScreenshot(WebElement element) throws IOException {
        //Capture entire page screenshot as buffer.
        //Used TakesScreenshot, OutputType Interface of selenium and File class of java to capture screenshot of entire page.
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //Used selenium getSize() method to get height and width of element.
        //Retrieve width of element.
        int ImageWidth = element.getSize().getWidth();
        //Retrieve height of element.
        int ImageHeight = element.getSize().getHeight();

        //Used selenium Point class to get x y coordinates of Image element.
        //get location(x y coordinates) of the element.
        Point point = element.getLocation();
        int xcord = point.getX();
        int ycord = point.getY();

        //Reading full image screenshot.
        BufferedImage img = ImageIO.read(screen);

        //cut Image using height, width and x y coordinates parameters.
        BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
        ImageIO.write(dest, "png", screen);

        //Used FileUtils class of apache.commons.io.
        //save Image screenshot In D: drive.
        FileUtils.copyFile(screen, new File(imgPath));
    }
}
