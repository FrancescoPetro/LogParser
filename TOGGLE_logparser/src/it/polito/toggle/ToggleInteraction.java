package it.polito.toggle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public abstract class ToggleInteraction {
	
	protected String packagename; //DOVE METTERLO???	
	
	protected String search_type;
	protected String search_keyword;
	protected String timestamp;
	protected String interaction_type;
	protected String args; //optional
	protected boolean need_screenshot;
	
	protected File screen_capture;
	protected File dump;
	
	protected int left;
	protected int top;
	protected int right;
	protected int bottom;
	
	protected BufferedImage cropped_image;
	protected File cropped_screenshot_file;
	
	protected int interaction_number; //used for translations that require variable instances

	
	
	
	
	
	public ToggleInteraction(String packagename, String search_type, String search_keyword, String timestamp, String interaction_type, String args, File screen_capture, File dump) throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		
		this.packagename = packagename;
		this.search_type = search_type;
		this.search_keyword = search_keyword;
		this.screen_capture = screen_capture;
		this.timestamp = timestamp;
		this.interaction_type = interaction_type;
		this.args = args;
		this.dump = dump;		
		this.need_screenshot = true;
		this.extractBounds();
		
		interaction_number = 1;
		
		
	}
	
	
	public boolean needScreenshot() {
		return need_screenshot;
	}
	
	
	
	public String getSearchType() {
		return search_type;
	}
	
	public String getSearchKeyword() {
		return search_keyword;
	}
	
	public File getScreenCapture() {
		return screen_capture;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public File getDump() {
		return dump;
	}
	
	public void setDump(File dump) {
		this.dump = dump;
	}
	
	public String toString() {
		
		return this.timestamp + ", " + this.search_type + ", " + this.search_keyword + ", " + this.interaction_type;
	}
	
	
	
	
	
	public BufferedImage getCropped_image() {
		return cropped_image;
	}






	public File getCropped_screenshot_file() {
		return cropped_screenshot_file;
	}






	public String getInteractionType () {
		return interaction_type;
	}
	
	
	public ArrayList<String> generateEyeAutomateJavaLines(String starting_folder) {
		
		//TRANSLATES OPERATIONS TO EYEAUTOMATE API CALLS IN PURE JAVA
		
		ArrayList<String> res = new ArrayList<>();
		
		System.out.println("interaction type " + interaction_type + " not found");
		
		return res;
	}
	
	
	public ArrayList<String> generateSikuliJavaLines(String starting_folder) {
		
		//TRANSLATES OPERATIONS TO SIKULI API CALLS IN PURE JAVA
		
		ArrayList<String> res = new ArrayList<>();
		
		System.out.println("interaction type " + interaction_type + " not found");
		
		return res;
	}
	
	
	public ArrayList<String> generateCombinedJavaLines(String starting_folder) {
		
		//TRANSLATES OPERATIONS TO COMBINED (EYEAUTOMATE THEN SIKULI) JAVA CODE
		
		ArrayList<String> res = new ArrayList<>();
		
		System.out.println("interaction type " + interaction_type + " not found");
		
		return res;
	}
	
	
	public ArrayList<String> generateCombinedJavaLinesSikuliFirst(String starting_folder) {
		
		ArrayList<String> res = new ArrayList<>();
		
		System.out.println("interaction type " + interaction_type + " not found");
		
		return res;
	}
	
	
	public ArrayList<String> generateSikuliLines() {
		
		//TRANSLATES OPERATIONS TO SIKULI
		
		ArrayList<String> res = new ArrayList<>();
		
		System.out.println("interaction type " + interaction_type  + " not found");
	
		return res;
	}
	
	
	
	
	public ArrayList<String> generateEyeStudioLines() {
		
		//TRANSLATES OPERATIONS TO SCRIPTS TO BE LOADED IN EYESTUDIO
		
		ArrayList<String> res = new ArrayList<>();
			
		System.out.println("interaction type " + interaction_type  + " not found");
		
		return res;
	}
	
	
	
	
	
	
	
	
	//EXTRACT THE BOUNDS FROM THE IMAGE
	public void extractBounds() throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {
		
		if (interaction_type.equals("fullcheck") || interaction_type.equals("pressback") || interaction_type.equals("closekeyboard")) return;
			
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
			
		String bounds = "";
			
		Document document = builder.parse(dump);
			
		Element root = document.getDocumentElement();
			
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();			
		XPathExpression expr = null;
			
		if (search_type.equals("id")) {
			expr = xpath.compile("//node[@resource-id=\"" + packagename + ":id/" + search_keyword + "\"]");
		}
		else if (search_type.equals("text")) {
			expr = xpath.compile("//node[@text=\"" + search_keyword + "\"]");
			//TODO vedere come si comporta se il testo è preso da string resources 
		}
		else if (search_type.equals("content-desc")) {
			expr = xpath.compile("//node[@content-desc=\"" + search_keyword + "\"]");
		}
		
		NodeList nl = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
			
		if (nl != null) 
			for (int i=0; i<nl.getLength(); i++) 
				bounds = (nl.item(i).getAttributes().getNamedItem("bounds").toString());
			
		String[] splitted_string = bounds.split("(\\[)|(\\])|((,))");
						
		left = Integer.valueOf(splitted_string[1]);
		top = Integer.valueOf(splitted_string[2]);
		
		right = Integer.valueOf(splitted_string[4]);
		bottom = Integer.valueOf(splitted_string[5]);
			
		

	}
	
	
	
	public File manageScreenshot(String starting_folder) throws IOException {
		
		
		cropped_image = ImageManipulationTools.cutImage(screen_capture, left, top, right, bottom);
		cropped_screenshot_file = ImageManipulationTools.saveCroppedScreenshotToFile(starting_folder, timestamp, ImageManipulationTools.resizeScreenshot(cropped_image,1080,369));
		return cropped_screenshot_file;
		
	}
	

	
	
}
