/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import citibob.app.App;
import java.io.IOException;

/**
 * Factory for providing a configuration to an application
 * @author citibob
 */
public interface ConfigMaker {

/**
 *
 * @param app Partially-constructed appliation.  DialogConfigMaker needs this
 * to show Swing dialog, etc.  Other ConfigMakers do not need it, app can be
 * set to null for them.
 * @return
 * @throws java.io.IOException
 */
public Config newConfig(App app) throws IOException;

}
