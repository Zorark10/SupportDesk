package com.project.SupportDesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SupportDesk.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
