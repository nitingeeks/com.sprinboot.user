package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.User;

public interface UserDao extends JpaRepository<User , Long>{

}
