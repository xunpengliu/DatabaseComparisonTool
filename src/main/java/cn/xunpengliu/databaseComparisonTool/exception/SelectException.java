package cn.xunpengliu.databaseComparisonTool.exception;

/**
 * Created by LXP on 2017/4/19.
 * select exception
 */

public class SelectException extends RuntimeException {
    public SelectException(){}
    public SelectException(String s){
        super(s);
    }
}
