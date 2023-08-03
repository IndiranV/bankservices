package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;
import org.in.com.controller.io.UserIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.UserDTO;
import org.in.com.service.AuthService;
import org.in.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/{authtoken}/user")
@Controller
public class UserController {
	@Autowired
	private AuthService authService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/add/update/delete/user", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserIO updateUser(@PathVariable("authtoken") String authToken, @RequestBody UserIO userIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		UserDTO userDTO = new UserDTO();
		userDTO.setCode(userIO.getCode());
		userDTO.setUsername(userIO.getUsername());
		userDTO.setPassword(userIO.getPassword());
		userDTO.setToken(userIO.getToken());
		userDTO.setFirstName(userIO.getFirstName());
		userDTO.setLastName(userIO.getLastName());
		userDTO.setMobileNumber(userIO.getMobileNumber());
		userDTO.setEmail(userIO.getEmail());
		userDTO.setActiveFlag(userIO.getActiveFlag());

		userService.updateUser(authDTO, userDTO);
		UserIO user = new UserIO();
		user.setCode(userDTO.getCode());
		user.setActiveFlag(userDTO.getActiveFlag());

		return user;
	}

	@RequestMapping(value = "/user/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public UserIO getUser(@PathVariable("authtoken") String authToken, @RequestBody UserIO userIO, @PathVariable("code") String code) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		UserDTO userDTO = new UserDTO();
		userDTO.setActiveFlag(userIO.getActiveFlag());
		userService.getUser(authDTO, userDTO, code);
		
		UserIO user = new UserIO();
		user.setCode(userDTO.getCode());
		user.setToken(userDTO.getToken());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setEmail(userDTO.getEmail());
		user.setActiveFlag(userDTO.getActiveFlag());

		return user;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<UserIO> getAllUser(@PathVariable("authtoken") String authToken, @RequestBody UserIO userIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		List<UserIO> userIOList = new ArrayList<>();

		UserDTO userDTO = new UserDTO();
		userDTO.setActiveFlag(userIO.getActiveFlag());
		List<UserDTO> userList = userService.getAllUser(authDTO, userDTO);

		for (UserDTO userData : userList) {
			UserIO user = new UserIO();
			user.setCode(userData.getCode());
			user.setToken(userData.getToken());
			user.setFirstName(userData.getFirstName());
			user.setLastName(userData.getLastName());
			user.setMobileNumber(userData.getMobileNumber());
			user.setEmail(userData.getEmail());
			user.setActiveFlag(userData.getActiveFlag());
			userIOList.add(user);
		}
		return userIOList;
	}
}
