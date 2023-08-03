package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;
import org.in.com.controller.io.NamespaceIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.service.AuthService;
import org.in.com.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/{authtoken}/namespace")
@Controller
public class NamespaceController {
	@Autowired
	private AuthService authService;
	@Autowired
	private NamespaceService namespaceService;

	@RequestMapping(value = "/add/namespace", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public NamespaceIO updateNamespace(@PathVariable("authtoken") String authToken, @RequestBody NamespaceIO namespaceIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);

		NamespaceDTO namespaceDTO = new NamespaceDTO();
		namespaceDTO.setCode(namespaceIO.getCode());
		namespaceDTO.setName(namespaceIO.getName());
		namespaceDTO.setActiveFlag(namespaceIO.getActiveFlag());

		namespaceService.updateNamespace(authDTO, namespaceDTO);

		NamespaceIO namespace = new NamespaceIO();
		namespace.setCode(namespaceDTO.getCode());
		namespace.setName(namespaceDTO.getName());
		namespace.setActiveFlag(namespaceDTO.getActiveFlag());
		return namespace;

	}

	@RequestMapping(value = "/namespace/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public NamespaceIO getNameSpace(@PathVariable("authtoken") String authToken, @RequestBody NamespaceIO namespaceIO, @PathVariable("code") String code) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		namespaceDTO.setActiveFlag(namespaceIO.getActiveFlag());
		namespaceService.getNameSpace(authDTO, namespaceDTO, code);

		namespaceIO.setName(namespaceDTO.getName());
		namespaceIO.setActiveFlag(namespaceDTO.getActiveFlag());
		return namespaceIO;
	}

	@RequestMapping(method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public List<NamespaceIO> getAllCustomer(@PathVariable("authtoken") String authToken, @RequestBody NamespaceIO namespaceIO) throws Exception {
		AuthDTO authDTO = authService.getAuth(authToken);
		List<NamespaceIO> namespaceIoList = new ArrayList<>();

		NamespaceDTO namespaceDTO = new NamespaceDTO();
		namespaceDTO.setActiveFlag(namespaceIO.getActiveFlag());
		List<NamespaceDTO> namespaceList = namespaceService.getAllNamespace(authDTO, namespaceDTO);

		for (NamespaceDTO namespaceData : namespaceList) {
			NamespaceIO namespace = new NamespaceIO();

			namespace.setName(namespaceData.getName());
			namespace.setActiveFlag(namespaceData.getActiveFlag());
			namespaceIoList.add(namespace);
		}
		return namespaceIoList;
	}
}
