package AliexpressProductInfo.dao;

import AliexpressProductInfo.entites.DailyRanksOrders;

public interface DailyRanksOrdersDao {
    void insertDailyRanksOrdersDao(DailyRanksOrders dailyRanksOrders);
    Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber);
}