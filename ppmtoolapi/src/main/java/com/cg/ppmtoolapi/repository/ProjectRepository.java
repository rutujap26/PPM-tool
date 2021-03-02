package com.cg.ppmtoolapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.cg.ppmtoolapi.domain.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

	Project findByProjectIdentifier(String projectIdentifier);
	
}
