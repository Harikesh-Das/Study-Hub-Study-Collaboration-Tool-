package com.CSCI201.StudySC.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CSCI201.StudySC.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String uscEmail);
}
 