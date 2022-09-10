package com.exams.microservices.appexamusers.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Asset {
  private byte[] content;
  private String contentType;
  private String url;
}
