package org.sitenv.spring.service;

import java.util.List;

import org.sitenv.spring.model.DafOrganization;
import org.sitenv.spring.util.SearchParameterMap;

public interface OrganizationService {

	public DafOrganization getOrganizationById(int id);

	public DafOrganization getOrganizationByVersionId(int theId, String versionId);

	public List<DafOrganization> getOrganizationHistoryById(int id);
	
	public List<DafOrganization> search(SearchParameterMap theMap);

	
}