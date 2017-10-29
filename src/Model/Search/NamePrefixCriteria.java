package Model.Search;

import Model.Member;
import Model.MemberRegistry;

public class NamePrefixCriteria implements SearchCriteria {
	
	private String namePrefix;
	public NamePrefixCriteria(String namePrefix){
			this.namePrefix = namePrefix;
		}

	@Override
	public MemberRegistry meetCriteria(MemberRegistry members) {
		MemberRegistry filter_result = new MemberRegistry();
		for (Member m : members.getMemberList())
			if (m.getM_name().startsWith(namePrefix)){ filter_result.addMember(m); }
	      return filter_result;
	   }
}
