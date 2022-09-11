package com.exams.microservices.appexamusers.services;

import com.exams.microservices.appexamusers.models.entities.Asset;
import com.exams.microservices.libcommonmicroservices.services.GenericService;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;


public interface StudentService extends GenericService<Student> {
  public List<Student> findByNameOrLastname(String name);

  public Iterable<Student> findAllByIds(Iterable<Long> ids);

  public Map<String, Object> savePhoto(MultipartFile file);

  public Asset getPhoto(String filename);

  public void deletePhoto(String filename);

  public void deleteStudent(@PathVariable Long idStudent);
}
