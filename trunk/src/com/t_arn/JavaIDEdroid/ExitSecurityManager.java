package com.t_arn.JavaIDEdroid;

//##################################################################
class ExitSecurityManager extends SecurityManager 
//##################################################################
{
	// Many thanks to www.hasiland.com for this class
	
	/** Checks to see if the system is exiting the virtual
	 machine with an exit code.  Only allows exit if
	 object property canExit is true.
	 <p>
	 @param status exit status, 0 if successful, other values
	 indicate various error types.
	 @throws ExitSecurityManager.Exception If a system
	 exit was attempted while canExit is false.
	 */
	 
	private boolean canExit;
	public int ExitValue = 255; // = on exit value

//===================================================================
public void checkExit(int status)
//===================================================================
{
	this.ExitValue = status;
	if( !isCanExit() ) 
	{
		// we convert status to a string as pass it in:
	  throw new ExitSecurityManager.Exception("" + status);
	} 
}
//===================================================================
public boolean isCanExit()
//===================================================================
{ 
	return this.canExit; 
}
//===================================================================
public void setCanExit(boolean canExit)  
//===================================================================
{ 
	this.canExit = canExit; 
} 
//===================================================================
public void checkPackageAccess(String pkg) 
{
}

public void checkAccept(String  host, int  port) 
{
}

public void checkAccess(Thread  g) 
{
}

public void checkAccess(ThreadGroup  g) 
{
}

public void checkConnect(String  host, int  port) 
{
}

public void checkConnect(String  host, int  port, Object  context) 
{
}

public void checkCreateClassLoader() 
{
}

public void checkDelete(String  file) 
{
}

public void checkExec(String  cmd) 
{
}

public void checkLink(String  lib) 
{
}

public void checkListen(int  port) 
{
}

public void checkPackageDefinition(String  pkg) 
{
}

public void checkPropertiesAccess() 
{
}

public void checkPropertyAccess(String  key) 
{
}

public void checkRead(java.io.FileDescriptor  fd) 
{
}

public void checkRead(String  file) 
{
}

public void checkRead(String  file, Object  context) 
{
}

public void checkSetFactory() 
{
}

public void checkWrite(java.io.FileDescriptor  fd) 
{
}

public void checkWrite(String  file) 
{
}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
/** Exception that is thrown when a system exit is
 attempted while ExitSecurityManager.canExit is
 false.
 */
public class Exception extends SecurityException 
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
{
	static final long serialVersionUID = 1L;
	public Exception()  { super(); }
	public Exception(String msg)  { super(msg); }
}
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
} // ExitSecurityManager
//##################################################################
