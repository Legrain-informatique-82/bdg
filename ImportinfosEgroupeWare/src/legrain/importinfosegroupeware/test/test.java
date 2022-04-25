package legrain.importinfosegroupeware.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.net.ftp.FTPFile;

import fr.legrain.importinfosegroupeware.connect.ControlConnect;
import fr.legrain.importinfosegroupeware.constant.ConstantPlugin;
import fr.legrain.importinfosegroupeware.handlers.AfficheEtTraiterTiers;
import fr.legrain.ftp.FtpUtil;
import fr.legrain.gestCom.Appli.Const;

public class test {

	public static String fileInterval(String CodeTiers){
		String a = null;
		String bordup = null;
		String borddown = null;
		Float b = Float.valueOf(CodeTiers);
		Float aa = Float.valueOf("200");
		int nombrefois = Math.round(b/aa);
		System.out.println(nombrefois);
		int bord = nombrefois*200;
		System.out.println(bord);
		if(bord<b){
			//borddown = Integer.valueOf(bord+1).toString();
			borddown = String.valueOf(bord+1);
			bordup = String.valueOf(bord+200);
			//bordup = Integer.valueOf(bord+200).toString();
			//System.out.println(borddown+"---");
		}
		if(bord>=b){
			bordup = String.valueOf(bord);
			borddown = String.valueOf(bord-199);
			//System.out.println(bordup);
			
		}
//		if(bord == b){
//			borddown = Integer.valueOf(bord-199).toString();
//			bordup = String.valueOf(bord);
//		}
		a = borddown+"-"+bordup+"/";
		return a;
	}
	public static void copy(File source,File dest){
		FileChannel in = null, out = null;

		try {
			in = new FileInputStream(source).getChannel();
			out = new FileOutputStream(dest).getChannel();
			long size = in.size();
			MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
			out.write(buf);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}

	}
	 public static boolean Move(File srcFile, String destPath)
	 {
	        // Destination directory
	        File dir = new File(destPath);
	       
	        // Move file to new directory
	        boolean success = srcFile.renameTo(new File(dir, srcFile.getName()));
	       
	        return success;
	    }

	public static void ftpcopyEtMovefile(String source,String dest){
		FtpUtil ftpUtil = new FtpUtil();
		ftpUtil.connectServer("lgrser.lgr", 21, "zhaolin", "lgr009",source);
//		String command = "mv /home/comm/tiers/prospect_test/a/albet/* /home/comm/tiers/client_test/1-200/1/";
//		try {
//			ftpUtil.getFtpClient().completePendingCommand();
//			//boolean a = ftpUtil.getFtpClient().sendSiteCommand(command);
//			ftpUtil.getFtpClient().sendSiteCommand(command);
//			System.out.println(ftpUtil.getFtpClient().getReplyCode()+" -- "+ftpUtil.getFtpClient().getReplyString()); 
//			//System.out.println(a);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			FTPFile[] ftpFiles = ftpUtil.getFtpClient().listFiles();
//			for (FTPFile file : ftpFiles) {
//				String nameFile = file.getName();
//				ftpUtil.getFtpClient().rename(nameFile, dest+nameFile);
//			}
//			
//			//ftpUtil.getFtpClient().rename("a.eml", "/home/comm/tiers/client_test/1-200/1/a.eml");
//			//System.out.println(ftpUtil.getFtpClient().getReplyCode()+" -- "+ftpUtil.getFtpClient().getReplyString()); 
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			ftpUtil.getFtpClient().removeDirectory(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//ftpUtil.removeDirectory(source);
	}
	public static void moveFileEtFolder(String source,String dest) {
		FtpUtil ftpUtil = new FtpUtil();
		ftpUtil.connectServer("lgrser.lgr", 21, 
							  "zhaolin","lgr009", source);
		
//		try {
//			FTPFile[] ftpFiles = ftpUtil.getFtpClient().listFiles();
//			for (FTPFile file : ftpFiles) {
//				ftpUtil.getFtpClient().rename(file.getName(), dest+file.getName());
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		ftpUtil.removeDirectory(source);
		System.out.println(ftpUtil.getFtpClient().getReplyCode()+" -- "+ftpUtil.getFtpClient().getReplyString()); 
		//ftpUtil.closeServer();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		String url = "http://lgrser.lgr/lgr/prospect/client/3001-3200/3070/";
		String prospect = "/prospect/";
		String client = "/client/";
		String remplace = "/prospect_test/";
		if(url.indexOf(prospect)!=-1){
			System.out.println("ok");
			String urlRemplace = url.replaceAll(prospect, remplace);
			System.out.println(urlRemplace);
		}else{
			System.out.println("no");
		}
		String chaineEspace = "sss s s5 5";
		System.out.println(chaineEspace.length());
		String chaine = chaineEspace.replaceAll("\\s", "_");
		System.out.println(chaine.length());
		if(chaine.length()>=10){
			
			System.out.println(chaine.substring(0,10));
		}else{
			System.out.println(chaine.substring(0));
		}
		//copy(new File("/home/lee/Bureau/source/motivation1.odt"),new File("/home/lee/Bureau/dist"));
		//Move(new File("/home/lee/Bureau/source/motivation1.odt"), "/home/lee/Bureau/dist");
		String s = "http://lgrser.lgr/lgr/tiers/prospect_test/a/albet/";
		//System.out.println(s.substring(28));
		
		//AfficheEtTraiterTiers afficheEtTraiterTiers = new AfficheEtTraiterTiers();
		//String source = "/home/comm/tiers/prospect_test/e/espace_piscine_46/";
		String source = "/home/zhaolin/a/b/c/";
		String dest = "/home/comm/tiers/client_test/1-200/1/";
		ftpcopyEtMovefile(source,dest);
		
		//moveFileEtFolder(source,dest);
		//System.out.println(Integer.valueOf("00002"));
		//System.out.println(fileInterval("00320"));
		//System.out.println(ConstantPlugin.PATH_DOSSIER_CLIENT+fileInterval("00800")+Integer.valueOf("00800"));
		
		
		
//		ControlConnect controlConnect = new ControlConnect();
//		Connection connection = controlConnect.makeConnect(ConstantPlugin.MYSQL_DB, ConstantPlugin.DB_EGROUPWARE);
//		try {
//			PreparedStatement preparedStatement = connection.prepareStatement(ConstantPlugin.SELECT_EGW_ADDRESSBOOK);
//			preparedStatement.setInt(1,2430);
//			ResultSet resultSet =  preparedStatement.executeQuery();
//			while (resultSet.next()) {
//				String contactValue = resultSet.getString("n_family");
//				System.out.println(contactValue);
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ControlConnect controlConnect = new ControlConnect();
//		Connection connection = controlConnect.makeConnect(ConstantPlugin.MYSQL_DB, ConstantPlugin.DB_EGROUPWARE);
//		Statement statement= null;
		
//		try {
//			statement = connection.createStatement();
//			ResultSet resultSet = statement.executeQuery("select * from egw_addressbook_extra");
//			
//			while (resultSet.next()) {
//				statementNew = connection.createStatement();
//				int idContact = resultSet.getInt("contact_id");
//				String contactValue = resultSet.getString("contact_value");
//				if(contactValue.indexOf("/prospect/")!=-1){
//					String contactValueRemplace =  contactValue.replaceAll("/prospect/", "/prospect_test/");
//					
//					statementNew.execute("update egw_addressbook_extra set contact_value = '"+contactValueRemplace
//							+"' where contact_id = "+idContact);
//					statementNew.close();
//					
//				}
//				if(contactValue.indexOf("/client/")!=-1){
//					String contactValueRemplace =  contactValue.replaceAll("/client/", "/client_test/");
//					statementNew.execute("update egw_addressbook_extra set contact_value = '"+contactValueRemplace
//							+"' where contact_id = "+idContact);
//					statementNew.close();
//					
//				}
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			try {
//				statement.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
//		Connection connectionFirebird = controlConnect.makeConnect(ConstantPlugin.FIREBIRD_DB,
//				"/home/lee/runtime-GestionCommercialeComplet.product/dossier/Bd/GEST_COM.FDB");
//		try {
//			Statement statement = connectionFirebird.createStatement();
//			ResultSet resultSet = statement.executeQuery(ConstantPlugin.sqlfirebird);
//			while (resultSet.next()) {
//				System.out.println(resultSet.getString("CODE_TIERS"));
//				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
