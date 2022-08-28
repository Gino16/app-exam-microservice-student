package com.exams.microservices.appexamusers.repositories;

import com.exams.microservices.appexamusers.models.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
