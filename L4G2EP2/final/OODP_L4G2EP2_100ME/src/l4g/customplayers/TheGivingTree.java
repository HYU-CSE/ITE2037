package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.Reaction;
import l4g.data.Reaction.TypeCode;
import l4g.data.TurnInfo;

public class TheGivingTree extends Player
{
	// ���� ���̵�ƼƼ = ������ ��ī���� + ��ü��ź
	public TheGivingTree(int ID)
	{
		// �̸�
		super(ID, "�Ƴ����� �ִ� ����");
		
		// ���� ���� ������? ����
		this.trigger_acceptDirectInfection = false;
	}
	
	// �ֺ� ������ ����ϱ� ���� �迭��
	int[] Infecteds;
	int[] Corpses;
	int[] Survivors;
	int[] weightTable;
	final int MIN_VALUE = -999;
	
	// ���� ��ȭ�� ��Ÿ���� �ʵ�	
	int state;
	final int Scout = 0;
	final int CorpseBomb = 1;
	
	// ���� ��ȭ��
	void UpdateState()
	{
		switch ( state )
		{
		// �⺻ : ������ ��ī����
		case Scout:
			//���� ���� ����ü �ܰ迡�� ���� ����
			if ( myInfo.state == StateCode.Infected )
				state++;
			break;
		
		// ù ���� ���� : ��ü��ź
		case CorpseBomb:
			break;
		
		default:
			break;
		}
	}
	
	// �ֺ� 13ĭ�� ������ ��� ���� �޼ҵ�
	int[] GetAroundInformation(StateCode Code, int row, int column)
	{
		int[] numberOfPlayers = new int[13];
		
		// 0
		row -= 2;
		
		if ( row >= 0 )
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == Code);
		
		// 1, 2, 3
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == Code);
			
			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == Code);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == Code);
		}
		
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;
		
		if ( column >= 1 )
		{
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == Code);
			
			if ( column >= 2 )
				numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == Code);
		}
		
		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == Code);
			
			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == Code);
		}
		
		// 9, A, B
		++row;
		
		if ( row < Constants.Classroom_Height )
		{
			if ( column >= 1 )
				numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == Code);
			
			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == Code);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == Code);
		}
		
		// C
		++row;
		
		if ( row < Constants.Classroom_Height )
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == Code);
		
		return numberOfPlayers;
	}
	
	// ����ġ �迭�� �޾� ������ �̵��� �������ִ� �޼ҵ�
	DirectionCode MoveDecision(int[] weightTable)
	{
		int max_weight = MIN_VALUE;
		int max_index = 0;
		
		for ( int index = 0; index < 4; index++ )
		{
			if ( weightTable[index] > max_weight )
			{
				max_weight = weightTable[index];
				max_index = index;
			}
		}
		
		switch ( max_index )
		{
		case 0:
			return DirectionCode.Up;
		
		case 1:
			return DirectionCode.Left;
		
		case 2:
			return DirectionCode.Right;
		
		default:
			return DirectionCode.Down;
		}
	}
	
	// Ȥ�ó� �� ��ܸ� ����� ��ȿ���� Ȯ�����ִ� �޼ҵ�
	void ValidityCheck(int[] table_weight, int row, int column)
	{
		//���� ��Ż
		if ( row - 1 <= 0 )
			table_weight[0] = MIN_VALUE;
		//���� ��Ż
		if ( column - 1 <= 0 )
			table_weight[1] = MIN_VALUE;
		//������ ��Ż
		if ( column + 1 >= Constants.Classroom_Width )
			table_weight[2] = MIN_VALUE;
		//�Ʒ��� ��Ż
		if ( row + 1 >= Constants.Classroom_Height )
			table_weight[3] = MIN_VALUE;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		//���� ����
		UpdateState();
		weightTable = new int[4];
		
		Infecteds = GetAroundInformation(StateCode.Infected, myInfo.position.row, myInfo.position.column);
		Corpses = GetAroundInformation(StateCode.Corpse, myInfo.position.row, myInfo.position.column);
		Survivors = GetAroundInformation(StateCode.Survivor, myInfo.position.row, myInfo.position.column);
		
		// ���� �������� ��� ��������
		if ( turnInfo.turnNumber == 11 )
		{
			weightTable[0] -= Survivors[0] + Survivors[1] + Survivors[3];
			weightTable[1] -= Survivors[1] + Survivors[4] + Survivors[9];
			weightTable[2] -= Survivors[3] + Survivors[8] + Survivors[11];
			weightTable[3] -= Survivors[9] + Survivors[11] + Survivors[12];
		}
		
		// �ִ��� ��Ƴ��Ƽ� ��������!
		else
		{
			// ������ ����ġ �ջ�
			weightTable[0] += ( 2 * Survivors[0] + 2 * Survivors[1] + 3 * Survivors[2] + 2 * Survivors[3] );
			weightTable[1] += ( 2 * Survivors[1] + 2 * Survivors[4] + 3 * Survivors[5] + 2 * Survivors[9] );
			weightTable[2] += ( 2 * Survivors[3] + 3 * Survivors[7] + 2 * Survivors[8] + 2 * Survivors[11] );
			weightTable[3] += ( 2 * Survivors[9] + 3 * Survivors[10] + 2 * Survivors[11] + 2 * Survivors[12] );
			
			// ����ü ����ġ �ջ�
			weightTable[0] -= ( 5 * Infecteds[0] + 5 * Infecteds[1] + 3 * Infecteds[2] + 5 * Infecteds[3] );
			weightTable[1] -= ( 5 * Infecteds[1] + 5 * Infecteds[4] + 3 * Infecteds[5] + 5 * Infecteds[9] );
			weightTable[2] -= ( 5 * Infecteds[3] + 3 * Infecteds[7] + 5 * Infecteds[8] + 5 * Infecteds[11] );
			weightTable[3] -= ( 5 * Infecteds[9] + 3 * Infecteds[10] + 5 * Infecteds[11] + 5 * Infecteds[12] );
			
			// ��ü ����ġ �ջ�
			weightTable[0] -= ( 2 * Corpses[0] + 2 * Corpses[1] + 4 * Corpses[2] + 2 * Corpses[3] );
			weightTable[1] -= ( 2 * Corpses[1] + 2 * Corpses[4] + 4 * Corpses[5] + 2 * Corpses[9] );
			weightTable[2] -= ( 2 * Corpses[3] + 4 * Corpses[7] + 2 * Corpses[8] + 2 * Corpses[11] );
			weightTable[3] -= ( 2 * Corpses[9] + 4 * Corpses[10] + 2 * Corpses[11] + 2 * Corpses[12] );
		}
		
		ValidityCheck(weightTable, myInfo.position.row, myInfo.position.column);
		return MoveDecision(weightTable);
	}
	
	@Override
	public void Corpse_Stay()
	{
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		//���� ����
		UpdateState();
		weightTable = new int[4];
		
		// �� �ؿ� ��ü�� ��� ������ ������ OR �� �ڸ��� �ʹ� ���� ����ü�� ������ ������
		if ( cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse) > 0
				|| cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Infected) > 6)
		{
			Corpses = GetAroundInformation(StateCode.Corpse, myInfo.position.row, myInfo.position.column);
			Survivors = GetAroundInformation(StateCode.Survivor, myInfo.position.row, myInfo.position.column);
			
			// ����ü�� �������� ����ġ �ջ�
			weightTable[0] -= Survivors[0] + Survivors[1] + 3 * Corpses[2] + Survivors[3];
			weightTable[1] -= Survivors[1] + Survivors[4] + 3 * Corpses[5] + Survivors[9];
			weightTable[2] -= Survivors[3] + 3 * Corpses[7] + Survivors[8] + Survivors[11];
			weightTable[3] -= Survivors[9] + 3 * Corpses[10] + Survivors[11] + Survivors[12];
			
			ValidityCheck(weightTable, myInfo.position.row, myInfo.position.column);
			return MoveDecision(weightTable);
		}
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}
	
	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
			state = Scout;
	}
	
	@Override
	public Point Soul_Spawn()
	{
		// �ʱ� ��ġ : �߾�
		int result_row = 6;
		int result_column = 6;
		
		int max_row = -1;
		int max_column = -1;
		
		int weight = 0;
		int max_weight = 0;
		
		int NumberOfInfecteds = 0;
		int NumberOfCorpses = 0;
		
		if ( state == CorpseBomb )
		{
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					CellInfo cell = cells[row][column];
					
					NumberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					NumberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					
					weight = NumberOfInfecteds != 0 ? NumberOfCorpses + NumberOfInfecteds : 0;
					
					// ���� ���� ĭ�� �߰ߵǸ� ����
					if ( weight > max_weight )
					{
						max_weight = weight;
						max_row = row;
						max_column = column;
					}
				}
			}
			
			result_row = max_row;
			result_column = max_column;
		}
		
		return new Point(result_row, result_column);
	}
}
