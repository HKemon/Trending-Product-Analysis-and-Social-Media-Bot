package AliexpressProductInfo.service;

import AliexpressProductInfo.entites.ProductsInfo;

public interface ProductsInfoService {
    void insertProductsInfoService(ProductsInfo productsInfo);
    void updateProductsInfoService(Object[] product, String tagId);
    String fetchRanksProductsInfoDao(String tagId, long pId);
}