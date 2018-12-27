package Country.website;

import Country.city.CountryCity;
import Country.code.CountryCode;
import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import util.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.SeleniumUtil.changeIp;
import static util.SeleniumUtil.waitForElementToBeClickable;
import static util.SeleniumUtil.waitForElementToBeVisible;


public class WebsiteLink {
//    private static ArrayList<String> errorPageIteration = new ArrayList<>();
    private static List<WebsiteCategory> websiteCategoryList = new ArrayList<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:/Users/emon/Downloads/geckodriver-v0.23.0-win64/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        getCountryCity_IdAndCity(driver, 228, "United Kingdom");
//        findShop(WebsiteLink.executorService,driver,288,2,"men fashion",);
    }

    public static void findShop(ExecutorService executorService, WebDriver driver, int cityId, int categoryId, String category, String location) {
        driver.get("https://www.yelp.com/search?find_desc=sports&find_loc=Buenos%20Aires&start=30");
        int pageNumber = 0;
        waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='search_description']")), 50).sendKeys(category);
        waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='search_location']")), 50).sendKeys(location);
        waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='search_location']")), 50);
        waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='header_find_form']/div/div[2]/div/div[2]")), 50).click();
        String rootUrl = driver.getCurrentUrl();
        do {
//            int y = waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@id='wrap']/div[4]")), 50).getLocation().getY();
//            js.executeScript("window.scrollTo(0," + (y - 100) + ");");
            try {
                Thread.sleep(5000);
                pageNumber++;
//                String splits = driver.getCurrentUrl().split("&start=")[0];
                driver.get(rootUrl + "&start=" + (pageNumber * 10 - 10));

                if (driver.getPageSource().contains("lemon--a__373c0__1_OnJ link__373c0__29943 photo-box-link__373c0__1AvT5 link-color--blue-dark__373c0__1mhJo link-size--default__373c0__1skgq")) {

                    System.out.println(driver.getCurrentUrl());

                    executorService.execute(new WebsiteJsoup(driver.getPageSource(), categoryId, cityId));

                    Thread.sleep(2500);
                } else break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
//                errorPageIteration.add(pageNumber + " " + category + " " + location);
            }
//            System.out.println(errorPageIteration);
        } while (pageNumber < 20);
    }


    private static void getCountryCity_IdAndCity(WebDriver driver, int index, String country) {
        List<CountryCity> countryCities = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "FROM CountryCity where countryId=:countryId";
            List list = session.createQuery(HQL)
                    .setParameter("countryId", index)
                    .list();
            for (int i = 0; i < list.size(); i++) {
                CountryCity countryCity = (CountryCity) list.get(i);
                countryCities.add(countryCity);
                System.out.println("          " + countryCities.get(i).getId() + " " + countryCities.get(i).getCity());
                populateWebsiteCategory(driver, countryCities.get(i).getId(), countryCities.get(i).getCity(), country);
            }
        } catch (Exception e) {
            System.out.println("getCountryCity_IdAndCity " + e.getMessage());
        }
    }

    private static void populateWebsiteCategory(WebDriver driver, int cityId, String city, String country) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "From WebsiteCategory where id=:id";
            for (int i = 1; i <= 4; i++) {
                WebsiteCategory list = (WebsiteCategory) session.createQuery(HQL)
                        .setParameter("id", i)
                        .list()
                        .get(0);
                websiteCategoryList.add(list);
            }
            for (int i = 0; i < 4; i++) {
                try {
                    findShop(WebsiteLink.executorService, driver, cityId, websiteCategoryList.get(i).getId(), websiteCategoryList.get(i).getCategory(), city + ", " + country);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("populateWebsiteCategory " + e.getMessage());
        }
    }

}