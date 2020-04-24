package utils;

import beans.Student;
import net.sf.json.JSONObject;

public class JsonUtils{

    //将javabean对象转化为json
    public static String stringify(Object obj) {
        JSONObject object = new JSONObject();
        object.put("info",obj);
        return object.toString();
    }

}
