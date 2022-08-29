package com.exams.microservices.appexamusers.repositories;

import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {

  @Query("SELECT s FROM Student s WHERE s.name LIKE %?1% OR s.lastname LIKE %?1%")
  public List<Student> findByNameOrLastname(String name);
}
