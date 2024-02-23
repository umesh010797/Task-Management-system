package com.musterdekho.tutorial.controller;


import com.musterdekho.tutorial.dto.UserDto;
import com.musterdekho.tutorial.entity.Task;
import com.musterdekho.tutorial.entity.User;
import com.musterdekho.tutorial.repository.TaskRepository;
import com.musterdekho.tutorial.service.TaskService;
import com.musterdekho.tutorial.serviceimpl.TaskServiceImpl;
import com.musterdekho.tutorial.serviceimpl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/addtask")
    public String showRegistrationForm(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        Task task=new Task();
        model.addAttribute("addtask", task);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        System.out.println("inside task controller line no.37"+model.getAttribute("title"));
        return "addtask";
    }

    @PostMapping("/task/save")
    public String addTask(@Valid @ModelAttribute("addtask") Task task,
                          BindingResult result,
                          Model model) {
        System.out.println("inside task controller line no.42");
        System.out.println("inside task controller line no.43"+task.toString());
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        System.out.println("inside task controller line no.46"+users.get(0).getName()+users.get(0).getName()+users.get(0).getName());

        if (result.hasErrors()) {

            model.addAttribute("users", users);
            System.out.println(users.toString());

            return "addtask";
        }

        taskService.createTask(task);


        return "redirect:/addtask?success";
    }

    @GetMapping("/management")
    public String showTaskManagementPage(Model model) {
        List<Task> tasks = taskService.findAll();
        System.out.println(tasks.toString());
        model.addAttribute("tasks", tasks);
        return "activetask";
    }
    @GetMapping("/search")
    public String searchTasks(@RequestParam("keyword") String keyword, Model model) {
        // Call the service method to search for tasks based on the keyword
        List<Task> tasks = taskService.searchTasks(keyword);

        // Add the searched tasks to the model to be displayed in the HTML
        model.addAttribute("tasks", tasks);

        // Return the view to display the search results
        return "activetask"; // Assuming the view name is "task-list.html"
    }



    // Controller method to handle editing a task
    @GetMapping("/editTask/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task updatedTask = taskService.getTaskById(id);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("task", updatedTask);
        System.out.println("inside task controller line no.94" + updatedTask.toString());
        System.out.println("inside task controller line no.95" + users.toString());
        //taskService.updateTask(id, updatedTask);

        return "edittask";
    }

    // Controller method to handle updating a task
    @PostMapping("/updateTask/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task updatedTask, RedirectAttributes redirectAttributes) {
        // Assuming you have a TaskService injected
        taskService.updateTask(id, updatedTask);
        System.out.println("inside task controller line no.106" + updatedTask.toString());

        // Redirecting with a flash attribute to display a message on the redirected page
        redirectAttributes.addFlashAttribute("message", "Task updated successfully.");

        return "redirect:/management";
    }

    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        System.out.println("In controller line no.125");
        return "redirect:/management"; // Redirect to the main page after deleting the task
    }


}
