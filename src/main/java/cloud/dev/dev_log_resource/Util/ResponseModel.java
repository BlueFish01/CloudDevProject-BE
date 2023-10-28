package cloud.dev.dev_log_resource.Util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
    private Object response;
    private String description;
    private String status;
    private int status_code;
}
