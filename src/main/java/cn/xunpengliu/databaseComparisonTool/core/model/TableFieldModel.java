package cn.xunpengliu.databaseComparisonTool.core.model;

import java.math.BigInteger;


public class TableFieldModel {
    public enum KEY_TYPE{PRI,MUL,UN,NOT}

    private String fieldName;
    private String dataType;
    private BigInteger length;
    private Object columnDefault;
    private boolean nullable;
    private KEY_TYPE keyType = KEY_TYPE.NOT;

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

    public KEY_TYPE getKeyType() {
        return keyType;
    }

    public void setKeyType(KEY_TYPE keyType) {
        this.keyType = keyType;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null || !(obj instanceof TableFieldModel)){
            return false;
        }
        return fieldName.equals(((TableFieldModel) obj).getFieldName());
    }
}
