package cn.xunpengliu.databaseComparisonTool.core.command;

import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;

/**
 * Created by LXP on 2017/4/19.
 * database command
 */

public interface DbCommand {
    String dbCommand();

    String tableCommand(DatabaseModel db);

    String tableInfoCommand(DatabaseModel db, TableModel tb);
//    String
}
