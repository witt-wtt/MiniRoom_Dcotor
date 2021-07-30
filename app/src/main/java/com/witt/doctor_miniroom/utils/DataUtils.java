package com.witt.doctor_miniroom.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author zhangjinqi
 * @explain branch_first
 * @since 2020/5/8
 */
public class DataUtils {
    //import java.lang.reflect.Field;
    /** 获取Object对象，所有成员变量属性值 */
    public static HashMap<String,? extends Object> getObjAttrToMap(Object obj)
    {
        HashMap<String, Object> httpMap = new HashMap<>();
        // 获取对象obj的所有属性域
        if(null!=obj){
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields)
            {
                // 对于每个属性，获取属性名
                String varName = field.getName();
                try
                {
                    boolean access = field.isAccessible();
                    if(!access) field.setAccessible(true);

                    //从obj中获取field变量
                    Object o = field.get(obj);
                    httpMap.put(varName,o);
                    System.out.println("变量： " + varName + " = " + o);
                    if(!access) field.setAccessible(false);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        return httpMap;
    }
}
