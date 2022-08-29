package com.exams.microservices.appexamusers.controllers;

import com.exams.microservices.appexamusers.services.StudentService;
import com.exams.microservices.libcommonmicroservices.controllers.GenericController;
import com.exams.microservices.libcommonmicroservices.services.GenericService;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.Optional;
import lombok.AllArgsConstructor;
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
public class StudentController extends GenericController<StudentService, Student> {

  public StudentController(StudentService service) {
    super(service);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@RequestBody Student student, @PathVariable Long id) {
    Optional<Student> o = this.service.findById(id);

    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Student studentDb = o.get();
    studentDb.setName(student.getName());
    studentDb.setLastname(student.getLastname());
    studentDb.setEmail(student.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(studentDb));
  }

  @GetMapping("/filter/{name}")
  public ResponseEntity<?> filterByName(@PathVariable String name) {
    return ResponseEntity.ok().body(this.service.findByNameOrLastname(name));
  }

}
