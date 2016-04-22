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

	// 가중치를 나타내는 설정값
	int infectedConst = 3000_0000;
	int bodyConstWhenSurvive = 1200_0000;
	int survivorConst = 4250_0000;
	int soulConst = 1000_0000;
	int bodyConstWhenInfected = 6750_0000;

	double infectedDecreaseValue = 0.5;
	double infectedHPDecreaseValue = 0.5;
	double survivorDecreaseValue = 0.5;
	double bodyDecreaseValue = 0.5;

	double body2infectedProb = 0.6; // 1턴 남은 시체가 감염체가 될 확률
	double infectedStayProb = 0.3; // 감염체가 제자리에 있을 확률

	int changeToBeDieModeTurn = 32;

	private int infectedHPWhenSpawnConst = 6;
	private int bodyWhenSpawnConst = 2;
	int stayCountBody = 2;

	int updateCount = 2; // 1턴당 정보가 퍼져나가는 횟수

	double stayRange = 80; // 감염체가 나오기 전
	double stayRangeAfterInfected = 3.19E11; // 감염체가 나오고 나면 점수가 올라간다
	
	long pongVectorSizeMax = 6050500000L; // 교실 끝에서 이 크기보다 작은 벡터값이 있다면 반대편으로
											// 튀어나오게 한다
	double survivorRemove = 0.7; //생존자 점수 감소. 처음 생존 끝나면 1로 자동 전환.
	
	// 생각해보니 항상 튀어나오게 하는 것이 더 나은 것 같기도...

	// 현재 상황을 기록하는 변수
	private double survivorCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height]; // 가로세로
	private double infectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
	private double bodyCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
	private double infectedSumHP[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
	private double body2infectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];

	// 사소한 상황 기록 변수
	int firstDieTurn; // 처음으로 죽은 턴 기록
	boolean infectedOpen = false; // 감염체가 된 적이 있는지 없는지 기
	boolean isToBeDieModeTurnChanged = false;
	int randomInftectedTurnCount = 0;

	// 진행할 모드를 나타내는 변수 + 초기화
	boolean surviveMode = true;
	boolean toBeDieMode = false;

	public Player_PreferName(int ID)
	{

		super(ID, "선호하는이름!");

		this.trigger_acceptDirectInfection = false; // 점수를 얻기 위한 작전
	}

	//
	// 현재 상태에 따라 행동해야 할 것을 정의한 메소드
	// =======================================================

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		double vectorX = 0, vectorY = 0;
		boolean positive = true;

		this.updateInfo();

		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		

		
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
		else if (surviveMode && turnInfo.turnNumber <= changeToBeDieModeTurn) // 등장!!
		{
			survivorRemove = 1;
			// 정보를 제공하기 위해 생존자에게 가까이 간다
			vectorX = vectorSum(survivorCount, true, survivorConst);
			vectorY = vectorSum(survivorCount, false, survivorConst);
			// 알려진 감염체에서 도망!
			vectorX -= vectorSum(infectedSumHP, true, infectedConst);
			vectorY -= vectorSum(infectedSumHP, false, infectedConst);
			// 알려진 시체로부터 도망!
			vectorX -= vectorSum(bodyCount, true, bodyConstWhenSurvive);
			vectorY -= vectorSum(bodyCount, false, bodyConstWhenSurvive);

			positive = true;
			result = nextDirection(vectorX, vectorY, positive); // 기운이 약한 곳으로
			result = checkNoInfected(result);
		} // 죽는 작전 폐지
		else
		{
			survivorRemove = 1;
			vectorX = vectorSum(infectedSumHP, true, infectedConst);
			vectorY = vectorSum(infectedSumHP, false, infectedConst);
			vectorX += vectorSum(bodyCount, true, bodyConstWhenSurvive);
			vectorY += vectorSum(bodyCount, false, bodyConstWhenSurvive);
			positive = false;
			result = nextDirection(vectorX, vectorY, positive); // 기운이 센 곳으로
			result = checkNoInfected(result);
		}

		// 후처리
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
						&& result == DirectionCode.Down) // 위아래로 움직이면서 위아래에 감염체가
															// 있다면
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
						&& result == DirectionCode.Right) // 좌우로 움직이면서 좌우에 감염체가
															// 있다면
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
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
		this.updateInfo(); // 시체를 이용한 정보를 업데이트
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

		// 조건 확인을 위한 정보 수집
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

			// 생존자와 시체를 모두 고려한다
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
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
			 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
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
		// 그럴 리는 없지만 감염자가 한 명도 없다면 임의 지점에

		int maxHP = 0;
		int tmpHP;

		this.updateInfo();


		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		// 만일 바로 시체가 되어야 한다면 감염체 많은 곳으로 떨어진다. 위치 + 상하좌우 HP 합이 가장 큰 곳으로 부활하면 될 것
		// 같다

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

	// 정보를 업데이트해 주는 메소드
	// =======================================

	void updateInfo()
	{
		/**
		 * 각 판을 나타내는 필드를 업데이트해 주는 함수. 이 메소드의 동작이 끝나기 전까지 기존 데이터 변동 없음.
		 * 
		 * 한 턴에서 이 메소드는 하나만 호출되어야 한다.
		 */

		// 새롭게 정보가 될 배열을 만든다
		double tmpSurvivorCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpInfectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpBodyCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpInfectedSumHP[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];
		double tmpBody2infectedCount[][] = new double[Constants.Classroom_Width][Constants.Classroom_Height];

		// 임시 저장 배열
		int tmp;

		// 새로 만든 배열 초기화
		for (int i = 0; i < Constants.Classroom_Height; i++) // 가로 : i / 세로 : j
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

		// 현재 정보를 넣어서 예측 모형에 집어넣는다

		for (int i = 0; i < Constants.Classroom_Height; i++) // 가로 : i / 세로 : j
		{
			for (int j = 0; j < Constants.Classroom_Width; j++)
			{
				// 생존자 정보를 집어넣는다

				tmp = survivorConst * cells[i][j].CountIf_Players(player -> player.state == StateCode.Survivor);
				tmpSurvivorCount[i][j] += tmp;
				// 감염체 정보(개수, HP합)를 집어넣는다
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

		// 기존의 값이 새로운 배열로 퍼져나가도록 한다
		for (int k = 0; k < updateCount; k++)
		{
			for (int i = 0; i < Constants.Classroom_Height; i++) // 가로 : i / 세로
																	// // : j
			{
				for (int j = 0; j < Constants.Classroom_Width; j++)
				{
					// 기존의 값이 새로운 배열로 퍼져나간다.
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

		// 확실한 정보를 집어넣는다

		for (int i = 0; i < Constants.Classroom_Height; i++) // 가로 : i / 세로 : j
		{
			for (int j = 0; j < Constants.Classroom_Width; j++)
			{
				// 생존자는 제 자리에 있을 리가 없다
				tmp = survivorConst * cells[i][j].CountIf_Players(player -> player.state == StateCode.Survivor);


				
				tmpSurvivorCount[i][j] -= tmp * survivorRemove;
				// 감염체가 제 자리에 있을 값만 남기고 다 지운다
				tmpInfectedCount[i][j] += infectedConst
						* cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
				for (PlayerInfo tmpPlayer : cells[i][j].Select_Players(player -> player.state == StateCode.Infected))
				{
					tmpInfectedSumHP[i][j] -= tmpPlayer.HP * (1 - infectedStayProb);
				}
				// 시체 남은 턴이 1이고 상하좌우에 감염체가 없다면 다음번에 감염체가 될 가능성.

				// TODO : 상하좌우에 감염체가 있다면 다른 상수를 적용하도록 수정.
				//
			}
		}

		// 마지막으로 새로 만든 배열을 제자리에 넣는다.
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
		 * 새로운 배열에 가중치를 곱해서 상하좌우에 값을 넣는 매소드.
		 * 
		 * 예외 처리를 하는 것이 복잡하고 공통된 것을 여러번 쓰게 되므로 매소드를 만듦.
		 * 
		 * oldArr : 기존의 상태를 담고 있는 배열 newArr : 새로운 상태를 담을 배열. 모든 배열의 원소들이 0으로
		 * 초기화되어 있다는 가정 하에서 진행된다. c : 값을 감소시킬 상수 x : 가로 좌표 y : 세로 좌표
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
		tmpResult = __checkVaild(result); // 모서리에 있었다면
		if (tmpResult != result) // 원래 결과로 움직일 수 없는 경우
		{
			if (result == DirectionCode.Up || result == DirectionCode.Down)
			{ // 위아래로 움직이면 경계를 넘는 경우 위아래 성분을 제외하고 판단
				newResult = nextDirection(vectorX, 0, positive);
			}
			else if (result == DirectionCode.Left || result == DirectionCode.Right)
			{
				newResult = nextDirection(0, vectorY, positive);
			}
		}
		
		return newResult;
	}

	// 다른 메소드를 보조하는 메소드
	// ========================================

	DirectionCode nextDirection(double vectorX, double vectorY, boolean positive)
	{
		DirectionCode result = null; // 오류 확인을 위해
		// positive : 양의 방향 / 음의 방향 선택. 양의 방향이면 true.
		double stayRange;
		double vectorX1 = vectorX, vectorY1 = vectorY;

		if (!positive) // 음의 방향은 벡터를 180도 반전시킨 것이다
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

		// 위 방향을 기준으로 해서 시계 방향으로 돌아가며 한다
		if (vectorX > 0 && vectorY > 0) // 1사분면
		{
			if (vectorX < vectorY)
				result = DirectionCode.Up;
			else
				result = DirectionCode.Right;
		}
		else if (vectorX > 0 && vectorY < 0) // 2사분면
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
		 * 현재 위치를 기준으로 벡터합을 한다. 게임 진행을 위해서 거리가 멀어질수록 크기가 1 / n 으로 작아지게 한다.
		 * 
		 * widthMode : 벡터합 원소 중 가로 원소만 반환하려면 true, 세로 원소만 반환하려면 false 지정한다
		 */
		double result = 0;
		int distance;

		for (int i = 0; i < Constants.Classroom_Width; i++) // i 가로 j 세로
		{ // 왜 i값이 13까지 올라갈까 (13 미만인 경우에만 해야 하는데)
			for (int j = 0; j < Constants.Classroom_Height; j++)
			{

				if (widthMode) // 가로 방향 벡터만 반환하는 경우
					distance = (i - myInfo.position.column);
				else // 음수를 붙이는 이유 : 음수가 아래 방향이므로
					distance = -(j - myInfo.position.row);
				if (distance != 0)
				{
					result += constant * distance * Math.abs(Math.pow((1 / distance), 3)) * list[i][j];
				}
			}
		} // 양수면 오른쪽 위 방향

		return result;
	}

	// 최소한의 안전장치 메소드
	// ==========================

	DirectionCode __checkVaild(DirectionCode result)
	{
		/**
		 * _checkVaild 함수에서 오류 코드를 발생시키지 않는 메소드. 가장자리에서 어느 경우 반대 방향으로 과감히 돌진하는
		 * 것을 구현하기 위해 오류 코드 발생 부분을 분리했습니다.
		 * 
		 */
		DirectionCode newResult = result;
		// 범위를 벗어나지 않는가?
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
		// 생존자가 제자리에 있고자 하는 것은 아닌가?
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
		 * 내고자 하는 결과값이 옳게 행동할 수 있는 것인지 판단하는 함수입니다.
		 * 
		 * @return newResult : 교실 범위를 벗어나지 않는 경우 원래 결과를, 교실 범위를 벗어난다면 상하좌우를 반전해서
		 *         반환.
		 * 
		 */
		DirectionCode newResult = result; // 변경된 것이 없다면 같은 객체를 가리키게 해서 쉽게 비교를 하기
											// 위함
		newResult = __checkVaild(result);

		return newResult;
	}

	int pesudoRandom()
	{
		/**
		 * 최대값이 무엇인지 알 수 없고 믿을 수도 없는 수열 생성기. 아마도 100 정도까지는 쓸만할지도 모른다
		 * 
		 */
		int seed, tmpnum;

		seed = (int) this.gameNumber % 216091 + (int) this.gameNumber % 19937 + (int) this.gameNumber % 101
				+ (int) this.gameNumber % 7;
		seed = (int) (( (double) seed * (double) seed - (double) seed) % seed);
		// 나머지를 구하는 두 수는 모두 소수로 해서 최대한 무작위적으로 나오게 함
		tmpnum = 2 * myInfo.HP + 3 * myInfo.transition_cooldown + 5 * (turnInfo.turnNumber / 3) + (int) seed;
		tmpnum = (int) Math.abs(tmpnum);
		return tmpnum;
	}

	DirectionCode randomDirection()
	{
		/**
		 * 랜덤한 방향을 반환하기는 하지만 웬만하면 안 쓰는 것이 좋은 메소드
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
