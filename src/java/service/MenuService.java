/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import DAO.ProductItemDAO;
import DTO.CategoryDTO;
import DTO.MenuDTO;
import DTO.ProductDTO;
import DTO.ProductItemDTO;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import utils.Marshalling;
import utils.Unmarshalling;

/**
 *
 * @author MYNVTSE61526
 */
public class MenuService implements Serializable {

    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private ProductItemDAO productItemDAO;

    public List<MenuDTO> getAllMenuItem() {
        String filePath = "E:/FPT/XML/project_xml/coffeeShopManagement/listProductMashalled.xml";
        List<MenuDTO> listMenu = new ArrayList<MenuDTO>();
        Unmarshalling unmarshalling = new Unmarshalling();
        File f = new File(filePath);
        if (!f.exists() && !f.isDirectory() && f.length() == 0) {
            categoryDAO = new CategoryDAO();
            productDAO = new ProductDAO();
            productItemDAO = new ProductItemDAO();
            List<CategoryDTO> listCategory = categoryDAO.getAllCategory();
            for (CategoryDTO categoryDTO : listCategory) {
                MenuDTO menuDTO = new MenuDTO();
                List<ProductDTO> listProduct = productDAO.getAllproductByCateId(categoryDTO.getId());
                for (ProductDTO productDTO : listProduct) {
                    List<ProductItemDTO> listProductItemDTO = productItemDAO.getAllPricebyProductId(productDTO.getId());
                    productDTO.setListProductItemDto(listProductItemDTO);
                }
                menuDTO.setListProduct(listProduct);
                menuDTO.setCategoryName(categoryDTO.getName());
                menuDTO.setCategoryId(categoryDTO.getId());
                listMenu.add(menuDTO);
            }
            Marshalling marshalling = new Marshalling();
            boolean smarshalled = marshalling.marshalMenuToXMl(listMenu, filePath);
            if (smarshalled == false) {
                return null;
            }
        }
        List<MenuDTO> listmDTO = unmarshalling.UnMarshalXMLToMenu(filePath);
        if (listmDTO.size() > 0) {
            return listmDTO;
        } else {
            return null;
        }
    }
}
