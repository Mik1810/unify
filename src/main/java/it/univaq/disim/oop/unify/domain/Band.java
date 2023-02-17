package it.univaq.disim.oop.unify.domain;

import java.util.HashSet;
import java.util.Set;

public class Band extends Artist{
	
	private Set<Artist> members = new HashSet<>();

	public Set<Artist> getMembers() {
		return members;
	}
	
	public void setMembers(Set<Artist> members) {
		this.members = members;
	}

}
