package cn.xunpengliu.databaseComparisonTool.core.analysis;

import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableInfoModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;

import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/17.
 * 数据库解析器
 */

public interface DataAnalysis {
    /**
     * analysis Database
     * @param datas datas is resultset data
     * @return DatabaseModels
     */
    List<DatabaseModel> analysisDb(List<Map<Integer,Object>> datas);

    /**
     * analysis table datas
     * @param datas datas is resultset data
     * @return TableModels
     */
    List<TableModel> analysisTable(List<Map<Integer,Object>> datas);

    List<TableInfoModel> analysisTableInfo(List<Map<Integer,Object>> datas);
}
