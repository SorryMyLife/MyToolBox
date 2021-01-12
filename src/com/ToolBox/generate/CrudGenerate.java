package com.ToolBox.generate;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.ToolBox.util.FileTool;
import com.ToolBox.util.StringTool;

/**
 * <p>
 * 创建时间：2020年12月18日 下午12:47:14
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明： crud代码自动生成器
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：CrudGenerate.java
 */
public class CrudGenerate {
	private String text,savefilepath;

	public String getSavefilepath() {
		return savefilepath;
	}

	public void setSavefilepath(String savefilepath) {
		this.savefilepath = savefilepath;
	}

	public String getPackagename() {
		return packagename;
	}

	public String getOutpath() {
		return outpath;
	}

	public String getTablename() {
		return tablename;
	}

	public List<jdbcFiledInfo> getJdbcFiledInfoList() {
		return jdbcFiledInfoList;
	}

	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}

	public void setOutpath(String outpath) {
		this.outpath = outpath;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setJdbcFiledInfoList(List<jdbcFiledInfo> jdbcFiledInfoList) {
		this.jdbcFiledInfoList = jdbcFiledInfoList;
	}

	public CrudGenerate(String packagename, String outpath, String tablename, List<jdbcFiledInfo> jdbcFiledInfoList) {
		super();
		this.packagename = packagename;
		this.outpath = outpath;
		this.tablename = tablename;
		this.jdbcFiledInfoList = jdbcFiledInfoList;
	}

	public String packagename, outpath, tablename;

	@Override
	public String toString() {
		return "CrudGenerate [text=" + text + ", packagename=" + packagename + ", outpath=" + outpath + ", tablename="
				+ tablename + ", jdbcFiledInfoList=" + Arrays.toString(jdbcFiledInfoList.toArray()) + "]";
	}

	public List<jdbcFiledInfo> jdbcFiledInfoList;

	public String toText() {

		return text;
	}

	public String getPath() {
		return getOutpath() + "/" + getPackagename().replace(".", "/");
	}

	public void jdbcServiceImplGenerate(boolean iswrite) {

		StringBuilder sb = new StringBuilder();
		StringTool st = new StringTool();
		sb.append("package " + getPackagename() + ".service.impl;\n");
		setTablename(st.toHump(getTablename() + "_service_impl", false));
		sb.append("import org.springframework.stereotype.Service;\n");
		sb.append("import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n");
		sb.append("import " + getPackagename() + ".dao."+getTablename().replaceAll("ServiceImpl", "Dao")+";\n");
		sb.append("import " + getPackagename() + ".entity."+getTablename().replaceAll("ServiceImpl", "Entity")+";\n");
		sb.append("import " + getPackagename() + ".service."+getTablename().replaceAll("ServiceImpl", "Service")+";\n");
		
		sb.append("@Service(\"" + getTablename().replaceAll("ServiceImpl", "Service") + "\")\n");
		sb.append("public class " + getTablename() + " extends ServiceImpl<"
				+ getTablename().replaceAll("ServiceImpl", "Dao") + " , "
				+ getTablename().replaceAll("ServiceImpl", "Entity") + "> implements "
				+ getTablename().replaceAll("ServiceImpl", "Service"));

		sb.append("\n{\n");

		sb.append("\n}\n\n");
		setSavefilepath(getPath() + "/service/impl/" + getTablename() + ".java");
		text = sb.toString();
		if (iswrite) {
			FileTool ft = new FileTool();
			File file = new File(getPath());
			if (!file.exists()) {
				file.mkdirs();
			}
			ft.setEncode("gbk");
			
			ft.writeFile(text, getSavefilepath());
		}

	}

	public void jdbcServiceGenerate(boolean iswrite) {

		StringBuilder sb = new StringBuilder();
		StringTool st = new StringTool();
		sb.append("package " + getPackagename() + ".service;\n");
		setTablename(st.toHump(getTablename() + "_service", false));
		sb.append("import com.baomidou.mybatisplus.extension.service.IService;\n");

		sb.append("public interface " + getTablename() + " extends IService<"
				+ getTablename().replaceAll("Service", "Entity") + ">");
		sb.append("\n{\n");

		sb.append("\n}\n\n");
		setSavefilepath(getPath() + "/service/" + getTablename() + ".java");
		text = sb.toString();
		if (iswrite) {
			FileTool ft = new FileTool();
			File file = new File(getPath());
			if (!file.exists()) {
				file.mkdirs();
			}
			
			ft.setEncode("gbk");
			ft.writeFile(text,getSavefilepath());
		}

	}
	

	public void jdbcDAOGenerate(boolean iswrite) {

		StringBuilder sb = new StringBuilder();
		StringTool st = new StringTool();
		sb.append("package " + getPackagename() + ".dao;\n");
		setTablename(st.toHump(getTablename() + "_dao", false));
		sb.append("import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n");
		sb.append("@Mapper\n");
		sb.append("public interface " + getTablename() + " extends BaseMapper<"
				+ getTablename().replaceAll("Dao", "Entity") + ">");
		sb.append("\n{\n");

		sb.append("\n}\n\n");
		setSavefilepath(getPath() + "/dao/" + getTablename() + ".java");
		text = sb.toString();
		if (iswrite) {
			FileTool ft = new FileTool();
			File file = new File(getPath());
			if (!file.exists()) {
				file.mkdirs();
			}
			ft.setEncode("gbk");
			
			ft.writeFile(text, getSavefilepath());
		}

	}

	public void jdbcEntityGenerate(boolean iswrite) {
		StringBuilder sb = new StringBuilder();
		StringBuilder rowsStr = new StringBuilder();
		StringBuilder columnStr = new StringBuilder();
		StringBuilder columnThisStr = new StringBuilder();
		StringBuilder setgetStr = new StringBuilder();

		StringTool st = new StringTool();
		sb.append("package " + getPackagename() + ".entity;\n");
		setTablename(st.toHump(getTablename() + "_entity", false));
		sb.append("public class " + getTablename());
		sb.append("\n{\n");
		sb.append("public " + getTablename() + "(){\nsuper();\n}\n");
		sb.append("public " + getTablename() + "(");
		getJdbcFiledInfoList().stream().forEach((f) -> {

			try {
				Class<?> c = Class.forName(f.getFiledtype());
				String typename = c.getSimpleName();
				String upfiledname = st.toHump(f.getFiledname(), false);
				String lowfiledname = st.toHump(f.getFiledname(), true);
				rowsStr.append(typename + " " + lowfiledname + ", ");
				columnStr.append("/**\n" + lowfiledname + " : " + f.getFiledcomment() + "\n" + "*/\n");
				columnStr.append("private " + typename + " " + lowfiledname + ";\n\n");
				columnThisStr.append("this." + lowfiledname + " = " + lowfiledname + ";\n");
				setgetStr.append("/**\n" + "设置" + f.getFiledcomment() + "\n" + "*/\n");
				setgetStr.append("public void" + " set" + upfiledname + "(" + typename + " " + lowfiledname + "){\n");
				setgetStr.append("this." + lowfiledname + " = " + lowfiledname + ";\n}\n");
				setgetStr.append(
						"public " + typename + " get" + upfiledname + "(){\n return " + lowfiledname + ";\n}\n\n");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		});
		sb.append(rowsStr.toString().substring(rowsStr.toString().indexOf(""), rowsStr.toString().lastIndexOf(","))
				+ "){\n");
		sb.append(columnThisStr + "\n}\n");
		sb.append(columnStr);
		sb.append(setgetStr);
		sb.append("\n}\n\n");
		text = sb.toString();
		setSavefilepath(getPath() + "/entity/" + getTablename() + ".java");
		if (iswrite) {
			FileTool ft = new FileTool();
			File file = new File(getPath());
			if (!file.exists()) {
				file.mkdirs();
			}
			
			ft.setEncode("gbk");
			ft.writeFile(text, getSavefilepath());
		}
	}

	public void Generate(boolean iswrite) {

	}

}
