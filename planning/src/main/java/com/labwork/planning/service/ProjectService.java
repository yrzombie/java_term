package com.labwork.planning.service;

import com.labwork.planning.model.Project;
import com.labwork.planning.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {

        project.getTasks().forEach(task -> task.setProject(project));
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project updateProject(Long id, String newName) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Проект не найден"));

        project.setName(newName);
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
    public Project updateProject(Long id, Project updatedProject) {
    Project project = projectRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Проект не найден"));
    
    project.setName(updatedProject.getName());
    project.getTasks().clear();
    for (var task : updatedProject.getTasks()) {
        task.setProject(project);
    }
    project.getTasks().addAll(updatedProject.getTasks());

    return projectRepository.save(project);
}
}
