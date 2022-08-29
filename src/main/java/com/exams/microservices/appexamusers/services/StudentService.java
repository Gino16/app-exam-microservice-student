package com.exams.microservices.appexamusers.services;

import com.exams.microservices.libcommonmicroservices.services.GenericService;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;


public interface StudentService extends GenericService<Student> {
  public List<Student> findByNameOrLastname(String name);

}
