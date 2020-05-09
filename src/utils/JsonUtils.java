package utils;

import beans.Course;
import beans.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtils {

    //将javabean对象转化为json
    public static String stringify(Object obj) {
        JSONObject object = new JSONObject();
        object.put("info", obj);
        return object.toString();
    }

    //将javabean数组转化为json
    public static String stringify(Object[] course) {
        JSONArray array = JSONArray.fromObject(course);
        return array.toString();
    }

    //将String类型数据转化为json
    public static String stringify(String[] major) {
        JSONArray array = JSONArray.fromObject(major);
        return array.toString();
    }
}
