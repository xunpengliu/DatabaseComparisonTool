package cn.xunpengliu.databaseComparisonTool.core.analysis.impl;

import cn.xunpengliu.databaseComparisonTool.core.analysis.DataAnalysis;
import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableFieldModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/17.
 */

public class MySqlAnalysisImpl implements DataAnalysis {

    @Override
    public List<DatabaseModel> analysisDb(List<Map<Integer, Object>> datas) {
        List<DatabaseModel> dbModels = new ArrayList<>(20);
        for (Map<Integer, Object> data : datas) {
            DatabaseModel dbm = new DatabaseModel();
            String dbName = (String) data.get(1);
            dbm.setDbName(dbName);
            dbModels.add(dbm);
        }
        return dbModels;
    }

    @Override
    public List<TableModel> analysisTable(List<Map<Integer, Object>> datas) {
        List<TableModel> tableModels = new ArrayList<>(20);
        for (Map<Integer, Object> data : datas) {
            TableModel tm = new TableModel();
            String tableName = (String) data.get(1);
            tm.setTableName(tableName);
            tableModels.add(tm);
        }
        return tableModels;
    }

    @Override
    public List<TableFieldModel> analysisTableInfo(List<Map<Integer, Object>> datas) {
        List<TableFieldModel> tableInfoModels = new ArrayList<>(10);
        for (Map<Integer, Object> data : datas) {
            TableFieldModel tim = new TableFieldModel();
            tim.setFieldName((String) data.get(1));
            tim.setDataType((String) data.get(2));

            String s = String.valueOf(data.get(3));
            if (s != null && s.matches("\\d*")) {
                tim.setLength(new BigInteger(s));
            }

            tim.setColumnDefault(data.get(4));
            tim.setNullable("yes".equalsIgnoreCase((String) data.get(5)));
            String keyType = (String) data.get(6);
            if("pri".equalsIgnoreCase(keyType)){
                tim.setKeyType(TableFieldModel.KEY_TYPE.PRI);
            }else if("mul".equalsIgnoreCase(keyType)){
                tim.setKeyType(TableFieldModel.KEY_TYPE.MUL);
            }else if("un".equalsIgnoreCase(keyType)){
                tim.setKeyType(TableFieldModel.KEY_TYPE.UN);
            }

            tableInfoModels.add(tim);
        }
        return tableInfoModels;
    }
}
