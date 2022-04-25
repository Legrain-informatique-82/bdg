package fr.legrain.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPListParseEngine;

public class testFTP_Lee {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * String server = "lgrser.lgr";
		 * String user = "zhaolin";
		 * String pass = "lgr009";
		 */
		FtpUtil ftpUtil = new FtpUtil();
		ftpUtil.connectServer("lgrser.lgr", 21, "zhaolin", "lgr009","");
		ftpUtil.setFileType(FTP.BINARY_FILE_TYPE);
		
//		if(ftpUtil.createDirectory("/ccc/c")){
//			System.out.println("ok");
//		}else{
//			System.out.println("no");
//		}
		
//		if(ftpUtil.changeDirectory("a")){
//			System.out.println("ok change directory");
//		}else{
//			System.out.println("no change directory");
//		}
		
		
//		ftpUtil.removeDirectory("/AAA/");//ok when Directory is empty
		
//		try {
//			ftpUtil.removeDirectory("lee",true);//ok
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ftpUtil.deleteFile("a/test.txt");//ok
//		ftpUtil.existDirectory("lee/ma");//ok
		
//		if(ftpUtil.existFile("a/b/l5.txt")){
//			System.out.println("exist");
//		}else{
//			System.out.println("No exsit");
//		}
//		try {
//			ftpUtil.uploadFile("C:/Documents and Settings/lee.LEE-94A756772D2/Bureau/l.txt","l.txt","lee");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/*
		 * downLoad from ftp server
		 */
//		try {
//			ftpUtil.download("lee/l.txt", "C:/Documents and Settings/lee.LEE-94A756772D2/Bureau/llllll.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		/*
		 * update permission (!!NO)
		 */
		//ftpUtil.updatePermissionDirectory("/a/b","777");
		//ftpUtil.updatePermissionFile("/a/b/l.txt", "777");
		
		//ftpUtil.uploadDirectory("C:/tmp", "a/b");
	
		ftpUtil.downloadDirectory("a/b", "C:/tmp",false);

		ftpUtil.getReplyCodeServer();
//		ftpUtil.uploadDirectory("C:/Documents and Settings/lee.LEE-94A756772D2/Bureau/testFTP", "lee");
//		if(ftpUtil.existDirectory("lee/testFTP")){
//			System.out.println("exist!");
//		}else{
//			System.out.println("no exist!");
//		}
		ftpUtil.closeServer();
	}

}
