package toys;

import java.util.Random;

/**
 * �� �峭�� instance�� ����� ���� static method�� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class ToyFactory
{
	private static Random rand = new Random();
	
	private ToyFactory()
	{
	}
	
	public static Toy MakeOne()
	{
		Toy newToy = new Toy();
		
		if ( rand.nextInt(10) < 3 )
			newToy.numberOfHeads = 3;
		
		return newToy;
	}
}
