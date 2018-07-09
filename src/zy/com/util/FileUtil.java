package zy.com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import zy.com.entity.FileInfo;

public final class FileUtil {

	private static final Log logger = LogFactory.getLog(FileUtil.class);

	/**
	 * 判断文件路径是否存在
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean CreatDir(File dir) {

		if (!dir.exists()) {
			dir.mkdir();
			return false;
		} else {
			return true;
		}
	}

	public static String deleteFile(String dir, String fileName) {

		File file = new File(dir + File.separatorChar + fileName);
		if (file.exists()) {
			file.delete();
			return "YES";
		} else {
			return "NO_SUCH_FILE";
		}
	}

	public static String deleteFile(String path) {

		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return "YES";
		} else {
			return "NO_SUCH_FILE";
		}
	}

	/**
	 * 单文件压缩
	 * 
	 * @param filePath
	 * @param toFilePath
	 */
	public static ZipFile zipFile(String filePath, String toFilePath) {

		ZipFile zipFile = null;
		File singleFile = new File(filePath);
		try {
			zipFile = new ZipFile(toFilePath);
		} catch (ZipException e1) {
			logger.error("创建压缩目的文件失败！");
		}
		ZipParameters parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		try {
			zipFile.addFile(singleFile, parameters);
		} catch (ZipException e) {
			logger.error("压缩文件失败！");
			return null;
		}
		deleteFile(filePath);
		return zipFile;
	}

	public static String reNameToZip(String originalFilename) {

		return originalFilename.substring(0, originalFilename.lastIndexOf('.'));
	}

	public static List<FileInfo> getAllFiles(String dir) {

		List<FileInfo> list = new ArrayList<FileInfo>();
		File Dir = new File(dir);
		File[] files = Dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					FileInfo info = new FileInfo();
					info.setFileName(files[i].getName());
					info.setFileSize(String.valueOf(files[i].length()));
					info.setFileDate(files[i].getAbsolutePath());
					list.add(info);
				}
			}
			return list;
		} else {
			return null;
		}

	}
}
