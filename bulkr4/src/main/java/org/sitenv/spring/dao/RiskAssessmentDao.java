package org.sitenv.spring.dao;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.sitenv.spring.model.DafRiskAssessment;
import org.sitenv.spring.util.SearchParameterMap;

public interface RiskAssessmentDao {

	
	public DafRiskAssessment getRiskAssessmentById(String id);

	public DafRiskAssessment getRiskAssessmentByVersionId(String theId, String versionId);

	public List<DafRiskAssessment> search(SearchParameterMap paramMap);

	public List<DafRiskAssessment> getRiskAssessmentHistoryById(String id);

	public List<DafRiskAssessment> getRiskAssessmentForBulkData(StringJoiner patients, Date start);
}
