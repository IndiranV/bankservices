package org.in.com.serviceimpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import org.in.com.exception.CustomException;
import org.in.com.dao.UserDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDao;

	@Override
	public UserDTO updateUser(AuthDTO authDTO, UserDTO userDTO) throws ClassNotFoundException, SQLException {
		return userDao.updateUser(authDTO, userDTO);
	}

	@Override
	public UserDTO getUser(AuthDTO authDTO, UserDTO userDTO, String code) throws ClassNotFoundException, SQLException {
		return userDao.getUser(userDTO, code);
	}

	@Override
	public List<UserDTO> getAllUser(AuthDTO authDTO, UserDTO userDTO) throws ClassNotFoundException, SQLException {
		return userDao.getAllUser(userDTO);
	}

	public UserDTO userLogin(String username, String password) throws ClassNotFoundException, SQLException, Exception {
		try {
			UserDTO userDTO = userDao.userLogin(username, password);
			if (!userDTO.getPassword().equals(password)) {
				throw new CustomException("Invalid user Credentials");
			}
			String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			StringBuilder stringBuilder = new StringBuilder();
			Random random = new Random();
			for (int i = 0; i <= 10; i++) {
				int index = random.nextInt(alphabet.length());
				char randomChar = alphabet.charAt(index);
				stringBuilder.append(randomChar);
			}
			String randomString = stringBuilder.toString();
			userDao.updateUserToken(randomString, userDTO.getCode());
		}
		catch (CustomException e) {
			throw e;
		}
		return userDao.userLogin(username, password);
	}

	@Override
	public UserDTO getUserDetails(String authToken) throws ClassNotFoundException, SQLException, Exception {

		UserDTO userDTO = userDao.getUserDetails(authToken);
		return userDTO;
	}
}