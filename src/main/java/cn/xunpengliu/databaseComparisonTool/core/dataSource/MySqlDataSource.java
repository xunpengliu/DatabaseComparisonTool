package cn.xunpengliu.databaseComparisonTool.core.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.xunpengliu.databaseComparisonTool.core.dataSource.model.DataSourceModel;
import cn.xunpengliu.databaseComparisonTool.exception.LinkDataBaseException;

/**
 * 数据源
 * @author xunpengliu
 * @version 创建时间：2017年4月14日 下午9:17:35
 */
public class MySqlDataSource implements DataSource {
	private DataSourceModel datasourceModel;
	public MySqlDataSource(DataSourceModel datasourceModel){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.datasourceModel = datasourceModel;
	}
	
	public Connection getConnection(){
		try{
			return DriverManager.getConnection(datasourceModel.getConnectionUrl());
		}catch(SQLException e){
			throw new LinkDataBaseException("link url==>"+datasourceModel.getConnectionUrl()+" fail \nmessage:"+e.getMessage());
		}
	}
}
