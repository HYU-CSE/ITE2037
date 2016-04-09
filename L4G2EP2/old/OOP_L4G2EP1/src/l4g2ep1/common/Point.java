package l4g2ep1.common;

/**
 * ���ǽ� ���� �� ĭ�� ���� ��ǥ�� ��Ÿ���� Ŭ�����Դϴ�.
 * 
 * ����: �� ��ǥ�� ���� �� �ܼ��� == �����ڸ� ����ϸ� ������ ���ϴ� ����� ������ �ʽ��ϴ�.
 * 		 ��� p1.equals(p2)�� ����
 * 		 � ��ǥ�� ���� ��� �ٸ� ��ǥ�� argument�� �־� equals() �޼��带 ȣ���� ����
 * 		 ��ȯ���� true���� �˻��Ͽ� �� ��ǥ�� ���� ������ ���θ� Ȯ���ؾ� �մϴ�.
 * 
 * @author Racin
 *
 */
public final class Point
{
	/**
	 * ��ǥ�� x ���(���ι���, ���ʿ��� ���������� ����)�Դϴ�.
	 */
	public int x;
	
	/**
	 * ��ǥ�� y ���(���ι���, ������ �Ʒ��� ����)�Դϴ�.
	 */
	public int y;

	/**
	 * �ƹ� ĭ�� ����Ű�� �ʴ� ���ο� Point class�� �ν��Ͻ��� �����մϴ�.
	 */
	public Point()
	{
		x = -1;
		y = -1;
	}

	/**
	 * �־��� ��ǥ�� ���� ���ο� Point class�� �ν��Ͻ��� �����մϴ�.
	 */
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * �ش� ��ǥ�� ������ ���ο� Point class�� �ν��Ͻ��� �����մϴ�.
	 */
	public Point(Point other)
	{
		x = other.x;
		y = other.y;
	}
	
	/**
	 * �־��� ���� ��ǥ���� �ش� �������� �̵����� �� �����ϰ� �� ���� ����Ű�� ���ο� Point class�� �ν��Ͻ��� �����մϴ�.
	 */
	public Point(Point origin, DirectionCode direction)
	{
		x = origin.x;
		y = origin.y;
		
		if ( direction != null )
		{
			switch ( direction )
			{
			case Up:
				--y;
				break;
			case Left:
				--x;
				break;
			case Right:
				++x;
				break;
			case Down:
				++y;
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * �� ��ǥ�� ���ǽ� ���� �� ĭ�� ����Ű�� ��ȿ�� ��ǥ���� ���θ� ��ȯ�մϴ�.
	 */
	public boolean IsValid()
	{
		// ��ǥ�� ���ǽ� ������ ������� ���� Ȯ��
		return x >= 0 && x < Constants.Classroom_Width &&
				y >= 0 && y < Constants.Classroom_Height;
	}
	
	/**
	 * �־��� ��ǥ�� ���ǽ� ���� �� ĭ�� ����Ű�� ��ȿ�� ��ǥ���� ���θ� ��ȯ�մϴ�.
	 */
	public static boolean IsValid(int x, int y)
	{
		return x >= 0 && x < Constants.Classroom_Width &&
				y >= 0 && y < Constants.Classroom_Height;
	}

	/**
	 * �־��� ��ǥ�� ���ǽ� ���� �� ĭ�� ����Ű�� ��ȿ�� ��ǥ���� ���θ� ��ȯ�մϴ�.
	 */
	public static boolean IsValid(Point p)
	{
		return IsValid(p.x, p.y);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		//���� ���Ϸ��� �ν��Ͻ��� ���� Point �����̶�� ��ǥ�� �� ����
		if ( obj.getClass() == this.getClass() )
			return equals((Point)obj);
		
		//�׷��� ���� ��� ���� ���� ������ false ��ȯ
		return false;
	}

	public boolean equals(Point other)
	{
		return x == other.x && y == other.y;
	}

	public boolean equals(int x, int y)
	{
		return this.x == x && this.y == y;
	}

	@Override
	public String toString()
	{
		return String.format("(%d, %d)", x, y);
	}
}
