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
import org.in.com.dto.UserDTO;
import org.springframework.stereotype.Repository;

import lombok.Cleanup;

@Repository
public class UserDAO {
	public UserDTO updateUser(AuthDTO authDTO, UserDTO userDTO) throws SQLException, ClassNotFoundException {
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
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_USER_IUD(?,?,?,?,? ,?,?,?,?,? ,?,?,?)}");
			callableStatement.setString(++pindex, userDTO.getCode());
			callableStatement.setInt(++pindex, authDTO.getNamespace().getId());
			callableStatement.setString(++pindex, userDTO.getUsername());
			callableStatement.setString(++pindex, userDTO.getPassword());
			callableStatement.setString(++pindex, userDTO.getFirstName());
			callableStatement.setString(++pindex, userDTO.getLastName());
			callableStatement.setString(++pindex, userDTO.getMobileNumber());
			callableStatement.setString(++pindex, userDTO.getEmail());
			callableStatement.setInt(++pindex, userDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.setInt(++pindex, 0);
			callableStatement.registerOutParameter(++pindex, Types.INTEGER);
			callableStatement.setString(++pindex, code);
			callableStatement.execute();
			if (callableStatement.getInt("pitRowCount") > 0) {
				userDTO.setCode(callableStatement.getString("pcrCode"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return userDTO;
	}

	public UserDTO getUser(UserDTO userDTO, String code) throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT id, code, namespace_id, username, password, first_name, last_name, mobile_number, email, active_flag FROM user WHERE code=? AND active_flag = ?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, code);
		preparedStatement.setInt(2, userDTO.getActiveFlag());
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			userDTO.setId(resultSet.getInt("id"));
			userDTO.setCode(resultSet.getString("code"));
			userDTO.setNamespaceId(resultSet.getInt("namespace_id"));
			userDTO.setUsername(resultSet.getString("username"));
			userDTO.setPassword(resultSet.getString("password"));
			userDTO.setFirstName(resultSet.getString("first_name"));
			userDTO.setLastName(resultSet.getString("last_name"));
			userDTO.setMobileNumber(resultSet.getString("mobile_number"));
			userDTO.setEmail(resultSet.getString("email"));
			userDTO.setActiveFlag(resultSet.getInt("active_flag"));
		}
		return userDTO;
	}

	public List<UserDTO> getAllUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
		List<UserDTO> userList = new ArrayList<>();
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT namespace_id, username, password, first_name, last_name, mobile_number, email, active_flag FROM user WHERE active_flag = ?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, userDTO.getActiveFlag());
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			UserDTO dto = new UserDTO();
			dto.setNamespaceId(resultSet.getInt("namespace_id"));
			dto.setUsername(resultSet.getString("username"));
			dto.setPassword(resultSet.getString("password"));
			dto.setFirstName(resultSet.getString("first_name"));
			dto.setLastName(resultSet.getString("last_name"));
			dto.setMobileNumber(resultSet.getString("mobile_number"));
			dto.setEmail(resultSet.getString("email"));
			dto.setActiveFlag(resultSet.getInt("active_flag"));
			userList.add(dto);
		}
		return userList;
	}

	public UserDTO userLogin(String username, String password) throws ClassNotFoundException, SQLException {
		UserDTO userDTO = new UserDTO();
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT code, username, password, token, first_name, last_name, mobile_number, email, active_flag, updated_by FROM user WHERE username=? ";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, username);
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			userDTO.setCode(resultSet.getString("code"));
			userDTO.setUsername(resultSet.getString("username"));
			userDTO.setPassword(resultSet.getString("password"));
			userDTO.setToken(resultSet.getString("token"));
			userDTO.setFirstName(resultSet.getString("first_name"));
			userDTO.setLastName(resultSet.getString("last_name"));
			userDTO.setMobileNumber(resultSet.getString("mobile_number"));
			userDTO.setEmail(resultSet.getString("email"));
			userDTO.setActiveFlag(resultSet.getInt("active_flag"));
			userDTO.setUpdatedBy(resultSet.getInt("updated_by"));
		}
		return userDTO;
	}

	public void updateUserToken(String randomString, String code) throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "UPDATE user SET token = ? WHERE code = ?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, randomString);
		preparedStatement.setString(2, code);
		preparedStatement.executeUpdate();
	}

	public UserDTO getUserDetails(String authToken) throws ClassNotFoundException, SQLException {
		UserDTO userDTO = new UserDTO();
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT id, code, namespace_id, username, password, token, first_name, last_name, mobile_number, email, active_flag, updated_by FROM user WHERE token = ?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, authToken);
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			userDTO.setId(resultSet.getInt("id"));
			userDTO.setCode(resultSet.getString("code"));
			userDTO.setNamespaceId(resultSet.getInt("namespace_id"));
			userDTO.setUsername(resultSet.getString("username"));
			userDTO.setPassword(resultSet.getString("password"));
			userDTO.setToken(resultSet.getString("token"));
			userDTO.setFirstName(resultSet.getString("first_name"));
			userDTO.setLastName(resultSet.getString("last_name"));
			userDTO.setMobileNumber(resultSet.getString("mobile_number"));
			userDTO.setEmail(resultSet.getString("email"));
			userDTO.setActiveFlag(resultSet.getInt("active_flag"));
			userDTO.setUpdatedBy(resultSet.getInt("updated_by"));
		}
		return userDTO;
	}
}
