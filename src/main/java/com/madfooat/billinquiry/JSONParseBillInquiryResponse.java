package com.madfooat.billinquiry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSONParseBillInquiryResponse implements ParseBillInquiryResponse {
	@Override
	public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {
		
	
		List<Bill> billsListJSON = new ArrayList<Bill>();		
	     List<Bill> receivedData = null;
		//List<Bill> receivedData = new ArrayList<Bill>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		objectMapper.setDateFormat(format);

		try {
			receivedData = objectMapper.readValue(billerResponse,objectMapper.getTypeFactory().constructCollectionType(List.class, Bill.class));
		} catch (InvalidBillInquiryResponse e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
			 
			 Date dueDate;
			 
			 for (int i = 0; i < receivedData.size(); i++) {
				 
					 dueDate = receivedData.get(i).getDueDate();
			       		
						BigDecimal dueAmount = receivedData.get(i).getDueAmount();
						Bill billingData= new Bill();
			       		billingData.setDueDate(dueDate);
			       		billingData.setDueAmount(dueAmount);
			       		
			       		
			       		BigDecimal Fees = receivedData.get(i).getFees()	;
			       		
			       		
			       		
			       		billsListJSON.add(billingData);
			       		
			 }//end loop
	return billsListJSON;
	 
	} //end function
}
