package l4g.data;

/**
 * �̹� �Ͽ� ����, �� �� �Ϲ����� ������ ��� ���� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class TurnInfo
{
	/**
	 * �̹� ���� �� �������� ��Ÿ���ϴ�.
	 * 
	 * �� �ʵ��� ���� �ּ� 0, �ִ�(����) <code>Constants.Max_Turn_Numbers</code>�Դϴ�.
	 */
	public final int turnNumber;
	
	/**
	 * �̹� �Ͽ� ���� ���� ���� ���θ� Ȯ���ؾ� �ϴ��� ���θ� ��Ÿ���ϴ�.
	 * 
	 * �� �ʵ��� ���� true�� �Ͽ� �������� �����ڶ��,
	 * ������ Ŭ���� �ȿ� �ִ� <code>trigger_acceptDirectInfection</code> �ʵ��� ���� �����̳Ŀ� ����
	 * ���� ������ �����ϰų� �����ϰ� �˴ϴ�.
	 */
	public final boolean isDirectInfectionChoosingTurn;
	
	/**
	 * ����: �� Ŭ������ �ν��Ͻ��� �������� ���� ���� ���� �ʿ䰡 �����ϴ�.
	 * ���� �̹� �Ͽ� ���� ������ �纻�� �����ϰ� ���� ��쿣
	 * �׳� �� �ν��Ͻ� ��ü�� �������� �ʵ忡 �Ҵ��� ��Ƶ� �˴ϴ�.
	 * �б� �����̶� ���� �Ͽ� ������ �� �ν��Ͻ� ���� ����ϱ� �����Դϴ�.
	 */
	public TurnInfo(int turnNumber, boolean isDirectInfectionChoosingTurn)
	{
		this.turnNumber = turnNumber;
		this.isDirectInfectionChoosingTurn = isDirectInfectionChoosingTurn;
	}
}
