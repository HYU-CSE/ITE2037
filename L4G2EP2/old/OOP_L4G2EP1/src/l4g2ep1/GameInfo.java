package l4g2ep1;

/**
 * ��� �÷��̾�� �����ϰ� �־�����, ���� ����ǰ� �ִ� ���� ��ü�� ���� �����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class GameInfo
{
	/*
	 * ���� �ִ� �ʵ���� �������� ���� �� �� ������ ��� �Ʒ��� ���ǵ� getter �޼������ ����Ͽ� ������ �о�� �� �ֽ��ϴ�.
	 * ( �ٽ� ���ϸ�, �������� �� �ʵ��� ������ ������ ���� �����ϴ� )
	 */	
	int gameNumber;
	int currentTurnNumber;
	boolean isDirectInfectionChoosingTurn;
	int directInfectionCountdown;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * ���� ������ �� ������ ���۵� �� �� �÷��̾�� �ڵ����� �־����ϴ�.
	 */
	GameInfo()
	{
		directInfectionCountdown = -1;
	}

	/**
	 * ���� �������� ���� ��ȣ�� �����ɴϴ�.
	 */
	public int GetGameNumber() { return gameNumber; }

	/**
	 * ���� �� ��°������ ��Ÿ���� ��ȣ�� �����ɴϴ�.
	 */
	public int GetCurrentTurnNumber() { return currentTurnNumber; }

	/**
	 * �̹� ���� ���� ������ �����ؾ� �ϴ� ������ ���θ� �����ɴϴ�.
	 * �� ���� true�鼭 �ڽ��� �������� ��� this.acceptDirectInfection �ʵ尪�� ���������ν� ���� ���� ���� ���θ� ������ �� �ֽ��ϴ�.
	 */
	public boolean GetIsDirectInfectionChoosingTurn() { return isDirectInfectionChoosingTurn; }

	/**
	 * ���� ������ ����Ǳ���� ���� �� ���� �����ɴϴ�.
	 * �� ���� -1�� ��� ���� ���� ������ ���۵��� �ʾ����� �ǹ��մϴ�.
	 */
	public int GetDirectInfectionCountdown() { return directInfectionCountdown; }
}
