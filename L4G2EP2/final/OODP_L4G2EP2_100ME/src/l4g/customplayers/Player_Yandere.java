package l4g.customplayers;

import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 * ����ؼ� �� ����� �Ѿƴٴմϴ�.
 * ó������ �Ѿƴٴ� ����� �������Դϴ�.
 * �Ѿƴٴ� ����� �����ڵ� �����ڵ� �� ����ٴմϴ�.
 * ���� �����ڵ� �����ڵ� �� ���� ����� ����ٴմϴ�.
 * ���� �������� ���, ������ �־�� �ϴ� ��Ȳ������ �¿� �ݺ� �̵��� �մϴ�.
 * �Ѿƴٴ� ����� ��ȥ ������ ��� ���ο� ����� ã���ϴ�.
 *
 */
public class Player_Yandere extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_Yandere(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "Yandere");
		
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
	
	int loveID;	// �Ѿƴٴ� ID
	int spawn_row=3;
	int spawn_col=3;
	int lrCheck=0;
	
	// �Ѿƴٴ� ID�� ���ϴ� �Լ�
	public void FindLoveID()
	{
		int rowArr[]={-1,0,0,1};
		int colArr[]={0,-1,1,0};
		loveID = 0;
		
		// ���� ��ȥ ���°� �ƴ� ��쿡�� �� �����¿츸 �˻�(�� ĭ �̻� �������� �� ��)
		if( myInfo.state != StateCode.Soul ){
			for( int i = 0; i < 4; i++ ){
				if( loveID != 0 ) break;
				if( myInfo.position.row+rowArr[i] >= 0 && myInfo.position.row+rowArr[i] < Constants.Classroom_Height && myInfo.position.column+colArr[i] >= 0 && myInfo.position.column+colArr[i] < Constants.Classroom_Width ){
					cells[myInfo.position.row+rowArr[i]][myInfo.position.column+colArr[i]].ForEach_Players(player ->
					{
						if( player.state == StateCode.Survivor ){
							loveID = player.ID;
						}
					});
				}
			}
		}
		// ���� ��ȥ ������ ��� 0,0���� ������ �� �˻�
		for (int row = 0; row < Constants.Classroom_Height; ++row )
		{
			if( loveID != 0 ) break;
			for (int col = 0; col < Constants.Classroom_Width; ++col )
			{
				if( loveID != 0 ) break;
				cells[row][col].ForEach_Players(player ->
				{
					if( player.state == StateCode.Survivor ){
						loveID = player.ID;
						spawn_row = player.position.row;
						spawn_col = player.position.column;
					}
				});
			}
		}
	}
	DirectionCode iFoundYou;
	int iForFindYou;
	// �Ѿƴٴϰ��� �ϴ� ID�� ��� �ִ��� ã�Ƴ��� �Լ�
	public void WhereAreYou()
	{
		int rowArr[]={-1,0,0,1};
		int colArr[]={0,-1,1,0};
		iFoundYou = null;
		
		for( iForFindYou = 0; iForFindYou < 4; iForFindYou++ ){
			if( myInfo.position.row+rowArr[iForFindYou] >= 0 && myInfo.position.row+rowArr[iForFindYou] < Constants.Classroom_Height && myInfo.position.column+colArr[iForFindYou] >= 0 && myInfo.position.column+colArr[iForFindYou] < Constants.Classroom_Width ){
				cells[myInfo.position.row+rowArr[iForFindYou]][myInfo.position.column+colArr[iForFindYou]].ForEach_Players(player ->
				{
					if( player.ID == loveID ){
						if( iForFindYou == 0 ) iFoundYou = DirectionCode.Up;
						if( iForFindYou == 1 ) iFoundYou = DirectionCode.Left;
						if( iForFindYou == 2 ) iFoundYou = DirectionCode.Right;
						if( iForFindYou == 3 ) iFoundYou = DirectionCode.Down;
					}
				});
			}
		}
		// ���� loveID�� ����� id��� �� �� �� ����
		if( iFoundYou == null ){
			FindLoveID();
			for( iForFindYou = 0; iForFindYou < 4; iForFindYou++ ){
				if( myInfo.position.row+rowArr[iForFindYou] >= 0 && myInfo.position.row+rowArr[iForFindYou] < Constants.Classroom_Height && myInfo.position.column+colArr[iForFindYou] >= 0 && myInfo.position.column+colArr[iForFindYou] < Constants.Classroom_Width ){
					cells[myInfo.position.row+rowArr[iForFindYou]][myInfo.position.column+colArr[iForFindYou]].ForEach_Players(player ->
					{
						if( player.ID == loveID ){
							if( iForFindYou == 0 ) iFoundYou = DirectionCode.Up;
							if( iForFindYou == 1 ) iFoundYou = DirectionCode.Left;
							if( iForFindYou == 2 ) iFoundYou = DirectionCode.Right;
							if( iForFindYou == 3 ) iFoundYou = DirectionCode.Down;
						}
					});
				}
			}
		}
		// (���� ������ loveID�� �� ã�´ٸ�)
		if( iFoundYou == null ){
			if( lrCheck == 0 ){
				if( myInfo.position.column+1 < Constants.Classroom_Width ){
					iFoundYou = DirectionCode.Right;
				}else{
					iFoundYou = DirectionCode.Left;
				}
				lrCheck = 1;
			}else{
				if( myInfo.position.column-1 >= 0 ){
					iFoundYou = DirectionCode.Left;
				}else{
					iFoundYou = DirectionCode.Right;
				}
				lrCheck = 0;
			}
		}
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		WhereAreYou();
		return iFoundYou;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		WhereAreYou();
		return iFoundYou;
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
		FindLoveID();

		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		return new Point(spawn_row, spawn_col);
	}
}

