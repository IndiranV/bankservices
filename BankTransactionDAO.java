package org.in.com.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.BankTransactionDTO;
import org.in.com.dto.CustomerDTO;
import org.springframework.stereotype.Repository;
import lombok.Cleanup;

@Repository
public class BankTransactionDAO {
	public BankTransactionDTO addTransaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		String alphabet = "cust123";
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i <= 5; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			stringBuilder.append(randomChar);
		}
		String code = stringBuilder.toString();
		bankTransactionDTO.setCode(code);
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "INSERT INTO bank_transaction(code, namespace_id, customer_id, credit_amount, debit_amount, closing_balance, active_flag, updated_by, updated_at) VALUES(?,?,?,?,?, ?,1,?,now())";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, bankTransactionDTO.getCode());
		preparedStatement.setInt(2, authDTO.getNamespace().getId());
		preparedStatement.setInt(3, customerDTO.getId());
		preparedStatement.setBigDecimal(4, bankTransactionDTO.getCreditAmount());
		preparedStatement.setBigDecimal(5, bankTransactionDTO.getDebitAmount());
		preparedStatement.setBigDecimal(6, bankTransactionDTO.getClosingBalance());
		preparedStatement.setInt(7, authDTO.getUser().getId());
		preparedStatement.executeUpdate();
		return bankTransactionDTO;
	}

	public List<BankTransactionDTO> getTransactionReport(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		List<BankTransactionDTO> bankTransactionList = new ArrayList<>();
		try {
			@Cleanup
			Connection connection = DaoConnection.connection();
			int pindex = 0;
			@Cleanup
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_BANK_TRANSACTION_IUD(?,?,?,?,? ,?)}");
			callableStatement.setInt(++pindex, customerDTO.getId());
			callableStatement.setString(++pindex, bankTransactionDTO.getFromDate());
			callableStatement.setString(++pindex, bankTransactionDTO.getToDate());
			callableStatement.setInt(++pindex, bankTransactionDTO.getActiveFlag());
			callableStatement.setInt(++pindex, 0);
			callableStatement.registerOutParameter(++pindex, Types.INTEGER);
			callableStatement.execute();
			@Cleanup
			ResultSet resultSet = callableStatement.getResultSet();
			while (resultSet.next()) {
				BankTransactionDTO dto = new BankTransactionDTO();
				dto.setCreditAmount(resultSet.getBigDecimal("credit_amount"));
				dto.setDebitAmount(resultSet.getBigDecimal("debit_amount"));
				dto.setClosingBalance(resultSet.getBigDecimal("closing_balance"));
				dto.setActiveFlag(resultSet.getInt("active_flag"));
				bankTransactionList.add(dto);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return bankTransactionList;
	}

	public BankTransactionDTO getCustomerTransaction(CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		BankTransactionDTO bankTransactionDTO = new BankTransactionDTO();
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT MAX(id) AS id FROM bank_transaction WHERE customer_id = ? AND active_flag = 1";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, customerDTO.getId());
		ResultSet resultSet = preparedStatement.executeQuery();
		int maxId = 0;
		if (resultSet.next()) {
			maxId = resultSet.getInt("id");
		}
		String newQuery = "SELECT closing_balance FROM bank_transaction WHERE id=?";
		@Cleanup
		PreparedStatement preparedStatement1 = connection.prepareStatement(newQuery);
		preparedStatement1.setInt(1, maxId);
		ResultSet resultSet1 = preparedStatement1.executeQuery();
		if (resultSet1.next()) {
			bankTransactionDTO.setClosingBalance(resultSet1.getBigDecimal("closing_balance"));
		}
		return bankTransactionDTO;
	}

	public BankTransactionDTO transaction(AuthDTO authDTO, BankTransactionDTO bankTransactionDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		String alphabet = "cust123";
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i <= 5; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			stringBuilder.append(randomChar);
		}
		String code = stringBuilder.toString();
		bankTransactionDTO.setCode(code);
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "INSERT INTO bank_transaction (code, namespace_id, customer_id, credit_amount, debit_amount, closing_balance, active_flag, updated_by, updated_at) VALUES(?,?,?,?,?, ?,1,?,now())";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, bankTransactionDTO.getCode());
		preparedStatement.setInt(2, authDTO.getNamespace().getId());
		preparedStatement.setInt(3, customerDTO.getId());
		preparedStatement.setBigDecimal(4, bankTransactionDTO.getCreditAmount());
		preparedStatement.setBigDecimal(5, bankTransactionDTO.getDebitAmount());
		preparedStatement.setBigDecimal(6, bankTransactionDTO.getClosingBalance());
		preparedStatement.setInt(7, authDTO.getUser().getId());
		preparedStatement.executeUpdate();
		return bankTransactionDTO;
	}

}
