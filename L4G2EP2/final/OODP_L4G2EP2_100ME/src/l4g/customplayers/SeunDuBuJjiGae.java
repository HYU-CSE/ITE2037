package l4g.customplayers;

import java.security.Guard;

import javax.xml.ws.handler.MessageContext.Scope;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class SeunDuBuJjiGae extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public SeunDuBuJjiGae ( int ID )
	{

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "아조씨랑비밀친구할래?");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니
		// 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;

		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어
		// 보고 돌아옵시다.

	}

	/*
	 * TODO#5 이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요. 당연히 한 방에 될 리
	 * 없으니, 중간중간 코드를 백업해 두는 것이 좋으며, 코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다. 그러니
	 * 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면, '코드에 노력이란게 묻어 날
	 * 수도 있구나'라는 생각이 절로 들게 될 것입니다.
	 */

	boolean ValidPos ( int row , int column )
	{
		boolean rowTest = (row < Constants.Classroom_Height && row >= 0);
		boolean columnTest = (column < Constants.Classroom_Width && column >= 0);

		return (rowTest && columnTest);
	}

	int[] CheckFourDirection ( StateCode wannaKnow , int myRow , int myColumn )
	{
		/**
		 ** 리턴값 {동,서,남,북} 4방위 특정 상대 수 * 거리 가중치 카운트
		 */

		// 근접한 대상
		int eastC = 0;
		int westC = 0;
		int southC = 0;
		int northC = 0;

		// 전체 대상
		int east = 0;
		int west = 0;
		int south = 0;
		int north = 0;
		final int notValidValue = 100;

		for ( int R = 0; R < 5; R++ )
		{
			for ( int C = -R; C <= R; C++ )
			{
				if ( ValidPos(myRow + R, myColumn + C) )
				{
					south += (8 - Math.abs(R) + Math.abs(C)) * cells[myRow + R][myColumn + C]
							.CountIf_Players(target -> target.state == wannaKnow);
					if ( Math.abs(R) + Math.abs(C) <= 2 )
						southC += (3 - Math.abs(R) + Math.abs(C)) * cells[myRow + R][myColumn + C]
								.CountIf_Players(target -> target.state == wannaKnow);
				} else
					south += (8 - Math.abs(R) + Math.abs(C)) * notValidValue;
			}
		}

		for ( int R = 0; R < 5; R++ )
		{
			for ( int C = -R; C <= R; C++ )
			{
				if ( ValidPos(myRow - R, myColumn + C) )
				{
					north += (8 - Math.abs(R) + Math.abs(C)) * cells[myRow - R][myColumn + C]
							.CountIf_Players(target -> target.state == wannaKnow);
					if ( Math.abs(R) + Math.abs(C) <= 2 )
						northC += (3 - Math.abs(R) + Math.abs(C)) * cells[myRow - R][myColumn + C]
								.CountIf_Players(target -> target.state == wannaKnow);
				} else
					north += (8 - Math.abs(R) + Math.abs(C)) * notValidValue;
			}

		}

		for ( int C = 0; C < 5; C++ )
		{
			for ( int R = -C; R <= C; R++ )
			{
				if ( ValidPos(myRow + R, myColumn + C) )
				{
					east += (8 - Math.abs(R) + Math.abs(C)) * cells[myRow + R][myColumn + C]
							.CountIf_Players(target -> target.state == wannaKnow);
					if ( Math.abs(R) + Math.abs(C) <= 2 )
						eastC += (3 - Math.abs(R) + Math.abs(C)) * cells[myRow + R][myColumn + C]
								.CountIf_Players(target -> target.state == wannaKnow);
				} else
					east += (8 - Math.abs(R) + Math.abs(C)) * notValidValue;
			}
		}

		for ( int C = 0; C < 5; C++ )
		{
			for ( int R = -C; R <= C; R++ )
			{
				if ( ValidPos(myRow + R, myColumn - C) )
				{
					west += (8 - Math.abs(R) + Math.abs(C)) * cells[myRow + R][myColumn - C]
							.CountIf_Players(target -> target.state == wannaKnow);
					if ( Math.abs(R) + Math.abs(C) <= 2 )
						westC += (3 - Math.abs(R) + Math.abs(C)) * cells[myRow + R][myColumn - C]
								.CountIf_Players(target -> target.state == wannaKnow);
				} else
					west += (8 - Math.abs(R) + Math.abs(C)) * notValidValue;
			}
		}

		/*
		 * if(west == Math.min(west,Math.min(east,Math.min(north,south)))) {
		 * choice = DirectionCode.Left; maxCount++; } if(east ==
		 * Math.min(west,Math.min(east,Math.min(north,south)))) { choice =
		 * DirectionCode.Right; maxCount++; } if(south ==
		 * Math.min(west,Math.min(east,Math.min(north,south)))) { choice =
		 * DirectionCode.Down; maxCount++; } if(north ==
		 * Math.min(west,Math.min(east,Math.min(north,south)))) { choice =
		 * DirectionCode.Up; maxCount++; }
		 */

		int[] toReturn = { east, west, north, south, eastC, westC, northC, southC };

		return toReturn;
	}

	// TODO 상수 변수들은 여기있다

	// state용
	static final int goRight = 0;
	static final int goLeft = 1;
	static final int goUp = 2;
	static final int goDown = 3;
	static final int stay = 4;

	// 우디르급 태세전환용
	static final int surviveMax = 80;
	static final int dieOkLeast = 60;

	int state = goRight;

	int DistanceWith ( Player target )
	{
		/**
		 * 대상과의 거리계산
		 * 
		 */

		int columnDist = Math.abs(this.myInfo.position.column - target.myInfo.position.column);
		int rowDist = Math.abs(this.myInfo.position.row - target.myInfo.position.row);
		// cells[0][0].Count_Actions();
		return columnDist + rowDist;
	}

	int[] SameVal ( int[] t )
	{
		/**
		 * 4방위에 적이 동일하게 분포할 시 사용
		 * 
		 */
		if ( t[0] == t[1] && t[1] == t[2] && t[2] == t[3] )
		{
			int[] toReturn = { 0, 1, 2, 3 };
			return toReturn;
		} else if ( t[0] == t[1] && t[1] == t[2] )
		{
			int[] toReturn = { 0, 1, 2 };
			return toReturn;
		} else if ( t[0] == t[1] && t[1] == t[3] )
		{
			int[] toReturn = { 0, 1, 3 };
			return toReturn;
		} else if ( t[3] == t[1] && t[1] == t[2] )
		{
			int[] toReturn = { 1, 2, 3 };
			return toReturn;
		} else if ( t[0] == t[3] && t[3] == t[2] )
		{
			int[] toReturn = { 0, 2, 3 };
			return toReturn;
		} else if ( t[0] == t[1] )
		{
			int[] toReturn = { 0, 1 };
			return toReturn;
		} else if ( t[0] == t[2] )
		{
			int[] toReturn = { 0, 2 };
			return toReturn;
		} else if ( t[0] == t[3] )
		{
			int[] toReturn = { 0, 3 };
			return toReturn;
		} else if ( t[1] == t[2] )
		{
			int[] toReturn = { 1, 2 };
			return toReturn;
		} else if ( t[1] == t[3] )
		{
			int[] toReturn = { 1, 3 };
			return toReturn;
		} else if ( t[2] == t[3] )
		{
			int[] toReturn = { 2, 3 };
			return toReturn;
		}

		else
		{
			int[] toReturn = { 10 };
			return toReturn;
		}
	}

	int[] calBlank ( int myRow , int myColumn )
	{
		/**
		 * 여유공간 계산 메소드
		 * 
		 */
		int north = 0;
		int south = 0;
		int east = 0;
		int west = 0;
		for ( int R = 0; myColumn + R <= Constants.Classroom_Height; R++ )
		{
			for ( int C = -R; C <= R; C++ )
			{
				if ( ValidPos(myRow + R, myColumn + C) )
					south++;
			}
		}
		for ( int R = 0; myColumn - R >= 0; R++ )
		{
			for ( int C = -R; C <= R; C++ )
			{
				if ( ValidPos(myRow + R, myColumn + C) )
					north++;
			}
		}
		for ( int C = 0; myColumn + C <= Constants.Classroom_Width; C++ )
		{
			for ( int R = -C; R <= C; R++ )
			{
				if ( ValidPos(myRow + R, myColumn + C) )
					east++;
			}
		}
		for ( int C = 0; myColumn - C >= 0; C++ )
		{
			for ( int R = -C; R <= C; R++ )
			{
				if ( ValidPos(myRow + R, myColumn + C) )
					west++;
			}
		}

		int[] toResult = { east, west, north, south };

		return toResult;

	}

	int CenterDirection ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;

		int rDirection = 6 - myRow;
		int cDirection = 6 - myColumn;

		if ( Math.abs(rDirection) >= Math.abs(cDirection) )
		{
			if ( rDirection > 0 )
				return goRight;
			else
				return goLeft;
		} else
		{
			if ( rDirection > 0 )
				return goDown;
			else
				return goUp;
		}

	}

	boolean HostileNext ( int myRow , int myColumn )
	{
		int right = 0;
		int left = 0;
		int up = 0;
		int down = 0;

		if ( ValidPos(myRow, myColumn + 1)
				&& cells[myRow][myColumn + 1].CountIf_Players(target -> target.state == StateCode.Infected) == 0 )
		{
			right = 1;
		}
		if ( ValidPos(myRow, myColumn - 1)
				&& cells[myRow][myColumn - 1].CountIf_Players(target -> target.state == StateCode.Infected) == 0 )
		{
			left = 1;
		}
		if ( ValidPos(myRow + 1, myColumn)
				&& cells[myRow + 1][myColumn].CountIf_Players(target -> target.state == StateCode.Infected) == 0 )
		{
			up = 1;
		}
		if ( ValidPos(myRow - 1, myColumn)
				&& cells[myRow - 1][myColumn].CountIf_Players(target -> target.state == StateCode.Infected) == 0 )
		{
			down = 1;
		}

		return (right + left + up + down > 2);
	}

	void before11 ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;
		int[] ewnsH = CheckFourDirection(StateCode.Survivor, myRow, myColumn);
		int[] spaces = calBlank(myRow, myColumn);
		int[] EWNStotal = new int[4];

		for ( int i = 4; i < 8; i++ )
		{
			EWNStotal[i - 4] = ewnsH[i] - spaces[i - 4] * 2;
		}
		int check = Math.min(EWNStotal[0], Math.min(EWNStotal[1], Math.min(EWNStotal[2], EWNStotal[3])));

		if ( check == EWNStotal[0] )
		{
			if ( ValidPos(myRow, myColumn + 1) )
				state = goRight;
			else
				state = goLeft;
		}
		if ( check == EWNStotal[1] )
		{
			if ( ValidPos(myRow, myColumn - 1) )
				state = goLeft;
			else
				state = goRight;
		}

		if ( check == EWNStotal[2] )
		{
			if ( ValidPos(myRow - 1, myColumn) )
				state = goUp;
			else
				state = goDown;
		}
		if ( check == EWNStotal[3] )
		{
			if ( ValidPos(myRow + 1, myColumn) )
				state = goDown;
			else
				state = goUp;
		}

	}

	void knowdirection ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;

		int[] ewnsH = CheckFourDirection(StateCode.Infected, myRow, myColumn);

		int minVal = Math.min(ewnsH[0], Math.min(ewnsH[1], Math.min(ewnsH[2], ewnsH[3])));

		if ( minVal == ewnsH[0] )
			state = goRight;
		else if ( minVal == ewnsH[1] )
			state = goLeft;
		else if ( minVal == ewnsH[2] )
			state = goUp;
		else
			state = goDown;
	}

	void notknowdirection ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;
		int[] ewnsH = CheckFourDirection(StateCode.Infected, myRow, myColumn);
		int[] spaces = calBlank(myRow, myColumn);
		int[] EWNStotal = new int[4];
		int[] ewnsF = CheckFourDirection(StateCode.Survivor, myRow, myColumn);
		int getMAX = 0;
		int decision = 0;

		for ( int i = 0; i < 4; i++ )
		{
			EWNStotal[i] = spaces[i] * 2 + ewnsH[i] + ewnsF[i];

			if ( getMAX < EWNStotal[i] )
			{
				getMAX = EWNStotal[i];
				decision = i;
			}
		}
		switch ( decision )
		{
		case 0:
			state = goRight;
			break;
		case 1:
			state = goLeft;
			break;
		case 2:
			state = goUp;
			break;
		case 3:
			state = goDown;
			break;
		default:

		}
	}

	void gonnadie ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;
		int[] ewnsH = CheckFourDirection(StateCode.Infected, myRow, myColumn);
		int[] ewnsF = CheckFourDirection(StateCode.Survivor, myRow, myColumn);

		int[] spaces = calBlank(myRow, myColumn);
		int[] EWNStotal = new int[4];
		int getMAX = 0;
		int decision = 0;
		for ( int i = 0; i < 4; i++ )
		{
			EWNStotal[i] = spaces[i] * 2 + ewnsH[i] + ewnsF[i];

			if ( getMAX < EWNStotal[i] )
			{
				getMAX = EWNStotal[i];
				decision = i;
			}
		}

		switch ( decision )
		{
		case 0:
			state = goRight;
			break;
		case 1:
			state = goLeft;
			break;
		case 2:
			state = goUp;
			break;
		case 3:
			state = goDown;
			break;
		default:

		}
	}

	void gonnalive ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;

		if ( cells[myRow][myColumn].CountIf_Players(target -> target.state == StateCode.Corpse) == 0 )
		{
			// 발밑에 시체가 없다면
			state = stay;
		} else
		{
			int[] EWNStotal = new int[4];
			int getMAX = 0;
			int decision = 0;
			int[] ewnsS = CheckFourDirection(StateCode.Survivor, myRow, myColumn);
			int[] ewnsC = CheckFourDirection(StateCode.Corpse, myRow, myColumn);
			int[] ewnsH = new int[4];
			for ( int i = 0; i < 4; i++ )
			{
				ewnsH[i] = ewnsS[i] + ewnsC[i + 4];

				EWNStotal[i] = ewnsH[i];

				if ( getMAX < EWNStotal[i] )
				{
					getMAX = EWNStotal[i];
					decision = i;
				}
			}
			switch ( decision )
			{
			case 0:
				state = goRight;
				break;
			case 1:
				state = goLeft;
				break;
			case 2:
				state = goUp;
				break;
			case 3:
				state = goDown;
				break;
			default:

			}
		}
	}

	void UpdateStateSurvivor ()
	{

		/*
		 * TODO 밑에 샘플코드는 다 없애 버리고 주변 정보를 받아들여서 4방위에 대해 정보를 만들고 계산
		 * 
		 */
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;

		if ( turnInfo.turnNumber < 11 )
		{
			// 11턴 이전 무조건
			before11();
		}

		else if ( turnInfo.turnNumber < surviveMax )
		// 최대 생존자 파밍 턴 이하시
		{
			int[] ewnsH = CheckFourDirection(StateCode.Infected, myRow, myColumn);
			int[] ewnsF = CheckFourDirection(StateCode.Survivor, myRow, myColumn);
			int minVal = Math.min(ewnsH[0], Math.min(ewnsH[1], Math.min(ewnsH[2], ewnsH[3])));
			if ( ewnsH[4] + ewnsH[5] + ewnsH[6] + ewnsH[7] != 0 )
			{
				// 11턴 이후 생존 및 최대 생존자 파밍 턴 이하시
				// 직접 시야내 적이 없으면

				if ( SameVal(ewnsH)[0] == 10 )
				{
					// 11턴 이후 생존 및 최대 생존자 파밍 턴 이하시
					// 직접 시야내 적이 없으면
					// 간접시야를 포함한 시야에서 갈 방향이 뚜렷하면
					knowdirection();

				} else
				{
					// 11턴 이후 생존 및 최대 생존자 파밍 턴 이하시
					// 직접 시야내 적이 없으면
					// 간접시야가 포함해서 갈 방향이 뚜렷하지 않으면
					notknowdirection();

					// 다 필요없고 나 혼자면
					if ( ewnsH[0] + ewnsH[1] + ewnsH[2] + ewnsH[3] + ewnsF[0] + ewnsF[1] + ewnsF[2]
							+ ewnsF[3] == 0 )
						state = CenterDirection();

				}
			} else
			{
				// 11턴 이후 최대 생존 파밍 턴 이후
				// 직접 시야 내 적이 있으면
				knowdirection();

			}
		} else
		{
			gonnadie();
			/*
			 * // 생존 포기 상태 int[] EWNStotal = new int[4]; int getMAX = 0; int
			 * decision = 0; int[] ewnsH =
			 * CheckFourDirection(StateCode.Infected, myRow, myColumn); for (
			 * int i = 0; i < 4; i++ ) { EWNStotal[i] = ewnsH[i];
			 * 
			 * if ( getMAX < EWNStotal[i] ) { getMAX = EWNStotal[i]; decision
			 * = i; } } switch ( decision ) { case 0: state = goRight; break;
			 * case 1: state = goLeft; break; case 2: state = goUp; break;
			 * case 3: state = goDown; break; default:
			 * 
			 * }
			 */
		}
		// 움직이려는 방향이 유효한지 판단

		switch ( state )
		{
		case goDown:
			if ( !ValidPos(myRow + 1, myColumn) )
				state = goUp;
		case goUp:
			if ( !ValidPos(myRow - 1, myColumn) )
				state = goDown;
		case goRight:
			if ( !ValidPos(myRow, myColumn + 1) )
				state = goLeft;
		case goLeft:
			if ( !ValidPos(myRow, myColumn - 1) )
				state = goRight;
		}
	}

	void UpdateStateInfected ()
	{
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;
		int[] ewnsF = CheckFourDirection(StateCode.Corpse, myRow, myColumn);
		int CloseCorpse = ewnsF[4]+ewnsF[5]+ewnsF[6]+ewnsF[7];
		if ( myInfo.state == StateCode.Infected )
		{
			if ( InfectedTurn < dieOkLeast )
			{
				// 빠르게 생존자로 복귀
				gonnalive();
			}

			else
			{
				if (( turnInfo.turnNumber - InfectedTurn == 10 && turnInfo.turnNumber < 110) || CloseCorpse  == 0 )
				{
					// 기도하고 시체점수를 먹으러 가자
					LetsPray();
				}
				else
				{
					//떨어지는 시체나 받아먹자
					state= stay;
				}
			}
		}

		// 움직이려는 방향이 유효한지 판단

		switch ( state )
		{
		case goDown:
			if ( !ValidPos(myRow + 1, myColumn) )
				state = goUp;
		case goUp:
			if ( !ValidPos(myRow - 1, myColumn) )
				state = goDown;
		case goRight:
			if ( !ValidPos(myRow, myColumn + 1) )
				state = goLeft;
		case goLeft:
			if ( !ValidPos(myRow, myColumn - 1) )
				state = goRight;
		}

	}

	@Override
	public DirectionCode Survivor_Move ()
	{
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		DirectionCode result = DirectionCode.Right;

		UpdateStateSurvivor();

		switch ( state )
		{
		case goRight:
			result = DirectionCode.Right;
			break;
		case goLeft:
			result = DirectionCode.Left;
			break;
		case goUp:
			result = DirectionCode.Up;
			break;
		case goDown:
			result = DirectionCode.Down;
			break;
		default:

		}

		return result;
	}

	int InfectedTurn = 0;

	void LetsPray ()
	{
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		if ( cells[row][column].CountIf_Players(target -> target.state == StateCode.Corpse) > 0 )
		{
			// 발밑에 시체가 있으면
			gonnalive();
		} else
		{
			state = stay;
		}
	}

	@Override
	public void Corpse_Stay ()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
		InfectedTurn = turnInfo.turnNumber + 1;
	}

	@Override
	public DirectionCode Infected_Move ()
	{
		UpdateStateInfected();
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		DirectionCode result = DirectionCode.Stay;
		switch ( state )
		{
		case goRight:
			result = DirectionCode.Right;
			break;
		case goLeft:
			result = DirectionCode.Left;
			break;
		case goUp:
			result = DirectionCode.Up;
			break;
		case goDown:
			result = DirectionCode.Down;
			break;
		default:

		}
		return result;
	}

	@Override
	public void Soul_Stay ()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은
			 * 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
			 */
		}
	}

	@Override
	public Point Soul_Spawn ()
	{
		int result_row = 3;
		int result_column = 3;
		if ( turnInfo.turnNumber < 11 )
		{

		} else if ( turnInfo.turnNumber >= dieOkLeast )
		{
			// 더 이상 생존할 필요가 없으면
			int[][] Map = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int MapMax = 0;

			for ( int[] arr : Map )
			{
				for ( int i : arr )
				{
					i = 0;
				}
			}

			for ( int r = 0; r < Constants.Classroom_Height; r++ )
			{
				for ( int c = 0; c < Constants.Classroom_Width; c++ )
				{

					if ( r + 1 < Constants.Classroom_Height )
						Map[r][c] += cells[r + 1][c]
								.CountIf_Players(target -> target.state == StateCode.Infected);
					if ( r - 1 >= 0 )
						Map[r][c] += cells[r - 1][c]
								.CountIf_Players(target -> target.state == StateCode.Infected);
					if ( c + 1 < Constants.Classroom_Width )
						Map[r][c] += cells[r][c + 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
					if ( c - 1 >= 0 )
						Map[r][c] += cells[r][c - 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
					Map[r][c] += 10 * cells[r][c].CountIf_Players(target -> target.state == StateCode.Infected);

					if ( MapMax < Map[r][c] )
					{
						MapMax = Map[r][c];
						result_row = r;
						result_column = c;
					}
				}
			}

		} else
		{
			// 아직은 생존 점수가 더 필요하면
			int[][] Map = new int[Constants.Classroom_Height][Constants.Classroom_Width];
			int MapMin = 500;
			for ( int[] arr : Map )
			{
				for ( int i : arr )
				{
					i = 1000;
				}
			}

			for ( int r = 2; r < Constants.Classroom_Height - 3; r++ )
			{
				for ( int c = 2; c < Constants.Classroom_Width - 3; c++ )
				{

					if ( cells[r][c].CountIf_Players(target -> target.state == StateCode.Infected) == 0
							&& HostileNext(r, c) )
					{
						Map[r][c] += cells[r + 1][c]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r + 2][c]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] += cells[r - 1][c]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r - 2][c]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] += cells[r][c + 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r][c + 2]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] += cells[r][c - 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r][c - 2]
								.CountIf_Players(target -> target.state == StateCode.Infected);

						Map[r][c] -= cells[r + 1][c + 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r - 1][c + 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r + 1][c - 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);
						Map[r][c] -= cells[r - 1][c - 1]
								.CountIf_Players(target -> target.state == StateCode.Infected);

					}
					if ( MapMin > Map[r][c] )
					{
						MapMin = Map[r][c];
						result_row = r;
						result_column = c;
					} else if ( MapMin == Map[r][c] )
					{
						if ( Math.abs(6 - Math.abs(r)) * (6 - Math.abs(c)) < Math
								.abs((6 - Math.abs(result_row)) * (6 - Math.abs(result_column))) )
						{
							MapMin = Map[r][c];
							result_row = r;
							result_column = c;
						}
					}
				}
			}
			// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
		}
		return new Point(result_row, result_column);
	}
}
