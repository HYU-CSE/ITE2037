package l4g.customplayers;

import java.util.function.Predicate;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_NO_ANSWER extends Player
{
	//���� ���� ��ġ�� ��,������ �Է��ϰ� ������� ��ġ ��,������ �Է��� ���� ���� �� ��ġ ��ȯ
	private DirectionCode WhereToGo(int myRow, int myCol, int targetRow, int targetCol)
	{
		int x, y, absx, absy;
		x= myRow-targetRow; // ��(����) ���̰� ��ȯ
		y= myCol-targetCol; // ��(����) ���̰� ��ȯ
		absx = x>0 ? x : -x;// ��(����) ���̰��� ���밪
		absy = y>0 ? y : -y;// ��(����) ���̰��� ���밪
		
		if(absx>absy) //�� ,�Ʒ�(����, ��) ���� 
		{
			if(x>0) 
				return DirectionCode.Up;
			else
				return DirectionCode.Down;
		}
		else //�� ,��(����, ��) ���� 
		{
			if(y>0)
				return DirectionCode.Left;
			else
				return DirectionCode.Right;
		}
	}
	
	//�߾��̶�� true , �ƴϸ� false
	private boolean IsCenter(int row, int col)
	{
		// (5,5) (5,6) (5,7)
		// (6,5) (6,6) (6,7)
		// (7,5) (7,6) (7,7)
		// �� 9���� ��ǥ�� �߾��̶�� �����Ѵ�.
		
		if( (5<=row && row <=7) && ((5<=col)&&(col<=7))  )
			return true;
		else
			return false;
	}
		
	//IsCenter�� false �϶� ���, �߾����� ����.
	private DirectionCode GoCenter(int row, int col)
	{
		return WhereToGo(row, col, 6, 6);
	}
	
	//��, ��, ��, �� �������ϴ� ���⿡ ���� �������� Ȯ�� �ִٸ� true, ���ٸ� false
	private boolean IsWall(int row, int col, DirectionCode direction)
	{
		//���� �����ϴµ� ���� 0�̰ų�, �Ʒ��� �����ϴµ� ���� 12�̰ų�
		//�������� �����ϴµ� ���� 0�̰ų�, ���������� �����ϴµ� ���� 12�� ���̹Ƿ� 
		if(direction==DirectionCode.Up && row==0)
			return true; // �������ϴ� ���⿡ ���� �ִ�
		else if(direction==DirectionCode.Down && row==Constants.Classroom_Height-1)
			return true; // �������ϴ� ���⿡ ���� �ִ�
		else if(direction==DirectionCode.Left && col==0 )
			return true; // �������ϴ� ���⿡ ���� �ִ�
		else if(direction==DirectionCode.Right && col==Constants.Classroom_Width-1)
			return true; // �������ϴ� ���⿡ ���� �ִ�
		else
			return false; // ���� �ƴϴϱ� �Ƚ��ϰ� ����
	}
	
	//������ ����� �� ���Ǵ� �޼ҵ�, �þ߿��� �����ڰ� ���帹�� �������� ���Ѵ�
	private DirectionCode WhereToKill(int row , int col)
	{

		int numOfSurvivor, maxNum=0, maxRow=0, maxCol=0;
		 
		for(int i=row-2; i<=row+2 ; i++)
			for(int j=col-2; j<=col+2 ; j++)
			{
				//���ձ����
				if((0<=i && i<Constants.Classroom_Width )&&(0<=j && j<Constants.Classroom_Height))
				{
					numOfSurvivor = cells[i][j].CountIf_Players(player -> player.state == StateCode.Survivor);
					if(maxNum<numOfSurvivor){
						maxNum= numOfSurvivor;
						maxRow =i;
						maxCol= j;
					}
				}
			}
	
		return WhereToGo(row, col, maxRow, maxCol);	
	}
	
	//������ ����� �� ������ ���������� �ڻ��ϱ�!!
	private DirectionCode WhereToSurvivorGo(int row , int col)
	{
		int numOfInfected;
		int minRow=6, minCol=6;
		for (int i = row-1; i <=row+1 ; ++i)
		{
			for (int j = col-1; j <=col+1; ++j)
			{
				
				//�� �ձ����
				if((0<=i && i<Constants.Classroom_Width )&&(0<=j && j<Constants.Classroom_Height))
				{
					numOfInfected= cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
					if(1<=numOfInfected && numOfInfected<=2)
					{	
						minRow =i;
						minCol =j;
					}
				}
			}
		}
		
		return WhereToGo(row, col, minRow, minCol);
		
	}
	
	
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_NO_ANSWER(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "����߳��");
		
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
		//���� �׳� �ڻ��� �ϰ� �ͽ��ϴ�.
		DirectionCode result = null;
		int row = this.myInfo.position.row;
		int col = this.myInfo.position.column;
		
		
			result = WhereToSurvivorGo(row, col);
			if(IsWall(row, col, result))
				result = GoCenter(row, col);
		
			
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
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
		
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		int row = this.myInfo.position.row;
		int col = this.myInfo.position.column;
		
		DirectionCode result = null;
		
		//50ź������ ���������̷����ƴٴϱ���
		if(turnInfo.turnNumber <=50)
		{
			result = WhereToKill(row, col);
			//���࿡ ���̶�� �߾����� ����
			if(IsWall(row, col, result))
				result = GoCenter(row, col);
		
		}
		else if(turnInfo.turnNumber <=100) // 50~100ź������ �ε��ڼ����
		{
			result = DirectionCode.Stay;
		}
		else  //100ź~121���� �߾����� �ö󰡼� ��ȭ�⵵
		{
			if(IsCenter(row, col))
				result = DirectionCode.Stay;
			else
				result = GoCenter(row, col);
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
		int result_row=6;
		int result_column=6;
		int numOfInfecteds, maxNum=0, maxRow=0, maxCol=0;

		if(this.turnInfo.turnNumber==0 )
		{
			result_row = 6;
			result_column= 6;
		}
		else if(this.turnInfo.turnNumber<=30 ) // 1~30ź�� �׳� �߾ӿ��� ������
		{
		
			for(int i=4; i<Constants.Classroom_Height-4 ; i++)
				for(int j=4; j<Constants.Classroom_Width-4 ; j++)
				{
					numOfInfecteds = cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
					if(numOfInfecteds==0){
						maxNum= numOfInfecteds;
						maxRow =i;
						maxCol= j;
					}
				}
			
			result_row = maxRow;
			result_column = maxCol;
			
		}
		else // 31ź���� 121ź������ �����ڼ��� ���ϸ��������� ������
		{
		for(int i=0; i<Constants.Classroom_Height ; i++)
			for(int j=0; j<Constants.Classroom_Width ; j++)
			{
				numOfInfecteds = cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
				if(maxNum<numOfInfecteds){
					maxNum= numOfInfecteds;
					maxRow =i;
					maxCol= j;
				}
			}
		
		result_row = maxRow;
		result_column = maxCol;
		}
		
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return new Point(result_row, result_column);
	}
}
