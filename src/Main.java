
import citibob.config.ZipConfig;
import citibob.gui.AppLauncher;
import citibob.io.RecursiveDirIterator;
import citibob.io.RobustOpen;
import citibob.licensor.Licensor;
import citibob.licensor.MakeNbm;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import citibob.mobilecode.MobileCodeClient;
import citibob.mobilecode.MobileCodeServer;
import citibob.reflect.ClassAnalyzer;
import citibob.reflect.ClassPathUtils;
import citibob.swing.calendar.JDayChooser;
import citibob.swing.typed.JKeyedMulti;
import citibob.swing.typed.JKeyedMultiPanel;
import citibob.text.NoYearDateFormat;
import citibob.util.IndexSet;
import org.xnap.commons.gui.CloseableTabbedPane;

/**
 *
 * @author citibob
 */
public class Main {
public static void main(String[] args) throws Exception {
//	
//	
//	
//	
//	
//	
//	
//	
//                bsh.Interpreter bsh = new bsh.Interpreter();
//
//                // Evaluate statements and expressions
//                bsh.eval("foo=Math.sin(0.5)");
//                bsh.eval("bar=foo*5; bar=Math.cos(bar);");
//                bsh.eval("for(i=0; i<10; i++) { print(\"hello\"); }");
//                // same as above using java syntax and apis only
//                bsh.eval("for(int i=0; i<10; i++) { System.out.println(\"hello\"); }");
//System.exit(0);

	AppLauncher.launch("holyokefw", new Class[] {
		RecursiveDirIterator.class,
		RobustOpen.class,
		ZipConfig.class,
		NoYearDateFormat.class,
		PojoTMTest.class,
		CloseableTabbedPane.class,
		JKeyedMulti.class,
		JKeyedMultiPanel.class,
		JDayChooser.class,
		MobileCodeServer.class,
		MobileCodeClient.class,
		ClassAnalyzer.class,
		ClassPathUtils.class,
		IndexSet.class,
		MakeNbm.class,
//		de.jppietsch.prefedit.PrefEdit.class,
		Licensor.class
	});
}
}
