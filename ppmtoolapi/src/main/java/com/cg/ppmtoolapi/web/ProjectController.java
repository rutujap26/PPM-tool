package com.cg.ppmtoolapi.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ppmtoolapi.domain.Project;
import com.cg.ppmtoolapi.service.MapValidationErrorService;
import com.cg.ppmtoolapi.service.ProjectService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@ApiOperation(value="Create new project by provided project")
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
		
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if (errorMap != null) return errorMap;
		
		Project proj = projectService.saveOrUpdate(project);
		return new ResponseEntity<Project>(proj, HttpStatus.CREATED);	
	}
	
	@ApiOperation(value="Get project by project identifier")
	@GetMapping("/{projectIdentifier}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectIdentifier){
		Project project = projectService.findProjectByIdentifier(projectIdentifier);
		return new ResponseEntity<Project>(project,HttpStatus.OK);
	}
	
	@ApiOperation(value="Get all projects")
	@GetMapping("/all")
	public Iterable<Project> getAllProjects(){
		return projectService.getAllProjects();
	}
	
	@ApiOperation(value="Delete project by project identifier")
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> deleteProject(@PathVariable String projectId){
		projectService.deleteProjectByIdentifier(projectId);
		return new ResponseEntity<String>("Project with Id: "+projectId+" was deleted",HttpStatus.OK);
	}
}
