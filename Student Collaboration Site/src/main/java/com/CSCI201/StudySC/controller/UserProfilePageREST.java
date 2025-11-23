package com.CSCI201.StudySC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CSCI201.StudySC.Repository.UserRepository;
import com.CSCI201.StudySC.model.StudyGroup;
import com.CSCI201.StudySC.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserProfilePageREST {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/StudySC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Daniel7504!";

    @GetMapping("/UserStudyGroupServlet")
    public List<StudyGroup> doGet(@RequestParam("email") String email) {
        List<StudyGroup> studyGroups = new ArrayList<>();

        System.out.println(email);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null; // Consider handling this better
        }

        String query = "SELECT * FROM StudySC.study_group_members WHERE user_email = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String group_id = rs.getString("group_id");
               // System.out.println(group_id);
                query = "SELECT * FROM StudySC.study_group WHERE group_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(query);
                ps2.setString(1, group_id);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    StudyGroup studyGroup = new StudyGroup(
                        null,
                        rs2.getString("coursename"),
                        rs2.getString("meeting_date"),
                        rs2.getString("meeting_time_start"),
                        rs2.getInt("capacity"),
                        rs2.getString("location")
                    );
                    studyGroups.add(studyGroup);           
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); // Consider a better way to handle this exception
            return null;
        }

        return studyGroups;
    }
    
    @GetMapping("/deleteUser/{email}")
    public String deleteUser(@PathVariable String email) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Error: Unable to delete user.";
        }

        String deleteUserQuery = "SELECT * FROM StudySC.study_group WHERE creator_email = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(deleteUserQuery)) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            System.out.println("Made it here!!!!!!  1");
            while (rs.next()) {
                String groupID = rs.getString("group_id");
                deleteUserQuery = "SELECT * FROM StudySC.study_group_members WHERE group_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(deleteUserQuery);
                ps2.setString(1, groupID);
                ResultSet rs2 = ps2.executeQuery();
                String newEmail = "creator@usc.edu";
                System.out.println("Made it here!!!!!!  1");
                while (rs2.next()) {
                    String makeEmail = rs2.getString("user_email");
                    if (!makeEmail.equals(email)) {
                        newEmail = makeEmail;
                        break;
                    }
                }
                System.out.println("Made it here!!!!!!  1");
                if (!newEmail.equals("creator@usc.edu")) {
                    deleteUserQuery = "UPDATE StudySC.study_group SET creator_email = ? WHERE group_id = ?";
                    PreparedStatement ps3 = conn.prepareStatement(deleteUserQuery);
                    ps3.setString(1, newEmail);
                    ps3.setString(2, groupID);
                    ps3.executeUpdate();
                    System.out.println("enter here 4");
                }
                else {
                    deleteUserQuery = "DELETE FROM StudySC.study_group_members WHERE group_id = ?";
                    PreparedStatement ps3 = conn.prepareStatement(deleteUserQuery);
                    ps3.setString(1, groupID);
                    ps3.executeUpdate();
                    System.out.println("enter here 4");

                    deleteUserQuery = "DELETE FROM StudySC.study_group WHERE group_id = ?";
                    ps3 = conn.prepareStatement(deleteUserQuery);
                    ps3.setString(1, groupID);
                    ps3.executeUpdate();
                    System.out.println("enter here 4");
                }
            }

            deleteUserQuery = "DELETE FROM StudySC.study_group_members WHERE user_email = ?";
            PreparedStatement ps4 = conn.prepareStatement(deleteUserQuery);
            ps4.setString(1, email);
            ps4.executeUpdate();

            deleteUserQuery = "DELETE FROM StudySC.user WHERE email = ?";
            PreparedStatement ps5 = conn.prepareStatement(deleteUserQuery);
            ps5.setString(1, email);
            ps5.executeUpdate();

            return "DELETE ACCOUNT Successfully";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Unable to delete user.";
        }
    }
    
    @GetMapping("/deleteUserFromGroup/{email}/{coursename}")
    public String deleteUserFromGroup(@PathVariable String email, @PathVariable String coursename) {
        System.out.println("Enter here!");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return "Error: Unable to delete user from group.";
        }
        //System.out.println("enter here!");
        String deleteUserFromGroupQuery = "SELECT * FROM StudySC.study_group WHERE coursename = ?";
        String deleteMemberQuery = "DELETE FROM StudySC.study_group_members WHERE user_email = ? AND group_id = ?";
        //System.out.println("email is : " + email);
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement ps = conn.prepareStatement(deleteUserFromGroupQuery)) {
            ps.setString(1, coursename);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String groupId = rs.getString("group_id");
                try (PreparedStatement deletePs = conn.prepareStatement(deleteMemberQuery)) {
                    deletePs.setString(1, email);
                    deletePs.setString(2, groupId);
                    int deletedRows = deletePs.executeUpdate();
    
                    if (deletedRows > 0) {
                        System.out.println("Deleted user " + email + " from group " + groupId);
                    } else {
                        System.out.println("No such user " + email + " in group " + groupId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: Unable to delete user from group.";
        }
        return "Exit From The Study Group Successfully";
    }

    @Autowired
    UserRepository userRepo;

    @GetMapping ("/getUserName")
    public String getUserNameFromEmail(String email){
        User user = userRepo.findByEmail(email);
        return user.getName();
    }
}