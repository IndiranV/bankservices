package org.in.com.serviceimpl;

import java.sql.SQLException;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.AuthService;
import org.in.com.service.NamespaceService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private UserService userService;
	@Autowired
	private NamespaceService namespaceService;

	@Override
	public AuthDTO userLogin(String username, String password) throws ClassNotFoundException, SQLException, Exception {
		UserDTO userDTO = userService.userLogin(username, password);
		AuthDTO authDTO = new AuthDTO();
		authDTO.setUser(userDTO);
		return authDTO;
	}

	@Override
	public AuthDTO getAuth(String authToken) throws ClassNotFoundException, SQLException, Exception {
		AuthDTO authDTO = new AuthDTO();
		UserDTO userDTO = userService.getUserDetails(authToken);
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		namespaceDTO.setId(userDTO.getNamespaceId());
		NamespaceDTO namespaceDTO1 = namespaceService.getNamespaceDetails(namespaceDTO);
		authDTO.setNamespace(namespaceDTO1);
		authDTO.setUser(userDTO);
		return authDTO;
	}
}
