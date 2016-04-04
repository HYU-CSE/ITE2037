package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class Hyerim extends Player
{
	
	DirectionCode[] Directions;
	int[] Direction_values;

	int changedirection=0;
	
	Point basePoint;
	
	int index_directionToMove=0;
	
	int soulcount=0;
	
	
	public Hyerim()
	{
		name = "Hyerim";
		acceptDirectInfection = false;	// TODO '���� ����'=true
	}

	void SetBasePoint()
	{
		basePoint = new Point(4,4);
	}
	

	void SetDirections()
	{
		Directions = new DirectionCode [4];
		
		Direction_values = new int[4];
		
		for(int i=0; i<4; i++){
		
			Direction_values[(i+changedirection)%4] = (i+changedirection)%4;
		}
		Directions[3]=DirectionCode.Down;
		Directions[2]=DirectionCode.Right;
		Directions[1]=DirectionCode.Left;
		Directions[0]=DirectionCode.Up;
		
	}

	
	//���⼳�� �������� �ֺ� ����
	public DirectionCode Survivor_Move_as_Turn(){
		DirectionCode result;
		
		//�̵��� �� �ִ� �ѵ� ������ '���� ����' ������� ���� �� ������ ���� ���������� �̵� ���� - �̵� ������ ������ �ּ� �� �̻� ����
		do
		{
			result = Directions[index_directionToMove];
			++index_directionToMove;
			index_directionToMove %= 4;
		}
		while ( IsValidMove(result) == false );
		
		return result;
	}
	
	
	//����ü�� ���� ���� �������� �̵�
	public DirectionCode Survivor_Move_as_Goto_Infected(){
		
		
		//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;
		
		//�þ� ���� ��� �÷��̾ ���� ����ü ��	 ���
				for ( PlayerInfo other : othersInfo_withinSight )
				{
					//�ش� �÷��̾�� �� ������ �Ÿ� ��
					Vector v = GetDistanceVectorBetweenPlayers(other);

					if ( other.GetState() == PlayerInfo.State.Infected )
					{
						if ( v.y_offset < 0 )
							++weights[0];
			
						if ( v.x_offset < 0 )
							++weights[1];
						
						if ( v.x_offset > 0 )
							++weights[2];
						
						if ( v.y_offset > 0 )
							++weights[3];
					}
				}
		
		//�� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = Integer.MAX_VALUE;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = Integer.MAX_VALUE;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = Integer.MAX_VALUE;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = Integer.MAX_VALUE;

		//������ ���赵�� �ּҰ��� ������ ���
		for ( int weight : weights )
			if ( weight < min_weight )
				min_weight = weight;
		
		//'���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
				return Directions[iShuffledDirection];

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
		
	}
	
	//����ü ���ϱ�
	public DirectionCode Survivor_Move_as_avoid_Infect(){
			
			
			//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			//�þ� ���� ��� �÷��̾ ���� ����ü ��	 ���
					for ( PlayerInfo other : othersInfo_withinSight )
					{
						//�ش� �÷��̾�� �� ������ �Ÿ� ��
						Vector v = GetDistanceVectorBetweenPlayers(other);

						if ( other.GetState() == PlayerInfo.State.Infected  && v.GetDistance() > 1)
						{
							if ( v.y_offset < 0 )
								++weights[0];
				
							if ( v.x_offset < 0 )
								++weights[1];
							
							if ( v.x_offset > 0 )
								++weights[2];
							
							if ( v.y_offset > 0 )
								++weights[3];
						}
					}
			
			//�� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = Integer.MAX_VALUE;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = Integer.MAX_VALUE;

			//������ ���赵�� �ּҰ��� ������ ���
			for ( int weight : weights )
				if ( weight > min_weight )
					min_weight = weight;
			
			//'���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
					return Directions[iShuffledDirection];

			return Move_To_center();
			//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
//			return DirectionCode.Stay;
			
		}

	//�����Ѱ� ���� ����ü�� ���� ���� �������� �̵�
	public DirectionCode Survivor_Move_as_Goto_Infected_except_adject(){
			
			
			//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			//�þ� ���� ��� �÷��̾ ���� ����ü ��	 ���
					for ( PlayerInfo other : othersInfo_withinSight )
					{
						//�ش� �÷��̾�� �� ������ �Ÿ� ��
						Vector v = GetDistanceVectorBetweenPlayers(other);

						if ( other.GetState() == PlayerInfo.State.Infected  && v.GetDistance() ==1)
						{
							if ( v.y_offset < 0 )
								++weights[0];
				
							if ( v.x_offset < 0 )
								++weights[1];
							
							if ( v.x_offset > 0 )
								++weights[2];
							
							if ( v.y_offset > 0 )
								++weights[3];
						}
					}
			
			//�� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = Integer.MAX_VALUE;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = Integer.MAX_VALUE;

			//������ ���赵�� �ּҰ��� ������ ���
			for ( int weight : weights )
				if ( weight < min_weight )
					min_weight = weight;
			
			//'���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
					return Directions[iShuffledDirection];

			//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
			return DirectionCode.Stay;
			
		}
		
		
		
		
	//����ü_������	
	public DirectionCode Infected_Move_as_Predator()
		{
			/*
			 * �������� ����ü �̵�: �� ��ġ�� ��ü�� �ִ� ��� ���ڸ��� �ӹ���. �׷��� ���� ��� ������ �� ��ü�� ���� ���� �������� �̵�.
			 */
			
			//�� ��ġ�� ��ü�� �ִ��� ���� �˻�
			boolean isCorpseHere = false;
			CellInfo here = GetCellInfo(myInfo.GetPosition());
			
			for ( int iPlayer = 0; iPlayer < here.GetNumberOfPlayersInTheCell(); ++iPlayer )
			{
				PlayerInfo other = here.GetPlayerInfo(iPlayer);
				
				if ( other.GetState() == PlayerInfo.State.Corpse )
				{
					isCorpseHere = true;
					break;
				}
			}
			
			//��ü�� �ִ� ��� ���ڸ��� �ӹ���
			if ( isCorpseHere == true )
				return DirectionCode.Stay;
			
			//���⺰�� ������ �� ��ü�� ���� ����ϱ� ���� �迭 ���
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int max_weight = -1;
			
			//�þ� ���� ��� �÷��̾ ���� ������ �� ��ü �� ���
			for ( PlayerInfo other : othersInfo_withinSight )
			{
				//�ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);

				if ( other.GetState() != PlayerInfo.State.Infected )
				{
					if ( v.y_offset < 0 )
						++weights[0];
		
					if ( v.x_offset < 0 )
						++weights[1];
					
					if ( v.x_offset > 0 )
						++weights[2];
					
					if ( v.y_offset > 0 )
						++weights[3];
				}
			}
			
			//�� �� ���� ���⿡ ���� ������ �� ��ü ���� �ּҰ����� ����
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = -1;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = -1;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = -1;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = -1;

			//������ ������ �� ��ü ���� �ִ밪�� ������ ���
			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			//'���� ����' ������ ���� ������ �� ��ü ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ Direction_values[iShuffledDirection] ] == max_weight )
					return Directions[iShuffledDirection];

			//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
			return DirectionCode.Stay;
		}
	
	public DirectionCode Infected_Move_as_Stayer()
	{
		DirectionCode result;
		
		//�� ��ġ�� ��ü�� �ִ��� �˻�
		boolean isCorpseHere = false;
		CellInfo here = GetCellInfo(myInfo.GetPosition());
		
		for ( int iPlayer = 0; iPlayer < here.GetNumberOfPlayersInTheCell(); ++iPlayer )
		{
			PlayerInfo other = here.GetPlayerInfo(iPlayer);
			
			if ( other.GetState() == PlayerInfo.State.Corpse )
			{
				isCorpseHere = true;
				break;
			}
		}
		
		//��ü�� �ִٸ� ������ �̵��� ���� ������� �ֺ� ĭ���� �̵�
		if ( isCorpseHere == true )
			result = Survivor_Move_as_Turn();
		//�׷��� �ʴٸ� ��ȭ �⵵
		else
			result = DirectionCode.Stay;
 
		return result;
	}	
		
	//������_������	
	//�����ڼ�*��ü�� �� ���� ū �������� �̵�
	public DirectionCode Survivor_Move_as_Scout(){
		/*
		 * �������� ������ �̵�: ������ �� x ��ü�� ����ü ���� ���� ū ������ ����
		 */
		
		//���⺰�� �� �÷��̾� �� �� ���� ��밪�� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] survivors = new int[4];
		int[] others = new int[4];
		int[] weights = new int[4];
		int max_weight = -1;
		
		//���� ������ ��� �÷��̾ ���� �˻� ����
		for ( PlayerInfo other : othersInfo_detected )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);

			//�ش� �÷��̾��� ���� ���¿� ���� �÷��̾� �� ���
			if ( other.GetState() == PlayerInfo.State.Survivor )
			{
				if ( v.y_offset < 0 )
					++survivors[0];
	
				if ( v.x_offset < 0 )
					++survivors[1];
				
				if ( v.x_offset > 0 )
					++survivors[2];
				
				if ( v.y_offset > 0 )
					++survivors[3];
			}
			else
			{
				if ( v.y_offset < 0 )
					++others[0];
	
				if ( v.x_offset < 0 )
					++others[1];
				
				if ( v.x_offset > 0 )
					++others[2];
				
				if ( v.y_offset > 0 )
					++others[3];
			}
		}

		//���� ��밪: ������ �� x ��ü �� ����ü �� ���
		for ( int i = 0; i < 4; ++i )
			weights[i] = survivors[i] * others[i];
		
		//�� �� ���� ���⿡ ���� ��밪�� �ּҰ����� ����
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = -1;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = -1;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = -1;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = -1;

		//������ ��밪�� �ִ밪�� ������ ���
		for ( int weight : weights )
			if ( weight > max_weight )
				max_weight = weight;
		
		//'���� ����' ������ ���� ��밪�� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ Direction_values[iShuffledDirection] ] == max_weight )
				return Directions[iShuffledDirection];

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
	}
	
	
	public DirectionCode Infected_Move_as_Seeker()
	{
		/*
		 * �������� ����ü �̵�: ������ ĭ�� ������ �þ� ���� ������ �����ڰ� ���� ���� �������� �̵�
		 */
		
		//���⺰�� ������ ���� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int max_weight = -1;
		
		//�þ� ���� ��� �÷��̾ ���� ������ ĭ�� �����ϰ� ������ �� ���
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);

			//������ ĭ(�Ÿ��� 1�� ĭ)�� ���� ���� �����ڿ� ���� ���⺰ �� ����  
			if ( other.GetState() == PlayerInfo.State.Survivor && v.GetDistance() > 1 )
			{
				if ( v.y_offset < 0 )
					++weights[0];
	
				if ( v.x_offset < 0 )
					++weights[1];
				
				if ( v.x_offset > 0 )
					++weights[2];
				
				if ( v.y_offset > 0 )
					++weights[3];
			}
		}
		
		//�� �� ���� ���⿡ ���� ������ ���� �ּҰ����� ����
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = -1;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = -1;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = -1;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = -1;

		//������ ������ ���� �ִ밪�� ������ ���
		for ( int weight : weights )
			if ( weight > max_weight )
				max_weight = weight;
		
		//'���� ����' ������ ���� ������ ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ Direction_values[iShuffledDirection] ] == max_weight )
				return Directions[iShuffledDirection];

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
	}
	
	
	//�ٸ� �÷��̾���� ���� ���� �������� �̵�
	public DirectionCode Survivor_Move_as_Loner(){
		/*
		 * �������� ������ �̵�: �ٸ� �÷��̾ ���� ���� ������ ����
		 */
		
		//���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;
		
		//���� ������ ��� �÷��̾ ���� ���赵 ���
		for ( PlayerInfo other : othersInfo_detected )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);

			//�ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
			if ( v.y_offset < 0 )
				++weights[0];

			//�ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
			if ( v.x_offset < 0 )
				++weights[1];
			
			//�ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
			if ( v.x_offset > 0 )
				++weights[2];
			
			//�ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
			if ( v.y_offset > 0 )
				++weights[3];
		}
		
		//�� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = Integer.MAX_VALUE;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = Integer.MAX_VALUE;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = Integer.MAX_VALUE;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = Integer.MAX_VALUE;

		//������ ���赵�� �ּҰ��� ������ ���
		for ( int weight : weights )
			if ( weight < min_weight )
				min_weight = weight;
		
		//'���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
				return Directions[iShuffledDirection];

		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
	}
	
	
	//����ü_��ü�� ������ �����ڿ� ���� �ൿ�ϰ� ��ü�� ������ Stay
	public DirectionCode Infected_Move_as_Corpse_Bomb(){

		DirectionCode result;
		
		//�� ��ġ�� ��ü�� �ִ��� �˻�
		boolean isCorpseHere = false;
		CellInfo here = GetCellInfo(myInfo.GetPosition());
		
		for ( int iPlayer = 0; iPlayer < here.GetNumberOfPlayersInTheCell(); ++iPlayer )
		{
			PlayerInfo other = here.GetPlayerInfo(iPlayer);
			
			if ( other.GetState() == PlayerInfo.State.Corpse )
			{
				isCorpseHere = true;
				break;
			}
		}
		
		//��ü�� �ִٸ� ������ �̵��� ���� ������� �ֺ� ĭ���� �̵�
		if ( isCorpseHere == true )
			result = Survivor_Move_as_Turn();
		//�׷��� �ʴٸ� ��ȭ �⵵
		else
			result = DirectionCode.Stay;
 
		return result;
	}
	//��ȥ��ġ_����ü�� ���� ���� ĭ����
	public Point Soul_Spawn_as_Corpse_Bomb(){
		/*
		 * ��ü ��ź�� ��ȥ ��ġ: ���� ����ü�� ���� ĭ�� ��� ��ġ
		 */
		Point pointToSpawn = basePoint;

		//�� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		//��� ����ü�� ���� �˻� ���� - 9x9ĭ�� �÷��̾�� 40�� �Ұ��ϹǷ� ����ü�� ���� ���� ĭ ����� ���ÿ� ����
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//��� ����ü�� ���� �迭 ���� �� �ִ밪 ���, �ִ밪�� ���� ĭ�� ���� ��ǥ ��� ����
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				
				++weights[pos_other.y][pos_other.x];

				//�ִ밪�� �ٲ���ٸ� '����ü�� ���� ���� ĭ' ��� �ʱ�ȭ
				if ( weights[pos_other.y][pos_other.x] > max_weight )
				{
					++max_weight; //weight�� �׻� 1�� �����ϹǷ� �翬�� �ִ밪�� 1�� ������
					list_pos_max_weight.clear();
				}
				
				//���� ĭ�� ���� �ִ밪�� ���ٸ� ���� ĭ�� '����ü�� ���� ���� ĭ' ��Ͽ� �߰� (������ �ִ밪�� �ٲ���ٸ� �׻� �߰���)
				if ( weights[pos_other.y][pos_other.x] == max_weight )
					list_pos_max_weight.add(pos_other);
			}
		}
		
		//�˻簡 ������ ����ü�� ���� ���� ĭ ��Ͽ��� '���� ����' ������� ���� �� ������ ���� ����� ù��° ĭ ����
		int min_distance = Integer.MAX_VALUE;
		Point myPosition = myInfo.GetPosition();
		
		for ( Point pos_max_weight : list_pos_max_weight )
		{
			int distance = GetDistance(myPosition, pos_max_weight);
			
			if ( distance < min_distance )
			{
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}
		
		return pointToSpawn;
	}
	//��ȥ��ġ_�ʱ���ġ��

	public DirectionCode Move_To_center(){
		if(this.myInfo.GetPosition().x<4)
			return DirectionCode.Right;
		else if(this.myInfo.GetPosition().x>4)
			return DirectionCode.Left;
		else if(this.myInfo.GetPosition().y<4)
			return DirectionCode.Down;
		else
			return DirectionCode.Up;
	}
	
	
	public Point Soul_Spawn_as_not_Corpse_Bomb(){
		/*
		 * ��ü ��ź�� ��ȥ ��ġ: ���� ����ü�� ���� ĭ�� ��� ��ġ
		 */
		Point pointToSpawn = basePoint;

		//�� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		//��� ����ü�� ���� �˻� ���� - 9x9ĭ�� �÷��̾�� 40�� �Ұ��ϹǷ� ����ü�� ���� ���� ĭ ����� ���ÿ� ����
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//��� ����ü�� ���� �迭 ���� �� �ִ밪 ���, �ִ밪�� ���� ĭ�� ���� ��ǥ ��� ����
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				
				++weights[pos_other.y][pos_other.x];

				//�ִ밪�� �ٲ���ٸ� '����ü�� ���� ���� ĭ' ��� �ʱ�ȭ
				if ( weights[pos_other.y][pos_other.x] > max_weight )
				{
					++max_weight; //weight�� �׻� 1�� �����ϹǷ� �翬�� �ִ밪�� 1�� ������
					list_pos_max_weight.clear();
				}
				
				//���� ĭ�� ���� �ִ밪�� ���ٸ� ���� ĭ�� '����ü�� ���� ���� ĭ' ��Ͽ� �߰� (������ �ִ밪�� �ٲ���ٸ� �׻� �߰���)
				if ( weights[pos_other.y][pos_other.x] == -1 )
					list_pos_max_weight.add(pos_other);
			}
		}
		
		//�˻簡 ������ ����ü�� ���� ���� ĭ ��Ͽ��� '���� ����' ������� ���� �� ������ ���� ����� ù��° ĭ ����
		int min_distance = Integer.MAX_VALUE;
		Point myPosition = myInfo.GetPosition();
		
		for ( Point pos_max_weight : list_pos_max_weight )
		{
			int distance = GetDistance(myPosition, pos_max_weight);
			
			if ( distance < min_distance )
			{
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}
		
		return pointToSpawn;
	}

	
	
	
	public Point Soul_Spawn_as_BaseSetting(){
		return basePoint;
	}
	
	
	
	@Override
	public DirectionCode Survivor_Move()
	{
		return Survivor_Move_as_Goto_Infected();
		// TODO ������ ���� �̵�
	}

	@Override
	public void Corpse_Stay()
	{
		
		
		
		
		
		// TODO ��ü ������ ��
	}

	@Override
	public DirectionCode Infected_Move()
	{
		return Infected_Move_as_Stayer();

	}

	@Override
	public void Soul_Stay()
	{
		
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			SetDirections();
			SetBasePoint();
		}
		// TODO ��ȥ ����
	}

	
	@Override
	public Point Soul_Spawn()
	{
		return Soul_Spawn_as_Corpse_Bomb();
		
		
		
		
		
		// TODO ��ȥ ��ġ
		
	}

}
