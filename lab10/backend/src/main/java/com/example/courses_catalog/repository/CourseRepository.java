package com.example.courses_catalog.repository;

import com.example.courses_catalog.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
