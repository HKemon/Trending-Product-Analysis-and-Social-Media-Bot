package AliexpressProductInfo.scheduler;

import AliexpressProductInfo.classes.ArrayToEntityObjects;
import AliexpressProductInfo.excel.ReadDataFromExcel;
import AliexpressProductInfo.util.ProjectUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import static AliexpressProductInfo.util.ProjectUtils.*;

public class WebScraperScheduler implements Job {
    private ReadDataFromExcel readDataFromExcel = new ReadDataFromExcel();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (!stopProgram) {
            // Populate Ip Address and Agent 2D array from AliexpressProductInfo.excel file
            readDataFromExcel.readDataForIpAgent();
            // Read url from AliexpressProductInfo.excel file and check it that page is eligible to
            // execute on certain conditions
            readDataFromExcel.readDataForDatabase();
        } else System.out.println("You Stopped the Program");
    }

    public void webScraperAliexpress(String url) {
        if (!stopProgram) {
            Document document = ProjectUtils.callJsoup(url);
            if (document != null) {
                String tagId = ProjectUtils.setTagId(document);
                int pageNumber = ProjectUtils.splitURLForPageNumber(url);
                Elements picture = document.getElementsByClass("picRind ");
                Elements productId = document.getElementsByClass("atc-product-id");

                Elements productName = document.getElementsByClass("icon-hotproduct");
                Elements orderNumbers = document.getElementsByClass("order-num-a ");

                data = arraySize(orderNumbers);
                populateDataArray(picture, productId, productName, orderNumbers, pageNumber);
                new ArrayToEntityObjects(tagId, url, pageNumber).iterate();
            }
        } else System.out.println("You Stopped the Program 2");
    }

    private void populateDataArray(Elements picture,
                                   Elements productId, Elements productName,
                                   Elements orderNumbers, int pageNumber) {

        int i = 0;
        for (Element e : orderNumbers) {
            int x = Integer.parseInt(e.text().substring(e.text().indexOf("(") + 1, e.text().lastIndexOf(")")));
            if (x < ProjectUtils.minimumOrder) {
                ProjectUtils.accessNextPage = false;
                System.out.println("Stop Executing Next Page");
                break;
            }
            i++;
        }
        System.out.println("I " + i);

        data = new Object[i][6];

        int rank = (pageNumber - 1) * data.length + 1;
        int index = 0;

        for (Element e : picture) {
            if (index < i) {
                data[index][0] = rank;
                data[index][1] = ProjectUtils.splitURLForProduct(e.attr("href"));
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : productId) {
            if (index < i) {
                data[index][2] = Long.parseLong(e.attr("value"));
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : productName) {
            if (index < i && !e.getElementsByClass("product ").isEmpty()) {
                data[index][3] = e.text();
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : picture.select("img")) {
            if (index < i) {
                if (!e.attr("src").equals("")) {
                    data[index][4] = e.attr("src");
                } else {
                    data[index][4] = e.attr("image-src");
                }
                index++;
                rank++;
            } else break;
        }
        rank = (pageNumber - 1) * data.length + 1;
        index = 0;

        for (Element e : orderNumbers) {
            if (index < i) {
                String value = e.text();
                data[index][5] = Integer.parseInt(value.substring(value.indexOf("(") + 1, value.lastIndexOf(")")));
                index++;
                rank++;
            } else break;
        }
    }

    private Object[][] arraySize(Elements orderNumbers) {
        int i = 0;
        for (Element e : orderNumbers) {
            int x = Integer.parseInt(e.text().substring(e.text().indexOf("(") + 1, e.text().lastIndexOf(")")));
            if (x < ProjectUtils.minimumOrder) {
                break;
            }
            i++;
        }
        return new Object[i][6];
    }
}