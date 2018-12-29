package AliexpressProductInfo.classes;

import AliexpressProductInfo.entites.DailyRanksOrders;
import AliexpressProductInfo.entites.ProductsInfo;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import AliexpressProductInfo.service.DailyRanksOrdersServiceImp;
import AliexpressProductInfo.service.ProductsInfoServiceImp;

import java.util.Date;

import static AliexpressProductInfo.util.ProjectUtils.*;

public class ArrayToEntityObjects {
    private DailyRanksOrders dailyRanksOrders = new DailyRanksOrders();
    private String tagId;
    private String url;
    private int pageNum;

    public ArrayToEntityObjects(String tagId, String url, int pageNum) {
        this.tagId = tagId;
        this.url = url;
        this.pageNum = pageNum;
    }

    public void iterate() {
        System.out.println("Thread.currentThread().getName() " + Thread.currentThread().getName());
        JSONArray arrayRankOrder = new JSONArray();
        String ranks = "";
        boolean arrayListReturn = fetchDailyRanksOrdersDao(tagId, pageNum);
        System.out.println(tagId + " " + pageNum + " arrayListReturn " + arrayListReturn);
        if (arrayListReturn) {
            for (Object[] aData : data) {
                JSONObject obj = null;
                boolean insertInOrderRank = true;
                if (aData[0] != null) {
                    obj = new JSONObject();
                    for (int j = 0; j < aData.length; j++) {
                        if (j == 0)
                            obj.put("r", aData[j]);  // r - rank
                        else if (j == 2) {
                            obj.put("p", aData[j]);  // p - productId
                            ranks = fetchObjectInProductsInfo(tagId, (Long) aData[j]);
                        } else if (j == 5) {
                            obj.put("o", aData[j]);  // o - order
                            if ((int) aData[j] < minimumOrder) {
                                accessNextPage = false;
                            }
                        }
                    }

                    if (ranks.equals("")) {
                        if ((int) aData[5] > minimumOrder) {
                            insertObjectInProductsInfo(aData, tagId);
                        } else {
                            insertInOrderRank = false;
                            accessNextPage = false;
                        }
                    } else {
                        try {
                            JSONArray jsonArr = (JSONArray) jsonParser.parse(ranks);
                            JSONObject jsonObj = (JSONObject) jsonArr.get(jsonArr.size() - 1);
                            if (!jsonObj.get("d").equals(yearMonthDate)) {
                                updateObjectInProductsInfo(aData, tagId);
                            } else {
                                System.out.println("This Product is Already in Database !!!! YOOOOO");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (insertInOrderRank)
                    arrayRankOrder.add(obj);
            }
            if (!arrayRankOrder.isEmpty())
                insertObjectInDailyRanksOrders(arrayRankOrder, tagId, url, pageNum);
            else System.out.println("arrayRankOrder is empty");  // work on it
        }
    }

    private void updateObjectInProductsInfo(Object[] product, String tagId) {
        new ProductsInfoServiceImp().updateProductsInfoService(product, tagId);
    }

    private void insertObjectInProductsInfo(Object[] product, String tagId) {
        JSONArray arrayProductInfo = new JSONArray();
        ProductsInfo productsInfo = new ProductsInfo();

        productsInfo.setTagId(tagId);
        productsInfo.setProductURL((String) product[1]);
        productsInfo.setProductId((Long) product[2]);
        productsInfo.setProductName((String) product[3]);
        productsInfo.setProductImage((String) product[4]);

        productsInfo.setCreatedDate(dateFormat.format(new Date()));

        String year = dateFormat.format(new Date()).split("-")[0];
        JSONObject obj = new JSONObject();
        obj.put("d", dateFormat.format(new Date()));   // d - date
        obj.put("r", product[0]);   // r -rank
        obj.put("o", product[5]);   // r -rank
        arrayProductInfo.add(obj);

        switch (year) {
            case "2018":
                productsInfo.setRanks_2018(arrayProductInfo.toJSONString());
                break;
            case "2019":
                productsInfo.setRanks_2019(arrayProductInfo.toJSONString());
                break;
            case "2020":
                productsInfo.setRanks_2020(arrayProductInfo.toJSONString());
                break;
            case "2021":
                productsInfo.setRanks_2021(arrayProductInfo.toJSONString());
                break;
        }
        new ProductsInfoServiceImp().insertProductsInfoService(productsInfo);
    }

    private String fetchObjectInProductsInfo(String tagId, long id) {
        return new ProductsInfoServiceImp().fetchRanksProductsInfoDao(tagId, id);
    }

    private Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber) {
        return new DailyRanksOrdersServiceImp().fetchDailyRanksOrdersDao(tagId, pageNumber);
    }

    private void insertObjectInDailyRanksOrders(JSONArray arrayRankOrder, String tagId, String url, int pageNum) {
        dailyRanksOrders.setTagId(tagId);
        dailyRanksOrders.setPageURL(url);
        dailyRanksOrders.setDates(dateFormat.format(new Date()));
        dailyRanksOrders.setPageNumber(pageNum);
        dailyRanksOrders.setRankOrder(arrayRankOrder.toJSONString());
        new DailyRanksOrdersServiceImp().insertDailyRanksOrdersService(dailyRanksOrders);
    }
//    public void iterateArray(String tagId, String url, int pageNum) {
//        JSONArray arrayRankOrder = new JSONArray();
//        String ranks = "";
//        boolean arrayListReturn = fetchDailyRanksOrdersDao(tagId, pageNum);
//        System.out.println(tagId + " " + pageNum + " arrayListReturn " + arrayListReturn);
//        if (arrayListReturn) {
////            System.out.println("iterateArray arrayListReturn");
////            print(data);
//            for (Object[] aData : data) {
//                JSONObject obj = null;
//                boolean insertInOrderRank = true;
//                if (aData[0] != null) {
//                    obj = new JSONObject();
//                    for (int j = 0; j < aData.length; j++) {
//                        if (j == 0)
//                            obj.put("r", aData[j]);  // r - rank
//                        else if (j == 2) {
//                            obj.put("p", aData[j]);  // p - productId
//                            ranks = fetchObjectInProductsInfo(tagId, (Long) aData[j]);
//                        } else if (j == 5) {
//                            obj.put("o", aData[j]);  // o - order
//                            if ((int) aData[j] < minimumOrder) {
//                                accessNextPage = false;
//                            }
//                        }
//                    }
//
//                    if (ranks.equals("")) {
//                        if ((int) aData[5] > minimumOrder) {
//                            insertObjectInProductsInfo(aData, tagId);
//                        } else {
//                            insertInOrderRank = false;
//                            accessNextPage = false;
//                        }
//                    } else {
//                        try {
//                            JSONArray jsonArr = (JSONArray) parser.parse(ranks);
//                            JSONObject jsonObj = (JSONObject) jsonArr.get(jsonArr.size() - 1);
//                            if (!jsonObj.get("d").equals(yearMonthDate)) {
//                                updateObjectInProductsInfo(aData, tagId);
//                            } else {
//                                System.out.println("This Product is Already in Database !!!! YOOOOO");
//                            }
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                if (insertInOrderRank)
//                    arrayRankOrder.add(obj);
//            }
//            if (!arrayRankOrder.isEmpty())
//                insertObjectInDailyRanksOrders(arrayRankOrder, tagId, url, pageNum);
//
////            Visiting https://www.aliexpress.com/category/200005171/snowboarding-jackets/1.html?isFavorite=y&SortType=total_tranpro_desc
////            URL https://www.aliexpress.com/category/200005171/snowboarding-jackets/1.html?isFavorite=y&SortType=total_tranpro_desc
////            Connection timed out: connect https://www.aliexpress.com/category/200005171/snowboarding-jackets/1.html?isFavorite=y&SortType=total_tranpro_desc
////            In For next page iteration
////            URL https://www.aliexpress.com/category/200005171/snowboarding-jackets/1.html?isFavorite=y&SortType=total_tranpro_desc
////            No exception in Jsoup
////            Set tag id
////                    splitURLForPageNumber
////            Stop Executing Next Page
////            I 0
////            Sports & Entertainment > Skiing & Snowboarding > Snowboarding Jackets 1 arrayListReturn true
////            arrayRankOrder is empty
//
//            else System.out.println("arrayRankOrder is empty");  // work on it
//        }
//    }

}