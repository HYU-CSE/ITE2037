package l4g.customplayers;

import java.util.HashMap;

import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;
import l4g.common.Point_Immutable;

public class Player_������ extends Player {
	
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_������(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "������");
		
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
	
	HashMap< Point, DirectionCode > turning_point;
	DirectionCode curr_direction;
	Point_Immutable last_point;
	boolean isSnailMode;


	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
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
			
			curr_direction = DirectionCode.Right;
			
			last_point = new Point_Immutable( 3, 1 );
			
			/* ������ �׸�, ���� ��ȯ�� ���� �����մϴ�. */
			turning_point = new HashMap< Point, DirectionCode >();
			turning_point.put( new Point(3, 2), DirectionCode.Down );
			turning_point.put( new Point(9, 2), DirectionCode.Right );
			turning_point.put( new Point(9, 10), DirectionCode.Up );
			turning_point.put( new Point(3, 10), DirectionCode.Left );
			turning_point.put( new Point(3, 4), DirectionCode.Down );
			turning_point.put( new Point(8, 4), DirectionCode.Right );
			turning_point.put( new Point(8, 9), DirectionCode.Up );
			turning_point.put( new Point(4, 9), DirectionCode.Left );
			turning_point.put( new Point(4, 5), DirectionCode.Down );
			turning_point.put( new Point(7, 5), DirectionCode.Right );
			turning_point.put( new Point(7, 8), DirectionCode.Up );
			turning_point.put( new Point(5, 8), DirectionCode.Left );
			turning_point.put( new Point(5, 6), DirectionCode.Down );
			turning_point.put( new Point(6, 6), DirectionCode.Right );
			turning_point.put( new Point(6, 7), null );
			
			isSnailMode = true;
		}
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		last_point = myInfo.position;
		
		if( isSnailMode )
		{
			for( Point point : turning_point.keySet() )
			{
				if( point.equals( myInfo.position ) )
				{
					curr_direction = turning_point.get( point );
					break;
				}
			}
		}
		
		return curr_direction;
	}

	
	@Override
	public DirectionCode Infected_Move()
	{
		last_point = myInfo.position;
		
		return DirectionCode.Stay;
	}

	@Override
	public Point Soul_Spawn()
	{
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
		if( turnInfo.turnNumber == 1 )
			if( isSnailMode )
				return new Point(3, 1);
		
		else if( isSnailMode && last_point.equals( new Point( 6,7 ) ) )
		{
			last_point = new Point_Immutable( 3, 1 );
			curr_direction = DirectionCode.Right;
		}
		
		return last_point.Copy();
	}
}
