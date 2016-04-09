package l4g2ep1;

import java.util.ArrayList;

/**
 * ���ǽ� ���� ĭ �ϳ��� ������ ������ ��� ���� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class CellInfo
{
	/**
	 * '��� �ִ� ĭ'�� ��Ÿ���� ���� �ν��Ͻ��Դϴ�.
	 */
	public static CellInfo Blank = new CellInfo();

	/*
	 * ���� �ִ� ��ϵ��� �������� ���� �� �� ������ ��� �Ʒ��� ���ǵ� getter �޼������ ����Ͽ� ��� ���� ��ҵ��� �о�� �� �ֽ��ϴ�.
	 * ( �ٽ� ���ϸ�, �������� �� ��� ��ü �� ���빰�� ������ ���� �����ϴ� )
	 */
	ArrayList<PlayerInfo> playersInTheCell;
	ArrayList<Action> actionsInTheCell;
	ArrayList<Reaction> reactionsInTheCell;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * �������� �þ� ���� ������ ����� �ൿ���� ��� �ڵ����� ��Ͽ� ��ϵǾ� ���޵˴ϴ�.
	 */
	CellInfo()
	{
		playersInTheCell = new ArrayList<PlayerInfo>();
		actionsInTheCell = new ArrayList<Action>();
		reactionsInTheCell = new ArrayList<Reaction>();
	}

	/**
	 * ���� �� ĭ�� �ִ� �÷��̾��� ���� ��ȯ�մϴ�.
	 */
	public int GetNumberOfPlayersInTheCell() { return playersInTheCell.size(); }

	/**
	 * ���� �Ͽ� �� ĭ���� ����� �ൿ�� ������ ��ȯ�մϴ�.
	 */
	public int GetNumberOfActionsInTheCell() { return actionsInTheCell.size(); }
 
	/**
	 * ���� �Ͽ� �� ĭ���� �߻��� ����� ������ ��ȯ�մϴ�.
	 */
	public int GetNumberOfReactionsInTheCell() { return reactionsInTheCell.size(); }

	/**
	 * ���� �� ĭ�� �ִ� �ش� ��° �÷��̾� ������ ��Ͽ��� �����ɴϴ�.
	 * �÷��̾� ���� GetNumberOfPlayersInTheCell()�� ���� �� �� �ֽ��ϴ�.
	 */
	public PlayerInfo GetPlayerInfo(int index) { return playersInTheCell.get(index); }

	/**
	 * ���� �Ͽ� �� ĭ���� ����� �ش� ��° �ൿ ������ ��Ͽ��� �����ɴϴ�.
	 * �ൿ�� ������ GetNumberOfActionsInTheCell()�� ���� �� �� �ֽ��ϴ�.
	 */
	public Action GetAction(int index) { return actionsInTheCell.get(index); }

	/**
	 * ���� �Ͽ� �� ĭ���� �߻��� �ش� ��° ��� ������ ��Ͽ��� �����ɴϴ�.
	 * ����� ������ GetNumberOfReactionsInTheCell()�� ���� �� �� �ֽ��ϴ�.
	 */
	public Reaction GetReaction(int index) { return reactionsInTheCell.get(index); }
}
