package com.exams.microservices.appexamusers.services.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.exams.microservices.appexamusers.models.entities.Asset;
import com.exams.microservices.appexamusers.repositories.StudentRepository;
import com.exams.microservices.appexamusers.services.StudentService;
import com.exams.microservices.appexamusers.utils.S3Util;
import com.exams.microservices.libcommonmicroservices.services.impl.GenericServiceImpl;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service

public class StudentServiceImpl extends GenericServiceImpl<Student, StudentRepository> implements
    StudentService {

  private final AmazonS3Client amazonS3Client;

  public StudentServiceImpl(StudentRepository repository, AmazonS3Client amazonS3Client) {
    super(repository);
    this.amazonS3Client = amazonS3Client;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Student> findByNameOrLastname(String name) {
    return this.repository.findByNameOrLastname(name);
  }

  @Override
  public Map<String, Object> savePhoto(MultipartFile file) {
    String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
    String filename = UUID.randomUUID() + "." + extension;

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());

    try {
      PutObjectRequest putObjectRequest = new PutObjectRequest(S3Util.BUCKET_NAME, filename,
          file.getInputStream(), metadata);
      amazonS3Client.putObject(putObjectRequest);
      return Map.of(
          "filename", filename,
          "url", String.format("%s/%s", S3Util.BUCKET_URL, filename)
      );
    } catch (IOException e) {
      return Map.of(
          "error", "Error uploading file",
          "message", "Error uploading file: " + e.getMessage()
      );
    }

  }

  @Override
  public Asset getPhoto(String filename) {
    S3Object s3Object = amazonS3Client.getObject(S3Util.BUCKET_NAME, filename);
    ObjectMetadata metadata = s3Object.getObjectMetadata();

    try {
      S3ObjectInputStream inputStream = s3Object.getObjectContent();
      byte[] content = inputStream.readAllBytes();
      return new Asset(content, metadata.getContentType(), S3Util.BUCKET_URL + "/" + filename);
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public void deletePhoto(String filename) {
    amazonS3Client.deleteObject(S3Util.BUCKET_NAME, filename);
  }
}
