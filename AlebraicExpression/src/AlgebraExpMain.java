import java.util.*;

public class AlgebraExpMain {
	
	static ArrayList<Integer> numerical = new ArrayList<Integer>();
	static ArrayList<String> finalExp = new ArrayList<String>();
	static ArrayList<String> finalExpPower = new ArrayList<String>();

	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		
//		String str1 = scanner.nextLine();
//		String str2 = scanner.nextLine();

		String str1 = "2x^2y+3xy^2z-xz^3";
		String str2 = "5xyz+3y^2z-2z";
		
		str1 = str1.concat("$");
		str2 = str2.concat("$");
		
		char[] eqn1 = str1.toCharArray();
		char[] eqn2 = str2.toCharArray();
		
		char symbol1 = ' ';
		if(eqn1[0] == '-')	symbol1 = '-';
		ArrayList<Character> partOf1 = new ArrayList<Character>();
		
		for(int i=0; i<str1.length(); i++) {
			
			if(eqn1[i] == ' ') continue;
			
			if((eqn1[i] == '+' || eqn1[i] == '-' || i == str1.length()-1) && i != 0 ) {
				find2nd(partOf1, eqn2, str2.length(), symbol1);
				symbol1 = eqn1[i];
				partOf1.clear();
			}
			else if(eqn1[i] != '-' || i != 0)
				partOf1.add(eqn1[i]);
		}
		
		for(int i=0; i<finalExpPower.size()-1; i++) {
			for(int j=i+1; j<finalExpPower.size(); j++) {
				if(finalExpPower.get(i).equals(finalExpPower.get(j))) {
					numerical.set(j, numerical.get(i) + numerical.get(j));
					numerical.remove(i);
					finalExpPower.remove(i);
				}
			}
		}
		
		for(int i=0; i<numerical.size(); i++) {
			
			if(numerical.get(i) < 0)
				System.out.print("-" + " " + Math.abs(numerical.get(i)) + finalExpPower.get(i) + " ");
			else
				System.out.print("+" + " " + numerical.get(i) + finalExpPower.get(i) + " ");
		}
	}

	private static void find2nd
	(ArrayList<Character> partOf1, char[] eqn2, int len2, char symbol1) throws Exception {
	
		ArrayList<Character> partOf2 = new ArrayList<Character>();

		char symbol2 = ' ';
		if(eqn2[0] == '-')	symbol2 = '-';
		for(int i=0; i<len2; i++) {
			
			if(eqn2[i] == ' ') continue;
			
			if((eqn2[i] == '+' || eqn2[i] == '-' || i == len2-1) && i != 0 ) {
				perform(partOf1, partOf2, symbol1, symbol2);
				symbol2 = eqn2[i];
				partOf2.clear();
			}
			else if(eqn2[i] != '-' || i != 0)
				partOf2.add(eqn2[i]);
		}
		
	}

	private static void perform
	(ArrayList<Character> partOf1, ArrayList<Character> partOf2, char ex1, char ex2) throws Exception {
		
		int num1, num2;
		if(partOf1.get(0) >= '0' && partOf1.get(0) <= '9')
			num1 = partOf1.get(0) - '0';
		else
			num1 = 1;
		if(partOf2.get(0) >= '0' && partOf2.get(0) <= '9')
			num2 = partOf2.get(0) - '0';	
		else
			num2 = 1;
		
		char finalchar = resultExp(ex1, ex2);

		if(finalchar == '+')
			numerical.add(num1*num2); 
		else
			numerical.add(num1*num2*(-1));		
		
		char x;
		String expression = "";
		for(int i=0; i<partOf1.size(); i++) {
			if(isAlphabet(partOf1.get(i))) {
				x = partOf1.get(i);
				int powerCount = calculatePower(partOf1, i);
				if(powerCount > 1)
					for(int j=1; j<=powerCount; j++)
						expression += x;
				else
					expression += x;
			}
		}
		
		for(int i=0; i<partOf2.size(); i++) {
			if(isAlphabet(partOf2.get(i))) {
				x = partOf2.get(i);
				int powerCount = calculatePower(partOf2, i);
				if(powerCount > 1)
					for(int j=1; j<=powerCount; j++)
						expression += x;
				else
					expression += x;
			}
		}
		char[] temp = expression.toCharArray();
		Arrays.sort(temp);
		expression = String.valueOf(temp);
		finalExp.add(expression);
		
		String withPower = makePower(expression);
		finalExpPower.add(withPower);
	}
	
	private static String makePower(String e) {
		
		String send = "";
		int i = 1, j;
		int len = e.length(), count;
		char[] temp = e.toCharArray();
		for(i = 0; i < len; i++) {  
	        count = 1;  
	        for(j = i+1; j < len; j++) { 
	            if(temp[i] == temp[j]) {  
	                count++; 
	                i++;
	            }
	            if(temp[i] != temp[j])
	            	break;
	        }
	        if(count > 1) {
	        	send += temp[j-1];
	        	send += '^';
	        	send += (char)(count + '0');
	        }
	        else
	        	send += temp[j-1];
	        	
	    }
		return send;
	}

	private static int calculatePower(ArrayList<Character> partOf1, int i) {
		
		int count = 0;
		if(i != partOf1.size()-1 && partOf1.get(i+1) == '^') {
			count += partOf1.get(i+2) - '0';
		}
		return count;
	}
	
	private static boolean isAlphabet(char c) {
		
		if(c >= 'a' && c <= 'z')
			return true;
		else if(c >= 'A' && c <= 'Z')
			return true;
		return false;
	}

	private static char resultExp(char ex1, char ex2) {
		
		if(ex1 == '-' && ex2 == '-')
			return '+';
		else if(ex1 == '-' || ex2 == '-')
			return '-';
		else
			return '+';
	}
}