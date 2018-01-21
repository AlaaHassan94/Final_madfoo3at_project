package com.madfooat.billinquiry.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

public class Bill {

	private Date dueDate;
	private BigDecimal dueAmount;
	private BigDecimal fees;

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		if (dueDate == null) {
			throw new InvalidBillInquiryResponse("ER: dueDate Value Not Found :(");

		}
		Date current = new Date();
		if (dueDate.after(current)) {
			throw new Error("ER: Invalid dueDate Date ,date Should not be future date :(");
		}
		this.dueDate = dueDate;
	}

	public BigDecimal getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(BigDecimal dueAmount) {
		if (dueAmount == null) {
			throw new InvalidBillInquiryResponse("ER: dueAmount Value Not Found :(");

		}
		String[] splitterAmount = dueAmount.toString().split("\\.");
		if (splitterAmount[0].length() > 3 || splitterAmount[1].length() > 3) {

			throw new Error("ER :Invalid dueAmount format :(");
		}

		this.dueAmount = dueAmount;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		
		if (fees != null) {
			String[] splitterFees = fees.toString().split("\\.");
			if (splitterFees[0].length() > 3 || splitterFees[1].length() > 3) {
				throw new InvalidBillInquiryResponse("ER :Invalid Fees format :(");
			} else if (dueAmount.doubleValue() < fees.doubleValue()) {

				throw new InvalidBillInquiryResponse("ER :Invalid Fees Value,it's greater than dueAmount :(");
			}

		}
		this.fees = fees;
	}

	@Override
	public String toString() {
		return "Bill{" + "dueDate=" + dueDate + ", dueAmount=" + dueAmount + ", fees=" + fees + '}';
	}
}
