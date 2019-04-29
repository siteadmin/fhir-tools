package org.sitenv.spring.service;

import java.util.List;

import org.sitenv.spring.dao.MedicationRequestDao;
import org.sitenv.spring.model.DafMedicationRequest;
import org.sitenv.spring.util.SearchParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("medicationRequestService")
@Transactional
public class MedicationRequestServiceImpl implements MedicationRequestService {
	@Autowired
    private MedicationRequestDao medicationRequestDao;
	
	@Transactional
    public DafMedicationRequest getMedicationRequestById(int id) {
        return this.medicationRequestDao.getMedicationRequestById(id);
    }
	
	@Transactional
	public DafMedicationRequest getMedicationRequestByVersionId(int theId, String versionId) {
		return this.medicationRequestDao.getMedicationRequestByVersionId(theId, versionId);
	}
	
	@Transactional
    public List<DafMedicationRequest> search(SearchParameterMap paramMap){
        return this.medicationRequestDao.search(paramMap);
    }

	@Override
	public List<DafMedicationRequest> getMedicationRequestHistoryById(int id) {
		return this.medicationRequestDao.getMedicationRequestHistoryById(id);
	}

}
