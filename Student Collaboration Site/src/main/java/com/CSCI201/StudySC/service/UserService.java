package com.CSCI201.StudySC.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.CSCI201.StudySC.Repository.UserRepository;
import com.CSCI201.StudySC.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	
	//Source: "How to make it so that a user can't use an already existing email in my table to sign up?" prompt, ChatGPT (4 lines), Nov. 22 2023
	  //https://chat.openai.com/c/54dae8bb-4524-4684-b6a3-4c9d68fdbd0c
	    public User create(User user) {
	    	//System.out.println("Create method is invoked");
	    	//System.out.println("userRepository is........... " + userRepository);
	        Optional<User> existingUser = userRepository.findById(user.getUscEmail());

	        if (!existingUser.isPresent()) {
	        	//System.out.println("We adding that itttt");
	        	// No existing user with the same email, proceed with saving
	            return userRepository.save(user);
	        }
	        else
	        {
	        	return null;
	        }
	    }
	    
	    
    public User getUserByEmail(String uscEmail) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudySC?user=root&password=Bwagner2003."); // add
            st = conn.prepareStatement("SELECT * FROM User WHERE email = ?");
            st.setString(1, uscEmail);
            rs = st.executeQuery();
            
            if (rs.next()) {
                user = new User();
                user.setFullName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setUscEmail(rs.getString("email"));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
            }
        }
        
        return user;
    }
    
}
  