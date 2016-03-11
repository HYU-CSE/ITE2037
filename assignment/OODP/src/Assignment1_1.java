import java.util.Scanner;

public class Assignment1_1 {
	
	static int myAge = 22;
	
	public static void main(String[] args) {
		for(int idx = 0; idx < 3; idx++)
			Insa(GetAge());	
		System.out.println("The End!");
	}
	
	static int GetAge() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your age: ");
		return in.nextInt();
	}
	
	static void Insa(int age) {
		if(age > myAge) {
			System.out.println("æ»≥Á«œººø‰.");
		} else if(age == myAge) {
			System.out.println("æ»≥Á.");
		} else {
			System.out.println("æ»≥Á. ¿”∏∂");
		}
	}
}
