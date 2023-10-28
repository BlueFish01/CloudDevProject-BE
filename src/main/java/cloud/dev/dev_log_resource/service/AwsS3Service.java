package cloud.dev.dev_log_resource.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class AwsS3Service {
    @Autowired
    AmazonS3 amazonS3;

    @Value("${spring.amazon.aws.s3bucket-name}")
    private String s3BucketName;

    public void putImage(MultipartFile imageMultipartFile, String imageFileName) throws IOException {

        File imageFile = convertMultiPartToFile(imageMultipartFile);

        amazonS3.putObject(s3BucketName, imageFileName,  imageFile);
    }







    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
