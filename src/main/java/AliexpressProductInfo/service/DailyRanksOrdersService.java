package AliexpressProductInfo.service;

import AliexpressProductInfo.entites.DailyRanksOrders;

public interface DailyRanksOrdersService {
    void insertDailyRanksOrdersService(DailyRanksOrders dailyRanksOrders);
    Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber);
}