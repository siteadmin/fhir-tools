package org.sitenv.spring.dao;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.sitenv.spring.model.DafMedicationDispense;
import org.sitenv.spring.util.SearchParameterMap;

public interface MedicationDispenseDao {
	
	public DafMedicationDispense getMedicationDispenseById(String id);
	
	public DafMedicationDispense getMedicationDispenseByVersionId(String theId, String versionId);
		
	public List<DafMedicationDispense> search(SearchParameterMap paramMap);
	
	public List<DafMedicationDispense> getMedicationDispenseHistoryById(String theId);

	public List<DafMedicationDispense> getMedicationDispenseForBulkData(StringJoiner patient, Date start);

}
