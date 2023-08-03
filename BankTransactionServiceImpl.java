package org.in.com.serviceimpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import org.in.com.dao.BankTransactionDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.BankTransactionService;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionServiceImpl implements BankTransactionService {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BankTransactionDAO bankTransactionDao;

	@Override
	public BankTransactionDTO addTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO) throws ClassNotFoundException, SQLException {

		UserDTO userDTO = new UserDTO();
		userDTO.setId(authDTO.getUser().getId());
		CustomerDTO customerDTO = customerService.getCustomerId(userDTO);

		return bankTransactionDao.addTransaction(authDTO, bankTransactionDTO, customerDTO);
	}

	@Override
	public List<BankTransactionDTO> getTransactionReport(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO) throws ClassNotFoundException, SQLException {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(authDTO.getUser().getId());
		CustomerDTO customerDTO = customerService.getCustomerId(userDTO);

		return bankTransactionDao.getTransactionReport(authDTO, bankTransactionDTO, customerDTO);
	}

	@Override
	public BankTransactionDTO updateTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO) throws ClassNotFoundException, SQLException {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(authDTO.getUser().getId());
		CustomerDTO customerDTO = customerService.getCustomerId(userDTO);

		BankTransactionDTO bankTransaction = bankTransactionDao.getCustomerTransaction(customerDTO);
		BigDecimal balance = BigDecimal.ZERO;

		if (bankTransactionDTO.getCreditAmount() != null && bankTransactionDTO.getCreditAmount().compareTo(BigDecimal.ZERO) != 0) {
			balance = bankTransaction.getClosingBalance().add(bankTransactionDTO.getCreditAmount());
		}
		else {
			balance = bankTransaction.getClosingBalance().subtract(balance = bankTransactionDTO.getDebitAmount());
		}
		bankTransactionDTO.setClosingBalance(balance);
		return bankTransactionDao.transaction(authDTO, bankTransactionDTO, customerDTO);
	}

	@Override
	public BankTransactionDTO getClosingBalance(AuthDTO authDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(authDTO.getUser().getId());
		customerDTO = customerService.getCustomerId(userDTO);

		return bankTransactionDao.getCustomerTransaction(customerDTO);
	}

}
