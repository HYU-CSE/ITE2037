package toys;

/**
 * �Ӹ�, ��, �ٸ��� �� ���� �޸� �� �ִ�
 * �峭�� �ϳ��� ��Ÿ���� class�Դϴ�.
 * 
 * @author Racin
 *
 */
public class Toy
{
	int numberOfHeads;
	int numberOfArms;
	int numberOfLegs;
	
	Toy()
	{
		Fix();
	}
	
	public boolean IsValid()
	{
		return numberOfHeads == 1 &&
				numberOfArms == 2 &&
				numberOfLegs == 2;
	}
	
	public void Fix()
	{
		numberOfHeads = 1;
		numberOfArms = 2;
		numberOfLegs = 2;
	}
	
	@Override
	public String toString()
	{
		return String.format("�Ӹ� %d��, �� %d��, �ٸ� %d�� �޸� �峭��", 
				numberOfHeads, numberOfArms, numberOfLegs);
	}
}
