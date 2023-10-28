package cloud.dev.dev_log_resource.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@Slf4j
public class AwsS3Service {
    @Autowired
    AmazonS3 amazonS3;

    @Value("${spring.amazon.aws.s3bucket-name}")
    private String s3BucketName;

    public void putImage(MultipartFile imageMultipartFile, String imageFileName) throws Exception {

        try{
            log.info("Start AwsS3Service.putImage()");
        }

        catch(Exception e){
            log.info("Error AwsS3Service.putImage()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End AwsS3Service.putImage()");
        }

        File imageFile = convertMultiPartToFile(imageMultipartFile);

        amazonS3.putObject(s3BucketName, imageFileName,  imageFile);
    }







    private File convertMultiPartToFile(MultipartFile file) throws Exception {

        try{
            log.info("Start AwsS3Service.convertMultiPartToFile()");

            File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }

        catch(Exception e){
            log.info("Error AwsS3Service.convertMultiPartToFile()");
            throw new Exception(HttpStatus.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
        finally {
            log.info("End AwsS3Service.convertMultiPartToFile()");
        }


    }

}
