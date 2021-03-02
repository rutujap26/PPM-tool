package com.cg.ppmtoolapi.tests;


import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.ppmtoolapi.repository.ProjectRepository;
import com.cg.ppmtoolapi.repository.ProjectTaskRepository;
import com.cg.ppmtoolapi.service.ProjectTaskService;

public class ProjectTaskTest {

	ProjectTaskService projectTaskService;
	
	@Mock
	ProjectTaskRepository projectTaskRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		projectTaskService = new ProjectTaskService(projectTaskRepository);
	}
}
