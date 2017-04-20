package cn.xunpengliu.databaseComparisonTool.core;

import cn.xunpengliu.databaseComparisonTool.core.analysis.DataAnalysis;
import cn.xunpengliu.databaseComparisonTool.core.command.DbCommand;
import cn.xunpengliu.databaseComparisonTool.core.dataSource.DataSource;
import cn.xunpengliu.databaseComparisonTool.core.model.DatabaseModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableFieldModel;
import cn.xunpengliu.databaseComparisonTool.core.model.TableModel;
import cn.xunpengliu.databaseComparisonTool.exception.LinkDataBaseException;
import cn.xunpengliu.databaseComparisonTool.exception.SelectException;
import cn.xunpengliu.databaseComparisonTool.exception.VisitDatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LXP on 2017/4/19.
 * 工作者
 */

public class Worker {
    private DataSource dataSource;
    private DbCommand command;
    private DataAnalysis analysis;
    private Connection conn;

    private Worker(DataSource dataSource, DbCommand command, DataAnalysis analysis) {
        this.dataSource = dataSource;
        this.command = command;
        this.analysis = analysis;
    }

    public static Worker getWork(DataSource dataSource, DbCommand command, DataAnalysis analysis) throws LinkDataBaseException {
        Worker worker = new Worker(dataSource, command, analysis);
        worker.conn = dataSource.getConnection();
        return worker;
    }

    public List<DatabaseModel> obtainDatabase() {
        List<Map<Integer, Object>> maps = obtainData(command.dbCommand());
        return analysis.analysisDb(maps);
    }

    public List<TableModel> obtainTable(DatabaseModel db){
        List<Map<Integer, Object>> maps = obtainData(command.tableCommand(db));
        return analysis.analysisTable(maps);
    }

    public List<TableFieldModel> obtainTableInfo(DatabaseModel db, TableModel tb){
        List<Map<Integer,Object>> maps = obtainData(command.tableInfoCommand(db,tb));
        return analysis.analysisTableInfo(maps);
    }

    public List<Map<Integer, Object>> obtainData(String sql){
        try {
            if (conn.isClosed()) {
                conn = dataSource.getConnection();
            }
        } catch (LinkDataBaseException|SQLException e) {
            throw new VisitDatabaseException(e.getMessage());
        }
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return convertResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new SelectException(e.getMessage());
        }
    }

    private List<Map<Integer,Object>> convertResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int column = rsmd.getColumnCount();
        List<Map<Integer,Object>> data = new ArrayList<>(32);
        while (resultSet.next()){
            Map<Integer,Object> m = new HashMap<>(column,1.0f);
            for(int i = 0;i<column;++i){
                m.put(i+1,resultSet.getObject(i+1));
            }
            data.add(m);
        }
        return data;
    }
}
