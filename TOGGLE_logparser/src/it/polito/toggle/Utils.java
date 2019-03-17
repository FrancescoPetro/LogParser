package it.polito.toggle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Utils {
	
	public static final String java_project_file = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<projectDescription>\r\n" + 
			"	<name>JavaTranslatedProject</name>\r\n" + 
			"	<comment></comment>\r\n" + 
			"	<projects>\r\n" + 
			"	</projects>\r\n" + 
			"	<buildSpec>\r\n" + 
			"		<buildCommand>\r\n" + 
			"			<name>org.eclipse.jdt.core.javabuilder</name>\r\n" + 
			"			<arguments>\r\n" + 
			"			</arguments>\r\n" + 
			"		</buildCommand>\r\n" + 
			"	</buildSpec>\r\n" + 
			"	<natures>\r\n" + 
			"		<nature>org.eclipse.jdt.core.javanature</nature>\r\n" + 
			"	</natures>\r\n" + 
			"</projectDescription>\r\n";
	
	
	public static final String java_project_classpath = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
			"<classpath>\r\n" + 
			"	<classpathentry kind=\"src\" path=\"src\"/>\r\n" + 
			"	<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>\r\n" + 
			"	<classpathentry kind=\"lib\" path=\"libs/eye2.jar\"/>\r\n" + 
			"	<classpathentry kind=\"lib\" path=\"libs/EyeAutomate.jar\"/>\r\n" + 
			"	<classpathentry kind=\"lib\" path=\"libs/sikulixapi.jar\"/>\r\n" + 
			"	<classpathentry kind=\"output\" path=\"bin\"/>\r\n" + 
			"</classpath>\r\n";
	
	
	public static void copyFile(File source, File dest) throws IOException {
		
		Files.copy(source.toPath(), dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
		
	}
	
	
	public static boolean deleteDir(String path) {
		
		File dir = new File(path);

	    if (dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            boolean success = deleteDir(path + "\\" + children[i]);
	            if (!success) {
	                return false;
	            }
	        }
	    }
	    return dir.delete();
	}
	
	
	public static void deleteDirContents(String dirpath) {
		
		File dir = new File(dirpath);
		
		
		for(File file: dir.listFiles()) 
		    if (!file.isDirectory() && !file.getName().equals("logcat.txt")) 
		        file.delete();
		
	}
	
	public static void copyImages(File startingDir) {
		
		File[] list = startingDir.listFiles();
		if(list!=null)
			for (File fil : list)
			{
				if (fil.getName().contains(".png"))
				{

				try {
					Files.copy(Paths.get(fil.getPath()), Paths.get(startingDir.getPath()+"\\JavaTranslatedProject\\"+fil.getName()),StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
					

				}
				
			}
		
	}
	
	private static void copyLib(String libSourcePath, String libDestPath) {
		
		//String libDestPath=starting_folder + "\\JavaTranslatedProject\\libs";
		
		try {
			Files.copy(Paths.get(libSourcePath+"\\eye2.jar"), Paths.get(libDestPath+"\\eye2.jar"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(libSourcePath+"\\EyeAutomate.jar"), Paths.get(libDestPath+"\\EyeAutomate.jar"), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(Paths.get(libSourcePath+"\\sikulixapi.jar"), Paths.get(libDestPath+"\\sikulixapi.jar"), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void createJavaProjectFolder(String starting_folder,String libSourcePath) throws IOException {
		
		//TODO aggiungere download delle librerie all'interno della cartella libs
		//TODO caricare le librerie sul server (ora come ora bisogna copiarle a manina)
		
		File dir = new File(starting_folder + "\\JavaTranslatedProject");
		dir.mkdirs();
		
		dir = new File(starting_folder + "\\JavaTranslatedProject\\src");
		dir.mkdirs();
		
		dir = new File(starting_folder + "\\JavaTranslatedProject\\libs");
		dir.mkdirs();
		
		dir = new File(starting_folder + "\\JavaTranslatedProject\\bin");
		dir.mkdirs();
		
		copyLib(libSourcePath,dir.getPath());
		
		File fout_project = new File(starting_folder + "\\JavaTranslatedProject\\.project");
		FileOutputStream fos = new FileOutputStream(fout_project);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(java_project_file);
		bw.close();
		
		File fout_classpath = new File(starting_folder + "\\JavaTranslatedProject\\.classpath");
		fos = new FileOutputStream(fout_classpath);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		bw.write(java_project_classpath);
		bw.close();



	}


}
