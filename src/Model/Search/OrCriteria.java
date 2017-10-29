package Model.Search;


import Model.Member;
import Model.MemberRegistry;

public class OrCriteria implements SearchCriteria {
    private SearchCriteria criteria;
    private SearchCriteria otherCriteria;

    public OrCriteria(SearchCriteria criteria, SearchCriteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public MemberRegistry meetCriteria(MemberRegistry members) {
        MemberRegistry firstCriteriaItems = criteria.meetCriteria(members);
        MemberRegistry otherCriteriaItems = otherCriteria.meetCriteria(members);
        for (Member m : otherCriteriaItems.getMemberList()) {
            if(!firstCriteriaItems.containsMember(m.getM_Id()))
                firstCriteriaItems.addMember(m);
        }
        return firstCriteriaItems;
    }
}
