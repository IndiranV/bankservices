package org.in.com.controller.io;

import lombok.Data;

@Data
public class UserIO {

	private String code;
	private String username;
	private String password;
	private String token;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String email;
	private int activeFlag;
}
