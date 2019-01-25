/*
 *  ReportServer
 *  Copyright (c) 2016 datenwerke Jan Albrecht
 *  http://reportserver.datenwerke.net
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.security.ext.client.crypto;

public class CryptoJsWrapper {
	
	public static native String sha1(String msg) /*-{
		return $wnd.Crypto.util.bytesToBase64($wnd.Crypto.SHA1(msg, { asBytes: true }));
	}-*/;
	
	
	public static native String hmac(String data, String passphrase) /*-{
		var bytes = $wnd.Crypto.HMAC($wnd.Crypto.SHA1, data, passphrase, { asBytes: true });
		return $wnd.Crypto.util.bytesToBase64(bytes);
	}-*/;
	
	public static native String encryptAES(String data, String passphrase) /*-{
		return $wnd.Crypto.AES.encrypt(data, passphrase);
	}-*/;
	
	public static native String decryptAES(String data, String passphrase) /*-{
		return $wnd.Crypto.AES.decrypt(data, passphrase);
	}-*/;
	
	public static native String encryptAES(String data, String passphrase, String salt, int keylength, int nrOfIterations) /*-{
		var key = $wnd.Crypto.PBKDF2(passphrase, salt, keylength / 8, { iterations: nrOfIterations, asBytes: true });
		var cipher = $wnd.Crypto.AES.encrypt(data, key);
		
		// this is base64 encoded
		return cipher;
	}-*/;
	
	public static native String pbkdf2(String passphrase, String salt, int keylength, int nrOfIterations) /*-{
		var key = $wnd.Crypto.PBKDF2(passphrase, salt, keylength / 8, { iterations: nrOfIterations, asBytes: true });
		return $wnd.Crypto.util.bytesToHex(key);
	}-*/;	
	
	public static native String decryptAES(String data, String passphrase, String salt, int keylength, int nrOfIterations) /*-{
		var key = $wnd.Crypto.PBKDF2(passphrase, salt, keylength / 8, { iterations: nrOfIterations, asBytes: true });
		return $wnd.Crypto.AES.decrypt(data, key);
	}-*/;
	

	public static native byte[] bytesToBase64(byte[] bytes) /*-{
		return $wnd.Crypto.util.bytesToBase64(bytes);
	}-*/;
	
	public static native byte[] bytesToString(byte[] bytes) /*-{
		return $wnd.Crypto.util.bytesToString(bytes);
	}-*/;
	
	public static native byte[] bytesToHex(byte[] bytes) /*-{
		return $wnd.Crypto.util.bytesToHex(bytes);
	}-*/;
}
