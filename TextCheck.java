import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class TextCheck {
	//WARNING THERE IS A CONSIDERABLE AMOUNT OF OVERLOADING DONE
	private final String[] NORM_INTS={"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private final String[] MISC_CHAR= {"`", " ", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", ",", "/", "\\", "\"", "'", "[", "{", "}", "]", "|", ";", ";"};	
	private final String[] LOW_LET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	private final String[] UP_LET = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private final String[] MONTH = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private ArrayList<String[]> arrays = new ArrayList<String[]>(Arrays.asList(NORM_INTS, MISC_CHAR, LOW_LET, UP_LET, MONTH));

	private int pMin;
	private int pMax;
	/*public static void main(String[] args){
		System.out.println(check("1100","wingindg",1,20));
		System.out.println(phoneCheck("(916) 817-1234"));
		System.out.println(dateCheck("1995/12/42"));
		System.out.println(emailCheck("w@w.com"));                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	}*/

	public TextCheck(int n1, int n2){
		pMin=n1;
		pMax=n2;
	}
	public void addArray(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the address of where file is located e.g. C:Users\\Username\\Documents\\magic.text");
		System.out.println("Alternatively add it to the project and make my life easy");
		String datapath = scan.nextLine();
		scan.close();
		ArrayList<String> add = new ArrayList<String>();
		try {
			Scanner scanFile = new Scanner(new File(datapath));
			while(scanFile.hasNext()){
				add.add((scanFile.nextLine()));
			}
			String[] asdf = new String[add.size()];
			for(int i=0;i<add.size(); i++){
				asdf[i]=add.get(i);
			}
			
			scanFile.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("File was not found.");
			e.printStackTrace();
		}
	}

	//check the single character against the entire array
	boolean letCheck(String types, String b){
		for(int i=0; i<arrays.size()&&i<types.length(); i++)
			if(types.substring(i,i+1).equals("1"))
				for(int j=0; j< arrays.get(i).length; j++){
					String c =arrays.get(i)[j];
					if(b.equals(c))
						return true;
				}
		return false;
	}
	//checks that the given string only has the specific types
	boolean check(String types, String b, int c, int d){
		//length is first priority
		if(b.length()<c||b.length()>d)
			return false;

		for(int i=0; i<b.length(); i++){
			if(!letCheck(types, b.substring(i, i+1)))
				return false;
		}
		return true;
	}


	/****
	 * 
	 * @param types		
	 * @param b 			string submitted
	 * @param c			lowersize
	 * @param d			largersize
	 * @param flag			0 default, otherwise add in things to check.
	 * 								c -- contains the type of character, number appended on means how many
	 * 								e -- contains the specific characters at the specific indexes
	 * @param extras		used to store whatever arrays needed
	 * @return
	 */
	boolean check(String types, String b, int c, int d, String flag, Object[][] extras){
		//length is first priority
		if(b.length()<c||b.length()>d)
			return false;

		//flags next priority
		if(flag.substring(0,1).equals("c")){
			int counter = 1;
			if(flag.length()>1)
				counter = Integer.parseInt(flag.substring(1));
			if(counter==0)return true;
			for(int i=0;i<arrays.size()&&i<types.length(); i++){
				if(types.substring(i,i+1).equals("1"))
					for(int j=0; j<arrays.get(i).length; j++){
						if(b.contains(arrays.get(i)[j])){
							counter--;
						}
						if(counter==0)
							return true;
					}
				
			}
			return false;

		}
		if(flag.substring(0,1).equals("e")){
			for(int i=0;i<extras[0].length;i++){
				int i1 = (int)(extras[0])[i];
				String s = (String)(extras[1][i]);
				if(!b.substring(i1,i1+1).equals(s))
					return false;
			}
			return true;

		}

		return false;
	}
	
	
	/**** u no it's serious when this formatting comes out
	 * 
	 * @param types		same as the others
	 * @param text 			string submitted
	 * @param st		start char
	 * @param ed		end char
	 * @param flag		0 default, otherwise add in things to check.
	 * 							c__ -- contains the type of character, number appended on means how many
	 * 							e__ -- contains the specific characters at the specific indexes
	 * 							s__ -- mod that checks certain sequence of characters (beefed up e)
	 * 							__h -- text is 0'd out return true
	 * 
	 * @param c			lowersize
	 * @param d			largersize
	 * @param extras		used to store whatever arrays needed
	 * @return
	 */
	boolean seqCheck(String[] types, String text, String st, String ed, String flag, int c, int d, Object[][] extras){
		//types of sequences:
		//1 -> ... + (char) + stream + ...
		//2 -> ... + stream + (char) + ...
		//3 -> ... + (char) + stream + (char) + ...
		//the dots are insignificant
		//the sequence is effectively "binded" down to min number of types + chars
		//for the above, stream works in a certain type order, given by the array of types.
		//extras has a very specific format:
		//int array for determining if there is a specific char or if there is a sequence
		//-1 in a slot means it can go as long as it wants, -2 means it must match the char in the string array
		//string array that matches with int array

		//first check to see if original char is inside string, located at
		if(st==null&&ed==null)
			return false;
		if(st!=null){
			if (!text.contains(st))
				return false;
		}
		if(ed!=null){
			if(!text.contains(ed))
				return false;
		}
		if(flag==null){
			flag="";
		}
		//substring text from either st or ed
		if(st!=null){
			text=text.substring(text.indexOf(st));
			if(text.length()==0||(flag.length()>2&&!flag.substring(2,3).equals("h")))
				return false;
			else if(text.length()==0&&(flag.length()>2&&flag.substring(2,3).equals("h")))
				return true;
		}
		if(ed!=null){
			text=text.substring(0,text.indexOf(ed));
			if(text.length()==0||(flag.length()>2&&!flag.substring(2,3).equals("h")))
				return false;
			else if(text.length()==0&&(flag.length()>2&&flag.substring(2,3).equals("h")))
				return true;
		}
		Integer a1[];
		String a2[];
		try{
			a1 = (Integer[])(extras[0]);
			a2 = (String[])(extras[1]);
		}
		catch(Exception e){
			System.out.println("formatting error");
			return false;
		}
		int counter=0;  //counts for the necessary parts
		int arplace=0;	//array place holder

		//this loop is ugly, considering fixes later
		//loop through what is left of text
		for(int i=0;i<text.length();i++){
			if(a1[arplace]==-2){
				if(!text.substring(i,i+1).equals(a2[arplace]))
					return false;
				arplace++;
			}

			else if(a1[arplace]==-1){
				int num = arplace;
				while(i<text.length()||num==arplace){
					if(counter==0){
						if(!letCheck(a2[arplace],text.substring(i, i+1)))
							return false;
						counter++;
						i++;
					}
					else{
						boolean cont=true;
						//break out of loop completely
						if(arplace+1<a1.length){
							if((a1[arplace+1]).equals(text.substring(i,i+1))){
								cont=false;
								arplace++;
							}
							else if(letCheck(a2[arplace+1],text.substring(i,i+1))){
								cont=false;
								arplace++;
							}			
						}
						if(cont){
							if(!letCheck(a2[arplace],text.substring(i, i+1)))
								return false;
							i++;
						}
					}//end of else
				}//end of while loop
				//in the case that counter was incremented, it is set to 0
				counter=0;
			}
			//no 0s allowed omigo
			//check to see if current letter matches with the allowed types
			else if(a1[arplace]>0){
				if(!letCheck(a2[arplace],text.substring(i,i+1)))
					return false;
				
				counter++;				
			}
			//end check
			if(counter==a1[arplace]){
				arplace++;
				counter=0;
				if(arplace>a1.length)
					return true;
			}

		}
		return true;
	}
	

	//checks for multiple things
	boolean multCheck(String[] types, Integer[] indexes, String b, int c, int d){
		//int i=0;
		for(int j=0; j<indexes.length; j++){
			if(j==indexes.length-1){
				if(!check(types[j], b.substring(indexes[j]), 0, b.length()))
					return false;
			}
			else if(!check(types[j], b.substring(indexes[j],indexes[j+1]), 0, b.length()))
				return false;
			//i+=indexes[j];

		}

		return true;
	}

	//now make some specific ones, such as checking for names and stuff to make the code more readable
	boolean phoneCheck(String a){
		String[] t1 = {"(",")"," ","-"};
		String[] t2 = {"(",")","-"};
		Integer[] t3 = {0,4,5,9};
		Integer[] t4 = {0,4,8};
		// * --- * ** --- * ----
		String[] numSeq = {"111","100","111","100","111", "100"};
		//ArrayList<Integer> inds = new ArrayList<Integer>();
		//inds.add(0);inds.add(1);inds.add(4);inds.add(6);inds.add(9);inds.add(14);
		Integer[] inds = {0,1,4,6,9,10};
		Object[][] o1 = {(Object[])(t3),(Object[])(t1)};
		Object[][] o2 = {(Object[])(t4),(Object[])(t2)};

		boolean var13=check("100", a, 14, 14, "e", o1)&&multCheck(numSeq, inds, a, 14, 14);
		//if there is no space
		for(int i=3;i<inds.length;i++)
			inds[i]=inds[i]-1;

		boolean var24 = check("100", a, 13, 13, "e", o2)&&multCheck(numSeq, inds, a, 13, 13);
		//else check if
		return check("100", a, 10, 10)||var13||var24;
	}

	/****
	 * 
	 * @param pass		password 
	 * @param types		what types are going to be checked, 1000 means there must be at least an int inside
	 * @param diffs		these are the difficult parameters, pass in an int array that matches the above string
	 * @param min		min pass length
	 * @param max		max pass length
	 * @return
	 */
	boolean passCheck(String pass, String types, Integer[] diffs, int min, int max){
		//two different letters, two different numbers, two different spec chars
		for(int i=0;i<types.length(); i++)
			if(types.substring(i,i+1).equals("1")){
				String s="";
				for(int j=0;j<i;j++)
					s+="0";
				s+="1";
				String complexity= "c"+ diffs[i];
				if(!check(s, pass, min, max, complexity, null))
					return false;
			}

		return true;
	}
	//just checks for 1 of each type and fits requirements
	boolean passCheck(String txt, int min, int max){
		return check("100", txt, min, max, "c", null)&&check("010",txt,min,32,"c", null)&&check("001",txt,min,32,"c", null)&&check("0001",txt, min, max, "c", null);
	}

	//just checks for year/month/day in that format
	boolean dateCheck(String date){
		//1996 / 20 / 12
		//0--- 4 5- 7 8-
		Integer[] locs = {0,4,5,7,8};
		String[] numSeq = {"100","010","100","010","100"};

		return multCheck(numSeq, locs, date, 10, 10);

	}
	boolean ssnCheck(String ssn){
		//333 / 33 / 3333
		//0-- 3 4- 6 7-
		Integer[] locs = {0,3,4,6,7};
		String[] numSeq = {"100","010","100","010","100"};

		return multCheck(numSeq, locs, ssn, 10, 10);

	}


	boolean emailCheck(String txt){
		if(txt.length()<7||!txt.contains("@"))
			return false;
		if(!(txt.substring(txt.length()-4).equals(".com")||txt.substring(txt.length()-4).equals(".net")))
			return false;
		String a=txt.substring(0,txt.indexOf("@"));
		boolean b = check("010",a,1,txt.indexOf("@"));
		if(!check("1011",txt.substring(0,txt.indexOf("@")),1,txt.indexOf("@")))
			return false;
		String c = txt.substring(txt.indexOf("@")+1,txt.length()-4);
		if(!check("1011",txt.substring(txt.indexOf("@")+1,txt.length()-4),1,txt.length()-6))
			return false;

		return true;
	}

	boolean addressCheck(String[] txt){
		
		return false;
	}


	//returns 1 if true, 0 if false, -1 if incorrect input, -2 if crashed
	public int typeCheck(String type, String text){
		try{
			if(type.equalsIgnoreCase("email")){
				if(emailCheck(text))
					return 1;
				else return 0;
			}
			else if(type.equalsIgnoreCase("pass")){
				if(passCheck(text,pMin,pMax))
					return 1;
				else return 0;

			}
			else if(type.equalsIgnoreCase("date")){
				if(dateCheck(text))
					return 1;
				else return 0;
			}
			else if(type.equalsIgnoreCase("string")){
				if(check("0011",text,0,text.length()))
					return 1;
				return 0;
			}
			else if(type.equalsIgnoreCase("number")){
				if(check("1000",text,0,text.length()))
					return 1;
				return 0;
			}
			else if(type.equalsIgnoreCase("username")){
				if(check("1011",text,0,20))
					return 1;
				return 0;
			}
			else if(type.equalsIgnoreCase("ssn")){
				if(ssnCheck(text))
					return 1;
				return 0;
			}
			
			return -1;
		}
		catch(Exception e){
			return -2;
		}
	}


}
