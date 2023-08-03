package org.in.com.service;

import java.sql.SQLException;
import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;

public interface NamespaceService {

	public NamespaceDTO updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException;

	public NamespaceDTO getNameSpace(AuthDTO authDTO, NamespaceDTO namespaceDTO, String code) throws ClassNotFoundException, SQLException;

	public List<NamespaceDTO> getAllNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException;

	public NamespaceDTO getNamespaceDetails(NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException;

}
