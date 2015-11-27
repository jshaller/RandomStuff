package ciphers;

import java.util.ArrayList;


public class Cipher {
	private final String[] NORM_INTS={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private final String[] MISC_CHAR= {"`", " ", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", ",", "/", "\\", "\"", "'", "[", "{", "}", "]", "|", ";", ";"};	
	private final String[] LOW_LET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	private final String[] UP_LET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	public Cipher(){
		
	}

	String shift(String a, int b){
		ArrayList<Integer> c = help1(a);

		a=a.toLowerCase();
		if(b>=LOW_LET.length || b<=LOW_LET.length*-1);
		b=b%LOW_LET.length;
		for(int i=0; i<LOW_LET.length; i++){
			if(a.contains(LOW_LET[i])){
				if(i+b<0)
					a.replaceAll(LOW_LET[i], UP_LET[i+b+a.length()]);
				else if(i+b>=a.length())
					a.replaceAll(LOW_LET[i], UP_LET[i+b-a.length()]);
				else
					a.replaceAll(LOW_LET[i], UP_LET[i+b]);
			}
		}

		return a;
	}


	public ArrayList<Integer> help1(String a){
		ArrayList<Integer> locs = new ArrayList<Integer>();
		int count = 0;
		String temp=a;
		for(int i=0;i<UP_LET.length;i++)
			if(temp.indexOf(UP_LET[i])>-1){
				if(locs.size()>0){
					for(int j=locs.size()-1;j>=0;j--)
						if(locs.get(j)<=temp.indexOf(UP_LET[i])+count)
							count+=1;
				}
				//System.out.println(count);
				locs.add(temp.indexOf(UP_LET[i])+count);
				count=0;
				if(temp.indexOf(UP_LET[i])==temp.length()-1)
					temp = temp.substring(0,temp.indexOf(UP_LET[i]));
				else
					temp = temp.substring(0,temp.indexOf(UP_LET[i]))+temp.substring(temp.indexOf(UP_LET[i]));
				i--;
			}
		return locs;

	}

	public String convert(String a, ArrayList<Integer> c){
		for(int j=0; j<c.size(); j++){
			for(int i=0; i<UP_LET.length; i++){
				if(a.substring(c.get(j), c.get(j)+1).equals(LOW_LET[i])){
					if(c.get(j)==a.length()-1)
						a=a.substring(0,c.get(j))+UP_LET[i];
					else
						a=a.substring(0,c.get(j))+UP_LET[i]+a.substring(c.get(j)+1);
				}
			}
		}
		return a;
	}



}
