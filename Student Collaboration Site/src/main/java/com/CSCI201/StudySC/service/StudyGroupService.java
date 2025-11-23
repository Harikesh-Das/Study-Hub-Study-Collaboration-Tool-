package com.CSCI201.StudySC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CSCI201.StudySC.Repository.StudyGroupRepository;
import com.CSCI201.StudySC.Repository.UserRepository;
import com.CSCI201.StudySC.model.StudyGroup;
import com.CSCI201.StudySC.model.User;

@Service
public class StudyGroupService {
	
	@Autowired
	private StudyGroupRepository studyGroupRepository;
	
	@Autowired
	private UserRepository userRepository;

     
    public StudyGroup createStudyGroup(StudyGroup studyGroup, User creator) {
    	 //HERE IS WHERE WE ADD THE CREATOR PART OF THE studyGroup. Use the email of the current signed in 
    								//user to find which user in the User table to set the creator equal to.
         // Check if the user already exists
         User currentUser = userRepository.findByEmail(creator.getUscEmail());
         if (currentUser != null) 
         {
             // Use the existing user
             studyGroup.setCreator(currentUser);
         } 
         else 
         {
             // Save the new user
             userRepository.save(creator);
         }
         
         System.out.println(studyGroup.toString());
         // Save the study group
         return studyGroupRepository.save(studyGroup);
     }

	public StudyGroup getStudyGroup(Integer groupId) {
		StudyGroup studyGroup = studyGroupRepository.findByGroupId(groupId);
		return studyGroup;
	}
    
   
     
    
}
