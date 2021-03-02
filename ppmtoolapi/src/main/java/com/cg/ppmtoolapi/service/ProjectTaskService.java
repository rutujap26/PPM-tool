package com.cg.ppmtoolapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ppmtoolapi.domain.Backlog;
import com.cg.ppmtoolapi.domain.Project;
import com.cg.ppmtoolapi.domain.ProjectTask;
import com.cg.ppmtoolapi.exceptions.ProjectNotFoundException;
import com.cg.ppmtoolapi.repository.BacklogRepository;
import com.cg.ppmtoolapi.repository.ProjectRepository;
import com.cg.ppmtoolapi.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTastRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	
	public ProjectTaskService(ProjectTaskRepository projectTastRepository) {
		super();
		this.projectTastRepository = projectTastRepository;
	}

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		try {
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			projectTask.setBacklog(backlog);
			
			Integer backlogSequence = backlog.getPTSequence();
			backlogSequence++;
			backlog.setPTSequence(backlogSequence);
			
			projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			if(projectTask.getPriority()==null)
				projectTask.setPriority(3);
			
			if(projectTask.getStatus()==""|| projectTask.getStatus()==null)
				projectTask.setStatus("TO_DO");
			
			return projectTastRepository.save(projectTask);
		} catch(Exception e) {
			throw new ProjectNotFoundException("Specified Project Not Found, Please check your input");
		}
	}
	
	public Iterable<ProjectTask> findBackogById(String projectIdentifier){
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier);
		if (project == null) {
			throw new ProjectNotFoundException("Project with id: "+projectIdentifier.toUpperCase()+" does not exists");
		}
		return projectTastRepository.findByProjectIdentifierOrderByPriority(projectIdentifier);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
		Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with id: "+backlog_id.toUpperCase()+" does not exists");
		}
		
		ProjectTask projectTask = projectTastRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("Project Task with id: "+pt_id.toUpperCase()+" does not exists");
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Backlog id: "+backlog_id.toUpperCase()+" does not match with project identifier "+projectTask.getProjectIdentifier().toUpperCase());
		}
		return projectTask;
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updateProjectTask, String backlog_id, String pt_id) {
		//Find the existing project task
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		//Replace project task with updateProjectTask
		projectTask = updateProjectTask;
		//Save the projectTask
		return projectTastRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		Backlog backlog = projectTask.getBacklog();
		List<ProjectTask> pts = backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTastRepository.delete(projectTask);
	}
}
