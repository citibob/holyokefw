/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package beans2nbm.gen;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author Tim Boudreau
 */
public class Beans2Nbm {

	
public static class Params {
		public String version = "";
		public String projectHome = "";
		public String jarFolder = "";
		public String jarName = "";
		
		public String description = "";
		public String homepage = "";
		public String codeName = "";
		public String author = "";
		public String docsJar = null;
		public String sourceJar = null;
		public String displayName = "";
		public String license = "";
		public String minJDK = "";
		public String[] beanNames;
}


static void testNbm(String[] beanNames) throws Exception
{
	for (String name : beanNames) {
		Class klass = Class.forName(name);
		System.out.println("Testing class: " + klass);
		final JFrame frame = new JFrame();
		if (JComponent.class.isAssignableFrom(klass)) {
			JComponent comp = (JComponent)klass.newInstance();
			frame.getContentPane().add(comp);
			frame.pack();
	//        java.awt.EventQueue.invokeLater(new Runnable() {
	//        public void run() {
					frame.setVisible(true);
					frame.setVisible(false);
	 //       }});
		}
	}
}

    /**
     * @param args the command line arguments
     */
    public static void makeNbm(Params prm) throws Exception
	{
		testNbm(prm.beanNames);

		char sep = File.separatorChar;
		String jarFileName = prm.projectHome + sep + prm.jarFolder + sep + prm.jarName + ".jar";
		String destFileName = prm.projectHome + sep + prm.jarFolder + sep + prm.jarName + ".nbm";
		
		File f = new File (destFileName);
		if (f.exists()) {
			f.delete();
		}

		if (!f.createNewFile()) {
			throw new IOException("Could not create " + f.getPath());
		}
		String moduleJarName = prm.codeName.replace('.', '-') + ".jar";

		String jarFileNameSimple = new File (jarFileName).getName();
		NbmFileModel nbm = new NbmFileModel (f.getPath());

		ModuleModel module = new ModuleModel ("netbeans/modules/" + moduleJarName,
			prm.codeName, prm.description, prm.version, prm.displayName, prm.minJDK);
		ModuleInfoModel infoXml = new ModuleInfoModel (module,
			prm.homepage, prm.author, prm.license);

		nbm.add (module);
		nbm.add (infoXml);

		JarToCopyModel libJar = new JarToCopyModel ("netbeans/libs/" + jarFileNameSimple, jarFileName);
		nbm.add (libJar);

		String srcFileNameSimple = null;
		if (prm.sourceJar != null) {
			srcFileNameSimple = new File (prm.sourceJar).getName();
			JarToCopyModel srcJarMdl = new JarToCopyModel (
				"netbeans/sources/" + srcFileNameSimple, prm.sourceJar);
			nbm.add (srcJarMdl);
		}
		String docsJarNameSimple = null;
		if (prm.docsJar != null) {
			docsJarNameSimple = new File (prm.docsJar).getName();
			JarToCopyModel docsJarMdl = new JarToCopyModel (
				"netbeans/docs/" + docsJarNameSimple, prm.docsJar);
			nbm.add (docsJarMdl);
		}
		String codeNameSlashes = prm.codeName.replace('.', '/');
		String companyNameUnderscores = prm.displayName.replace(' ', '_');

		LayerFileModel layer = new LayerFileModel (codeNameSlashes + "/layer.xml",
			companyNameUnderscores, prm.codeName);
		module.addFileEntry(layer);

		layer.addLibraryName(companyNameUnderscores);

		LibDescriptorModel libDesc = new LibDescriptorModel (
			codeNameSlashes + "/" + companyNameUnderscores + ".xml",
			companyNameUnderscores, prm.codeName,
			jarFileNameSimple, srcFileNameSimple, docsJarNameSimple);
		module.addFileEntry(libDesc);
		module.addFileDisplayName("org-netbeans-api-project-libraries/Libraries/" +
			companyNameUnderscores + ".xml", prm.displayName);
		module.addFileDisplayName(companyNameUnderscores, prm.displayName);

		if (prm.beanNames != null) {
			for (String name : prm.beanNames) {
				BeanItem bi = new BeanItem (name);
				String beanClassName = bi.getClassName();
				String beanSimpleName = bi.getSimpleName();
				String paletteItemPathInLayer = "FormDesignerPalette/" + companyNameUnderscores + "/" + beanSimpleName + ".palette_item";
				PaletteItemFileModel palMdl = new PaletteItemFileModel (codeNameSlashes + "/" + beanSimpleName + "_paletteItem.xml", companyNameUnderscores, beanClassName);
				module.addFileEntry(palMdl);
				layer.addBeanEntry(companyNameUnderscores, beanSimpleName);
				module.addFileDisplayName (paletteItemPathInLayer, beanClassName);
			}
		}
		module.addFileDisplayName(codeNameSlashes + "/" + companyNameUnderscores, prm.displayName);

		ModuleXMLModel mxml = new ModuleXMLModel (prm.codeName,prm.version);
		nbm.add(mxml);

		OutputStream out = new BufferedOutputStream (new FileOutputStream (f));
		nbm.write(out);

		out.close();
    }
    
    
}
