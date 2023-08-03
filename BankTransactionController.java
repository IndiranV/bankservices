package org.in.com.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.in.com.controller.io.BankTransactionIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.service.AuthService;
import org.in.com.service.BankTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/{authtoken}/bank/transaction")
@Controller
public class BankTransactionController {

	@Autowired
	private AuthService authService;
	@Autowired
	private BankTransactionService bankTransactionService;

	@RequestMapping(value = "/add/bank/transaction", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BankTransactionIO addTransaction(@PathVariable("authtoken") String authToken, @RequestBody BankTransactionIO bankTransactionIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		bankTransactionDTO.setCode(bankTransactionIO.getCode());
		bankTransactionDTO.setCreditAmount(bankTransactionIO.getCreditAmount());
		bankTransactionDTO.setDebitAmount(bankTransactionIO.getDebitAmount());
		bankTransactionDTO.setClosingBalance(bankTransactionIO.getClosingBalance());
		bankTransactionDTO.setActiveFlag(bankTransactionIO.getActiveFlag());

		bankTransactionService.addTransaction(authDTO, bankTransactionDTO);
		BankTransactionIO bankTransaction = new BankTransactionIO();
		bankTransaction.setCode(bankTransactionDTO.getCode());
		bankTransaction.setActiveFlag(bankTransactionDTO.getActiveFlag());
		return bankTransaction;
	}

	@RequestMapping(value = "/transaction/Report", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<BankTransactionIO> getTransactionReport(@PathVariable("authtoken") String authToken, @RequestBody BankTransactionIO bankTransactionIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		List<BankTransactionIO> bankTransactionIOList = new ArrayList<>();
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		bankTransactionDTO.setFromDate(bankTransactionIO.getFromDate());
		bankTransactionDTO.setToDate(bankTransactionIO.getToDate());
		bankTransactionDTO.setActiveFlag(bankTransactionIO.getActiveFlag());

		List<BankTransactionDTO> bankTransactionDTOList = bankTransactionService.getTransactionReport(authDTO, bankTransactionDTO);
		for (BankTransactionDTO bankTransactionData : bankTransactionDTOList) {
			BankTransactionIO bankTransaction = new BankTransactionIO();
			bankTransaction.setCreditAmount(bankTransactionData.getCreditAmount());
			bankTransaction.setDebitAmount(bankTransactionData.getDebitAmount());
			bankTransaction.setClosingBalance(bankTransactionData.getClosingBalance());
			bankTransaction.setActiveFlag(bankTransactionData.getActiveFlag());
			bankTransactionIOList.add(bankTransaction);
		}
		return bankTransactionIOList;
	}

	@RequestMapping(value = "/update/transaction", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BankTransactionIO updateTransaction(@PathVariable("authtoken") String authToken, @RequestBody BankTransactionIO bankTransactionIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		bankTransactionDTO.setCode(bankTransactionIO.getCode());
		bankTransactionDTO.setCreditAmount(bankTransactionIO.getCreditAmount());
		bankTransactionDTO.setDebitAmount(bankTransactionIO.getDebitAmount());
		bankTransactionDTO.setClosingBalance(bankTransactionIO.getClosingBalance());
		bankTransactionDTO.setActiveFlag(bankTransactionIO.getActiveFlag());
	    bankTransactionService.updateTransaction(authDTO, bankTransactionDTO);

		BankTransactionIO bankTransaction = new BankTransactionIO();
		bankTransaction.setCode(bankTransactionDTO.getCode());
		bankTransaction.setCreditAmount(bankTransactionDTO.getCreditAmount());
		bankTransaction.setDebitAmount(bankTransactionDTO.getDebitAmount());
		bankTransaction.setClosingBalance(bankTransactionDTO.getClosingBalance());
		bankTransaction.setActiveFlag(bankTransactionDTO.getActiveFlag());
		return bankTransaction;
	}

	@RequestMapping(value = "/closing/balance", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public BankTransactionIO getClosingBalance(@PathVariable("authtoken") String authToken) throws ClassNotFoundException, SQLException, Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		CustomerDTO customerDto = new CustomerDTO();
		BankTransactionDTO bankTransactionDTO = bankTransactionService.getClosingBalance(authDTO, customerDto);
		BankTransactionIO bankTransactionIO = new BankTransactionIO();
		bankTransactionIO.setClosingBalance(bankTransactionDTO.getClosingBalance());
		return bankTransactionIO;
	}
}
