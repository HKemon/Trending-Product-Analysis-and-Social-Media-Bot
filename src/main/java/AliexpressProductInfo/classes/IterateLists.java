package AliexpressProductInfo.classes;

import AliexpressProductInfo.util.ProjectUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import AliexpressProductInfo.scheduler.WebScraperScheduler;

import java.io.IOException;
import static AliexpressProductInfo.util.ProjectUtils.*;


public class IterateLists {
    // Used to hold html raw file
    private Document document = null;

    // Extract Raw Html file by calling Jsoup function
    public void rawHtml(String url) throws IOException {
        document = ProjectUtils.callJsoup(url);
        categoryList(document.body());
    }

    // Find All Category Subcategory of that url and populate the list
    private void categoryList(Element element) throws IOException {
        Outer:
        for (Element element1 : element.getElementsByClass("category-list  ")) {
            for (Element element2 : element1.getElementsByClass("son-category")) {
                if (!element2.select("ul").text().equals("")) {
                    // Add all categories in the list
                    for (Element element3 : element2.select("ul")) {
                        for (Element e : element3.select("li")) {
                            String link = e.select("li").select("a").attr("href");
                            String url = "https://" + ProjectUtils.splitURLForPage(link.substring(2)) + ProjectUtils.getUrlEx(1);

                            // Add to the list if that page is new and unvisited
                            // Add page number with it also
                            // For fist page pass 1 to getUrlEx method

                            if (!visited.contains(url)) {                        // -----------------> Have to work on it
                                unVisited.add(url);
                            } else System.out.println("This page is already visited");
                        }
                    }
                    break Outer;
                }
            }
        }
        if (!unVisited.isEmpty())
            callURL();
        else System.out.println("Done");
    }

    // Access all data for specific URL
    public void callURL() throws IOException {
        String s = unVisited.poll();    // Take the first url and start operation
        visited.add(s);                 // Mark the url as visited.
        System.out.println("Visiting " + s);
        document = ProjectUtils.callJsoup(s);

        Outer:
        for (Element element1 : document.getElementsByClass("category-list  ")) {
            for (Element element2 : element1.getElementsByClass("son-category")) {
                if (element2.select("ul").text().equals("")) {
                    accessNextPage = true;
                    int i = 1;
                    // Access Next page if the order of last product of that page is greater that ProjectUtils.minimumOrder
                    while (accessNextPage && !stopProgram) {
                        if (s == null) {
                            System.out.println("Provided URL is null");
                            break Outer;
                        } else {
                            String url = splitURLForPage(s);
                            Document document = callJsoup(url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i));
                            if (document == null) {
                                System.out.println("Document is null ok ???");
                                continue;
                            }
                            int x = orderCountForFirstProductOfPage(document.body());
                            if (x != 0) {
                                executeWebScraperAliexpressWithExtension(x, i, url);
                                // increment the page number to execute next page
                                if (accessNextPage) {
                                    i++;
                                    System.out.println("Value of i " + i);
                                }
                            }
                        }
                    }
                    break Outer;
                }
            }
        }
        // Find all subcategory of that page
        categoryList(document);
    }

    // Return the order of first product of that page
    private int orderCountForFirstProductOfPage(Element document) {
        String value = document.getElementsByClass("order-num-a ").text();
        int x = 0;
        if (value.equals("")) {
            ProjectUtils.accessNextPage = false;
        } else {
            x = Integer.parseInt(value.substring(value.indexOf("(") + 1, value.indexOf(")")));
            System.out.println("First Order Value " + x);
        }
        return x;
    }

//    // Return the order of first product of that page
//    public int orderCountForLastProductOfPage(Element document) {
//        String value = document.getElementsByClass("order-num-a ").text();
//        int x = 0;
//        if (value.equals("")) {
//            ProjectUtils.accessNextPage = false;
//        } else {
//            System.out.println("Value " + value);
//            x = Integer.parseInt(value.substring(value.indexOf("(") + 1, value.indexOf(")")));
//            System.out.println("X " + x);
//        }
//        return x;
//    }

    // Execute that page if the order of the first product is grater than minimumOrder
    // Otherwise do not need to execute that and next page
    private void executeWebScraperAliexpressWithExtension(int x, int i, String url) {
        if (x > minimumOrder) {
            new WebScraperScheduler()
                    .webScraperAliexpress(url.substring(0, url.length() - 2) + ProjectUtils.getUrlEx(i));
        } else {
            accessNextPage = false; // To stop executing the next page
        }
    }
}