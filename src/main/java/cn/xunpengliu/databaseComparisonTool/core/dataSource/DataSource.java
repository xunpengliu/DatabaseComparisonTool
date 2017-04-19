package cn.xunpengliu.databaseComparisonTool.core.dataSource;

import java.sql.Connection;

import cn.xunpengliu.databaseComparisonTool.exception.LinkDataBaseException;

public interface DataSource {
	/**
	 * 获取数据库连接
	 * @return 数据库连接
	 * @throws LinkDataBaseException 连接失败将抛出此异常
	 */
	Connection getConnection();
}
