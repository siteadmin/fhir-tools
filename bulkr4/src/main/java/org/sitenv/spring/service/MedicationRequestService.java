package org.sitenv.spring.service;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.sitenv.spring.model.DafMedicationRequest;
import org.sitenv.spring.util.SearchParameterMap;

public interface MedicationRequestService {

	DafMedicationRequest getMedicationRequestById(String id);

	DafMedicationRequest getMedicationRequestByVersionId(String theId, String versionId);

	List<DafMedicationRequest> search(SearchParameterMap paramMap);

	List<DafMedicationRequest> getMedicationRequestHistoryById(String id);

	List<DafMedicationRequest> getMedicationRequestForBulkData(StringJoiner patients, Date start);

}
