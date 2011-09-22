/*
Copyright (C) 2004-2011 t-arn.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 
*/

package com.t_arn.lib.io;
import java.util.Vector;

//##################################################################
/** taGetOpt is used to parse the commandline parameters passed to an application.
 * 
 * The class assumes following conventions:
 * <UL>
 * <LI>A switch is defined with a '-' and one alphanumeric character.
 * </LI>
 * <LI>If the character is capital, then the switch requires a following switch argument.
 * </LI>
 * <LI>If an unknown switch follows a capital switch, then the switch is treated as an argument.
 * </LI>
 * <LI>An unknown switch cannot have a switch argument, even if it is a capital switch.</LI>
 * </UL>
 * 
 * <P>
 * <B>Usage example:
 * </B><BR>
 * taGetOpt getopt = new taGetOpt(args, "ab", "cDeF");
 * <BR>
 * String[] arg=getopt.getArguments(); // e.g. { "-c", "-a", "Test", "-F", "c:\\test.fil", "blabla", "-D", "-1", "-b" } <BR>
 * System.out().println("OK ="+getopt.paramsOK());
 * <BR>
 * System.out().println("iMandatory="+getopt.iMandatory);
 * <BR>
 * System.out().println("iOptional="+getopt.iOptional); <BR>
 * System.out().println("iUnknown="+getopt.iUnknown); <BR>
 * System.out().println("-a="+getopt.getOption('a')); <BR>
 * System.out().println("-c="+getopt.getOption('c')); <BR>
 * System.out().println("-e="+getopt.getOption('e')); <BR>
 * System.out().println("-D="+getopt.getOption('D')); <BR>
 * System.out().println("-F="+getopt.getOption('F')); <BR>
 * System.out().println("Arguments: "+arg.length); <BR>
 * for (int i=0;i&lt;arg.length;i++) Vm.out().println(arg[i]);
 * <BR>
 * </P>
 * 
 * @since 21.05.04 (version 1.0) - 14.09.11
 * @author Tom Arn
 * @version 2.0.5
 */
public class taGetOpt
//##################################################################
{
	/** Holds the version of the class.
	 */
	protected static String stVersion="2.0.5";
	/** Contains the switches and switch arguments
	 */
	protected String[] aOptions;
	/** Contains the commandline paramenters
	 */
	protected String[] aArgs;
	/** Contains the standalone parameters
	 */
	protected Vector<String> vArgs;
	/** Specifies the mandatory switches
	 */
	protected String stMandatory;
	/** Specifies the optional switches
	 */
	protected String stOptional;
	/** Contains all switches
	 */
	protected String stOptions;
	/** Counter for the found mandatory switches
	 */
	public int iMandatory=0;
	/** Counter for the found optional switches
	 */
	public int iOptional=0;
	/** Counter for the found unknown switches
	 */
	public int iUnknown=0;
	/** Counter. Must be 0 - then no required arguments are missing.
	 */
	public int iReqArgMissing=0;

//===================================================================
/** Creates a new taGetOpt object.
 * 
 * @param args commandline parameters
 * @param mandatory mandatory switches
 * @param optional optional switches
 */
public taGetOpt (String[] args,
	String mandatory,
	String optional)
//===================================================================
{
	int i;
	
	vArgs = new Vector<String>();
	aArgs = args;
	stMandatory = (mandatory==null) ? "" : mandatory;
	stOptional = (optional==null) ? "": optional;
	stOptions = mandatory+optional;
	aOptions = new String[stOptions.length()];
	for ( i=0 ; i<aOptions.length ; i++ )
	{
		aOptions[i] = null;
	}
	fnParseOptions();
} // taGetOpt 
//===================================================================
/** Returns the standalone arguments
 * 
 * @return returns the standalone arguments
 */
public String[] getArguments ()
//===================================================================
{
	String[] aTmp 	= new String[vArgs.size()];

	for (int i=0;i<aTmp.length;i++) aTmp[i] = vArgs.get(i);
	return aTmp;
} // getArguments 
//===================================================================
/** Returns an option.
 * 
 * @param switchChar the switch in question
 * @return returns null (if switch is not available), an empty String (if 
 *     switch has no arguments) or the switch argument
 */
public String getOption (char switchChar)
//===================================================================
{
	int idx=0;
	
	if (aOptions==null) return "";
	idx = stOptions.indexOf(switchChar);
	if (idx == -1) return null;
	return aOptions[idx];
} // getOption 
//===================================================================
/** Returns the version of the class.
 * 
 * @return returns the verstion of the class
 */
public static String getVersion ()
//===================================================================
{
	return stVersion;
} // getVersion 
//===================================================================
/** Checks if the parameters were passed correctly.
 * 
 * @return returns true if all mandatory switches were found AND no unknown 
 *     switches were found AND all capital switches have an argument.
 */
public boolean paramsOK ()
//===================================================================
{
	boolean ok = true;
	
	if (iUnknown > 0) ok = false;
	if (iMandatory != stMandatory.length()) ok = false;
	if (iReqArgMissing != 0) ok = false;
	return ok;
} // paramsOK 
//===================================================================
/** Parses the commandline parameters.
 */
protected void fnParseOptions ()
//===================================================================
{
	int i, idx, lastIdx = -1;
	char switchChar;
	boolean bNeedsArg = false;
			
	for ( i=0 ; i<aArgs.length ; i++ )
	{
		if ( (aArgs[i].charAt(0) == '-') && (aArgs[i].length()==2) )  // switch
		{
			switchChar = aArgs[i].charAt(1);
			idx = stMandatory.indexOf(switchChar);
			if ( idx != -1 )  // mandatory switch
			{
				iMandatory++;
				lastIdx = stOptions.indexOf(switchChar);
				aOptions[lastIdx] = "";
				bNeedsArg = requiresArg(switchChar);
				if (bNeedsArg) iReqArgMissing++;
			}
			else
			{
				idx = stOptional.indexOf(switchChar);
				if (idx != -1)  // optional switch
				{
					iOptional++;
					lastIdx = stOptions.indexOf(switchChar);
					aOptions[lastIdx] = "";
					bNeedsArg = requiresArg(switchChar);
					if (bNeedsArg) iReqArgMissing++;
				}
				else // unknown switch
				{
					if (bNeedsArg)  // unknown switch is treated as argument of preceding capital switch
					{
						aOptions[lastIdx] = aArgs[i];
						lastIdx = -1;
						bNeedsArg = false;
						iReqArgMissing--;
					}
					else
					{
						lastIdx = -1;
						iUnknown++;
						bNeedsArg = false;
					}
				}				
			}
		}
		else // argument
		{
			if (bNeedsArg)  // argument belongs to preceding switch
			{
				aOptions[lastIdx] = aArgs[i];
				lastIdx = -1;
				bNeedsArg = false;
				iReqArgMissing--;
			}
			else
			{
				vArgs.add(aArgs[i]);
			}
		}
	}
} // fnParseOptions 
//===================================================================
/** Checks if a switch requires a switch argument or not.
 * 
 * @param switchChar the switch in question
 * @return returns true for capital switches, false otherwise
 */
protected boolean requiresArg (char switchChar)
//===================================================================
{
	if ( (switchChar >= 'A') && (switchChar <= 'Z') ) return true;
	else return false;
} // requiresArg 
//===================================================================
}
//##################################################################

