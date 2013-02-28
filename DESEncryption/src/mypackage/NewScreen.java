package mypackage;

import net.rim.device.api.browser.field2.BrowserField;
import net.rim.device.api.browser.field2.BrowserFieldConfig;
import net.rim.device.api.crypto.HMAC;
import net.rim.device.api.crypto.HMACKey;
import net.rim.device.api.crypto.SHA256Digest;
import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

import org.w3c.dom.Node;
import org.w3c.dom.html2.HTMLElement;
import org.w3c.dom.html2.HTMLScriptElement;

public class NewScreen extends MainScreen implements FieldChangeListener {
	VerticalFieldManager vertical;
	HorizontalFieldManager hor;
	ButtonField click;
	String str, url;
	HTMLScriptElement script;
	HTMLElement element;
	Node node;
	BrowserField browserField;

	EditField edtKey, edtMsg;

	public NewScreen() {
		// str="<p align=center>ali shaik</p>";
		vertical = new VerticalFieldManager();
		url = "local:///test_ofs.html";
		click = new ButtonField("click");
		click.setChangeListener(this);
		vertical.add(click);
		// BrowserFieldConfig config=new BrowserFieldConfig();
		// config.setProperty(BrowserFieldConfig.JAVASCRIPT_ENABLED,
		// Boolean.TRUE);
		browserField = new BrowserField();
		browserField.requestContent(url);
//		 vertical.add(browserField);

		edtKey = new EditField("Des Key      ", "hdfcbank");
		edtMsg = new EditField("Plain Text   ", "hdfcbank123");
		add(edtKey);
		add(edtMsg);
		add(vertical);
	}

	public void fieldChanged(Field field, int context) {
		if (click == field) {

//			String k = edtKey.getText();
			String m = edtMsg.getText();

//			String str = (String) browserField.executeScript("myFunction('" + k
//					+ "','" + m + "')");
			
			
			
			browserField.executeScript("enc()");

//			Dialog.alert("Encrypted Hex String  :  "+str);
//			
//			
//			String key="79341585";
//			
//			String str1 = (String) browserField.executeScript("hex_hmac('" + key
//					+ "','" + str + "')");
			
			
			
//			try {
//				Dialog.alert(""+sign(str.toUpperCase(), key));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		}
	}
	
	
	
	
	private String sign(String content, String secretAccessKey)
			throws Exception {
		HMAC sha256 = new HMAC(new HMACKey(secretAccessKey.getBytes()),
				new SHA256Digest());
		sha256.update(content.getBytes());
		byte[] signed = new byte[sha256.getLength()];
		sha256.getMAC(signed, 0);

//		String signedbase64 = Base64OutputStream.encodeAsString(signed, 0,
//				signed.length, false, false);

		
		String signedbase64 =byteArrayToHexString(signed);
		
		
		return signedbase64;
	}
	
	
	
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}
	
}