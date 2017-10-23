/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import DTO.MenuDTO;
import DTO.ProductDTO;
import DTO.ProductItemDTO;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import jaxb.listcategory.Category;
import jaxb.listcategory.ListCategory;
import jaxb.listcategory.Product;
import jaxb.listcategory.ProductItem;

/**
 *
 * @author MYNVTSE61526
 */
public class Unmarshalling implements Serializable {

    public List<MenuDTO> UnMarshalXMLToMenu(String filepath) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(ListCategory.class);
            Unmarshaller unmarshallerObj = contextObj.createUnmarshaller();
            File f = new File(filepath);
            JAXBElement<ListCategory> root = unmarshallerObj.unmarshal(new StreamSource(
                    f), ListCategory.class);
            ListCategory listCategory = root.getValue();
            List<MenuDTO> listmDTO = new ArrayList<MenuDTO>();
            for (Category category : listCategory.getCategory()) {
                MenuDTO menuDTO = new MenuDTO();
                menuDTO.setCategoryId(Integer.parseInt(category.getId() + ""));
                menuDTO.setCategoryName(category.getName());
                List<ProductDTO> list = new ArrayList<>();
                for (Product product : category.getProduct()) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(Integer.parseInt(product.getId() + ""));
                    productDTO.setName(product.getName() + "");
                    if (product.getImgLink() == null) {
                        productDTO.setImgLink("updating");
                    } else {
                        productDTO.setImgLink(product.getImgLink());
                    }
                    if (product.getMaterial() == null) {
                        productDTO.setMaterial("updating");
                    } else {
                        productDTO.setMaterial(product.getMaterial());
                    }
                    if (product.getDescription() == null) {
                        productDTO.setDescription("updating");
                    } else {
                        productDTO.setDescription(product.getDescription());
                    }
                    List<ProductItemDTO> listproductItemDTO = new ArrayList<ProductItemDTO>();
                    for (ProductItem productItem : product.getProductItem()) {
                        ProductItemDTO productItemDTO = new ProductItemDTO();
                        productItemDTO.setCompetitorName(productItem.getCompetitorName());
                        productItemDTO.setPrice(Double.parseDouble(productItem.getPrice() + ""));
                        listproductItemDTO.add(productItemDTO);
                    }
                    productDTO.setListProductItemDto(listproductItemDTO);
                    list.add(productDTO);
                }
                menuDTO.setListProduct(list);
                listmDTO.add(menuDTO);
            }
            return listmDTO;
        } catch (JAXBException ex) {
            Logger.getLogger(Unmarshalling.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception e) {
            Logger.getLogger(Unmarshalling.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
