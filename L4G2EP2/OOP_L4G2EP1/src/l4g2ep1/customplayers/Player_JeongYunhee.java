package l4g2ep1.customplayers;

//import java.util.ArrayList;

//import javax.swing.text.Position;

import l4g2ep1.*;
import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class Player_JeongYunhee extends Player
{
	public Player_JeongYunhee()
	{
		name = "Whoooooo";	// TODO �ڽ��� ���� �÷��̾��� �̸����� name �ʵ带 �ʱ�ȭ�ϼ���.
		acceptDirectInfection = false;				// TODO '���� ����'�� �������� ��� �� �ʵ带 true�� �ΰ� �ƴ� ��� false�� �μ���.
		receiveOthersInfo_detected = true;
		receiveActions=true;
		receiveReactions=true;
	}
	
	public int max(int[] danger){
		int maximum=0;
		int[] point= new int[4];
		int direction=0;
		for(int j=0;j<4;j++){
			
			if(j==0){
				if(this.IsValidMove(DirectionCode.Up)==false) {
					point[0]=-1;
				}
			}
			else if(j==1){
				if(this.IsValidMove(DirectionCode.Left)==false) point[1]=-1;
			}
			else if(j==2){
				if(this.IsValidMove(DirectionCode.Right)==false) point[2]=-1;
			}
			else{
				if(this.IsValidMove(DirectionCode.Down)==false) point[3]=-1;
			}
						
				if(danger[j]>maximum){
					maximum=danger[j];
					if(j==0) direction= 3;
					else if(j==1) direction= 2;
					else if(j==2) direction= 1;
					else if(j==3) direction= 0;
				}
			
		}
		
		if(point[direction]==-1){
			if(direction==0) direction= 3;
			else if(direction==1) direction= 2;
			else if(direction==2) direction= 1;
			else if(direction==3) direction= 0;
		}
		
		return direction;
}
	

	private int max_i(int[] survivor) {
		int maximum=0;
		int[] point= new int[4];
		int direction=0;
		for(int j=0;j<4;j++){
			
			if(j==0){
				if(this.IsValidMove(DirectionCode.Up)==false) {
					point[0]=-1;
				}
			}
			else if(j==1){
				if(this.IsValidMove(DirectionCode.Left)==false) point[1]=-1;
			}
			else if(j==2){
				if(this.IsValidMove(DirectionCode.Right)==false) point[2]=-1;
			}
			else{
				if(this.IsValidMove(DirectionCode.Down)==false) point[3]=-1;
			}
						
				if(survivor[j]>maximum){
					maximum=survivor[j];
					if(j==0) direction= 0;
					else if(j==1) direction= 1;
					else if(j==2) direction= 2;
					else if(j==3) direction= 3;
				}
			
		}
		
		if(point[direction]==-1){
			if(direction==0) direction= 3;
			else if(direction==1) direction= 2;
			else if(direction==2) direction= 1;
			else if(direction==3) direction= 0;
		}
		
		return direction;
	}
	
	private int Super_surviovor(PlayerInfo other) {
		Vector v = GetDistanceVectorBetweenPlayers(other);
		if(v.x_offset==0&&v.y_offset==-2) return 0;
		else if(v.x_offset==-2&&v.y_offset==0) return 1;
		else if(v.x_offset==2&&v.y_offset==0) return 2;
		else if(v.x_offset==0&&v.y_offset==2) return 3;
		else if(v.x_offset==1&&v.y_offset==-1) return 20;
		else if(v.x_offset==-1&&v.y_offset==-1) return 10;
		else if(v.x_offset==-1&&v.y_offset==1) return 13;
		else if(v.x_offset==1&&v.y_offset==1) return 23;
		return 5;
	}
	@Override
	public DirectionCode Survivor_Move()
	{
		//int i=0;//�ݺ����� ����
		int[] danger = new int[4];//��������Ʈ ����� �迭, danger[0]���� ��, ����, ������, �Ʒ��� ����
		
		for ( PlayerInfo other : othersInfo_detected )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);
			
			//�ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
			if(other.GetState()==State.Infected){
				if ( v.y_offset < 0 )
					danger[0]+=1;

				//�ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
				if ( v.x_offset < 0 )
					danger[1]+=1;
				
				//�ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
				if ( v.x_offset > 0 )
					danger[2]+=1;
				
				//�ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
				if ( v.y_offset > 0 )
					danger[3]+=1;
			}
			if(other.GetState()==State.Survivor){
				if ( v.y_offset < 0 )
					danger[0]-=1;

				if ( v.x_offset < 0 )
					danger[1]-=1;
				
				if ( v.x_offset > 0 )
					danger[2]-=1;
				
				if ( v.y_offset > 0 )
					danger[3]-=1;
			}
		}
		
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//�ش� �÷��̾�� �� ������ �Ÿ� ��
			Vector v = GetDistanceVectorBetweenPlayers(other);
			
		
			if(other.GetState()==State.Infected){
				if ( v.y_offset < 0 )
					danger[0]+=10;

				if ( v.x_offset < 0 )
					danger[1]+=10;
				
				if ( v.x_offset > 0 )
					danger[2]+=10;
				
				if ( v.y_offset > 0 )
					danger[3]+=10;
			}
			if(other.GetState()==State.Survivor){
				if ( v.y_offset < 0 )
					danger[0]-=1;

				if ( v.x_offset < 0 )
					danger[1]-=1;
				
				if ( v.x_offset > 0 )
					danger[2]-=1;
				
				if ( v.y_offset > 0 )
					danger[3]-=1;
			}
		}
		
		
		DirectionCode direction=null;
		if(max(danger)==0) direction=DirectionCode.Up;
		else if(max(danger)==1) direction=DirectionCode.Left;
		else if(max(danger)==2) direction=DirectionCode.Right;
		else if(max(danger)==3) direction=DirectionCode.Down;


		// TODO ������ ������ �� �̵��ϱ� ���� ������ ���⿡ ��������.
		return direction;
	}

	@Override
	public void Corpse_Stay()
	{
		
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		int corpse=0,num=0;
		PlayerInfo pos = null;
		DirectionCode direction=DirectionCode.Stay;
		for(PlayerInfo other: othersInfo_withinSight){
			Vector v= GetDistanceVectorBetweenPlayers(other);
			if((v.x_offset<=2||v.x_offset>=-2||v.y_offset<=2||v.y_offset>=-2) && other.GetState()==State.Corpse){
				corpse=-1;
				if(pos==null) pos=other;
				else if(GetDistance(myInfo.GetPosition(), other.GetPosition())<GetDistance(myInfo.GetPosition(), pos.GetPosition()))
					pos=other;
			}
		
		}
		
		if(corpse==-1){
			Vector v=GetDistanceVectorBetweenPlayers(pos);
			if ( v.y_offset < 0 )
				direction=DirectionCode.Up;

			if ( v.x_offset < 0 )
				direction=DirectionCode.Left;
			
			if ( v.x_offset > 0 )
				direction=DirectionCode.Right;
			
			if ( v.y_offset > 0 )
				direction=DirectionCode.Down;

			
		}
	
		else{
			//int i=0;//�ݺ����� ����
			int[] survivor = new int[4];//��������Ʈ ����� �迭, danger[0]���� ��, ����, ������, �Ʒ��� ����
			
			for ( PlayerInfo other : othersInfo_withinSight )
				{
					//�ش� �÷��̾�� �� ������ �Ÿ� ��
					Vector v = GetDistanceVectorBetweenPlayers(other);
					num++;
					if(other.GetState()==State.Survivor){
						if ( v.y_offset < 0 )
							if(Super_surviovor(other)==0) survivor[0]+=5;
							else if(v.y_offset==-1&&v.x_offset==0) ;
							else survivor[0]+=1;

						if ( v.x_offset < 0 )
							if(Super_surviovor(other)==1) survivor[1]+=5;
							else if(v.x_offset==-1&&v.y_offset==0) ;
							else survivor[1]+=1;
						
						if ( v.x_offset > 0 )
							if(Super_surviovor(other)==2) survivor[2]+=5;
							else if(v.x_offset==1&&v.y_offset==0) ;
							else survivor[2]+=1;
						
						if ( v.y_offset > 0 )
							if(Super_surviovor(other)==3) survivor[3]+=5;
							else if(v.y_offset==1&&v.x_offset==0) ;
							else survivor[3]+=1;
					}
				}
								
				if(num<3&&myInfo.GetHitPoint()<11) direction=DirectionCode.Stay;
				else if(max_i(survivor)==0) direction=DirectionCode.Up;
				else if(max_i(survivor)==1) direction=DirectionCode.Left;
				else if(max_i(survivor)==2) direction=DirectionCode.Right;
				else if(max_i(survivor)==3) direction=DirectionCode.Down;
			
				// TODO ������ ������ �� �̵��ϱ� ���� ������ ���⿡ ��������.
				
		}
		
		return direction;
	}


	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			
			// TODO ���� ���� ������ �ʵ忡 ���� �ʱ�ȭ �ڵ带 ���⿡ ��������. �� �޼���� ������ ���۵Ǹ� ���� ���� ȣ��˴ϴ�.
		}

		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn()
	{
		Point origin =new Point();
		
			origin.x=4;
			origin.y=4;
				
		// TODO ��ȥ ������ �� ���ġ�ϱ� ���� ������ ���⿡ ��������.
		return origin;
	}

}
