package org.sitenv.spring.dao;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.sitenv.spring.model.DafEncounter;
import org.sitenv.spring.util.SearchParameterMap;

public interface EncounterDao {
	
	 public DafEncounter getEncounterById(String id);
	 
	 public DafEncounter getEncounterByVersionId(String theId, String versionId);
	 
	 public List<DafEncounter> search(SearchParameterMap theMap);
	 
	 public List<DafEncounter> getEncounterHistoryById(String theId);

	public List<DafEncounter> getEncounterForBulkData(StringJoiner patient, Date start);
}
