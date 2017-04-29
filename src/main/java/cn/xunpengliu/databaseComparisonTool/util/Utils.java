package cn.xunpengliu.databaseComparisonTool.util;

public class Utils {
    public static boolean isEqual(Object o1,Object o2){
        if(o1 == o2){
            return true;
        }else if(o1 == null && o2 != null){
            return false;
        }else if(o1 != null && o2 == null){
            return false;
        }

        return o1.equals(o2);
    }
}
