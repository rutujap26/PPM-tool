package com.cg.ppmtoolapi.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.ppmtoolapi.domain.ProjectTask;
import com.cg.ppmtoolapi.service.MapValidationErrorService;
import com.cg.ppmtoolapi.service.ProjectTaskService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@ApiOperation(value="Add project task to backlog")
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTToBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backog_id ){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		ProjectTask projectTask1 = projectTaskService.addProjectTask(backog_id, projectTask);
		return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.OK);
	}
	
	@ApiOperation(value="Get project backlog by backlog id")
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
		return projectTaskService.findBackogById(backlog_id);
	}
	
	@ApiOperation(value="Get project task by backlog id and project task id")
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
		ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id);
		return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
	}
	
	@ApiOperation(value="Update project task")
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id) {
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationError(result);
		if(errorMap!=null) return errorMap;
		ProjectTask updatedProjectTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id);
		return new ResponseEntity<ProjectTask>(updatedProjectTask, HttpStatus.OK);
	}
	
	@ApiOperation(value="Delete project task")
	@DeleteMapping
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
		projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);
		return new ResponseEntity<String>("Project task with id "+pt_id.toUpperCase()+" Removed Successfully!", HttpStatus.OK);
	}
}
