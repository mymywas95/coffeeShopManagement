package entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-25T23:04:06")
@StaticMetamodel(Bill.class)
public class Bill_ { 

    public static volatile SingularAttribute<Bill, Integer> id;
    public static volatile SingularAttribute<Bill, Double> total;
    public static volatile SingularAttribute<Bill, Date> paymentDate;
    public static volatile SingularAttribute<Bill, String> tableName;
    public static volatile SingularAttribute<Bill, Integer> promotion;
    public static volatile SingularAttribute<Bill, String> note;

}