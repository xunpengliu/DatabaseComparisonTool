package cn.xunpengliu.databaseComparisonTool.core.command.impl;

import cn.xunpengliu.databaseComparisonTool.core.command.DbCommand;
import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;

/**
 * Created by LXP on 2017/4/19.
 * MySql command
 */

public class MySqlDbCommandImpl implements DbCommand {

    @Override
    public String dbCommand() {
        return "show databases";
    }

    @Override
    public String tableCommand(DatabaseModel db) {
        return "show tables from "+db.getDbName();
    }

    @Override
    public String tableInfoCommand(DatabaseModel db, TableModel tb) {
        return "select column_name,data_type,CHARACTER_MAXIMUM_LENGTH,COLUMN_DEFAULT,IS_NULLABLE " +
                " from information_schema.columns " +
                " where table_schema ='"+db.getDbName()+"' " +
                " and table_name = '"+tb.getTableName()+"' ;";
    }
}
