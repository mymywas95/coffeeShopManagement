package entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-25T23:04:06")
@StaticMetamodel(ProductItem.class)
public class ProductItem_ { 

    public static volatile SingularAttribute<ProductItem, Integer> id;
    public static volatile SingularAttribute<ProductItem, Double> price;
    public static volatile SingularAttribute<ProductItem, String> description;
    public static volatile SingularAttribute<ProductItem, Integer> competitorId;
    public static volatile SingularAttribute<ProductItem, Integer> productId;

}