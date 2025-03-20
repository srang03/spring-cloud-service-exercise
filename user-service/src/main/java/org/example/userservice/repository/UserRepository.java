package org.example.userservice.repository;

import org.example.userservice.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserId(String userid);
    UserEntity findByEmail(String email);
    UserEntity findByName(String name);
}
