package socialMedia;

import Country.city.CountryCity;
import Country.website.Website;
import Country.website.WebsiteJsoup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebsiteSocialMediaMain {
    public static void main(String[] args) {
        int lastIndex = -1;
        WebsiteJsoup websiteJsoup = new WebsiteJsoup();
        int i = 22711;
        while (true) {
            int x = websiteJsoup.fetchLastInsertedWebsiteCityIdIndex();
            List<WebsiteSocialMedia> websiteSocialMediaList = new ArrayList<>();
            System.out.println(lastIndex + " " + x);
            if (lastIndex == x) {
                break;
            } else {
                lastIndex = x;
            }
            List<Website> websites = websiteJsoup.fetchWebsiteListById(i);
            i += 50;
            System.out.println(websites);
            for (Website website : websites) {
                try {
                    Elements elements = extractElementsOfWebsite(website.getWebsite());
                    if (elements != null) {
                        WebsiteSocialMedia websiteSocialMedia = socialMediaOfWebsite(website.getId(), elements);
                        if (websiteSocialMedia != null) {
                            websiteSocialMediaList.add(websiteSocialMedia);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            batchInsert(websiteSocialMediaList);
        }
    }

    private static void batchInsert(List<WebsiteSocialMedia> websiteSocialMediaList) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            for (WebsiteSocialMedia websiteSocialMedia : websiteSocialMediaList) {
                session.save(websiteSocialMedia);
            }
            transaction.commit();
            System.out.println("YES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Elements extractElementsOfWebsite(String url) {
        try {
            return Jsoup.connect("https://www." + url).get().select("a[href]");
        } catch (IOException e1) {
            try {
                return Jsoup.connect("http://www." + url).get().select("a[href]");
            } catch (IOException e2) {
                try {
                    return Jsoup.connect("https://" + url).get().select("a[href]");
                } catch (IOException e3) {
                    try {
                        return Jsoup.connect("http://" + url).get().select("a[href]");
                    } catch (IOException e4) {
                        return null;
                    }
                }
            }
        }
    }

    private static WebsiteSocialMedia socialMediaOfWebsite(int i, Elements elements) {
        boolean facebook = false;
        boolean instagram = false;
        boolean twitter = false;
        boolean pinterest = false;
        boolean tumblr = false;
        boolean vk = false;
        boolean ok = false;
        boolean snapchat = false;
        boolean youtube = false;
        int count = 0;
        WebsiteSocialMedia websiteSocialMedia = new WebsiteSocialMedia();
        System.out.println("I " + i);
        websiteSocialMedia.setCityWebsiteId(i);
        for (Element link : elements) {
            if (!facebook && link.attr("href").contains("facebook.com")) {
//                System.out.println(link.attr("href"));
                facebook = true;
                count++;
                websiteSocialMedia.setFacebook(link.attr("href"));
            } else if (!instagram && link.attr("href").contains("instagram.com")) {
//                System.out.println(link.attr("href"));
                instagram = true;
                count++;
                websiteSocialMedia.setInstagram(link.attr("href"));
            } else if (!twitter && link.attr("href").contains("twitter.com")) {
//                System.out.println(link.attr("href"));
                twitter = true;
                count++;
                websiteSocialMedia.setTwitter(link.attr("href"));
            } else if (!pinterest && link.attr("href").contains("pinterest.com")) {
//                System.out.println(link.attr("href"));
                pinterest = true;
                count++;
                websiteSocialMedia.setPinterest(link.attr("href"));
            } else if (!tumblr && link.attr("href").contains("tumblr.com")) {
//                System.out.println(link.attr("href"));
                tumblr = true;
                count++;
                websiteSocialMedia.setTumblr(link.attr("href"));
            } else if (!vk && link.attr("href").contains("vkontakte.ru")) {
//                System.out.println(link.attr("href"));
                vk = true;
                count++;
                websiteSocialMedia.setVk(link.attr("href"));
            } else if (!ok && link.attr("href").contains("ok.ru")) {
//                System.out.println(link.attr("href"));
                ok = true;
                count++;
                websiteSocialMedia.setOk(link.attr("href"));
            } else if (!snapchat && link.attr("href").contains("snapchat.com")) {
//                System.out.println(link.attr("href"));
                snapchat = true;
                count++;
                websiteSocialMedia.setSnapchat(link.attr("href"));
            } else if (!youtube && link.attr("href").contains("https://youtube.com")) {
//                System.out.println(link.attr("href"));
                youtube = true;
                count++;
                websiteSocialMedia.setYouTube(link.attr("href"));
            }
            if (count >= 9) {
                break;
            }
        }
        if (count == 0)
            return null;
        return websiteSocialMedia;
    }
}