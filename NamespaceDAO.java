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
import org.in.com.dto.NamespaceDTO;
import org.springframework.stereotype.Repository;
import lombok.Cleanup;

@Repository
public class NamespaceDAO {
	public NamespaceDTO updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException {
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
			CallableStatement callableStatement = connection.prepareCall("{CALL EZEE_SP_NAMESPACE_IUD(?,?,?,?,? ,?,?)}");
			callableStatement.setString(++pindex, namespaceDTO.getCode());
			callableStatement.setString(++pindex, namespaceDTO.getName());
			callableStatement.setInt(++pindex, namespaceDTO.getActiveFlag());
			callableStatement.setInt(++pindex, authDTO.getUser().getId());
			callableStatement.setInt(++pindex, 0);
			callableStatement.registerOutParameter(++pindex, Types.INTEGER);
			callableStatement.setString(++pindex, code);
			callableStatement.execute();
			System.out.println(callableStatement.getInt("pitRowCount"));
			if (callableStatement.getInt("pitRowCount") > 0) {
				namespaceDTO.setCode(callableStatement.getString("pcrCode"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return namespaceDTO;
	}

	public NamespaceDTO getNameSpace(AuthDTO authDTO, NamespaceDTO namespaceDTO, String code) throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT name, active_flag, updated_by FROM namespace WHERE code = ? AND active_flag = ?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, code);
		preparedStatement.setInt(2, namespaceDTO.getActiveFlag());
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			namespaceDTO.setName(resultSet.getString("name"));
			namespaceDTO.setActiveFlag(resultSet.getInt("active_flag"));
		}
		return namespaceDTO;
	}

	public List<NamespaceDTO> getAllNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws SQLException, ClassNotFoundException {
		List<NamespaceDTO> namespaceList = new ArrayList<>();
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT name, active_flag, updated_by FROM namespace WHERE active_flag = ?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, namespaceDTO.getActiveFlag());
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			NamespaceDTO dto = new NamespaceDTO();
			dto.setName(resultSet.getString("name"));
			dto.setActiveFlag(resultSet.getInt("active_flag"));
			namespaceList.add(dto);
		}
		return namespaceList;
	}

	public NamespaceDTO getNamespaceDetails(NamespaceDTO namespaceDTO) throws SQLException, ClassNotFoundException {
		@Cleanup
		Connection connection = DaoConnection.connection();
		String query = "SELECT id, code, name, active_flag, updated_by FROM namespace WHERE id=?";
		@Cleanup
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, namespaceDTO.getId());
		@Cleanup
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			namespaceDTO.setId(resultSet.getInt("id"));
			namespaceDTO.setCode(resultSet.getString("code"));
			namespaceDTO.setName(resultSet.getString("name"));
			namespaceDTO.setActiveFlag(resultSet.getInt("active_flag"));
			namespaceDTO.setUpdatedBy(resultSet.getInt("updated_by"));
		}
		return namespaceDTO;
	}
}
