package cn.xunpengliu.databaseComparisonTool.exception;

public class LinkDataBaseException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6010061556304891094L;

	public LinkDataBaseException(){
		this("");
	}
	
	public LinkDataBaseException(String message){
		super("link database exception. "+message);
	}
	
}
