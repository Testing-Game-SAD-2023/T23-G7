package com.reeverse.gseven.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reeverse.gseven.model.Role;

public interface RoleRepository extends MongoRepository<Role,String>{
	Role findByName(String name);
}
