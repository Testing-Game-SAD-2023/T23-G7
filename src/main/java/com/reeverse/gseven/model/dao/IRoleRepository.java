package com.reeverse.gseven.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.reeverse.gseven.model.dto.Role;

public interface IRoleRepository extends MongoRepository<Role,String>{
	Role findByName(String name);
}
