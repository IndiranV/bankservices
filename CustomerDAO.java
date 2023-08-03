package org.in.com.dao;

import java.util.ArrayList;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Random;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.UserDTO;
import org.springframework.stereotype.Repository;
import lombok.Cleanup;

@Repository
public class CustomerDAO {
	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		try {
			@Cleanup
			Connection connection = DaoConnection.connection();
			int pindex = 0;
			String alphabet = "cust123";
			StringBuilder stringBuilder = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i <= 5; i++) {
				int index = random.nextInt(alphabet.length());
				char randomChar = alphabet.charAt(index);
				stringBuilder.append(randomChar);
			}
			String code = stringBuilder.toString();
			@Cleanup
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_CUSTOMER_IUD(?,?,?,?,? ,?,?,?,?,? ,?,?)}");
			callableStatement.setString(++pindex, customerDTO.getCode());
			callableStatement.setInt(++pindex, authDTO.getNamespace().getId());
			callableStatement.setString(++pindex, customerDTO.getFirstName());
			callableStatement.setString(++pindex, customerDTO.getLastName());
			callableStatement.setString(++pindex, customerDTO.getMobileNumber());
			callableStatement.setString(++pindex, customerDTO.getEmail());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.setInt(++pindex, customerDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.setInt(++pindex, 0);
			callableStatement.registerOutParameter(++pindex, Types.INTEGER);
			callableStatement.setString(++pindex, code);
			callableStatement.execute();
			if (callableStatement.getInt("pitRowCount") > 0) {
				customerDTO.setCode(callableStatement.getString("pcrCode"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return customerDTO;
	}

	public CustomerDTO getCustomer(AuthDTO authDTO, String code, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT first_name, last_name, active_flag, mobile_number FROM customer WHERE code = ? AND active_flag = ?";
		@Cleanup
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setString(1, code);
		prepareStatement.setInt(2, customerDTO.getActiveFlag());
		@Cleanup
		ResultSet resultSet = prepareStatement.executeQuery();
		if (resultSet.next()) {
			customerDTO.setFirstName(resultSet.getString("firesultSett_name"));
			customerDTO.setLastName(resultSet.getString("last_name"));
			customerDTO.setActiveFlag(resultSet.getInt("active_flag"));
			customerDTO.setMobileNumber(resultSet.getString("mobile_number"));
		}
		return customerDTO;
	}

	public List<CustomerDTO> getAllCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT first_name, last_name, active_flag, mobile_number FROM customer WHERE  active_flag = ? ";
		@Cleanup
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setInt(1, customerDTO.getActiveFlag());
		@Cleanup
		ResultSet resultSet = prepareStatement.executeQuery();
		List<CustomerDTO> customerList = new ArrayList<>();
		while (resultSet.next()) {
			CustomerDTO dto = new CustomerDTO();
			dto.setFirstName(resultSet.getString("firesultSett_name"));
			dto.setLastName(resultSet.getString("last_name"));
			dto.setActiveFlag(resultSet.getInt("active_flag"));
			dto.setMobileNumber(resultSet.getString("mobile_number"));
			customerList.add(dto);
		}
		return customerList;
	}

	public CustomerDTO getCustomerId(UserDTO userDTO) throws ClassNotFoundException, SQLException {
		CustomerDTO customerDTO = new CustomerDTO();
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT id , first_name, last_name, mobile_number, email, user_id, active_flag, updated_by FROM customer WHERE user_id = ?";
		@Cleanup
		PreparedStatement prepareStatement = connection.prepareStatement(query);
		prepareStatement.setInt(1, userDTO.getId());
		@Cleanup
		ResultSet resultSet = prepareStatement.executeQuery();
		if (resultSet.next()) {
			customerDTO.setId(resultSet.getInt("id"));
			customerDTO.setFirstName(resultSet.getString("firesultSett_name"));
			customerDTO.setLastName(resultSet.getString("last_name"));
			customerDTO.setMobileNumber(resultSet.getString("mobile_number"));
			customerDTO.setEmail(resultSet.getString("email"));
			customerDTO.setUserId(resultSet.getInt("user_id"));
			customerDTO.setActiveFlag(resultSet.getInt("active_flag"));
			customerDTO.setUpdatedBy(resultSet.getInt("updated_by"));
		}
		return customerDTO;
	}

	public void updateUserId(CustomerDTO customerDTO, int id) {
		try {
			@Cleanup
			Connection connection = DaoConnection.connection();
			String query = "UPDATE customer SET user_id = ? WHERE code = ?";
			@Cleanup
			PreparedStatement prepareStatement = connection.prepareStatement(query);
			prepareStatement.setInt(1, id);
			prepareStatement.setString(2, customerDTO.getCode());
			prepareStatement.executeUpdate();
		}
		catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		catch (SQLException e) {

			e.printStackTrace();
		}
	}
}
