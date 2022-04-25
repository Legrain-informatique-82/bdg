package fr.legrain.updater;


import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ChiffrementBlowfishPHP {
	
	static Logger logger = Logger.getLogger(ChiffrementBlowfishPHP.class.getName());

	private String algo = "Blowfish"; 
	private String mode = "CBC";
	private String padding = "NoPadding"; //comment completer les chaines de caracteres si elles sont trop courte

	private String iv = "12341234"; //vecteur d'initialisation
	private String pad = " ";
	private String cle = "1234";
	
	private Cipher c = null;
	private SecretKey skeySpec = null;
	private IvParameterSpec ivSpec = null;
	
	private BASE64Encoder base64Encoder = new BASE64Encoder();
    private BASE64Decoder base64Decoder = new BASE64Decoder();
	
	public ChiffrementBlowfishPHP() {
		try {
			byte[] raw = cle.getBytes();
			skeySpec = new SecretKeySpec(raw, algo);
			ivSpec = new IvParameterSpec(iv.getBytes());
			c = Cipher.getInstance(algo+"/"+mode+"/"+padding);
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		} catch (NoSuchPaddingException e) {
			logger.error("",e);
		} 

	}
	
	public String encrypte(String data) {
		String retour = null;
		try {
			c.init(Cipher.ENCRYPT_MODE, skeySpec,ivSpec);
			byte[] buf_crypt = c.doFinal(data.getBytes());
			retour = base64Encoder.encode(buf_crypt);
			return retour;

		} catch (InvalidKeyException e) {
			logger.error("",e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error("",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("",e);
		} catch (BadPaddingException e) {
			logger.error("",e);
		}
		return retour;
	}
	
	public String decrypte(String data) {
		String retour = null;
		try {
			c.init(Cipher.DECRYPT_MODE, skeySpec,ivSpec);
			byte[] buf_crypt = c.doFinal(base64Decoder.decodeBuffer(data));
			retour = buf_crypt.toString();
			return retour;

		} catch (InvalidKeyException e) {
			logger.error("",e);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error("",e);
		} catch (IllegalBlockSizeException e) {
			logger.error("",e);
		} catch (BadPaddingException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		}
		return retour;
	}
	
	public void test() {
		String chaine = "pwd1";
		System.out.println("Chaine ...................... : "+chaine);
		
		chaine = padString(chaine," ");
		System.out.println("Chaine padding .............. : "+chaine);
		
		String chaineCryptee = encrypte(chaine);
		System.out.println("Chaine cryptee .............. : "+chaineCryptee);
		
		String chaineDecryptee = decrypte(chaineCryptee);
		System.out.println("Chaine decrypte ............. : "+chaineDecryptee);
		
		System.out.println("Chaine decryptee sans padding : "+chaineDecryptee.trim());
	}


	/** 
	 * Le padding par defaut de java (PKCS5Padding) n'existe pas en PHP.
	 * PHP complete les chaines avec avec des caracteres NULL, pour pouvoir crypter/decrypter en java/php
	 * il faut donc faire le padding "a la main" de facon a avoir la meme chose entre les langages.
	 * 
	 * cf: http://www.php.net/mcrypt
	 * 
	 * <code>
	 * $dlen = strlen($data);
     * $pad = 16 - fmod($dlen, 16); //change here
     * if ($pad > 0) {
     *  $i = (int)$pad;
     *  while ($i > 0) {
     *      $data.=" ";
     *      $i--;
     *  }
     *}
     *</code>
	 * @param in - la chaine a completer
	 * @param pad - le caractère avec lequel on complete
	 * @return
	 */
	public String padString(String in, String pad) {
		int slen = (in.length() % 16);
		int i = (16 - slen);
		if ((i > 0) && (i < 16)){
			StringBuffer buf = new StringBuffer(in.length() + i);
			buf.insert(0, in);
			for (i = (16 - slen); i > 0; i--) {
				buf.append(" ");
			}
			return buf.toString();
		}
		else {
			return in;
		}
	}
	
//	public void crypt() {
//		try {
//			// Fichier à chiffrer
//			String in = "pwd1";
//
//			String p = PadString(in, pad);
//			System.out.println(p);
//			in=p;
//
//			System.out.println(in);
//
//			// Clé secrète choisie
//			byte[] raw = cle.getBytes();
//			SecretKey skeySpec = new SecretKeySpec(raw, algo);
//			IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
//			Cipher c = Cipher.getInstance(algo+"/"+mode+"/"+padding); 
//
//			// Chiffrement du fichier
//			c.init(Cipher.ENCRYPT_MODE, skeySpec,ivSpec);
//			byte[] buf_crypt = c.doFinal(in.getBytes());
//
//			System.out.println(buf_crypt.toString());
//			System.out.println( new sun.misc.BASE64Encoder().encode(buf_crypt));
//
//			// Déchiffrement du fichier  
//			c.init(Cipher.DECRYPT_MODE, skeySpec,ivSpec);
//			byte[] buf_decrypt = c.doFinal(buf_crypt);
//
//			System.out.println(buf_decrypt.toString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
