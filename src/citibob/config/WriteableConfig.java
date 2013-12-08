/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package citibob.config;

import java.io.IOException;

/**
 *
 * @author citibob
 */
public interface WriteableConfig extends Config
{

public void add(String name, byte[] bytes)
throws IOException;

}
