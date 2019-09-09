package converter;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String sourceRadix = "";
		String sourceNumber = "";
		String targetRadix = "";
		if (scanner.hasNextLine()) {
			sourceRadix = scanner.nextLine();
		}
		if (scanner.hasNextLine()) {
			sourceNumber = scanner.nextLine();
		}
		if (scanner.hasNextLine()) {
			targetRadix = scanner.nextLine();
		}

		String answer;

		if (sourceRadix.equals("") || sourceRadix.equals(" ") || sourceNumber.equals("") || sourceNumber.equals(" ") || targetRadix.equals("") || targetRadix.equals(" ")) { // Empty string check
			System.out.println("Error Empty String");
			return;
		} else if (Pattern.matches("[a-zA-Z]+", sourceRadix) || Pattern.matches("[a-zA-Z]+", targetRadix)) {
			System.out.println("Error Radix should be a number");
			return;
		} else if (Integer.parseInt(sourceRadix) < 1 || Integer.parseInt(targetRadix) < 1) {
			System.out.println("Error");
			return;
		}
		else if (Integer.parseInt(sourceRadix) > 36 || Integer.parseInt(targetRadix) > 36) {
			System.out.println("Error");
			return;
		}

		if (sourceNumber.contains(".")) {
			answer = computeFractionPart(sourceNumber, Integer.parseInt(sourceRadix), Integer.parseInt(targetRadix));
		} else {
			answer = computeIntegerPart(sourceNumber, Integer.parseInt(sourceRadix), Integer.parseInt(targetRadix));
		}

		System.out.println(answer);
	}

	private static String computeIntegerPart(String sourceNumber, int sourceRadix, int targetRadix) {

		StringBuilder answer = new StringBuilder();

		if (sourceRadix == 1) {
			int sum = 0;
			for (int i = 0; i < sourceNumber.length(); i++) {
				sum += 1;
			}
			answer = new StringBuilder(Long.toString(sum, targetRadix));
		} else if (targetRadix == 1) {
			int number = Integer.parseInt(integerPart(sourceNumber, sourceRadix, 10));
			answer.append("1".repeat(Math.max(0, number)));
		} else {
			answer = new StringBuilder(integerPart(sourceNumber, sourceRadix, targetRadix));
		}

		return answer.toString();
	}

	private static String computeFractionPart(String sourceNumber, int sourceRadix, int targetRadix) {

		String[] integerAndFraction = sourceNumber.split("\\.");
		StringBuilder answer = new StringBuilder();
		String fraction = integerAndFraction[1];
		double decimalFraction;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(5);
		nf.setRoundingMode(RoundingMode.HALF_UP);

		String integerPart = computeIntegerPart(integerAndFraction[0], sourceRadix, targetRadix);

		if (sourceRadix == 10) {
			decimalFraction = Double.parseDouble("0." + fraction);
		} else {
			decimalFraction = fractionToDecimal(fraction, sourceRadix);
		}
		//	System.out.println(decimalFraction);

		if (targetRadix == 1) {
			answer.append(integerPart);
		} else {
			answer.append(integerPart);
			answer.append(".");
			answer.append(decimalFractionToRadix(decimalFraction, targetRadix));
		}

		return answer.toString();
	}

	private static String integerPart(String number, int sourceRadix, int targetRadix) {

		return Long.toString(Long.parseLong(number, sourceRadix), targetRadix);
	}

	private static double fractionToDecimal(String fraction, int radix) {

		double decimalFraction = 0;
		int pow = -1;

		for (char c : fraction.toCharArray()) {
			if (c >= 'a') {
				decimalFraction += (double) (c - 'a' + 10) * Math.pow(radix, pow);

			} else {
				decimalFraction += (c - '0') * Math.pow(radix, pow);
			}
			pow--;

		}
		return decimalFraction;
	}

	private static String decimalFractionToRadix(double fraction, int radix) {

		StringBuilder radixFraction = new StringBuilder();
		int repeat = 0;
		while (fraction % 1 != 0 && repeat < 5) {
			fraction = radix * fraction;
			int whole = (int) fraction;
			fraction -= whole;
			char newChar = (char) ('0' + whole);
			if (newChar > '9') {
				newChar = (char) ('a' + (newChar - '9') - 1);
			}
			radixFraction.append(newChar);
			repeat++;
		}

		return radixFraction.toString();
	}


}
