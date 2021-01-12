package com.ToolBox.video;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import com.ToolBox.net.HttpUtils;
import com.ToolBox.util.FileTool;
import com.ToolBox.util.StringTool;

/**
 * <p>
 * 创建时间：2019年7月19日 下午5:12:35
 * <p>
 * 项目名称：ToolBox
 * 
 * <p>
 * 类说明：
 * <p>
 * 这个工具是用来解析并下载m3u8文件的，
 * <p>
 * 采用多线程并行下载，如果遇到阻塞情况还请停止程序并重新运行一遍即可，直至程序正常退出！
 * <p>
 * 个人建议用URL下载m3u8文件而不是直接从本地解析下载
 * 
 * @version 1.0
 * @since JDK 1.8 文件名称：m3u8.java
 */
public class m3u8 {

	private String m3u8File, savePath, host, Links;
	private URL urlLink;
	private int reCount = 0, timeout;
	public int getTimeout() {
		return timeout == 0 ? 400:timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	private boolean fileCheckFlag = true;
	private File outPath;
	private final static StringTool st = new StringTool();
	private final static FileTool ft = new FileTool();

	public m3u8() {
	}

	public m3u8(String m3u8File) {
		this.m3u8File = m3u8File;
	}

	public m3u8(URL urlLink) {
		this.urlLink = urlLink;
	}

	public m3u8(URL urlLink, String savePath) {
		this.urlLink = urlLink;
		this.savePath = savePath;
		outPath = new File(getSavePath());
	}

	public m3u8(String m3u8File, String savePath) {
		this.m3u8File = m3u8File;
		this.savePath = savePath;
		outPath = new File(getSavePath());
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
		outPath = new File(getSavePath());
	}

	public String getM3u8File() {
		return m3u8File;
	}

	public void setM3u8File(String m3u8File) {
		this.m3u8File = m3u8File;
	}

	private String checkValue() {
		return getM3u8File() != null ? new FileTool().readFile(getM3u8File())
				: (getUrlLink() != null ? new HttpUtils().getPage(getUrlLink().toString())
						: "please set url or file path");
	}

	public String getAllLink() {
		String tmp = checkValue();
		Links = tmp.indexOf("http") != -1 ? st.getByAllString(tmp, "http(.+?\\.ts)(.+?\\S\\n)", "\\n")
				: st.getByAllString(tmp, "(.+?\\.ts)", "\\n");
		return Links;
	}

	public int getSize() {
		int size = 1;
		for (String s : Links == null ? getAllLink().split("\n") : Links.split("\n")) {
			String url = getHost() + s;
			size += new HttpUtils().getFileSize(url);
			sleep(getTimeout());
		}

		System.err.println("size finish : " + size);

		return size;
	}

	public void sleep(int time) {
		try {
			new Thread().sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getFilesNum() {
		if (Links == null) {
			getAllLink();
		}

		return st.toList().size();
	}

	public void merge(File filePath, File outName) {
		try {
			if (filePath.isDirectory() && outName.exists() == false) {
				ft.BinaryFileMerge(filePath.listFiles(), outName);
			} else {
				System.err.println("文件夹不存在或者目标文件已存在");
				System.exit(-1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkFiles(File localFiles) {
		if (localFiles.listFiles().length == (Links == null ? getAllLink().split("\n").length
				: Links.split("\n").length)) {
			for (File f : localFiles.listFiles()) {
				if (f.length() < 1) {
					f.delete();
					fileCheckFlag = false;
				}
			}
		}
		return fileCheckFlag;
	}

	public void cleanTmpFiles() {
		ft.delNoMatchFiles(outPath, ".");
	}
	
	public void cleanFiles() {
		ft.delFolder(outPath);
	}

	private class ThreadDownload extends Thread {
		private String outName , host , savepath;
		private int timeout;

		public ThreadDownload(String outName, String host, String savepath, String[] downloadArr,int timeout) {
			super();
			this.outName = outName;
			this.host = host;
			this.savepath = savepath;
			this.downloadArr = downloadArr;
			this.timeout = timeout;
		}

		private String[] downloadArr;

		@Override
		public void run() {

			int len = downloadArr.length;
			System.err.println("file num : " + len);
			String size = len + "";
			String name = "";
			StringBuilder sb = null;
			HttpUtils hu = new HttpUtils();
			for (int j = 0; j < len; j++) {
				name = j + "";
				if (name.length() < size.length()) {
					sb = new StringBuilder(name);
					sb.insert(sb.indexOf(""), "0");
					if (sb.length() < size.length()) {
						for(int a =sb.length();a<size.length();a++) {
							sb = new StringBuilder(sb.toString());
							sb.insert(sb.indexOf(""), "0");
						}
					}
				} else {
					sb = new StringBuilder(name);
				}
//				System.out.println(sb.toString());
				hu.threadDown(downloadArr[j].indexOf("http") != -1 ?downloadArr[j]: host+ "/" + downloadArr[j], savePath, sb.toString());
				try {
					this.sleep(timeout);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public void Download(String outName) {
		if (getSavePath() != null) {
			if (!outPath.exists()) {
				outPath.mkdirs();
				Download(outName);
			} else {
				try {
					String downloadArr[] = Links == null ? getAllLink().split("\n") : Links.split("\n");
					ThreadDownload threadDownload = new ThreadDownload(outName, getHost(), getSavePath(), downloadArr,getTimeout());
					threadDownload.start();
					threadDownload.join();
					System.err.println("files download ok !");
					if (checkFiles(outPath)) {
						merge(outPath, new File(getSavePath()+"/"+outName));
						System.err.println("merge ok !");
						cleanTmpFiles();
						System.err.println("clean tmp files ok !");
					} else {
						if (reCount < 3) {
							reCount++;
							Download(outName);
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// String downloadArr[] = Links == null ? getAllLink().split("\n") :
				// Links.split("\n");
				// int len = downloadArr.length;
				// System.err.println("file size : " + len);
				// String size = len + "";
				// String name = "";
				// StringBuilder sb = null;
				// HttpUtils hu = new HttpUtils();
				// for (int j = 0; j < len; j++) {
				// name = j + "";
				// if (name.length() < size.length()) {
				// sb = new StringBuilder(name);
				// sb.insert(sb.indexOf(""), "0");
				// if (sb.length() < size.length()) {
				// sb = new StringBuilder(sb.toString());
				// sb.insert(sb.indexOf(""), "0");
				// }
				// } else {
				// sb = new StringBuilder(name);
				// }
				// hu.threadDown(getHost() + "/" + downloadArr[j], getSavePath(),
				// sb.toString());
				// sleep(400);
				// }
				// System.err.println("files download ok !");
				// if (checkFiles(outPath)) {
				// merge(outPath, new File(outName));
				// System.err.println("merge ok !");
				// } else {
				// if (reCount < 3) {
				// reCount++;
				// Download(outName);
				// }
				// }
			}
		} else {
			System.err.println("please set save path !");
			System.exit(-1);
		}
	}

	public void Download() {
		Download("video.mp4");
	}

	public URL getUrlLink() {
		return urlLink;
	}

	public void setUrlLink(URL urlLink) {
		this.urlLink = urlLink;
	}

	public String getHost() {
		return urlLink != null ? urlLink.getProtocol() + "://" + urlLink.getHost() : host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
