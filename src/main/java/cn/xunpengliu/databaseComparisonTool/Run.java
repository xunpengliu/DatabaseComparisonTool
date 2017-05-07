package cn.xunpengliu.databaseComparisonTool;

import cn.xunpengliu.databaseComparisonTool.core.Worker;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.model.DataSourceModel;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.model.MysqlDataSrouceModel;
import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.ReportModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;
import cn.xunpengliu.databaseComparisonTool.core.report.Report;
import cn.xunpengliu.databaseComparisonTool.core.report.ReportImpl;
import cn.xunpengliu.databaseComparisonTool.exception.NotSupportUrl;
import cn.xunpengliu.databaseComparisonTool.factory.Factory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/5/6.
 * run class
 */

public class Run {
    public static void main(String[] args) throws IOException {
        if (args.length < 2 || !args[0].matches(Factory.supportUrlPatten) || !args[1].matches(Factory.supportUrlPatten)) {
            System.out.println("你必须提交源链接和目标链接");
            System.out.println("例如：mysql://host:port/DATABASE?username=USERNAME&password=PASSWORD mysql://host:port/DATABASE?username=USERNAME&password=PASSWORD");
            return;
        }
        String sourceUrl = args[0];
        String targetUrl = args[1];

        //解析源链接
        DataSourceModel sourceDs;
        Map<String, String> sourceData;
        try {
            sourceData = Factory.conversionUrl(sourceUrl);
            sourceDs = Factory.getDataSourceModel(sourceData);
        } catch (NotSupportUrl notSupportUrl) {
            System.out.println("源链接不合法");
            return;
        }

        DataSourceModel targetDs;
        Map<String, String> targetData;
        try {
            targetData = Factory.conversionUrl(targetUrl);
            targetDs = Factory.getDataSourceModel(targetData);
        } catch (NotSupportUrl notSupportUrl) {
            System.out.println("目标链接不合法");
            return;
        }

        Report reportTool = new ReportImpl();

        DatabaseModel sourceDb = new DatabaseModel();
        sourceDb.setDbName(sourceData.get("databaseName"));
        Worker sourceW = Factory.getWork(sourceDs);
        List<TableModel> sourceTables = sourceW.obtainTable(sourceDb);
        sourceDb.setTableModels(sourceTables);
        for (TableModel sourceTable : sourceTables) {
            sourceTable.setFields(sourceW.obtainTableInfo(sourceDb,sourceTable));
        }

        DatabaseModel targetDb = new DatabaseModel();
        targetDb.setDbName(targetData.get("databaseName"));
        Worker targetW = Factory.getWork(targetDs);
        List<TableModel> targetTables = targetW.obtainTable(targetDb);
        targetDb.setTableModels(targetTables);
        for (TableModel sourceTable : targetTables) {
            sourceTable.setFields(targetW.obtainTableInfo(sourceDb,sourceTable));
        }

        ReportModel report = reportTool.report(sourceDb, targetDb, Report.all);
        System.out.println(report.getMessage());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh_mm_ss");
        File path = new File("report");
        boolean mkdirs = true;
        if(!path.exists()){
            mkdirs = path.mkdirs();
        }
        if(mkdirs) {
            File reportFile = new File(path, String.format("%s-%s_%s.txt", sourceData.get("databaseName"), targetData.get("databaseName"), sdf.format(new Date())));
            boolean createFile = true;
            if (!reportFile.exists()) {
                createFile = reportFile.createNewFile();
            }
            if(createFile) {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(reportFile));
                writer.write(report.getMessage());
                writer.flush();
                writer.close();
            }
        }
    }


}
