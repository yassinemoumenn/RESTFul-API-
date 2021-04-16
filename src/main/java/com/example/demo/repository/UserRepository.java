package com.example.demo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserEntity;
@Repository
public interface UserRepository extends PagingAndSortingRepository <UserEntity, Long> {
UserEntity findByEmail(String email);
UserEntity findByUserId(String userId);

}
