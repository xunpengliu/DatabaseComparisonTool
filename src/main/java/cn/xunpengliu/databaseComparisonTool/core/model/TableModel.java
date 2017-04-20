package cn.xunpengliu.databaseComparisonTool.core.model;

import java.util.List;

/**
 * Created by LXP on 2017/4/19.
 *
 */

public class TableModel {
    private String tableName;

    List<TableFieldModel> field;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableFieldModel> getField() {
        return field;
    }

    public void setField(List<TableFieldModel> field) {
        this.field = field;
    }
}
