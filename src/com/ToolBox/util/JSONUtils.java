package com.ToolBox.util;

import java.io.File;

/**
 * <p>
 * ����ʱ�䣺2020��2��2�� ����1:28:33
 * <p>
 * ��Ŀ���ƣ�ToolBox
 * 
 * <p>
 * ��˵���� ����json������
 *
 * @version 1.0
 * @since JDK 1.8 �ļ����ƣ�JSONUtils.java
 */
public class JSONUtils {

	public File FilePath;
	public String JsonData, TmpJsonData, JsonFileSavePath, JsonFileName;
	public int JsonCount = 0;

	public String getJsonFileName() {
		return JsonFileName == null ? "jsonData.json" : JsonFileName;
	}

	public void setJsonFileName(String jsonFileName) {
		JsonFileName = jsonFileName;
	}

	public String getJsonFileSavePath() {
		return JsonFileSavePath;
	}

	public void setJsonFileSavePath(String jsonFileSavePath) {
		JsonFileSavePath = jsonFileSavePath;
	}

	public JSONUtils(String jsonData) {
		JsonData = jsonData;
	}

	public JSONUtils() {

	}

	public JSONUtils(File filePath) {
		FilePath = filePath;
	}

	public File getFilePath() {
		return FilePath;
	}

	/**
	 * <p>
	 * ��ȡjson����
	 */
	public String getJsonData() {
		JsonData = getFilePath() != null ? new FileTool().readFile(getFilePath()) : JsonData;
		return JsonData == null || JsonData.equals("") || JsonData.length() < 1 ? "{}" : JsonData;
	}

	public void setFilePath(File filePath) {
		FilePath = filePath;
	}

	public void setJsonData(String jsonData) {
		JsonData = jsonData;
	}

	/**
	 * <p>
	 * ��ȡkey��ֵ
	 */
	public Object getByName(String key) {
		return new StringTool().getByJson(getJsonData(), key);
	}

	/**
	 * <p>
	 * ɾ��һ��key
	 */
	public boolean delete(String key) {
		if (!getByName(key).equals("")) {
			TmpJsonData = getJsonData().replaceAll(",\"" + key + "\":\"" + getByName(key) + "\"", "");
		}
		if (TmpJsonData.length() != getJsonData().length()) {
			setJsonData(TmpJsonData);
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * �޸�key��ֵ
	 */
	public boolean change(String key, Object value) {
		if (!getByName(key).equals("")) {
			TmpJsonData = getJsonData().replaceAll("\"" + key + "\":\"" + getByName(key) + "\"",
					"\"" + key + "\":\"" + value + "\"");
		}
		if (TmpJsonData.length() != getJsonData().length()) {
			setJsonData(TmpJsonData);
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * ���һ���µ�key
	 */
	public boolean add(String key, Object value) {
		if (JsonCount == 0) {
			TmpJsonData = getJsonData().replaceAll("\\{\\}", "\\{\"" + key + "\":\"" + value + "\"\\}");
			JsonCount++;
		} else {
			TmpJsonData = getJsonData().replaceAll("\\}", ",\"" + key + "\":\"" + value + "\"\\}");
		}
		if (TmpJsonData.length() != getJsonData().length()) {
			setJsonData(TmpJsonData);
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * ����json���ݵ�����
	 */
	public boolean save() {
		File JsonFile = new File(getJsonFileSavePath());
		if (!JsonFile.exists()) {
			JsonFile.mkdirs();
		}
		JsonFile = new File(getJsonFileSavePath() + "/" + getJsonFileName());
		if (JsonFile.exists()) {
			JsonFile.delete();
		}
		new FileTool().writeFile(getJsonData(), JsonFile);
		return JsonFile.exists();
	}

}
