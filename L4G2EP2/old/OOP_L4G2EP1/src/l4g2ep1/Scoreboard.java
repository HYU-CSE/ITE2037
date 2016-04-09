package l4g2ep1;

import l4g2ep1.common.*;

/**
 * �÷��̾���� ������ ����ϰ� �̸� ���� ������ �����ϱ� ���� ��� ������ ��� Ŭ�����Դϴ�.
 * �� Ŭ������ ���ǽ� ���ο����� ���Ǹ� �������� �� Ŭ������ �� �� �����ϴ�.
 * �ڽ��� ���� ������ Ȯ���Ϸ��� this.myScore �ʵ带 �����ϼ���. 
 * 
 * @author Racin
 *
 */
class Scoreboard
{
	enum ScoreTypeCode
	{
		Survivor_Max,
		Survivor_Total,
		Corpse_Max,
		Corpse_Total,
		Infected_Max,
		Infected_Total,
		Soul_Freedom,
		Soul_Destruction
	}

	Score[] scores;

	int[][] grades;
	int[][] ranks;

	int[] final_grades;
	int[] final_ranks;

	/*
	 * �������� �� Ŭ������ ���� ����� �� �����ϴ�.
	 * �ڽ��� ���� ������ Ȯ���Ϸ��� this.myScore �ʵ带 �����ϼ���.
	 */
	Scoreboard()
	{
		scores = new Score[Constants.Total_Players];
		for ( int iScore = 0; iScore < Constants.Total_Players; ++iScore )
		{
			scores[iScore] = new Score();
		}

		grades = new int[Constants.Total_Players][8];
		ranks = new int[Constants.Total_Players][8];
		final_grades = new int[Constants.Total_Players];
		final_ranks = new int[Constants.Total_Players];
	}

	void Update(ScoreTypeCode type, int ID, int amount)
	{
		switch (type)
		{
		case Survivor_Max:
			if ( scores[ID].data[0] < amount )
			{
				scores[ID].data[0] = amount;
			}
			break;
		case Survivor_Total:
			scores[ID].data[1] += amount;
			break;
		case Corpse_Max:
			if ( scores[ID].data[2] < amount )
			{
				scores[ID].data[2] = amount;
			}
			break;
		case Corpse_Total:
			scores[ID].data[3] += amount;
			break;
		case Infected_Max:
			if ( scores[ID].data[4] < amount )
			{
				scores[ID].data[4] = amount;
			}
			break;
		case Infected_Total:
			scores[ID].data[5] += amount;
			break;
		case Soul_Freedom:
			scores[ID].data[6] += amount;
			break;
		case Soul_Destruction:
			scores[ID].data[7] += amount;
			break;
		}
	}

	void CalculateGrades()
	{
		int[] max = new int[6];
		int[] min = new int[6];

		// Max �迭�� �ִ밪�� ����(�ּҰ��� �׻� 0), Total �迭�� �ִ밪�� �ּҰ� ��� ����
		for ( int i = 0; i < 6; ++i )
		{
			if ( i % 2 == 1 )
				min[i] = Integer.MAX_VALUE;

			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				int value = scores[iPlayer].data[i];
				if ( max[i] < value )
					max[i] = value;
				if ( min[i] > value )
					min[i] = value;
			}
		}

		// ������ ���� ���� �迭�� ���� �� ���� ����
		for ( int i = 0; i < 6; ++i )
		{
			// �ش� �迭�� ���� ��� �÷��̾��� ������ ���ٸ� ���� 1��
			if ( max[i] - min[i] == 0 )
			{
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
				{
					grades[iPlayer][i] = 100;
					final_grades[iPlayer] += 100;
					ranks[iPlayer][i] = 1;
				}
			}
			// �׷��� ���� ��� ����� �ǽ�
			else
			{
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
				{
					int value = scores[iPlayer].data[i];
					int grade = (value - min[i]) * 100 / (max[i] - min[i]);

					grades[iPlayer][i] = grade;
					final_grades[iPlayer] += grade;

					ranks[iPlayer][i] = 1;
					for ( int iOtherPlayer = 0; iOtherPlayer < Constants.Total_Players; ++iOtherPlayer )
						if ( value < scores[iOtherPlayer].data[i] )
							++ranks[iPlayer][i];
				}
			}
		}

		// �ջ�� ������ ���� ���� ���� ����
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			final_ranks[iPlayer] = 1;
			for ( int iOtherPlayer = 0; iOtherPlayer < Constants.Total_Players; ++iOtherPlayer )
				if ( final_grades[iPlayer] < final_grades[iOtherPlayer] )
					++final_ranks[iPlayer];
		}
	}
}
