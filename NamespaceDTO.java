package org.in.com.dto;

import lombok.Data;

@Data
public class NamespaceDTO {
	private int id;
	private String code;
	private String name;
	private int activeFlag;
	private int updatedBy;
	private String updatedAt;
}
