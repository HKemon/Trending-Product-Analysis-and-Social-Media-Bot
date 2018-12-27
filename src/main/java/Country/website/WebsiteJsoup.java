package Country.website;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import socialMedia.WebsiteSocialMedia;
import util.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WebsiteJsoup implements Runnable {
    Document document;
    int catId;
    int citId;
    HashSet<String> unique = new HashSet<>();

    public WebsiteJsoup() {
    }

    public WebsiteJsoup(String s, int cat, int cityId) {
        document = Jsoup.parse(s);
        catId = cat;
        citId = cityId;
        System.out.println(cat);
    }

    @Override
    public void run() {
        Elements links = document.select("a[href]");

        //Iterate links and print link attributes.
        for (Element link : links) {
            if (link.attr("href").startsWith("/biz/")) {
                unique.add(link.attr("href").split("\\?")[0]);
            }
        }

        for (String h : unique) {
            extractWebsite(h);
        }

//        System.out.println(unique);
//        System.out.println(unique.size());
    }

    public void extractWebsite(String url) {
        try {
            Document document = Jsoup.connect("https://www.yelp.com" + url).get();
            Elements links = document.select("a[href]");
            for (Element link : links) {
                if (link.attr("href").startsWith("/biz_redir")) {
                    System.out.println(link.text().split("/")[0]);
                    if (fetchWebsiteByWebsite(link.text().split("/")[0])) {
                        Website website = new Website();
                        website.setWebsiteCategoryId(catId);
                        website.setCityId(citId);
                        website.setWebsite(link.text().split("/")[0]);
                        insertWebsite(website);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Url is not accessable");
        }
    }

    public boolean fetchWebsiteByWebsite(String website) {
        List websiteList = new ArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "From Website p where website=:website";
            websiteList = session.createQuery(HQL)
                    .setParameter("website", website)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return websiteList.isEmpty();
    }

    public int fetchLastInsertedWebsiteCityIdIndex() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            WebsiteSocialMedia websiteSocialMedia = (WebsiteSocialMedia) session.createQuery("from WebsiteSocialMedia order by cityWebsiteId DESC").list().get(0);
            System.out.println(websiteSocialMedia);
            return websiteSocialMedia.getCityWebsiteId();
        } catch (IndexOutOfBoundsException e) {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Website> fetchWebsiteListById(int idStart) {
        List websiteList = new ArrayList();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            String HQL = "From Website p where id>:i";
            websiteList = session.createQuery(HQL)
                    .setParameter("i", idStart)
                    .setMaxResults(50)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return websiteList;
    }

    public void insertWebsite(Website website) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(website);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
