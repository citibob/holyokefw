/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.app.App;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author citibob
 */
public class MultiConfigMaker implements ConfigMaker {

Object[] configs;

public MultiConfigMaker(Object[] configs)
{
	this.configs = configs;
}

public Config newConfig(App app)
throws IOException
{
//System.out.println("MultiConfigMaker.newConfig");
	MultiConfig ret = new MultiConfig();
	for (Object obj : configs) {
		if (obj instanceof Config) {
			ret.add((Config)obj);
		} else if (obj instanceof File) {
			File f = (File)obj;
			if (f.isDirectory()) {
				ret.add(new DirConfig((File)obj));
			} else {
				String name = f.getName();
				int dot = name.lastIndexOf('.');
				String ext = name.substring(dot+1).toLowerCase();
				Config config;
				if ("jar".equals(ext)) config = ZipConfig.loadFromLauncher(f);
				else config = ZipConfig.loadFromFile(f);
				config.setName(f.getName());
//System.out.println("MultiConfigMaker setting config name to " + config.getName());
				ret.add(config);
			}
		} else if (obj instanceof String) {
			ret.add(new ResourceConfig((String)obj));
		} else if (obj instanceof ConfigMaker) {
			ConfigMaker maker = (ConfigMaker)obj;
			ret.add(maker.newConfig(app));
		} else {
			throw new IllegalArgumentException("class " + obj.getClass());
		}
	}
	if (ret.size() == 1) return ret.get(0);
	return ret;
}

//static public Config loadConfig(Object[] configSpecs, String pwdEnvVar)
//throws IOException
//{
//	MultiConfigMaker maker = new MultiConfigMaker(configSpecs);
//	Config config = maker.newConfig(null);
//	if (config == null) return null;
//	return new PBEConfig(config, new StdinPBEAuth(pwdEnvVar));
//}

}
