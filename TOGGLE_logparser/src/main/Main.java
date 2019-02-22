package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polito.toggle.ToggleClassManager;
import it.polito.toggle.ToggleInteraction;
import it.polito.toggle.ToggleTranslator;
import it.polito.toggle.Utils;


public class Main {
	
	
	private static final String logcat_filename = "logcat.txt";
	
	private static final String logcat_tool_tag = "touchtest";
	
	private static final String logcat_test_tag_screen_size = "touchtest-screen";
	
	private static List<ToggleInteraction> interactions = new ArrayList<>();
	
	private static final String starting_folder = "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest";
	
	private static final String package_name = "it.feio.android.omninotes.foss";
	
	private static final String class_name = "BaseEspressoTest";
	
	private static final String test_name = "testTest";
	
	public static void main(String[] args) throws Exception {
				
		ArrayList<String> tests = new ArrayList<String>();
		
		
		/*tests.add("testTest1");
		ToggleClassManager tcm = new ToggleClassManager("TestClass1", "it.feio.android.omninotes.alpha", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);*/
		
		/*tests.add("testCreatePassIsShown");
		tests.add("testScanIsShown");
		tests.add("testDemoPassIsShown");
		tests.add("testOpenFileIsShown");
		tests.add("testFabButtonGoesBackWithClick");
		tests.add("testWhatIsIt");
		tests.add("testLeftMenuIsShown");
		ToggleClassManager tcm = new ToggleClassManager("TestHomeActivity", "org.ligi.passandroid", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);*/

		//tests.add("testColorWheelIsThere");
		//tests.add("testTimePickerWorks");
		//tests.add("testCalendarIsShown");
		//tests.add("testImageOptionsAreThere");
		//tests.add("testSwipeBackToFirstPage");
		//ToggleClassManager tcm = new ToggleClassManager("TestCreatePassTabsEnhanced", "org.ligi.passandroid", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);
		
		//tests.add("testSetToBoardingPassWorks");
		//tests.add("testSetToEventWorks");
		//tests.add("testSetToCouponWorks");
		//tests.add("testSetToStoreCardWorks");
		//tests.add("testSetToAnyThenGenericWorks");		
		//ToggleClassManager tcm = new ToggleClassManager("TestPassEditActivityTypes", "org.ligi.passandroid", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);

		
		//tests.add("testSetDescriptionWorks");
		//tests.add("testCanSetToQR");
		//tests.add("testCanSetToPDF417");
		//tests.add("testCanSetToAZTEC");
		//tests.add("testCanSetMessage");		
		//tests.add("testCanSetAltMessage");		

		//ToggleClassManager tcm = new ToggleClassManager("TestEditPassProperties", "org.ligi.passandroid", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);

		
		//tests.add("testCreatePass");
		//tests.add("testCreateMultiplePasses");
		//tests.add("testDeletePass");
		//tests.add("testDeleteWithLongClick");
		//tests.add("testEditPass");
		//tests.add("testMenuIsShownUponClick");
		//tests.add("testMenuIsShownUponLongClick");
		
		//ToggleClassManager tcm = new ToggleClassManager("TestPassCreation", "org.ligi.passandroid", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);

		
		tests.add("testTextNoteIsDisplayed");
		tests.add("testChecklistIsDisplayed");
		tests.add("testCameraIsDisplayed");
		tests.add("testReducedViewMenu");
		tests.add("testLeftMenuContent");
		tests.add("testOrderByMenu");
		tests.add("testSearchButtons");

		ToggleClassManager tcm = new ToggleClassManager("TestHome", "it.feio.android.omninotes.alpha", "C:\\Users\\Francesco\\Desktop\\touchtest", tests);

		//tests.add("testDeleteNote");
		//tests.add("testRestoreFromTrash");
		//tests.add("testArchiveNote");
		//tests.add("testAddReminder");
		//tests.add("testSwipeLeftRight");
		//ToggleClassManager tcm = new ToggleClassManager("TestNoteCreation", "it.feio.android.omninotes.alpha", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);

		
		//tests.add("testDeleteNote");
		//tests.add("testRestoreFromTrash");
		//tests.add("testEnableChecklist");
		//tests.add("testWrongSearch");
		//tests.add("testWrongSearch");
		//ToggleClassManager tcm = new ToggleClassManager("TestSearchChecklist", "it.feio.android.omninotes.alpha", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);

		//tests.add("testDeleteNote");
		//tests.add("testRestoreFromTrash");
		//tests.add("testOrderByCreationDate");
		//tests.add("testTrashUndo");
		//tests.add("testOrderByModificationDate");
		//ToggleClassManager tcm = new ToggleClassManager("TestUndoOrder", "it.feio.android.omninotes.alpha", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);


		//tests.add("testDeleteNote");
		//tests.add("testRestoreFromTrash");
		//tests.add("testSettings1");
		//tests.add("testTrashUndo");
		//tests.add("testSettings2");
		//ToggleClassManager tcm = new ToggleClassManager("TestSettings", "it.feio.android.omninotes.alpha", "C:\\Users\\Riccardo Coppola\\Desktop\\touchtest", tests);

		
		ArrayList<String> result_class = tcm.createClass();
		
		for (String s: result_class) {
			System.out.println(s);
		}
		
		
		
		/*ToggleTranslator tt = new ToggleTranslator(starting_folder, package_name, class_name, test_name);
		
		tt.readLogcatToFile(logcat_filename);				
		
		
		
		
		List<String> filtered_logcat_interactions = tt.filterLogcat(logcat_filename, logcat_tool_tag);
		
		
		for (String s:filtered_logcat_interactions) {
			
			String[] separated = s.split(": ");
			String line_data = separated[1];						
			String[] separated2 = line_data.split(", ");

			
			ToggleInteraction interaction = tt.readInteractionsFromLogcat(s);
			
			interactions.add(interaction);

		}*/
		
		//tt.saveCroppedScreenshots(interactions);
		//tt.createEyeStudioScript(interactions);
		//tt.createSikuliScript(interactions);
		//tt.createEyeAutomateJavaMethod(interactions);
		//tt.createSikuliJavaMethod(interactions);
		//tt.createCombinedJavaMethod(interactions);

		
		//COMMENT ONLY FOR DEBUG
		
		//Utils.deleteDirContents(tt.getStarting_folder());
		
		//tt.clearLogcat();
		
		
	}

}
