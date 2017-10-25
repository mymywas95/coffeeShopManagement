/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author MYNVTSE61526
 */
public class IndexService implements Serializable {

    public int checkDataBeforeLoad() {
        try {
            File orderUnsaveFile = new File(ManageConstantService.orderUnSavedFilePath);
            if (orderUnsaveFile.exists() == true) {
                insertorderUnsaveFileDatetoDB(ManageConstantService.orderUnSavedFilePath);
            }
            File productListFile = new File(ManageConstantService.listProductMashalled);
            if (productListFile.exists() == false) {
               return 0;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 2;

        }
    }

    public void insertorderUnsaveFileDatetoDB(String filePath) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
