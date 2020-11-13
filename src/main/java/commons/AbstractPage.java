package commons;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AbstractPage {
    WebDriver driver;
    WebElement element;
    By by;
    Select select;
    JavascriptExecutor jsExecutor;
    WebDriverWait waitExplicit;
    List<WebElement> elements;
    Set<String> allWindows;
    Actions action;
    long shortTimeout = 5;
    long midTimeout = 5;
    long longTimeout = 30;
    public final Log log;


    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
        log = LogFactory.getLog(getClass());
    }

    public String getCurrentPageURL() {
        return driver.getCurrentUrl();
    }

    /* WEB ELEMENTS */

    //throw exception
    public WebElement findElement(String locator) {
        try {
            element = driver.findElement(By.xpath(locator));
        } catch (NoSuchElementException e) {
            log.info("CÓ LỖI XẢY RA - Không thể tìm thấy element");
            e.printStackTrace();
            driver.quit();
        }
        return element;
    }

    public void clickToElement(String locator) {
        element = findElement(locator);
        element.click();
    }


    public void clickToElementByJS(String locator) {
        element = driver.findElement(By.xpath(locator));
        JavascriptExecutor javascript = (JavascriptExecutor) driver;
        javascript.executeScript("arguments[0].click();", element);
    }

    public void sendKeyToElement(String locator, String value) {
        element = findElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    public String getAttributeValue(String locator, String attributeName) {
        element = findElement(locator);
        return element.getAttribute(attributeName);
    }

    public String getTextElement(String locator) {
        element = findElement(locator);
        return element.getText();
    }

    public boolean isElementDisplayed(String locator) {
        element = findElement(locator);
        return element.isDisplayed();
    }

    public void switchToWindowsByTitle(String title) {
        allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentWindow = driver.getTitle();
            if (currentWindow.equals(title)) {
                break;
            }
        }
    }

    public void overideGlobalTimeout(long timeout) {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    public boolean isElementPresentInDOM(String locator) {
        overideGlobalTimeout(shortTimeout);
        elements = driver.findElements(By.xpath(locator));
        overideGlobalTimeout(longTimeout);
        if (elements.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void waitToElementVisible(String locator) {
        by = By.xpath(locator);
        try {
            waitExplicit = new WebDriverWait(driver, longTimeout);
            waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            log.error("CÓ LỖI XẢY RA - Không tìm thấy element");
            e.printStackTrace();
            driver.quit();
        }
    }

    public void waitToElementClickable(String locator) {
        by = By.xpath(locator);
        waitExplicit = new WebDriverWait(driver, longTimeout);
        waitExplicit.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void writeDataToCsv(String userName, String password, String url, String ipAddress) {
        //Create new data object
        AccountInfo data = new AccountInfo(userName, password, url, Constants.IP_ADDRESS);

        List<AccountInfo> accountList = new ArrayList<>();
        accountList.add(data);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(Constants.CSV_PATH, true);
            for (AccountInfo id : accountList) {
                fileWriter.append(id.getUserName());
                fileWriter.append(Constants.COMMA_DELIMITER);
                fileWriter.append(id.getPassword());
                fileWriter.append(Constants.COMMA_DELIMITER);
                fileWriter.append(id.getUrl());
                fileWriter.append(Constants.COMMA_DELIMITER);
                fileWriter.append(id.getIpAddress());
            }
            fileWriter.append(Constants.NEW_LINE_SEPARATOR);
        } catch (Exception e) {
            System.out.println("Error in CSVFileWriter");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter");
                e.printStackTrace();
            }
        }
    }

    public void appendDataToExcel(String userName, String password, String url, String ipAddress) throws IOException {
        File file = new File(Constants.EXCEL_PATH);
        // Create Workbook
        InputStream inputStream = new FileInputStream(file);
//        Workbook workbook = getWorkbook(inputStream, Constants.EXCEL_PATH);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);

        Row row;
        int lastRow = sheet.getLastRowNum();
        row = sheet.getRow(lastRow);

//        cell = row.createCell(0);
//        cell.setCellValue(userName);
//
//        cell = row.createCell(1);
//        cell.setCellValue(password);
//
//        cell = row.createCell(2);
//        cell.setCellValue(url);
//
//        cell = row.createCell(3);
//        cell.setCellValue(ipAddress);

        XSSFCell cell = (XSSFCell) row.getCell(0);
        cell.setCellValue(userName);

        cell = (XSSFCell) row.getCell(1);
        cell.setCellValue(password);

        cell = (XSSFCell) row.getCell(2);
        cell.setCellValue(url);

        cell = (XSSFCell) row.getCell(3);
        cell.setCellValue(ipAddress);

        inputStream.close();
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();

        log.info("Written data to " + Constants.EXCEL_PATH);
    }

    private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;

        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
        return workbook;
    }


    public void sleepInSecond(long numberInSecond) {
        try {
            Thread.sleep(numberInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void closeAllWindowsWithoutParent() {
        String parentID = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String runWindows : allWindows) {

            if (!runWindows.equals(parentID)) {
                driver.switchTo().window(runWindows);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
    }

    public String getFirstNameRandom() {
        Random random = new Random();
        final String[] firstName = new String[]{"nguyen", "do", "tran", "le", "pham", "phan", "vu", "dang", "hoang",
                "bui", "ho", "ly", "truong", "nguyenle", "nguyentran", "tranvo", "vo"};
        int index = random.nextInt(firstName.length);
        return firstName[index];
    }

    public String getPhoneNumberRandom() {
        int num1; //3 numbers in area code
        int set2; //sequence 2 and 3 of the phone number

        Random generator = new Random();

        //Area code number; Will not print 8 or 9
        num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin
//        num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
//        num3 = generator.nextInt(8);

        // Sequence two of phone number
        // the plus 100 is so there will always be a 3 digit number
        // randomize to 643 because 0 starts the first placement so if i randomized up to 642 it would only go up yo 641 plus 100
        // and i used 643 so when it adds 100 it will not succeed 742
        set2 = generator.nextInt(643) + 1000000000;

        int phoneNumber = num1 + set2;
        System.out.println(phoneNumber);
        String newPhonenumber = Integer.toString(phoneNumber);
        return newPhonenumber;
    }


    public String getLastNameRandom() {
        Random random = new Random();
        final String[] lastName = new String[]{"cuong", "tung", "thang", "son", "huy", "dung", "hung", "linh",
                "hieu", "hiep", "luan", "nam", "long", "minh", "dat", "quang", "tam", "thanh", "chien", "duc", "vuong",
                "phong", "tan", "quyen", "thi", "vinh", "quangvinh", "minhhoang", "hoanglong", "hoa", "ngoc", "phung",
                "sen", "tien", "congcuong", "huuloc", "minhthien", "thanhphuong", "trong", "minhcong", "dinhtuan",
                "hungdung", "tranthanh", "huuhoang", "huyloc", "hahoang", "thanhphong", "quocdinh", "ngochai"};
        int index = random.nextInt(lastName.length);
        return lastName[index];
    }

    public String getLastNameRandomForJbo064() {
        Random random = new Random();
        final String[] lastName = new String[]{"cuong", "tung", "thang", "son", "huy", "dung", "hung", "linh",
                "hieu", "hiep", "luan", "nam", "long", "minh", "dat", "quang", "tam", "thanh", "chien", "duc", "vuong",
                "phong", "tan", "quyen", "thi", "vinh", "vinh", "minh", "long", "hoa", "ngoc", "phung",
                "sen", "tien", "cong", "huuloc", "thien", "thanh", "trong", "minhcong", "tuan",
                "dung", "thanh", "huu", "loc", "ha", "thphon", "dinh", "hai"};
        int index = random.nextInt(lastName.length);
        return lastName[index];
    }

    public int getRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(9999);
        return randomNumber;
    }

    public void captureElementScreenshot(String locator) {
        element = findElement(locator);
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
        BufferedImage img = null;
        try {
            img = ImageIO.read(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //cut Image using height, width and x y coordinates parameters.
        BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth, ImageHeight);
        try {
            ImageIO.write(dest, "png", screen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Used FileUtils class of apache.commons.io.
        //save Image screenshot In D: drive.
        try {
            FileUtils.copyFile(screen, new File(Constants.CAPTCHA_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
