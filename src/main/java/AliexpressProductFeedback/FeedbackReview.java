package AliexpressProductFeedback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static util.SeleniumUtil.*;

public class FeedbackReview {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:/Users/emon/Downloads/geckodriver-v0.23.0-win64/geckodriver.exe");
        WebDriver driver = new FirefoxDriver(changeIp());
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String returnedUrlValue = getUrlOfProduct(driver);
//        if (returnedUrlValue.equalsIgnoreCase("No Exception")) {
//            closePopup(driver);
//            endOfThePage(js, driver);
//            WebElement transactionFeedbackInput = LibraryUtil.waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='j-transaction-feedback']/div[2]/div[2]/div/div[2]/label/input")), 50);
//            callJsoupForTableExtract(driver.getPageSource());
//            inputValue(transactionFeedbackInput, 2);
//            clickButton(driver, js, transactionFeedbackInput);
//        }else {
//            System.out.println("go to next url from db");  // work on it
//        }
    }

    private static void clickButton(WebDriver driver, JavascriptExecutor js, WebElement transactionFeedbackInput) {
        int y = transactionFeedbackInput.getLocation().getY();
        js.executeScript("window.scrollTo(0," + (y - 250) + ");");
        WebElement element2 = waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='j-transaction-feedback']/div[2]/div[2]/div/div[2]/button")), 50);
        if (element2 != null)
            element2.click();
        else System.out.println("element 2 is null");
    }

    private static void inputValue(WebElement transactionFeedbackInput, int i) {
        if (transactionFeedbackInput != null) {
            transactionFeedbackInput.sendKeys(String.valueOf(i));
        } else System.out.println("element 1 is null");
    }

    private static void closePopup(WebDriver driver) {
        if (driver.getPageSource().contains("layer-close")) {
            WebElement element = waitForElementToBeClickable(driver, driver.findElement(By.xpath("//a[@data-role='layer-close']")), 50);
            if (element != null)
                element.click();
            else System.out.println("element is null");
        }
    }

    private static void endOfThePage(JavascriptExecutor js, WebDriver driver) {
        WebElement lastElement = driver.findElement(By.xpath("//div[@class='site-footer']"));
        int y = lastElement.getLocation().getY();
        js.executeScript("window.scrollTo(0," + y + ");");
        try {
            Thread.sleep(3000); // Work on it
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getUrlOfProduct(WebDriver driver) {
        try {
            driver.get("https://www.instagram.com");
        } catch (Exception e) {
            return e.getMessage();
        }
        return "No Exception";
    }

    private static void callJsoupForTableExtract(String pageSource) {
        Document doc = Jsoup.parse(pageSource);
        Elements elementsByClass = doc.getElementsByClass("transaction-feedback-table");
        Elements rows = elementsByClass.tagName("tbody")
                .select("tr")
                .select("td")
                .select("b");
        System.out.println(rows.text());
        Elements row = doc.getElementsByClass("order-time");
        System.out.println(row.text());
    }
}