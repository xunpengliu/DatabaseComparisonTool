package cn.xunpengliu.databaseComparisonTool.core.dataSource.model;

public class MysqlDataSrouceModel extends DataSourceModel {
	private String host;
	private int port;
	private String username;//连接账户
	private String password;//连接密码
	private String charset = "utf-8";//字符集
	
	public MysqlDataSrouceModel(String host,String username,String password) {
		this(host,3306,username,password);
	}
	
	public MysqlDataSrouceModel(String host,int port,String username,String password) {
		this.host = host;
		this.username = username;
		this.password = password;
		this.port = port;
	}

    public MysqlDataSrouceModel() {

    }

    @Override
	public String getConnectionUrl() {
		StringBuilder sb = new StringBuilder(128);
		sb.append("jdbc:mysql://").append(host).append(':').append(port);
		sb.append('?').append("user=").append(username).append("&").append("password=").append(password);
		sb.append("&autoReconnect=true");
		sb.append("&characterEncoding=").append(charset);
		
		return sb.toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
