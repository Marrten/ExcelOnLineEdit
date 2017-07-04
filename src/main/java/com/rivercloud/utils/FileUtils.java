package com.rivercloud.utils;

import java.io.File;
import java.util.Map;

/**
 * 设置文件的目录结构
 * @author Administrator
 */
public class FileUtils {
	//分层目录 
	public static String generateFilename(String uploadPath, String filename) {
		int hashCode = filename.hashCode();
		int dir1 = hashCode & 0xF;
		int dir2 = (hashCode >> 4) & 0xF;
		uploadPath = uploadPath + "/" + dir1 + "/" + dir2;
		File path =  new File(uploadPath);
		if(!path.exists()){
			path.mkdirs();
		}
		return filename = uploadPath + "/" + filename;
	}
	public static void generateMap(File uploadFile, Map<String, String> map) {
		File[] files = uploadFile.listFiles();
		for(File file : files){
			if(file.isDirectory()){
				generateMap(file, map);
			}else{
				String filename = file.getName().substring(file.getName().indexOf(";") + 1);
				map.put(file.getName(), filename);
			}
		}
	}
}
