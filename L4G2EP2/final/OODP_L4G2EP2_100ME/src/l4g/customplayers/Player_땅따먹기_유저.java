package l4g.customplayers;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_�����Ա�_���� extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_�����Ա�_����(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "�����Ա�_����");
		
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
	
	LinkedList< HashMap< Point, DirectionCode > > point_list;
	Point_Immutable last_point;
	DirectionCode curr_direction, last_direction;
	ListIterator< HashMap< Point, DirectionCode > > curr_iterator;
	int prev_count;

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
			
			HashMap< Point, DirectionCode > new_map;
			
			point_list = new LinkedList< HashMap< Point, DirectionCode > >();
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 12,2 ), DirectionCode.Up );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 8,2 ), DirectionCode.Left );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 8,0 ), DirectionCode.Right );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 8,5 ), DirectionCode.Up );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 5,5 ), DirectionCode.Left );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 5,0 ), DirectionCode.Right );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 5,3 ), DirectionCode.Up );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 2,3 ), DirectionCode.Left );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 2,0 ), DirectionCode.Right );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 2,6 ), DirectionCode.Up );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 0,6 ), DirectionCode.Down );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 6,6 ), DirectionCode.Right );
			point_list.add( new_map );
			
			new_map = new HashMap< Point, DirectionCode >();
			new_map.put( new Point( 6,12 ), null );
			point_list.add( new_map );
			
			
			curr_direction = DirectionCode.Right;
			last_direction = DirectionCode.Right;
			last_point = new Point_Immutable( 12, 0 );
			
			curr_iterator = point_list.listIterator();
		}
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		HashMap< Point, DirectionCode > curr_map = curr_iterator.next();
		for( Point point : curr_map.keySet() )
		{
			if( point.equals( myInfo.position ) )
			{
				if( point.row == 0 || point.row == Constants.Classroom_Height-1 ||
						point.column == 0 || point.column == Constants.Classroom_Width-1)
				{
					last_point = myInfo.position;
					last_direction = curr_map.get( point );
					prev_count = 0;
				}
				else
					prev_count++;
				
				curr_direction = curr_map.get( point );
				break;
			}
			
			curr_iterator.previous();
		}
		
		return curr_direction;
	}

	@Override
	public DirectionCode Infected_Move()
	{
		return DirectionCode.Stay;
	}

	@Override
	public Point Soul_Spawn()
	{
		if( turnInfo.turnNumber == 1 )
			return new Point(12, 0);
		
		else if( last_point.equals( new Point( 6,12 ) ) )
		{
			int a = 0;
			a = a / a;
		}
		
		for( int i=0; i<prev_count; i++ )
			curr_iterator.previous();
		
		prev_count = 0;
		
		curr_direction = last_direction;
		return last_point.Copy();
	}
}
