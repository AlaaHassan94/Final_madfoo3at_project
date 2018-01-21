package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.List;



public class XMLParseBillInquiryResponse implements ParseBillInquiryResponse {
    @Override
    public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {

    	
    	 List<Bill> billsListXML = new ArrayList<Bill>();
    	 Bill billingData= null;
		    
		 	try {
		         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		         InputStream stream = new ByteArrayInputStream(billerResponse.getBytes("UTF-8"));
		        // Document stream = dBuilder.parse(new InputSource(new StringReader(Pasred.VALID_XML_RESPONSE)));
		         Document doc = dBuilder.parse(stream);
		         doc.getDocumentElement().normalize();
		         NodeList nodeList = doc.getElementsByTagName("bill");
	         
			         for (int temp = 0; temp < nodeList.getLength(); temp++) {
			            Node node = nodeList.item(temp);
			            if (node.getNodeType() == Node.ELEMENT_NODE) {
			            	
				               Element element = (Element) node;				               
				               String dueDateValue =null;
				             					       		Date ParseStringToDate = null;
					       		String dueAmountValue ="" ;
					       		BigDecimal bigdecimal =null;
					       		try {
					       			dueDateValue = element.getElementsByTagName("dueDate").item(0).getTextContent();
						       		 ParseStringToDate = new SimpleDateFormat("dd-MM-yyyy").parse(dueDateValue); 					       	
						       		 dueAmountValue = element.getElementsByTagName("dueAmount").item(0).getTextContent();
						       		 bigdecimal = new BigDecimal(dueAmountValue);
					       		} catch (InvalidBillInquiryResponse e) {
					       		    e.printStackTrace();
					       		} catch (ParseException e) {
									e.printStackTrace();
								}catch(Exception e) {
									e.printStackTrace();
								}
	
					       		billingData= new Bill();
					       		billingData.setDueDate(ParseStringToDate);					       						       		
					       		billingData.setDueAmount(bigdecimal);
					       		billingData.setFees(bigdecimal);
	
					       		billsListXML.add(billingData);
					       		
					               
					              
				            } // end cheak node
			                 //
			            
			         } // end for loop
			         	
		       } catch (Exception e) {
					   e.getMessage();
					      }
			 
		 return billsListXML;
    }
}
