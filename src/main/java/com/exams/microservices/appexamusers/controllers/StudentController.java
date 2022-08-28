package com.exams.microservices.appexamusers.controllers;

import com.exams.microservices.appexamusers.models.entities.Student;
import com.exams.microservices.appexamusers.services.StudentService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

  private StudentService studentService;

  @GetMapping
  public ResponseEntity<?> list() {
    return ResponseEntity.ok().body(studentService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable("id") Long id) {
    Optional<Student> o = studentService.findById(id);

    return o.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(o.get());
  }

  @PostMapping
  public ResponseEntity<?> save(@RequestBody Student student) {
    return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(student));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@RequestBody Student student, @PathVariable Long id) {
    Optional<Student> o = studentService.findById(id);

    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Student studentDb = o.get();
    studentDb.setName(student.getName());
    studentDb.setLastname(student.getLastname());
    studentDb.setEmail(student.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(studentService.save(studentDb));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    studentService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
