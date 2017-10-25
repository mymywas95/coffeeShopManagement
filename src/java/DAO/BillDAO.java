/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.OrderDTO;
import entity.Bill;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author MYNVTSE61526
 */
public class BillDAO implements Serializable {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("coffeeShopManagementPU");
    public int addNewBill(Bill bill) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(bill);
            em.getTransaction().commit();
            return bill.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }

    }

   
}
