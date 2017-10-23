/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MYNVTSE61526
 */
@Entity
@Table(name = "Bill_Item", catalog = "CoffeeShopDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BillItem.findAll", query = "SELECT b FROM BillItem b"),
    @NamedQuery(name = "BillItem.findById", query = "SELECT b FROM BillItem b WHERE b.id = :id"),
    @NamedQuery(name = "BillItem.findByBillId", query = "SELECT b FROM BillItem b WHERE b.billId = :billId"),
    @NamedQuery(name = "BillItem.findByProductId", query = "SELECT b FROM BillItem b WHERE b.productId = :productId")})
public class BillItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "billId", nullable = false)
    private int billId;
    @Basic(optional = false)
    @Column(name = "productId", nullable = false)
    private int productId;

    public BillItem() {
    }

    public BillItem(Integer id) {
        this.id = id;
    }

    public BillItem(Integer id, int billId, int productId) {
        this.id = id;
        this.billId = billId;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillItem)) {
            return false;
        }
        BillItem other = (BillItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BillItem[ id=" + id + " ]";
    }
    
}
