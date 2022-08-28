package com.exams.microservices.appexamusers.services;

import com.exams.microservices.appexamusers.models.entities.Student;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import java.util.Optional;


public interface StudentService {
    public Iterable<Student> findAll();

    public Optional<Student> findById(Long id);

    public Student save(Student student);

    public void deleteById(Long id);
}
