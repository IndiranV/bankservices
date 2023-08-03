package org.in.com.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BankTransactionDTO {
	private int id;
	private String code;
	private int namespaceId;
	private int customerId;
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private BigDecimal closingBalance;
	private int activeFlag;
	private int updatedBy;
	private String fromDate;
	private String toDate;
}
