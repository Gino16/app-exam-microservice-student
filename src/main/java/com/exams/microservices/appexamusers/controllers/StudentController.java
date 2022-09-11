package com.exams.microservices.appexamusers.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.exams.microservices.appexamusers.models.entities.Asset;
import com.exams.microservices.appexamusers.services.StudentService;
import com.exams.microservices.appexamusers.utils.S3Util;
import com.exams.microservices.libcommonmicroservices.controllers.GenericController;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StudentController extends GenericController<StudentService, Student> {

  public StudentController(StudentService service, AmazonS3Client amazonS3Client) {
    super(service);
  }

  @GetMapping("/students-by-course")
  public ResponseEntity<?> getStudentsByCourse(@RequestParam List<Long> ids) {
    return ResponseEntity.ok(this.service.findAllByIds(ids));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@Valid @RequestBody Student student, BindingResult result,
      @PathVariable Long id) {

    if (result.hasErrors()) {
      return this.validate(result);
    }

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

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    Optional<Student> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Student student = o.get();
    if (student.getPhoto() != null) {
      student.setPhotoUrl(S3Util.BUCKET_URL + "/" + student.getPhoto());
    }
    return super.findById(id);
  }

  @PostMapping("/save-with-photo")
  public ResponseEntity<?> saveWithPhoto(@Valid Student student, BindingResult result,
      @RequestBody MultipartFile file) {
    if (!file.isEmpty()) {
      student.setPhoto(this.service.savePhoto(file).get("filename").toString());
    }
    return super.save(student, result);
  }

  @PostMapping("/upload/{id}")
  public ResponseEntity<?> upload(@RequestBody MultipartFile file, @PathVariable Long id) {

    Optional<Student> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    Student studentDb = o.get();
    if (studentDb.getPhoto() != null) {
      this.service.deletePhoto(studentDb.getPhoto());
    }
    studentDb.setPhoto(this.service.savePhoto(file).get("filename").toString());
    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(studentDb));
  }

  @DeleteMapping("/uploads/img/{id}")
  public ResponseEntity<?> deletePhoto(@PathVariable Long id) {
    Optional<Student> o = this.service.findById(id);
    if (o.isEmpty() || o.get().getPhoto() == null) {
      return ResponseEntity.notFound().build();
    }

    this.service.deletePhoto(o.get().getPhoto());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/see-photo/{id}")
  public ResponseEntity<ByteArrayResource> seePhoto(@PathVariable Long id) {
    Optional<Student> o = this.service.findById(id);
    if (o.isEmpty() || o.get().getPhoto() == null) {
      return ResponseEntity.notFound().build();
    }
    Asset asset = this.service.getPhoto(o.get().getPhoto());
    return ResponseEntity.ok()
        .header("Content-Type", asset.getContentType())
        .body(new ByteArrayResource(asset.getContent()));
  }

}
