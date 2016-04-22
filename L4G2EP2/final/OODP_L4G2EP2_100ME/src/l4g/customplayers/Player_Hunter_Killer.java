package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */

/**
 * �÷��̾��� �߷п��� ���� ������ ������ �� �ִ� ���� �÷��̾��Դϴ�.<br>
 * <br>
 * <b>Note:</b> �� Ŭ������ �þ� �ۿ��� Ȯ�������� �����ϴ� �÷��̾�鿡 ���� ���� ������ ����ϴ�.
 * �ش� �÷��̾��� ����Ȯ���� <code>int weight_exist<code>�� ����˴ϴ�.
 * 
 * @author LJW
 */
class QuantumPlayer
{
	/**���� ������ */
	int ID; 
	/**ü�� */
	int HP;
	/**���� */
	StateCode state;
	/**���º�ȭ ��ٿ�. 0�� �Ǹ� ���°� ��ȭ */
	int transition_cooldown;
	/**���� �⵵ȸ�� */
	int prayCount;
	/**����Ȯ�� */
	int weight_exist;
	/**�̵�Ȯ�� */
	int[] weight_direction = new int[5];
}

/**
 * �÷��̾��� �߷п����� ������ ��ȹ�Դϴ�. ��ȹ���� �𿩼� ��� ���� ������ ���ǽ��� �̷�ϴ�.<br>
 * <br>
 * <b>Note:</b> ������ ��ȹ�� �׻� <code>Total_Players<code>���� ���� �÷��̾�(<code>QuantumPlayer<code>)�� �����ϴ�. ����
 * �÷��̾���� ��ġ�� ���� �ش� ��ȹ�� ���� ������ ����(weight)���� �Ű��� �� �ֽ��ϴ�.
 * 
 * @author LJW
 *
 */
class VirtualCell
{
	QuantumPlayer[] q_Player = new QuantumPlayer[Constants.Total_Players];
	/** �þ� ������� true, �þ� ���̸� false */
	boolean CellIsSearched;
	/** ���̸� true, ���ǽ��̸� false */
	boolean CellIsWall;
	/**�ش� ��ȹ�� �����ڰ� ������ Ȯ��. �ش� ��ȹ ����/���� �����ڵ��� ������ ���������� ��������. (0~MAX_WEIGHT) */
	int weight_Infected;
	/**�ش� ��ȹ�� ������ ����. �ش� ��ȹ ����/���� �����ڵ��� ������ �տ������� ��������. (0~���Ѵ�) */
	int weight_Survivor;
	/**�ش� ��ȹ�� ��ü ����. �ش� ��ȹ ����/���� ��ü���� ������ �տ������� ��������. (0~���Ѵ�) */
	int weight_Corpse;
	
	//�ڹ�, �ڹٴ� �������� ������, ������, ������ ������.
	public VirtualCell()
	{
		//Ang?
		for (int i = 0; i < Constants.Total_Players; ++i)
			q_Player[i] = new QuantumPlayer();
	}
}

/**
 * �÷��̾��� ��� �ӿ����� ������ ���ǽ��Դϴ�.<br>
 * <br>
 * 
 * <b>Note:</b> ������ ���ǽ��� ���� ��ȹ���� �𿩼� ������� �ֽ��ϴ�. ���� �÷��̾���� ���� ���·� �����ϴ��� Ȥ��
 * �����Ǿ������� ���� boolean�� ���� �� Ŭ������ ����˴ϴ�.
 * ������ ���ǽǿ��� 2ĭ �ʺ��� ��(<code>WALL<code>)�� �����¿쿡 �����ϸ�
 * �� ���� weight_infected�� MAX, weight_Survivor�� weight_Corpse�� 0�̱� ������
 * � ���� �÷��̾ �� �������� �̵� ������ ��ȣ���� �ʽ��ϴ�.
 * 
 * @author LJW
 *
 */
class VirtualMap
{
	VirtualCell[][] Map = new VirtualCell[Constants.Classroom_Height + 4][Constants.Classroom_Width + 4];
	boolean[] PlayerIsFound = new boolean[Constants.Total_Players];
	
	//�ڹ�, �ڹٴ� �������� ������, ������, ������ ������.
	public VirtualMap()
	{
		//Ang?
		for (int i = 0; i < Constants.Classroom_Height + 4; ++i)
		{
			//Ang?
			for (int j = 0; j < Constants.Classroom_Width + 4; ++j)
				Map[i][j] = new VirtualCell();
		}
	}
}

public class Player_Hunter_Killer extends Player
{
	DirectionCode[] directions = new DirectionCode[5];
	/** ���� �Ͽ� ���� �߷��� ���� ���̴� */
	VirtualMap CurrentVMap = new VirtualMap();

	/** ���� �Ͽ� ���� �߷��� ���� ���̴� */
	VirtualMap LastVMap  = new VirtualMap();
	
	static final int GoRight = 0;
	static final int GoDown = 1;
	static final int GoLeft = 2;
	static final int GoUp = 3;
	static final int Stay = 4;

	static final int MAX_WEIGHT = 10000;
	static final int WALL = 2;
	
	static final int Style_CorpseBomb = 10;
	static final int Style_CorpseJackpot = 11;
	static final int Style_Hunter_Killer = 12;
	static final int Style_I_am_Legend = 13;

	boolean played_CorpseJackpot;
	int state = GoRight;
	int playstyle;
	int lastPlaystyle;	//��ü ���� �÷��̽� ���� �÷��̽�Ÿ���� ����ϴ� ����
	int prayCount;
	int purification;
	int[] weight_direction = new int[5];
	boolean stylechanged;

	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_Hunter_Killer(int ID)
	{

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "Hunter Killer");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		
			this.trigger_acceptDirectInfection = true;
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ����
		// ���ƿɽô�.
	}

	/*
	 * TODO#5 ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���. �翬�� �� �濡 �� ��
	 * ������, �߰��߰� �ڵ带 ����� �δ� ���� ������, �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�. �׷���
	 * �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����, '�ڵ忡 ����̶��� ���� ��
	 * ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */
	
	/**
	 * �� ū ���� ��ȯ�ϴ� �Լ��̴�
	 */
	int BiggerNum(int a, int b)
	{
		if (a > b)
			return a;
		else
			return b;
	}
	
	/**
	 * 2���� ���� �÷��̾� �� ����Ȯ���� ���� ���� ���� �÷��̾ ��ȯ�ϴ� �Լ��̴�.
	 */
	QuantumPlayer HeavyPlayer(QuantumPlayer a, QuantumPlayer b)
	{
		if (a.weight_exist > b.weight_exist)
			return a;
		else
			return b;
	}
	
	/**
	 * ��ǥ�� ���� ���� ���޹޾� �ش� ��ǥ �ٹ��� ������ �þ߿� ���� ���� �� ���� Ž�� ���θ� true�� ����� �Լ��̴�.
	 */
	void MakeSightTrue(int row, int col, VirtualMap CurrentMap)
	{
		CurrentMap.Map[row+2][col].CellIsSearched = true;
		CurrentMap.Map[row+1][col].CellIsSearched = true;
		CurrentMap.Map[row-1][col].CellIsSearched = true;
		CurrentMap.Map[row-2][col].CellIsSearched = true;
		
		CurrentMap.Map[row][col+2].CellIsSearched = true;
		CurrentMap.Map[row][col+1].CellIsSearched = true;
		CurrentMap.Map[row][col-1].CellIsSearched = true;
		CurrentMap.Map[row][col-2].CellIsSearched = true;
		
		CurrentMap.Map[row+1][col+1].CellIsSearched = true;
		CurrentMap.Map[row-1][col+1].CellIsSearched = true;
		CurrentMap.Map[row+1][col-1].CellIsSearched = true;
		CurrentMap.Map[row-1][col-1].CellIsSearched = true;
	}
	/**
	 * ���� ���� ��ҵ��� ���� �����ϰ� ���� �ʱ�ȭ��Ű�� �Լ��̴�.
	 */
	void MapSet(VirtualMap VirtualMap)
	{
		int row, col;
		for (row = 0; row < Constants.Classroom_Height + WALL * 2; ++row)
		{
			for (col = 0; col < Constants.Classroom_Width + WALL * 2; ++col)
			{
				for (int i = 0; i < Constants.Total_Players; ++i)
				{
					VirtualMap.Map[row][col].q_Player[i].HP=0;
					VirtualMap.Map[row][col].q_Player[i].ID=0;
					VirtualMap.Map[row][col].q_Player[i].prayCount=0;
					VirtualMap.Map[row][col].q_Player[i].state=StateCode.Soul;
					VirtualMap.Map[row][col].q_Player[i].transition_cooldown=0;
					VirtualMap.Map[row][col].q_Player[i].weight_exist=0;
					for (int j = 0; j < 5; ++j)
						VirtualMap.Map[row][col].q_Player[i].weight_direction[j]=0;
				}
				
				if (row < WALL || Constants.Classroom_Width <= row || col < WALL || Constants.Classroom_Width <= col)
				{
					VirtualMap.Map[row][col].CellIsSearched = true;
					VirtualMap.Map[row][col].CellIsWall = true;
					VirtualMap.Map[row][col].weight_Infected = MAX_WEIGHT;
					VirtualMap.Map[row][col].weight_Corpse=0;
					VirtualMap.Map[row][col].weight_Survivor=0;
				}
				else
				{
					VirtualMap.Map[row][col].CellIsSearched = false;
					VirtualMap.Map[row][col].CellIsWall = false;
					VirtualMap.Map[row][col].weight_Infected=0;
					VirtualMap.Map[row][col].weight_Corpse=0;
					VirtualMap.Map[row][col].weight_Survivor=0;
				}
			}
		}
	}

	/**
	 * �÷��̾ ���� ������ ������ ���� �ʿ� ������� �Լ��̴�.
	 * 
	 * @param CurrentMap
	 */
	void Seek(VirtualMap CurrentMap)
	{
		// VirtualCell[][] VirtualMap = new
		// VirtualCell[Constants.Classroom_Height][Constants.Classroom_Width];

		for (int row = WALL; row < Constants.Classroom_Height + WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width + WALL; ++col)
			{
				// cells�� �ܼ��� ����ִ� ������, �÷��̾ ���� �Ұ����� ���¿� �ִ� ������ �� �� �ִ� ����� ����!
				// �������! ��������!
				// ������ : �����
				// �� : ...
				// 2�ð� ��
				// �� : �̳��� �����!
				if (cells[row - WALL][col - WALL].Select_Players(player->(true)).isEmpty())
				{
					CurrentMap.Map[row][col].CellIsSearched = false;
				}
				else // �����Ǵ� ������ ���� �ʿ� ����.
				{
					for (PlayerInfo Player : cells[row - WALL][col - WALL].Select_Players(player->(true)))
					{
						CurrentMap.Map[row][col].q_Player[Player.ID].HP = Player.HP;
						CurrentMap.Map[row][col].q_Player[Player.ID].state = Player.state;
						CurrentMap.Map[row][col].q_Player[Player.ID].weight_exist = MAX_WEIGHT;
						CurrentMap.PlayerIsFound[Player.ID] = true;
						CurrentMap.Map[row][col].CellIsSearched = false;
						if (Player.state == StateCode.Survivor)
						{
							MakeSightTrue(row, col, CurrentMap);
							CurrentMap.Map[row][col].weight_Survivor += MAX_WEIGHT;
						}
						else if (Player.state == StateCode.Corpse)
							CurrentMap.Map[row][col].weight_Corpse += MAX_WEIGHT;
						else if (Player.state == StateCode.Infected)
							CurrentMap.Map[row][col].weight_Infected = MAX_WEIGHT;
					}
				}
			}
		}
	}
	
	/**
	 * ����(seek)�� �Ϸ�� ���� ���� �������� �Ͽ�, ���� ���� ���� �ʿ��� ���� weight_direction�� ������� �����ڿ�
	 * ���� �߷� ��ġ�� ������� �Լ��̴�. <br>
	 * <br>
	 * <b>Note:</b>
	 * 
	 * @param CurrentMap,
	 *            LastMap
	 */
	void GuessQPlayerMoves(VirtualMap CurrentMap, VirtualMap LastMap)
	{
		for (int row = WALL; row < Constants.Classroom_Height + WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width + WALL; ++col)
			{
				if (CurrentMap.Map[row][col].CellIsSearched = false)
				{
					for (int i = 0; i < Constants.Total_Players; ++i)
					{
						/* ���� �Ұ����� �÷��̾�鿡 ���ؼ��� 
						 * ���� ���� ���� ���� �÷��̾� �̵� weight�� ������� ������ �õ��Ѵ�.
						 * �� �� ������ ID�� ���� �÷��̾ ���� �ٸ� ������ ������ �� ��ҿ� �ְ� �Ǵ� ��찡 �ִµ�
						 * weight�� weight_direction�� ������ ��� ������
						 * ���� ���� �ʿ��� ���� ����Ȯ���� ���Ҵ� �÷��̾��� ���� �����޴´�.  */
						
						CurrentMap.Map[row][col].q_Player[i] = 
						HeavyPlayer(
								HeavyPlayer(HeavyPlayer(LastMap.Map[row+1][col].q_Player[i], 
													LastMap.Map[row-1][col].q_Player[i]),
											HeavyPlayer(LastMap.Map[row][col+1].q_Player[i], 
												 	LastMap.Map[row][col-1].q_Player[i])),
								LastMap.Map[row][col].q_Player[i]);
						
						/* ���� ���� ���� �ʿ��� ���� ����Ȯ���� ���Ҵ� �÷��̾ Stay�� ������ �÷��̾��̸� 
						 * �ش� �÷��̾� ����ü�� ���, �̴� ��ȭ �⵵�� �ø� ������ ���ֵȴ�.
						 */
						
						if (HeavyPlayer(
								HeavyPlayer(HeavyPlayer(LastMap.Map[row+1][col].q_Player[i], 
													LastMap.Map[row-1][col].q_Player[i]),
											HeavyPlayer(LastMap.Map[row][col+1].q_Player[i], 
												 	LastMap.Map[row][col-1].q_Player[i])),
								LastMap.Map[row][col].q_Player[i]) == LastMap.Map[row][col].q_Player[i]
							&& CurrentMap.Map[row][col].q_Player[i].state == StateCode.Infected)
						{
							CurrentMap.Map[row][col].q_Player[i].prayCount++;
						}
						else
						{
							CurrentMap.Map[row][col].q_Player[i].prayCount=0;
						}
						
						CurrentMap.Map[row][col].q_Player[i].weight_exist =
								LastMap.Map[row][col].q_Player[i].weight_direction[Stay]
								* LastMap.Map[row][col].q_Player[i].weight_exist
								+ LastMap.Map[row+1][col].q_Player[i].weight_direction[GoUp]
										* LastMap.Map[row+1][col].q_Player[i].weight_exist
								+ LastMap.Map[row-1][col].q_Player[i].weight_direction[GoDown]
										* LastMap.Map[row-1][col].q_Player[i].weight_exist
								+ LastMap.Map[row][col+1].q_Player[i].weight_direction[GoLeft]
										* LastMap.Map[row][col+1].q_Player[i].weight_exist
								+ LastMap.Map[row][col-1].q_Player[i].weight_direction[GoRight]
										* LastMap.Map[row][col-1].q_Player[i].weight_exist;
						
						//weight_direction �ʱ�ȭ
						for (int j = 0; j < 5; ++j)
						{
							CurrentMap.Map[row][col].q_Player[i].weight_direction[j]=0;
						}	
						
						// �̹� ������ �÷��̾��� ��� ��Ȯ�� ���� �÷��̾ �����Ѵ�.
						if (CurrentMap.PlayerIsFound[i] == true)
						{
							CurrentMap.Map[row][col].q_Player[i].weight_exist = 0;
						}
					}
				}
			}
		}
	}

	/**
	 * ����(seek)�� ��ġ �߷�(GuessMoves)�� �Ϸ�� ���� ���� �������� �Ͽ�, �� ���� �߷� weight�� ���ϴ� �Լ��̴�.
	 * <br>
	 * <br>
	 * <b>Note:</b>
	 * 
	 * @param CurrentMap,
	 *            LastMap
	 */
	void GuessCellWeights(VirtualMap CurrentMap)
	{
		for (int row = WALL; row < Constants.Classroom_Height + WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width + WALL; ++col)
			{
				if (CurrentMap.Map[row][col].CellIsSearched = false)
				{
					for (int i = 0; i < Constants.Total_Players; ++i)
					{
						// �������� ��� weight�� ���� ���Ѵ�
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Infected)
						{
							CurrentMap.Map[row][col].weight_Infected = MAX_WEIGHT
									- (MAX_WEIGHT - CurrentMap.Map[row][col].q_Player[i].weight_exist)
											* (MAX_WEIGHT - CurrentMap.Map[row][col].weight_Infected) / MAX_WEIGHT;
						}
						// �������� ��� weight�� ���� ���Ѵ�.
						else if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Survivor)
						{
							CurrentMap.Map[row][col].weight_Survivor += CurrentMap.Map[row][col].q_Player[i].weight_exist;
						}
						// ��ü�� ��� weight�� ���� ���ϸ鼭 ���ÿ� ��ü�� transition_cooldown��
						// ���� ��ü ���� ���� weight�� �տ� �����Ѵ�.
						else if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Corpse)
						{
							CurrentMap.Map[row][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
							// �̽��� ��ü�� �ֺ� 12ĭ�� ���� ������ �۶߸���. ���̥�
							if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown == 2)
							{
								CurrentMap.Map[row
										+ 2][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row
										+ 1][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row][col
										+ 2].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row][col
										+ 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;

								CurrentMap.Map[row
										- 2][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row
										- 1][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row][col
										- 2].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row][col
										- 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;

								CurrentMap.Map[row + 1][col
										+ 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row - 1][col
										- 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row + 1][col
										- 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row - 1][col
										+ 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
							}
							// ���� ��ü�� �ֺ� 4ĭ�� ������ �۶߸���.
							else if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown == 1)
							{
								CurrentMap.Map[row
										+ 1][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row
										- 1][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row][col
										+ 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
								CurrentMap.Map[row][col
										- 1].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
							}
							// ����ü�� �Ǳ� ���� ���� ��ü�� ������ �۶߸��� �ʴ´�. ���� �� ���� ��ü�� ��ǻ� ����ü�� ���� ���赵 ����̴�.
							else if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown == 0)
							{
								CurrentMap.Map[row][col].weight_Infected += CurrentMap.Map[row][col].q_Player[i].weight_exist;
							}
						}
					}
				}
			}
		}
		//��� �� ��ü�� ������ ��Ѹ� ������� ���� ġ���. � ���ض�!
		for (int row = 0; row < Constants.Classroom_Height + WALL * 2; ++row)
		{
			for (int col = 0; col < Constants.Classroom_Width + WALL * 2; ++col)
			{
				if(CurrentMap.Map[row][col].CellIsWall)
					CurrentMap.Map[row][col].weight_Corpse = 0;
			}
		}
	}

	/**
	 * ����(seek)�� ��ġ �߷�(GuessMoves), ���� �߷�(GuessCellWeights)�� ��� �Ϸ�� ���� ���� ��������
	 * �Ͽ� ���� �÷��̾��� ���� �� weight_direction�� ����ϴ� �Լ��̴�. <br>
	 * <br>
	 * <b>Note:</b>
	 * 
	 * @param CurrentMap,
	 *            LastMap
	 */
	void GuessQPlayerStates(VirtualMap CurrentMap)
	{
		for (int row = WALL; row < Constants.Classroom_Height + WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width + WALL; ++col)
			{
				if (CurrentMap.Map[row][col].CellIsSearched = false)
				{
					for (int i = 0; i < Constants.Total_Players; ++i)
					{
						// �������� ���� ��ȭ
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Survivor)
						{
							// ���� Ȯ���� MAX_WEIGHT �̻��� ������ ���� ��� ��ü�� ���°� ��ȭ�ȴ�.
							if (CurrentMap.Map[row][col].weight_Infected == MAX_WEIGHT)
							{
								CurrentMap.Map[row][col].q_Player[i].state = StateCode.Corpse;
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown = Constants.Corpse_Rise_Cooldown;
								CurrentMap.Map[row][col].q_Player[i].HP = Constants.Corpse_Init_HP;
								for (int j = 0; j < 4; ++j)
								{
									CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = 0;
								}
								CurrentMap.Map[row][col].q_Player[i].weight_direction[Stay] = MAX_WEIGHT;
							}
							/*
							 * 2X2 �þ� ������ weight_Infected�� ��������
							 * �Ͽ� weight_direction�� �����Ѵ�. 1. weight_Infected�� ����
							 * weight_direction�� �ݿ� ���� �켱 �������� ���
							 * weight_direction�� ���� ���� MAX_WEIGHT�� �����Ѵ�. 2 2 1 2
							 * 2 1 S 1 2 2 1 2 2 - 2ĭ ���� ������ ���� infection�� ������
							 * ��� �ش� ������ weight_direction���� weight_infected�� ����.
							 * - 2ĭ �밢�� ������ ���� infection�� ������ ��� �ش� ������
							 * weight_direction���� weight_infected/2�� ����. - 1ĭ
							 * ������ ���� infection�� ������ ��� �ش� ������
							 * weight_direction���� weight_infected/5�� ����. - ���
							 * ������ weight_direction�� ���� ���� 0�� ��� �÷��̾��� state��
							 * Corpse�� �ȴ�. - ��� ������ weight_direction�� ���� ���� 0����
							 * Ŭ ��� �� ���� �������� �ٽ� weight_direction�� ������
							 * MAX_WEIGHT�� �ǵ��� ������ �����.
							 * 
							 * �ּ��� ���ڶ��! ��Ŭ���� �����! ��Ŭ���� ��! ���! ��Ŭ���� �����! ��Ŭ����
							 * �����...
							 */
							else
							{
								// GoUp ���
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row - 1][col].weight_Infected / 5)
										- (CurrentMap.Map[row - 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 2][col].weight_Infected);
								
								// GoDown ���
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row + 1][col].weight_Infected / 5)
										- (CurrentMap.Map[row + 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row + 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row + 2][col].weight_Infected);
								
								// GoLeft ���
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row][col - 1].weight_Infected / 5)
										- (CurrentMap.Map[row + 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row][col - 2].weight_Infected);
								
								// GoRight ���
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row][col + 1].weight_Infected / 5)
										- (CurrentMap.Map[row + 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row][col + 2].weight_Infected);
								
								int AddedRatio = 0;
								// 0 ������ ���� direction�� 0���� ġȯ�ϰ� ��� direction�� �ջ�
								// ���� ����
								for (int j = 0; j < 4; ++j)
								{
									if (CurrentMap.Map[row][col].q_Player[i].weight_direction[j] < 0)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = 0;
									AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[j];
								}
								// �ջ� ���� 0, �� �� �� �ִ� ���� ���� ��� ������ �߾����� ���� ������ ������ �̵��Ѵ�.
								if (AddedRatio == 0)
								{
									if (!CurrentMap.Map[row-1][col].CellIsWall)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp]=MAX_WEIGHT;
									if (!CurrentMap.Map[row+1][col].CellIsWall)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[GoDown]=MAX_WEIGHT;
									if (!CurrentMap.Map[row][col-1].CellIsWall)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[GoLeft]=MAX_WEIGHT;
									if (!CurrentMap.Map[row][col+1].CellIsWall)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[GoRight]=MAX_WEIGHT;
									for (int j = 0; j < 4; ++j)
										AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[j];
									for (int j = 0; j < 4; ++j)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = CurrentMap.Map[row][col].q_Player[i].weight_direction[j]
												* MAX_WEIGHT / AddedRatio;
								}
								// �ջ� ���� 0�� �ƴ� ���, ������ ���߾� �ջ� ���� MAX_WEIGHT�� �ǵ���
								// �� weight_direction�� �����Ѵ�.
								else
								{
									for (int j = 0; j < 4; ++j)
									{
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = CurrentMap.Map[row][col].q_Player[i].weight_direction[j]
												* MAX_WEIGHT / AddedRatio;
									}
								}
								// I want some �ҹ��!
							}
						}
						// ��ü�� ���� ��ȭ
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Corpse)
						{
							// ���� ���������� ���̰� ����Ǿ� 3�� �ȿ� �����ڰ� �ȴ�.
							CurrentMap.Map[row][col].q_Player[i].transition_cooldown--;
							if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown == 0)
							{
								CurrentMap.Map[row][col].q_Player[i].state = StateCode.Infected;
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown = 6;
							}
							else
								CurrentMap.Map[row][col].q_Player[i].HP += Constants.Infected_Infection_Power
										* CurrentMap.Map[row][col].weight_Infected;
						}
						// �������� ���� ��ȭ
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Infected)
						{
							// ü�� ����
							CurrentMap.Map[row][col].q_Player[i].HP += Constants.Corpse_Heal_Power
									* CurrentMap.Map[row][col].weight_Corpse;
							// ��ü ���� ���� ���ɼ��� �����̶� �ִٸ� praycount�� 0�� �ȴ�.
							if (CurrentMap.Map[row][col].weight_Corpse > 0)
							{
								CurrentMap.Map[row][col].q_Player[i].prayCount=0;
							}
							// ��ü ���� ���� �ʴٴ� ���� Ȯ���� ��� prayCount��ŭ transition_cooldown�� �����Ѵ�.
							else
							{
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown-=
										CurrentMap.Map[row][col].q_Player[i].prayCount;
							}
							// �ش� ĭ�� ��ü ���԰� MAX �̸��� ��� HP�� ��ü ���Կ� �ݺ���Ͽ� �����Ѵ�.
							if (CurrentMap.Map[row][col].weight_Corpse < MAX_WEIGHT)
								CurrentMap.Map[row][col].q_Player[i].HP -= 1
										* (MAX_WEIGHT - CurrentMap.Map[row][col].weight_Corpse);
							/*
							 * ü���� 0 ������ ��� ��ȥ���� ���� ���� ��ȥ�� �ǻ� �������� ����� ��� �̴�
							 * ��Ȯ������ �ʹ� �����Ƿ� ������� �ʴ´�. ���� �������� ���Ͽ� �켱 ��ȥ��
							 * trasition_cooldown�� ����Ѵ�. �㳪 ��¥��
							 * GuessQPlayerMoves�Լ����� ��ȥ�� ���� ������� �����Ƿ� ��ȥ ����
							 * �÷��̾�� ���� �����ڸ��� �ﰢ������ �Ұŵȴ�.
							 */
							if (CurrentMap.Map[row][col].q_Player[i].HP <= 0)
							{
								CurrentMap.Map[row][col].q_Player[i].state = StateCode.Soul;
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown = 3;
							}
							//�⵵�� ���������� ��ģ ��쿡�� ��ȥ���� ����.
							if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown <= 0)
							{
								CurrentMap.Map[row][col].q_Player[i].state = StateCode.Soul;
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown = 2;
							}
							/*
							 * ���� �������� AI�� �ܼ�ȭ ������ �̿��Ѵ�.
							 * ���� �����ڴ� �ֺ� 5X5ĭ�� ���ؼ��� �����ϸ�, ��ü�� ���� ������ ���� ����� �����Ѵ�
							 * (��¥�� ���� ��Ȳ������ ��ü�� ���� ������ ���� ����� ���� ���� ����)
							 * �����ڿ� ���� ��ȣ���� �׻� �����ϸ�, ü���� �������� ��ü�� ���� ��ȣ���� ��������.
							 * ü���� 1�� ���� �������� �Ͽ�, �����ڴ� �����ں��� ��ü�� 4�� ��ȣ�Ѵ�.
							 * ���� �� �Ÿ��� ��ǥ���� ����� �Ÿ��� ��ǥ�� 2�� ��ȣ�Ѵ�.
							 * �ڱ� ��ġ�� �ִ� ��ü�� ��� ���� HP�� ������� �����ڿ� ���� 8�� ��ȣ�Ѵ�.
							 */
							else
							{
								for (int j = -2; j <= 2; ++j)
								{
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoDown] +=
											+ CurrentMap.Map[row + 1][col+j].weight_Survivor;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoDown] +=
											+ CurrentMap.Map[row + 2][col+j].weight_Survivor/2;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoDown] +=
											+ 4*(CurrentMap.Map[row + 1][col+j].weight_Corpse
													/CurrentMap.Map[row + 1][col+j].q_Player[i].HP);
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoDown] +=
											+ 4*(CurrentMap.Map[row + 2][col+j].weight_Corpse
													/CurrentMap.Map[row + 1][col+j].q_Player[i].HP)/2;
									
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] +=
											+ CurrentMap.Map[row - 1][col+j].weight_Survivor;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] +=
											+ CurrentMap.Map[row - 2][col+j].weight_Survivor/2;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] +=
											+ 4*(CurrentMap.Map[row - 1][col+j].weight_Corpse
													/CurrentMap.Map[row - 1][col+j].q_Player[i].HP);
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] +=
											+ 4*(CurrentMap.Map[row - 2][col+j].weight_Corpse
													/CurrentMap.Map[row - 1][col+j].q_Player[i].HP)/2;
									
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoLeft] +=
											+ CurrentMap.Map[row + j][col - 1].weight_Survivor;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoLeft] +=
											+ CurrentMap.Map[row + j][col - 2].weight_Survivor/2;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoLeft] +=
											+ 4*(CurrentMap.Map[row + j][col - 1].weight_Corpse
													/CurrentMap.Map[row + 1][col+j].q_Player[i].HP);
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoLeft] +=
											+ 4*(CurrentMap.Map[row + j][col - 2].weight_Corpse
													/CurrentMap.Map[row + 1][col+j].q_Player[i].HP)/2;
									
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoRight] +=
											+ CurrentMap.Map[row + j][col + 1].weight_Survivor;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoRight] +=
											+ CurrentMap.Map[row + j][col + 2].weight_Survivor/2;
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoRight] +=
											+ 4*(CurrentMap.Map[row + j][col + 1].weight_Corpse
													/CurrentMap.Map[row + 1][col+j].q_Player[i].HP);
									CurrentMap.Map[row][col].q_Player[i].weight_direction[GoRight] +=
											+ 4*(CurrentMap.Map[row + j][col + 2].weight_Corpse
													/CurrentMap.Map[row + 1][col+j].q_Player[i].HP)/2;
									
									CurrentMap.Map[row][col].q_Player[i].weight_direction[Stay] +=
											8*CurrentMap.Map[row][col].weight_Corpse;	
								}
								int AddedRatio = 0;
								//���ڸ��� ������ direction�� �ջ갪�� ����
								for (int j = 0; j < 5; ++j)
								{
									AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[j];
								}
								// �׷� ���ɼ��� ���� ������, �ջ� ���� 0, �� �� �� �ִ� ���� ���� ��� ���ڸ��� �ִ´�.
								if (AddedRatio == 0)
								{
									CurrentMap.Map[row][col].q_Player[i].weight_direction[Stay] = MAX_WEIGHT;
								}
								// �ջ� ���� 0�� �ƴ� ���, ������ ���߾� �ջ� ���� MAX_WEIGHT�� �ǵ���
								// �� weight_direction�� �����Ѵ�.
								else
								{
									AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[Stay];
									for (int j = 0; j < 5; ++j)
									{
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = CurrentMap.Map[row][col].q_Player[i].weight_direction[j]
												* MAX_WEIGHT / AddedRatio;
									}
								}
								//�S�S�S��ָ�����!
							}
						}
					}
				}
			}
		}

	}

	/**
	 *  CurrentVMap�� LastVMap���� �����, ���ο� CurrentMap�� ���� �� �߷��ϴ� �Լ��̴�.
	 */
	void UpdateVirtualMap()
	{
		LastVMap = CurrentVMap;
		MapSet(CurrentVMap);
		Seek(CurrentVMap);
		GuessQPlayerMoves(CurrentVMap, LastVMap);
		GuessCellWeights(CurrentVMap);
		GuessQPlayerStates(CurrentVMap);
	}
	
	/**
	 *  ���� ���� �� ���� ���� �ʱ�ȭ��Ű�� ���� �÷��̾ ���� ��ġ�ϴ� �Լ��̴�.
	 */
	void UpdateVirtualMap_First()
	{
		int UnSearchedCells=0;
		
		MapSet(CurrentVMap);
		Seek(CurrentVMap);
		
		for (int row = WALL; row < Constants.Classroom_Height+WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width+WALL; ++col)
			{
				if (!CurrentVMap.Map[row][col].CellIsSearched)
					UnSearchedCells++;
			}
		}		
		//��Ȯ�� �÷��̾�� ������ ��Ȯ�� ������ ��ü ��Ȯ�� ������ ���̿� �ݺ���ϴ� Ȯ���� �����Ѵ�.
		for (int row = WALL; row < Constants.Classroom_Height+WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width+WALL; ++col)
			{
				for (int i = 0; i < col; ++i)
				{
/*�Ƶ���! */			if (!CurrentVMap.PlayerIsFound[i])
  /*�Ƶ���! */ 		{
	/*�Ƶ���! */   		CurrentVMap.Map[row][col].q_Player[i].weight_exist=MAX_WEIGHT/UnSearchedCells;	
  /*�Ƶ���! */		}
/*�Ƶ���! */		}
			}
		}
		
		GuessCellWeights(CurrentVMap);
		GuessQPlayerStates(CurrentVMap);	
	}
	
	/**
	 * ��ī���� �����ڴ� ������� ������ �����ϱ� �����Ѵ�.
	 * ��Ƴ��� �� ���� �߿�������, ������� ����ü�� �����ϰų� �ټ��� �����ڿ� ���Ĵٴϸ� ������ �����ϴ� ��
	 * �ణ�� ������ �����Ͽ� ���� �� ������� �÷��̸� �븮���� �ϴ� ���� ��ī���� ������ �÷����� �ٽ��̴�.
	 * ��ī���� �����ڴ� �ڱ� �ֺ� �������� ������ ����ü�� ���� ���� ���� �����ϴ�.
	 */
	int UpdateState_Survivor_Scout()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//���� ���� �ʱ�ȭ
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 12���� ������ �������� ���� ����ü�� �� �� ������ �� �� �����Ƿ� �������� 2ĭ ������ �ٰ����� ���� ���� ���� Ȯ���� ����Ͽ� �Ⱦ��Ѵ�.
		 * ������ 1ĭ ���� ����� �ִ� ���� ������ Ȯ���� �����Ƿ� �ſ� �����Ѵ�. 
		 */
		if(turnInfo.turnNumber<12)
		{
			weight_direction[GoUp]
					= CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Survivor;
			weight_direction[GoDown]
					= CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Survivor;
			weight_direction[GoLeft]
					= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Survivor;
			weight_direction[GoRight]
					= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Survivor;
			
			weight_direction[GoUp]
					-= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor/10;
			weight_direction[GoDown]
					-= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor/10;
			weight_direction[GoLeft]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor/10;
			weight_direction[GoRight]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor/10
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor/10;
		}
		/*
		 * 12ĭ�� ���� �Ŀ��� �þ� ���� ����ü�� �ִ� ���� �ص��� �Ⱦ��ϸ�, ���ǽǿ��� ������ �е��� ���� ������ �̵��ϰ��� �Ѵ�.
		 * �������� ��� �̵��� ������ ���� ������ ���� ����� ���� ���� �÷��̾�� �ٰ����� �ͺ��ٴ�
		 * ���� �� �ִ��� �ִ��� ������ �е��� ���� �������� �̵��ϴ� ���� ���� ������ ���� �� �ִ�.
		 */
		else
		{	
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//�Ʒ��� ������ �е� ���
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//������ ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/* �����¿쿡�� �����ڿ� ���� ���
			 * 2ĭ ������ �����ڸ� 1ĭ ������ �����ں��� 5�� ����Ѵ�.
			 * 1ĭ ������ �����ڸ� ����ġ�� �ŷ��ϴ� �� ��������, ������� ��ȭ�⵵�� ���� �ҾȰ��� ����� ������
			 * ���� ������ �� �� ����.
			 */
			weight_direction[GoUp]
					-= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Infected*4;
			weight_direction[GoDown]
					-= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Infected*4;
			weight_direction[GoLeft]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Infected*4;
			weight_direction[GoRight]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected*20
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Infected*4;
		}


		/*
		 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
		 */
		if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoUp]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoDown]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
			weight_direction[GoLeft]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
			weight_direction[GoRight]= -Integer.MAX_VALUE;
		
		BiggestWeight = BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
				BiggerNum(weight_direction[GoRight], weight_direction[GoLeft]));
		
		if (BiggestWeight==weight_direction[GoUp])
			return GoUp;
		else if (BiggestWeight==weight_direction[GoDown])
			return GoDown;
		else if (BiggestWeight==weight_direction[GoLeft])
			return GoLeft;
		else //if (BiggestWeight==weight_direction[GoRight])
			return GoRight;
	}

	/**
	 * �������� �����ڴ� ��� ���赵 �������� �ʴ´�.
	 * �������� �������� �ൿ����� �� �ϳ�, ��Ƴ��� ���̴�. ������ ��� �ɷ��� ȸ�ǿ� ġ���Ѵ�.
	 * �������� �����ڴ� �ڱ� �ֺ� �������� ������ �������� �������ٴ� ������, �и��� ���� ���ɼ��� ������ ���� �����ϴ�.
	 */
	int UpdateState_Survivor_Loner()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//���� ���� �ʱ�ȭ
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 12���� ������ �������� ���� ����ü�� �� �� ������ �� �� ����.
		 * ������ �������� �����ڴ� ���� ������������ �ҽ��Ѵ�.
		 * �������� �����ڴ� 1ĭ ������ �����ں��� 2ĭ ������ �����ڸ� 2�� �̻� �ҽ��Ѵ�.
		 */
		if(turnInfo.turnNumber<12)
		{
			weight_direction[GoUp]
					-= CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Survivor/10;
			weight_direction[GoDown]
					-= CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Survivor/10;
			weight_direction[GoLeft]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Survivor/10;
			weight_direction[GoRight]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Survivor/10;
			
			weight_direction[GoUp]
					-= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor/5;
			weight_direction[GoDown]
					-= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor/5;
			weight_direction[GoLeft]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor/5;
			weight_direction[GoRight]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor/5;
		}
		/*
		 * 12ĭ�� ���� �Ŀ��� �þ� ���� ����ü�� �ִ� ���� �ص��� �Ⱦ��ϸ�, ���ǽǿ��� ������ �е��� ���� ������ �̵��ϰ��� �Ѵ�.
		 */
		else
		{	
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//�Ʒ��� ������ �е� ���
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//������ ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/* �����¿쿡�� �����ڿ� ���� ���
			 * 1ĭ ������ �����ڴ� ��������� �� ����Ѵ�.
			 * ��ȭ �⵵�ڰ� �ƴ� �̻� �հ� ������ ���ɼ��� ����ϰԳ��� �����ϱ� ����
			 */
			weight_direction[GoUp]
					-= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Infected*50;
			weight_direction[GoDown]
					-= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Infected*50;
			weight_direction[GoLeft]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Infected*50;
			weight_direction[GoRight]
					-= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Infected*50;
		}


		/*
		 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
		 */
		if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoUp]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoDown]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
			weight_direction[GoLeft]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
			weight_direction[GoRight]= -Integer.MAX_VALUE;
		
		BiggestWeight = BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
				BiggerNum(weight_direction[GoRight], weight_direction[GoLeft]));
		
		if (BiggestWeight==weight_direction[GoUp])
			return GoUp;
		else if (BiggestWeight==weight_direction[GoDown])
			return GoDown;
		else if (BiggestWeight==weight_direction[GoLeft])
			return GoLeft;
		else //if (BiggestWeight==weight_direction[GoRight])
			return GoRight;
	}

	/**
	 * ���� ���ɼ��� ���� ���� ������, Hunter Killer �÷��̽�Ÿ�� �� �����ڰ� �Ǿ��� ���� �ൿ����̴�.
	 */
	int UpdateState_Survivor_Hunter()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//���� ���� �ʱ�ȭ
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* �������� �������� �ݴ�� �ൿ�Ѵ�.
		 */
		if(turnInfo.turnNumber<12)
		{
			weight_direction[GoUp]
					+= CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Survivor/10;
			weight_direction[GoDown]
					+= CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Survivor/10;
			weight_direction[GoLeft]
					+= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Survivor/10;
			weight_direction[GoRight]
					+= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Survivor/10;
			
			weight_direction[GoUp]
					+= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor/5;
			weight_direction[GoDown]
					+= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor/5;
			weight_direction[GoLeft]
					+= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor/5;
			weight_direction[GoRight]
					+= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor/5
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor/5;
		}
		/*
		 * �������� �������� �ݴ�� �ൿ�Ѵ�.
		 */
		else
		{	
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//�Ʒ��� ������ �е� ���
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//������ ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/* �������� �������� �ݴ�� �ൿ
			 */
			weight_direction[GoUp]
					+= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Infected*50;
			weight_direction[GoDown]
					+= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Infected*50;
			weight_direction[GoLeft]
					+= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Infected*50;
			weight_direction[GoRight]
					+= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected*100
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Infected*50;
		}


		/*
		 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
		 */
		if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoUp]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoDown]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
			weight_direction[GoLeft]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
			weight_direction[GoRight]= -Integer.MAX_VALUE;
		
		BiggestWeight = BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
				BiggerNum(weight_direction[GoRight], weight_direction[GoLeft]));
		
		if (BiggestWeight==weight_direction[GoUp])
			return GoUp;
		else if (BiggestWeight==weight_direction[GoDown])
			return GoDown;
		else if (BiggestWeight==weight_direction[GoLeft])
			return GoLeft;
		else //if (BiggestWeight==weight_direction[GoRight])
			return GoRight;
	}

	
	/**
	 * ��ɲ��� �����ڴ� ��ȸ�������̴�.
	 * �̵��� �׻� ��ü�� ���� ���� �� �ִ� �̵�� �����ڸ� �Ѿ� ���� �� �ִ� �̵��� �������Ѵ�.
	 * ���� ����ü ������ �����ڿ� ���� ����ġ�� ���� �����Ǹ� �ﰢ������ �ڻ��� �õ��Ѵ�.(��� ������ ������ �ٸ������� ��)
	 */
	int UpdateState_Infected_Hunter()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		ArrayList<PlayerInfo> corpses = new ArrayList<>();
		/** ��ü ��ȣ�� */
		int preferCorpse;

		// ��� ĭ�� �����Ͽ� ���ǽǿ� �ִ� ��� ��ü�鿡 ���� ����� ���� 
		for ( CellInfo[] rows : cells )
			for ( CellInfo cell : rows )
				corpses.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));

		// ���� HP�� ���� ��ü ��ȣ���� ����
		if (myInfo.HP > 25)
			preferCorpse = 4;
		else if (myInfo.HP > 5)
			preferCorpse = 100/myInfo.HP;
		else
			preferCorpse = 20;

		//���� ���� �ʱ�ȭ
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 12���� ������ ���� ��ɲ��� �����ڰ� ���� �����ؾ� �� �����̴�.
		 * 12���� �� ������ ���� �ٸ��ƴ� �����ڿ��� �־ ���� ���� ������ �ø� �� �ִ� �м����̱� ����
		 * ������ 9���� ������ ������ �����ڴ� �ִ��� ������ �е��� ���� �������� �̵��Ѵ�.
		 */
		if(turnInfo.turnNumber<10)
		{
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//�Ʒ��� ������ �е� ���
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//������ ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/*
			 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
			 */
			if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
				weight_direction[GoUp]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
				weight_direction[GoDown]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
				weight_direction[GoLeft]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
				weight_direction[GoRight]= -Integer.MAX_VALUE;
			
			BiggestWeight = BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
					BiggerNum(weight_direction[GoRight], weight_direction[GoLeft]));
			
			if (BiggestWeight==weight_direction[GoUp])
				return GoUp;
			else if (BiggestWeight==weight_direction[GoDown])
				return GoDown;
			else if (BiggestWeight==weight_direction[GoLeft])
				return GoLeft;
			else //if (BiggestWeight==weight_direction[GoRight])
				return GoRight;
		}
		/*
		 * 9�ϱ��� ������ �е��� ���� �������� �̵��Ͽ��ٸ�
		 * 10, 11���� �׾߸��� ���ɿ� ���� �ñ�� ���� ���� �ð��̴�.
		 * �ֺ��� �ִ� ���� ���� ���̰��̴�. �ִ��� ���̰� ������ ���� ���� ������ �̵��ϱ⸸ �ϸ� �ȴ�.
		 */
		else if (10<=turnInfo.turnNumber && turnInfo.turnNumber<12)
		{
			/* �����¿쿡�� �����ڿ� ���� ���
			 * 2ĭ ������ �����ڸ� 1ĭ ������ �����ں��� 2�� ��ȣ�Ѵ�. (1ĭ ������ �����ڴ� �ǿܷ� óġ�ϱ� �����)
			 * ������� ���� �̹� �е��� ���� �������. ��ȹ��ζ�� ����� �̹� �����ڵ��� �Ѻ����̴�.
			 */
			weight_direction[GoUp]
					= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Survivor;
			weight_direction[GoDown]
					= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Survivor;
			weight_direction[GoLeft]
					= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Survivor;
			weight_direction[GoRight]
					= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Survivor;
			
			/*
			 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
			 */
			if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
				weight_direction[GoUp]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
				weight_direction[GoDown]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
				weight_direction[GoLeft]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
				weight_direction[GoRight]= -Integer.MAX_VALUE;
			
			BiggestWeight = BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
					BiggerNum(weight_direction[GoRight], weight_direction[GoLeft]));
			
			if (BiggestWeight==weight_direction[GoUp])
				return GoUp;
			else if (BiggestWeight==weight_direction[GoDown])
				return GoDown;
			else if (BiggestWeight==weight_direction[GoLeft])
				return GoLeft;
			else //if (BiggestWeight==weight_direction[GoRight])
				return GoRight;
		}
		/*
		 * 12�� ���ĺ��ʹ� ������� �÷��̸� �ؾ� �Ѵ�.
		 * ��ü��� Ȯ�������� ���� �̵�� ������ ����̶�� ��Ȯ�������� ū �̵� ���̿���
		 * ������� ��� �� �� �־�� �ϱ� �����̴�.
		 */
		else
		{		
			/* �����¿쿡�� ������ ��ġ �ľ�
			 * 2ĭ ������ �����ڰ� ���� ��� �ش� ���������� ��ȣ�� ����
			 * �����ڿ� ���� ��ȣ���� �׻� ����.
			 */
			weight_direction[GoUp]
					= CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor*2;
			weight_direction[GoDown]
					= CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor*2;
			weight_direction[GoLeft]
					= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Survivor*2;
			weight_direction[GoRight]
					= CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Survivor*2
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Survivor*2;
			
			/* 1ĭ ������ �������� ��� ���ڸ� ���� ��ȣ�� ����.
			 * �� ��, 1ĭ ������ �������� �ֺ��� �ٸ� �����Ұ� �ִٸ� ���ڸ� ���� ��ȣ���� ����.
			 * ��, ��ȭ �⵵�� �÷��� ��ȭ�ع����� ���� ����.
			 * 1ĭ ������ �������� ����� ���� �����Ҷ�� �����Ͽ�, �����ڴ� 1ĭ ������ �����ڸ� 2ĭ ������ �����ں��� 2�� ��ȣ�Ѵ�.
			 * �Ʒ� �ڵ忡 weight_Infected�� �ִ°� ���� ������ �ƴϴ�. ���ٰ� �����ϸ� ū�ϳ�.
			 */
			if(purification-prayCount >0)
			{
				weight_direction[Stay]
					=CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].weight_Survivor*MAX_WEIGHT*2
					/(4*MAX_WEIGHT-(CurrentVMap.Map[pos_now.row+WALL+2][pos_now.column+WALL].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected))
					
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].weight_Survivor*MAX_WEIGHT*2
					/(4*MAX_WEIGHT-(CurrentVMap.Map[pos_now.row+WALL-2][pos_now.column+WALL].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected))
					
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].weight_Survivor*MAX_WEIGHT*2
					/(4*MAX_WEIGHT-(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+2].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL+1].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL+1].weight_Infected))
					
					+CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].weight_Survivor*MAX_WEIGHT*2
					/(4*MAX_WEIGHT-(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-2].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL-1].weight_Infected
					+CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL-1].weight_Infected));
			}	
			
			/* 
			 * ���ڸ��� �ִ� ��ü�� ��� �Ÿ��� �ִ� ��ü�� ���� 3�� ��ȣ�Ѵ�. ��, ���ڸ��� �ִ� ��ü�� �Ÿ��� �ִ� �����ڿ� ���� �ִ� 15�� ��ȣ�Ѵ�.
			 * ��ü ��ȣ���� �� �� Ư���� ������ ��ġ�µ�, �̴� �ش� ��ü�� �����Ҷ����� ��ü�� ���� �� �ִ� �����ΰ��� ����ؾ� �ϱ� �����̴�.
			 */
			for ( PlayerInfo corpse : corpses )
			{
				//���� �� �ִ� ��ü��� (�� ��ü�� ���� �����ð��� ��ü���� �Ÿ����� Ŭ ���)
				if(corpse.transition_cooldown > corpse.position.GetDistance(myInfo.position))
				{
					//���ڸ��� ��ü�� 3�� ��ȣ
					if(corpse.position == pos_now)
					{
						weight_direction[Stay]
						+=CurrentVMap.Map[corpse.position.row+WALL][corpse.position.column+WALL].weight_Infected*3*preferCorpse
						*corpse.transition_cooldown;
					}
					//��ü�� ���ʿ� ���� ���
					else 
					{
						if (corpse.position.row < pos_now.row)
						{
							weight_direction[GoUp]
							+=CurrentVMap.Map[corpse.position.row+WALL][corpse.position.column+WALL].weight_Infected*preferCorpse
							*corpse.transition_cooldown;
						}
						else
						{
							weight_direction[GoDown]
							+=CurrentVMap.Map[corpse.position.row+WALL][corpse.position.column+WALL].weight_Infected*preferCorpse
							*corpse.transition_cooldown;
						}
						
						if (corpse.position.column < pos_now.column)
						{
							weight_direction[GoLeft]
							+=CurrentVMap.Map[corpse.position.row+WALL][corpse.position.column+WALL].weight_Infected*preferCorpse
							*corpse.transition_cooldown;
						}
						else
						{
							weight_direction[GoRight]
							+=CurrentVMap.Map[corpse.position.row+WALL][corpse.position.column+WALL].weight_Infected*preferCorpse
							*corpse.transition_cooldown;
						}
					}
				}
			}
			
			/* ������ �е� ����� ���� �켱���� ���� ����̴�.
			 * ��ǻ� �ڱ� �ֺ��� ��������̰ų� �����ڷθ� �������� ���� �̻� ��ġ�� ������ �ּ�ȭ�Ǿ�� �Ѵ�.
			 * �þ� ���� ������ �е����� �þ� �� ������ �е��� 5�� �̻� ���� ������ �þ� ���� �켱���� �� ���� ������
			 * WEIGHT�� �Ű����� ���� �߿��ϴ�.
			 */
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/(pos_now.row*10);
			}
			
			//�Ʒ��� ������ �е� ���
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/((Constants.Classroom_Height-pos_now.row-1)*10);
			}
			
			//���� ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/(pos_now.column*10);
			}
			
			//������ ������ �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/((Constants.Classroom_Width-pos_now.column-1)*10);
			}
		}
		/*
		 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
		 */
		if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoUp]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
			weight_direction[GoDown]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
			weight_direction[GoLeft]= -Integer.MAX_VALUE;
		if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
			weight_direction[GoRight]= -Integer.MAX_VALUE;
		
		BiggestWeight = BiggerNum(BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
				BiggerNum(weight_direction[GoRight], weight_direction[GoLeft])), weight_direction[Stay]);
		
		if (BiggestWeight==weight_direction[GoUp])
			return GoUp;
		else if (BiggestWeight==weight_direction[GoDown])
			return GoDown;
		else if (BiggestWeight==weight_direction[GoLeft])
			return GoLeft;
		else if (BiggestWeight==weight_direction[GoRight])
			return GoRight;
		else //if (BiggestWeight==weight_direction[Stay])
			return Stay;
	}
	
	/** 
	 * ��ü ��ź�� �����ڴ� �����ΰ� �����ڶ�� ����� ���� �� �����Ѵ�.
	 * ������ ��ȭ �⵵�� �÷��� �ִ��� ���� ������ �Ѵ�.
	 */
	int UpdateState_Infected_CorpseBomb()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//���� ���� �ʱ�ȭ
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		// �� �ؿ� ��ü�� ��� ������ ��ü �е��� ���� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
		{
			//���� ��ü �е� ���
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//�Ʒ��� ��ü �е� ���
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//���� ��ü �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//������ ��ü �е� ���
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/*
			 * ��� ���簣�� �����δ� ���� �̵����� �ʴ´�.
			 */
			if(CurrentVMap.Map[pos_now.row+WALL-1][pos_now.column+WALL].CellIsWall)
				weight_direction[GoUp]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL+1][pos_now.column+WALL].CellIsWall)
				weight_direction[GoDown]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL-1].CellIsWall)
				weight_direction[GoLeft]= -Integer.MAX_VALUE;
			if(CurrentVMap.Map[pos_now.row+WALL][pos_now.column+WALL+1].CellIsWall)
				weight_direction[GoRight]= -Integer.MAX_VALUE;
			
			BiggestWeight = BiggerNum(BiggerNum(BiggerNum(weight_direction[GoUp], weight_direction[GoDown]),
					BiggerNum(weight_direction[GoRight], weight_direction[GoLeft])), weight_direction[Stay]);
			
			if (BiggestWeight==weight_direction[GoUp])
				return GoUp;
			else if (BiggestWeight==weight_direction[GoDown])
				return GoDown;
			else if (BiggestWeight==weight_direction[GoLeft])
				return GoLeft;
			else if (BiggestWeight==weight_direction[GoRight])
				return GoRight;
			else
				return Stay;
		}
	
		// �׷��� ������ ��ȭ �⵵ �õ�
		else
			return Stay;
	}
	
	/** 
	 * ��ü ��ź�� �������� ��ȯ �Լ��̴�. �� �ڵ带 �����ߴ�.
	 */
	public Point Soul_Spawn_CorpseBomb()
	{
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		Point favoritePoint = new Point(0, 0);
		
		favoritePoint.row = (int)(gameNumber / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int)(gameNumber % Constants.Classroom_Height);
		
		// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// ���� ���� ĭ�� �߰ߵǸ� ����
				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if ( weight == max_weight )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
					else if ( distance == min_distance )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row, max_column) )
							{
								max_row = row;
								max_column = column;
								break;
							}
						} 
						
						//������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}
		
		if (max_weight < 6)
		{
			if (playstyle == Style_CorpseJackpot)
			{
				played_CorpseJackpot = false;
				playstyle = lastPlaystyle;
				if (playstyle == Style_Hunter_Killer)
					return new Point(max_row, max_column);
				else
					return Soul_Spawn_Survivor();
			}
			else
			{
				return new Point(max_row, max_column);
			}
		}
		else	
			return new Point(max_row, max_column);
	}
	
	/** 
	 * ��� �����ڴ� ������ ��ȥ ��ȯ �Լ��� �����Ѵ�.
	 */
	public Point Soul_Spawn_Survivor()
	{
		int tempWeight;
		int maxWeight=-Integer.MAX_VALUE;
		Point spawnPoint = new Point(6, 6);
		//�ֺ� 5*5ĭ�� ������ �е��� ���� ������ �е��� ���� ���� ����. Vmap�� Ư���� �ڿ������� �� ��ó�� ���ܵȴ�.
		for (int row = WALL; row < Constants.Classroom_Height+WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width+WALL; ++col)
			{
				if (CurrentVMap.Map[row][col].weight_Infected==0)
				{
					tempWeight=0;
					tempWeight-=(CurrentVMap.Map[row+1][col].weight_Infected
								+CurrentVMap.Map[row-1][col].weight_Infected
								+CurrentVMap.Map[row][col+1].weight_Infected
								+CurrentVMap.Map[row][col-1].weight_Infected
								
								+CurrentVMap.Map[row+2][col].weight_Infected*2
								+CurrentVMap.Map[row-2][col].weight_Infected*2
								+CurrentVMap.Map[row][col+2].weight_Infected*2
								+CurrentVMap.Map[row][col-2].weight_Infected*2
								
								+ CurrentVMap.Map[row + 1][col + 1].weight_Infected*2
								+ CurrentVMap.Map[row + 1][col - 1].weight_Infected*2
								+ CurrentVMap.Map[row - 1][col + 1].weight_Infected*2
								+ CurrentVMap.Map[row - 1][col - 1].weight_Infected*2)*3;
					tempWeight+=(CurrentVMap.Map[row][col].weight_Survivor
								+CurrentVMap.Map[row+1][col].weight_Survivor
								+CurrentVMap.Map[row-1][col].weight_Survivor
								+CurrentVMap.Map[row][col+1].weight_Survivor
								+CurrentVMap.Map[row][col-1].weight_Survivor
								
								+CurrentVMap.Map[row+2][col].weight_Survivor
								+CurrentVMap.Map[row-2][col].weight_Survivor
								+CurrentVMap.Map[row][col+2].weight_Survivor
								+CurrentVMap.Map[row][col-2].weight_Survivor
								
								+ CurrentVMap.Map[row + 1][col + 1].weight_Survivor
								+ CurrentVMap.Map[row + 1][col - 1].weight_Survivor
								+ CurrentVMap.Map[row - 1][col + 1].weight_Survivor
								+ CurrentVMap.Map[row - 1][col - 1].weight_Survivor);
					
					if (tempWeight>maxWeight)
					{
						maxWeight = tempWeight;
						spawnPoint.row = row-WALL;
						spawnPoint.column = col-WALL;
					}
				}
			}
		}
		return spawnPoint;
	}
	
	/**
	 * �� �Լ��� �÷��̽�Ÿ�� ���� �Լ��̴�.
	 * �پ��� ��Ȳ ��ȭ�� ���� �÷��̽�Ÿ���� ������ ���̴�
	 */
	void UpdatePlayStyle()
	{
		//�����ڿ��� �߿��� ������ 17�� ������ ��ü�� �Ǿ������ ��� �׳� ��ü ��ź���� ����
		if (turnInfo.turnNumber < 17 && (playstyle == Style_I_am_Legend))
		{
			if(myInfo.state==StateCode.Corpse)
				playstyle = Style_CorpseBomb;
		}

		//�����ڿ��� �߿��� ������ 40�� ������ ��ȥ�� �Ǿ������ ��� �׳� ��ü ��ź���� ����
		if (turnInfo.turnNumber < 40 && (playstyle == Style_Hunter_Killer))
		{
			if(myInfo.state==StateCode.Soul)
				playstyle = Style_CorpseBomb;
		}
		
		// �����ڷ� �����Ͽ� 30���� ���� ��
		if (turnInfo.turnNumber > 30 && playstyle == Style_I_am_Legend && stylechanged == false)
		{
			//������ ������ �ʹ� ���ٸ�
			if(myScore.survivor_total < 30)
			{
				//�׳� �����ڷ� �����Ѵ�
				playstyle = Style_Hunter_Killer;
				stylechanged = true;
			}
		}
		// �����ڷ� �����Ͽ� 30���� ���� ��
		if (turnInfo.turnNumber > 30 && playstyle == Style_Hunter_Killer && stylechanged == false)
		{
			//������ ������ �ʹ� ���ٸ�
			if(myScore.infected_total < 30)
			{
				//�׳� �����ڷ� �����Ѵ�
				playstyle = Style_I_am_Legend;
				stylechanged = true;
			}
		}
		//100�� ���Ŀ� ���� �ڽ��� ��ü ������ �ʹ� ���� ���
		if(turnInfo.turnNumber>90 && myScore.corpse_max<10 
				&& played_CorpseJackpot==false && (playstyle != Style_CorpseBomb))
		{
			lastPlaystyle = playstyle;
			playstyle = Style_CorpseJackpot;
			played_CorpseJackpot = true;
		}

		
	}
	@Override
	public DirectionCode Survivor_Move()
	{
		int density_survivor=0;
		int density_infected = 0;

		UpdatePlayStyle();

		if (turnInfo.turnNumber == 1)
		{
			UpdateVirtualMap_First();
		}
		else
		{
			UpdateVirtualMap();
		}
		DirectionCode result = DirectionCode.Right;

		if (playstyle == Style_I_am_Legend)
		{
			for (int row = myInfo.position.row + WALL - 2; row <= myInfo.position.row + WALL + 2; ++row)
			{
				for (int col = myInfo.position.column + WALL - 2; col <= myInfo.position.column + WALL + 2; ++col)
				{
					density_survivor += CurrentVMap.Map[row][col].weight_Survivor;
					density_infected += CurrentVMap.Map[row][col].weight_Infected;
				}
			}

			if (density_survivor > density_infected * 10)
				state = UpdateState_Survivor_Scout();
			else
				state = UpdateState_Survivor_Loner();
		}
		else // (playstyle == Style_I_am_Leegend || playstyle == Style_CorpseJackpot)
			state = UpdateState_Survivor_Hunter();

		switch (state)
		{
		case GoRight:
			result = DirectionCode.Right;
			break;
		case GoDown:
			result = DirectionCode.Down;
			break;
		case GoLeft:
			result = DirectionCode.Left;
			break;
		case GoUp:
			result = DirectionCode.Up;
			break;
		default:
			break;
		}
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		UpdateVirtualMap();
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		UpdateVirtualMap();
		UpdatePlayStyle();
		DirectionCode result = DirectionCode.Right;
		
		if (playstyle == Style_CorpseBomb)
			state = UpdateState_Infected_CorpseBomb();
		else if (playstyle == Style_Hunter_Killer)
			state = UpdateState_Infected_Hunter();
		else if (playstyle == Style_CorpseJackpot)
			state = UpdateState_Infected_CorpseBomb();
		else
		{
			state = UpdateState_Infected_CorpseBomb();
		}

		switch (state)
		{
		case GoRight:
			result = DirectionCode.Right;
			break;
		case GoDown:
			result = DirectionCode.Down;
			break;
		case GoLeft:
			result = DirectionCode.Left;
			break;
		case GoUp:
			result = DirectionCode.Up;
			break;
		case Stay:
			result = DirectionCode.Stay;
			//��ü ���� �� ���� �ʾҴٸ�
			if(CurrentVMap.Map[myInfo.position.row+WALL][myInfo.position.column+WALL].weight_Corpse<MAX_WEIGHT)
				prayCount++;
			break;
		default:
			break;
		}
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return result;
	}

	@Override
	public void Soul_Stay()
	{
		//��ȭ �⵵ ���� ���� �ʱ�ȭ
		purification=6;
		prayCount=1;
		UpdateVirtualMap();
		UpdatePlayStyle();
		if (turnInfo.turnNumber == 0)
		{
			played_CorpseJackpot=false;
			playstyle = Style_Hunter_Killer;
			stylechanged=false;
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		Point point = new Point(7, 7);
		if (turnInfo.turnNumber == 0)
		{
			point.row = 7;
			point.column = 6;
		}
		else
		{
			if(playstyle == Style_CorpseBomb)
			{
				point = Soul_Spawn_CorpseBomb();
			}
			else if(playstyle == Style_I_am_Legend)
			{
				point = Soul_Spawn_Survivor();
			}
			else //if(playstyle == Style_Hunter_Killer)
			{
				point = Soul_Spawn_CorpseBomb();
			}
		}
		state = GoRight;
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return new Point(point.row, point.column);
	}
}
