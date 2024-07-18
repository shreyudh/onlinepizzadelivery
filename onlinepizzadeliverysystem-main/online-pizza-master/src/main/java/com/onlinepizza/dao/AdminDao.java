package com.onlinepizza.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onlinepizza.model.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {
	
	Admin findByUserIdAndPassword(String userId, String password);

}
