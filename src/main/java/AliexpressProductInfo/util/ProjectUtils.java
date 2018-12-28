package AliexpressProductInfo.util;

import AliexpressProductInfo.classes.IterateLists;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import AliexpressProductInfo.scheduler.WebScraperScheduler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectUtils {
    // Used to hold the ip and user agent
    public static String[][] tempIpAgent = new String[1000][2];
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String yearMonthDate = dateFormat.format(new Date());
    public static String year = dateFormat.format(new Date()).split("-")[0];
    // Set the minimum order to iterate that product
    public static int minimumOrder = 300;
    public static String excelFolder = "C:\\Users\\emon\\Desktop\\";
    // Check if the next page is needed to iterate;
    public static boolean accessNextPage = true;
    public static Object[][] data;
    private static SchedulerFactory schFactory = new StdSchedulerFactory();
    public static boolean stopProgram = false;

    public static JSONParser jsonParser = new JSONParser();

    public static Scheduler scheduler = null;
    private static JobDetail job = null;
    private static Trigger trigger = null;

    public static IterateLists iterateLists = new IterateLists();
    public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // Used to hold all unvisited urls
    public static LinkedList<String> unVisited = new LinkedList<>();
    // Used to hold all visited urls
    public static LinkedList<String> visited = new LinkedList<>();

    public static StringBuilder tagId = new StringBuilder();

    public static void startScheduler() {
        try {
            job = JobBuilder.newJob(WebScraperScheduler.class)
                    .build();
            trigger = TriggerBuilder
                    .newTrigger()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24).repeatForever())
                    .build();
            scheduler = schFactory.getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public static void stopScheduler() {
        try {
            scheduler.shutdown();
            scheduler = null;
            job = null;
            trigger = null;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    // Return RawHtmlFile
    public static Document callJsoup(String url) {
        setProxyIpAgent();  // Set Proxy
        Document document = null;
        try {
            System.out.println("URL " + url);
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")
                    .timeout(999999999)
                    .get();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + url);
        } finally {
            if (document == null) {
                unVisited = new LinkedList<>();
                visited = new LinkedList<>();

//                // For new url from unVisited list
                if (isNumeric(splitURLForPage(url))) {
                    System.out.println("In For new url from unVisited list");
                    unVisited.add(url);
                    visited.remove(url);
                    try {
                        System.out.println("In For new url from unVisited list 2");
                        new IterateLists().callURL();
                    } catch (IOException e) {
                        System.out.println("Exception in callJsoup Connection timed out: connect");
                    }
                } else {
                    // For next page iteration
                    System.out.println("In For next page iteration");
                    new WebScraperScheduler().webScraperAliexpress(url);
                }
            } else {
                System.out.println("No exception in Jsoup");
            }
        }
        return document;
    }

    public static boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
    }

    // Used to set Proxy for every request
    private static void setProxyIpAgent() {
        int randValueForIp = (int) (Math.random() * 1000 - 1);
        System.setProperty("http.proxyHost", ProjectUtils.tempIpAgent[randValueForIp][0]);
        System.setProperty("http.proxyPort", String.valueOf((int) (Math.random() * 1000 + 1024)));
    }

    // Split url for getting the page number of that url
    public static String splitURLForPage(String URL) {
        return URL.split(".html")[0];
    }

    public static Integer splitURLForPageNumber(String urls) {
        String[] url = urls.split(".html")[0].split("/");
        return Integer.valueOf(url[url.length - 1]);
    }

    public static String setTagId(Document document) {
        Elements elements = document.getElementsByClass("sn-parent-title");
        for (Element element1 : elements) {
            String s;
            if (element1.text().contains("<")) {
                s = element1.text().replace("<", "") + " > ";
            } else {
                s = element1.text() + " > ";
            }
            tagId.append(s);
        }
        String s = tagId.toString().trim();
        tagId = new StringBuilder();
        return s.substring(0, s.length() - 2);
    }

    // Add page number and extension to the url
    public static String getUrlEx(int i) {
        return "/" + i + ".html?isFavorite=y&SortType=total_tranpro_desc";
    }

    public static Object splitURLForProduct(String urls) {
        String[] url = urls.split(".html");
        return "https:" + url[0] + ".html";
    }

    // Print the 2d Array
    public static void print(Object[][] data) {
        for (Object[] aData : data) {
            for (Object anAData : aData) {
                System.out.print(anAData + " | ");
            }
            System.out.println();
        }
    }
}