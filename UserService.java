package org.in.com.service;

import java.sql.SQLException;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;

public interface UserService {
	public UserDTO updateUser(AuthDTO authDTO, UserDTO userDTO) throws ClassNotFoundException, SQLException;

	public UserDTO getUser(AuthDTO authDTO, UserDTO userDTO, String code) throws ClassNotFoundException, SQLException;

	public List<UserDTO> getAllUser(AuthDTO authDTO, UserDTO userDTO) throws ClassNotFoundException, SQLException;

	public UserDTO getUserDetails(String authToken) throws ClassNotFoundException, SQLException, Exception;

	public UserDTO userLogin(String username, String password) throws ClassNotFoundException, SQLException, Exception;
}
