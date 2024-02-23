package com.musterdekho.tutorial.serviceimpl;

import com.musterdekho.tutorial.entity.Task;
import com.musterdekho.tutorial.entity.User;
import com.musterdekho.tutorial.repository.TaskRepository;
import com.musterdekho.tutorial.service.TaskService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Task createTask(Task task) {
        Task task1=new Task();
        task1.setTitle(task.getTitle());
        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task1.setDueDate(task.getDueDate());
        task1.setStatus(task.getStatus());
        task1.setPriority(task.getPriority());
        task1.setAssignTo(task.getAssignTo());
        return taskRepository.save(task1);
    }

    @Override
    public List<Task> findAll() {
        Sort sortByStatus = Sort.by(Sort.Direction.DESC,"status");
        return taskRepository.findAll(sortByStatus);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task updateTask(Long id,Task task) {
        Task task1=taskRepository.findById(id).get();
        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task1.setDueDate(task.getDueDate());
        task1.setAssignTo(task.getAssignTo());
        return taskRepository.save(task1);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public List<Task> searchTasks(String keyword) {
        return taskRepository.searchTasks(keyword);
    }
}

