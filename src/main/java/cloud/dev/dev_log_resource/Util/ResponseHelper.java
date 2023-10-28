package cloud.dev.dev_log_resource.Util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public static final String SUCCESS = "success";
    public static final String FAIL = "failure";

    public static ResponseEntity<ResponseModel> success(){
        ResponseModel response = new ResponseModel("","",SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public static ResponseEntity<ResponseModel> success(Object data){
        ResponseModel response = new ResponseModel(data,"",SUCCESS, HttpStatus.OK.value());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public static ResponseEntity<ResponseModel> success(HttpStatus httpStatus, Object data){
        ResponseModel response = new ResponseModel(data,"",SUCCESS, httpStatus.value());
        return new ResponseEntity<>(response,httpStatus);
    }

    public static ResponseEntity<ResponseModel> badRequest(String errorCode, String statusDescription){
        ResponseModel response = new ResponseModel(errorCode, statusDescription, FAIL, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<ResponseModel> unAuthorized(String errorCode, String statusDescription){
        ResponseModel response = new ResponseModel(errorCode, statusDescription, FAIL, HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
    }



}
