package fr.legrain.ftp;

public class testSubstring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String path = "lee/l.txt";
		
		System.out.println(path);
		int indexChar = path.lastIndexOf("/");
    	System.out.println(indexChar);
    	String part1Path = path.substring(0,indexChar);
    	System.out.println(part1Path);
    	
	}

}
