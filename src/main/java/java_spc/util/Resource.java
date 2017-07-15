package java_spc.util;

import java.io.File;

public class Resource {
	public static String path(String filePath){
		ClassLoader loader=Resource.class.getClassLoader();
		return loader.getResource(filePath)==null?newPath(filePath):loader.getResource(filePath).getPath();
	}
	
	private static String newPath(String filePath){
		ClassLoader loader=Resource.class.getClassLoader();
		return loader.getResource("").getPath()+filePath;
	}
	
	public static String pathToSource(String filePath){
		return new File("").getAbsolutePath()+"\\src\\main\\resources\\"+filePath;
	}
}
