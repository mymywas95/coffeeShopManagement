/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import DAO.BillDAO;
import DAO.BillItemDAO;
import DTO.BillItemDTO;
import DTO.OrderDTO;
import com.sun.jmx.remote.internal.Unmarshal;
import entity.Bill;
import entity.BillItem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jaxb.listorders.ListOrders;
import jaxb.listorders.Order;
import jaxb.listorders.OrderItem;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author MYNVTSE61526
 */
public class OrderService implements Serializable {

    private BillDAO billDAO;
    private BillItemDAO billItemDAO;
    public Boolean createNewBill(OrderDTO orderDTO) {
        billDAO = new BillDAO();
        billItemDAO = new BillItemDAO();
        Bill bill = convertBillDTOToEntity(orderDTO);
        int billId = billDAO.addNewBill(bill);
        boolean billItemAllCreated = true;
        if (billId > 0) {
            for (BillItemDTO billItemDTO : orderDTO.getListBillItemDTO()) {
                BillItem billItem = convertBillItemDTOToEntity(billItemDTO);
                billItem.setBillId(billId);
                boolean billItemCreated = billItemDAO.addNewBillItem(billItem);
                if (billItemCreated == false) {
                    return false;
                }
            }
        } else {
             return insertOrderItemNodebyStax(orderDTO);
        }
        return true;
    }

    public Bill convertBillDTOToEntity(OrderDTO orderDTO) {
        Bill bill = new Bill();
        bill.setNote(orderDTO.getNote());
        Date paymentDateConverted = convertDateToSQLDate(orderDTO.getPaymentDate());
        bill.setPaymentDate(paymentDateConverted);
        bill.setPromotion(orderDTO.getPromotion());
        bill.setTableName("Bàn" + orderDTO.getTableName());
        bill.setTotal(orderDTO.getTotal());
        return bill;
    }

    public BillItem convertBillItemDTOToEntity(BillItemDTO billItemDTO) {
        BillItem billItem = new BillItem();
        billItem.setBillId(billItemDTO.getBillId());
        billItem.setProductId(billItemDTO.getProductId());
        billItem.setQuantity(billItemDTO.getQuantity());
        return billItem;
    }

    public Date convertDateToSQLDate(java.util.Date date) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            java.sql.Date sql = new java.sql.Date(date.getTime());
            return sql;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public OrderDTO convertJsonToDTO(JSONObject jo) {
        try {
            OrderDTO orderDTO = new OrderDTO();
            java.util.Date myDate = new java.util.Date();
            SimpleDateFormat sm = new SimpleDateFormat("mm-dd-yyyy");
            String strDate = sm.format(myDate);
            java.util.Date dt = sm.parse(strDate);
            orderDTO.setTableName(jo.getString("tableId"));
            orderDTO.setTotal(jo.getInt("tableTotal"));
            orderDTO.setPaymentDate(dt);
            orderDTO.setNote("");
            orderDTO.setPromotion(0);

            List<BillItemDTO> listBillItemDTO = new ArrayList<BillItemDTO>();
            for (int i = 0; i < jo.getJSONArray("productList").length(); i++) {
                BillItemDTO billItemDTO = new BillItemDTO();
                JSONObject productList = jo.getJSONArray("productList").getJSONObject(i);
                billItemDTO.setBillId(0);
                billItemDTO.setProductId(productList.getInt("id"));
                billItemDTO.setQuantity(productList.getInt("quantity"));
                listBillItemDTO.add(billItemDTO);
            }
            orderDTO.setListBillItemDTO(listBillItemDTO);
            return orderDTO;
        } catch (ParseException ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Boolean insertOrderItemNodebyStax(OrderDTO orderDTO) {
        InputStream is = null;
        OutputStream os = null;
        XMLEventReader reader = null;
        XMLEventWriter writer = null;
        try {
            File f = new File(ManageConstantService.orderUnSavedFilePath);
            if (!f.exists()) {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                // root elements
                Document doc = docBuilder.newDocument();
                Element rootElement = doc.createElement("orders");
                doc.appendChild(rootElement);
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(ManageConstantService.orderUnSavedFilePath));
                transformer.transform(source, result);

            }
            XMLInputFactory xif = XMLInputFactory.newInstance();
            is = new FileInputStream(ManageConstantService.orderUnSavedFilePath);
            reader = xif.createXMLEventReader(is);
            XMLOutputFactory xof = XMLOutputFactory.newInstance();
            os = new FileOutputStream(ManageConstantService.orderUnSavedFilePath + ".new");
            writer = xof.createXMLEventWriter(os);
            JAXBContext jaxb = JAXBContext.newInstance(OrderDTO.class);
            Unmarshaller unmarshall = jaxb.createUnmarshaller();
            Marshaller marshall = jaxb.createMarshaller();
            marshall.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshall.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            QName qName = new QName("jaxb.orderItem", "orderItem");
            JAXBElement<OrderDTO> root = new JAXBElement<>(qName, OrderDTO.class, orderDTO);

            while (reader.hasNext()) {

                if (reader.peek().isEndElement() && reader.peek().asEndElement().getName().getLocalPart().equals("orders")) {
                    marshall.marshal(root, writer);
                    writer.add(reader.nextEvent());
                } else {
//                    marshall.marshal(OrderDTO, writer);
                    writer.add(reader.nextEvent());
                }

            }
            writer.flush();
            writer.close();
            is.close();
            os.close();
            File file = new File(ManageConstantService.orderUnSavedFilePath);
            file.delete();
            file = null;
            file = new File(ManageConstantService.orderUnSavedFilePath + ".new");
            file.renameTo(new File(ManageConstantService.orderUnSavedFilePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException ex) {
                    Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (XMLStreamException ex) {
                    Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public JAXBContext marshalOrderToXMl(OrderDTO orderDTO, String filePath) {
        ListOrders listOrders = new ListOrders();
        Order order = new Order();
        order.setNote(orderDTO.getNote());
        order.setPaymentDate(orderDTO.getPaymentDate().toString());
        order.setPromotion(new BigInteger(orderDTO.getPromotion() + ""));
        order.setTableName(orderDTO.getTableName());
        order.setTotal(new BigDecimal(orderDTO.getTotal() + ""));
        for (BillItemDTO billItemDTO : orderDTO.getListBillItemDTO()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBillId(new BigInteger(billItemDTO.getBillId() + ""));
            orderItem.setProductId(new BigInteger(billItemDTO.getProductId() + ""));
            orderItem.setQuantity(new BigInteger(billItemDTO.getQuantity() + ""));
            order.getOrderItem().add(orderItem);
        }
        listOrders.getOrder().add(order);
        try {
            JAXBContext contextObj = JAXBContext.newInstance(ListOrders.class);
//            Marshaller marshallerObj = contextObj.createMarshaller();
//            marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            QName qName = new QName("jaxb.listorders", "listOrders");
//            JAXBElement<ListOrders> root = new JAXBElement<>(qName, ListOrders.class, listOrders);
//            marshallerObj.marshal(root, new FileOutputStream(filePath));
            return contextObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}