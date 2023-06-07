package com.testgame.gseven.model.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testgame.gseven.model.dto.Role;

public interface IRoleDAO extends MongoRepository<Role,String>{
	Role findByName(String name);
}
