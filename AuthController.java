package org.in.com.controller;

import java.sql.SQLException;
import org.in.com.controller.io.AuthIO;
import org.in.com.controller.io.BaseIO;
import org.in.com.controller.io.NamespaceIO;
import org.in.com.controller.io.ResponseIO;
import org.in.com.controller.io.UserIO;
import org.in.com.dto.AuthDTO;
import org.in.com.exception.CustomException;
import org.in.com.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AuthController {
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ResponseIO<BaseIO> userLogin(@RequestParam("username") String username, @RequestParam("password") String password) throws ClassNotFoundException, SQLException, CustomException {
		try {
			authService.userLogin(username, password);
		}
		catch (CustomException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseIO.success();
	}

	@RequestMapping(value = "/{authtoken}/profile/details", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public AuthIO getAuth(@PathVariable("authtoken") String authToken) throws ClassNotFoundException, SQLException, Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		UserIO userIO = new UserIO();
		NamespaceIO namespaceIO = new NamespaceIO();
		namespaceIO.setCode(authDTO.getNamespace().getCode());
		namespaceIO.setName(authDTO.getNamespace().getName());
		namespaceIO.setActiveFlag(authDTO.getNamespace().getActiveFlag());

		userIO.setCode(authDTO.getUser().getCode());
		userIO.setUsername(authDTO.getUser().getUsername());
		userIO.setFirstName(authDTO.getUser().getFirstName());
		userIO.setLastName(authDTO.getUser().getLastName());
		userIO.setMobileNumber(authDTO.getUser().getMobileNumber());
		userIO.setEmail(authDTO.getUser().getEmail());
		userIO.setActiveFlag(authDTO.getUser().getActiveFlag());
		AuthIO authIO = new AuthIO();
		authIO.setUser(userIO);
		authIO.setNamespace(namespaceIO);
		return authIO;
	}
}
