/* Program will convert the numerical values in integers to numbers in words as we pronounce. And program will work from 0 to 9999999*/
package number_challenge;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class numbers {

	public static void main(String[] args)  {
		// TODO Auto-generated method stub

		
		//Define the variables
		HashMap<Integer, String> number_pronunce = new HashMap<Integer, String>();
		HashMap<Integer, String> digit = new HashMap<Integer, String>();
		Scanner scan = new Scanner(System.in);
		String temp_num = "";
		String words = "";
		String tens_value = "";
		int number = -1;
		
		
		number_pronunce = Initialize_numbers();
		//Call the initialize_numbers to initialize all unique prononunciation from 1 to 20 and 30,40,50,60,70,80,90
		digit = Initialize_digit();
		//Initialize_digit function will initialize the unique prononunciation of numbers based on total number of digits
		//For example if number is of length three then 3rd position is hundred position
		//4th and 5th position is thousand position
		//6th and 7th position is lakh position
		
		
		
		while ((number < 0) || (number > 1000000)) {

			//Get the valid input untill user enters number between 0 and 10 lakh
			try
			{
				System.out
				.println("Enter valid number between 0 to 1000000 without any quotes or commas");
			scan = new Scanner(System.in);
			number = scan.nextInt();
			}
			catch(InputMismatchException e)
			{
				System.out
				.println("Please enter integer values without any quotes or commas");
				scan = new Scanner(System.in);
				
			}
		}

		//Convert the input integer to string for easy navigation and splitting of values to identify the numbers in corresponding positions
		// To identify what values are in hundred position thousand position lakh position
		String number_string = Integer.toString(number);
		int len = number_string.length();
		String temp_digits = digit.get(len);
		
		if (number_string.length() > 2) {
			// the number value should be higher than tens digit inorder to split and identify positions
			for (int i = len; i >= 2; i--) {

				//Get the values of the number and identify the numbers in ten's place hundred place, thousand place and in lakh place
				if (temp_digits.equals(digit.get(i))) {

					temp_num = temp_num + number_string.charAt(len - i);
				} else {
					
					if (Integer.parseInt(temp_num.trim()) != 0) {
						words = words
								+ " "
								+ number_in_words(
										Integer.parseInt(temp_num.trim()),
										number_pronunce, "") + " "
								+ temp_digits;
					}
					temp_digits = digit.get(i);
					
					temp_num = "" + number_string.charAt(len - i);
				}
			}

			//Get the numbers at tens and ones position 
			temp_num = number_string.substring(number_string.length() - 2,
					number_string.length());

		} else {
			temp_num = number_string;
		}
		if (!(temp_num.equals("00"))) {
			tens_value = number_in_words(Integer.parseInt(temp_num),
					number_pronunce, tens_value);
		}

		System.out.println((words + " " + tens_value).trim());
		
	}

	private static HashMap<Integer, String> Initialize_digit() {
		// TODO Auto-generated method stub
		//Initialize_digit function with unique prononunciation of numbers based on total number of digits
		HashMap<Integer, String> digit = new HashMap<Integer, String>();
		digit.put(3, "hundred");
		digit.put(4, "thousand");
		digit.put(5, "thousand");
		digit.put(6, "lakh");
		digit.put(7, "lakh");
		return digit;
	}

	private static String number_in_words(int number,
			HashMap<Integer, String> number_pronunce, String words) {
		// TODO Auto-generated method stub
		//Returns the pronuntiation for two digit numbers 
		if (number == 0)
			return "zero";
		if ((number > 20) && !(number_pronunce.containsKey(number))) {
			int denominator = 10;
			int reminder = number % denominator;

			number = number - reminder;

			if (number_pronunce.containsKey(number)) {
				words = number_pronunce.get(number) + " " + words;

			} else {
				words = words + " " + number_pronunce.get(reminder);

				number_in_words(number, number_pronunce, words);
			}

			words = words + " " + number_pronunce.get(reminder);
		} else {
			words = number_pronunce.get(number);
		}

		return words;

	}

	private static HashMap<Integer, String> Initialize_numbers() {
		// TODO Auto-generated method stub
		//Initialize all unique prononunciation from 1 to 20 and 30,40,50,60,70,80,90
		HashMap<Integer, String> number2 = new HashMap<Integer, String>();
		number2.put(1, "one");
		number2.put(2, "two");
		number2.put(3, "three");
		number2.put(4, "four");
		number2.put(5, "five");
		number2.put(6, "six");
		number2.put(7, "seven");
		number2.put(8, "eight");
		number2.put(9, "nine");
		number2.put(10, "ten");
		number2.put(11, "eleven");
		number2.put(12, "twelve");
		number2.put(13, "thirteen");
		number2.put(14, "fourteen");
		number2.put(15, "fifteen");
		number2.put(16, "sixteen");
		number2.put(17, "seventeen");
		number2.put(18, "eighteen");
		number2.put(19, "nineteen");
		number2.put(20, "twenty");
		number2.put(30, "thirty");
		number2.put(40, "fourty");
		number2.put(50, "fifty");
		number2.put(60, "sixty");
		number2.put(70, "seventy");
		number2.put(80, "eighty");
		number2.put(90, "ninety");
		return number2;

	}

}
