package org.in.com.dto;

import lombok.Data;

@Data
public class UserDTO {
	private int id;
	private String code;
	private int namespaceId;
	private String username;
	private String password;
	private String token;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String email;
	private int activeFlag;
	private int updatedBy;
	private String updatedAt;
}
