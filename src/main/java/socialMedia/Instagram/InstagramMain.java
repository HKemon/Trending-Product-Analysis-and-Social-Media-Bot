package socialMedia.Instagram;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import util.FullProjectUtils;

import java.util.List;

import static util.SeleniumUtil.waitForElementToBeClickable;
import static util.SeleniumUtil.waitForElementToBeVisible;

public class InstagramMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:/Users/emon/Downloads/geckodriver-v0.23.0-win64/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.instagram.com/");

        // Login
        FullProjectUtils.customSleep(2000, 2500);
        loginToInstagram(driver, "sports.slavvy@aol.com", "Mynameissportsslavvy1996?");

        // Remove Notification
        FullProjectUtils.customSleep(3000, 3500);
        removeNotificationMessage(driver);

        // Text Field
        FullProjectUtils.customSleep(4000, 5000);
        searchTextfield(driver, "key2success");

        // Logout
//        FullProjectUtils.customSleep(4000, 5000);
//        logoutToInstagram(driver);
    }

    private static void loginToInstagram(WebDriver driver, String userName, String password) {
        waitForElementToBeClickable(driver, driver.findElement(By.cssSelector("#react-root > section > main > article > div.rgFsT > div:nth-child(2) > p > a")), 50).click();
        FullProjectUtils.customSleep(2000, 2500);
        for (int i = 0; i < userName.length(); i++) {
            waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[1]/div/div[1]/input")), 50)
                    .sendKeys(String.valueOf(userName.charAt(i)));
            FullProjectUtils.customSleep(25, 50);
        }
        FullProjectUtils.customSleep(2000, 2500);
        for (int i = 0; i < password.length(); i++) {
            waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[2]/div/div[1]/input")), 50)
                    .sendKeys(String.valueOf(password.charAt(i)));
            FullProjectUtils.customSleep(25, 50);
        }
        FullProjectUtils.customSleep(2000, 2500);
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/article/div/div[1]/div/form/div[3]")), 50)
                .click();
    }

    private static void removeNotificationMessage(WebDriver driver) {
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div[3]/button[2]")), 50).click();
    }

    private static void logoutToInstagram(WebDriver driver) {
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/nav/div[2]/div/div/div[3]/div/div[3]/a/span")), 50).click();
        FullProjectUtils.customSleep(1000, 1500);
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/main/div/header/section/div[1]/div/button/span")), 50).click();
        FullProjectUtils.customSleep(1000, 1500);
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/div[3]/div/div/div/div/button[6]")), 50).click();
    }

    private static void searchTextfield(WebDriver driver, String text) {
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='react-root']/section/nav/div[2]/div/div/div[2]/div/div")), 50).click();
        FullProjectUtils.customSleep(2000, 2500);
        for (int i = 0; i < text.length(); i++) {
            waitForElementToBeVisible(driver, driver.findElement(By.xpath("/html/body/span/section/nav/div[2]/div/div/div[2]/input")), 50)
                    .sendKeys(String.valueOf(text.charAt(i)));
            FullProjectUtils.customSleep(25, 50);
        }//*[@id="react-root"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[1]
        //*[@id="react-root"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[2]
        //*[@id="react-root"]/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[3]
        FullProjectUtils.customSleep(2000, 2500);

        List<WebElement> elements = driver.findElements(By.xpath("/html/body/span/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a"));
//        for (WebElement element : elements) {
////            if (element.findElements(By.xpath("//a[@href]")).equals("/" + text + "/")) {
////                System.out.println();
////            }
//            System.out.println(element.findElements(By.xpath("//a[@href]")));
//        }
        FullProjectUtils.customSleep(2000, 2500);
        System.out.println(elements.size());

        for (int i = 0; i < elements.size(); i++) {
            if (waitForElementToBeVisible(driver, driver.findElement(By.xpath("/html/body/span/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[" + (i + 1) + "]")), 50)
                    .getAttribute("href")
                    .equals("https://www.instagram.com/" + text + "/")) {
                waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/span/section/nav/div[2]/div/div/div[2]/div[2]/div[2]/div/a[" + (i + 1) + "]")), 50)
                        .click();
                break;
            }
            FullProjectUtils.customSleep(25, 50);
        }
        FullProjectUtils.customSleep(4000, 5000);
        if (waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/span/section/main/div/header/section/div[1]/span/span[1]/button")), 50)
                .getText().equalsIgnoreCase("Follow")){
            waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/span/section/main/div/header/section/div[1]/span/span[1]/button")), 50)
                    .click();
        }else {
            System.out.println("Already Following");
        }
    }
}