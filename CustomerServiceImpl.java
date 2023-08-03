package org.in.com.serviceimpl;

import java.sql.SQLException;
import java.util.List;
import org.in.com.dao.CustomerDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.CustomerService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDAO customerDao;

	@Autowired
	private UserService userService;

	@Override
	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {
		if (customerDTO.getActiveFlag() == 1 && customerDTO.getCode() == "") {
			customerDao.updateCustomer(authDTO, customerDTO);
			UserDTO userDTO = new UserDTO();
			userDTO.setUsername(customerDTO.getUsername());
			userDTO.setPassword(customerDTO.getPassword());
			userDTO.setFirstName(customerDTO.getFirstName());
			userDTO.setLastName(customerDTO.getLastName());
			userDTO.setMobileNumber(customerDTO.getMobileNumber());
			userDTO.setEmail(customerDTO.getEmail());

			UserDTO dto = new UserDTO();
			dto = userService.updateUser(authDTO, userDTO);
			dto = userService.getUser(authDTO, userDTO, dto.getCode());
			customerDao.updateUserId(customerDTO, dto.getId());

		}
		else {
			customerDao.updateCustomer(authDTO, customerDTO);
		}
		return customerDTO;
	}

	@Override
	public CustomerDTO getCustomer(AuthDTO authDTO, String code, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException {

		return customerDao.getCustomer(authDTO, code, customerDTO);
	}

	@Override
	public List<CustomerDTO> getAllCustomer(AuthDTO authDTO, CustomerDTO customer) throws ClassNotFoundException, SQLException {

		return customerDao.getAllCustomer(authDTO, customer);
	}

	@Override
	public CustomerDTO getCustomerId(UserDTO userDTO) throws ClassNotFoundException, SQLException {

		return customerDao.getCustomerId(userDTO);
	}
}