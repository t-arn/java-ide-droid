package com.t_arn.JavaIDEdroid;

import com.t_arn.lib.io.taGetOpt;

import java.io.File;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import kellinwood.security.zipsigner.ZipSigner;


//##################################################################
/** This class uses Ken Ellinwoods zipsigner-lib to sign the APK file
 * For reference see http://code.google.com/p/zip-signer/
 */
public class SignApk
//##################################################################
{
  // class variables
  int ExitValue;
  taGetOpt getopt;
  String mode = null;
  String infilename = null;
  String outfilename = null;
  String provider = null;
  String keyfilename = null;
  String keypass = null;
  String certfilename = null;
  String templatefilename = null;

//===================================================================
public SignApk (String[] args) 
//===================================================================
{
  ExitValue = 255;
  getopt = new taGetOpt(args, "MIO", "hPKWCT");
} // constructor
//===================================================================
private boolean fnCheckParams()
//===================================================================
{
  boolean ok;
  int count;

  ok = getopt.paramsOK();
  if (getopt.getOption('h')!=null) ok = false;
  fnReadParams();
  count=0;
  if (keyfilename!=null) count++;
  if (keypass!=null) count++;
  if (certfilename!=null) count++;
  if (templatefilename!=null) count++;
  if ((count!=0)&&count!=4) ok = false;
  if (!ok)
  {
    System.out.println("\nUse: iExitValue = SignApk.main(arguments)");
    System.out.println("\nArguments can be:");
    System.out.println("-h help               show this help");
    System.out.println("-M keymode            auto, auto-testkey, auto-none, media, platform, shared, testkey, none");
    System.out.println("-I unsignedAPK        filename of the unsigned APK file");
    System.out.println("-O signedAPK          filename of the signed APK file");
    System.out.println("-P provider           Alternate security provider class - e.g., 'org.bouncycastle.jce.provider.BouncyCastleProvider'");
    System.out.println("-K key                filename of the PCKS#8 encoded private key file");
    System.out.println("-W keypass            Private key password");
    System.out.println("-C cert               filename of the X.509 public key certificate file");
    System.out.println("-T template           filename of the Signature block template file");
    System.out.println("\nRequired arguments are -M, -I, -O");
    System.out.println("Options -K, -W, -C and -T must always be used together. If used, the provided -M option is ignored.");
    System.out.println("\nReturns following exit values:");
    System.out.println("0:   OK");
    System.out.println("1:   invalid or missing parameters");
    System.out.println("2:   signing error");
    System.out.println("255: undefined");
  } // if
  return ok;
} // fnCheckParams
//===================================================================
private void fnReadParams ()
//===================================================================
{
  mode = getopt.getOption('M');
  infilename = getopt.getOption('I');
  outfilename = getopt.getOption('O');
  provider = getopt.getOption('P');
  keyfilename = getopt.getOption('K');
  keypass = getopt.getOption('W');
  certfilename = getopt.getOption('C');
  templatefilename = getopt.getOption('T');
} //fnReadParams
//===================================================================
private int fnSignApk()
//===================================================================
{
  int rc = 255;
  ZipSigner signer;
  PrivateKey privateKey = null;
  URL privateKeyUrl, certUrl, sbtUrl;
  X509Certificate cert = null;
  byte[] sigBlockTemplate = null;

  try
  {
    signer = new ZipSigner();
    if (provider!=null) signer.loadProvider(provider);
    if (keyfilename!=null)
    {
      certUrl = new File( certfilename).toURI().toURL();
      cert = signer.readPublicKey(certUrl);
      sbtUrl = new File(templatefilename).toURI().toURL();
      sigBlockTemplate = signer.readContentAsBytes(sbtUrl);
      privateKeyUrl = new File(keyfilename).toURI().toURL();
      privateKey = signer.readPrivateKey(privateKeyUrl, keypass);
      signer.setKeys("custom", cert, privateKey, sigBlockTemplate);
    }
    else
    {
      signer.setKeymode(mode);
    }
    signer.signZip(infilename, outfilename);
    rc = 0;
  }
  catch (Throwable t)
  {
    rc = 2;
    t.printStackTrace();
  }
  return rc;
} // fnSignApk
//===================================================================
public static int main (String[] args)
//===================================================================
{
   int rc = 255;
   boolean ok;

   SignApk main = new SignApk(args);
   ok = main.fnCheckParams();
   if (!ok) return 1;
   rc = main.fnSignApk();
   return rc;
} // main
//===================================================================
} // SignApk
//##################################################################
