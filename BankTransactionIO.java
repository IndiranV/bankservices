package org.in.com.controller.io;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BankTransactionIO {
	private String code;
	private BigDecimal creditAmount;
	private BigDecimal debitAmount;
	private BigDecimal closingBalance;
	private int activeFlag;
	private String fromDate;
	private String toDate;
}
