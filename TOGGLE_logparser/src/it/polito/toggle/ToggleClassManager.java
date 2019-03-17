package it.polito.toggle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class ToggleClassManager {

	private static final String logcat_filename = "logcat.txt";
	private static List<ToggleInteraction> interactions = new ArrayList<>();
	private static final String logcat_tool_tag = "touchtest";


	private String class_name;
	private String package_name;
	private String starting_folder;
	private ArrayList<String> testNames;

	private Set<String> settings;

	private Integer avdHeight;
	private Integer avdRes;


	public ToggleClassManager(String class_name, String package_name, String starting_folder,
			ArrayList<String> testNames, Integer avdHeight, Integer avdRes, Set<String> settings) {
		super();
		this.class_name = class_name;
		this.package_name = package_name;
		this.starting_folder = starting_folder;
		this.testNames = testNames;
		this.avdHeight=avdHeight;
		this.avdRes=avdRes;
		this.settings=settings;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getPackage_name() {
		return package_name;
	}

	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}

	public String getStarting_folder() {
		return starting_folder;
	}

	public void setStarting_folder(String starting_folder) {
		this.starting_folder = starting_folder;
	}

	public ArrayList<String> getTestNames() {
		return testNames;
	}

	public void setTestNames(ArrayList<String> testNames) {
		this.testNames = testNames;
	}



	//***************
	//returns in a string the right header for the creation of a class
	//***************

	private static ArrayList<String> createHeaders() {

		ArrayList<String> res = new ArrayList<String>();

		res.add("import java.awt.AWTException;");
		res.add("import java.awt.MouseInfo;");
		res.add("import java.awt.Robot;");
		res.add("import java.awt.event.InputEvent;");
		res.add("import java.awt.image.BufferedImage;");
		res.add("import java.io.File;");
		res.add("import java.io.IOException;");
		res.add("import javax.imageio.ImageIO;");
		res.add("import eyeautomate.ScriptRunner;");
		res.add("import eyeautomate.VizionEngine;");
		res.add("import org.sikuli.script.*;");
		res.add("import eye.Eye;");
		res.add("import eye.Eye.RecognitionMode;");
		res.add("import eye.Match;");
		res.add("import eyeautomate.*;");

		return res;
	}



	private ArrayList<String> createEyeAutomateOrSikuliJavaMain() {

		ArrayList<String> res = new ArrayList<String>();
		res.add("public static void main(String[] args) throws InterruptedException {");
		res.add("\n");
		res.add("\tint tests_ok = 0;");
		res.add("\tint tests_failed = 0;");

		res.add("\tlong startTime = 0;");
		res.add("\tlong endTime = 0;");
		res.add("\tlong executionTime = 0;");
		res.add("\tString curr_test_return = \"\";");
		res.add("\tString curr_test_res = \"\";");
		res.add("\tint curr_test_interactions = 0;");

		for (String test: testNames) {

			res.add("\tSystem.out.println(\"Starting test + " + test + "\");");

			res.add("\tstartTime = System.currentTimeMillis();");
			res.add("\ttry {");
			res.add("\t\tcurr_test_return = " + test + "();");
			//res.add("\t\tif (curr_test_return == true) {");
			//res.add("\t\tcurr_test_return = " + test + "();");
			res.add("\t\tcurr_test_res = curr_test_return.split(\";\")[0];");
			res.add("\t\tcurr_test_interactions = Integer.valueOf(curr_test_return.split(\";\")[1]);");
			res.add("\t\tif (curr_test_res.equals(\"pass\")) {");


			res.add("\t\t\tSystem.out.println(\"" + test + " ok\");");
			res.add("\t\t\ttests_ok++;");
			res.add("\t\t}");
			res.add("\t\telse {");
			res.add("\t\t\tSystem.out.println(\"" + test + " failed\");");
			res.add("\t\t\ttests_failed++;");
			res.add("\t\t}");
			res.add("\t}");
			res.add("\tcatch (Exception e) {");
			res.add("\t\tSystem.out.println(\"" + test + " failed: \" + e.getMessage());");
			res.add("\t\ttests_failed++;");
			res.add("\t}");


			res.add("\tendTime = System.currentTimeMillis();");
			res.add("\texecutionTime = endTime - startTime;");
			res.add("\tSystem.out.println(\"Execution time: \" + executionTime);");
			res.add("\tSystem.out.println(\"" + package_name + ";" + class_name + ";" + test + ";\" + curr_test_res +\";\" + executionTime + \";\" +  curr_test_interactions);");
			res.add("\tThread.sleep(2000);");

			res.add("\n\n");

		}

		res.add("\tSystem.out.println(\"Passed tests: \" + tests_ok);");
		res.add("\tSystem.out.println(\"Failed tests: \" + tests_failed);");


		res.add("\treturn;");
		res.add("}");

		return res;

	}

	//returns the main that calls all the tests one by one and verifies how many of them failed or not
	private ArrayList<String> createCombinedMainEyeAutomateFirst() {


		ArrayList<String> res = new ArrayList<String>();



		res.add("public static void main(String[] args) throws InterruptedException {");
		res.add("\n");
		res.add("\tint tests_ok = 0;");
		res.add("\tint tests_failed = 0;");

		res.add("\tint eyeautomate_failures = 0; //number of fallbacks");
		res.add("\tint curr_test_interactions = 0;");


		res.add("\tlong startTime = 0;");
		res.add("\tlong endTime = 0;");
		res.add("\tlong executionTime = 0;");
		res.add("\tString curr_test_return = \"\";");
		res.add("\tString curr_test_res = \"\";");

		res.add("\tint curr_test_eyeautomate_failures = 0;");


		for (String test: testNames) {

			res.add("\tSystem.out.println(\"Starting test + " + test + "\");");

			res.add("\tstartTime = System.currentTimeMillis();");
			res.add("\ttry {");
			res.add("\t\tcurr_test_return = " + test + "();");
			res.add("\t\tcurr_test_res = curr_test_return.split(\";\")[0];");
			res.add("\t\tcurr_test_eyeautomate_failures = Integer.valueOf(curr_test_return.split(\";\")[1]);");
			res.add("\t\tcurr_test_interactions = Integer.valueOf(curr_test_return.split(\";\")[2]);");
			res.add("\t\tif (curr_test_res.equals(\"pass\")) {");
			res.add("\t\t\tSystem.out.println(\"" + test + " ok\");");
			res.add("\t\t\ttests_ok++;");
			res.add("\t\t}");
			res.add("\t\telse {");
			res.add("\t\t\tSystem.out.println(\"" + test + " failed\");");
			res.add("\t\t\ttests_failed++;");
			res.add("\t\t}");
			res.add("\t}");
			res.add("\tcatch (Exception e) {");
			res.add("\t\tSystem.out.println(\"" + test + " failed: \" + e.getMessage());");
			res.add("\t\ttests_failed++;");
			res.add("\t}");

			res.add("\tSystem.out.println(\"Number of EyeAutomate failures: \" + curr_test_eyeautomate_failures);");
			res.add("\teyeautomate_failures += curr_test_eyeautomate_failures;");


			res.add("\tendTime = System.currentTimeMillis();");
			res.add("\texecutionTime = endTime - startTime;");
			res.add("\tSystem.out.println(\"Execution time: \" + executionTime);");


			res.add("\tSystem.out.println(\"" + package_name + ";" + class_name + ";" + test + ";\" + curr_test_res +\";\" + executionTime + \";\" +  curr_test_interactions + \";\" + curr_test_eyeautomate_failures);");
			res.add("\tThread.sleep(2000);");
			res.add("\n\n");

		}

		res.add("\tSystem.out.println(\"Passed tests: \" + tests_ok);");
		res.add("\tSystem.out.println(\"Failed tests: \" + tests_failed);");
		res.add("\tSystem.out.println(\"Total Eyeautomate failures: \" + eyeautomate_failures);");



		res.add("\treturn;");
		res.add("}");

		return res;


	}

	private ArrayList<String> createCombinedMainSikuliFirst() {


		ArrayList<String> res = new ArrayList<String>();		

		res.add("public static void main(String[] args) throws InterruptedException {");
		res.add("\n");
		res.add("\tint tests_ok = 0;");
		res.add("\tint tests_failed = 0;");

		res.add("\tint sikuli_failures = 0; //number of fallbacks");
		res.add("\tint curr_test_interactions = 0;");

		res.add("\tlong startTime = 0;");
		res.add("\tlong endTime = 0;");
		res.add("\tlong executionTime = 0;");
		res.add("\tString curr_test_return = \"\";");
		res.add("\tString curr_test_res = \"\";");

		res.add("\tint curr_test_sikuli_failures = 0;");


		for (String test: testNames) {

			res.add("\tstartTime = System.currentTimeMillis();");
			res.add("\ttry {");
			res.add("\t\tcurr_test_return = " + test + "();");
			res.add("\t\tcurr_test_res = curr_test_return.split(\";\")[0];");
			res.add("\t\tcurr_test_sikuli_failures = Integer.valueOf(curr_test_return.split(\";\")[1]);");
			res.add("\t\tcurr_test_interactions = Integer.valueOf(curr_test_return.split(\";\")[2]);");

			res.add("\t\tif (curr_test_res.equals(\"pass\")) {");
			res.add("\t\t\tSystem.out.println(\"" + test + " ok\");");
			res.add("\t\t\ttests_ok++;");
			res.add("\t\t}");
			res.add("\t\telse {");
			res.add("\t\t\tSystem.out.println(\"" + test + " failed\");");
			res.add("\t\t\ttests_failed++;");
			res.add("\t\t}");
			res.add("\t}");
			res.add("\tcatch (Exception e) {");
			res.add("\t\tSystem.out.println(\"" + test + " failed: \" + e.getMessage());");
			res.add("\t\ttests_failed++;");
			res.add("\t}");


			res.add("\tSystem.out.println(\"Number of Sikuli failures: \" + curr_test_sikuli_failures);");
			res.add("\tsikuli_failures += curr_test_sikuli_failures;");


			res.add("\tendTime = System.currentTimeMillis();");
			res.add("\texecutionTime = endTime - startTime;");
			res.add("\tSystem.out.println(\"Execution time: \" + executionTime);");


			res.add("\n\n");
			res.add("\tThread.sleep(2000);");
			res.add("\tSystem.out.println(\"" + package_name + ";" + class_name + ";" + test + ";\" + curr_test_res +\";\" + executionTime + \";\" +  curr_test_interactions + \";\" + curr_test_sikuli_failures);");
			res.add("\n\n");

		}

		res.add("\tSystem.out.println(\"Passed tests: \" + tests_ok);");
		res.add("\tSystem.out.println(\"Failed tests: \" + tests_failed);");
		res.add("\tSystem.out.println(\"Total Sikuli failures: \" + sikuli_failures);");



		res.add("\treturn;");
		res.add("}");

		return res;


	}

	public ArrayList<String> createClass() throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, ToggleException {

		//TODO
		//FUNZIONAMENTO: si aggiunge al logger come primo parametro dopo TOGGLETOOL il nome del test;
		//createClass lancia per ogni nome di test ricevuto un toggle translator.
		//gli script sikuli ed eyeautomate vengono salvati direttamente all'interno della cartella con le giuste immagini
		//gli script java vengono salvati all'interno di una cartella dove:
		//1) si salvano le immagini (tutte)
		//2) si crea una classe Main.java, la classe main contiene un metodo per ogni test + nel main lancia tutti i test e fa girare statistiche su quanti hanno ritornato true e quanti null


		int method_interactions = 0;

		ArrayList<String> test_class_code = new ArrayList<String>();
		ArrayList<String> eyeautomate_only = new ArrayList<String>();

		ArrayList<String> sikuli_only = new ArrayList<String>();

		ArrayList<String> eyeautomate_sikuli = new ArrayList<String>();

		ArrayList<String> sikuli_eyeautomate = new ArrayList<String>();		


		//add the headers
		for (String s: createHeaders()) {
			eyeautomate_only.add(s);
			sikuli_only.add(s);
			eyeautomate_sikuli.add(s);
			sikuli_eyeautomate.add(s);
		}

		//add class spec
		eyeautomate_only.add("\n\n");
		eyeautomate_only.add("public class " + class_name + "EyeAutomate { ");
		eyeautomate_only.add("\n\n");

		sikuli_only.add("\n\n");
		sikuli_only.add("public class " + class_name + "Sikuli { ");
		sikuli_only.add("\n\n");


		eyeautomate_sikuli.add("\n\n");
		eyeautomate_sikuli.add("public class " + class_name + "EyeAutomateSikuli { ");
		eyeautomate_sikuli.add("\n\n");


		sikuli_eyeautomate.add("\n\n");
		sikuli_eyeautomate.add("public class " + class_name+ "SikuliEyeAutomate { ");
		sikuli_eyeautomate.add("\n\n");


		//add the methods
		for (String test_name: testNames) {

			method_interactions = 0;

			System.out.println("Starting folder: "+starting_folder);
			System.out.println("Package: "+package_name);
			System.out.println("Class: "+class_name);
			System.out.println("Test: "+test_name);

			ToggleTranslator tt = new ToggleTranslator(starting_folder, package_name, class_name, test_name, avdHeight,avdRes);


			tt.readLogcatToFile(logcat_filename);				


			List<String> filtered_logcat_interactions = tt.filterLogcat(logcat_filename, logcat_tool_tag);


			interactions = new ArrayList<ToggleInteraction>();

			for (String s:filtered_logcat_interactions) {
				ToggleInteraction interaction=null;
				try {
					interaction= tt.readInteractionsFromLogcat(s);
				}
				catch(Exception e){
					
					System.out.println("EXCEPTION! "+e.getMessage());
					
					interaction= tt.readInteractionsFromLogcat(s);
				}

				interactions.add(interaction);
				method_interactions++;

			}


			//never comment
			try {
				tt.saveCroppedScreenshots(interactions);
			}
			catch(Exception e) {
				
				System.out.println("EXCEPTION! "+e.getMessage());
				
				for (String s:filtered_logcat_interactions) {
					ToggleInteraction interaction= tt.readInteractionsFromLogcat(s);

					interactions.add(interaction);
					method_interactions++;

				}
				tt.saveCroppedScreenshots(interactions);
			}

			for(String s:settings) {
				if(s=="eye")
					tt.createEyeStudioScript(interactions);
				if(s=="sikuli")
					tt.createSikuliScript(interactions);
			}

			//tt.createEyeStudioScript(interactions);
			//tt.createSikuliScript(interactions);

			for (String method_line: tt.createEyeAutomateJavaMethod(interactions)) {
				eyeautomate_only.add(method_line);
			}
			for (String method_line: tt.createSikuliJavaMethod(interactions)) {
				sikuli_only.add(method_line);
			}
			for (String method_line: tt.createCombinedJavaMethod(interactions)) {
				eyeautomate_sikuli.add(method_line);
			}
			for (String method_line: tt.createCombinedJavaMethodSikuliFirst(interactions)) {
				sikuli_eyeautomate.add(method_line);
			}

			eyeautomate_only.add("\n\n\n");
			sikuli_only.add("\n\n\n");
			eyeautomate_sikuli.add("\n\n\n");
			sikuli_eyeautomate.add("\n\n\n");


		}

		//add the main function
		eyeautomate_only.add("\n\n\n");
		sikuli_only.add("\n\n\n");
		eyeautomate_sikuli.add("\n\n\n");
		sikuli_eyeautomate.add("\n\n\n");

		for (String main_line: this.createEyeAutomateOrSikuliJavaMain()) {
			eyeautomate_only.add(main_line);
			sikuli_only.add(main_line);
		}
		for (String main_line: this.createCombinedMainEyeAutomateFirst()) {
			eyeautomate_sikuli.add(main_line);
		}
		for (String main_line: this.createCombinedMainSikuliFirst()) {
			sikuli_eyeautomate.add(main_line);
		}




		//add closure of function

		eyeautomate_only.add("\n\n");
		eyeautomate_only.add("}");

		sikuli_only.add("\n\n");
		sikuli_only.add("}");

		eyeautomate_sikuli.add("\n\n");
		eyeautomate_sikuli.add("}");

		sikuli_eyeautomate.add("\n\n");
		sikuli_eyeautomate.add("}");



		FileOutputStream fos;
		BufferedWriter bw;

		/*File foutjava_eyeautomate = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "EyeAutomate.java");
		fos = new FileOutputStream(foutjava_eyeautomate);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (String s:eyeautomate_only) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();

		File foutjava_sikuli = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "Sikuli.java");
		fos = new FileOutputStream(foutjava_sikuli);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (String s:sikuli_only) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();

		File foutjava_eyeautomate_sikuli = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "EyeAutomateSikuli.java");
		fos = new FileOutputStream(foutjava_eyeautomate_sikuli);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (String s:eyeautomate_sikuli) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();

		File foutjava_sikuli_eyeautomate = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "SikuliEyeAutomate.java");
		fos = new FileOutputStream(foutjava_sikuli_eyeautomate);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		for (String s:sikuli_eyeautomate) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();*/

		for(String setting:settings) {
			if(setting=="javaEyeAutomate") {

				System.out.println("SETTING: "+setting);

				File foutjava_eyeautomate = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "EyeAutomate.java");
				fos = new FileOutputStream(foutjava_eyeautomate);
				bw = new BufferedWriter(new OutputStreamWriter(fos));
				for (String s:eyeautomate_only) {
					bw.write(s);
					bw.newLine();
				}
				bw.close();

			}

			if(setting=="javaSikuli") {
				System.out.println("SETTING: "+setting);

				File foutjava_sikuli = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "Sikuli.java");
				fos = new FileOutputStream(foutjava_sikuli);
				bw = new BufferedWriter(new OutputStreamWriter(fos));
				for (String s:sikuli_only) {
					bw.write(s);
					bw.newLine();
				}
				bw.close();
			}

			if(setting=="eyeSikuli") {
				System.out.println("SETTING: "+setting);

				File foutjava_eyeautomate_sikuli = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "EyeAutomateSikuli.java");
				fos = new FileOutputStream(foutjava_eyeautomate_sikuli);
				bw = new BufferedWriter(new OutputStreamWriter(fos));
				for (String s:eyeautomate_sikuli) {
					bw.write(s);
					bw.newLine();
				}
				bw.close();
			}

			if(setting=="sikuliEye") {
				System.out.println("SETTING: "+setting);

				File foutjava_sikuli_eyeautomate = new File(starting_folder + "\\JavaTranslatedProject\\src\\" + class_name + "SikuliEyeAutomate.java");
				fos = new FileOutputStream(foutjava_sikuli_eyeautomate);
				bw = new BufferedWriter(new OutputStreamWriter(fos));
				for (String s:sikuli_eyeautomate) {
					bw.write(s);
					bw.newLine();
				}
				bw.close();
			}

		}


		//tt.clearLogcat();


		return test_class_code;
	}

}
