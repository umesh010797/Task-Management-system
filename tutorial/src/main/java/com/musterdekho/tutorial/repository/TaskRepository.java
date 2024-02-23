package com.musterdekho.tutorial.repository;

import com.musterdekho.tutorial.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE " +
            "LOWER(t.title) LIKE %:keyword% OR " +
            "LOWER(t.description) LIKE %:keyword% OR " +
            "LOWER(t.assignTo.name) LIKE %:keyword%")
    List<Task> searchTasks(@Param("keyword") String keyword);
}
