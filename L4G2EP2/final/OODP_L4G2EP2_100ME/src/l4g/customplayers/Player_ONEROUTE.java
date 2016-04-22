package l4g.customplayers;

import java.awt.HeadlessException;
import java.awt.LinearGradientPaint;
import java.text.ParsePosition;

import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_ONEROUTE extends Player
{
	int d=1;
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_ONEROUTE(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "oneroute");
		
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
		DirectionCode result=null;
		
		switch((int)(gameNumber % 4)){
		case 0:
			if(myInfo.position.row== Constants.Classroom_Height-1)
				d=1;
			else if(myInfo.position.row==0)
				d=0;
			if(d==0)
			result = DirectionCode.Down;
			if(d==1)
			result = DirectionCode.Up;
			break;
		case 1:
			if(myInfo.position.row== Constants.Classroom_Height-1)
				d=0;
			else if(myInfo.position.row==0)
				d=1;
			if(d==1)
			result = DirectionCode.Down;
			if(d==0)
			result = DirectionCode.Up;
			break;
		case 2:
			if(myInfo.position.column== Constants.Classroom_Width-1)
				d=0;
			else if(myInfo.position.column==0)
				d=1;
			if(d==0)
			result = DirectionCode.Left;
			if(d==1)
			result = DirectionCode.Right;
			break;
		case 3:
			if(myInfo.position.column== Constants.Classroom_Width-1)
				d=1;
			else if(myInfo.position.column==0)
				d=0;
			if(d==1)
			result = DirectionCode.Left;
			if(d==0)
			result = DirectionCode.Right;
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
		DirectionCode result=null;
		
		switch((int)(gameNumber % 4)){
		case 0:
			if(myInfo.position.row== Constants.Classroom_Height-1)
				d=1;
			else if(myInfo.position.row==0)
				d=0;
			if(d==0)
			result = DirectionCode.Down;
			if(d==1)
			result = DirectionCode.Up;
			break;
		case 1:
			if(myInfo.position.row== Constants.Classroom_Height-1)
				d=0;
			else if(myInfo.position.row==0)
				d=1;
			if(d==1)
			result = DirectionCode.Down;
			if(d==0)
			result = DirectionCode.Up;
			break;
		case 2:
			if(myInfo.position.column== Constants.Classroom_Width-1)
				d=0;
			else if(myInfo.position.column==0)
				d=1;
			if(d==0)
			result = DirectionCode.Left;
			if(d==1)
			result = DirectionCode.Right;
			break;
		case 3:
			if(myInfo.position.column== Constants.Classroom_Width-1)
				d=1;
			else if(myInfo.position.column==0)
				d=0;
			if(d==1)
			result = DirectionCode.Left;
			if(d==0)
			result = DirectionCode.Right;
			break;
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
		int result_row = -1;
		int result_column = -1;

		switch((int)(gameNumber % 4)){
		case 0:
			result_row = 5;
			result_column = 5;
			break;
		case 1:
			result_row = 5;
			result_column = 6;
			break;
		case 2:
			result_row = 6;
			result_column = 5;
			break;
		case 3:
			result_row = 6;
			result_column = 6;
			break;
		}
		
		return new Point(result_row, result_column);
	}
}
