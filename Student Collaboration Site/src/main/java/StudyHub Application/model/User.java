package com.CSCI201.StudySC.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {
	
	 @Id
	 private String email;
	
	 private String name;
	 private String password;
	 
	 @OneToMany(mappedBy = "creator", cascade = CascadeType.PERSIST)
	  private List<StudyGroup> studyGroups;
	  
	 @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	  private List<StudyGroupMembers> studyGroupMembers;
    
	 public User() {
		 
	 }
    // Getter and setter for fullName
    public String getFullName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.name = fullName;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for uscEmail
    public String getUscEmail() {
        return email;
    }

    public void setUscEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "fullName='" + name + '\'' +
                ", password='" + password + '\'' +
                ", uscEmail='" + email + '\'' +
                '}';
    }
    
}