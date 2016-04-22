package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.Reaction;
import l4g.data.Reaction.TypeCode;
import l4g.data.TurnInfo;

public class TheGivingTree extends Player
{
	// 나의 아이덴티티 = 생존형 스카우터 + 시체폭탄
	public TheGivingTree(int ID)
	{
		// 이름
		super(ID, "아낌없이 주는 나무");
		
		// 직접 감염 받을지? ㄴㄴ
		this.trigger_acceptDirectInfection = false;
	}
	
	// 주변 정보를 기록하기 위한 배열들
	int[] Infecteds;
	int[] Corpses;
	int[] Survivors;
	int[] weightTable;
	final int MIN_VALUE = -999;
	
	// 상태 변화를 나타내는 필드	
	int state;
	final int Scout = 0;
	final int CorpseBomb = 1;
	
	// 상태 변화도
	void UpdateState()
	{
		switch ( state )
		{
		// 기본 : 생존형 스카우터
		case Scout:
			//죽은 이후 감염체 단계에서 상태 변경
			if ( myInfo.state == StateCode.Infected )
				state++;
			break;
		
		// 첫 죽음 이후 : 시체폭탄
		case CorpseBomb:
			break;
		
		default:
			break;
		}
	}
	
	// 주변 13칸의 정보를 얻기 위한 메소드
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
		
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
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
	
	// 가중치 배열을 받아 적절한 이동을 결정해주는 메소드
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
	
	// 혹시나 모를 장외를 대비해 유효값을 확인해주는 메소드
	void ValidityCheck(int[] table_weight, int row, int column)
	{
		//위쪽 이탈
		if ( row - 1 <= 0 )
			table_weight[0] = MIN_VALUE;
		//왼쪽 이탈
		if ( column - 1 <= 0 )
			table_weight[1] = MIN_VALUE;
		//오른쪽 이탈
		if ( column + 1 >= Constants.Classroom_Width )
			table_weight[2] = MIN_VALUE;
		//아래쪽 이탈
		if ( row + 1 >= Constants.Classroom_Height )
			table_weight[3] = MIN_VALUE;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		//상태 갱신
		UpdateState();
		weightTable = new int[4];
		
		Infecteds = GetAroundInformation(StateCode.Infected, myInfo.position.row, myInfo.position.column);
		Corpses = GetAroundInformation(StateCode.Corpse, myInfo.position.row, myInfo.position.column);
		Survivors = GetAroundInformation(StateCode.Survivor, myInfo.position.row, myInfo.position.column);
		
		// 감염 직전에만 잠시 떨어지기
		if ( turnInfo.turnNumber == 11 )
		{
			weightTable[0] -= Survivors[0] + Survivors[1] + Survivors[3];
			weightTable[1] -= Survivors[1] + Survivors[4] + Survivors[9];
			weightTable[2] -= Survivors[3] + Survivors[8] + Survivors[11];
			weightTable[3] -= Survivors[9] + Survivors[11] + Survivors[12];
		}
		
		// 최대한 살아남아서 관찰하자!
		else
		{
			// 생존자 가중치 합산
			weightTable[0] += ( 2 * Survivors[0] + 2 * Survivors[1] + 3 * Survivors[2] + 2 * Survivors[3] );
			weightTable[1] += ( 2 * Survivors[1] + 2 * Survivors[4] + 3 * Survivors[5] + 2 * Survivors[9] );
			weightTable[2] += ( 2 * Survivors[3] + 3 * Survivors[7] + 2 * Survivors[8] + 2 * Survivors[11] );
			weightTable[3] += ( 2 * Survivors[9] + 3 * Survivors[10] + 2 * Survivors[11] + 2 * Survivors[12] );
			
			// 감염체 가중치 합산
			weightTable[0] -= ( 5 * Infecteds[0] + 5 * Infecteds[1] + 3 * Infecteds[2] + 5 * Infecteds[3] );
			weightTable[1] -= ( 5 * Infecteds[1] + 5 * Infecteds[4] + 3 * Infecteds[5] + 5 * Infecteds[9] );
			weightTable[2] -= ( 5 * Infecteds[3] + 3 * Infecteds[7] + 5 * Infecteds[8] + 5 * Infecteds[11] );
			weightTable[3] -= ( 5 * Infecteds[9] + 3 * Infecteds[10] + 5 * Infecteds[11] + 5 * Infecteds[12] );
			
			// 시체 가중치 합산
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
		//상태 갱신
		UpdateState();
		weightTable = new int[4];
		
		// 내 밑에 시체가 깔려 있으면 도망감 OR 내 자리에 너무 많은 감염체가 있으면 도망감
		if ( cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse) > 0
				|| cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Infected) > 6)
		{
			Corpses = GetAroundInformation(StateCode.Corpse, myInfo.position.row, myInfo.position.column);
			Survivors = GetAroundInformation(StateCode.Survivor, myInfo.position.row, myInfo.position.column);
			
			// 감염체와 생존자의 가중치 합산
			weightTable[0] -= Survivors[0] + Survivors[1] + 3 * Corpses[2] + Survivors[3];
			weightTable[1] -= Survivors[1] + Survivors[4] + 3 * Corpses[5] + Survivors[9];
			weightTable[2] -= Survivors[3] + 3 * Corpses[7] + Survivors[8] + Survivors[11];
			weightTable[3] -= Survivors[9] + 3 * Corpses[10] + Survivors[11] + Survivors[12];
			
			ValidityCheck(weightTable, myInfo.position.row, myInfo.position.column);
			return MoveDecision(weightTable);
		}
		
		// 그렇지 않으면 정화 기도 시도
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
		// 초기 위치 : 중앙
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
			// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					CellInfo cell = cells[row][column];
					
					NumberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					NumberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					
					weight = NumberOfInfecteds != 0 ? NumberOfCorpses + NumberOfInfecteds : 0;
					
					// 가장 많은 칸이 발견되면 갱신
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
