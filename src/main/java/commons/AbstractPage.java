package commons;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    long shortTimeout = 3;
    long midTimeout = 5;
    long longTimeout = 30;


    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        action = new Actions(driver);
    }

    public String getCurrentPageURL() {
        return driver.getCurrentUrl();
    }

    /* WEB ELEMENTS */

    public void clickToElement(String locator) {
        element = driver.findElement(By.xpath(locator));
        element.click();
    }

    public void sendKeyToElement(String locator, String value) {
        element = driver.findElement(By.xpath(locator));
        element.clear();
        element.sendKeys(value);
    }

    public String getAttributeValue(String locator, String attributeName) {
        element = driver.findElement(By.xpath(locator));
        return element.getAttribute(attributeName);
    }

    public String getTextElement(String locator) {
        element = driver.findElement(By.xpath(locator));
        return element.getText();
    }

    public boolean isElementDisplayed(String locator) {
        element = driver.findElement(By.xpath(locator));
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
        waitExplicit = new WebDriverWait(driver, longTimeout);
        waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitToElementClickable(String locator) {
        by = By.xpath(locator);
        waitExplicit = new WebDriverWait(driver, longTimeout);
        waitExplicit.until(ExpectedConditions.elementToBeClickable(by));
    }

    public void writeDataToCsv(String userName, String password, String url) {
        //Create new data object
        AccountInfo data = new AccountInfo(userName, password, url);

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
        final String[] firstName = new String[]{"nguyen", "do", "tran", "le", "pham", "phan", "vu", "dang", "hoang", "bui", "ho", "ly"};
        int index = random.nextInt(firstName.length);
        return firstName[index];
    }

    public String getLastNameRandom() {
        Random random = new Random();
        final String[] lastName = new String[]{"cuong", "tung", "thang", "son", "huy", "dung", "hung", "linh",
                "hieu", "hiep", "luan", "nam", "long", "minh", "dat", "quang", "tam", "thanh", "chien", "duc", "vuong",
                "phong", "tan", "quyen", "thi", "vinh", "quangvinh", "minhhoang", "hoanglong"};
        int index = random.nextInt(lastName.length);
        return lastName[index];
    }

    public int getRandomNumber(){
        Random random = new Random();
        int randomNumber = random.nextInt(999);
        return randomNumber;
    }
}
