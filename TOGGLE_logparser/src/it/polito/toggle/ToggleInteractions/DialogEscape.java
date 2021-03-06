package it.polito.toggle.ToggleInteractions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import it.polito.toggle.KeyCodeConstants;
import it.polito.toggle.TextManipulationTools;
import it.polito.toggle.ToggleInteraction;

public class DialogEscape extends ToggleInteraction {
	
	

	public DialogEscape(String packagename, String search_type, String search_keyword, String timestamp,
			String interaction_type, String args, File screen_capture, File dump)
			throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		super(packagename, search_type, search_keyword, timestamp, interaction_type, args, screen_capture, dump);
		
		this.need_screenshot = false;
		// TODO Auto-generated constructor stub
	}
	
	
	public ArrayList<String> generateSikuliLines() {
		ArrayList<String> res = new ArrayList<>();

		res.add("type(Key.TAB)");
		res.add("type(Key.TAB)");
		res.add("type(Key.TAB)");
		res.add("type(Key.TAB)");
		res.add("type(Key.TAB)");

		res.add("type(Key.RIGHT)");		
		res.add("type(Key.RIGHT)");
		res.add("type(Key.RIGHT)");
		res.add("type(Key.RIGHT)");
		res.add("type(Key.RIGHT)");
		
		res.add("type(Key.ENTER");

		return res;
		
		
	}
	
	
	public ArrayList<String> generateEyeStudioLines() {
		ArrayList<String> res = new ArrayList<>();

		res.add("Type [TAB]");
		res.add("Type [TAB]");
		res.add("Type [TAB]");
		res.add("Type [TAB]");
		res.add("Type [TAB]");

		
		
		res.add("Type [RIGHT]");
		res.add("Type [RIGHT]");
		res.add("Type [RIGHT]");
		res.add("Type [RIGHT]");
		res.add("Type [RIGHT]");

		res.add("Type [ENTER]");

		
		return res;
	
	}
	
	@Override
	public void extractBounds() {
		return;
	}

	@Override
	public ArrayList<String> generateEyeAutomateJavaLines(String starting_folder) {

		ArrayList<String> res = new ArrayList<>();

		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");

		res.add("\teye.type(\"[ENTER]\");");

		return res;

	}
	
	@Override
	public ArrayList<String> generateSikuliJavaLines(String starting_folder) {

		ArrayList<String> res = new ArrayList<>();

		res.add("\tsikuli_screen.type(Key.TAB);");
		res.add("\tsikuli_screen.type(Key.TAB);");
		res.add("\tsikuli_screen.type(Key.TAB);");
		res.add("\tsikuli_screen.type(Key.TAB);");
		res.add("\tsikuli_screen.type(Key.TAB);");

		res.add("\tsikuli_screen.type(Key.RIGHT);");
		res.add("\tsikuli_screen.type(Key.RIGHT);");
		res.add("\tsikuli_screen.type(Key.RIGHT);");
		res.add("\tsikuli_screen.type(Key.RIGHT);");
		res.add("\tsikuli_screen.type(Key.RIGHT);");

		res.add("\tsikuli_screen.type(Key.ENTER);");


		
		return res;

	}
	
	@Override
	public ArrayList<String> generateCombinedJavaLines(String starting_folder) {

		
		ArrayList<String> res = new ArrayList<>();
		
		
		
		//IS IT POSSIBLE TO HAVE EXCEPTIONS IN THIS SIMPLE OPERATIONS WITH EYEAUTOMATE??? CHECK
		
		res.add("try {");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");

		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[ENTER]\");");

		res.add("}");
		res.add("catch (Exception e) {");
		res.add("\teyeautomate_failures++;");
		res.add("\tSystem.out.println(\"catched exception with EyeAutomate\");");
		res.add("\ttry{");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");

		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");

		res.add("\t\tsikuli_screen.type(Key.ENTER);");

		res.add("\t}");
		res.add("\tcatch (Exception e2) {");
		res.add("\t\tSystem.out.println(\"catched exception with Sikuli\");");
		res.add("\t\treturn \"fail;\" + eyeautomate_failures + \";\" + interactions;");
		res.add("\t}");
		res.add("}");



		return res;
		
	}
	
	@Override
	public ArrayList<String> generateCombinedJavaLinesSikuliFirst(String starting_folder) {
	
		ArrayList<String> res = new ArrayList<String>();
		
		res.add("try {");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");
		res.add("\t\tsikuli_screen.type(Key.TAB);");

		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");
		res.add("\t\tsikuli_screen.type(Key.RIGHT);");

		res.add("\t\tsikuli_screen.type(Key.ENTER);");

		res.add("}");
		res.add("catch (Exception e) {");
		res.add("\tsikuli_failures++;");
		res.add("\tSystem.out.println(\"Exception with Sikuli\");");
		res.add("\ttry {");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");
		res.add("\teye.type(\"[TAB]\");");

		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");
		res.add("\teye.type(\"[RIGHT]\");");

		res.add("\teye.type(\"[ENTER]\");");

		res.add("\t}");
		res.add("\tcatch (Exception e2) {");
		res.add("\t\tSystem.out.println(\"Exception with Eyeautomate\");");
		res.add("\t\treturn \"fail;\" + sikuli_failures + \";\" + interactions;");
		res.add("\t}");
		res.add("}");
		
		return res;
		
	}
	
}
