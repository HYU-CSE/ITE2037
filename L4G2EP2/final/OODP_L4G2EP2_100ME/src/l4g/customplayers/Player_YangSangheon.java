package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_YangSangheon extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_YangSangheon(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "�߰�������");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
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
	
	//////////////////////////////////���⼭���� ���ڵ�
	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�.
	 * �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[] directions = new DirectionCode[4];
	
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
		 */
		 directions[0] = DirectionCode.Up;
		 directions[1] = DirectionCode.Left;
		 directions[2] = DirectionCode.Right;
	     directions[3] = DirectionCode.Down;
		  
		 favoritePoint.row = 2;
		 favoritePoint.column = 2;
		  
		 // ...�� ���� �������� �����ϴ� �켱 ������ �׳� �ٷ� �Ҵ��� �ᵵ �����մϴ�.
		 // (Bot���� �Ȱ��� Ŭ������ �ν��Ͻ��� ���� �����ϹǷ� �̷� ���� �ؾ� �մϴ�).
		 //
		

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
	///////////////////////////////////////////////////////////������� ���ڵ�
	
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
		/**�� ����**/
		//�ִ��� ���� �����ϴ°��� � �ñ��, �� �߰�Ƚ�� ������ �븮���� 
		//�þ߸� ���� �а� ����Ҽ� �ִ� ������ [(2,2)-(2,10)-(10,2)-(10,10)] ���(���簢�� ���)�� �ݽð�������θ� �����δ�.
		//�����װԵǵ� �� ��¿�� ����.
		
		if(myInfo.position.row< 10&&myInfo.position.row>= 2&&myInfo.position.column== 2){
				result=DirectionCode.Down;//(2,2)~(9,2)�϶� �Ʒ��� �̵�
			}
		else if(myInfo.position.row==10&&myInfo.position.column>=2&&myInfo.position.column<10){
				result=DirectionCode.Right;//(10,2)~(10,9)�϶� ���������� �̵�
			}
		else if(myInfo.position.row>2&&myInfo.position.row<=10&&myInfo.position.column==10){
				result = DirectionCode.Up;//(10,10)~(3,10)�϶� �������� �̵�
			}
		else if(myInfo.position.row==2&&myInfo.position.column<=10&&myInfo.position.column> 2){
			result = DirectionCode.Down;//(2,10)~(2,3)�϶� �������� �̵�
		}
			
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
		
		//�׷��� �����.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = null;
		
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
		/**�� ����**/
		//���������� ����� ������.
		//�ϴ� ������ ���°� �Ǹ�, �ٸ� �����ڿ� �����ڴ� �Ű澲�� �ʴ´�.(������ ��ȥ���¿��� �����ڰ� ���� ���� ������ ��Ȱ�ϱ� ����)
		//1. ��ü�� ����ĭ�� �ϳ��� �����ϴ°�� ����ġ���� �������� �ʴ´�.
		//2. ����ġ�� ��ü�� ����, �����¿�4ĭ�� ��ü�� �����ϸ� ��ü�� ���� ���� ������ �̵��Ѵ�.(��ü�� ���� ���� ĭ�� 2�� �̻��� ��� Ư���� ����(�����¿� ����)���� �̵��Ѵ�.)
		//3. �� ��ġ�� �����¿�4ĭ�� ��ü�� �ϳ��� �������� ������ �� ��ġ���� �������� �ʴ´�.(��ȸ�⵵, Ȥ�� �ڻ��ϴ� ��Ȱ�ڸ� ��ٸ�)
		//CellInfo cell = this.cells[myInfo.position.row][myInfo.position.column];
		if(this.cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse)>0){//�� ��ġ�� ��ü�� �ϳ��� ������ stay
			result= DirectionCode.Stay;
		}
		else{//����ġ�� ��ü�� ���°��
			//�ϴ� �����¿쿡 �����ϴ� ��ü ������ ������ ��������
			int max_number_of_C=1;//��ü�� �����¿� ��򰡿� �����ϹǷ� �ʱ�ȭ���� 1
			int index = -1; //�����¿��� ��� ���� ���Ҷ� ���� ���� (0123�� �ϳ�)
			int[] number_of_C = new int[4];//�����¿� ������� 0123�� ����
			if (myInfo.position.row != 0)
				number_of_C[0] = this.cells[myInfo.position.row - 1][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
			if (myInfo.position.row != Constants.Classroom_Height - 1)
				number_of_C[1] = this.cells[myInfo.position.row + 1][myInfo.position.column]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
			if (myInfo.position.column != 0)
				number_of_C[2] = this.cells[myInfo.position.row][myInfo.position.column - 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
			if (myInfo.position.column != Constants.Classroom_Width - 1)
				number_of_C[3] = this.cells[myInfo.position.row][myInfo.position.column + 1]
						.CountIf_Players(player -> player.state == StateCode.Corpse);
			
			//�����¿� ��ü�� �ִ°�� ��ü�� ���� ������
			if(number_of_C[0]+number_of_C[1]+number_of_C[2]+number_of_C[3]!=0){
				for (int i = 0; i < number_of_C.length; i++) {
					if(number_of_C[i]>=max_number_of_C){
						max_number_of_C=number_of_C[i];
						index=i;
					}	
				}
				switch(index){
				case 0:
					result = DirectionCode.Up;
					break;
				case 1:
					result = DirectionCode.Down;
					break;					
				case 2:
					result = DirectionCode.Left;
					break;
				case 3:
					result = DirectionCode.Right;
					break;
				}
				
			}
			//�ֺ��� ��ü�� ���°��
			else{
				result=DirectionCode.Stay;
			}
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
			Init_Data();
		}

	}

	@Override
	public Point Soul_Spawn()
	{
		int max_row = -1;
		int max_column = -1;
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		//1. ���� ���� �����ڰ� ���ִ� ĭ���� ��Ȱ
		
		if(turnInfo.turnNumber == 0){//ù��° �Ͽ��� ������ (2,2) ���� ��Ȱ
			max_row = 2;
			max_column = 2;
		}
		
		else//ù��° ���� �ƴѰ�� ����ü�� ���� ������ ��Ȱ
		{
			int max_weight = 0;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					CellInfo cell = this.cells[row][column];

					// int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					
					int weight = numberOfInfecteds;
					int distance = favoritePoint.GetDistance(row, column);

					// ���� ���� ĭ�� �߰ߵǸ� ����
					if ( weight > max_weight )
					{
						max_weight = weight;
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
					else if ( weight == max_weight )
					{
						// �Ÿ��� �� ������ ����
						if ( distance < min_distance )
						{
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if ( distance == min_distance )
						{
							for ( int iDirection = 0; iDirection < 4; iDirection++ )
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
								
								if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row, max_column) )
								{
									max_row = row;
									max_column = column;
									break;
								}
							} 
							
							//������� �Դٸ� ���� �׸� ���� ����
						}
					}
				}
			}
			
		}



		return new Point(max_row, max_column);
	}
}
