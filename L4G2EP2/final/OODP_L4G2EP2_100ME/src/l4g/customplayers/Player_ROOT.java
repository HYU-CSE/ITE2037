package l4g.customplayers;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_ROOT extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_ROOT(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "�� �̷� ��ɲ��̾�");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = true;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}
	
	/*
	 * TODO#5	���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���.
	 * 			�翬�� �� �濡 �� �� ������, �߰��߰� �ڵ带 ����� �δ� ���� ������,
	 * 			�ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * 			L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * 			�������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�.
	 * 			�׷��� �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * 			��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����,
	 * 			'�ڵ忡 ����̶��� ���� �� ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */
	
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		int priority[] = {0,0,0,0}; // ��,�Ʒ�,����,������
		// ����ü�� �߰ߵ� ���
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
		{
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				if(cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected) > 0)
				{
					if (iRow < myInfo.position.row)	priority[0] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
					if (iRow > myInfo.position.row) priority[1]	+= cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
					if (iColumn < myInfo.position.column)	priority[2] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
					if (iColumn > myInfo.position.column)	priority[3] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
		}
		if (priority[0]+priority[1]+priority[2]+priority[3] > 0)
		{
			int select[] = {0,0,0,0};
			int copied_prior[] = {0,0,0,0};
			for (int i = 0; i < 4; ++i) copied_prior[i] = priority[i];
			//�켱���� ����
			for (int j = 0; j < 4; ++j)
			{
				int iMax = 0;
				for (int i = 0; i < 4; ++i)
				{
					if (copied_prior[i] > copied_prior[iMax])	iMax = i;
				}
				select[j] = iMax;
				copied_prior[iMax] = -1;
			}
			//�̵� �õ�
			for (int i = 0; i < 4; ++i)
			{
				if (select[i]==0 && myInfo.position.row > 0)	return DirectionCode.Up;
				if (select[i]==1 && myInfo.position.row < Constants.Classroom_Height)	return DirectionCode.Down;
				if (select[i]==2 && myInfo.position.column > 0)	return DirectionCode.Left;
				if (select[i]==3 && myInfo.position.column < Constants.Classroom_Width)	return DirectionCode.Right;
			}
		}
		// ����ü�� �߰����� ���� ���
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
		{
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				if(cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor) > 0)
				{
					if (iRow < myInfo.position.row)	priority[0] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
					if (iRow > myInfo.position.row) priority[1]	+= cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
					if (iColumn < myInfo.position.column)	priority[2] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
					if (iColumn > myInfo.position.column)	priority[3] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
				}
			}
		}
		if (priority[0]+priority[1]+priority[2]+priority[3] > 0)
		{
			int select[] = {0,0,0,0};
			int copied_prior[] = {0,0,0,0};
			for (int i = 0; i < 4; ++i) copied_prior[i] = priority[i];
			//�켱���� ����
			for (int j = 0; j < 4; ++j)
			{
				int iMax = 0;
				for (int i = 0; i < 4; ++i)
				{
					if (copied_prior[i] > copied_prior[iMax])	iMax = i;
				}
				select[j] = iMax;
				copied_prior[iMax] = -1;
			}
			//�̵� �õ�
			for (int i = 0; i < 4; ++i)
			{
				if (select[i]==0 && myInfo.position.row > 0)	return DirectionCode.Up;
				if (select[i]==1 && myInfo.position.row < Constants.Classroom_Height)	return DirectionCode.Down;
				if (select[i]==2 && myInfo.position.column > 0)	return DirectionCode.Left;
				if (select[i]==3 && myInfo.position.column < Constants.Classroom_Width)	return DirectionCode.Right;
			}
		}
		//�ƹ��� �߰����� �� �� ���
		priority[0] = myInfo.position.row - Constants.Classroom_Height/2;
		priority[1] = Constants.Classroom_Height/2 - myInfo.position.row;
		priority[2] = myInfo.position.column - Constants.Classroom_Width/2;
		priority[3] = Constants.Classroom_Width/2 - myInfo.position.column;
		int select[] = {0,0,0,0};
		int copied_prior[] = {0,0,0,0};
		for (int i = 0; i < 4; ++i) copied_prior[i] = priority[i];
		//�켱���� ����
		for (int j = 0; j < 4; ++j)
		{
			int iMax = 0;
			for (int i = 0; i < 4; ++i)
			{
				if (copied_prior[i] > copied_prior[iMax])	iMax = i;
			}
			select[j] = iMax;
			copied_prior[iMax] = -9999;
		}
		//�̵� �õ�
		for (int i = 0; i < 4; ++i)
		{
			if (select[i]==0 && myInfo.position.row > 0)	return DirectionCode.Up;
			if (select[i]==1 && myInfo.position.row < Constants.Classroom_Height)	return DirectionCode.Down;
			if (select[i]==2 && myInfo.position.column > 0)	return DirectionCode.Left;
			if (select[i]==3 && myInfo.position.column < Constants.Classroom_Width)	return DirectionCode.Right;
		}
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = null;
		int priority[] = {0,0,0,0}; // ��,�Ʒ�,����,������
		// �����ڰ� �߰ߵ� ���
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
		{
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				if(cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor) > 0)
				{
					if (iRow < myInfo.position.row)	priority[0] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
					if (iRow > myInfo.position.row) priority[1]	+= cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
					if (iColumn < myInfo.position.column)	priority[2] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
					if (iColumn > myInfo.position.column)	priority[3] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Survivor);
				}
			}
		}
		if (priority[0]+priority[1]+priority[2]+priority[3] > 0)
		{
			int select[] = {0,0,0,0};
			int copied_prior[] = {0,0,0,0};
			for (int i = 0; i < 4; ++i) copied_prior[i] = priority[i];
			//�켱���� ����
			for (int j = 0; j < 4; ++j)
			{
				int iMax = 0;
				for (int i = 0; i < 4; ++i)
				{
					if (copied_prior[i] > copied_prior[iMax])	iMax = i;
				}
				select[j] = iMax;
				copied_prior[iMax] = -1;
			}
			//�̵� �õ�
			for (int i = 0; i < 4; ++i)
			{
				if (select[i]==0 && myInfo.position.row > 0)	return DirectionCode.Up;
				if (select[i]==1 && myInfo.position.row < Constants.Classroom_Height)	return DirectionCode.Down;
				if (select[i]==2 && myInfo.position.column > 0)	return DirectionCode.Left;
				if (select[i]==3 && myInfo.position.column < Constants.Classroom_Width)	return DirectionCode.Right;
			}
		}
		// ����ü�� �߰����� ���� ���
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
		{
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				if(cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected) > 0)
				{
					if (iRow < myInfo.position.row)	priority[0] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
					if (iRow > myInfo.position.row) priority[1]	+= cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
					if (iColumn < myInfo.position.column)	priority[2] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
					if (iColumn > myInfo.position.column)	priority[3] += cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
				}
			}
		}
		if (priority[0]+priority[1]+priority[2]+priority[3] > 0)
		{
			int select[] = {0,0,0,0};
			int copied_prior[] = {0,0,0,0};
			for (int i = 0; i < 4; ++i) copied_prior[i] = priority[i];
			//�켱���� ����
			for (int j = 0; j < 4; ++j)
			{
				int iMax = 0;
				for (int i = 0; i < 4; ++i)
				{
					if (copied_prior[i] > copied_prior[iMax])	iMax = i;
				}
				select[j] = iMax;
				copied_prior[iMax] = -1;
			}
			//�̵� �õ�
			for (int i = 0; i < 4; ++i)
			{
				if (select[i]==0 && myInfo.position.row > 0)	return DirectionCode.Up;
				if (select[i]==1 && myInfo.position.row < Constants.Classroom_Height)	return DirectionCode.Down;
				if (select[i]==2 && myInfo.position.column > 0)	return DirectionCode.Left;
				if (select[i]==3 && myInfo.position.column < Constants.Classroom_Width)	return DirectionCode.Right;
			}
		}
		//�ƹ��� �߰����� �� �� ���
		priority[0] = myInfo.position.row - Constants.Classroom_Height/2;
		priority[1] = Constants.Classroom_Height/2 - myInfo.position.row;
		priority[2] = myInfo.position.column - Constants.Classroom_Width/2;
		priority[3] = Constants.Classroom_Width/2 - myInfo.position.column;
		int select[] = {0,0,0,0};
		int copied_prior[] = {0,0,0,0};
		for (int i = 0; i < 4; ++i) copied_prior[i] = priority[i];
		//�켱���� ����
		for (int j = 0; j < 4; ++j)
		{
			int iMax = 0;
			for (int i = 0; i < 4; ++i)
			{
				if (copied_prior[i] > copied_prior[iMax])	iMax = i;
			}
			select[j] = iMax;
			copied_prior[iMax] = -9999;
		}
		//�̵� �õ�
		for (int i = 0; i < 4; ++i)
		{
			if (select[i]==0 && myInfo.position.row > 0)	return DirectionCode.Up;
			if (select[i]==1 && myInfo.position.row < Constants.Classroom_Height)	return DirectionCode.Down;
			if (select[i]==2 && myInfo.position.column > 0)	return DirectionCode.Left;
			if (select[i]==3 && myInfo.position.column < Constants.Classroom_Width)	return DirectionCode.Right;
		}
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		if ( turnInfo.turnNumber == 0 )
		{	
			return new Point(Constants.Classroom_Width/2, Constants.Classroom_Height/2);
		}
		int result_row = 0;
		int result_column = 0;
		int[][] zombieradar = new int [Constants.Classroom_Height][Constants.Classroom_Width];
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
		{
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				zombieradar[iRow][iColumn] = cells[iRow][iColumn].CountIf_Players(player->player.state == StateCode.Infected);
			}
		}
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
		{
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				if (zombieradar[result_row][result_column] < zombieradar[iRow][iColumn])
				{
					result_row = iRow;
					result_column = iColumn;
				}
			}
		}
		return new Point(result_row, result_column);
	}
}
