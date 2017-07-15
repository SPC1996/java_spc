package java_spc.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

import static java_spc.util.SPrint.*;

public class DirList {
	public static void main(String[] args) {
		File filePath=new File(".");
		String[] list;
		if(args.length==0){
			list=filePath.list();
		}else {
			list=filePath.list(new DirFilter(args[0]));
		}
		Arrays.sort(list,String.CASE_INSENSITIVE_ORDER);
		for(String item:list){
			print(item);
		}
	}

}

class DirFilter implements FilenameFilter{
	private Pattern pattern;
	
	public DirFilter(String regex){
		pattern=Pattern.compile(regex);
	}

	@Override
	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}
	
}