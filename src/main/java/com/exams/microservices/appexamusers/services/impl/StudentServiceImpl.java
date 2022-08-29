package com.exams.microservices.appexamusers.services.impl;

import com.exams.microservices.appexamusers.repositories.StudentRepository;
import com.exams.microservices.appexamusers.services.StudentService;
import com.exams.microservices.libcommonmicroservices.services.impl.GenericServiceImpl;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class StudentServiceImpl extends GenericServiceImpl<Student, StudentRepository> implements
    StudentService {

  public StudentServiceImpl(StudentRepository repository) {
    super(repository);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Student> findByNameOrLastname(String name) {
    return this.repository.findByNameOrLastname(name);
  }
}
