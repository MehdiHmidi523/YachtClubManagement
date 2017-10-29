package Model.Search;

import Model.MemberRegistry;

public class AndCriteria implements SearchCriteria {
	private SearchCriteria criteria;
	private SearchCriteria otherCriteria;

	public AndCriteria(SearchCriteria criteria, SearchCriteria otherCriteria) {
		this.criteria = criteria;
		this.otherCriteria = otherCriteria;
	}

	@Override
	public MemberRegistry meetCriteria(MemberRegistry members) {
		MemberRegistry firstCriteriaPersons = criteria.meetCriteria(members);
		return otherCriteria.meetCriteria(firstCriteriaPersons);
	}
}
