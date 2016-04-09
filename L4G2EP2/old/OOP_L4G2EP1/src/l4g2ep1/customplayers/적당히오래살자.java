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
public class �������������� extends Player {
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	Point basePoint;

	void ShuffleDirections() {
		// �ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;

		if (seed <= 0)
			seed += Integer.MAX_VALUE;

		/*
		 * �� ���� ������ ������ �� �ִ� ����� �� 4 * 3 * 2 * 1 = 24���� �����ϹǷ� seed�� 24�� ���� ��������
		 * ���� ���� ����. (24������ �׳� switch�� ���°� �� ���� ���������� �ڵ尡 ������� ���� ���)
		 */
		// �� �ڸ����� '���� ����'�ؾ� �ϴ� ������ ���� ����. seed�� 24�� ���� �������� 0�� ��� 0000, 23�� ���
		// 3210�� ��.
		int[] offsets = new int[4];

		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;

		// ������ ������ offset�� ���� �� �ڸ��� ���� ���.
		// ��� ����� ������ offset�� 0000�̾��� �� ���� 0123�� �� (�����ϰ� offset�� 3210�̾��� ���� �� �迭
		// �״�� ���� ��)
		shuffledDirection_values = new int[4];

		for (int iCurrent = 0; iCurrent < 4; ++iCurrent) {
			int current_value = 0;

			while (true) {
				// ���� �ڸ����� �տ� �̹� ���� ���� �ִ��� �˻�
				boolean isSameValueFound = false;

				for (int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious)
					if (shuffledDirection_values[iPrevious] == current_value) {
						isSameValueFound = true;
						break;
					}

				// ���� ���� �ִ� ��� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻�
				if (isSameValueFound == true) {
					++current_value;
				}
				// ���� ���� ���� ���� �ڸ��� offset�� 0�� �ƴ� ���(���⼭ ���� �������Ѿ� �ϴ� ���)
				// offset�� 1 ���� ���� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻�
				else if (offsets[iCurrent] != 0) {
					--offsets[iCurrent];
					++current_value;
				}
				// ���� ���� ���� offset�� 0�� ��� �� ��� �Ϸ�
				else {
					break;
				}
			}

			// ����� ���� ���� �ڸ��� ���� ���
			shuffledDirection_values[iCurrent] = current_value;
		}

		// 0: Up, 1: Left, 2: Right, 3: Down���� �����Ͽ� �� �ڸ��� ���� ���� ���� ���� ����
		shuffledDirections = new DirectionCode[4];

		for (int i = 0; i < 4; ++i)
			switch (shuffledDirection_values[i]) {
			case 0:
				shuffledDirections[i] = DirectionCode.Up;
				break;
			case 1:
				shuffledDirections[i] = DirectionCode.Left;
				break;
			case 2:
				shuffledDirections[i] = DirectionCode.Right;
				break;
			default:
				shuffledDirections[i] = DirectionCode.Down;
				break;
			}
	}

	void SetBasePoint() {
		// �ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;

		if (seed <= 0)
			seed += Integer.MAX_VALUE;

		// seed�� ���ǽ��� �� ĭ ���� ���� �������� ���� ���� ��ǥ ����
		int base_y = seed
				% (Constants.Classroom_Width * Constants.Classroom_Height)
				/ Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;

		basePoint = new Point(base_x, base_y);
	}

	// ����ġ�� ����� �� �þ߿� �ִ� ���º� ���� ���
	int[] NumState1(PlayerInfo.State state) {
		int[] num_State = new int[4];
		for (PlayerInfo other : othersInfo_detected) {
			Vector v = GetDistanceVectorBetweenPlayers(other);
			// ���� �Ÿ��� 2�̸� ��쿡�� ����ġ�� �ξ� 2������ ���.
			if (other.GetState().equals(state)) {

				if (v.GetDistance() == 2) {

					// �ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
					if (v.y_offset < 0)
						num_State[0] = num_State[0] + 2;

					// �ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if (v.x_offset < 0)
						num_State[1] = num_State[1] + 2;

					// �ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if (v.x_offset > 0)
						num_State[2] = num_State[2] + 2;

					// �ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if (v.y_offset > 0)
						num_State[3] = num_State[3] + 2;
				}

				// �Ÿ��� 3�̻��̸� �׳� �Ѹ����� ���
				else {
					if (v.y_offset < 0)
						++num_State[0];

					// �ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if (v.x_offset < 0)
						++num_State[1];

					// �ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if (v.x_offset > 0)
						++num_State[2];

					// �ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if (v.y_offset > 0)
						++num_State[3];

				}
			}
		}
		return num_State;

	}

	int[] NumState2(PlayerInfo.State state) {
		int[] num_State = new int[4];
		for (PlayerInfo other : othersInfo_detected) {
			Vector v = GetDistanceVectorBetweenPlayers(other);
			// ���� �Ÿ��� 1�̸� ��쿡�� ����ġ�� �ξ� 2������ ���.
			if (other.GetState().equals(state)) {

				if (v.GetDistance() == 1) {

					// �ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
					if (v.y_offset < 0)
						num_State[0] = num_State[0] + 2;

					// �ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if (v.x_offset < 0)
						num_State[1] = num_State[1] + 2;

					// �ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if (v.x_offset > 0)
						num_State[2] = num_State[2] + 2;

					// �ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if (v.y_offset > 0)
						num_State[3] = num_State[3] + 2;
				}

				// �Ÿ��� 3�̻��̸� �׳� �Ѹ����� ���
				else {
					if (v.y_offset < 0)
						++num_State[0];

					// �ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
					if (v.x_offset < 0)
						++num_State[1];

					// �ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
					if (v.x_offset > 0)
						++num_State[2];

					// �ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
					if (v.y_offset > 0)
						++num_State[3];

				}
			}
		}
		return num_State;

	}
	

	public ��������������() {
		name = "��������������"; // TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false; // TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ�
										// �ƴ� ��� false�� �μ���.
		this.receiveOthersInfo_detected = true; // �� �þ��� ���� �޾ƿ�.

	}

	@Override
	public DirectionCode Survivor_Move() {
		// ���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
		// 0: Up, 1: Left, 2: Right, 3: Down
		int[] num_Infected = new int[4]; // ���� �� �� �ִ� �þ߿� �ִ� ����ü ���� ���⺰�� ����.
		int[] num_Survivor = new int[4]; // ���� �� �� �ִ� �þ߿� �ִ� �����ڼ��� ���⺰�� ����.
		int[] num_Corpse = new int[4]; // ���� �� �� �ִ� �þ߿� �ִ� ��ü���� ���⺰�� ����.
		int[] weights = new int[4]; // ���� �� �� �ִ� �þ߿� �ִ� ����ü, ������, ��ü���� ����� ���赵
									// ����.
		int min_weight = Integer.MAX_VALUE;

		// ó�� 10�ϱ����� ���� ���������� �޾Ҵ��� �𸣴ϱ� ������ó�� �ٸ� �÷��̾���� ���� �ٴ���.
		if (gameInfo.GetCurrentTurnNumber() < 11) {
			// ���� ������ ��� �÷��̾ ���� ���赵 ���
			for (PlayerInfo other : othersInfo_detected) {
				// �ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);

				// �ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
				if (v.y_offset < 0)
					++weights[0];

				// �ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
				if (v.x_offset < 0)
					++weights[1];

				// �ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
				if (v.x_offset > 0)
					++weights[2];

				// �ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
				if (v.y_offset > 0)
					++weights[3];
			}

		} else {
			// �� �þ߿� �ִ� ����ü���� ��������.
			num_Infected = NumState1(PlayerInfo.State.Infected);
			// �� �þ߿� �ִ� �����ڼ��� ��������.
			num_Survivor = NumState2(PlayerInfo.State.Survivor);
			// �� �þ߿� �ִ� ��ü���� ��������.
			num_Corpse = NumState2(PlayerInfo.State.Corpse);

			// ���赵�� ��������- ����ü : +5, ������ : -2, ��ü : +1
			weights[0] = num_Infected[0] * 5 + num_Survivor[0] * (-2) + num_Corpse[0];
					
			weights[1] = num_Infected[1] * 5 + num_Survivor[1] * (-2) + num_Corpse[1];
					
			weights[2] = num_Infected[2] * 5 + num_Survivor[2] * (-2) + num_Corpse[2];
					
			weights[3] = num_Infected[3] * 5 + num_Survivor[3] * (-2) + num_Corpse[3];
					
		}

		// �� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
		if (IsValidMove(DirectionCode.Up) == false)
			weights[0] = Integer.MAX_VALUE;

		if (IsValidMove(DirectionCode.Left) == false)
			weights[1] = Integer.MAX_VALUE;

		if (IsValidMove(DirectionCode.Right) == false)
			weights[2] = Integer.MAX_VALUE;

		if (IsValidMove(DirectionCode.Down) == false)
			weights[3] = Integer.MAX_VALUE;

		// ������ ���赵�� �ּҰ��� ������ ���
		for (int weight : weights)
			if (weight < min_weight)
				min_weight = weight;

		// '���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for (int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection) {
			if (weights[shuffledDirection_values[iShuffledDirection]] == min_weight)
				return shuffledDirections[iShuffledDirection];
		}

		// ������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
	}

	@Override
	public void Corpse_Stay() {
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		/*
		 * �������� ����ü �̵�: �� ��ġ�� ��ü�� �ִ� ��� ���ڸ��� �ӹ���. �׷��� ���� ��� ������ �� ��ü�� ���� ���� �������� �̵�.
		 * �ణ ������.
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
		if ( isCorpseHere == true || this.myScore.GetSurvivor_Max_Survived_Turns()<70)
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
					if(v.GetDistance()==1)
						 ++weights[2];
				
				if ( v.y_offset > 0 )
					if(v.GetDistance()==1)
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
		
		
		//�� �ٷο��� �������ִ��� Ȯ��.
		int num_Survivor=0;
		for (PlayerInfo other : othersInfo_detected) {
			Vector v = GetDistanceVectorBetweenPlayers(other);
			
			if (other.GetState().equals(PlayerInfo.State.Survivor)) {
				if (v.GetDistance() == 1)
					++num_Survivor;
			}
		}
					
		
		if(num_Survivor>1 || myInfo.GetHitPoint() >5)
			return DirectionCode.Stay;
			
		
		else{
		//'���� ����' ������ ���� ������ �� ��ü ���� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
				return shuffledDirections[iShuffledDirection];
		}
		//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
		return DirectionCode.Stay;
	}


	@Override
	public void Soul_Stay() {
		if (gameInfo.GetCurrentTurnNumber() == 0) {

			ShuffleDirections();
			SetBasePoint();

			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ����
			// ȣ��˴ϴ�.
		}

		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn() {
		Point pointToSpawn = basePoint;

		// �� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		int min_weight = Integer.MAX_VALUE;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		ArrayList<Point> list_pos_min_weight = new ArrayList<Point>();

		// ��� ����ü�� ���� �˻� ���� - 9x9ĭ�� �÷��̾�� 40�� �Ұ��ϹǷ� ����ü�� ���� ���� ĭ ����� ���ÿ� ����
		for (PlayerInfo other : othersInfo_withinSight) {

			// ��� ����ü�� ���� �迭 ���� �� �ִ밪 ���, �ִ밪�� ���� ĭ�� ���� ��ǥ ��� ����
			if (other.GetState() == PlayerInfo.State.Infected) {
				Point pos_other = other.GetPosition();

				++weights[pos_other.y][pos_other.x];

				// �ִ� ������ 60�� ������ ����ü�� ���� ������ ��������.
				if (myScore.GetSurvivor_Max_Survived_Turns() > 60) {

					// �ִ밪�� �ٲ���ٸ� '����ü�� ���� ���� ĭ' ��� �ʱ�ȭ
					if (weights[pos_other.y][pos_other.x] > max_weight) {
						++max_weight; // weight�� �׻� 1�� �����ϹǷ� �翬�� �ִ밪�� 1�� ������
						list_pos_max_weight.clear();
					}

					// ���� ĭ�� ���� �ִ밪�� ���ٸ� ���� ĭ�� '����ü�� ���� ���� ĭ' ��Ͽ� �߰� (������ �ִ밪��
					// �ٲ���ٸ� �׻� �߰���)
					if (weights[pos_other.y][pos_other.x] == max_weight)
						list_pos_max_weight.add(pos_other);

					int min_distance = Integer.MAX_VALUE;
					Point myPosition = myInfo.GetPosition();

					for (Point pos_max_weight : list_pos_max_weight) {
						int distance = GetDistance(myPosition, pos_max_weight);

						if (distance < min_distance) {
							min_distance = distance;
							pointToSpawn = pos_max_weight;
						}

					}
				}
				// �ִ� �������� 55�����̸� ����ü�� ���� ���� ���� ��ġ.

				else {
					if (weights[pos_other.y][pos_other.x] < min_weight) {
						++min_weight; // weight�� �׻� 1�� �����ϹǷ� �翬�� �ִ밪�� 1�� ������
						list_pos_min_weight.clear();
					}

					// ���� ĭ�� ���� �ּڰ��� ���ٸ� ���� ĭ�� '����ü�� ���� ���� ĭ' ��Ͽ� �߰� (������ �ּڰ���
					// �ٲ���ٸ� �׻� �߰���)
					if (weights[pos_other.y][pos_other.x] == min_weight)
						list_pos_min_weight.add(pos_other);

					int max_distance = -1;
					Point myPosition = myInfo.GetPosition();

					for (Point pos_min_weight : list_pos_min_weight) {
						int distance = GetDistance(myPosition, pos_min_weight);

						if (distance < max_distance) {
							max_distance = distance;
							pointToSpawn = pos_min_weight;
						}
					}

				}

			}
		}

		return pointToSpawn;
	}
}
