package l4g2ep1.customplayers;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class ����� extends Player {
	
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	
	void ShuffleDirections()
	{
		//�ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		
		/*
		 * �� ���� ������ ������ �� �ִ� ����� �� 4 * 3 * 2 * 1 = 24���� �����ϹǷ�
		 * seed�� 24�� ���� �������� ���� ���� ����.
		 * (24������ �׳� switch�� ���°� �� ���� ���������� �ڵ尡 ������� ���� ���)
		 */
		//�� �ڸ����� '���� ����'�ؾ� �ϴ� ������ ���� ����. seed�� 24�� ���� �������� 0�� ��� 0000, 23�� ��� 3210�� ��.
		int[] offsets = new int[4];
		
		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;

		//������ ������ offset�� ���� �� �ڸ��� ���� ���.
		//��� ����� ������ offset�� 0000�̾��� �� ���� 0123�� �� (�����ϰ� offset�� 3210�̾��� ���� �� �迭 �״�� ���� ��)
		shuffledDirection_values = new int[4];

		for ( int iCurrent = 0; iCurrent < 4; ++iCurrent )
		{
			int current_value = 0;
			
			while ( true )
			{
				//���� �ڸ����� �տ� �̹� ���� ���� �ִ��� �˻� 
				boolean isSameValueFound = false;
				
				for ( int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious )
					if ( shuffledDirection_values[iPrevious] == current_value )
					{
						isSameValueFound = true;
						break;
					}
				
				//���� ���� �ִ� ��� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻�
				if ( isSameValueFound == true )
				{
					++current_value;
				}
				//���� ���� ���� ���� �ڸ��� offset�� 0�� �ƴ� ���(���⼭ ���� �������Ѿ� �ϴ� ���)
				//offset�� 1 ���� ���� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻� 
				else if ( offsets[iCurrent] != 0 )
				{
					--offsets[iCurrent];
					++current_value;
				}
				//���� ���� ���� offset�� 0�� ��� �� ��� �Ϸ�
				else
				{
					break;
				}
			}
			
			//����� ���� ���� �ڸ��� ���� ���
			shuffledDirection_values[iCurrent] = current_value;
		}
				
		//0: Up, 1: Left, 2: Right, 3: Down���� �����Ͽ� �� �ڸ��� ���� ���� ���� ���� ���� 
		shuffledDirections = new DirectionCode[4];
		
		for ( int i = 0; i < 4; ++i )
			switch ( shuffledDirection_values[i] )
			{
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


	public �����() {
		name = "�����"; // TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false;
		receiveOthersInfo_detected = true;
		
		// TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ�
										// �ƴ� ��� false�� �μ���.
	}

	@Override
	public DirectionCode Survivor_Move() {
			 /* �������� ������ �̵�: ������ �� x ��ü�� ����ü ���� ���� ū ������ ����
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
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];

			//������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
			return DirectionCode.Stay;
		}
		// TODO ������ ������ �� �̵��ϱ� ���� ������ ���⿡ ��������.
	
	@Override
	public void Corpse_Stay()
	{
	
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	int MS;// ���� 0 ���� 1
	DirectionCode[][] DC;// ���鼭 ���� �ڵ�
	int i;

	@Override
	public DirectionCode Infected_Move() {
		Point ME = myInfo.GetPosition();
		int x, y;

		if (MS == 0) {
			if (ME.x >= 4 && ME.y < 4) {
				x = 7 - ME.x;
				y = 1 - ME.y;

				if (x < 0) {

					x *= (-1);
				}

				if (y < 0) {

					y *= (-1);
				}

				if (x >= y) { // �̰� ���η� ���°���

					if (ME.x > 7) {

						return DirectionCode.Left;
					}

					if (ME.x < 7) {

						return DirectionCode.Right;
					}

					MS = 1;
					i = 0;

				}

				if (x < y) {// �̰� ���η� ������

					if (ME.y > 1) {

						return DirectionCode.Up;
					}

					if (ME.y < 1) {

						return DirectionCode.Down;
					}

				}
			}// 1��и��϶�

			if (ME.x < 4 && ME.y < 4) {
				x = 1 - ME.x;
				y = 1 - ME.y;

				if (x < 0) {

					x *= (-1);
				}

				if (y < 0) {

					y *= (-1);
				}
				
				if (x >= y) { // �̰� ���η� ���°���

					if (ME.x > 1) {

						return DirectionCode.Left;
					}

					if (ME.x < 1) {

						return DirectionCode.Right;
					}

					MS = 1;
					i = 0;

				}

				if (x < y) {// �̰� ���η� ������

					if (ME.y > 1) {

						return DirectionCode.Up;
					}

					if (ME.y < 1) {

						return DirectionCode.Down;
					}

				}

			}// 2��и�

			if (ME.x < 4 && ME.y >= 4) {
				x = 1 - ME.x;
				y = 7 - ME.y;

				if (x < 0) {

					x *= (-1);
				}

				if (y < 0) {

					y *= (-1);
				}
				
				if (x >= y) { // �̰� ���η� ���°���

					if (ME.x > 1) {

						return DirectionCode.Left;
					}

					if (ME.x < 7) {

						return DirectionCode.Right;
					}

					MS = 1;
					i = 0;

				}

				if (x < y) {// �̰� ���η� ������

					if (ME.y > 1) {

						return DirectionCode.Up;
					}

					if (ME.y < 1) {

						return DirectionCode.Down;
					}

				}
				
				if (x >= y) { // �̰� ���η� ���°���

					if (ME.x > 7) {

						return DirectionCode.Left;
					}

					if (ME.x < 7) {

						return DirectionCode.Right;
					}

					MS = 1;
					i = 0;

				}

				if (x < y) {// �̰� ���η� ������

					if (ME.y > 7) {

						return DirectionCode.Up;
					}

					if (ME.y < 7) {

						return DirectionCode.Down;
					}

				}
			}// 3

			if (ME.x >= 4 && ME.y >= 4) {
				x = 7 - ME.x;
				y = 7 - ME.y;

				if (x < 0) {

					x *= (-1);
				}

				if (y < 0) {

					y *= (-1);
				}
				
				if (x >= y) { // �̰� ���η� ���°���

					if (ME.x > 7) {

						return DirectionCode.Left;
					}

					if (ME.x < 7) {

						return DirectionCode.Right;
					}

					MS = 1;
					i = 0;

				}

				if (x < y) {// �̰� ���η� ������

					if (ME.y > 7) {

						return DirectionCode.Up;
					}

					if (ME.y < 7) {

						return DirectionCode.Down;
					}
				}
			}// 4
			

		}

		if(MS==1){
			
			if (ME.x >= 4 && ME.y < 4){
				
				DirectionCode Use = DC[0][i];

				i++;
				i %= 8;
				
				return Use;
			}
			if (ME.x < 4 && ME.y < 4){
				
				DirectionCode Use = DC[1][i];

				i++;
				i %= 8;
				
				return Use;
			}
			if (ME.x < 4 && ME.y >= 4){
				
				DirectionCode Use = DC[2][i];

				i++;
				i %= 8;
				
				return Use;
			}
			if (ME.x >= 4 && ME.y >= 4){
				
				DirectionCode Use = DC[3][i];

				i++;
				i %= 8;
				
				return Use;
			}
			
		}
		// TODO ����ü ������ �� �̵� �Ǵ� ����ϱ� ���� ������ ���⿡ ��������.
		return null;
	}

	@Override
	public void Soul_Stay() {
		if (gameInfo.GetCurrentTurnNumber() == 0) {
			spawn = new Point[4];
			spawn[0] = new Point(3, 3);
			spawn[1] = new Point(3, 5);
			spawn[2] = new Point(5, 5);
			spawn[3] = new Point(5, 3);
			
			DC = new DirectionCode[4][8];
			
			DC[0][0] = DirectionCode.Right;
			DC[0][1] = DirectionCode.Up;
			DC[0][2] = DirectionCode.Left;
			DC[0][3] = DirectionCode.Down;
			DC[0][4] = DirectionCode.Down;
			DC[0][5] = DirectionCode.Left;
			DC[0][6] = DirectionCode.Up;
			DC[0][7] = DirectionCode.Right;
			
			DC[1][0] = DirectionCode.Up;
			DC[1][1] = DirectionCode.Left;
			DC[1][2] = DirectionCode.Down;
			DC[1][3] = DirectionCode.Right;
			DC[1][4] = DirectionCode.Right;
			DC[1][5] = DirectionCode.Down;
			DC[1][6] = DirectionCode.Left;
			DC[1][7] = DirectionCode.Up;
			
			DC[2][0] = DirectionCode.Down;
			DC[2][1] = DirectionCode.Left;
			DC[2][2] = DirectionCode.Up;
			DC[2][3] = DirectionCode.Right;
			DC[2][4] = DirectionCode.Right;
			DC[2][5] = DirectionCode.Up;
			DC[2][6] = DirectionCode.Left;
			DC[2][7] = DirectionCode.Down;

			DC[3][0] = DirectionCode.Down;
			DC[3][1] = DirectionCode.Right;
			DC[3][2] = DirectionCode.Up;
			DC[3][3] = DirectionCode.Left;
			DC[3][4] = DirectionCode.Left;
			DC[3][5] = DirectionCode.Up;
			DC[3][6] = DirectionCode.Right;
			DC[3][7] = DirectionCode.Down;
			
			ShuffleDirections();
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ����
			// ȣ��˴ϴ�.
			
		}

		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	Point[] spawn;
	int next;

	@Override
	public Point Soul_Spawn() {
		Point Use = spawn[next];

		next++;
		next %= 4;

		MS = 0;
		return Use; // TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
	}

}
