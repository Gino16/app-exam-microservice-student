package com.exams.microservices.appexamusers.services.impl;

import com.exams.microservices.appexamusers.models.entities.Student;
import com.exams.microservices.appexamusers.repositories.StudentRepository;
import com.exams.microservices.appexamusers.services.StudentService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;


  @Transactional(readOnly = true)
  @Override
  public Iterable<Student> findAll() {
    return studentRepository.findAll();
  }

  @Transactional(readOnly = true)

  @Override
  public Optional<Student> findById(Long id) {
    return studentRepository.findById(id);
  }

  @Transactional
  @Override
  public Student save(Student student) {
    return studentRepository.save(student);
  }

  @Override
  public void deleteById(Long id) {
    studentRepository.deleteById(id);
  }
}
