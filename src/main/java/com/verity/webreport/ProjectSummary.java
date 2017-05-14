package com.verity.webreport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectSummary {
	String name;
	List<CycleSummary> cycles;
	
	public ProjectSummary(){
		name = "N/A";
		cycles = new ArrayList<CycleSummary>();
	}
	
	public ProjectSummary(String name){
		this.name = name;
		cycles = new ArrayList<CycleSummary>();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public List<CycleSummary> getCycles(){
		return Collections.unmodifiableList(cycles);
	}
	
	public void addCycle(CycleSummary cycle){
		cycles.add(cycle);
	}
	
	public int indexOfCycleName(String cycleName){
		List<CycleSummary> list = getCycles();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getName().equalsIgnoreCase(cycleName))
				return i;
		}
		return -1;
	}
	

}
