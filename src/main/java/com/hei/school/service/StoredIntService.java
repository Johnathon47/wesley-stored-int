package com.hei.school.service;

import com.hei.school.file.bucket.BucketComponent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoredIntService {
  private final BucketComponent bucketComponent;

  @SneakyThrows
  public String uploadStoredInt() {
    int randomInt = new Random().nextInt(1_000_000);
    var fileSuffix = ".txt";
    var filePreffix = "stored-int";
    var bucketKey = filePreffix + fileSuffix;

    File fileToUpload = File.createTempFile(filePreffix, fileSuffix);
    writeToFile(String.valueOf(randomInt), fileToUpload);

    bucketComponent.upload(fileToUpload, bucketKey);
    return bucketComponent.presign(bucketKey, Duration.ofMinutes(5)).toString();
  }

  private void writeToFile(String content, File file) throws IOException {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(content);
    }
  }
}
