package cn.xunpengliu.databaseComparisonTool.core.model;

import java.util.List;

/**
 * Created by LXP on 2017/4/17.
 *
 */

public class DatabaseModel {
    private String dbName;
    private List<TableModel> tableModels;

    public List<TableModel> getTableModels() {
        return tableModels;
    }

    public void setTableModels(List<TableModel> tableModels) {
        this.tableModels = tableModels;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
