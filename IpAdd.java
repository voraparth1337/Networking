/*
This java code calculates the first and last IP address in the block of addresses given any address in the group and the corresponding mask
This might not be the most optimal code but it is just an attempt :)
GLHF
*/

package practice;

import java.util.Scanner;

public class IpAdd {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Ip address");
		String IP = sc.nextLine();
		String ip[] = IP.split("\\.");
		String classOfIp = determineClass(ip[0]);
		System.out.println("Class of IP : " + IP + " is " + classOfIp);
		System.out.println("Use default subnet ? (Y/N) ");
		String choice = sc.next();
		if (choice.equalsIgnoreCase("Y")) {
			System.out.println("Default subnet selected");
			String subnet = determineSubnetMask(classOfIp);
			System.out.println("your mask is " + subnet);
			String subnetArray[] = subnet.split("\\.");
			System.out.print("First address is : \t");
			System.out.println(generateDotNotation(calculateFirstAddress(ip, subnetArray)));
			System.out.print("Last address is : \t");
			System.out.println(generateDotNotation(calculateLastAddress(ip, subnetArray)));

		}
		if (choice.equalsIgnoreCase("N")) {
			System.out.println("Enter the subnet bits");
			int subnetBits = sc.nextInt();
			int count = 0;
			StringBuffer maskString = new StringBuffer("");
			for (int i = 0; i < 32; i++) {
				if (count < subnetBits) {
					maskString.append("1");
					count++;
				} else
					maskString.append("0");
			}
			System.out.print("First address is : \t");
			System.out.println(generateDotNotation(calculateFirstAddress(ip, maskString.toString())));
			System.out.print("Last address is : \t");
			System.out.println(generateDotNotation(calculateLastAddress(ip, maskString.toString())));

		}

	}

	public static String determineClass(String firstByte) {
		int temp = Integer.parseInt(firstByte);
		if (temp > 0 && temp < 128)
			return "A";
		if (temp > 127 && temp < 192)
			return "B";
		if (temp > 191 && temp < 234)
			return "C";
		else
			return "invalid ip";

	}

	public static String determineSubnetMask(String classOfIP) {
		if (classOfIP.equalsIgnoreCase("a"))
			return "255.0.0.0";
		if (classOfIP.equalsIgnoreCase("b"))
			return "255.255.0.0";
		if (classOfIP.equalsIgnoreCase("c"))
			return "255.255.255.0";
		else
			return "-1";
	}

	// generates binary string from the decimal dotted address
	public static String getBinaryString(String[] tempArray) {
		StringBuffer binaryOfIp = new StringBuffer("");
		for (int i = 0; i < 4; i++) {
			String temp = String.format("%8s", Integer.toBinaryString(Integer.parseInt(tempArray[i]))).replace(" ",
					"0");
			binaryOfIp.append(temp);
		}
		return binaryOfIp.toString();
	}

	// calculates the first address and output is binary string
	public static String calculateFirstAddress(String[] ip, String[] mask) {
		long longOfIp = Long.parseLong(getBinaryString(ip), 2);
		long longOfMask = Long.parseLong(getBinaryString(mask), 2);
		long ans = longOfIp & longOfMask;
		return Long.toBinaryString(ans);
	}

	public static String calculateFirstAddress(String[] ip, String mask) {
		long longOfIp = Long.parseLong(getBinaryString(ip), 2);
		long longOfMask = Long.parseLong(mask, 2);
		long ans = longOfIp & longOfMask;
		return Long.toBinaryString(ans);
	}

	public static String calculateLastAddress(String[] ip, String[] mask) {
		long LongOfIp = Long.parseLong(getBinaryString(ip), 2);
		long complementLongOfMask = Long.parseLong(getComplement(getBinaryString(mask)), 2);
		long ans = LongOfIp | (complementLongOfMask);
		return Long.toBinaryString(ans);
	}

	public static String calculateLastAddress(String[] ip, String mask) {
		long LongOfIp = Long.parseLong(getBinaryString(ip), 2);
		long longOfMask = Long.parseLong(getComplement(mask), 2);
		long ans = LongOfIp | longOfMask;
		return Long.toBinaryString(ans);
	}
	
	public static String getComplement(String str){
		StringBuffer temp = new StringBuffer("");
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) == '0'){
				temp.append("1");
			}
			else temp.append("0");
		}
		return temp.toString();
	}
	
	public static String generateDotNotation(String str) {
		StringBuffer temp = new StringBuffer("");
		temp.append(Integer.parseInt(str.substring(0, 8), 2));
		temp.append(".");
		temp.append(Integer.parseInt(str.substring(8, 16), 2));
		temp.append(".");
		temp.append(Integer.parseInt(str.substring(16, 24), 2));
		temp.append(".");
		temp.append(Integer.parseInt(str.substring(24, 32), 2));
		return temp.toString();
	}
}