package com.ToolBox.generate;
/**
* <p>创建时间：2020年12月18日 下午1:35:07
* <p>项目名称：ToolBox
* 
* <p>类说明：数据库字段信息
* 字段名称、字段类型、字段注释
*
* @version 1.0
* @since JDK 1.8
* 文件名称：jdbcFiledInfo.java
* */
public class jdbcFiledInfo {
	private String filedname , filedtype , filedcomment;

	@Override
	public String toString() {
		return "jdbcFiledInfo [filedname=" + filedname + ", filedtype=" + filedtype + ", filedcomment=" + filedcomment
				+ "]";
	}

	public String getFiledname() {
		return filedname;
	}

	public String getFiledtype() {
		return filedtype;
	}

	public String getFiledcomment() {
		return filedcomment;
	}

	public void setFiledname(String filedname) {
		this.filedname = filedname;
	}

	public void setFiledtype(String filedtype) {
		this.filedtype = filedtype;
	}

	public void setFiledcomment(String filedcomment) {
		this.filedcomment = filedcomment;
	}

	public jdbcFiledInfo(String filedname, String filedtype, String filedcomment) {
		super();
		this.filedname = filedname;
		this.filedtype = filedtype;
		this.filedcomment = filedcomment;
	}
	public jdbcFiledInfo() {
		
	}
	
	
	
}
