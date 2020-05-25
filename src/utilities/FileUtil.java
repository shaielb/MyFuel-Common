package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

	public static void createDirectory(File directory) throws IOException {
		if (!directory.exists()) {
			createFile(directory.getParentFile());
			Files.createDirectory(Paths.get(directory.getAbsolutePath()));
		}
	}
	
	public static void createFile(File file) throws IOException {
		File parentFile = file.getParentFile();
		createDirectory(parentFile);
		file.createNewFile();
	}
	
	public static File deleteSub(String dirPath) {
		File directory = new File(dirPath);
		deleteSub(directory);
		return directory;
	}
	
	public static File deleteSubDirectories(String dirPath) {
		File directory = new File(dirPath);
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteSub(file);
				}
			}
		}
		return directory;
	}
	
	public static void deleteSub(File directory) {
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null) {
				for(File file: files) 
					deleteSub(file);
			}
			directory.delete();
		}
		else {
			directory.delete();
		}
	}
	
	public static void writeToFile(File file, List<String> list) throws IOException {
		createFile(file);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (String line : list) {
			bw.write(line);
		}
		bw.close();
	}
	
	public static void writeToFile(File file, String str) throws IOException {
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(str + "\n");
		bw.close();
	}
	
	public static void mkDirs(File directory) {
		if (!directory.exists()) {
			mkDirs(directory.getParentFile());
			directory.mkdir();
		}
	}
}
