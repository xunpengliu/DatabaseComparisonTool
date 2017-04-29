package cn.xunpengliu.databaseComparisonTool.core.model;

import java.util.List;


public class TableModel {
    private String tableName;

    private List<TableFieldModel> fields;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableFieldModel> getFields() {
        return fields;
    }

    public void setFields(List<TableFieldModel> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || !(obj instanceof TableModel)){
            return false;
        }
        return tableName.equals(((TableModel) obj).getTableName());
    }
}
