package l4g.common;

/**
 * ��ǥ <b>���</b> �ϳ��� ��Ÿ���� Ŭ�����Դϴ�.
 * �� Ŭ������ �ν��Ͻ��� �������� ���� ����� <code>Point</code>(����� �ƴ� ����) �ν��Ͻ��� ȣȯ�˴ϴ�.<br>
 * <br>
 * <b>Note:</b> �̷� Ŭ������ ���� ������ �׳� ���� ���� �����̸�
 * �������� ���� �� Ŭ������ �ν��Ͻ��� ����� �� �ʿ�� �����ϴ�.
 * 
 * @author Racin
 *
 */
public class Point_Immutable extends PointBase
{
	/**
	 * �� ��ǥ�� ���� ���� �����Դϴ�.
	 * 0�� ��� �� �� ���� ��Ÿ���ϴ�.
	 */
	public final int row;

	/**
	 * �� ��ǥ�� ���� ���� �����Դϴ�.
	 * 0�� ��� �� ���� ���� ��Ÿ���ϴ�.
	 */
	public final int column;

	/**
	 * ���ο� <code>Point_Immutable class</code>�� �ν��Ͻ��� �ʱ�ȭ�մϴ�.<br>
	 * <br>
	 * <b>Note:</b> �������� ���� �� Ŭ������ �ν��Ͻ��� ����� �� �ʿ�� �����ϴ�.
	 * � ��ǥ���� ����� �ΰ� ������ �̰� ��� <code>Point class</code>�� ������.
	 */
	public Point_Immutable(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	
	/* ----------------------------------------------------
	 * �Ʒ��� �ִ� �� �޼���鿡 ���� ������ ���� Ŭ����(PointBase class)�� ���� ���� ���� �����մϴ�.
	 * �޼��� �̸��� ���콺�� ���� ��� �ش� ������ �о� �� �� �ֽ��ϴ�.
	 */

	@Override
	public Point Copy()
	{
		return new Point(row, column);
	}

	@Override
	public Point Offset(int offset_row, int offset_column)
	{
		return new Point(row + offset_row, column + offset_column);
	}
	
	@Override
	public Point GetAdjacentPoint(DirectionCode direction)
	{
		switch ( direction )
		{
		case Up:
			return new Point(this, -1, 0);
		case Left:
			return new Point(this, 0, -1);
		case Right:
			return new Point(this, 0, 1);
		case Down:
			return new Point(this, 1, 0);
		default:
			return new Point(this);
		}
	}

	@Override
	public int GetDistance(int row, int column)
	{
		return ( this.row > row ? this.row - row : row - this.row ) + ( this.column > column ? this.column - column : column - this.column );
	}

	@Override
	public int GetDistance(Point other)
	{
		return ( other.row > row ? other.row - row : row - other.row ) + ( other.column > column ? other.column - column : column - other.column );
	}

	@Override
	public int GetDistance(Point_Immutable other)
	{
		return ( other.row > row ? other.row - row : row - other.row ) + ( other.column > column ? other.column - column : column - other.column );
	}
	
	@Override
	public boolean equals(Point other)
	{
		return this.row == other.row && this.column == other.column;
	}

	@Override
	public boolean equals(Point_Immutable other)
	{
		return this == other ? true : this.row == other.row && this.column == other.column;
	}
	
	@Override
	public boolean equals(int row, int column)
	{
		return this.row == row && this.column == column;
	}
	
	@Override
	public String toString()
	{
		if ( this == Constants.Pos_Sky )
			return "(�ϴ�)";
		
		return String.format("(%d, %d)", row, column);
	}
}
