package Country.city;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import util.HibernateUtil;

import java.util.ArrayList;

import static util.SeleniumUtil.waitForElementToBeClickable;
import static util.SeleniumUtil.waitForElementToBeVisible;

public class CountryCityMain {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "C:/Users/emon/Downloads/geckodriver-v0.23.0-win64/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.google.com/");

        waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/div/div[7]/span/center/div[3]/div[1]/div/a")), 50).click();

        ArrayList arrayList = new ArrayList();
        for (int i = 1; i <= 240; i++) {
            String cityName = fetchCountryName(i);
            try {

                waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@class='gLFyf gsfi']")), 30).sendKeys("All cities in " + cityName);
//                try {
                waitForElementToBeClickable(driver, driver.findElement(By.xpath("/html/body/div/div[3]/form/div[2]/div/div[2]/div[2]/div/center/input[1]")), 30).click();
//                }catch (Exception e){
//                    System.out.println("HERE 1");
//                    waitForElementToBeClickable(driver, driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div/div[3]/center/input[1]")), 50).click();
//                }
                waitForElementToBeVisible(driver, driver.findElement(By.xpath("//*[@class='klcc']/g-scrolling-carousel/div/div")), 30);
                extractHTMLToJsoup(driver.getPageSource(), i);
            } catch (Exception e) {
//                System.out.println("HERE 2");
                System.out.println("MESSAGE " + e.getMessage());
                arrayList.add(cityName);
            }
            driver.get("https://www.google.com/");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("===================ARRAY LIST==================");
        System.out.println(arrayList);
//        for (long i = 1; i <= 240; i++) {
//        System.out.println(fetchCountryName(100));
//        }
    }


    //    convertCitiesToJson
    private static void extractHTMLToJsoup(String html, int index) {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByClass("kltat");
        batchInsert(elements, index);
    }

    private static void batchInsert(Elements elements, int index) {
        Transaction transaction = null;
        int batchSize = 10;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            int i = 0;
            for (Element e : elements) {
                System.out.println(e.text());
                i++;
                CountryCity countryCity = new CountryCity();
                countryCity.setCountryId(index);
                countryCity.setCity(e.text());
                session.save(countryCity);
                if (i > 0 && i % batchSize == 0) {
//                    System.out.println("Batch");
                    session.flush();
                    session.clear();
                }
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetchCountryName(long i) {
        String countryName = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "select c.countryName From Country.code.CountryCode c where id=:id";
            countryName = session.createQuery(HQL)
                    .setParameter("id", i)
                    .getResultList()
                    .get(0)
                    .toString();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryName;
    }
}
