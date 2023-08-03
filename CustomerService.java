package org.in.com.service;

import java.sql.SQLException;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.dto.UserDTO;

public interface CustomerService {

	public CustomerDTO updateCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException;

	public CustomerDTO getCustomer(AuthDTO authDTO, String code, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException;

	public List<CustomerDTO> getAllCustomer(AuthDTO authDTO, CustomerDTO customerDTO) throws ClassNotFoundException, SQLException;

	public CustomerDTO getCustomerId(UserDTO userDTO) throws ClassNotFoundException, SQLException;

}
