package com.CSCI201.StudySC.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CSCI201.StudySC.model.StudyGroup;
import com.CSCI201.StudySC.model.StudyGroupMembers;

@Repository
public interface StudyGroupMembersRepository extends JpaRepository<StudyGroupMembers, Integer> {

	//List<StudyGroupMembers> findByStudyGroupGroup_Id(Integer groupId);
	
	public StudyGroupMembers findByStudyGroupGroupId(Integer groupId);
	public StudyGroupMembers findByStudyGroupGroupIdAndUserEmail(Integer groupId, String email);
} 