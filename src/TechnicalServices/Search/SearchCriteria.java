package TechnicalServices.Search;

import Model.MemberRegistry;

/* Filter design pattern */
public interface SearchCriteria {
	   public MemberRegistry meetCriteria(MemberRegistry members);
}
