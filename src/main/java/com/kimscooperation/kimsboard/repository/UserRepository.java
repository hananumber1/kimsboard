package com.kimscooperation.kimsboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimscooperation.kimsboard.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
