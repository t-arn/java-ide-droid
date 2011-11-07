package com.t_arn.lib.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.*;

//##################################################################
/** This class can be used to get the listing of a directory.
* 
* FileBrowser fb = new FileBrowser();
* fb.setDirectory ("/sdcard/test"); optional, default="/sdcard"
* fb.setSortCaseSensitive(true); // optional, default=false
* fb.setPattern(".*\.txt"); // optional, default=".*"
* String[] df = fb.getDirsAndFiles
*/
public class FileBrowser
//##################################################################
{
  // class variables
  protected ArrayList<String> alDirs, alFiles;
  /** Pattern list applies only to files
	 */
  protected Pattern regexPattern;
  protected String stStartDir;
  protected String stVersion="1.1.1";
  protected FileFilter ffDirs, ffFiles;
  protected Comparator<String> compByNameAsc;
  protected boolean bSortCaseSensitive = false;
  protected ArrayList<String> alExcludeDirs;
  
//===================================================================
  public FileBrowser ()
//===================================================================
  {
    alDirs = new ArrayList<String> ();
    alFiles = new ArrayList<String> ();
    regexPattern = null;
    stStartDir="/sdcard";
    alExcludeDirs = new ArrayList<String> ();
    ffDirs = new FileFilter()
    {
      public boolean accept(File f)
      {
        if (f==null) return false;
        if (f.isDirectory()) return true;
        return false;
      }
    };
    ffFiles = new FileFilter()
    {
      public boolean accept(File f)
      {
        if (f==null) return false;
        if (f.isDirectory()) return false;
        if (regexPattern==null) return true;
        Matcher matcher=regexPattern.matcher(f.getName());
        return matcher.find();
      }
    };
    bSortCaseSensitive = false;
    compByNameAsc = new Comparator<String> ()
    {
      public int compare (String s1, String s2)
      {
        if (bSortCaseSensitive==true)
          return s1.compareTo(s2);
        return s1.compareToIgnoreCase(s2);
      }
    };
  } // 
//===================================================================
  public String[] getDirectories ()
//===================================================================
  {
    File[] dirs;
    File d;
    String stDir;

    d = new File(stStartDir);
    if (!d.isDirectory()) return new String[0];
    dirs = d.listFiles(ffDirs);
    if (dirs==null) return new String[0];
    alDirs.clear();
    for (int i=0; i<dirs.length; i++)
    {
      stDir = dirs[i].getName();
      if (!stDir.endsWith("/")) stDir+="/";
      if (!alExcludeDirs.contains(stStartDir+stDir)) alDirs.add(stDir);
    }//for
    Collections.sort(alDirs,compByNameAsc);
    if (!stStartDir.equals("/")) alDirs.add(0,"..");
    return alDirs.toArray(new String[alDirs.size()]);
  } // getDirectories
//===================================================================
  public String[] getDirectoryTree ()
//===================================================================
  {
    ArrayList<String> dirs = new ArrayList<String> ();
    String startdir=this.stStartDir;
    File d = new File(startdir);
    if (!d.isDirectory()) return new String[0];
    walk (dirs, startdir);
    this.stStartDir=startdir;
    return dirs.toArray(new String[dirs.size()]);
  } // getDirectoryTree
//===================================================================
  public String[] getDirsAndFiles ()
//===================================================================
  {
    String[] d, f, df;
    d = getDirectories();
    f = getFiles();
    df = new String[d.length+f.length];
    System.arraycopy (d, 0, df, 0, d.length);
    System.arraycopy (f, 0, df, d.length, f.length);
    return df;
  } // getDirsAndFiles
//===================================================================
  public String[] getFiles ()
//===================================================================
  {
    File[] files;
    File f;

    f = new File(stStartDir);
    if (!f.isDirectory()) return new String[0];
    files = f.listFiles(ffFiles);
    if (files==null) return new String[0];
    alFiles.clear();
    for (int i=0; i<files.length; i++)
    {
      alFiles.add(files[i].getName());
    }//for
    Collections.sort(alFiles,compByNameAsc);
    return alFiles.toArray(new String[alFiles.size()]);
  } // getFiles
//===================================================================
  public String getVersion ()
//===================================================================
  {
    return this.stVersion;
  } // getVersion
//===================================================================
  public void setDirectory (String dir)
//===================================================================
  {
    stStartDir=dir;
    if (!stStartDir.endsWith("/")) stStartDir+="/";
  } // setDirectory
//===================================================================
/** Defines which directory trees to exclude from the directory list
 * Every tree to exclude must be contained with its full path, ending
 * with a '/'
 */
 public void setExcludeDirs (ArrayList<String> excludelist)
//===================================================================
  {
    if (excludelist!=null) this.alExcludeDirs = excludelist;
    else this.alExcludeDirs = new ArrayList<String> ();
  } // setExcludeDirs
//===================================================================
/** Sets a regex pattern which will be used in getFiles
 *  but not in getDirectories().
 * 
 * @param pattern The regex pattern string.
 *                null will behave as ".*" but faster
 */
 public void setPattern (String pattern)
//===================================================================
  {
    if (pattern == null) regexPattern = null;
    else regexPattern = Pattern.compile(pattern);
  } // setPattern
//===================================================================
/** Defines whether the files and dirs are sorted case sensitive
 *  or not. Default is sorting case insensitive.
 * @param sensitive set to true for case sensitive sorting
 */  public void setSortCaseSensitive (boolean sensitive)
//===================================================================
  {
    bSortCaseSensitive = sensitive;
  } // 
//===================================================================
  protected void walk (ArrayList<String> dirs, String subdir)
//===================================================================
  {
    int i;
    String[] arDirs;

    dirs.add(subdir);
    setDirectory(subdir);
    arDirs=getDirectories();
    for (i=1;i<arDirs.length;i++) // jump over ".." entry !
    {
      walk(dirs, subdir+arDirs[i]);
    }
  } // walk
//===================================================================
}
//##################################################################
