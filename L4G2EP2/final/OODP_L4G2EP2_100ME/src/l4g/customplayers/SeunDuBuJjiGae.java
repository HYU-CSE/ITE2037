package l4g.customplayers;

import java.security.Guard;

import javax.xml.ws.handler.MessageContext.Scope;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class SeunDuBuJjiGae extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public SeunDuBuJjiGae ( int ID )
	{

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "�����������ģ���ҷ�?");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫��
		// �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;

		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о�
		// ���� ���ƿɽô�.

	}

	/*
	 * TODO#5 ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���. �翬�� �� �濡 �� ��
	 * ������, �߰��߰� �ڵ带 ����� �δ� ���� ������, �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�. �׷���
	 * �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����, '�ڵ忡 ����̶��� ���� ��
	 * ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
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
		 ** ���ϰ� {��,��,��,��} 4���� Ư�� ��� �� * �Ÿ� ����ġ ī��Ʈ
		 */

		// ������ ���
		int eastC = 0;
		int westC = 0;
		int southC = 0;
		int northC = 0;

		// ��ü ���
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

	// TODO ��� �������� �����ִ�

	// state��
	static final int goRight = 0;
	static final int goLeft = 1;
	static final int goUp = 2;
	static final int goDown = 3;
	static final int stay = 4;

	// ��𸣱� �¼���ȯ��
	static final int surviveMax = 80;
	static final int dieOkLeast = 60;

	int state = goRight;

	int DistanceWith ( Player target )
	{
		/**
		 * ������ �Ÿ����
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
		 * 4������ ���� �����ϰ� ������ �� ���
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
		 * �������� ��� �޼ҵ�
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
			// �߹ؿ� ��ü�� ���ٸ�
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
		 * TODO �ؿ� �����ڵ�� �� ���� ������ �ֺ� ������ �޾Ƶ鿩�� 4������ ���� ������ ����� ���
		 * 
		 */
		int myRow = this.myInfo.position.row;
		int myColumn = this.myInfo.position.column;

		if ( turnInfo.turnNumber < 11 )
		{
			// 11�� ���� ������
			before11();
		}

		else if ( turnInfo.turnNumber < surviveMax )
		// �ִ� ������ �Ĺ� �� ���Ͻ�
		{
			int[] ewnsH = CheckFourDirection(StateCode.Infected, myRow, myColumn);
			int[] ewnsF = CheckFourDirection(StateCode.Survivor, myRow, myColumn);
			int minVal = Math.min(ewnsH[0], Math.min(ewnsH[1], Math.min(ewnsH[2], ewnsH[3])));
			if ( ewnsH[4] + ewnsH[5] + ewnsH[6] + ewnsH[7] != 0 )
			{
				// 11�� ���� ���� �� �ִ� ������ �Ĺ� �� ���Ͻ�
				// ���� �þ߳� ���� ������

				if ( SameVal(ewnsH)[0] == 10 )
				{
					// 11�� ���� ���� �� �ִ� ������ �Ĺ� �� ���Ͻ�
					// ���� �þ߳� ���� ������
					// �����þ߸� ������ �þ߿��� �� ������ �ѷ��ϸ�
					knowdirection();

				} else
				{
					// 11�� ���� ���� �� �ִ� ������ �Ĺ� �� ���Ͻ�
					// ���� �þ߳� ���� ������
					// �����þ߰� �����ؼ� �� ������ �ѷ����� ������
					notknowdirection();

					// �� �ʿ���� �� ȥ�ڸ�
					if ( ewnsH[0] + ewnsH[1] + ewnsH[2] + ewnsH[3] + ewnsF[0] + ewnsF[1] + ewnsF[2]
							+ ewnsF[3] == 0 )
						state = CenterDirection();

				}
			} else
			{
				// 11�� ���� �ִ� ���� �Ĺ� �� ����
				// ���� �þ� �� ���� ������
				knowdirection();

			}
		} else
		{
			gonnadie();
			/*
			 * // ���� ���� ���� int[] EWNStotal = new int[4]; int getMAX = 0; int
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
		// �����̷��� ������ ��ȿ���� �Ǵ�

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
				// ������ �����ڷ� ����
				gonnalive();
			}

			else
			{
				if (( turnInfo.turnNumber - InfectedTurn == 10 && turnInfo.turnNumber < 110) || CloseCorpse  == 0 )
				{
					// �⵵�ϰ� ��ü������ ������ ����
					LetsPray();
				}
				else
				{
					//�������� ��ü�� �޾Ƹ���
					state= stay;
				}
			}
		}

		// �����̷��� ������ ��ȿ���� �Ǵ�

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
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
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
			// �߹ؿ� ��ü�� ������
			gonnalive();
		} else
		{
			state = stay;
		}
	}

	@Override
	public void Corpse_Stay ()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
		InfectedTurn = turnInfo.turnNumber + 1;
	}

	@Override
	public DirectionCode Infected_Move ()
	{
		UpdateStateInfected();
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
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
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������
			 * 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
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
			// �� �̻� ������ �ʿ䰡 ������
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
			// ������ ���� ������ �� �ʿ��ϸ�
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
			// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		}
		return new Point(result_row, result_column);
	}
}
