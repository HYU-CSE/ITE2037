package l4g;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import l4g.data.Action;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;
import l4g.data.Reaction;

/**
 * ���ǽ� ���� �� ĭ�� ��Ÿ���� Ŭ�����Դϴ�.
 * �������� �� Ŭ������ �纻�� <code>CellInfo</code> �ν��Ͻ��� ����ϰ� �˴ϴ�.
 * 
 * �� Ŭ������ ������ �ڵ忡�� ������ �� ������
 * �̹� ������� �ν��Ͻ��鿡 �׼����� ���� �����ϴ�.
 * 
 * @author Racin
 *
 */
public class Cell
{
	// �� �� ����� �� �ϸ��� ���� ����, �� �÷��̾�� ���޵Ǵ� CellInfo�� �� ��� ��ü�� private �ʵ�� ������
	public ArrayList<PlayerInfo> players;
	public ArrayList<Action> actions;
	public ArrayList<Reaction> reactions;
	
	//�� ����� CellInfo�� ���������� ���ԵǱ� ���� players�� �Բ� �ٷ����
	public ArrayList<PlayerInfo> corpses;

	private final ArrayList<Action> empty_actions;
	private final ArrayList<Reaction> empty_reactions;
	
	Cell()
	{
		players = new ArrayList<PlayerInfo>();
		actions = new ArrayList<Action>();
		reactions = new ArrayList<Reaction>();
		
		corpses = new ArrayList<PlayerInfo>();
		
		empty_actions = new ArrayList<Action>();
		empty_reactions = new ArrayList<Reaction>();
	}

	/**
	 * �� ĭ�� ���� �纻 ������ �����մϴ�.
	 * 
	 * @param mode
	 *            0�� ��� ��� ������ ����ϴ�.
	 *            1�� ��� �÷��̾� �������� ����ϴ�.
	 *            �� ���� ��� ��ü �������� ����ϴ�.
	 */
	public CellInfo MakeCellInfo(int mode)
	{
		switch ( mode )
		{
		case 0:
			if ( players.isEmpty() == true && actions.isEmpty() == true && reactions.isEmpty() == true )
				return CellInfo.Blank;
			return new CellInfo(players, actions, reactions);
		case 1:
			if ( players.isEmpty() == true )
				return CellInfo.Blank;
			return new CellInfo(players, empty_actions, empty_reactions);
		default:
			if ( corpses.isEmpty() == true )
				return CellInfo.Blank;
			return new CellInfo(corpses, empty_actions, empty_reactions);
		}
	}
	
	public void ResetPreviousLists()
	{
		actions = new ArrayList<Action>();
		reactions = new ArrayList<Reaction>();
	}

	/* -------------------------------------------------------
	 * '��ȸ �޼���' ���� �κ�
	 */

	/**
	 * �� ĭ�� �ִ� �� �÷��̾� ���� ��ȯ�մϴ�.
	 */
	public int Count_Players()
	{
		return players.size();
	}

	/**
	 * �� ĭ�� �ִ� �÷��̾�� �� �־��� ���ǿ� �´� �÷��̾� ���� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �÷��̾� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public int CountIf_Players(Predicate<PlayerInfo> condition)
	{
		int count = 0;

		for ( PlayerInfo player : players )
			if ( condition.test(player) == true )
				++count;

		return count;
	}

	/**
	 * �� ĭ�� �ִ� �÷��̾�� ������ ���� �־��� �޼��带 �����մϴ�.
	 * �̰� ����ü ���� ����� ���� ������ �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 * 
	 * @param action
	 *            �÷��̾� ������ �μ��� �ް� Ư�� �۾��� �����ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public void ForEach_Players(Consumer<PlayerInfo> action)
	{
		players.forEach(action);
	}

	/**
	 * �� ĭ�� �ִ� �÷��̾�� �� �־��� ���ǿ� �´� �÷��̾�鸸 ��� ������� ����� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �÷��̾� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public ArrayList<PlayerInfo> Select_Players(Predicate<PlayerInfo> condition)
	{
		ArrayList<PlayerInfo> result = new ArrayList<PlayerInfo>();

		for ( PlayerInfo player : players )
			if ( condition.test(player) == true )
				result.add(player);

		return result;
	}

	/**
	 * �� ĭ���� ����� �� �ൿ ���� ��ȯ�մϴ�.
	 */
	public int Count_Actions()
	{
		return actions.size();
	}

	/**
	 * �� ĭ���� ����� �ൿ�� �� �־��� ���ǿ� �´� �ൿ ���� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �ൿ ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public int CountIf_Actions(Predicate<Action> condition)
	{
		int count = 0;

		for ( Action action : actions )
			if ( condition.test(action) == true )
				++count;

		return count;
	}

	/**
	 * �� ĭ���� ����� �ൿ�� ������ ���� �־��� �޼��带 �����մϴ�.
	 * �̰� ����ü ���� ����� ���� ������ �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 * 
	 * @param action
	 *            �ൿ ������ �μ��� �ް� Ư�� �۾��� �����ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public void ForEach_Actions(Consumer<Action> action)
	{
		actions.forEach(action);
	}

	/**
	 * �� ĭ���� ����� �ൿ�� �� �־��� ���ǿ� �´� �ൿ�鸸 ��� ������� ����� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �ൿ ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public ArrayList<Action> Select_Actions(Predicate<Action> condition)
	{
		ArrayList<Action> result = new ArrayList<Action>();

		for ( Action action : actions )
			if ( condition.test(action) == true )
				result.add(action);

		return result;
	}


	/**
	 * �� ĭ���� �߻��� �� ��� ���� ��ȯ�մϴ�.
	 */
	public int Count_Reactions()
	{
		return reactions.size();
	}

	/**
	 * �� ĭ���� �߻��� ��ǵ� �� �־��� ���ǿ� �´� ��� ���� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            ��� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public int CountIf_Reactions(Predicate<Reaction> condition)
	{
		int count = 0;

		for ( Reaction reaction : reactions )
			if ( condition.test(reaction) == true )
				++count;

		return count;
	}
	
	/**
	 * �� ĭ���� �߻��� ��ǵ� ������ ���� �־��� �޼��带 �����մϴ�.
	 * �̰� ����ü ���� ����� ���� ������ �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 * 
	 * @param action
	 *            ��� ������ �μ��� �ް� Ư�� �۾��� �����ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public void ForEach_Reactions(Consumer<Reaction> action)
	{
		reactions.forEach(action);
	}

	/**
	 * �� ĭ���� �߻��� ��ǵ� �� �־��� ���ǿ� �´� ��ǵ鸸 ��� ������� ����� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            ��� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public ArrayList<Reaction> Select_Reactions(Predicate<Reaction> condition)
	{
		ArrayList<Reaction> result = new ArrayList<Reaction>();

		for ( Reaction reaction : reactions )
			if ( condition.test(reaction) == true )
				result.add(reaction);

		return result;
	}}
