package Model.Search;

import Model.Boat;
import Model.Member;
import Model.MemberRegistry;

public class BoatsTypeCriteria implements SearchCriteria {
	
	private Boat.Boatstype boatstype;
	
	public BoatsTypeCriteria(Boat.Boatstype boatstype){
		this.boatstype = boatstype;
	}

	@Override
	public MemberRegistry meetCriteria(MemberRegistry members) {
		MemberRegistry filter_result = new MemberRegistry();
	      
	      for (Member m : members.getMemberList()){
	    	  for (Boat b : m.getM_boats()){
	    		  if (b.getType().equals(boatstype))filter_result.addMember(m);
	    	  }
	      }
	   return filter_result;
	}

}
