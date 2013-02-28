package mypackage;

import net.rim.device.api.crypto.BlockEncryptor;
import net.rim.device.api.crypto.DESEncryptorEngine;
import net.rim.device.api.crypto.DESKey;
import net.rim.device.api.crypto.HMAC;
import net.rim.device.api.crypto.HMACKey;
import net.rim.device.api.crypto.PKCS5FormatterEngine;
import net.rim.device.api.crypto.SHA256Digest;
import net.rim.device.api.io.NoCopyByteArrayOutputStream;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class MyScreen extends MainScreen
{
    /**
     * Creates a new MyScreen object
     */
	ButtonField but=new ButtonField("click");
    public MyScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("MyTitle");
        
        add(but);
        
    }
    
    
    protected boolean navigationClick(int status, int time) {
    	try {
        	String des_enc = encryptDES("hdfcbank","hdfcbank123");
           Dialog.alert(""+des_enc);
			String hmac = encryptHMACSHA256(des_enc,"79341585");
			Dialog.alert(""+hmac);
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return super.navigationClick(status, time);
    }
    
    public static String encryptDES(String str_key,String pass_str){
    	String des_enc_str = "";
    	try{
    		 // Create a new DES key based on the 8 bytes in the secretKey array
            DESKey key = new DESKey(str_key.getBytes("UTF-8"));
            NoCopyByteArrayOutputStream out = new NoCopyByteArrayOutputStream();         
            BlockEncryptor cryptoStream = new BlockEncryptor( 
                    new PKCS5FormatterEngine(new DESEncryptorEngine( key )),out);
            cryptoStream.write( pass_str.getBytes("UTF-8"), 0, pass_str.length());
            cryptoStream.close();
            des_enc_str = toHex(new String(out.getByteArray()).trim().getBytes());
            des_enc_str = des_enc_str.toUpperCase();
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	System.out.println("DES.."+des_enc_str);
    	return des_enc_str;
    }
    public static String encryptHMACSHA256(String des_str,String randomkey) throws Exception {
    	   String hmac  = "";
    	   HMAC sha256 = new HMAC(new HMACKey(randomkey.getBytes("UTF-8")),
    	    new SHA256Digest());
    	  sha256.update(des_str.getBytes("UTF-8"));
    	  byte[] signed = new byte[sha256.getLength()];
    	  sha256.getMAC(signed, 0);
    	  hmac = toHex(signed);
    	  System.out.println("HMAC-SHA256 "+hmac);  	
    	  return hmac;
    	}
    static final char[] DIGITS = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHex(byte[] bytes) {
      char[] out = new char[bytes.length * 2]; // 2  hex characters per byte
      for (int i = 0; i < bytes.length; i++) {
        out[2*i] = DIGITS[bytes[i] < 0 ? 8 + (bytes[i] + 128) / 16 : bytes[i] / 16]; // append sign bit for negative bytes
        out[2*i + 1] = DIGITS[bytes[i] < 0 ? (bytes[i] + 128) % 16 : bytes[i] % 16];
      }
      return new String(out); // char sequence to string
    }
}
