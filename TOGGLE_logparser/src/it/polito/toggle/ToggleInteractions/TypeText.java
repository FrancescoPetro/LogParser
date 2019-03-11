package it.polito.toggle.ToggleInteractions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import it.polito.toggle.ToggleInteraction;

public class TypeText extends ToggleInteraction {
	
	
	private String text;

	public TypeText(String packagename, String search_type, String search_keyword, String timestamp,
			String interaction_type, String args, File screen_capture, File dump)
			throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		super(packagename, search_type, search_keyword, timestamp, interaction_type, args, screen_capture, dump);
		
		
		text = args;
		// TODO Auto-generated constructor stub
	}
	
	
	public ArrayList<String> generateSikuliLines() {
		ArrayList<String> res = new ArrayList<>();
		res.add("click(\"" + timestamp + "_cropped.png\")");
		res.add("type(\"" + text + "\")");

		return res;
		
		
	}
	
	
	public ArrayList<String> generateEyeStudioLines() {
		ArrayList<String> res = new ArrayList<>();

		res.add("Click \"{ImageFolder}\\" + timestamp + "_cropped.png\"");
		res.add("Type \"" + text + "\"");

		return res;
	
	}
	
	@Override
	public ArrayList<String> generateEyeAutomateJavaLines(String starting_folder) {
		ArrayList<String> res = new ArrayList<>();
		
		res.add("image = eye.loadImage(\"" + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");
		res.add("if (image != null) {");
		res.add("\tmatch = eye.findImage(image);");
		res.add("\tif (match == null) {");
		res.add("\t\tSystem.out.println(\"Test failed - " + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");
		res.add("\t\treturn \"fail;\"+interactions;");
		res.add("\t}");
		res.add("\teye.click(match.getCenterLocation());");
		res.add("\teye.type(\"" + text + "\");");
		res.add("}");
		res.add("else {");
		res.add("\tSystem.out.println(\"image not found\");");
		res.add("\treturn \"fail;\"+interactions;");
		res.add("}");

		
		
		return res;

	}
	
	@Override
	public ArrayList<String> generateSikuliJavaLines(String starting_folder) {
		ArrayList<String> res = new ArrayList<>();
		
		res.add("try {");
		res.add("\tsikuli_screen.click(\"" + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");
		res.add("\tsikuli_screen.type(\"" + text + "\");");
		res.add("}");
		res.add("catch (FindFailed ffe) {");
		res.add("\tffe.printStackTrace();");
		res.add("\treturn \"fail;\"+interactions;");
		res.add("}");
		
		return res;

	}
	
	
	@Override
	public ArrayList<String> generateCombinedJavaLines(String starting_folder) {

		
		ArrayList<String> res = new ArrayList<>();

		res.add("image = eye.loadImage(\"" + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");
		res.add("if (image != null) {");
		res.add("\tmatch = eye.findImage(image);");
		
		
		//try sikuli
		res.add("\tif (match == null) {");
		res.add("\t\teyeautomate_failures++;");
		res.add("\t\ttry {");
		res.add("\t\t\tsikuli_screen.click(\"" + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");
		res.add("\t\t\tsikuli_screen.type(\"" + text + "\");");
		res.add("\t\t}");
		res.add("\t\tcatch (FindFailed ffe) {");
		res.add("\t\t\tffe.printStackTrace();");
		res.add("\t\t\treturn \"fail;\" + eyeautomate_failures + \";\" + interactions;");
		res.add("\t\t}");
		res.add("\t}");

		//eyeautomate

		res.add("\telse {");
		res.add("\t\teye.click(match.getCenterLocation());");
		res.add("\t\teye.type(\"" + text + "\");");
		res.add("\t}");
		res.add("}");
		res.add("else {");
		res.add("\tSystem.out.println(\"image not found\");");
		res.add("\treturn \"fail;\" + eyeautomate_failures + \";\" + interactions;");
		res.add("}");


	
		return res;
	}
	
	
	@Override
	public ArrayList<String> generateCombinedJavaLinesSikuliFirst(String starting_folder) {
	
		ArrayList<String> res = new ArrayList<String>();
		

		
		res.add("try {");
		res.add("\tsikuli_screen.click(\"" + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");

		res.add("\tsikuli_screen.type(\"" + text + "\");");

		res.add("}");
		res.add("catch (FindFailed ffe) {");
		res.add("\tsikuli_failures++;");
		res.add("\timage = eye.loadImage(\"" + new String(starting_folder + "\\JavaTranslatedProject\\" + timestamp + "_cropped.png").replace("\\", "\\\\") + "\");");
		res.add("\tif (image != null) {");
		res.add("\t\tmatch = eye.findImage(image);"); 
		res.add("\t\tif (match == null) {");		//test failed also with eyeautomate
		res.add("\t\t\treturn \"fail;\" + sikuli_failures + \";\" + interactions;");
		res.add("\t\t}");
		res.add("\t\telse {");						//test ok with eyeautomate
		res.add("\t\t\teye.click(match.getCenterLocation());");
		res.add("\t\t\teye.type(\"" + text + "\");");

		res.add("\t\t}");
		res.add("\t}");
		res.add("\telse {");
		res.add("\t\tSystem.out.println(\"image not found\");");
		res.add("\t\treturn \"fail;\" + sikuli_failures + \";\" + interactions;");
		res.add("\t}");
		res.add("}");
		
		return res;
		
		
	}
	
}