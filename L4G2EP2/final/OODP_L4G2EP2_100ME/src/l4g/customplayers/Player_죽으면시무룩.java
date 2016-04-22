package l4g.customplayers;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_������ù��� extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_������ù���(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "������ù���");
		
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
	
	static final int GoRight=0;
	static final int GoDown=1;
	static final int GoLeft=2;
	static final int GoUp=3;
	
	int state = GoRight;
	
	void UpdateState(){
		int[][] Dx={{0, -1, 0, 1}, {2, 1, 1, 1}, {0, -1, 0, 1}, {-2, -1, -1, -1}};
		int[][] Dy={{2, 1, 1, 1}, {0, -1, 0, 1}, {-2, -1, -1, -1}, {0, -1, 0, 1}};

		
		int x=this.myInfo.position.row;
		int y=this.myInfo.position.column;
		int Cnt, Max=-999;
		for (int i=0; i<4; i++){
			Cnt=0;
			for (int j=0; j<4; j++){
				if (x+Dx[i][j]>0 && x+Dx[i][j]<Constants.Classroom_Height && y+Dy[i][j]>0 && y+Dy[i][j]<Constants.Classroom_Width){
					Cnt+=cells[x+Dx[i][j]][y+Dy[i][j]].CountIf_Players(player -> player.state == StateCode.Survivor );
					Cnt-=cells[x+Dx[i][j]][y+Dy[i][j]].CountIf_Players(player -> player.state == StateCode.Infected );
				}
			}
			if (Max<=Cnt){
				Max=Cnt;
				state=i;
			}
		}
		if (state==GoRight && y==Constants.Classroom_Width-1){
			state=GoLeft;
		}
		else if (state==GoLeft && y==0){
			state=GoRight;
		}
		else if (state==GoUp && x==0){
			state=GoDown;
		}
		else if (state==GoDown && x==Constants.Classroom_Height-1){
			state=GoUp;
		}
	}
	
	
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		
		UpdateState();
		
		switch(state){
		case GoRight:
			result=DirectionCode.Right;
			break;
		case GoDown:
			result=DirectionCode.Down;
			break;
		case GoLeft:
			result=DirectionCode.Left;
			break;
		case GoUp:
			result=DirectionCode.Up;
			break;
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
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{

	}

	@Override
	public Point Soul_Spawn()
	{
		state=GoRight;
		return new Point(Constants.Classroom_Height/2, Constants.Classroom_Width/2);
	}
}
