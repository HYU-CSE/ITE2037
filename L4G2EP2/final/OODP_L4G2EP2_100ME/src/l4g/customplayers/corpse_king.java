package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class corpse_king extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public corpse_king(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "��ü��");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}

	
	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�.
	 * �� field�� �ݵ�� �ʿ��մϴ�.
	 */

	
	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�.
	 * �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
	
	
	
	/**
	 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
	
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
	
		if(turnInfo.turnNumber<10 && turnInfo.turnNumber%2==0)
			result=DirectionCode.Right;
		else if(turnInfo.turnNumber<10 && turnInfo.turnNumber%2!=0)
			result=DirectionCode.Left;
		else if(this.myInfo.position.column<=Constants.Classroom_Width-3 &&cells[this.myInfo.position.row][this.myInfo.position.column+2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0&&cells[this.myInfo.position.row][this.myInfo.position.column+1].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0)
		{
			
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Right;
			else if(this.myInfo.position.row==0 &&cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Down;
			}
			
		}		
		else if(this.myInfo.position.column>=2 && cells[this.myInfo.position.row][this.myInfo.position.column-2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row][this.myInfo.position.column-1].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0)
		{
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
							player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Up;
			}
			
		
		}
		else if(this.myInfo.position.row<=Constants.Classroom_Height-3 &&cells[this.myInfo.position.row+2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0&&cells[this.myInfo.position.row+1][this.myInfo.position.column].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0 )
		{	
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Down;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Right;
			}
		}
		
		else if(this.myInfo.position.row>=2 &&  cells[this.myInfo.position.row-2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0&&  cells[this.myInfo.position.row-1][this.myInfo.position.column].CountIf_Players(
						player -> player.state == StateCode.Infected )<=0)
		{
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column==0 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Up;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Left;
			}
			
		}	
		else if(this.myInfo.position.column<=Constants.Classroom_Width-3 &&cells[this.myInfo.position.row][this.myInfo.position.column+2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0)
		{
			
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Right;
			else if(this.myInfo.position.row==0 &&cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
			result=DirectionCode.Right;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Down;
			}
			
		}		
		else if(this.myInfo.position.column>=2 && cells[this.myInfo.position.row][this.myInfo.position.column-2].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0 )
		{
			
			if(this.myInfo.position.row==Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(this.myInfo.position.row>0 && this.myInfo.position.row<Constants.Classroom_Height-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
							player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Left;
			else if(result==null)
			{
				if(this.myInfo.position.row==Constants.Classroom_Height-1)
					result=DirectionCode.Up;
				else if(this.myInfo.position.row==0 )
					result=DirectionCode.Down;
				else
					result=DirectionCode.Up;
			}
			
		
		}
		else if(this.myInfo.position.row<=Constants.Classroom_Height-3 &&cells[this.myInfo.position.row+2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0 )
		{	
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column==0 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Down;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row+1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row+1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Down;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Right;
			}
		}
		
		else if(this.myInfo.position.row>=2 &&  cells[this.myInfo.position.row-2][this.myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Infected )<=0)
		{
			
			if(this.myInfo.position.column==Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column==0 &&cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0)
				result=DirectionCode.Up;
			else if(this.myInfo.position.column>0 && this.myInfo.position.column<Constants.Classroom_Width-1 && cells[this.myInfo.position.row-1][this.myInfo.position.column+1].CountIf_Players(
					player -> player.state == StateCode.Infected )<=0 && cells[this.myInfo.position.row-1][this.myInfo.position.column-1].CountIf_Players(
					player -> player.state == StateCode.Infected ) <=0 )
				result=DirectionCode.Up;
			else if(result==null)
			{
				if(this.myInfo.position.column==Constants.Classroom_Width-1)
					result=DirectionCode.Left;
				else if(this.myInfo.position.column==0)
					result=DirectionCode.Right;
				else
					result=DirectionCode.Left;
			}
			
		}	
		else if(result==null)
		{
			
			if(this.myInfo.position.column<Constants.Classroom_Width-1)
				result=DirectionCode.Right;
			else if(this.myInfo.position.row<Constants.Classroom_Height-1)
	        	 result=DirectionCode.Down;
			else if(this.myInfo.position.column==Constants.Classroom_Width-1)
	        	result=DirectionCode.Left;
			else if(this.myInfo.position.row==Constants.Classroom_Height-1)
	        	result=DirectionCode.Up;
		} 
		
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
		
			return DirectionCode.Stay;

		
		

		// ��� ĭ�� �����Ͽ� ���ǽǿ� �ִ� ��� ��ü�鿡 ���� ����� ���� 
	
		//if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			//return GetMovableAdjacentDirection();
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		
		//return DirectionCode.Stay;
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
		int result_row = 6;
		int result_column = 6;
		int tmp,icheck,ccheck,weight,imax=0;//������ġ�� ��ü���� �����Һ���, ������ġ �ֺ����ִ� ����ü���� ������ ����,��ü���ִ� ��ġ�ֺ��� �����ڼ��� �ִ�
		for(int row=1 ;row<Constants.Classroom_Height-1;row++)
		{
			for(int column=1;column<Constants.Classroom_Width-1;column++)
			{
				icheck=0;
				ccheck=0;
				tmp=cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse );
				if(tmp>=0)
				{
					for(int i=-1;i<=1;i++)
					{
						for(int j=-1;j<=1;j++)
						if(cells[row+i][column+j].CountIf_Players(player -> player.state == StateCode.Infected )>0 )
						{
								++ccheck;
						}
					}
					icheck=cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected );
				}	
					weight=icheck+tmp+ccheck;
					if(imax<weight)
					{
						imax=weight;
						result_row=row;
						result_column=column;
					}
				
			}
			
		}
		
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
		return new Point(result_row, result_column);
		
	
	}
}
