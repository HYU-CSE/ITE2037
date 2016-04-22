package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */

/**
 * 플레이어의 추론에서 양자 단위로 존재할 수 있는 가상 플레이어입니다.<br>
 * <br>
 * <b>Note:</b> 이 클래스는 시야 밖에서 확률적으로 존재하는 플레이어들에 대한 예측 정보를 담습니다.
 * 해당 플레이어의 존재확률은 <code>int weight_exist<code>에 저장됩니다.
 * 
 * @author LJW
 */
class QuantumPlayer
{
	/**쓸모가 ㅇ벗음 */
	int ID; 
	/**체력 */
	int HP;
	/**상태 */
	StateCode state;
	/**상태변화 쿨다운. 0이 되면 상태가 변화 */
	int transition_cooldown;
	/**연속 기도회수 */
	int prayCount;
	/**존재확률 */
	int weight_exist;
	/**이동확률 */
	int[] weight_direction = new int[5];
}

/**
 * 플레이어의 추론에서의 가상의 구획입니다. 구획들은 모여서 사고 속의 가상의 강의실을 이룹니다.<br>
 * <br>
 * <b>Note:</b> 가상의 구획은 항상 <code>Total_Players<code>명의 양자 플레이어(<code>QuantumPlayer<code>)를 갖습니다. 양자
 * 플레이어들의 수치에 따라서 해당 구획에 대한 유용한 정보(weight)들이 매겨질 수 있습니다.
 * 
 * @author LJW
 *
 */
class VirtualCell
{
	QuantumPlayer[] q_Player = new QuantumPlayer[Constants.Total_Players];
	/** 시야 범위라면 true, 시야 밖이면 false */
	boolean CellIsSearched;
	/** 벽이면 true, 강의실이면 false */
	boolean CellIsWall;
	/**해당 구획에 감염자가 존재할 확률. 해당 구획 실존/양자 감염자들의 무게의 곱연산으로 구해진다. (0~MAX_WEIGHT) */
	int weight_Infected;
	/**해당 구획의 생존자 무게. 해당 구획 실존/양자 생존자들의 무게의 합연산으로 구해진다. (0~무한대) */
	int weight_Survivor;
	/**해당 구획의 시체 무게. 해당 구획 실존/양자 시체들의 무게의 합연산으로 구해진다. (0~무한대) */
	int weight_Corpse;
	
	//자바, 자바는 정말이지 멋져요, 정말로, 정말로 멋지죠.
	public VirtualCell()
	{
		//Ang?
		for (int i = 0; i < Constants.Total_Players; ++i)
			q_Player[i] = new QuantumPlayer();
	}
}

/**
 * 플레이어의 사고 속에서의 가상의 강의실입니다.<br>
 * <br>
 * 
 * <b>Note:</b> 가상의 강의실은 가상 구획들이 모여서 만들어져 있습니다. 또한 플레이어들이 양자 상태로 존재하는지 혹은
 * 관측되었는지에 대한 boolean값 역시 이 클래스에 저장됩니다.
 * 가상의 강의실에는 2칸 너비의 벽(<code>WALL<code>)이 상하좌우에 존재하며
 * 이 벽의 weight_infected는 MAX, weight_Survivor와 weight_Corpse는 0이기 때문에
 * 어떤 양자 플레이어도 이 벽으로의 이동 방향을 선호하지 않습니다.
 * 
 * @author LJW
 *
 */
class VirtualMap
{
	VirtualCell[][] Map = new VirtualCell[Constants.Classroom_Height + 4][Constants.Classroom_Width + 4];
	boolean[] PlayerIsFound = new boolean[Constants.Total_Players];
	
	//자바, 자바는 정말이지 멋져요, 정말로, 정말로 멋지죠.
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
	/** 현재 턴에 대해 추론한 가상 맵이다 */
	VirtualMap CurrentVMap = new VirtualMap();

	/** 이전 턴에 대해 추론한 가상 맵이다 */
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
	int lastPlaystyle;	//시체 잭팟 플레이시 이전 플레이스타일을 기록하는 변수
	int prayCount;
	int purification;
	int[] weight_direction = new int[5];
	boolean stylechanged;

	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Player_Hunter_Killer(int ID)
	{

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "Hunter Killer");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		
			this.trigger_acceptDirectInfection = true;
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고
		// 돌아옵시다.
	}

	/*
	 * TODO#5 이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요. 당연히 한 방에 될 리
	 * 없으니, 중간중간 코드를 백업해 두는 것이 좋으며, 코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다. 그러니
	 * 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면, '코드에 노력이란게 묻어 날
	 * 수도 있구나'라는 생각이 절로 들게 될 것입니다.
	 */
	
	/**
	 * 더 큰 수를 반환하는 함수이다
	 */
	int BiggerNum(int a, int b)
	{
		if (a > b)
			return a;
		else
			return b;
	}
	
	/**
	 * 2개의 가상 플레이어 중 존재확률이 가장 높은 가상 플레이어를 반환하는 함수이다.
	 */
	QuantumPlayer HeavyPlayer(QuantumPlayer a, QuantumPlayer b)
	{
		if (a.weight_exist > b.weight_exist)
			return a;
		else
			return b;
	}
	
	/**
	 * 좌표와 가상 맵을 전달받아 해당 좌표 근방의 생존자 시야에 대한 가상 맵 셀의 탐색 여부를 true로 만드는 함수이다.
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
	 * 가상 맵의 요소들을 벽을 제외하고 전부 초기화시키는 함수이다.
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
	 * 플레이어가 관측 가능한 정보를 가상 맵에 덧씌우는 함수이다.
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
				// cells가 단순히 비어있는 것인지, 플레이어가 관측 불가능한 상태에 있는 것인지 알 수 있는 방법이 없음!
				// 도와줘요! 조교에몽!
				// 조교님 : 없어요
				// 나 : ...
				// 2시간 후
				// 나 : 이나라 좆까네!
				if (cells[row - WALL][col - WALL].Select_Players(player->(true)).isEmpty())
				{
					CurrentMap.Map[row][col].CellIsSearched = false;
				}
				else // 관측되는 정보를 가상 맵에 쓴다.
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
	 * 관측(seek)이 완료된 가상 맵을 바탕으로 하여, 이전 턴의 가상 맵에서 계산된 weight_direction을 기반으로 감염자에
	 * 대한 추론 위치를 덧씌우는 함수이다. <br>
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
						/* 관측 불가능한 플레이어들에 대해서는 
						 * 이전 가상 맵의 양자 플레이어 이동 weight를 기반으로 추측을 시도한다.
						 * 이 때 동일한 ID의 양자 플레이어가 각기 다른 정보를 가지고 한 장소에 있게 되는 경우가 있는데
						 * weight와 weight_direction을 제외한 모든 정보는
						 * 이전 가상 맵에서 가장 존재확률이 높았던 플레이어의 것을 물려받는다.  */
						
						CurrentMap.Map[row][col].q_Player[i] = 
						HeavyPlayer(
								HeavyPlayer(HeavyPlayer(LastMap.Map[row+1][col].q_Player[i], 
													LastMap.Map[row-1][col].q_Player[i]),
											HeavyPlayer(LastMap.Map[row][col+1].q_Player[i], 
												 	LastMap.Map[row][col-1].q_Player[i])),
								LastMap.Map[row][col].q_Player[i]);
						
						/* 만일 이전 가상 맵에서 가장 존재확률이 높았던 플레이어가 Stay를 선택한 플레이어이며 
						 * 해당 플레이어 감염체일 경우, 이는 정화 기도를 올린 것으로 간주된다.
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
						
						//weight_direction 초기화
						for (int j = 0; j < 5; ++j)
						{
							CurrentMap.Map[row][col].q_Player[i].weight_direction[j]=0;
						}	
						
						// 이미 관측된 플레이어일 경우 불확정 양자 플레이어를 삭제한다.
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
	 * 관측(seek)과 위치 추론(GuessMoves)이 완료된 가상 맵을 바탕으로 하여, 각 셀의 추론 weight를 구하는 함수이다.
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
						// 감염자의 경우 weight의 곱을 구한다
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Infected)
						{
							CurrentMap.Map[row][col].weight_Infected = MAX_WEIGHT
									- (MAX_WEIGHT - CurrentMap.Map[row][col].q_Player[i].weight_exist)
											* (MAX_WEIGHT - CurrentMap.Map[row][col].weight_Infected) / MAX_WEIGHT;
						}
						// 생존자의 경우 weight의 합을 구한다.
						else if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Survivor)
						{
							CurrentMap.Map[row][col].weight_Survivor += CurrentMap.Map[row][col].q_Player[i].weight_exist;
						}
						// 시체의 경우 weight의 합을 구하면서 동시에 시체의 transition_cooldown에
						// 따라 시체 파편 역시 weight의 합에 포함한다.
						else if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Corpse)
						{
							CurrentMap.Map[row][col].weight_Corpse += CurrentMap.Map[row][col].q_Player[i].weight_exist;
							// 싱싱한 시체는 주변 12칸에 전부 파편을 퍼뜨린다. 뫄이쪙
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
							// 상한 시체는 주변 4칸에 파편을 퍼뜨린다.
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
							// 감염체가 되기 전의 썩은 시체는 파편을 퍼뜨리지 않는다. 또한 이 때의 시체는 사실상 감염체와 같은 위험도 취급이다.
							else if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown == 0)
							{
								CurrentMap.Map[row][col].weight_Infected += CurrentMap.Map[row][col].q_Player[i].weight_exist;
							}
						}
					}
				}
			}
		}
		//방금 전 시체가 벽에다 흩뿌린 파편들을 전부 치운다. 어서 일해라!
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
	 * 관측(seek)과 위치 추론(GuessMoves), 무게 추론(GuessCellWeights)이 모두 완료된 가상 맵을 바탕으로
	 * 하여 양자 플레이어의 상태 및 weight_direction을 계산하는 함수이다. <br>
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
						// 생존자의 상태 변화
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Survivor)
						{
							// 감염 확률이 MAX_WEIGHT 이상인 지역에 있을 경우 시체로 상태가 변화된다.
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
							 * 2X2 시야 범위의 weight_Infected를 바탕으로
							 * 하여 weight_direction을 결정한다. 1. weight_Infected에 의한
							 * weight_direction의 반영 비율 우선 생존자의 모든
							 * weight_direction의 값은 각각 MAX_WEIGHT로 정의한다. 2 2 1 2
							 * 2 1 S 1 2 2 1 2 2 - 2칸 직선 떨어진 곳에 infection이 존재할
							 * 경우 해당 방향의 weight_direction에서 weight_infected를 뺀다.
							 * - 2칸 대각선 떨어진 곳에 infection이 존재할 경우 해당 방향의
							 * weight_direction에서 weight_infected/2를 뺀다. - 1칸
							 * 떨어진 곳에 infection이 존재할 경우 해당 방향의
							 * weight_direction에서 weight_infected/5를 뺀다. - 모든
							 * 방향의 weight_direction을 더한 값이 0일 경우 플레이어의 state는
							 * Corpse가 된다. - 모든 방향의 weight_direction을 더한 값이 0보다
							 * 클 경우 이 값을 바탕으로 다시 weight_direction의 총합이
							 * MAX_WEIGHT가 되도록 비율을 맞춘다.
							 * 
							 * 주석이 고자라니! 이클립스 좆까네! 이클립스 좆! 까는! 이클립스 좆까는! 이클립스
							 * 좆까네...
							 */
							else
							{
								// GoUp 계산
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row - 1][col].weight_Infected / 5)
										- (CurrentMap.Map[row - 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 2][col].weight_Infected);
								
								// GoDown 계산
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row + 1][col].weight_Infected / 5)
										- (CurrentMap.Map[row + 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row + 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row + 2][col].weight_Infected);
								
								// GoLeft 계산
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row][col - 1].weight_Infected / 5)
										- (CurrentMap.Map[row + 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 1][col - 1].weight_Infected / 2)
										- (CurrentMap.Map[row][col - 2].weight_Infected);
								
								// GoRight 계산
								CurrentMap.Map[row][col].q_Player[i].weight_direction[GoUp] = MAX_WEIGHT
										- (CurrentMap.Map[row][col + 1].weight_Infected / 5)
										- (CurrentMap.Map[row + 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row - 1][col + 1].weight_Infected / 2)
										- (CurrentMap.Map[row][col + 2].weight_Infected);
								
								int AddedRatio = 0;
								// 0 이하의 값의 direction을 0으로 치환하고 모든 direction의 합산
								// 값을 구함
								for (int j = 0; j < 4; ++j)
								{
									if (CurrentMap.Map[row][col].q_Player[i].weight_direction[j] < 0)
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = 0;
									AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[j];
								}
								// 합산 값이 0, 즉 갈 수 있는 곳이 없을 경우 마지막 발악으로 벽을 제외한 곳으로 이동한다.
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
								// 합산 값이 0이 아닐 경우, 비율에 맞추어 합산 값이 MAX_WEIGHT가 되도록
								// 각 weight_direction을 조정한다.
								else
								{
									for (int j = 0; j < 4; ++j)
									{
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = CurrentMap.Map[row][col].q_Player[i].weight_direction[j]
												* MAX_WEIGHT / AddedRatio;
									}
								}
								// I want some 뚝배기!
							}
						}
						// 시체의 상태 변화
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Corpse)
						{
							// 턴이 지날때마다 변이가 진행되어 3턴 안에 감염자가 된다.
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
						// 감염자의 상태 변화
						if (CurrentMap.Map[row][col].q_Player[i].state == StateCode.Infected)
						{
							// 체력 증감
							CurrentMap.Map[row][col].q_Player[i].HP += Constants.Corpse_Heal_Power
									* CurrentMap.Map[row][col].weight_Corpse;
							// 시체 위에 있을 가능성이 조금이라도 있다면 praycount는 0이 된다.
							if (CurrentMap.Map[row][col].weight_Corpse > 0)
							{
								CurrentMap.Map[row][col].q_Player[i].prayCount=0;
							}
							// 시체 위에 있지 않다는 것이 확실할 경우 prayCount만큼 transition_cooldown이 감소한다.
							else
							{
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown-=
										CurrentMap.Map[row][col].q_Player[i].prayCount;
							}
							// 해당 칸의 시체 무게가 MAX 미만일 경우 HP는 시체 무게에 반비례하여 감소한다.
							if (CurrentMap.Map[row][col].weight_Corpse < MAX_WEIGHT)
								CurrentMap.Map[row][col].q_Player[i].HP -= 1
										* (MAX_WEIGHT - CurrentMap.Map[row][col].weight_Corpse);
							/*
							 * 체력이 0 이하일 경우 영혼으로 변경 양자 영혼의 의사 결정까지 고려할 경우 이는
							 * 불확정성이 너무 높으므로 계산하지 않는다. 향후 유동성을 위하여 우선 영혼의
							 * trasition_cooldown은 고려한다. 허나 어짜피
							 * GuessQPlayerMoves함수에서 영혼에 대해 계산하지 않으므로 영혼 양자
							 * 플레이어는 턴이 지나자마자 즉각적으로 소거된다.
							 */
							if (CurrentMap.Map[row][col].q_Player[i].HP <= 0)
							{
								CurrentMap.Map[row][col].q_Player[i].state = StateCode.Soul;
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown = 3;
							}
							//기도를 성공적으로 마친 경우에도 영혼으로 변경.
							if (CurrentMap.Map[row][col].q_Player[i].transition_cooldown <= 0)
							{
								CurrentMap.Map[row][col].q_Player[i].state = StateCode.Soul;
								CurrentMap.Map[row][col].q_Player[i].transition_cooldown = 2;
							}
							/*
							 * 양자 감염자의 AI는 단순화 버전을 이용한다.
							 * 양자 감염자는 주변 5X5칸에 대해서만 감지하며, 시체에 대한 전방위 감지 기능은 삭제한다
							 * (어짜피 실제 상황에서도 시체에 대한 전방위 감지 기능은 거의 쓸모가 없다)
							 * 생존자에 대한 선호도는 항상 동일하며, 체력이 낮을수록 시체에 대한 선호도가 높아진다.
							 * 체력이 1일 때를 기준으로 하여, 감염자는 생존자보다 시체를 4배 선호한다.
							 * 또한 먼 거리의 목표보다 가까운 거리의 목표를 2배 선호한다.
							 * 자기 위치에 있는 시체의 경우 잔존 HP와 관계없이 생존자에 비해 8배 선호한다.
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
								//제자리를 제외한 direction의 합산값을 구함
								for (int j = 0; j < 5; ++j)
								{
									AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[j];
								}
								// 그럴 가능성은 거의 없지만, 합산 값이 0, 즉 갈 수 있는 곳이 없을 경우 제자리에 있는다.
								if (AddedRatio == 0)
								{
									CurrentMap.Map[row][col].q_Player[i].weight_direction[Stay] = MAX_WEIGHT;
								}
								// 합산 값이 0이 아닐 경우, 비율에 맞추어 합산 값이 MAX_WEIGHT가 되도록
								// 각 weight_direction을 조정한다.
								else
								{
									AddedRatio += CurrentMap.Map[row][col].q_Player[i].weight_direction[Stay];
									for (int j = 0; j < 5; ++j)
									{
										CurrentMap.Map[row][col].q_Player[i].weight_direction[j] = CurrentMap.Map[row][col].q_Player[i].weight_direction[j]
												* MAX_WEIGHT / AddedRatio;
									}
								}
								//헣헣헣허↗만든라면!
							}
						}
					}
				}
			}
		}

	}

	/**
	 *  CurrentVMap을 LastVMap으로 만들고, 새로운 CurrentMap을 관측 및 추론하는 함수이다.
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
	 *  게임 시작 시 가상 맵을 초기화시키고 양자 플레이어를 최초 배치하는 함수이다.
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
		//미확인 플레이어는 임의의 미확인 구역에 전체 미확인 구역의 넓이에 반비례하는 확률로 존재한다.
		for (int row = WALL; row < Constants.Classroom_Height+WALL; ++row)
		{
			for (int col = WALL; col < Constants.Classroom_Width+WALL; ++col)
			{
				for (int i = 0; i < col; ++i)
				{
/*아도겐! */			if (!CurrentVMap.PlayerIsFound[i])
  /*아도겐! */ 		{
	/*아도겐! */   		CurrentVMap.Map[row][col].q_Player[i].weight_exist=MAX_WEIGHT/UnSearchedCells;	
  /*아도겐! */		}
/*아도겐! */		}
			}
		}
		
		GuessCellWeights(CurrentVMap);
		GuessQPlayerStates(CurrentVMap);	
	}
	
	/**
	 * 스카웃형 생존자는 어느정도 위험을 감수하기 좋아한다.
	 * 살아남는 것 역시 중요하지만, 어느정도 감염체에 접근하거나 다수의 생존자와 뭉쳐다니며 정보를 공유하는 등
	 * 약간의 위험을 감수하여 보다 더 고득점의 플레이를 노리고자 하는 것이 스카웃형 생존자 플레이의 핵심이다.
	 * 스카웃형 생존자는 자기 주변 생존자의 비율이 감염체에 비해 높을 때에 유리하다.
	 */
	int UpdateState_Survivor_Scout()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//방향 무게 초기화
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 12턴이 지나기 전까지는 누가 감염체가 될 수 있을지 알 수 없으므로 누구든지 2칸 옆으로 다가오는 것을 직접 감염 확률에 비례하여 싫어한다.
		 * 하지만 1칸 옆에 사람이 있는 경우는 감염될 확률이 없으므로 매우 좋아한다. 
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
		 * 12칸이 지난 후에는 시야 내에 감염체가 있는 곳은 극도로 싫어하며, 강의실에서 생존자 밀도가 높은 곳으로 이동하고자 한다.
		 * 생존자의 경우 이동에 제약이 없기 때문에 당장 가까운 곳의 적은 플레이어에게 다가가는 것보다는
		 * 조금 더 멀더라도 최대한 생존자 밀도가 높은 지역으로 이동하는 것이 관측 점수를 높일 수 있다.
		 */
		else
		{	
			//위쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//아래쪽 생존자 밀도 계산
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//왼쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//오른쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/* 상하좌우에서 감염자에 대한 경계
			 * 2칸 떨어진 감염자를 1칸 떨어진 감염자보다 5배 경계한다.
			 * 1칸 떨어진 감염자를 지나치게 신뢰하는 듯 보이지만, 어느정도 정화기도에 대한 불안감을 덜어내지 않으면
			 * 관측 점수를 딸 수 없다.
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
		 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
	 * 외톨이형 생존자는 어떠한 위험도 감수하지 않는다.
	 * 외톨이형 생존자의 행동양식은 단 하나, 살아남는 것이다. 때문에 모든 능력을 회피에 치중한다.
	 * 외톨이형 생존자는 자기 주변 감염자의 비율이 생존자의 비율보다는 높으나, 분명히 생존 가능성이 존재할 때에 유리하다.
	 */
	int UpdateState_Survivor_Loner()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//방향 무게 초기화
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 12턴이 지나기 전까지는 누가 감염체가 될 수 있을지 알 수 없다.
		 * 때문에 외톨이형 생존자는 같은 생존자조차도 불신한다.
		 * 외톨이형 생존자는 1칸 떨어진 생존자보다 2칸 떨어진 생존자를 2배 이상 불신한다.
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
		 * 12칸이 지난 후에는 시야 내에 감염체가 있는 곳은 극도로 싫어하며, 강의실에서 감염자 밀도가 낮은 곳으로 이동하고자 한다.
		 */
		else
		{	
			//위쪽 감염자 밀도 계산
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//아래쪽 감염자 밀도 계산
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//왼쪽 감염자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//오른쪽 감염자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/* 상하좌우에서 감염자에 대한 경계
			 * 1칸 떨어진 감염자는 상대적으로 덜 경계한다.
			 * 정화 기도자가 아닌 이상 뚫고 지나갈 가능성이 희박하게나마 존재하기 때문
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
		 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
	 * 사용될 가능성은 거의 없긴 하지만, Hunter Killer 플레이스타일 중 생존자가 되었을 때의 행동양식이다.
	 */
	int UpdateState_Survivor_Hunter()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//방향 무게 초기화
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 외톨이형 생존자의 반대로 행동한다.
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
		 * 외톨이형 생존자의 반대로 행동한다.
		 */
		else
		{	
			//위쪽 감염자 밀도 계산
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//아래쪽 감염자 밀도 계산
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//왼쪽 감염자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//오른쪽 감염자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Infected;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/* 생존자형 외톨이의 반대로 행동
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
		 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
	 * 사냥꾼형 감염자는 기회주의적이다.
	 * 이들은 항상 시체를 통해 얻을 수 있는 이득과 생존자를 쫓아 얻을 수 있는 이득을 저울질한다.
	 * 만일 감염체 비율이 생존자에 비해 지나치게 높다 생각되면 즉각적으로 자살을 시도한다.(라고 했지만 구현은 다른곳에서 됨)
	 */
	int UpdateState_Infected_Hunter()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		ArrayList<PlayerInfo> corpses = new ArrayList<>();
		/** 시체 선호도 */
		int preferCorpse;

		// 모든 칸을 조사하여 강의실에 있는 모든 시체들에 대한 목록을 만듦 
		for ( CellInfo[] rows : cells )
			for ( CellInfo cell : rows )
				corpses.addAll(cell.Select_Players(player -> player.state == StateCode.Corpse));

		// 현존 HP에 따라 시체 선호도를 조정
		if (myInfo.HP > 25)
			preferCorpse = 4;
		else if (myInfo.HP > 5)
			preferCorpse = 100/myInfo.HP;
		else
			preferCorpse = 20;

		//방향 무게 초기화
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		/* 12턴이 지나기 전은 사냥꾼형 감염자가 가장 유의해야 할 기점이다.
		 * 12턴이 막 지났을 때가 다름아닌 감염자에게 있어서 가장 많은 득점을 올릴 수 있는 분수령이기 때문
		 * 때문에 9턴이 지나기 전까지 감염자는 최대한 생존자 밀도가 높은 지역으로 이동한다.
		 */
		if(turnInfo.turnNumber<10)
		{
			//위쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//아래쪽 생존자 밀도 계산
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//왼쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//오른쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Survivor;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/*
			 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
		 * 9턴까지 생존자 밀도가 높은 지역으로 이동하였다면
		 * 10, 11턴은 그야말로 본능에 몸을 맡기고 미쳐 날뛸 시간이다.
		 * 주변에 있는 것은 전부 먹이감이다. 최대한 먹이감 냄새가 많이 나는 곳으로 이동하기만 하면 된다.
		 */
		else if (10<=turnInfo.turnNumber && turnInfo.turnNumber<12)
		{
			/* 상하좌우에서 감염자에 대한 경계
			 * 2칸 떨어진 감염자를 1칸 떨어진 감염자보다 2배 선호한다. (1칸 떨어진 감염자는 의외로 처치하기 힘들다)
			 * 여기까지 오면 이미 밀도고 뭐고 상관없다. 계획대로라면 당신은 이미 생존자들의 한복판이다.
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
			 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
		 * 12턴 이후부터는 계산적인 플레이를 해야 한다.
		 * 시체라는 확실하지만 작은 이득과 생존자 사냥이라는 불확실하지만 큰 이득 사이에서
		 * 어느정도 사고를 할 수 있어야 하기 때문이다.
		 */
		else
		{		
			/* 상하좌우에서 생존자 위치 파악
			 * 2칸 떨어진 생존자가 있을 경우 해당 방향으로의 선호도 증가
			 * 생존자에 대한 선호도는 항상 동일.
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
			
			/* 1칸 떨어진 생존자의 경우 제자리 유지 선호도 증가.
			 * 이 때, 1칸 떨어진 생존자의 주변에 다른 위험요소가 있다면 제자리 유지 선호도는 폭등.
			 * 단, 정화 기도를 올려서 승화해버리는 것은 방지.
			 * 1칸 떨어진 생존자의 사방이 전부 위험요소라는 가정하에, 감염자는 1칸 떨어진 생존자를 2칸 떨어진 생존자보다 2배 선호한다.
			 * 아래 코드에 weight_Infected가 있는건 절대 오류가 아니다. 졸다가 수정하면 큰일남.
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
			 * 제자리에 있는 시체의 경우 거리가 있는 시체에 비해 3배 선호한다. 즉, 제자리에 있는 시체는 거리가 있는 생존자에 비해 최대 15배 선호한다.
			 * 시체 선호도는 좀 더 특수한 과정을 거치는데, 이는 해당 시체에 도달할때까지 시체가 먹을 수 있는 상태인가를 고려해야 하기 때문이다.
			 */
			for ( PlayerInfo corpse : corpses )
			{
				//먹을 수 있는 시체라면 (즉 시체의 변이 잔존시간이 시체와의 거리보다 클 경우)
				if(corpse.transition_cooldown > corpse.position.GetDistance(myInfo.position))
				{
					//제자리의 시체는 3배 선호
					if(corpse.position == pos_now)
					{
						weight_direction[Stay]
						+=CurrentVMap.Map[corpse.position.row+WALL][corpse.position.column+WALL].weight_Infected*3*preferCorpse
						*corpse.transition_cooldown;
					}
					//시체가 위쪽에 있을 경우
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
			
			/* 생존자 밀도 계산은 가장 우선도가 낮은 경우이다.
			 * 사실상 자기 주변이 허허벌판이거나 감염자로만 가득차지 않은 이상 미치는 영향이 최소화되어야 한다.
			 * 시야 내의 생존자 밀도보다 시야 밖 생존자 밀도가 5배 이상 높을 때에만 시야 밖을 우선시할 수 있을 정도의
			 * WEIGHT가 매겨지는 것이 중요하다.
			 */
			//위쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/(pos_now.row*10);
			}
			
			//아래쪽 생존자 밀도 계산
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/((Constants.Classroom_Height-pos_now.row-1)*10);
			}
			
			//왼쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/(pos_now.column*10);
			}
			
			//오른쪽 생존자 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Survivor/((Constants.Classroom_Width-pos_now.column-1)*10);
			}
		}
		/*
		 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
	 * 시체 폭탄형 감염자는 스스로가 감염자라는 사실을 참을 수 없어한다.
	 * 무조건 정화 기도를 올려서 최대한 빨리 죽으려 한다.
	 */
	int UpdateState_Infected_CorpseBomb()
	{
		int BiggestWeight=0;
		Point_Immutable pos_now = myInfo.position;
		
		//방향 무게 초기화
		for (int i = 0; i < 5; ++i)
		{
			weight_direction[i]=0;
		}
		
		// 내 밑에 시체가 깔려 있으면 시체 밀도가 낮은 곳으로 도망감
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
		{
			//위쪽 시체 밀도 계산
			for (int t_row = WALL; t_row < pos_now.row+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoUp]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.row!=0)
				weight_direction[GoUp] /= (pos_now.row);
			
			//아래쪽 시체 밀도 계산
			for (int t_row = pos_now.row+WALL+1; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoDown]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.row!=Constants.Classroom_Height-1)
				weight_direction[GoDown] /= (Constants.Classroom_Height-pos_now.row-1);
			
			//왼쪽 시체 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = WALL; t_col < pos_now.column+WALL; ++t_col)
					weight_direction[GoLeft]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.column!=0)
				weight_direction[GoLeft] /= (pos_now.column);
			
			//오른쪽 시체 밀도 계산
			for (int t_row = WALL; t_row < Constants.Classroom_Height+WALL; ++t_row)
			{
				for (int t_col = pos_now.column+WALL+1; t_col < Constants.Classroom_Width+WALL; ++t_col)
					weight_direction[GoRight]+=CurrentVMap.Map[t_row][t_col].weight_Corpse;
			}
			if (pos_now.column!=Constants.Classroom_Width-1)
				weight_direction[GoRight] /= (Constants.Classroom_Width-pos_now.column-1);
			
			/*
			 * 어떠한 경우든간에 벽으로는 절대 이동하지 않는다.
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
	
		// 그렇지 않으면 정화 기도 시도
		else
			return Stay;
	}
	
	/** 
	 * 시체 폭탄형 감염자의 소환 함수이다. 봇 코드를 차용했다.
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
		
		// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// 가장 많은 칸이 발견되면 갱신
				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
				else if ( weight == max_weight )
				{
					// 거리가 더 가까우면 갱신
					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// 거리마저 같으면 더 좋아하는 방향을 선택
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
						
						//여기까지 왔다면 이제 그만 놓아 주자
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
	 * 모든 생존자는 동일한 영혼 소환 함수를 공유한다.
	 */
	public Point Soul_Spawn_Survivor()
	{
		int tempWeight;
		int maxWeight=-Integer.MAX_VALUE;
		Point spawnPoint = new Point(6, 6);
		//주변 5*5칸의 생존자 밀도가 높고 감염자 밀도가 낮은 곳을 고른다. Vmap의 특성상 자연스럽게 벽 근처는 제외된다.
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
	 * 이 함수는 플레이스타일 결정 함수이다.
	 * 다양한 상황 변화에 따라 플레이스타일을 변경할 것이다
	 */
	void UpdatePlayStyle()
	{
		//생존자에게 중요한 지점인 17턴 이전에 시체가 되어버렸을 경우 그냥 시체 폭탄으로 전향
		if (turnInfo.turnNumber < 17 && (playstyle == Style_I_am_Legend))
		{
			if(myInfo.state==StateCode.Corpse)
				playstyle = Style_CorpseBomb;
		}

		//감염자에게 중요한 지점인 40턴 이전에 영혼이 되어버렸을 경우 그냥 시체 폭탄으로 전향
		if (turnInfo.turnNumber < 40 && (playstyle == Style_Hunter_Killer))
		{
			if(myInfo.state==StateCode.Soul)
				playstyle = Style_CorpseBomb;
		}
		
		// 생존자로 시작하여 30턴이 지난 후
		if (turnInfo.turnNumber > 30 && playstyle == Style_I_am_Legend && stylechanged == false)
		{
			//생존자 점수가 너무 낮다면
			if(myScore.survivor_total < 30)
			{
				//그냥 감염자로 전향한다
				playstyle = Style_Hunter_Killer;
				stylechanged = true;
			}
		}
		// 감염자로 시작하여 30턴이 지난 후
		if (turnInfo.turnNumber > 30 && playstyle == Style_Hunter_Killer && stylechanged == false)
		{
			//감염자 점수가 너무 낮다면
			if(myScore.infected_total < 30)
			{
				//그냥 생존자로 전향한다
				playstyle = Style_I_am_Legend;
				stylechanged = true;
			}
		}
		//100턴 이후에 만일 자신의 시체 점수가 너무 낮을 경우
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
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		UpdateVirtualMap();
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
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
			//시체 위에 서 있지 않았다면
			if(CurrentVMap.Map[myInfo.position.row+WALL][myInfo.position.column+WALL].weight_Corpse<MAX_WEIGHT)
				prayCount++;
			break;
		default:
			break;
		}
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		return result;
	}

	@Override
	public void Soul_Stay()
	{
		//정화 기도 관련 변수 초기화
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
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
			 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
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
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		return new Point(point.row, point.column);
	}
}
