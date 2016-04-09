package l4g2ep1;

/**
 * �÷��̾� �� ���� ���Ӻ� ������ ��� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class Score
{
	/*
	 * �� �ʵ�� �������� ���� �� �� ������ ��� �Ʒ��� ���ǵ� getter �޼������ ����Ͽ� ������ �о�� �� �ֽ��ϴ�.
	 * ( �ٽ� ���ϸ�, �������� �� �ʵ��� ������ ������ ���� �����ϴ� )
	 */
	int[] data;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * �������� ���� ������ �ڵ����� �����˴ϴ�.
	 */
	Score()
	{
		/*
		 * �⺻ ���� �ι�(�� ���º� �Ѿ�) �̿ܿ�
		 * �߸��� �ǻ� ���� Ƚ���� ��Ÿ�� ���� �߻� Ƚ���� ���� ����
		 * -> �÷��̾�� ���������� ����
		 */
		data = new int[8];
	}

	/**
	 * ���� ������� ������ �ִ� ���� �� ���� �о�ɴϴ�.
	 */
	public int GetSurvivor_Max_Survived_Turns() { return data[0]; }

	/**
	 * ���� ������� ������ �� ���� Ƚ���� �о�ɴϴ�.
	 */
	public int GetSurvivor_Total_Spots() { return data[1]; }

	/**
	 * ���� ������� ��ü �ִ� ġ�� �÷��̾� ���� �о�ɴϴ�.
	 */
	public int GetCorpse_Max_Healed_Players() { return data[2]; }

	/**
	 * ���� ������� ��ü �� ġ������ �о�ɴϴ�.
	 */
	public int GetCorpse_Total_Heals() { return data[3]; }

	/**
	 * ���� ������� ����ü �ִ� óġ Ƚ���� �о�ɴϴ�.
	 */
	public int GetInfected_Max_Kills() { return data[4]; }

	/**
	 * ���� ������� ����ü �� �������� �о�ɴϴ�.
	 */
	public int GetInfected_Total_Infects() { return data[5]; }

	@Override
	public String toString()
	{
		return String.format("%4d %4d %4d %4d %4d %4d", data[0], data[1], data[2], data[3], data[4], data[5]);
	}
}
