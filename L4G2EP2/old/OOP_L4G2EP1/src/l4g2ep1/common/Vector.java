package l4g2ep1.common;

/**
 * �� ��ǥ�� �մ� ���� �ϳ��� ��Ÿ���� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Vector
{
	/**
	 * ������ X�� ����(���� ����) �����Դϴ�.
	 */
	public int x_offset;
	
	/**
	 * ������ Y�� ����(���� ����) �����Դϴ�.
	 */
	public int y_offset;
	
	public Vector(int x_origin, int y_origin, int x_target, int y_target)
	{
		x_offset = x_target - x_origin;
		y_offset = y_target - y_origin;
	}

	public Vector(Point origin, Point target)
	{
		this(origin.x, origin.y, target.x, target.y);
	}
	
	public Vector()
	{
		this(0, 0, 0, 0);
	}

	/**
	 * �� ��ǥ ������ X�� ����(���� ����) �Ÿ��� ��ȯ�մϴ�.
	 * ��ȯ���� x_offset �ʵ��� ���밪�� �����ϴ�.
	 */
	public int GetDistance_X()
	{
		int x_distance = x_offset;
		
		if ( x_distance < 0 )
			x_distance = -x_distance;
		
		return x_distance;
	}

	/**
	 * �� ��ǥ ������ Y�� ����(���� ����) �Ÿ��� ��ȯ�մϴ�.
	 * ��ȯ���� y_offset �ʵ��� ���밪�� �����ϴ�.
	 */
	public int GetDistance_Y()
	{
		int y_distance = y_offset;
		
		if ( y_distance < 0 )
			y_distance = -y_distance;
		
		return y_distance;
	}

	/**
	 * �� ��ǥ ������ �Ÿ�(���� ��ǥ���� ������ ��ǥ���� �̵��ϱ� ���� �ɸ��� �� �� ��)�� ��ȯ�մϴ�.
	 * ��ȯ���� X�� ���� �Ÿ��� Y�� ���� �Ÿ��� �հ� �����ϴ�.
	 */
	public int GetDistance()
	{
		return GetDistance_X() + GetDistance_Y();
	}
	
	/**
	 * ������ ������ ����(���� ��ǥ�� ������ ��ǥ�� �մ� ������ ������ ����)�� ��ȯ�մϴ�.
	 * ��ȯ���� X�� ���� ������ ������ Y�� ���� ������ ������ �հ� �����ϴ�.
	 * (�� ���� ���������� ���ӻ��� ���� �����ϴ�. ��� GetDistance()�� ����Ͽ� �� ��ǥ ������ �������� �Ÿ��� ��ȯ�޾� ����ϼ���)
	 */
	public int GetSquaredLength()
	{
		return x_offset * x_offset + y_offset * y_offset; 
	}
}
