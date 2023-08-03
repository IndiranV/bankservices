package org.in.com.dto;

import lombok.Data;

@Data
public class CustomerDTO {

	private int id;
	private String code;
	private int namespaceId;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String email;
	private int userId;
	private int activeFlag;
	private int updatedBy;
	private int updatedAt;
	private String username;
	private String password;
}
