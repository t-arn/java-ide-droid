package com.t_arn.lib.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
 
//##################################################################
/** Redirect an OutputStream to a StringWriter
 *
 * StringWriterOutputStream swos = new StringWriterOutputStream();
 * System.setOut(new PrintStream(swos));
 * System.out.println("welcome");
 * 
 * @since 01.06.2011
 * @author Tom Arn
 * @author www.t-arn.com
 * @version 1.0.0
 */
public class StringWriterOutputStream 
  extends OutputStream 
//##################################################################
{
  private StringWriter stringWriter;
  public static final String stVersion = "1.0.0";
 
//===================================================================
public StringWriterOutputStream() 
//===================================================================
{
  this.stringWriter = new StringWriter();
}
//===================================================================
/** Returns the version of the class.
 * 
 * @return returns the version of the class
 */
public static String getVersion ()
//===================================================================
{
	return stVersion;
}
//===================================================================
/** Get the data as String
 * 
 * @return data
 */
@Override
public String toString() 
//===================================================================
{
  return stringWriter.toString();
}
//===================================================================
/** Get the data as StringBuffer
 * 
 * @return data
 */
public StringBuffer toStringBuffer() 
//===================================================================
{
  return stringWriter.getBuffer();
}
//===================================================================
@Override
public void write(int b) throws IOException 
{
  this.stringWriter.write(b);
}
//===================================================================
}
//##################################################################
