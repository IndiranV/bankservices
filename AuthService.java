package org.in.com.service;

import java.sql.SQLException;
import org.in.com.dto.AuthDTO;

public interface AuthService {

	public AuthDTO userLogin(String username, String password) throws ClassNotFoundException, SQLException, Exception;

	public AuthDTO getAuth(String authToken) throws ClassNotFoundException, SQLException, Exception;

}
