package com.ToolBox.generate;
/**
* <p>����ʱ�䣺2020��12��18�� ����1:35:07
* <p>��Ŀ���ƣ�ToolBox
* 
* <p>��˵�������ݿ��ֶ���Ϣ
* �ֶ����ơ��ֶ����͡��ֶ�ע��
*
* @version 1.0
* @since JDK 1.8
* �ļ����ƣ�jdbcFiledInfo.java
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
