package Country.code;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HibernateUtil;

import java.io.IOException;

public class CountryCodeMain {
    public static void main(String[] args) {
        try {
            Document document = null;
            document = Jsoup.connect("https://countrycode.org/")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                    .timeout(999999999)
                    .get();


            Elements trs2 = document.getElementsByClass("visible-sm visible-xs");
            int j = 0;
            for (Element e : trs2) {
                CountryCode countryCode = new CountryCode();
                for (Element element : e.select("td")) {
                    if (j == 0) {
                        countryCode.setCountryName(element.text());
                        System.out.println(element.text());
                        j++;
                    } else if (j == 2) {
                        String[] sp = element.text().split("/");
                        countryCode.setCountryCodeOne(sp[0].trim());
                        countryCode.setCountryCodeTwo(sp[1].trim());
                        insertCountryCode(countryCode);
                        j = 0;
                    } else {
                        j++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void insertCountryCode(CountryCode countryCode) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(countryCode);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateCountryCode(CountryCode countryCode, String citiesJson) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(countryCode);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
