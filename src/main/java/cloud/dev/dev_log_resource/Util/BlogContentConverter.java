package cloud.dev.dev_log_resource.Util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class BlogContentConverter implements DynamoDBTypeConverter<AttributeValue, Map<String, Object>> {
    @Override
    public AttributeValue convert(Map<String, Object> mapObject){
        System.out.println(mapObject);
        System.out.println(mapObject.values().toString());
        return new AttributeValue()
                .withSS(String.valueOf(mapObject));
    }

    @Override
    public Map<String, Object> unconvert(AttributeValue object){
        String objectS = object.getSS().get(0);
        System.out.println(objectS);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("blogContent", objectS);
        System.out.println(resultMap);
        return resultMap;
    }
}
