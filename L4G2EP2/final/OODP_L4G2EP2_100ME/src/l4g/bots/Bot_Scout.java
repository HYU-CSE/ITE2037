package l4g.bots;

import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;
import l4g.common.StateCode;

/**
 * �ٸ� �����ڸ� �Ѿ� �ٴϸ� �ڽ��� �߰��� ���� �˷� �ַ� ����ϴ� Bot �÷��̾� Ŭ�����Դϴ�.
 * 
 * ���� ����: �׻� �����մϴ�.
 * 
 * ������ �̵�: ������ ���� ���� ���� �������� �̵��մϴ�.
 *              �������� �þ� ������
 *                    0
 *                   123
 *                  45678
 *                   9AB
 *                    C
 *              ..�� ��
 *              ��:     0123�� �ִ� ������ ��
 *              ����:   1459�� �ִ� ������ ��
 *              ������: 378B�� �ִ� ������ ��
 *              �Ʒ�:   9ABC�� �ִ� ������ ��
 *              ..�� �ջ��Ͽ� ���մϴ�.
 * 
 * ����ü �̵�: ���� ĭ�� ��ü�� �ִٸ� �� ĭ���� �̵��մϴ�. �׷��� ���� ��� ��ȭ �⵵�� �帳�ϴ�.
 * 
 * ��ȥ ��ġ: �׻� '��ȣ�ϴ� ĭ'�� �����մϴ�.
 * 
 * @author Racin
 *
 */
public class Bot_Scout extends Player
{
	/* --------------------------------------------------
	 * TODO �� �о� ������!
	 * 
	 * ��� Bot �÷��̾���� ���� �ٸ� �ǻ� ������ �����ϱ� ����
	 * �ڽ��� ID�� ���� ��ȣ�� �̿��Ͽ�
	 * ������ '���� �켱����'�� '��ȣ�ϴ� ĭ'�� ���� �ǻ� ������ Ȱ���մϴ�.
	 * ���� �������� Bot �÷��̾��� �ڵ带 ������ ����Ϸ��� ���
	 * ���� �۾����� �� �Բ� �����ؾ� �մϴ�.
	 * 
	 * - �Ʒ��� '�� field, �޼���� �ݵ�� �ʿ��մϴ�'�� ���� �ִ� �͵���
	 * 	 ��� ������ �;� �մϴ�.
	 * 
	 * - Soul_Stay()�� �ִ�, �ʱ�ȭ ���� �ڵ带 ������ �;� �մϴ�.
	 */
	
	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�.
	 * �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[] directions = new DirectionCode[4];
	
	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�.
	 * �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	Point favoritePoint = new Point(0, 0);
	
	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�.
	 * �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
	void Init_Data()
	{
		/*
		 * �������� �ۼ��ϴ� �÷��̾�� ������ ���ǽǿ� �� �� �� �����ϹǷ�
		 * ��� �Ʒ��� �ڵ带 �� �ʿ� ����
		 * 
		 * directions[0] = DirectionCode.Up;
		 * directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right;
		 * directions[3] = DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6;
		 * favoritePoint.column = 6;
		 * 
		 * ...�� ���� �������� �����ϴ� �켱 ������ �׳� �ٷ� �Ҵ��� �ᵵ �����մϴ�.
		 * (Bot���� �Ȱ��� Ŭ������ �ν��Ͻ��� ���� �����ϹǷ� �̷� ���� �ؾ� �մϴ�).
		 */
		
		// �÷��̾�� �ٸ� ID, ���Ӹ��� �ٸ� ���� ��ȣ�� �����Ͽ� ���� �̻��� ���� �����
		long seed = (ID + 2016) * gameNumber + ID;
		
		if ( seed < 0 )
			seed = -seed;
		
		// �� ���� 24�� ���� �������� ���� ���� �켱���� ����
		switch ( (int)(seed % 24) )
		{
		case 0:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			break;
		case 1:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
			break;
		case 2:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
			break;
		case 3:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
			break;
		case 4:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Right;
			break;
		case 5:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Left;
			break;
		case 6:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			break;
		case 7:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
			break;
		case 8:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Down;
			break;
		case 9:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Up;
			break;
		case 10:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
			break;
		case 11:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
			break;
		case 12:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
			break;
		case 13:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
			break;
		case 14:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Down;
			break;
		case 15:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Up;
			break;
		case 16:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Left;
			break;
		case 17:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			break;
		case 18:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Right;
			break;
		case 19:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Left;
			break;
		case 20:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
			break;
		case 21:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
			break;
		case 22:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Left;
			break;
		case 23:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			break;
		}
		
		favoritePoint.row = (int)(seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int)(seed % Constants.Classroom_Height);
	}
	
	/**
	 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}
	
	
	public Bot_Scout(int ID)
	{
		super(ID, "������#" + ID);
		
		this.trigger_acceptDirectInfection = false;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		/*
		 * ������ �̵�: ������ ���� ���� ���� �������� �̵��մϴ�.
		 *              �������� �þ� ������
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..�� ��
		 *              ��:     0123�� �ִ� ������ ��
		 *              ����:   1459�� �ִ� ������ ��
		 *              ������: 378B�� �ִ� ������ ��
		 *              �Ʒ�:   9ABC�� �ִ� ������ ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfPlayers = new int[13];
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// ���� ���̴� 13���� ��쿡 ���� ������ �� ���
		
		// 0
		row -= 2;
		
		if ( row >= 0 )
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2, 3
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;
		
		if ( column >= 1 )
		{
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column >= 2 )
				numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 9, A, B
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 )
				numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
		}
		
		// C
		++row;
		
		if ( row < Constants.Classroom_Height)
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
				break;
			}
		}
		
		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		int max_weight = -1;
		int max_idx_weights = 0;
		
		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			if ( weights[iWeights] > max_weight )
			{
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
				
				if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
						adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				{
					max_weight = weights[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}
		
		return directions[max_idx_weights];
	}
	
	@Override
	public void Corpse_Stay()
	{
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		// �� �ؿ� ��ü�� ��� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}
	
	@Override
	public void Soul_Stay()
	{
		if ( this.turnInfo.turnNumber == 0 )
		{
			// �� �κ��� Bot �÷��̾� �ڵ带 ������ ����ϱ� ���� �ݵ�� �ʿ��մϴ�.
			Init_Data();
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		// �׻� '��ȣ�ϴ� ĭ'�� ����
		return favoritePoint;
	}
	
}
