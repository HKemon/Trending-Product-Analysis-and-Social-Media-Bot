package socialMedia.Instagram;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static util.SeleniumUtil.waitForElementToBeClickable;
import static util.SeleniumUtil.waitForElementToBeVisible;

public class InstagramMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:/Users/emon/Downloads/geckodriver-v0.23.0-win64/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.instagram.com/");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForElementToBeClickable(driver, driver.findElement(By.cssSelector("#react-root > section > main > article > div.rgFsT > div:nth-child(2) > p > a")), 50).click();
        try {
            Thread.sleep(2352);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[1]/div/div[1]/input")), 50).sendKeys("sports.slavvy@aol.com");
        try {
            Thread.sleep(2050);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[2]/div/div[1]/input")), 50).sendKeys("Mynameissportsslavvy1996?");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[3]")), 50).click();
    }
}
