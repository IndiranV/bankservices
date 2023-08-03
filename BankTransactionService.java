package org.in.com.service;

import java.sql.SQLException;
import java.util.List;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;

public interface BankTransactionService {

	public BankTransactionDTO addTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO ) throws ClassNotFoundException, SQLException;

	public List<BankTransactionDTO> getTransactionReport(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO ) throws ClassNotFoundException, SQLException;

	public BankTransactionDTO updateTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO) throws ClassNotFoundException, SQLException;

	public BankTransactionDTO getClosingBalance(AuthDTO authDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException;

}
