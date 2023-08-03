package org.in.com.serviceimpl;

import java.sql.SQLException;
import java.util.List;
import org.in.com.dao.NamespaceDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NamespaceServiceImpl implements NamespaceService {
	@Autowired
	private NamespaceDAO namespaceDao;

	@Override
	public NamespaceDTO updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException {
		return namespaceDao.updateNamespace(authDTO, namespaceDTO);
	}

	@Override
	public NamespaceDTO getNameSpace(AuthDTO authDTO, NamespaceDTO namespaceDTO, String code) throws ClassNotFoundException, SQLException {
		return namespaceDao.getNameSpace(authDTO, namespaceDTO, code);
	}

	@Override
	public List<NamespaceDTO> getAllNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException {

		return namespaceDao.getAllNamespace(authDTO, namespaceDTO);
	}

	@Override
	public NamespaceDTO getNamespaceDetails(NamespaceDTO namespaceDTO) throws ClassNotFoundException, SQLException {
		return namespaceDao.getNamespaceDetails(namespaceDTO);
	}

}
