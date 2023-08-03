package org.in.com.controller;

import java.util.List;
import java.util.ArrayList;
import org.in.com.controller.io.CustomerIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.CustomerDTO;
import org.in.com.service.AuthService;
import org.in.com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/{authtoken}/customer")
@Controller
public class CustomerController {
	@Autowired
	private AuthService authService;
	@Autowired
	private CustomerService cutomerService;

	@RequestMapping(value = "/add/customer", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerIO updateCustomer(@PathVariable("authtoken") String authToken, @RequestBody CustomerIO customerIo) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCode(customerIo.getCode());
		customerDTO.setFirstName(customerIo.getFirstName());
		customerDTO.setLastName(customerIo.getLastName());
		customerDTO.setMobileNumber(customerIo.getMobileNumber());
		customerDTO.setEmail(customerIo.getEmail());
		customerDTO.setUserId(customerIo.getUserId());
		customerDTO.setActiveFlag(customerIo.getActiveFlag());
		customerDTO.setUsername(customerIo.getUsername());
		customerDTO.setPassword(customerIo.getPassword());

		cutomerService.updateCustomer(authDTO, customerDTO);

		CustomerIO customer = new CustomerIO();
		customer.setActiveFlag(customerDTO.getActiveFlag());
		customer.setCode(customerDTO.getCode());
		return customer;
	}

	@RequestMapping(value = "/customer/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public CustomerIO getCustomer(@PathVariable("authtoken") String authToken, @RequestBody CustomerIO customerIO, @PathVariable("code") String code) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setActiveFlag(customerIO.getActiveFlag());
		customerDTO = cutomerService.getCustomer(authDTO, code, customerDTO);

		CustomerIO customer = new CustomerIO();
		customer.setFirstName(customerDTO.getFirstName());
		customer.setLastName(customerDTO.getLastName());
		customer.setMobileNumber(customerDTO.getMobileNumber());
		customer.setEmail(customerDTO.getEmail());
		customer.setActiveFlag(customerDTO.getActiveFlag());
		return customer;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<CustomerIO> getAllCustomer(@PathVariable("authtoken") String authToken, @RequestBody CustomerIO customerIo) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		List<CustomerIO> customerIOList = new ArrayList<>();

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setActiveFlag(customerIo.getActiveFlag());
		List<CustomerDTO> customerDTOList = cutomerService.getAllCustomer(authDTO, customerDTO);

		for (CustomerDTO customerData : customerDTOList) {
			CustomerIO customer = new CustomerIO();

			customer.setFirstName(customerData.getFirstName());
			customer.setLastName(customerData.getLastName());
			customer.setMobileNumber(customerData.getMobileNumber());
			customer.setEmail(customerData.getEmail());
			customer.setActiveFlag(customerData.getActiveFlag());
			customerIOList.add(customer);
		}
		return customerIOList;
	}
}