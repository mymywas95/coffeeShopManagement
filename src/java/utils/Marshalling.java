/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import DTO.CategoryDTO;
import DTO.MenuDTO;
import DTO.ProductDTO;
import DTO.ProductItemDTO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import jaxb.listcategory.Category;
import jaxb.listcategory.ListCategory;
import jaxb.listcategory.Product;
import jaxb.listcategory.ProductItem;

/**
 *
 * @author MYNVTSE61526
 */
public class Marshalling implements Serializable {

    public Boolean marshalMenuToXMl(List<MenuDTO> listmMenuDTO, String filePath) {
        ListCategory listCategory = new ListCategory();
        for (MenuDTO menuDTO : listmMenuDTO) {
            Category category = new Category();
            category.setId(new BigInteger(menuDTO.getCategoryId() + ""));
            category.setName(menuDTO.getCategoryName());
            for (ProductDTO productDTO : menuDTO.getListProduct()) {
                Product product = new Product();
                product.setId(new BigInteger(productDTO.getId() + ""));
                product.setName(productDTO.getName());
                product.setImgLink(productDTO.getImgLink());
                product.setDescription(productDTO.getDescription());
                product.setMaterial(productDTO.getMaterial());
                for (ProductItemDTO productItemDTO : productDTO.getListProductItemDto()) {
                    ProductItem productItem = new ProductItem();
                    productItem.setCompetitorName(productItemDTO.getCompetitorName());
                    productItem.setPrice(new BigDecimal(productItemDTO.getPrice()+ ""));
                    product.getProductItem().add(productItem);
                }
                category.getProduct().add(product);
            }
            listCategory.getCategory().add(category);
        }
        try {
            JAXBContext contextObj = JAXBContext.newInstance(ListCategory.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            QName qName = new QName("jaxb.listcategory", "listCategory");
            JAXBElement<ListCategory> root = new JAXBElement<>(qName, ListCategory.class, listCategory);
            marshallerObj.marshal(root, new FileOutputStream(filePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
