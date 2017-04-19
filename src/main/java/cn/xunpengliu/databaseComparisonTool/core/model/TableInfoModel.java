package cn.xunpengliu.databaseComparisonTool.core.model;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by LXP on 2017/4/19.
 *
 */

public class TableInfoModel{
    private String fieldName;
    private String dataType;
    private BigInteger length;
    private Object columnDefault;
    private boolean nullable;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public BigInteger getLength() {
        return length;
    }

    public void setLength(BigInteger length) {
        this.length = length;
    }

    public Object getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(Object columnDefault) {
        this.columnDefault = columnDefault;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
