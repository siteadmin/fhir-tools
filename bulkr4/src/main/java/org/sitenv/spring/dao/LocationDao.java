package org.sitenv.spring.dao;

import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import org.sitenv.spring.model.DafLocation;
import org.sitenv.spring.util.SearchParameterMap;

public interface LocationDao {

	public DafLocation getLocationById(String id);

	public DafLocation getLocationByVersionId(String theId, String versionId);

	public List<DafLocation> getLocationHistoryById(String theId);

	public List<DafLocation> search(SearchParameterMap searchParameterMap);

	public List<DafLocation> getLocationForBulkData(StringJoiner patient, Date start);
}
