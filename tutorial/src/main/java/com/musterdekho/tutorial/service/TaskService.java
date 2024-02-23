package com.musterdekho.tutorial.service;

import com.musterdekho.tutorial.entity.Task;
import com.musterdekho.tutorial.entity.User;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    public List<Task> findAll();
    Task getTaskById(Long id);
    Task updateTask (Long id,Task task);
    void deleteTask(Long taskId);
    List<Task> searchTasks(String keyword);

}
