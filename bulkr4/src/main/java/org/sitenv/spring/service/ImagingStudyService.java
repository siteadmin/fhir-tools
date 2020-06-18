package org.sitenv.spring.service;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.sitenv.spring.model.DafImagingStudy;
import org.sitenv.spring.util.SearchParameterMap;

public interface ImagingStudyService {

	DafImagingStudy getImagingStudyByVersionId(String id, String versionIdPart);

	DafImagingStudy getImagingStudyById(String id);

	List<DafImagingStudy> search(SearchParameterMap paramMap);

	List<DafImagingStudy> getImagingStudyHistoryById(String id);

	List<DafImagingStudy> getImagingStudyForBulkData(StringJoiner patients, Date start);

}