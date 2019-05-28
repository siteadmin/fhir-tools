package org.sitenv.spring.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.sitenv.spring.model.DafDiagnosticReport;
import org.sitenv.spring.util.SearchParameterMap;
import org.springframework.stereotype.Repository;

import ca.uhn.fhir.model.api.IQueryParameterType;
import ca.uhn.fhir.rest.param.DateParam;
import ca.uhn.fhir.rest.param.ReferenceParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.TokenParamModifier;

@Repository("diagosticReportDao")
public class DiagnosticReportDaoImpl extends AbstractDao implements DiagnosticReportDao {

	/**
	 * This method builds criteria for fetching DiagnosticReport record by id.
	 * 
	 * @param id : ID of the resource
	 * @return : DAF object of the DiagnosticReport
	 */
	public DafDiagnosticReport getDiagnosticReportById(int id) {

		Criteria criteria = getSession().createCriteria(DafDiagnosticReport.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.sqlRestriction("{alias}.data->>'id' = '" +id+"' order by {alias}.data->'meta'->>'versionId' desc"));
		return (DafDiagnosticReport) criteria.list().get(0);
	}

	/**
	 * This method builds criteria for fetching particular version of the
	 * DiagnosticReport record by id.
	 * 
	 * @param theId     : ID of the DiagnosticReport
	 * @param versionId : version of the DiagnosticReport record
	 * @return : DAF object of the DiagnosticReport
	 */
	public DafDiagnosticReport getDiagnosticReportByVersionId(int theId, String versionId) {
		Criteria criteria = getSession().createCriteria(DafDiagnosticReport.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Conjunction versionConjunction = Restrictions.conjunction();
		versionConjunction.add(Restrictions.sqlRestriction("{alias}.data->'meta'->>'versionId' = '" + versionId + "'"));
		versionConjunction.add(Restrictions.sqlRestriction("{alias}.data->>'id' = '" + theId + "'"));
		criteria.add(versionConjunction);
		return (DafDiagnosticReport) criteria.uniqueResult();
	}

	/**
	 * This method builds criteria for fetching history of the DiagnosticReport by
	 * id
	 * 
	 * @param theId : ID of the DiagnosticReport
	 * @return : List of DiagnosticReport DAF records
	 */
	@SuppressWarnings("unchecked")
	public List<DafDiagnosticReport> getDiagnosticReportHistoryById(int theId) {
		Criteria criteria = getSession().createCriteria(DafDiagnosticReport.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.sqlRestriction("{alias}.data->>'id' = '" + theId + "'"));
		return (List<DafDiagnosticReport>) criteria.list();
	}

	/**
	 * This method invokes various methods for search
	 * 
	 * @param theMap : parameter for search
	 * @return criteria : DAF DiagnosticReport object
	 */
	@SuppressWarnings("unchecked")
	public List<DafDiagnosticReport> search(SearchParameterMap theMap) {
		Criteria criteria = getSession().createCriteria(DafDiagnosticReport.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		// build criteria for id
		buildIdCriteria(theMap, criteria);

		// build criteria for identifier
		buildIdentifierCriteria(theMap, criteria);

		// build criteria for code
		buildCodeCriteria(theMap, criteria);

		// build criteria for subject
		buildSubjectCriteria(theMap, criteria);

		// build criteria for encounter
		buildEncounterCriteria(theMap, criteria);

		// build criteria for category
		buildCategoryCriteria(theMap, criteria);

		// build criteria for conclusion
		buildConclusionCriteria(theMap, criteria);

		// build criteria for status
		buildStatusCriteria(theMap, criteria);

		// build criteria for performer
		buildPerformerCriteria(theMap, criteria);

		// build criteria for result
		buildResultCriteria(theMap, criteria);

		// build criteria for basedOn
		buildBasedOnCriteria(theMap, criteria);

		// build criteria for specimen
		buildSpecimenCriteria(theMap, criteria);

		// build criteria for issued
		buildIssuedCriteria(theMap, criteria);

		// build criteria for effectiveDateTime
		buildEffectiveDateCriteria(theMap, criteria);

		return criteria.list();
	}

	/**
	 * This method builds criteria for DiagnosticReport id
	 * 
	 * @param theMap   : search parameter "_id"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildIdCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("_id");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				for (IQueryParameterType params : values) {
					StringParam id = (StringParam) params;
					if (id.getValue() != null) {
						criteria.add(Restrictions.sqlRestriction("{alias}.data->>'id' = '" + id.getValue() + "'"));
					}
				}
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport identifier
	 * 
	 * @param theMap   : search parameter "identifier"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildIdentifierCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("identifier");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					TokenParam identifier = (TokenParam) params;
					Criterion orCond = null;
					if (identifier.getValue() != null) {
						
						orCond = Restrictions.or(
								Restrictions.sqlRestriction("{alias}.data->'identifier'->0->>'value' ilike '%"
										+ identifier.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'identifier'->1->>'value' ilike '%"
										+ identifier.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'identifier'->0->>'system' ilike '%"
										+ identifier.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'identifier'->1->>'system' ilike '%"
										+ identifier.getValue() + "%'")
								);
					} 
					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport code
	 * 
	 * @param theMap   : search parameter "code"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildCodeCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("code");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					TokenParam code = (TokenParam) params;
					if (code.getValue() != null) {
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->0->>'system' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->0->>'code' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->0->>'display' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->1->>'system' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->1->>'code' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->1->>'display' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->0->>'system' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->0->>'code' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->0->>'display' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->1->>'system' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->1->>'code' ilike '%" + code.getValue() + "%'"));
						disjunction.add(Restrictions.sqlRestriction(
								"{alias}.data->'code'->'coding'->1->>'display' ilike '%" + code.getValue() + "%'"));
					}
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport subject
	 * 
	 * @param theMap   : search parameter "subject"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildSubjectCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("subject");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					ReferenceParam subject = (ReferenceParam) params;
					Criterion orCond = null;
					if (subject.getValue() != null) {
						orCond = Restrictions.or(
								Restrictions.sqlRestriction(
										"{alias}.data->'subject'->>'reference' ilike '%" + subject.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'subject'->>'display' ilike '%" + subject.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'subject'->>'type' ilike '%" + subject.getValue() + "%'"));
					} else if (subject.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'subject' IS NULL"));
					} else if (!subject.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'subject' IS NOT NULL"));
					}

					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport conclusion
	 * 
	 * @param theMap   : search parameter "conclusion"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildConclusionCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("conclusion");
		if (list != null) {

			for (List<? extends IQueryParameterType> values : list) {
				for (IQueryParameterType params : values) {
					TokenParam conclusion = (TokenParam) params;
					if (conclusion.getModifier() != null) {
						TokenParamModifier modifier = conclusion.getModifier();
						if (modifier.getValue() == ":not") {
							criteria.add(Restrictions.sqlRestriction(
									"{alias}.data->>'conclusion' not ilike '" + conclusion.getValue() + "'"));
						}
					} else if (StringUtils.isNoneEmpty(conclusion.getValue())) {
						criteria.add(Restrictions
								.sqlRestriction("{alias}.data->>'conclusion' ilike '%" + conclusion.getValue() + "%'"));
					} else if (conclusion.getMissing()) {
						criteria.add(Restrictions.sqlRestriction("{alias}.data->>'conclusion' IS NULL"));
					} else if (!conclusion.getMissing()) {
						criteria.add(Restrictions.sqlRestriction("{alias}.data->>'conclusion' IS NOT NULL"));
					}
				}
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport status
	 * 
	 * @param theMap   : search parameter "status"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildStatusCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("status");
		if (list != null) {

			for (List<? extends IQueryParameterType> values : list) {
				for (IQueryParameterType params : values) {
					TokenParam status = (TokenParam) params;
					if (status.getModifier() != null) {
						TokenParamModifier modifier = status.getModifier();
						if (modifier.getValue() == ":not") {
							criteria.add(Restrictions
									.sqlRestriction("{alias}.data->>'status' not ilike '" + status.getValue() + "'"));
						}
					} else if (StringUtils.isNoneEmpty(status.getValue())) {
						criteria.add(Restrictions
								.sqlRestriction("{alias}.data->>'status' ilike '%" + status.getValue() + "%'"));
					} else if (status.getMissing()) {
						criteria.add(Restrictions.sqlRestriction("{alias}.data->>'status' IS NULL"));
					} else if (!status.getMissing()) {
						criteria.add(Restrictions.sqlRestriction("{alias}.data->>'status' IS NOT NULL"));
					}
				}
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport performer
	 * 
	 * @param theMap   : search parameter "performer"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildPerformerCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("performer");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					ReferenceParam performer = (ReferenceParam) params;
					Criterion orCond = null;
					if (performer.getValue() != null) {
						orCond = Restrictions.or(
								Restrictions.sqlRestriction("{alias}.data->'performer'->0->>'reference' ilike '%"
										+ performer.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'performer'->0->>'type' ilike '%" + performer.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'performer'->0->>'display' ilike '%"
										+ performer.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'performer'->1->>'reference' ilike '%"
										+ performer.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'performer'->1->>'type' ilike '%" + performer.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'performer'->1->>'display' ilike '%"
										+ performer.getValue() + "%'")
								);
					} else if (performer.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'performer' IS NULL"));
					} else if (!performer.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'performer' IS NOT NULL"));
					}

					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport encounter
	 * 
	 * @param theMap   : search parameter "encounter"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildEncounterCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("encounter");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					ReferenceParam encounter = (ReferenceParam) params;
					Criterion orCond = null;
					if (encounter.getValue() != null) {
						orCond = Restrictions.or(
								Restrictions.sqlRestriction("{alias}.data->'encounter'->>'reference' ilike '%"
										+ encounter.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'encounter'->>'type' ilike '%" + encounter.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'encounter'->>'display' ilike '%"
										+ encounter.getValue() + "%'"));
					} else if (encounter.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'encounter' IS NULL"));
					} else if (!encounter.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'encounter' IS NOT NULL"));
					}

					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport result
	 * 
	 * @param theMap   : search parameter "result"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildResultCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("result");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					ReferenceParam result = (ReferenceParam) params;
					Criterion orCond = null;
					if (result.getValue() != null) {
						orCond = Restrictions.or(
								Restrictions.sqlRestriction(
										"{alias}.data->'result'->0->>'reference' ilike '%" + result.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'result'->0->>'type' ilike '%" + result.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'result'->0->>'display' ilike '%" + result.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'result'->1->>'reference' ilike '%" + result.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'result'->1->>'type' ilike '%" + result.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'result'->1->>'display' ilike '%" + result.getValue() + "%'")
								);
					} else if (result.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'result' IS NULL"));
					} else if (!result.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'result' IS NOT NULL"));
					}

					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport category
	 * 
	 * @param theMap   : search parameter "category"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildCategoryCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("category");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					TokenParam category = (TokenParam) params;
					if (category.getValue() != null) {
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->0->'coding'->0->>'system' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(
								Restrictions.sqlRestriction("{alias}.data->'category'->0->'coding'->0->>'code' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->0->'coding'->0->>'display' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->0->'coding'->1->>'system' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(
								Restrictions.sqlRestriction("{alias}.data->'category'->0->'coding'->1->>'code' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->0->'coding'->1->>'display' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->1->'coding'->0->>'system' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(
								Restrictions.sqlRestriction("{alias}.data->'category'->1->'coding'->0->>'code' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->1->'coding'->0->>'display' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->1->'coding'->1->>'system' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(
								Restrictions.sqlRestriction("{alias}.data->'category'->1->'coding'->1->>'code' ilike '"
										+ category.getValue() + "%'"));
						disjunction.add(Restrictions
								.sqlRestriction("{alias}.data->'category'->1->'coding'->1->>'display' ilike '"
										+ category.getValue() + "%'"));
					}
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport basedOn
	 * 
	 * @param theMap   : search parameter "basedOn"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildBasedOnCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("based-on");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					ReferenceParam basedOn = (ReferenceParam) params;
					Criterion orCond = null;
					if (basedOn.getValue() != null) {
						orCond = Restrictions.or(
								Restrictions.sqlRestriction("{alias}.data->'basedOn'->0->>'reference' ilike '%"
										+ basedOn.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'basedOn'->0->>'type' ilike '%" + basedOn.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'basedOn'->0->>'display' ilike '%" + basedOn.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'basedOn'->1->>'reference' ilike '%"
										+ basedOn.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'basedOn'->1->>'type' ilike '%" + basedOn.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'basedOn'->1->>'display' ilike '%" + basedOn.getValue() + "%'")
								);
					} else if (basedOn.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'basedOn' IS NULL"));
					} else if (!basedOn.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'basedOn' IS NOT NULL"));
					}

					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport specimen
	 * 
	 * @param theMap   : search parameter "specimen"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildSpecimenCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("specimen");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				Disjunction disjunction = Restrictions.disjunction();
				for (IQueryParameterType params : values) {
					ReferenceParam specimen = (ReferenceParam) params;
					Criterion orCond = null;
					if (specimen.getValue() != null) {
						orCond = Restrictions.or(
								Restrictions.sqlRestriction("{alias}.data->'specimen'->0->>'reference' ilike '%"
										+ specimen.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'specimen'->0->>'type' ilike '%" + specimen.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'specimen'->0->>'display' ilike '%"
										+ specimen.getValue() + "%'"),
								
								Restrictions.sqlRestriction("{alias}.data->'specimen'->1->>'reference' ilike '%"
										+ specimen.getValue() + "%'"),
								Restrictions.sqlRestriction(
										"{alias}.data->'specimen'->1->>'type' ilike '%" + specimen.getValue() + "%'"),
								Restrictions.sqlRestriction("{alias}.data->'specimen'->1->>'display' ilike '%"
										+ specimen.getValue() + "%'")
								);
					} else if (specimen.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'specimen' IS NULL"));
					} else if (!specimen.getMissing()) {
						orCond = Restrictions.or(Restrictions.sqlRestriction("{alias}.data->>'specimen' IS NOT NULL"));
					}

					disjunction.add(orCond);
				}
				criteria.add(disjunction);
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport issued
	 * 
	 * @param theMap   : search parameter "issued"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildIssuedCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("issued");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				for (IQueryParameterType params : values) {
					DateParam issued = (DateParam) params;
					String issuedFormat = issued.getValueAsString();
					if (issued.getPrefix() != null) {
						if (issued.getPrefix().getValue() == "gt") {
							criteria.add(
									Restrictions.sqlRestriction("{alias}.data->>'issued' > '" + issuedFormat + "'"));
						} else if (issued.getPrefix().getValue() == "lt") {
							criteria.add(
									Restrictions.sqlRestriction("{alias}.data->>'issued' < '" + issuedFormat + "'"));
						} else if (issued.getPrefix().getValue() == "ge") {
							criteria.add(
									Restrictions.sqlRestriction("{alias}.data->>'issued' >= '" + issuedFormat + "'"));
						} else if (issued.getPrefix().getValue() == "le") {
							criteria.add(
									Restrictions.sqlRestriction("{alias}.data->>'issued' <= '" + issuedFormat + "'"));
						} else {
							criteria.add(
									Restrictions.sqlRestriction("{alias}.data->>'issued' = '" + issuedFormat + "'"));
						}
					}
				}
			}
		}
	}

	/**
	 * This method builds criteria for DiagnosticReport date
	 * 
	 * @param theMap   : search parameter "date"
	 * @param criteria : for retrieving entities by composing Criterion objects
	 */
	private void buildEffectiveDateCriteria(SearchParameterMap theMap, Criteria criteria) {
		List<List<? extends IQueryParameterType>> list = theMap.get("date");
		if (list != null) {
			for (List<? extends IQueryParameterType> values : list) {
				for (IQueryParameterType params : values) {
					DateParam effectiveDate = (DateParam) params;
					String issuedFormat = effectiveDate.getValueAsString();
					if (effectiveDate.getPrefix() != null) {
						if (effectiveDate.getPrefix().getValue() == "gt") {
							criteria.add(Restrictions
									.sqlRestriction("{alias}.data->>'effectiveDateTime' > '" + issuedFormat + "'"));
						} else if (effectiveDate.getPrefix().getValue() == "lt") {
							criteria.add(Restrictions
									.sqlRestriction("{alias}.data->>'effectiveDateTime' < '" + issuedFormat + "'"));
						} else if (effectiveDate.getPrefix().getValue() == "ge") {
							criteria.add(Restrictions
									.sqlRestriction("{alias}.data->>'effectiveDateTime' >= '" + issuedFormat + "'"));
						} else if (effectiveDate.getPrefix().getValue() == "le") {
							criteria.add(Restrictions
									.sqlRestriction("{alias}.data->>'effectiveDateTime' <= '" + issuedFormat + "'"));
						} else {
							criteria.add(Restrictions
									.sqlRestriction("{alias}.data->>'effectiveDateTime' = '" + issuedFormat + "'"));
						}
					}
				}
			}
		}
	}

}
