package l4g.common;

/**
 * Ư�� ��ǥ�� ��Ÿ���� ���� <code>Point class, Point_Immutable class</code>�� �θ��� �߻� Ŭ�����Դϴ�.
 * ����, �� Ŭ������ �Ű澲�� �ʾƵ� �˴ϴ�.<br>
 * <br>
 * <b>Note:</b> �� Ŭ������ 'public'�� �پ� ���� �ʱ� ������ ������ �ڵ忡���� ������ �������� �ʽ��ϴ�.
 * 
 * @author Racin
 *
 */
abstract class PointBase
{
	/**
	 * �� ��ǥ�� ������ x, y���� ���� ���ο� Point �ν��Ͻ��� ����� ��ȯ�մϴ�.
	 */
	public abstract Point Copy();
	
	/**
	 * �� ��ǥ���� ������ �Ÿ���ŭ ������ ��ġ�� ����Ű�� ���ο� Point �ν��Ͻ��� ����� ��ȯ�մϴ�.
	 */
	public abstract Point Offset(int offset_row, int offset_column);
	
	/**
	 * �� ��ǥ���� ������ ���⿡ ��ġ�� ���� ����� Point �ν��Ͻ��� ����� ��ȯ�մϴ�.
	 */
	public abstract Point GetAdjacentPoint(DirectionCode direction);
	
	/**
	 * �� ��ǥ�� �ش� ��ǥ ������ ĭ ���� ��ȯ�մϴ�.
	 */
	public abstract int GetDistance(int row, int column);
	
	/**
	 * �� ��ǥ�� �ش� ��ǥ ������ ĭ ���� ��ȯ�մϴ�.
	 */
	public abstract int GetDistance(Point other);
	
	/**
	 * �� ��ǥ�� �ش� ��ǥ ������ ĭ ���� ��ȯ�մϴ�.
	 */
	public abstract int GetDistance(Point_Immutable other);
	
	/**
	 * �� ��ǥ�� �ش� ��ǥ�� ������ ĭ�� ����Ű�� �ִ��� ���θ� ��ȯ�մϴ�.
	 */
	public abstract boolean equals(Point other);
	
	/**
	 * �� ��ǥ�� �ش� ��ǥ�� ������ ĭ�� ����Ű�� �ִ��� ���θ� ��ȯ�մϴ�.
	 */
	public abstract boolean equals(Point_Immutable other);
	
	/**
	 * �� ��ǥ�� �ش� ��ǥ�� ������ ĭ�� ����Ű�� �ִ��� ���θ� ��ȯ�մϴ�.
	 */
	public abstract boolean equals(int row, int column);
}
