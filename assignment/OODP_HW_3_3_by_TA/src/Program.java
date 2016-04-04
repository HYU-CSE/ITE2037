import toys.Toy;
import toys.ToyFactory;

public class Program
{
	public static void main(String[] args)
	{
		Toy[] toys = new Toy[10];
		
		
		System.out.print("�峭�� ����...");
		
		for ( int i = 0; i < toys.length; i++ )
			toys[i] = ToyFactory.MakeOne();
		
		System.out.println("�Ϸ�!");
		System.out.println();
		
		
		System.out.println("�峭�� ���(���� ��):");
		
		for ( Toy toy : toys )
			System.out.println(toy);
		
		System.out.println();
		
		
		System.out.print("�峭�� ����...");
		
		for ( Toy toy : toys )
			if ( toy.IsValid() == false)
				toy.Fix();
		
		System.out.println("�Ϸ�!");
		System.out.println();
		
		
		System.out.println("�峭�� ���(���� ��):");
		
		for ( Toy toy : toys )
			System.out.println(toy);
		
		System.out.println();
		
		
		System.out.println("������ �Ϸ� �޽���");
	}
}
