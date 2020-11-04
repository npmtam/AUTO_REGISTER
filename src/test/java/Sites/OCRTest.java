package Sites;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OCRTest {
    WebDriver driver;

    @BeforeClass
    public void beforeTest() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://vuaking.net/");
    }

    @Test
    public void ocrTest() throws IOException {
        WebElement element = driver.findElement(By.xpath("//input[@id='mainCaptcha']"));
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
        FileUtils.copyFile(screen, new File("C:\\Attachments\\screenshot.png"));
    }
}
