/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.CategoryDTO;
import entity.ProductCategory;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import utils.DBUtils;

/**
 *
 * @author MYNVTSE61526
 */
public class CategoryDAO implements Serializable {
     EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");
    public List<CategoryDTO> getAllCategory() {
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery( 
                "SELECT c " +
                "FROM ProductCategory c "); 
        List<ProductCategory> resEnt  = q.getResultList();
        List<CategoryDTO> result = new ArrayList<CategoryDTO>();
        for (ProductCategory cate: resEnt){
            result.add(new CategoryDTO(cate.getId(), cate.getName()));
        }
        return result;
    }
}
