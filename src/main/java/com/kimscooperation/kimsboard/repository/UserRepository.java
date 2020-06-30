package com.kimscooperation.kimsboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimscooperation.kimsboard.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
