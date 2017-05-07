package cn.xunpengliu.databaseComparisonTool.factory;


import cn.xunpengliu.databaseComparisonTool.core.Worker;
import cn.xunpengliu.databaseComparisonTool.core.analysis.impl.MySqlAnalysisImpl;
import cn.xunpengliu.databaseComparisonTool.core.command.impl.MySqlDbCommandImpl;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.DataSource;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.MySqlDataSource;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.model.DataSourceModel;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.model.MysqlDataSrouceModel;
import cn.xunpengliu.databaseComparisonTool.exception.NotSupportUrl;

import java.util.HashMap;
import java.util.Map;

public class Factory {

    public static String supportUrlPatten = "\\S+://\\S+/\\S+?username=\\S+&password=\\S+";

    public static Map<String, String> conversionUrl(String url) throws NotSupportUrl {
        if (!url.matches(supportUrlPatten)) {
            throw new NotSupportUrl();
        }
        Map<String, String> map = new HashMap<>();
        int temp = -1;
        temp = url.indexOf("://");
        String dbType = url.substring(0, temp);
        map.put("protocol", dbType);//add db type

        String s = url.substring(temp + 3);
        String[] d = s.split("/");
        temp = d[0].indexOf(':');
        if (temp == -1) {
            map.put("host", d[0]);
        }else{
            map.put("host", d[0].substring(0,temp));
            map.put("port", d[0].substring(temp+1));
        }

        d = d[1].split("\\?");
        map.put("databaseName",d[0]);

        String[] params = d[1].split("&");
        for (String s1 : params) {
            temp = s1.indexOf('=');
            map.put(s1.substring(0, temp), s1.substring(temp + 1));
        }
        return map;
    }

    public static DataSourceModel getDataSourceModel(Map<String, String> data) {
        String dbType = data.get("protocol");

        if (dbType.equalsIgnoreCase("mysql")) {
            MysqlDataSrouceModel dataSourceModel = new MysqlDataSrouceModel();
            dataSourceModel.setHost(data.get("host"));
            if (data.containsKey("port")) {
                dataSourceModel.setPort(Integer.valueOf(data.get("port")));
            } else {
                dataSourceModel.setPort(3306);
            }
            dataSourceModel.setUsername(data.get("username"));
            dataSourceModel.setPassword(data.get("password"));

            return dataSourceModel;
        }

        throw new IllegalArgumentException("未知的数据源数据");
    }

    public static Worker getWork(DataSourceModel dataSourceModel) {
        if (dataSourceModel instanceof MysqlDataSrouceModel) {
            Worker w = Worker.getWork(new MySqlDataSource(dataSourceModel), new MySqlDbCommandImpl(), new MySqlAnalysisImpl());
            return w;
        }

        throw new IllegalArgumentException("不支持的数据源");
    }
}
