package com.CSCI201.StudySC.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "StudyGroupMembers")
@Getter
@Setter
public class StudyGroupMembers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer entry;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "groupId", referencedColumnName = "groupId")
	private StudyGroup studyGroup;
	 
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_email", referencedColumnName = "email")
	private User user;

	public StudyGroupMembers() {
		
	}
	
	public StudyGroupMembers(StudyGroup studyGroup, User user) {
	    this.studyGroup = studyGroup;
	    this.user = user;
	}

	public void setStudyGroup(StudyGroup studyGroup) {
		// TODO Auto-generated method stub
		this.studyGroup = studyGroup;
		
	}
	
	public StudyGroup getStudyGroup() {
		// TODO Auto-generated method stub
		return studyGroup;
		
	}

	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user = user;
	}
	

}