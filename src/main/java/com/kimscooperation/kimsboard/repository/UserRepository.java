package com.kimscooperation.kimsboard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimscooperation.kimsboard.entity.user.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	Optional<Users> findByUserNum(Long userNum);

	Optional<Users> findByUserId(String userId);

	Optional<Users> findByUserIdAndProvider(String id, String provider);
}
