package it.polito.toggle.ToggleInteractions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import it.polito.toggle.TextManipulationTools;
import it.polito.toggle.ToggleInteraction;

public class TypeIntoFocused extends ToggleInteraction {
	
	
	private String text;

	public TypeIntoFocused(String packagename, String search_type, String search_keyword, String timestamp,
			String interaction_type, String args, File screen_capture, File dump)
			throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		super(packagename, search_type, search_keyword, timestamp, interaction_type, args, screen_capture, dump);
		
		this.need_screenshot = false;
		text = args;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ArrayList<String> generateSikuliLines() {
		ArrayList<String> res = new ArrayList<>();
		res.add("type(\"" + text + "\")");

		return res;
		
		
	}
	
	@Override
	public ArrayList<String> generateEyeStudioLines() {
		ArrayList<String> res = new ArrayList<>();
		res.add("Type \"" + text + "\"");

		return res;
	
	}
	
	
	@Override
	public void extractBounds() {
		return;
	}
	
	
	@Override
	public ArrayList<String> generateEyeAutomateJavaLines(String starting_folder) {
		ArrayList<String> res = new ArrayList<>();
		
		res.add("eye.type(\"" + text + "\");");
		
		
		return res;

	}
	
	@Override
	public ArrayList<String> generateSikuliJavaLines(String starting_folder) {
		ArrayList<String> res = new ArrayList<>();
		
		res.add("sikuli_screen.type(\"" + text + "\");");
		
		return res;

	}
	
	@Override
	public ArrayList<String> generateCombinedJavaLines(String starting_folder) {

		
		ArrayList<String> res = new ArrayList<>();
		
		
		
		//IS IT POSSIBLE TO HAVE EXCEPTIONS IN THIS SIMPLE OPERATIONS WITH EYEAUTOMATE??? CHECK
		
		res.add("try {");
		res.add("\teye.type(\"" + text + "\");");
		res.add("}");
		res.add("catch (Exception e) {");
		res.add("\teyeautomate_failures++;");
		res.add("\tSystem.out.println(\"Exception with EyeAutomate\");");
		res.add("\ttry {");
		res.add("\t\tsikuli_screen.type(\"" + text + "\");");
		res.add("\t}");
		res.add("\tcatch (Exception e2) {");
		res.add("\t\tSystem.out.println(\"Exception with Sikuli\");");
		res.add("\t\treturn \"fail;\" + eyeautomate_failures + \";\" + interactions;");
		res.add("\t}");
		res.add("}");



		return res;
		
	}
	
	
	@Override
	public ArrayList<String> generateCombinedJavaLinesSikuliFirst(String starting_folder) {
	
		ArrayList<String> res = new ArrayList<String>();
		
		res.add("try {");
		res.add("\tsikuli_screen.type(\"" + text + "\");");
		res.add("}");
		res.add("catch (Exception e) {");
		res.add("\tsikuli_failures++;");
		res.add("\tSystem.out.println(\"Exception with Sikuli\");");
		res.add("\ttry {");
		res.add("\t\teye.type(\"" + text + "\");");
		res.add("\t}");
		res.add("\tcatch (Exception e2) {");
		res.add("\t\tSystem.out.println(\"Exception with Eyeautomate\");");
		res.add("\t\treturn \"fail;\" + sikuli_failures + \";\" + interactions;");
		res.add("\t}");
		res.add("}");

		
		
		return res;
		
	}
	
}
