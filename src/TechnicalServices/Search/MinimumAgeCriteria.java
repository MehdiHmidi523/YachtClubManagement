package TechnicalServices.Search;

import Model.Member;
import Model.MemberRegistry;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


public class MinimumAgeCriteria implements SearchCriteria {
	
	private int age;
	
	public MinimumAgeCriteria(int age){
		this.age = age;
	}

	@Override
   public MemberRegistry meetCriteria(MemberRegistry members) {
      MemberRegistry filter_result = new MemberRegistry(); 
      LocalDate today = LocalDate.now();
      
      for (Member m : members.getMemberList()) {
			LocalDate memberDate = LocalDate.of(Integer.parseInt(19+ m.getM_personal_number().substring(0, 2)),
												 Integer.parseInt(m.getM_personal_number().substring(2, 4)),
												 Integer.parseInt(m.getM_personal_number().substring(4, 6)) );
			if (ChronoUnit.YEARS.between(memberDate, today) >= age){
				System.err.println(memberDate + " : " + today);
				System.err.println(ChronoUnit.YEARS.between(memberDate, today));
	            filter_result.addMember(m);
	         }
      }
      return filter_result;
   }


}
