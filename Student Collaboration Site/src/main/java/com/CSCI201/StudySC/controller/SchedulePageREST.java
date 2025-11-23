package com.CSCI201.StudySC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CSCI201.StudySC.Repository.StudyGroupMembersRepository;
import com.CSCI201.StudySC.Repository.StudyGroupRepository;
import com.CSCI201.StudySC.Repository.UserRepository;
import com.CSCI201.StudySC.model.StudyGroup;
import com.CSCI201.StudySC.service.StudyGroupMembersService;
import jakarta.transaction.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
public class SchedulePageREST {

    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/StudySC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Daniel7504!";
 
    @Autowired
    private StudyGroupRepository studyGroupRepository;


    @GetMapping("/SchedulePageServlet")
    public List<StudyGroup> doGet() {
        List<StudyGroup> studyGroups = new ArrayList<>();
        List<StudyGroup> newStudyGroups = new ArrayList<>();
        System.out.println("made it to get function");
        //return studyGroups;
        studyGroups = studyGroupRepository.findAll();
        for (int i = 0; i < studyGroups.size(); i++){
            StudyGroup ourGroup = studyGroups.get(i);

            StudyGroup studyGroup = new StudyGroup(null,
            ourGroup.getCoursename(), ourGroup.getMeetingDate(), ourGroup.getMeetingTimeStart(), ourGroup.getCapacity(),
            ourGroup.getLocation());
            studyGroup.setCourseId(i+1);

            newStudyGroups.add(studyGroup);
        }
        
       
        return newStudyGroups;

    }

   
    
    @Autowired
    private StudyGroupMembersService studyGroupMembersService;
    
   
    //Needed to annotate this as transactional.
	@Transactional
	@PostMapping("/addToStudyMembers")
	public ResponseEntity<String> createStudyGroupMembers(@RequestParam Integer courseId, @RequestParam String email) {
	    try {
	    	System.out.println("I GOT IN THE CREATE MEMBER FUNCTION!!!!");
	    	System.out.println("Received request with groupId: " + courseId + ", email: " + email);

	    	//Calls the helper function that is implemented in the StudyGroupMembersService file.
	        if (studyGroupMembersService.createStudyGroupMembers(courseId, email)) {
	        	return new ResponseEntity<>("successfully added StudyGroupMembers", HttpStatus.CREATED); 
	        }
	        else {
	        	return new ResponseEntity<>("Failed to add", HttpStatus.CREATED); 
	        }
	        
	        //Then return a String to the JS file that says everything went well... CAN PROBABLY CHANGE THIS RESPONSE TO SOMETHING ELSE THEN USE IT IN THE JAVASCRIPT FILE
	        //TO CHANGE HOW THE JOIN BUTTON LOOKS WHEN YOU RECEIVE THE RESPONSE
	        
	    } catch (Exception e) {
	        // Handle exceptions and return an error response
	        return new ResponseEntity<>("Failed to create StudyGroupMembers: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    
    
    











	//OLD ATTEMPTS.............. BAD
	
//  @PostMapping("/addToStudyMembers")
//  public ResponseEntity<Void> createMember(@RequestParam("courseId") Integer courseId, @RequestParam("email") String email)
//  {
  	
//  	 // Fetch existing User and StudyGroup instances based on the provided values
//      User user = userService.getUserByEmail(email);
//      System.out.println(user.toString());
//      StudyGroup studyGroup = studyGroupService.getStudyGroup(courseId);
//      System.out.println(studyGroup.toString());
//      
//      if (user != null && studyGroup != null) {
//          // Create a new StudyGroupMembers instance and set the User and StudyGroup
//          StudyGroupMembers studyGroupMember = new StudyGroupMembers();
//       //   studyGroupMembersService.saveStudyGroupMember(studyGroupMember, user, courseId);
////          studyGroupMember.createStudyGroupMembers(courseId, email);
//          return ResponseEntity.status(HttpStatus.CREATED).build();
//      } else {
//          // Handle the case where the User or StudyGroup is not found
//          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//      }
//  }





//    @PostMapping("/addToStudyMembers")
//    public ResponseEntity<Void> createMember(@RequestBody StudyGroupMembers studyGroupMember) {
//    	
//    	System.out.println("I got in here!!!!!!");
//        studyGroupMembersService.saveStudyGroupMember(studyGroupMember);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
//    
//    @PostMapping("/addToStudyMembers")
//    public ResponseEntity<String> addToStudyMembers(@RequestBody StudyGroupMembersRequest request) {
//        // Validate request parameters
//
//    	//We have curr saved in other controller, make function for study group table that takes in find by id (email) return study group. 
//    	//System.out.println("We in this bitch"); 
//        // Create StudyGroupMembers entity
////        StudyGroupMembers studyGroupMembers = new StudyGroupMembers();
////        studyGroupMembers.setStudyGroup(request.getStudyGroup());
////        studyGroupMembers.setUser(request.getUser());
////        studyGroupMembers.setEntry(2);
//        
//        // Save to the database
////        studyGroupMembersRepository.save(studyGroupMembers);
//
////        return ResponseEntity.ok("Joined study group successfully");
//    }
//    
//    
//    @PostMapping("/addToStudyMembers")
//    public ResponseEntity<Void> createMember(@RequestBody StudyGroupMembers studyGroupMember) {
//        try {
//            // Assuming you have the necessary information to create a new StudyGroupMembers instance
//            StudyGroupMembers newMember = new StudyGroupMembers();
//            
//            // Set the StudyGroup and User entities
//            newMember.setStudyGroup(/* Your StudyGroup instance */);
//            newMember.setUser(/* Your User instance */);
//
//            // Save the StudyGroupMembers instance
//            studyGroupMembersService.saveStudyGroupMember(newMember);
//
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        } catch (Exception e) {
//            // Handle exceptions appropriately
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
