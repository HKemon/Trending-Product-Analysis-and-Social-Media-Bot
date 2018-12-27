package AliexpressProductInfo.dao;

import AliexpressProductInfo.entites.ProductsInfo;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.simple.JSONObject;
import util.HibernateUtil;

import java.util.Date;
import java.util.List;

import static AliexpressProductInfo.util.ProjectUtils.*;


public class ProductsInfoDaoImp implements ProductsInfoDao {
    @Override
    public void insertProductsInfoDao(ProductsInfo productsInfo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(productsInfo);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProductsInfoDao(Object[] product, String tagId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            JSONObject obj = new JSONObject();
            obj.put("d", dateFormat.format(new Date()));  // d - date
            obj.put("r", product[0]);  // r - rank
            obj.put("o", product[5]);  // o - order

            String previous = fetchRanksProductsInfoDao(tagId, (Long) product[2]);
            String ranks = concatOperation(previous, obj);

            String HQL = "update ProductsInfo set ranks_2018 =:ranks where productId=:productId and tagId=:tagId";

            switch (year) {
                case "2019":
                    HQL = "update ProductsInfo set ranks_2019 =:ranks where productId=:productId and tagId=:tagId";
                    break;
                case "2020":
                    HQL = "update ProductsInfo set ranks_2020 =:ranks where productId=:productId and tagId=:tagId";
                    break;
                case "2021":
                    HQL = "update ProductsInfo set ranks_2021 =:ranks where productId=:productId and tagId=:tagId";
                    break;
            }

            Query query = session.createQuery(HQL)
                    .setParameter("ranks", ranks)
                    .setParameter("productId", product[2])
                    .setParameter("tagId", tagId);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    private String concatOperation(String prevoius, JSONObject obj) {
        String pre = prevoius.substring(0, prevoius.lastIndexOf("]"));
        String newVal = "," + obj.toJSONString() + ']';
        return pre + newVal;
    }

    @Override
    public String fetchRanksProductsInfoDao(String tagId, long id) {
        String productsInfo = "";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            String HQL = "select p.ranks_2018 From ProductsInfo p where productId=:productId and tagId=:tagId";

            switch (year) {
                case "2019":
                    HQL = "select p.ranks_2019 From ProductsInfo p where productId=:productId and tagId=:tagId";
                    break;
                case "2020":
                    HQL = "select p.ranks_2020 From ProductsInfo p where productId=:productId and tagId=:tagId";
                    break;
                case "2021":
                    HQL = "select p.ranks_2021 From ProductsInfo p where productId=:productId and tagId=:tagId";
                    break;
            }

            List ret = session.createQuery(HQL)
                    .setParameter("productId", id)
                    .setParameter("tagId", tagId)
                    .getResultList();

            String temp = ret.toString();

            productsInfo = ret.toString().substring(1, temp.length() - 1);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productsInfo;
    }
}