package l4g2ep1;

import l4g2ep1.common.*;

/**
 * ���ǽ� ���� ��� ĭ�鿡 ���� ������ ��� �ִ� Ŭ�����Դϴ�.
 * �������� �� Ŭ������ �� �� ������
 * ��� Player class�� ���ǵ� getter �޼������ ����Ͽ� �ڽ��� �þ� ������ �ִ� ĭ�� ���� ������ ���������� �޾� �� �� �ֽ��ϴ�. 
 * 
 * @author Racin
 *
 */
class Cells
{
	/*
	 * ���ǽ� ���� �� ĭ�� ���� ������ ��� 2���� �迭�Դϴ�.
	 * 
	 * --> 2���� ����� ��Ÿ���� 2���� �迭�� �� �� ��ǥ ������ �Ϲ������� arr[y][x]�� ����մϴ�. 
	 */
	CellInfo[][] data;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * �������� �þ� ���� ���� �ִ� �� ĭ�� ���� ������ Player class�� ���ǵ� getter �޼������ ����Ͽ� �޾� �� �� �ֽ��ϴ�.
	 */
	Cells()
	{
		data = new CellInfo[Constants.Classroom_Height][Constants.Classroom_Width];
		
		Reset();
	}

	/*
	 * ĭ ������ '��� ĭ�� �� ĭ'�� ������ �����ϵ��� �缳���մϴ�.
	 */
	void Reset()
	{
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
				data[iRow][iColumn] = CellInfo.Blank;
	}
}
