package com.example.springdatarest.repo;

import com.example.springdatarest.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="user1")
public interface UserRepository extends CrudRepository<User, Long> {
}
