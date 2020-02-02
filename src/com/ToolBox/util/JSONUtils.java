package com.ToolBox.util;

import java.io.File;

/**
 * <p>
 * 创建时间：2020年2月2日 下午1:28:33
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： 简易json工具类
 *
 * @version 1.0
 * @since JDK 1.8 文件名称：JSONUtils.java
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
	 * 获取json数据
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
	 * 获取key的值
	 */
	public Object getByName(String key) {
		return new StringTool().getByJson(getJsonData(), key);
	}

	/**
	 * <p>
	 * 删除一个key
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
	 * 修改key的值
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
	 * 添加一个新的key
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
	 * 保存json数据到本地
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
