package Model.Search;

import Model.Member;
import Model.MemberRegistry;

public class BirthMonthCriteria implements SearchCriteria {
	
	private int month;
	
	public BirthMonthCriteria(int month){
		this.month=month;
	}

	@Override
	public MemberRegistry meetCriteria(MemberRegistry members) {
		MemberRegistry filter_result = new MemberRegistry(); 
	      
	      for (Member m : members.getMemberList()) {
	    	  if (Integer.parseInt(m.getM_personal_number().substring(2, 4)) == month){
	            filter_result.addMember(m);
	         }
	      }
	      return filter_result;
	}

}
