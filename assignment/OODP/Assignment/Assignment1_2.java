import java.util.Scanner;

public class Assignment1_2 {
	
	public static void main(String[] args) {
		int lastNumber = GetLastNumber();
		for(int number = 1; number <= lastNumber; number++)
			Say(number, lastNumber);
		System.out.println("The End!");
	}
	
	static int GetLastNumber() {
		int number;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Last Number: ");
		number = Integer.parseInt(in.nextLine());
		in.close();
		return number;
	}
	
	static void Say(int currentNumber, int lastNumber) {
		String[] kor = {"", "��", "��", "��", "��", "��", "��", "ĥ", "��", "��", "��"};
		String number_string = String.valueOf(currentNumber);
		int clap = 0;
		
		for (char ch : number_string.toCharArray()) {
			int number_each = Character.getNumericValue(ch);
			if (number_each == 3 || number_each == 6 || number_each == 9)
				clap++;
		}
		
		if ((clap > 0 ? 1 : 0) == ((currentNumber == lastNumber) ? 1 : 0)) {
			int first = currentNumber / 10;
			int second = currentNumber % 10;
			if (first > 1)
				System.out.print(kor[first]);
			if (first > 0)
				System.out.print(kor[10]);
			System.out.println(kor[second]);
		} else {
			System.out.println("Clap!");
		}
	}
}
