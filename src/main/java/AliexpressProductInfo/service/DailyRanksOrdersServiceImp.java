package AliexpressProductInfo.service;

import AliexpressProductInfo.dao.DailyRanksOrdersDaoImp;
import AliexpressProductInfo.entites.DailyRanksOrders;

public class DailyRanksOrdersServiceImp implements DailyRanksOrdersService {
    @Override
    public void insertDailyRanksOrdersService(DailyRanksOrders dailyRanksOrders) {
        new DailyRanksOrdersDaoImp().insertDailyRanksOrdersDao(dailyRanksOrders);
    }

    @Override
    public Boolean fetchDailyRanksOrdersDao(String tagId, int pageNumber) {
        return  new DailyRanksOrdersDaoImp().fetchDailyRanksOrdersDao(tagId,pageNumber);
    }
}