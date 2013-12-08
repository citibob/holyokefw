/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.util.Iterator;

/**
 *
 * @author citibob
 */
public interface ListableConfig extends Config
{

public Iterator<String> listStreams();
	
}
