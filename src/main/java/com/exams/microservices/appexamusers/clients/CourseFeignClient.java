package com.exams.microservices.appexamusers.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service")
public interface CourseFeignClient {

  @DeleteMapping("/delete-student/{idStudent}")
  public void deleteStudent(@PathVariable Long idStudent);
}
