package cn.xunpengliu.databaseComparisonTool.core.report;

import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.ReportModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;

/**
 * Created by LXP on 2017/4/20.
 * 比较器接口
 */

public interface Report {
    public static int all = 0;
    public static int info = 1;
    public static int warm = 2;
    public static int error = 3;
    ReportModel report(DatabaseModel sourceDb, DatabaseModel targetDb, int all);

    ReportModel report(TableModel source, TableModel target, int level);
}
