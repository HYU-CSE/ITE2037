package l4g.customplayers;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.directory.DirContext;

import l4g.Cell;
import l4g.common.*;
import l4g.data.PlayerInfo;

@SuppressWarnings("unused")
public class Player_PreferName extends Player
{

	// ����ġ�� ��Ÿ���� ������
	int infectedConst = 3000_0000;
	int bodyConstWhenSurvive = 1200_0000;
	int survivorConst = 4250_0000;
	int soulConst = 1000_0000;
	int bodyConstWhenInfected = 6750_0000;

	double infectedDecreaseValue = 0.5;
	double infectedHPDecreaseValue = 0.5;
	double survivorDecreaseValue = 0.5;
	double bodyDecreaseValue = 0.5;

	double body2infectedProb = 0.6; // 1�� ���� ��ü�� ����ü�� �� Ȯ��
	double infectedStayProb = 0.3; // ����ü�� ���ڸ��� ���� Ȯ��

	int changeToBeDieModeTurn = 32;

	private int infectedHPWhenSpawnConst = 6;
	private int bodyWhenSpawnConst = 2;
	int stayCountBody = 2;

	int updateCount = 2; // 1�ϴ� ������ ���������� Ƚ��

	double stayRange = 80; // ����ü�� ������ ��
	double stayRangeAfterInfected = 3.19E11; // ����ü�� ������ ���� ������ �ö󰣴�
	
	long pongVectorSizeMax = 6050500000L; // ���� ������ �� ũ�⺸�� ���� ���Ͱ��� �ִٸ� �ݴ�������
											// Ƣ����� �Ѵ�
	double survivorRemove = 0.7; //������ ���� ����. ó�� ���� ������ 1�� �ڵ� ��ȯ.
	
	// �����غ��� �׻� Ƣ����� �ϴ� ���� �� ���� �� ���⵵...

	// ���� ��Ȳ�� ����ϴ� ����
	private double survivorCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height]; // ���μ���
	private double infectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
	private double bodyCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
	private double infectedSumHP[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
	private double body2infectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];

	// ����� ��Ȳ ��� ����
	int firstDieTurn; // ó������ ���� �� ���
	boolean infectedOpen = false; // ����ü�� �� ���� �ִ��� ������ ��
	boolean isToBeDieModeTurnChanged = false;
	int randomInftectedTurnCount = 0;

	// ������ ��带 ��Ÿ���� ���� + �ʱ�ȭ
	boolean surviveMode = true;
	boolean toBeDieMode = false;

	public Player_PreferName(int ID)
	{

		super(ID, "��ȣ�ϴ��̸�!");

		this.trigger_acceptDirectInfection = false; // ������ ��� ���� ����
	}

	//
	// ���� ���¿� ���� �ൿ�ؾ� �� ���� ������ �޼ҵ�
	// =======================================================

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		double vectorX = 0, vectorY = 0;
		boolean positive = true;

		this.updateInfo();

		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		

		
		if (turnInfo.turnNumber < Constants.Duration_Direct_Infection)
		{
			vectorX = vectorSum(survivorCount, true, survivorConst);
			vectorY = vectorSum(survivorCount, false, survivorConst);
			
			if (turnInfo.turnNumber < 3)
			{
				positive = true;
			}
			else
			{
				positive = false;
			}
			result = nextDirection(vectorX, vectorY, positive);
			// result = randomDirection();

		}
		else if (surviveMode && turnInfo.turnNumber <= changeToBeDieModeTurn) // ����!!
		{
			survivorRemove = 1;
			// ������ �����ϱ� ���� �����ڿ��� ������ ����
			vectorX = vectorSum(survivorCount, true, survivorConst);
			vectorY = vectorSum(survivorCount, false, survivorConst);
			// �˷��� ����ü���� ����!
			vectorX -= vectorSum(infectedSumHP, true, infectedConst);
			vectorY -= vectorSum(infectedSumHP, false, infectedConst);
			// �˷��� ��ü�κ��� ����!
			vectorX -= vectorSum(bodyCount, true, bodyConstWhenSurvive);
			vectorY -= vectorSum(bodyCount, false, bodyConstWhenSurvive);

			positive = true;
			result = nextDirection(vectorX, vectorY, positive); // ����� ���� ������
			result = checkNoInfected(result);
		} // �״� ���� ����
		else
		{
			survivorRemove = 1;
			vectorX = vectorSum(infectedSumHP, true, infectedConst);
			vectorY = vectorSum(infectedSumHP, false, infectedConst);
			vectorX += vectorSum(bodyCount, true, bodyConstWhenSurvive);
			vectorY += vectorSum(bodyCount, false, bodyConstWhenSurvive);
			positive = false;
			result = nextDirection(vectorX, vectorY, positive); // ����� �� ������
			result = checkNoInfected(result);
		}

		// ��ó��
		result = _checkVaild(edgeDirection(result, vectorX, vectorY, positive));
		return result;
	}

	DirectionCode checkNoInfected(DirectionCode result)
	{
		if (myInfo.position.row > 0
				&& cells[myInfo.position.row - 1][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Infected) > 0
				&& result == DirectionCode.Up
				|| myInfo.position.row < Constants.Classroom_Height - 1
						&& cells[myInfo.position.row + 1][myInfo.position.column]
								.CountIf_Players(player -> player.state == StateCode.Infected) > 0
						&& result == DirectionCode.Down) // ���Ʒ��� �����̸鼭 ���Ʒ��� ����ü��
															// �ִٸ�
		{
			if (myInfo.position.column > 0 && cells[myInfo.position.row][myInfo.position.column - 1]
					.CountIf_Players(player -> player.state == StateCode.Infected) < 1)
				result = DirectionCode.Left;
			else if (myInfo.position.column < Constants.Classroom_Width - 1
					&& cells[myInfo.position.row][myInfo.position.column + 1]
							.CountIf_Players(player -> player.state == StateCode.Infected) < 1)
				result = DirectionCode.Right;
			else if (result == DirectionCode.Down)
				result = DirectionCode.Up;
			else if (result == DirectionCode.Up)
				result = DirectionCode.Down;
		}

		else if (myInfo.position.column > 0
				&& cells[myInfo.position.row][myInfo.position.column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected) > 0
				&& result == DirectionCode.Left
				|| myInfo.position.column < Constants.Classroom_Width - 1
						&& cells[myInfo.position.row][myInfo.position.column + 1]
								.CountIf_Players(player -> player.state == StateCode.Infected) > 0
						&& result == DirectionCode.Right) // �¿�� �����̸鼭 �¿쿡 ����ü��
															// �ִٸ�
		{
			if (myInfo.position.row > 0 && cells[myInfo.position.row - 1][myInfo.position.column]
					.CountIf_Players(player -> player.state == StateCode.Infected) < 1)
				result = DirectionCode.Up;
			else if (myInfo.position.row < Constants.Classroom_Height - 1
					&& cells[myInfo.position.row + 1][myInfo.position.column]
							.CountIf_Players(player -> player.state == StateCode.Infected) < 1)
				result = DirectionCode.Down;
			else if (result == DirectionCode.Right)
				result = DirectionCode.Left;
			else if (result == DirectionCode.Left)
				result = DirectionCode.Right;

		}

		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
		this.updateInfo(); // ��ü�� �̿��� ������ ������Ʈ
		return;
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = null;
		double vectorX = 0, vectorY = 0;
		infectedOpen = true;
		int sumOfBodyHP = 0;

		this.updateInfo();
		stayRange = stayRangeAfterInfected; //

		// ���� Ȯ���� ���� ���� ����
		for (PlayerInfo a : cells[myInfo.position.row][myInfo.position.column]
				.Select_Players(player -> player.state == StateCode.Corpse))
		{
			sumOfBodyHP += a.HP;
		}
		if (cells[myInfo.position.row][myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		{
			result = DirectionCode.Stay;
		}
		else if (myInfo.position.row > 0
				&& cells[myInfo.position.row - 1][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		{
			result = DirectionCode.Up;
		}
		else if (myInfo.position.row < Constants.Classroom_Height - 1
				&& cells[myInfo.position.row + 1][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		{
			result = DirectionCode.Down;
		}
		else if (myInfo.position.column > 0
				&& cells[myInfo.position.row][myInfo.position.column - 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		{
			result = DirectionCode.Left;
		}
		else if (myInfo.position.column < Constants.Classroom_Width - 1
				&& cells[myInfo.position.row][myInfo.position.column + 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		{
			result = DirectionCode.Right;
		}
		else
		{

			// �����ڿ� ��ü�� ��� ����Ѵ�
			vectorX = vectorSum(survivorCount, true, survivorConst);
			vectorY = vectorSum(survivorCount, false, survivorConst);
			vectorX += vectorSum(bodyCount, true, bodyConstWhenInfected);
			vectorY += vectorSum(bodyCount, false, bodyConstWhenInfected);
			result = nextDirection(vectorX, vectorY, true);
			result = edgeDirection(result, vectorX, vectorY, true);
			randomInftectedTurnCount++;
		}
		

		return _checkVaild(result);
	}

	@Override
	public void Soul_Stay()
	{
		randomInftectedTurnCount = 0;
		if (turnInfo.turnNumber == 0)
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
		}
		else
		{
			this.updateInfo();
			randomInftectedTurnCount = 0;
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		int result_row = pesudoRandom() % Constants.Classroom_Width;
		int result_column = pesudoRandom() % Constants.Classroom_Height;
		// �׷� ���� ������ �����ڰ� �� �� ���ٸ� ���� ������

		int maxHP = 0;
		int tmpHP;

		this.updateInfo();


		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		// ���� �ٷ� ��ü�� �Ǿ�� �Ѵٸ� ����ü ���� ������ ��������. ��ġ + �����¿� HP ���� ���� ū ������ ��Ȱ�ϸ� �� ��
		// ����

		for (int i = 0; i < Constants.Classroom_Width; i++)
		{
			for (int j = 0; j < Constants.Classroom_Height; j++)
			{
				tmpHP = 0;
				for (PlayerInfo infectedPlayer : cells[i][j]
						.Select_Players(player -> player.state == StateCode.Infected))
				{
					if (cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
					{
						tmpHP += infectedPlayer.HP * infectedHPWhenSpawnConst;
					}

				}
				if (tmpHP > maxHP)
				{
					maxHP = tmpHP;
					result_row = i;
					result_column = j;
				}
			}
		}

		return new Point(result_row, result_column);
	}

	// ������ ������Ʈ�� �ִ� �޼ҵ�
	// =======================================

	void updateInfo()
	{
		/**
		 * �� ���� ��Ÿ���� �ʵ带 ������Ʈ�� �ִ� �Լ�. �� �޼ҵ��� ������ ������ ������ ���� ������ ���� ����.
		 * 
		 * �� �Ͽ��� �� �޼ҵ�� �ϳ��� ȣ��Ǿ�� �Ѵ�.
		 */

		// ���Ӱ� ������ �� �迭�� �����
		double tmpSurvivorCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpInfectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpBodyCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpInfectedSumHP[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpBody2infectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];

		// �ӽ� ���� �迭
		int tmp;

		// ���� ���� �迭 �ʱ�ȭ
		for (int i = 0; i < Constants.Classroom_Height; i++) // ���� : i / ���� : j
		{
			for (int j = 0; j < Constants.Classroom_Width; j++)
			{
				tmpBodyCount[i][j] = 0;
				tmpInfectedCount[i][j] = 0;
				tmpSurvivorCount[i][j] = 0;
				tmpInfectedSumHP[i][j] = 0;
				tmpBody2infectedCount[i][j] = 0;
			}
		}

		// ���� ������ �־ ���� ������ ����ִ´�

		for (int i = 0; i < Constants.Classroom_Height; i++) // ���� : i / ���� : j
		{
			for (int j = 0; j < Constants.Classroom_Width; j++)
			{
				// ������ ������ ����ִ´�

				tmp = survivorConst * cells[i][j].CountIf_Players(player -> player.state == StateCode.Survivor);
				tmpSurvivorCount[i][j] += tmp;
				// ����ü ����(����, HP��)�� ����ִ´�
				tmpInfectedCount[i][j] += infectedConst
						* cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
				for (PlayerInfo tmpPlayer : cells[i][j].Select_Players(player -> player.state == StateCode.Infected))
				{
					tmpInfectedSumHP[i][j] += tmpPlayer.HP;
				}
				
				tmpBody2infectedCount[i][j] += body2infectedProb
						* cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse);
				for (PlayerInfo tmpPlayer : cells[i][j].Select_Players(player -> player.state == StateCode.Corpse))
				{
					tmpBodyCount[i][j] += tmpPlayer.HP;
				}
			}
		}

		// ������ ���� ���ο� �迭�� ������������ �Ѵ�
		for (int k = 0; k < updateCount; k++)
		{
			for (int i = 0; i < Constants.Classroom_Height; i++) // ���� : i / ����
																	// // : j
			{
				for (int j = 0; j < Constants.Classroom_Width; j++)
				{
					// ������ ���� ���ο� �迭�� ����������.
					// updateCountArray(bodyCount, tmpBodyCount, this.bodyDecreaseValue / Math.pow(2, k), i, j);
					updateCountArray(infectedCount, tmpInfectedCount, this.infectedDecreaseValue / Math.pow(2, k), i,
							j);
					updateCountArray(survivorCount, tmpSurvivorCount, this.survivorDecreaseValue / Math.pow(1.8, k), i,
							j);
					updateCountArray(infectedSumHP, tmpInfectedCount, this.infectedHPDecreaseValue / Math.pow(2, k), i,
							j);
				}
			}
		}

		// Ȯ���� ������ ����ִ´�

		for (int i = 0; i < Constants.Classroom_Height; i++) // ���� : i / ���� : j
		{
			for (int j = 0; j < Constants.Classroom_Width; j++)
			{
				// �����ڴ� �� �ڸ��� ���� ���� ����
				tmp = survivorConst * cells[i][j].CountIf_Players(player -> player.state == StateCode.Survivor);


				
				tmpSurvivorCount[i][j] -= tmp * survivorRemove;
				// ����ü�� �� �ڸ��� ���� ���� ����� �� �����
				tmpInfectedCount[i][j] += infectedConst
						* cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
				for (PlayerInfo tmpPlayer : cells[i][j].Select_Players(player -> player.state == StateCode.Infected))
				{
					tmpInfectedSumHP[i][j] -= tmpPlayer.HP * (1 - infectedStayProb);
				}
				// ��ü ���� ���� 1�̰� �����¿쿡 ����ü�� ���ٸ� �������� ����ü�� �� ���ɼ�.

				// TODO : �����¿쿡 ����ü�� �ִٸ� �ٸ� ����� �����ϵ��� ����.
				//
			}
		}

		// ���������� ���� ���� �迭�� ���ڸ��� �ִ´�.
		survivorCount = tmpSurvivorCount;
		infectedCount = tmpInfectedCount;
		bodyCount = tmpBodyCount;
		infectedSumHP = tmpInfectedSumHP;
		body2infectedCount = tmpBody2infectedCount;

		return;
	}

	void updateCountArray(double[][] bodyCount2, double[][] tmpBodyCount, double c, int i, int j)
	{
		/**
		 * ���ο� �迭�� ����ġ�� ���ؼ� �����¿쿡 ���� �ִ� �żҵ�.
		 * 
		 * ���� ó���� �ϴ� ���� �����ϰ� ����� ���� ������ ���� �ǹǷ� �żҵ带 ����.
		 * 
		 * oldArr : ������ ���¸� ��� �ִ� �迭 newArr : ���ο� ���¸� ���� �迭. ��� �迭�� ���ҵ��� 0����
		 * �ʱ�ȭ�Ǿ� �ִٴ� ���� �Ͽ��� ����ȴ�. c : ���� ���ҽ�ų ��� x : ���� ��ǥ y : ���� ��ǥ
		 * 
		 */
		if (i - 1 > 0)
			tmpBodyCount[i - 1][j] += (int) (c * (double) bodyCount2[i][j]);
		if (i + 1 < Constants.Classroom_Width)
			tmpBodyCount[i + 1][j] += (int) (c * (double) bodyCount2[i][j]);
		if (j - 1 > 0)
			tmpBodyCount[i][j - 1] += (int) (c * (double) bodyCount2[i][j]);
		if (j + 1 < Constants.Classroom_Height)
			tmpBodyCount[i][j + 1] += (int) (c * (double) bodyCount2[i][j]);
		if (i - 1 > 0 && j - 1 > 0)
			tmpBodyCount[i - 1][j - 1] += (int) (c * (double) bodyCount2[i][j]);
		if (i - 1 > 0 && j + 1 < Constants.Classroom_Height)
			tmpBodyCount[i - 1][j + 1] += (int) (c * (double) bodyCount2[i][j]);
		if (i + 1 < Constants.Classroom_Width && j - 1 > 0)
			tmpBodyCount[i + 1][j - 1] += (int) (c * (double) bodyCount2[i][j]);
		if (i + 1 < Constants.Classroom_Width && j + 1 < Constants.Classroom_Height)
			tmpBodyCount[i + 1][j + 1] += (int) (c * (double) bodyCount2[i][j]);
	}

	DirectionCode edgeDirection(DirectionCode result, double vectorX, double vectorY, boolean positive)
	{
		DirectionCode tmpResult, newResult = result;
		tmpResult = __checkVaild(result); // �𼭸��� �־��ٸ�
		if (tmpResult != result) // ���� ����� ������ �� ���� ���
		{
			if (result == DirectionCode.Up || result == DirectionCode.Down)
			{ // ���Ʒ��� �����̸� ��踦 �Ѵ� ��� ���Ʒ� ������ �����ϰ� �Ǵ�
				newResult = nextDirection(vectorX, 0, positive);
			}
			else if (result == DirectionCode.Left || result == DirectionCode.Right)
			{
				newResult = nextDirection(0, vectorY, positive);
			}
		}
		
		return newResult;
	}

	// �ٸ� �޼ҵ带 �����ϴ� �޼ҵ�
	// ========================================

	DirectionCode nextDirection(double vectorX, double vectorY, boolean positive)
	{
		DirectionCode result = null; // ���� Ȯ���� ����
		// positive : ���� ���� / ���� ���� ����. ���� �����̸� true.
		double stayRange;
		double vectorX1 = vectorX, vectorY1 = vectorY;

		if (!positive) // ���� ������ ���͸� 180�� ������Ų ���̴�
		{
			vectorX = -vectorX;
			vectorY = -vectorY;
		}
		if (myInfo.state == StateCode.Survivor)
		{
			stayRange = 0;
		}
		else
		{
			stayRange = this.stayRange;
		}
		
		if (vectorX == vectorY)
		{
			vectorX += pesudoRandom() % 15;
			vectorY += pesudoRandom() % 15;
			
		}

		if (vectorX < stayRange && vectorY > stayRange)
			vectorX = 1;
		else if (vectorX > stayRange && vectorY < stayRange)
			vectorY = 1;

		// �� ������ �������� �ؼ� �ð� �������� ���ư��� �Ѵ�
		if (vectorX > 0 && vectorY > 0) // 1��и�
		{
			if (vectorX < vectorY)
				result = DirectionCode.Up;
			else
				result = DirectionCode.Right;
		}
		else if (vectorX > 0 && vectorY < 0) // 2��и�
		{
			if (vectorX > -vectorY)
				result = DirectionCode.Right;
			else
				result = DirectionCode.Down;
		}
		else if (vectorX < 0 && vectorY < 0)
		{
			if (-vectorY > -vectorX)
				result = DirectionCode.Down;
			else
				result = DirectionCode.Left;
		}
		else if (vectorX < -0 && vectorY > 0)
		{
			if (-vectorX > vectorY)
				result = DirectionCode.Left;
			else
				result = DirectionCode.Up;
		}
		else
		{
			if (myInfo.state == StateCode.Infected)
				result = DirectionCode.Stay;
			else
				result = randomDirection();
		}

		return result;

	}

	double vectorSum(double[][] list, boolean widthMode, int constant)
	{
		/**
		 * ���� ��ġ�� �������� �������� �Ѵ�. ���� ������ ���ؼ� �Ÿ��� �־������� ũ�Ⱑ 1 / n ���� �۾����� �Ѵ�.
		 * 
		 * widthMode : ������ ���� �� ���� ���Ҹ� ��ȯ�Ϸ��� true, ���� ���Ҹ� ��ȯ�Ϸ��� false �����Ѵ�
		 */
		double result = 0;
		int distance;

		for (int i = 0; i < Constants.Classroom_Width; i++) // i ���� j ����
		{ // �� i���� 13���� �ö󰥱� (13 �̸��� ��쿡�� �ؾ� �ϴµ�)
			for (int j = 0; j < Constants.Classroom_Height; j++)
			{

				if (widthMode) // ���� ���� ���͸� ��ȯ�ϴ� ���
					distance = (i - myInfo.position.column);
				else // ������ ���̴� ���� : ������ �Ʒ� �����̹Ƿ�
					distance = -(j - myInfo.position.row);
				if (distance != 0)
				{
					result += constant * distance * Math.abs(Math.pow((1 / distance), 3)) * list[i][j];
				}
			}
		} // ����� ������ �� ����

		return result;
	}

	// �ּ����� ������ġ �޼ҵ�
	// ==========================

	DirectionCode __checkVaild(DirectionCode result)
	{
		/**
		 * _checkVaild �Լ����� ���� �ڵ带 �߻���Ű�� �ʴ� �޼ҵ�. �����ڸ����� ��� ��� �ݴ� �������� ������ �����ϴ�
		 * ���� �����ϱ� ���� ���� �ڵ� �߻� �κ��� �и��߽��ϴ�.
		 * 
		 */
		DirectionCode newResult = result;
		// ������ ����� �ʴ°�?
		if (result == DirectionCode.Up && myInfo.position.row == 0)
		{
			newResult = DirectionCode.Down;

		}
		else if (result == DirectionCode.Down && myInfo.position.row >= Constants.Classroom_Width - 1)
		{
			newResult = DirectionCode.Up;
		}
		else if (result == DirectionCode.Left && myInfo.position.column == 0)
		{
			newResult = DirectionCode.Right;
		}
		else if (result == DirectionCode.Right && myInfo.position.column >= Constants.Classroom_Height - 1)
		{
			newResult = DirectionCode.Left;
		}
		// �����ڰ� ���ڸ��� �ְ��� �ϴ� ���� �ƴѰ�?
		else if (result == DirectionCode.Stay && myInfo.state == StateCode.Survivor)
		{
			if (myInfo.position.row == 0)
			{
				newResult = DirectionCode.Down;
			}
			else
			{
				newResult = DirectionCode.Up;
			}
		}
		else
		{
			newResult = result;
		}
		return newResult;

	}

	DirectionCode _checkVaild(DirectionCode result)
	{
		/**
		 * ������ �ϴ� ������� �ǰ� �ൿ�� �� �ִ� ������ �Ǵ��ϴ� �Լ��Դϴ�.
		 * 
		 * @return newResult : ���� ������ ����� �ʴ� ��� ���� �����, ���� ������ ����ٸ� �����¿츦 �����ؼ�
		 *         ��ȯ.
		 * 
		 */
		DirectionCode newResult = result; // ����� ���� ���ٸ� ���� ��ü�� ����Ű�� �ؼ� ���� �񱳸� �ϱ�
											// ����
		newResult = __checkVaild(result);

		return newResult;
	}

	int pesudoRandom()
	{
		/**
		 * �ִ밪�� �������� �� �� ���� ���� ���� ���� ���� ������. �Ƹ��� 100 ���������� ���������� �𸥴�
		 * 
		 */
		int seed, tmpnum;

		seed = (int) this.gameNumber % 216091 + (int) this.gameNumber % 19937 + (int) this.gameNumber % 101
				+ (int) this.gameNumber % 7;
		seed = (int) (( (double) seed * (double) seed - (double) seed) % seed);
		// �������� ���ϴ� �� ���� ��� �Ҽ��� �ؼ� �ִ��� ������������ ������ ��
		tmpnum = 2 * myInfo.HP + 3 * myInfo.transition_cooldown + 5 * (turnInfo.turnNumber / 3) + (int) seed;
		tmpnum = (int) Math.abs(tmpnum);
		return tmpnum;
	}

	DirectionCode randomDirection()
	{
		/**
		 * ������ ������ ��ȯ�ϱ�� ������ �����ϸ� �� ���� ���� ���� �޼ҵ�
		 * 
		 */
		int i = pesudoRandom() % 4;
		DirectionCode result;
		switch (i)
		{
		case 0:
			result = DirectionCode.Down;
			break;
		case 1:
			result = DirectionCode.Left;
			break;
		case 2:
			result = DirectionCode.Right;
			break;
		case 3:
			result = DirectionCode.Up;
			break;
		default:
			result = DirectionCode.Down;
			break;
		}
		return result;
	}
}
