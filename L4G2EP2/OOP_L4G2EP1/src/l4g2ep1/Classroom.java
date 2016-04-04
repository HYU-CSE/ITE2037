package l4g2ep1;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import l4g2ep1.common.*;
import l4g2ep1.sampleplayers.*;

/**
 * �� �÷��̾ ���� �� �����ϸ� ������ �����ϴ� ���ǽ��� ��Ÿ���ϴ�.
 * �÷��̾� �ν��Ͻ�, ���� ����, ���� ���࿡ ������ ����, ���� ����� ��� �ֽ��ϴ�.
 * 
 * ����:
 * �� Ŭ������ L4G2EP1���� ���� �����ϰ� �����Ǿ� �����Ƿ�
 * ���빰�� �ƿ� �׳� �� ���� ���� �ǰ��� �̷ӽ��ϴ�.
 * ���ӿ� ���� �⺻ ������ ������ �� ���� ���Ͽ� �ڼ��� ����Ǿ� �ֽ��ϴ�.
 * 
 * @author Racin
 * 
 */
public class Classroom implements Runnable
{
	/**
	 * ���ǽ��� ���� ����(���� ���� ����)�� ��Ÿ���� �������Դϴ�.
	 * 
	 * @author Racin
	 * 
	 */
	public enum State
	{
		Not_Defined,
		Initialized,
		Running,
		Waiting_Decision_Survivor_Move, // Waiting_Decision �迭�� ���� ���� ������ �÷��̾ ���� ���� ����
		Waiting_Decision_Corpse_Stay,
		Waiting_Decision_Infected_Move,
		Waiting_Decision_Soul_Stay,
		Waiting_Decision_Soul_Spawn,
		Completed
	}

	public State state;						// ���ǽ��� ���� ����
	public Classroom_Settings settings;		// ���� ������ ���� ������ �Է¹��� ������
	public GameInfo gameInfo;				// �̹� ���Ӱ� ������ ����
	
	public Player[] players;				// �� �÷��̾� �ν��Ͻ� ���
	public PlayerInfo[] playerInfos;		// �� �÷��̾ ���� ���� ���
	public PlayerStat[] playerStats;		// �� �÷��̾ ���� ���ǽ� ���ο��� ����� �δ� (����) ���� ���

	public Decision[] decisions;			// �� �÷��̾ ������ �ǻ� ���� ��� ���
	public ArrayList<Action> moves;			// �̹� �Ͽ� ����� �̵� �ൿ ���
	public ArrayList<Action> spawns;		// �̹� �Ͽ� ����� ��ġ �ൿ ���
	public ArrayList<Action> valid_actions;	// �̹� �Ͽ� ����� ��ȿ�� �ൿ ���
	public ArrayList<Reaction> reactions;	// �̹� �Ͽ� �߻��� ��� ���

	public Cells cells;						// ���ǽ� ���� ��� ĭ�� ���� ����

	public Scoreboard scoreboard;			// �÷��̾��� ���� ���� �� ���� ���� ����

	public Point accepted_point;			// ���� ���� ������ �÷��̾ ����� �� ���콺 Ŭ���� ���� �ǻ� ������ ����� ��� �ʵ�
	public ActionListener request_decision;	// ���� ���� ������ �÷��̾ ����� �� �ǻ� ������ ��û�ϴ� �̺�Ʈ
	public ActionListener invalid_decision; // ���� ���� ������ �÷��̾ ����� �� �߸��� �ǻ� ������ ���������� �˸��� �̺�Ʈ
	
	public ActionListener turn_ended;		// Presenter�� ����� �� �̹� ���� ����Ǿ����� �˸��� �̺�Ʈ
	public ActionListener game_completed;	// Presenter�� ����� �� ������ ��� ����Ǿ����� �˸��� �̺�Ʈ
	public boolean isSkipping;				// Presenter�� ����� �� �̺�Ʈ �˸� ���� �ٷ� ������ �����ؾ� �ϴ��� ����

	public Classroom(Classroom_Settings settings)
	{
		state = State.Not_Defined;
		this.settings = settings;
		gameInfo = new GameInfo();

		players = new Player[Constants.Total_Players];
		playerInfos = new PlayerInfo[Constants.Total_Players];
		playerStats = new PlayerStat[Constants.Total_Players];

		decisions = new Decision[Constants.Total_Players];
		moves = new ArrayList<Action>();
		spawns = new ArrayList<Action>();
		valid_actions = new ArrayList<Action>();
		reactions = new ArrayList<Reaction>();

		cells = new Cells();

		scoreboard = new Scoreboard();
	}

	/*
	 * ������ �����ϱ� ������ ����Ǵ� �ʱ�ȭ ������ ��� �� �޼���
	 */
	void Initialize()
	{
		int iPlayer = 0;

		/* ���� ��ȣ ���� */
		if ( settings.game_number != -1 )
		{
			gameInfo.gameNumber = settings.game_number;
		}
		else
		{
			java.util.Random rand = new java.util.Random();
			gameInfo.gameNumber = rand.nextInt();
			
			if ( gameInfo.gameNumber < 0 )
				gameInfo.gameNumber += Integer.MAX_VALUE;
		}

		/* �÷��̾� ��� */

		// ���� ������ �÷��̾ ����ϴ� ��� ���� ���� ���
		if ( settings.use_ctrlable_player == true )
		{
			players[iPlayer] = new ControllablePlayer(this);
			++iPlayer;
		}

		// ���� �ۼ��� �÷��̾ ����ϴ� ��� ���̾� ���
		try
		{
			for ( Class<? extends Player> customPlayer_class : settings.custom_player_classes )
			{
				players[iPlayer] = (Player) customPlayer_class.newInstance();
				++iPlayer;
			}
		}
		catch (Exception e)
		{
			System.err.println("���� �ۼ��� �÷��̾� ����� �߸� �Ǿ� �ֽ��ϴ�.");
			System.exit(1);
		}

		// ���� �ڸ��� ������ ���� �÷��̾��� ä��
		if ( iPlayer != Constants.Total_Players )
		{
			/*
			 * ����:
			 * 
			 * ���⼭�� �� ���� �÷��̾ ������ ������ �߰��ϱ� ���� ���� ��Ҹ� ����߽��ϴ�.
			 * ������ �������� �÷��̾�� Random class�� ����� �� �����ϴ�.
			 */
			java.util.Random rand;
			
			if ( settings.seed_for_sample_players == null ||
				 settings.seed_for_sample_players.isEmpty() == true ||
				 settings.seed_for_sample_players.length() == 0 )
				settings.seed_for_sample_players = "14OOP";
			
			rand = new Random(settings.seed_for_sample_players.hashCode());
			
			for ( ; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				switch ( rand.nextInt(6) )
				{
				case 0:
					players[iPlayer] = new SamplePlayer_Loner(iPlayer);
					break;
				case 1:
					players[iPlayer] = new SamplePlayer_Scout(iPlayer);
					break;
				case 2:
					players[iPlayer] = new SamplePlayer_Corpse_Bomb(iPlayer);
					break;
				case 3:
					players[iPlayer] = new SamplePlayer_Paramedic(iPlayer);
					break;
				case 4:
					players[iPlayer] = new SamplePlayer_Seeker(iPlayer);
					break;
				default:
					players[iPlayer] = new SamplePlayer_Predator(iPlayer);
					break;
				}
			}
		}

		// ������ �ʱ�ȭ
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			playerInfos[iPlayer] = new PlayerInfo(iPlayer);
			playerStats[iPlayer] = new PlayerStat();
			decisions[iPlayer] = new Decision();
		}
		
		//ù �Ͽ��� ��� ��ȥ ���·� ��ġ �ǻ� ������ �ϰ� �ǹǷ� �׿� �°� ���� �ʱ�ȭ
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			playerInfos[iPlayer].state = PlayerInfo.State.Soul;
			decisions[iPlayer].type = Decision.TypeCode.Soul_Spawn;
			
			players[iPlayer].gameInfo = gameInfo;
			players[iPlayer].myInfo = playerInfos[iPlayer];
			players[iPlayer].cells = cells;
			players[iPlayer].myScore = scoreboard.scores[iPlayer];

			//�߰� ������ �����ϴ� �÷��̾ ���� �ٸ� ��ȥ ���� �߰�
			if ( players[iPlayer].receiveOthersInfo_detected == true )
				for ( int iOtherPlayer = 0; iOtherPlayer < Constants.Total_Players; ++iOtherPlayer )
					players[iPlayer].othersInfo_detected.add(playerInfos[iOtherPlayer]);
		}

		state = State.Initialized;
	}

	/*
	 * ��ü ������ �����ϴ� �޼���
	 */
	void Start()
	{
		state = State.Running;

		//������ 0�� ~ 120��(����)���� �����
		for ( gameInfo.currentTurnNumber = 0; gameInfo.currentTurnNumber <= Constants.Total_Turns; ++gameInfo.currentTurnNumber )
		{
			//�ܼ� ���� �����Ǿ� �ִ� ��� �� ���� ���۵� �� �� ��ȣ ���
			if ( settings.use_console_mode == true )
				System.out.println("----- Turn " + gameInfo.currentTurnNumber + " -----");

			//�̹� �� ����
			AdvanceTurn();

			//�� ���� �̺�Ʈ �����ڰ� �����Ǿ� �ִ� ��� �̺�Ʈ �߻� �� ���
			if ( turn_ended != null )
			{
				synchronized (this)
				{
					if ( isSkipping == false )
					{
						turn_ended.actionPerformed(null);
						
						try
						{
							this.wait();
						}
						catch (InterruptedException e)
						{
						}
					}
				}
			}
		}

		//������ ������ ���� ����
		scoreboard.CalculateGrades();

		//�ܼ� ���� �����Ǿ� �ִ� ��� ���� ����� �ֿܼ� ���
		if ( settings.use_console_mode == true )
			PrintResultToConsole_AtEndOfTheGame();

		state = State.Completed;

		//���� ���� �̺�Ʈ �����ڰ� �����Ǿ� �ִ� ��� �̺�Ʈ �߻�
		if ( game_completed != null )
			game_completed.actionPerformed(null);
	}

	/*
	 * ���� ���� �� ���� �����ϴ� �޼���
	 */
	void AdvanceTurn()
	{
		/*
		 * �� ���� Ʈ���Ŵ� ��ȿ�� �˻� ���� reset
		 * �� ������ ��ȭ ������ �߻��Ҷ����� Scoreboard�� �ݿ�
		 * �� Max ����� ���°� ���������� ��ȯ�� �� �ʱ�ȭ
		 */

		/*
		 * Phase 1. ������ / ����ü �̵�
		 */

		// �ൿ �� ��� ��� �ʱ�ȭ
		moves.clear();
		spawns.clear();
		valid_actions.clear();
		reactions.clear();

		// �̵� �� ��� �ǻ� ���� ����
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			try
			{
				switch (decisions[iPlayer].type)
				{
				case Survivor_Move:
					decisions[iPlayer].direction = players[iPlayer].Survivor_Move();
					break;
				case Corpse_Stay:
					players[iPlayer].Corpse_Stay();
					break;
				case Infected_Move:
					decisions[iPlayer].direction = players[iPlayer].Infected_Move();
					break;
				case Soul_Stay:
				case Soul_Spawn:
					players[iPlayer].Soul_Stay();
					break;
				default:
					break;
				}
			}
			catch (Exception e)
			{
				// ��Ÿ�� ���ܸ� ����Ų ��� �г�Ƽ �ο�
				playerInfos[iPlayer].state = PlayerInfo.State.Soul;
				playerInfos[iPlayer].position.x = -1;
				playerInfos[iPlayer].position.y = -1;
				playerStats[iPlayer].transition_cooldown = Constants.Soul_Interval_Penalty;

				// ���� �г�Ƽ ������ ���� '���� ��ȥ ���¿��� ��ó��' ����
				decisions[iPlayer].type = Decision.TypeCode.Soul_Stay;
				
				//��ȥ �ı� ���� ����
				scoreboard.Update(Scoreboard.ScoreTypeCode.Soul_Destruction, iPlayer, 1);

				// ��� ���
				reactions.add(new Reaction(iPlayer, Reaction.TypeCode.Arrested, iPlayer, decisions[iPlayer].location_from));

				// ���� ������ ����ϵ��� �����Ǿ� �ִ� ��� ���
				if ( settings.print_errors == true )
				{
					System.err.printf("Error. %s makes runtime exception. - Game #%d, Turn %d\n", players[iPlayer].name, gameInfo.gameNumber, gameInfo.currentTurnNumber);
					e.printStackTrace();
				}
			}
		}

		// �̵� ��� ����
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			Decision decision = decisions[iPlayer];

			if ( decision.type == Decision.TypeCode.Survivor_Move || decision.type == Decision.TypeCode.Infected_Move )
			{
				if ( players[iPlayer].IsValidMove(decision.direction) == true )
				{
					moves.add(new Action(iPlayer, decision, true));
				}
				else
				{
					moves.add(new Action(iPlayer, decision, false));
				}
			}
		}

		// �̵� ��Ͽ� ���� �÷��̾� ��ġ ����
		for ( Action move : moves )
		{
			if ( move.type == Action.TypeCode.Move )
			{
				//��ȿ�� �ൿ ��Ͽ� �߰�
				valid_actions.add(move);
				
				// ����ü�� ���ڸ��� �� �ִ� ���� �����ߴ��� üũ
				if ( playerInfos[move.actorID].state == PlayerInfo.State.Infected && move.location_from.equals(move.location_to) )
				{
					playerStats[move.actorID].infected_prayingAtThisTurn = true;
				}
				// �׷��� �ʴٸ� ��ġ ����
				else
				{
					playerInfos[move.actorID].position.x = move.location_to.x;
					playerInfos[move.actorID].position.y = move.location_to.y;
				}
			}
			else
			{
				// �߸��� �̵�: ��ȥ �г�Ƽ �ο�
				playerInfos[move.actorID].state = PlayerInfo.State.Soul;
				playerInfos[move.actorID].position.x = -1;
				playerInfos[move.actorID].position.y = -1;
				playerStats[move.actorID].transition_cooldown = Constants.Soul_Interval_Penalty;

				//��ȥ ���� ���� ����
				scoreboard.Update(Scoreboard.ScoreTypeCode.Soul_Freedom, move.actorID, 1);
				
				// ��� ���
				reactions.add(new Reaction(move.actorID, Reaction.TypeCode.Punished, move.actorID, move.location_to));

				// ���� ������ ����ϵ��� �����Ǿ� �ִ� ��� ���
				if ( settings.print_errors == true )
				{
					System.err.printf("Error. %s attempts to move from %s to %s. - Game #%d, Turn %d\n", players[move.actorID].name, move.location_from, move.location_to, gameInfo.gameNumber, gameInfo.currentTurnNumber);
				}
			}
		}

		// ���� ������ �����ϴ� ���� ��� �����ڵ��� ���� ���� ���� ���� Ȯ��
		if ( gameInfo.isDirectInfectionChoosingTurn == true )
		{
			gameInfo.isDirectInfectionChoosingTurn = false;

			for ( int i = 0; i < Constants.Total_Players; i++ )
			{
				if ( playerInfos[i].state == PlayerInfo.State.Survivor )
				{
					playerStats[i].survivor_acceptDirectInfection = players[i].acceptDirectInfection;
				}
				else
				{
					playerStats[i].survivor_acceptDirectInfection = false;
				}
			}
		}

		/*
		 * Phase 2. ��ȥ ��Ȱ
		 */

		// ��ȥ���� ���� �þ� ���� ����
		RefreshSightInfo(true);

		// ��ġ �ǻ� ���� ����
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			try
			{
				switch (decisions[iPlayer].type)
				{
				case Soul_Spawn:
					decisions[iPlayer].location_to = new Point(players[iPlayer].Soul_Spawn());
					break;
				default:
					break;
				}
			}
			catch (Exception e)
			{
				// ��Ÿ�� ���ܸ� ����Ų ��� �г�Ƽ �ο�
				playerInfos[iPlayer].state = PlayerInfo.State.Soul;
				playerInfos[iPlayer].position.x = -1;
				playerInfos[iPlayer].position.y = -1;
				playerStats[iPlayer].transition_cooldown = Constants.Soul_Interval_Penalty;

				// ���� �г�Ƽ ������ ���� '���� ��ġ�� ���� �ƴϾ��� ��ó��' ����
				decisions[iPlayer].type = Decision.TypeCode.Soul_Stay;
				
				//��ȥ �ı� ���� ����
				scoreboard.Update(Scoreboard.ScoreTypeCode.Soul_Destruction, iPlayer, 1);

				// ��� ���
				reactions.add(new Reaction(iPlayer, Reaction.TypeCode.Arrested, iPlayer, decisions[iPlayer].location_from));

				//���� ������ ����ϵ��� �����Ǿ� �ִ� ��� ���
				if ( settings.print_errors == true )
				{
					System.err.printf("Error. %s makes runtime exception. - Game #%d, Turn %d\n", players[iPlayer].name, gameInfo.gameNumber, gameInfo.currentTurnNumber);
					e.printStackTrace();
				}
			}
		}

		// ��ġ ��� ����
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			Decision decision = decisions[iPlayer];

			if ( decision.type == Decision.TypeCode.Soul_Spawn )
			{
				if ( decision.location_to.IsValid() == true )
				{
					spawns.add(new Action(iPlayer, decision, true));
				}
				else
				{
					spawns.add(new Action(iPlayer, decision, false));
				}
			}
		}

		// ��ġ ��Ͽ� ���� �÷��̾� ��ġ ����
		for ( Action spawn : spawns )
		{
			if ( spawn.type == Action.TypeCode.Spawn )
			{
				//��ȿ�� �ൿ ��Ͽ� �߰�
				valid_actions.add(spawn);
				
				// ���� ��ȯ: ��ȥ -> ������
				playerInfos[spawn.actorID].state = PlayerInfo.State.Survivor;
				playerInfos[spawn.actorID].position.x = spawn.location_to.x;
				playerInfos[spawn.actorID].position.y = spawn.location_to.y;
			}
			else
			{
				// �߸��� ��ġ: ��ȥ �г�Ƽ �ο�
				playerInfos[spawn.actorID].state = PlayerInfo.State.Soul;
				playerInfos[spawn.actorID].position.x = -1;
				playerInfos[spawn.actorID].position.y = -1;
				playerStats[spawn.actorID].transition_cooldown = Constants.Soul_Interval_Penalty;

				//��ȥ ���� ���� ����
				scoreboard.Update(Scoreboard.ScoreTypeCode.Soul_Freedom, spawn.actorID, 1);

				// ��� ���
				reactions.add(new Reaction(spawn.actorID, Reaction.TypeCode.Punished, spawn.actorID, spawn.location_to));

				// ���� ������ ����ϵ��� �����Ǿ� �ִ� ��� ���
				if ( settings.print_errors == true )
					System.err.printf("Error. %s attempts to spawn at %s. - Game #%d, Turn %d\n", players[spawn.actorID].name, spawn.location_to, gameInfo.gameNumber, gameInfo.currentTurnNumber);
			}
		}

		/*
		 * Phase 3. Rise
		 */

		for ( int i = 0; i < Constants.Total_Players; i++ )
		{
			// �ð��� �� ��ü�� �ִٸ�
			if ( playerInfos[i].state == PlayerInfo.State.Corpse && playerStats[i].transition_cooldown == 0 )
			{
				// ���� ��ȯ: ��ü -> ����ü
				playerInfos[i].state = PlayerInfo.State.Infected;

				// ��ü Max ��� �ʱ�ȭ
				playerStats[i].current_healed_players.clear();

				// ����ü�� �Ͼ ù ���� ü���� �������� ����
				playerStats[i].infected_healedAtThisTurn = true;

				// ��� ���
				reactions.add(new Reaction(i, Reaction.TypeCode.Rise, i, playerInfos[i].position));
			}
		}

		/*
		 * Phase 4. DirectInfect
		 */

		// ���� ������ �߻��ؾ� �Ѵٸ�
		if ( gameInfo.directInfectionCountdown == 0 )
		{
			for ( int i = 0; i < Constants.Total_Players; i++ )
			{
				// ���� ������ �����ߴ� �����ڰ� �ִٸ�
				if ( playerStats[i].survivor_acceptDirectInfection == true && playerInfos[i].state == PlayerInfo.State.Survivor )
				{
					// ���� ��ȯ: ������ -> ����ü
					playerInfos[i].state = PlayerInfo.State.Infected;
					playerInfos[i].hitPoint = Constants.Infected_Initial_Point;

					// ���� �������� ����ü�� �� ù ���� ü���� �������� ����
					playerStats[i].infected_healedAtThisTurn = true;

					// ��� ���
					reactions.add(new Reaction(i, Reaction.TypeCode.DirectInfect, i, playerInfos[i].position));
				}
			}
		}

		/*
		 * Phase 5. Kill
		 */

		// ��� ����ü�� ����
		for ( int iInfected = 0; iInfected < Constants.Total_Players; iInfected++ )
		{
			if ( playerInfos[iInfected].state == PlayerInfo.State.Infected )
			{
				boolean isKillOccured = false;

				// ��� �����ڿ� ����
				for ( int iSurvivor = 0; iSurvivor < Constants.Total_Players; iSurvivor++ )
				{
					// �ش� ����ü�� ���� ĭ�� �����ڰ� �ִٸ�
					if ( playerInfos[iSurvivor].state == PlayerInfo.State.Survivor &&
							playerInfos[iSurvivor].position.equals(playerInfos[iInfected].position) )
					{
						isKillOccured = true;

						// �����ڴ� ���� ������ üũ
						playerStats[iSurvivor].survivor_deadAtThisTurn = true;

						// ����ü Max ��� ����
						++playerStats[iInfected].current_kill_point;

						// ��� ���
						reactions.add(new Reaction(iInfected, Reaction.TypeCode.Kill, iSurvivor, playerInfos[iInfected].position));
					}
				}

				// ����ü�� �̹� �Ͽ� �����ڸ� óġ�� ��� ����ü Max ��� ����
				if ( isKillOccured == true )
				{
					scoreboard.Update(Scoreboard.ScoreTypeCode.Infected_Max, iInfected, playerStats[iInfected].current_kill_point);
				}
			}
		}

		// ��� �����ڿ� ����
		for ( int iSurvivor = 0; iSurvivor < Constants.Total_Players; iSurvivor++ )
		{
			if ( playerInfos[iSurvivor].state == PlayerInfo.State.Survivor )
			{
				// �̹� �Ͽ� ���� ������ üũ�Ǿ� �ִٸ�
				if ( playerStats[iSurvivor].survivor_deadAtThisTurn == true )
				{
					playerStats[iSurvivor].survivor_deadAtThisTurn = false;

					// ���� ��ȯ: ������ -> ��ü
					playerInfos[iSurvivor].state = PlayerInfo.State.Corpse;
					playerInfos[iSurvivor].hitPoint = Constants.Infected_Initial_Point;
					playerStats[iSurvivor].transition_cooldown = Constants.Corpse_Interval_Rise;

					// ������ Max ��� �ʱ�ȭ
					playerStats[iSurvivor].current_survive_point = 0;
				}
				// ���� �ʾҴٸ� �����ڷ� �̹� ���� �������� ���̹Ƿ� ������ Max ��� ����
				else
				{
					scoreboard.Update(Scoreboard.ScoreTypeCode.Survivor_Max, iSurvivor, ++playerStats[iSurvivor].current_survive_point);
				}
			}
		}

		/*
		 * Phase 6. Heal
		 */

		// ��� ��ü�� ����
		for ( int iCorpse = 0; iCorpse < Constants.Total_Players; iCorpse++ )
		{
			if ( playerInfos[iCorpse].state == PlayerInfo.State.Corpse )
			{
				boolean isNewHealOccured = false;

				// ��� ����ü�� ����
				for ( int iInfected = 0; iInfected < Constants.Total_Players; iInfected++ )
				{
					// �ش� ��ü�� ���� ĭ�� ����ü�� �ִٸ�
					if ( playerInfos[iInfected].state == PlayerInfo.State.Infected && playerInfos[iCorpse].position.equals(playerInfos[iInfected].position) )
					{
						// ����ü�� �̹� �Ͽ� ġ���� ���� ������ üũ
						playerStats[iInfected].infected_healedAtThisTurn = true;

						// ����ü�� ü���� ȸ����Ű�� ��ü Total ��� ����, Max ��� �߰�
						playerInfos[iInfected].hitPoint += Constants.Corpse_Rate_Heal;
						scoreboard.Update(Scoreboard.ScoreTypeCode.Corpse_Total, iCorpse, Constants.Corpse_Rate_Heal);
						if ( playerStats[iCorpse].current_healed_players.contains(iInfected) == false )
						{
							isNewHealOccured = true;
							playerStats[iCorpse].current_healed_players.add(iInfected);
						}

						// ��ü�� �ʱ� ü���� ������Ű�� ����ü Total ��� ����
						playerInfos[iCorpse].hitPoint += Constants.Infected_Rate_Infection;
						scoreboard.Update(Scoreboard.ScoreTypeCode.Infected_Total, iInfected, Constants.Infected_Rate_Infection);

						// ��� ���
						reactions.add(new Reaction(iCorpse, Reaction.TypeCode.Heal, iInfected, playerInfos[iCorpse].position));
					}
				}

				// �̹� �Ͽ� ���ο� ����ü�� ġ���� ��� ��ü Max ��� ����
				if ( isNewHealOccured == true )
				{
					scoreboard.Update(Scoreboard.ScoreTypeCode.Corpse_Max, iCorpse, playerStats[iCorpse].current_healed_players.size());
				}
			}
		}

		/*
		 * Phase 7. Vanish
		 */

		// ��� ����ü�� ����
		for ( int iInfected = 0; iInfected < Constants.Total_Players; iInfected++ )
		{
			if ( playerInfos[iInfected].state == PlayerInfo.State.Infected )
			{
				// �̹� �Ͽ� ġ���޾Ҵٸ� (���ڸ��� �ӹ����� �ϴ���) ü���� �������� ����
				if ( playerStats[iInfected].infected_healedAtThisTurn == true )
				{
					playerStats[iInfected].infected_healedAtThisTurn = false;
					playerStats[iInfected].infected_prayingAtThisTurn = false;
				}
				// �̹� �Ͽ� �ƹ��� óġ���� �ʾҴٸ� ü�� ����
				else
				{
					// �̹� �Ͽ� ���ڸ��� �� �־��ٸ� ü�� ���� ����
					if ( playerStats[iInfected].infected_prayingAtThisTurn == true )
					{
						playerStats[iInfected].infected_prayingAtThisTurn = false;

						playerInfos[iInfected].hitPoint -= Constants.Infected_Cost_Pray;
					}
					else
					{
						playerInfos[iInfected].hitPoint -= Constants.Infected_Cost_Move;
					}
				}

				// ������ ��� ü���� 0 ���ϰ� �� ���
				if ( playerInfos[iInfected].hitPoint <= 0 )
				{
					// ���� ��ȯ: ����ü -> ��ȥ
					playerInfos[iInfected].hitPoint = 0;
					playerInfos[iInfected].state = PlayerInfo.State.Soul;
					playerStats[iInfected].transition_cooldown = Constants.Soul_Interval_Respawn;

					// ����ü Max ��� �ʱ�ȭ
					playerStats[iInfected].current_kill_point = 0;

					// ��� ���
					reactions.add(new Reaction(iInfected, Reaction.TypeCode.Vanish, iInfected, playerInfos[iInfected].position));
				}
			}
		}

		/*
		 * Phase 8. ���� �� �غ�
		 */

		// �÷��̾� ���� �ʱ�ȭ
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			// �ν��Ͻ� ������ �����ϰ� ���߿� �׼����ϴ� ���� �����ϱ� ���� ���� ����
			PlayerInfo info = new PlayerInfo(playerInfos[iPlayer]);

			playerInfos[iPlayer] = info;
			players[iPlayer].myInfo = info;
		}

		// �þ� ���� ����
		RefreshSightInfo(false);

		// ��ٿ� ����
		if ( gameInfo.directInfectionCountdown != -1 )
		{
			--gameInfo.directInfectionCountdown;
		}

		for ( int i = 0; i < Constants.Total_Players; i++ )
		{
			if ( playerStats[i].transition_cooldown != 0 )
			{
				--playerStats[i].transition_cooldown;
			}
		}

		// ���� �Ͽ� ���� ������ �غ��ؾ� �ϴ��� üũ
		boolean isAllSurvivors = true;

		for ( int i = 0; i < Constants.Total_Players; i++ )
		{
			// ��ü �Ǵ� ����ü ������ �÷��̾ �� ���̶� ������ ��� ���� ������ ���۵��� ����
			if ( playerInfos[i].state == PlayerInfo.State.Corpse || playerInfos[i].state == PlayerInfo.State.Infected )
			{
				isAllSurvivors = false;
				break;
			}
		}

		if ( isAllSurvivors == true && gameInfo.directInfectionCountdown == -1 )
		{
			gameInfo.isDirectInfectionChoosingTurn = true;
			gameInfo.directInfectionCountdown = Constants.Survivor_Interval_DirectInfection;
		}

		// �ܼ� ���� �����Ǿ� �ִ� ��� �� ����� �ֿܼ� ���
		if ( settings.use_console_mode == true )
			PrintResultToConsole_AtEndOfTurn();

		// ���� �Ͽ� ������ Decision ����
		for ( int i = 0; i < Constants.Total_Players; i++ )
		{
			decisions[i].location_from.x = playerInfos[i].position.x;
			decisions[i].location_from.y = playerInfos[i].position.y;

			switch (playerInfos[i].state)
			{
			case Survivor:
				decisions[i].type = Decision.TypeCode.Survivor_Move;
				break;
			case Corpse:
				decisions[i].type = Decision.TypeCode.Corpse_Stay;
				break;
			case Infected:
				decisions[i].type = Decision.TypeCode.Infected_Move;
				break;
			case Soul:
				if ( playerStats[i].transition_cooldown == 0 )
				{
					decisions[i].type = Decision.TypeCode.Soul_Spawn;
				}
				else
				{
					decisions[i].type = Decision.TypeCode.Soul_Stay;
				}
				break;
			default:
				break;
			}
		}
	}

	/*
	 * �÷��̾�� ������ �þ� ���� ����
	 * isForSpawn�� true�� ���(��ġ �غ�) �̹��� ��ġ�� ��ȥ�鿡 ���ؼ��� ���� ����
	 */
	private void RefreshSightInfo(boolean isForSpawn)
	{
		// ��ġ �غ����� ���
		if ( isForSpawn == true )
		{
			// ���� �̹� �Ͽ� ��ġ�� ��ȥ�� �ϳ��� ���� ��� ���� ����
			boolean isNoSoulToSpawn = true;

			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
				if ( playerInfos[iPlayer].state == PlayerInfo.State.Soul && playerStats[iPlayer].transition_cooldown == 0 )
				{
					isNoSoulToSpawn = false;
					break;
				}

			if ( isNoSoulToSpawn == true )
				return;

			
			// ��ġ�� ��ȥ���� ���� ��� �ʱ�ȭ
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				if ( playerInfos[iPlayer].state == PlayerInfo.State.Soul && playerStats[iPlayer].transition_cooldown == 0 )
				{
					try
					{
						players[iPlayer].othersInfo_withinSight.clear();
						players[iPlayer].othersInfo_detected.clear();
						players[iPlayer].actions.clear();
						players[iPlayer].reactions.clear();
					}
					catch (Exception e)
					{
						// Ȥ�� �÷��̾ ��� �ʵ带 �����߸� ��� �����
						players[iPlayer].othersInfo_withinSight = new ArrayList<PlayerInfo>();
						players[iPlayer].othersInfo_detected = new ArrayList<PlayerInfo>();
						players[iPlayer].actions = new ArrayList<Action>();
						players[iPlayer].reactions = new ArrayList<Reaction>();
					}
				}
			}

			// �켱 ��� ĭ�� '�� ĭ'���� ����
			cells.Reset();

			// �÷��̾� ������ �� ĭ�� �߰�
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				PlayerInfo info = playerInfos[iPlayer];

				// ��ȥ ������ �÷��̾�� ���ǽ� ���� �� �����Ƿ� ����
				if ( info.state != PlayerInfo.State.Soul )
				{
					Point pos = playerInfos[iPlayer].position;
					CellInfo cell = cells.data[pos.y][pos.x];

					// �� ĭ�� ó�� ������ �߰��� �� �ش� ĭ�� ���� �� �ν��Ͻ� ����
					if ( cell == CellInfo.Blank )
					{
						cell = new CellInfo();
						cells.data[pos.y][pos.x] = cell;
					}

					cell.playersInTheCell.add(info);
				}
			}

			// �������� �̵� ������ �� ĭ�� �߰�(���� ��ġ�� �̷������ �ʾ����Ƿ� �������� '�̵�'�� ��Ͽ� ����)
			for ( Action action : valid_actions )
			{
				Point pos = action.location_to;
				CellInfo cell = cells.data[pos.y][pos.x];

				// �� ĭ�� ó�� ������ �߰��� �� �ش� ĭ�� ���� �� �ν��Ͻ� ����
				if ( cell == CellInfo.Blank )
				{
					cell = new CellInfo();
					cells.data[pos.y][pos.x] = cell;
				}

				cell.actionsInTheCell.add(action);
			}

			// ��� ������ �� ĭ�� �߰�
			for ( Reaction reaction : reactions )
			{
				Point pos = reaction.location;

				// ��ǥ ������ �������� ��Ǹ� �߰�
				if ( pos.IsValid() == true )
				{
					CellInfo cell = cells.data[pos.y][pos.x];

					// �� ĭ�� ó�� ������ �߰��� �� �ش� ĭ�� ���� �� �ν��Ͻ� ����
					if ( cell == CellInfo.Blank )
					{
						cell = new CellInfo();
						cells.data[pos.y][pos.x] = cell;
					}

					cell.reactionsInTheCell.add(reaction);
				}
			}

			// ��ġ�� ��ȥ���� ������ ���� �߰�
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				Player player = players[iPlayer];
				PlayerInfo info = playerInfos[iPlayer];

				if ( info.state == PlayerInfo.State.Soul && playerStats[iPlayer].transition_cooldown == 0 )
				{
					// ��� ĭ�� ���� �þ�, �ൿ, ��� ���� �߰�
					CellInfo cell;
					for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
						for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						{
							cell = cells.data[iRow][iColumn];
							player.othersInfo_withinSight.addAll(cell.playersInTheCell);
							if ( player.receiveActions == true )
								player.actions.addAll(cell.actionsInTheCell);
							if ( player.receiveReactions == true )
								player.reactions.addAll(cell.reactionsInTheCell);
						}

					// ���ǽ� ���� �� �ִ� ��ȥ ���� �߰�
					if ( player.receiveOthersInfo_detected == true )
						for ( PlayerInfo otherInfo : playerInfos )
							if ( otherInfo.state == PlayerInfo.State.Soul )
								player.othersInfo_detected.add(otherInfo);
				}
			}
		}
		else
		{
			// �÷��̾ ���� ��� �ʱ�ȭ
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				try
				{
					players[iPlayer].othersInfo_withinSight.clear();
					players[iPlayer].othersInfo_detected.clear();
					players[iPlayer].actions.clear();
					players[iPlayer].reactions.clear();
				}
				catch (Exception e)
				{
					// Ȥ�� �÷��̾ ��� �ʵ带 �����߸� ��� �����
					players[iPlayer].othersInfo_withinSight = new ArrayList<PlayerInfo>();
					players[iPlayer].othersInfo_detected = new ArrayList<PlayerInfo>();
					players[iPlayer].actions = new ArrayList<Action>();
					players[iPlayer].reactions = new ArrayList<Reaction>();
				}
			}

			// �켱 ��� ĭ�� '�� ĭ'���� ����
			cells.Reset();

			// �÷��̾� ������ �� ĭ�� �߰�
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				PlayerInfo info = playerInfos[iPlayer];

				// ��ȥ ������ �÷��̾�� ���ǽ� ���� �� �����Ƿ� ����
				if ( info.state != PlayerInfo.State.Soul )
				{
					Point pos = playerInfos[iPlayer].position;
					CellInfo cell = cells.data[pos.y][pos.x];

					// �� ĭ�� ó�� ������ �߰��� �� �ش� ĭ�� ���� �� �ν��Ͻ� ����
					if ( cell == CellInfo.Blank )
					{
						cell = new CellInfo();
						cells.data[pos.y][pos.x] = cell;
					}

					cell.playersInTheCell.add(info);
				}
			}

			// �þ� ���� ���� �ٸ� �÷��̾� ���� �߰�
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				Player player = players[iPlayer];
				PlayerInfo info = playerInfos[iPlayer];
				Point pos;

				switch (info.state)
				{
				case Survivor:
					/*
					 * �Ʒ� ������� ĭ ���� �˻� �� �߰�
					 * _ _ 0 _ _
					 * _ 1 2 3 _
					 * 4 5 6 7 8
					 * _ 9 A B _
					 * _ _ C _ _
					 */
					pos = new Point(info.position);

					pos.y -= 2;
					AddOthersInfoToPlayer(player, pos);

					++pos.y;
					--pos.x;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);

					++pos.y;
					pos.x -= 3;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);

					++pos.y;
					pos.x -= 3;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);
					++pos.x;
					AddOthersInfoToPlayer(player, pos);

					++pos.y;
					--pos.x;
					AddOthersInfoToPlayer(player, pos);
					break;
				case Corpse:
					// �ڽ��� ���� ��ġ�� ĭ�� ���� ������ �߰�
					AddOthersInfoToPlayer(player, info.position);
					break;
				case Infected:
					// �ڽ� �߽� 5x5ĭ�� ���� ���� �߰�
					pos = new Point();
					for ( int rowOffset = -2; rowOffset <= 2; ++rowOffset )
					{
						pos.y = info.position.y + rowOffset;
						for ( int columnOffset = -2; columnOffset <= 2; ++columnOffset )
						{
							pos.x = info.position.x + columnOffset;
							AddOthersInfoToPlayer(player, pos);
						}
					}
					break;
				case Soul:
					// ��� ĭ�� ���� ���� �߰�
					CellInfo cell;
					for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
						for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						{
							cell = cells.data[iRow][iColumn];
							player.othersInfo_withinSight.addAll(cell.playersInTheCell);
						}
					break;
				default:
					break;
				}
			}

			// ���� �� ���� ���� ����
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				PlayerInfo myInfo = playerInfos[iPlayer];
				Player player = players[iPlayer];

				switch (myInfo.state)
				{
				case Survivor:
					// ������ - ���� ���� ���� �� ����, ������ Total ��� ����
					int numberOfOtherSurvivors = -1; // �ٸ� ������ �� ���迡 �� �ڽ��� �����ϹǷ�
														// �ʱⰪ�� -1�� ����
					int numberOfOtherNonSurvivors = 0;
					int point_spot;

					// �� �þ� ���� ���� �ڽ� �� �ٸ� ������ �˻�, �ٸ� ������ ���� ��ü/����ü �� ����
					ArrayList<PlayerInfo> otherSurvivorsInSight = new ArrayList<PlayerInfo>();
					for ( PlayerInfo otherInfo : player.othersInfo_withinSight )
					{
						if ( otherInfo.state == PlayerInfo.State.Survivor )
						{
							otherSurvivorsInSight.add(otherInfo);
							++numberOfOtherSurvivors;
						}
						else
						{
							++numberOfOtherNonSurvivors;
						}

						//��� ���
						reactions.add(new Reaction(iPlayer, Reaction.TypeCode.Spot, otherInfo.ID, myInfo.position));
					}

					// �ڽ� �� �ٸ� �����ڵ鿡�� ���� ������ �÷��̾� ���� ����
					for ( PlayerInfo otherSurvivor : otherSurvivorsInSight )
					{
						// �ش� �����ڰ� ���� ������ �����Ѵٸ� ����
						if ( players[otherSurvivor.ID].receiveOthersInfo_detected == true )
							for ( PlayerInfo otherInfo : player.othersInfo_withinSight )
								// �ش� ���� ������ �����ϰ� ���� ���� ��쿡�� ����
								if ( players[otherSurvivor.ID].othersInfo_detected.contains(otherInfo) == false )
									players[otherSurvivor.ID].othersInfo_detected.add(otherInfo);
					}

					// ���� ���� �� ���� - �þ� ���� ���� ��ü �� ����ü �� * �þ� ���� ���� ������ ��(�ڽ� ����)
					point_spot = numberOfOtherNonSurvivors * numberOfOtherSurvivors;

					if ( point_spot != 0 )
						scoreboard.Update(Scoreboard.ScoreTypeCode.Survivor_Total, iPlayer, point_spot);
					break;
				case Infected:
					// ����ü - ���ǽ� ���� ��ü ����
					// �ڽ��� ���� ������ �����Ѵٸ� �߰�
					if ( player.receiveOthersInfo_detected == true )
						for ( PlayerInfo otherInfo : playerInfos )
							if ( otherInfo.state == PlayerInfo.State.Corpse )
								player.othersInfo_detected.add(otherInfo);
					break;
				case Soul:
					// ��ȥ - ���ǽ� ���� �� �ִ� ��ȥ ����
					// �ڽ��� ���� ������ �����Ѵٸ� �߰�
					if ( player.receiveOthersInfo_detected == true )
						for ( PlayerInfo otherInfo : playerInfos )
							if ( otherInfo.state == PlayerInfo.State.Soul )
								player.othersInfo_detected.add(otherInfo);
					break;
				default:
					break;
				}
			}

			// ��ȿ�� �ൿ ������ �� ĭ�� �߰�
			for ( Action action : valid_actions )
			{
				Point pos = action.location_to;
				CellInfo cell = cells.data[pos.y][pos.x];

				// �� ĭ�� ó�� ������ �߰��� �� �ش� ĭ�� ���� �� �ν��Ͻ� ����
				if ( cell == CellInfo.Blank )
				{
					cell = new CellInfo();
					cells.data[pos.y][pos.x] = cell;
				}

				cell.actionsInTheCell.add(action);
			}

			// ��� ������ �� ĭ�� �߰�
			for ( Reaction reaction : reactions )
			{
				Point pos = reaction.location;
				
				//��ȿ�� ��ǥ���� �߻��� ��Ǹ� �߰�
				if ( pos.IsValid() == true )
				{
					CellInfo cell = cells.data[pos.y][pos.x];
	
					// �� ĭ�� ó�� ������ �߰��� �� �ش� ĭ�� ���� �� �ν��Ͻ� ����
					if ( cell == CellInfo.Blank )
					{
						cell = new CellInfo();
						cells.data[pos.y][pos.x] = cell;
					}
	
					cell.reactionsInTheCell.add(reaction);
				}
			}

			// �ൿ �� ��� ���� �߰�
			for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				Player player = players[iPlayer];

				// �ش� �÷��̾ ���� ������ ����� ��쿡�� �߰�
				if ( player.receiveActions == true || player.receiveReactions == true )
				{
					PlayerInfo info = playerInfos[iPlayer];
					Point pos;

					switch (info.state)
					{
					case Survivor:
						/*
						 * �Ʒ� ������� ĭ ���� �˻� �� �߰�
						 * _ _ 0 _ _
						 * _ 1 2 3 _
						 * 4 5 6 7 8
						 * _ 9 A B _
						 * _ _ C _ _
						 */
						pos = new Point(info.position);

						pos.y -= 2;
						AddAdditionalInfoToPlayer(player, pos);

						++pos.y;
						--pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);

						++pos.y;
						pos.x -= 3;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);

						++pos.y;
						pos.x -= 3;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						++pos.x;
						AddAdditionalInfoToPlayer(player, pos);

						++pos.y;
						--pos.x;
						AddAdditionalInfoToPlayer(player, pos);
						break;
					case Corpse:
						// �ڽ��� ���� ��ġ�� ĭ�� ���� ������ �߰�
						AddAdditionalInfoToPlayer(player, info.position);
						break;
					case Infected:
						// �ڽ� �߽� 5x5ĭ�� ���� ���� �߰�
						pos = new Point();
						for ( int rowOffset = -2; rowOffset <= 2; ++rowOffset )
						{
							pos.y = info.position.y + rowOffset;
							for ( int columnOffset = -2; columnOffset <= 2; ++columnOffset )
							{
								pos.x = info.position.x + columnOffset;
								AddAdditionalInfoToPlayer(player, pos);
							}
						}
						break;
					case Soul:
						// ��� ĭ�� ���� ���� �߰�
						CellInfo cell;
						for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
							for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
							{
								cell = cells.data[iRow][iColumn];
								if ( player.receiveActions == true )
									player.actions.addAll(cell.actionsInTheCell);
								if ( player.receiveReactions == true )
									player.reactions.addAll(cell.reactionsInTheCell);
							}
						
						break;
					default:
						break;
					}
				}
			}
		}
	}
	

	/*
	 * Ư�� �÷��̾��� �þ� ���� ���� �ִ� �ش� ��ġ�� �ٸ� �÷��̾� ������ ��Ͽ� �߰�
	 */
	private void AddOthersInfoToPlayer(Player player, Point pos)
	{
		if ( pos.IsValid() == true )
		{
			CellInfo cell = cells.data[pos.y][pos.x];
			player.othersInfo_withinSight.addAll(cell.playersInTheCell);
		}
	}
	

	/*
	 * Ư�� �÷��̾��� �þ� ���� ���� �ִ� �ش� ��ġ�� �ൿ �� ��� ������ ��Ͽ� �߰�
	 */
	private void AddAdditionalInfoToPlayer(Player player, Point pos)
	{
		if ( pos.IsValid() == true )
		{
			CellInfo cell = cells.data[pos.y][pos.x];
			if ( player.receiveActions == true )
				player.actions.addAll(cell.actionsInTheCell);
			if ( player.receiveReactions == true )
				player.reactions.addAll(cell.reactionsInTheCell);
		}
	}
	

	/*
	 * ���� ���� �� �� ������ �ֿܼ� ���
	 */
	private void PrintResultToConsole_AtEndOfTurn()
	{
		// �÷��̾�#0�� ���� ��Ҹ� ����Ϸ��� ���
		if ( settings.print_first_player_only == true )
		{
			if ( settings.print_playerInfos == true )
			{
				System.out.println(players[0].name + " | State: " + playerInfos[0].state + ", HP: " + playerInfos[0].hitPoint + ", Position: " + playerInfos[0].position);
			}

			if ( settings.print_decisions == true )
			{
				switch (decisions[0].type)
				{
				case Survivor_Move:
					System.out.println("Decision: Move to " + decisions[0].direction + " as Survivor");
					break;
				case Corpse_Stay:
					System.out.println("Decision: Stay as Corpse");
					break;
				case Infected_Move:
					System.out.println("Decision: Move to " + decisions[0].direction + " as Infected");
					break;
				case Soul_Stay:
					System.out.println("Decision: Stay as Soul");
					break;
				case Soul_Spawn:
					System.out.println("Decision: Spawn at " + decisions[0].location_to);
					break;
				default:
					break;
				}
			}

			if ( settings.print_actions == true )
			{
				for ( Action move : moves )
					if ( move.actorID == 0 )
					{
						System.out.println("Action: Move from " + move.location_from + " to " + move.location_to + (move.type == Action.TypeCode.Illigal ? " - Illigal" : ""));
						break;
					}

				for ( Action spawn : spawns )
					if ( spawn.actorID == 0 )
					{
						System.out.println("Action: Spawn at " + spawn.location_to + (spawn.type == Action.TypeCode.Illigal ? " - Illigal" : ""));
						break;
					}
			}

			if ( settings.print_reactions == true )
			{
				System.out.println("Reactions:");

				for ( Reaction reaction : reactions )
					if ( reaction.subjectID == 0 )
					{
						System.out.println("Type: " + reaction.type + ", Subject: " + players[reaction.subjectID].name + ", Object: " + players[reaction.objectID].name + ", Location: " + reaction.location);
					}
			}

			if ( settings.print_scores_during_game == true )
			{
				System.out.println(
						"Score:\n" +
								"SMax STot CMax CTot IMax ITot");

				System.out.println(scoreboard.scores[0]);
			}
		}
		// ��� �÷��̾ ���� ����Ϸ��� ���
		else
		{
			if ( settings.print_playerInfos == true )
			{
				System.out.println("PlayerInfos:");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.println(players[0].name + " | State: " + playerInfos[0].state + ", HP: " + playerInfos[0].hitPoint + ", Position: " + playerInfos[0].position);
			}

			if ( settings.print_decisions == true )
			{
				System.out.println("Decisions:");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
				{
					switch (decisions[iPlayer].type)
					{
					case Survivor_Move:
						System.out.println(players[iPlayer].name + ": Move to " + decisions[iPlayer].direction + " as Survivor");
						break;
					case Corpse_Stay:
						System.out.println(players[iPlayer].name + ": Stay as Corpse");
						break;
					case Infected_Move:
						System.out.println(players[iPlayer].name + ": Move to " + decisions[iPlayer].direction + " as Infected");
						break;
					case Soul_Stay:
						System.out.println(players[iPlayer].name + ": Stay as Soul");
						break;
					case Soul_Spawn:
						System.out.println(players[iPlayer].name + ": Spawn at " + decisions[iPlayer].location_to);
						break;
					default:
						break;
					}
				}
			}

			if ( settings.print_actions == true )
			{
				System.out.println("Moves:");
				for ( Action move : moves )
					System.out.println(players[move.actorID].name + ": Move from " + move.location_from + " to " + move.location_to + (move.type == Action.TypeCode.Illigal ? " - Illigal" : ""));

				System.out.println("Spawns:");
				for ( Action spawn : spawns )
					System.out.println(players[spawn.actorID].name + ": Spawn at " + spawn.location_to + (spawn.type == Action.TypeCode.Illigal ? " - Illigal" : ""));
			}

			if ( settings.print_reactions == true )
			{
				System.out.println("Reactions:");
				for ( Reaction reaction : reactions )
					System.out.println("Type: " + reaction.type + ", Subject: " + players[reaction.subjectID].name + ", Object: " + players[reaction.objectID].name + ", Location: " + reaction.location);
			}

			if ( settings.print_scores_during_game == true )
			{
				System.out.println(
						"Score:\n" +
								"SMax STot CMax CTot IMax ITot - Name");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.println(scoreboard.scores[iPlayer].toString() + " - " + players[iPlayer].name);
			}
		}
	}
	
	/*
	 * ���� ����� �ֿܼ� ���
	 */
	private void PrintResultToConsole_AtEndOfTheGame()
	{
		if ( settings.print_first_player_only == true )
		{
			if ( settings.print_scores_at_the_end == true )
			{
				System.out.println(
						"Score|Grade(Rank):\n" +
								"       SMax         STot         CMax         CTot         IMax         ITot |    Final");
				System.out.printf("%3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d) |  %3d(%2d)\n", scoreboard.scores[0].data[0], scoreboard.grades[0][0], scoreboard.ranks[0][0], scoreboard.scores[0].data[1], scoreboard.grades[0][1], scoreboard.ranks[0][1], scoreboard.scores[0].data[2], scoreboard.grades[0][2], scoreboard.ranks[0][2], scoreboard.scores[0].data[3], scoreboard.grades[0][3], scoreboard.ranks[0][3], scoreboard.scores[0].data[4], scoreboard.grades[0][4], scoreboard.ranks[0][4], scoreboard.scores[0].data[5], scoreboard.grades[0][5], scoreboard.ranks[0][5], scoreboard.final_grades[0], scoreboard.final_ranks[0]);
			}
			else
			{
				System.out.println(
						"Grade(Rank):\n" +
								"   SMax     STot     CMax     CTot     IMax     ITot |    Final");
				System.out.printf("%3d(%2d)  %3d(%2d)  %3d(%2d)  %3d(%2d)  %3d(%2d)  %3d(%2d) |  %3d(%2d)\n", scoreboard.grades[0][0], scoreboard.ranks[0][0], scoreboard.grades[0][1], scoreboard.ranks[0][1], scoreboard.grades[0][2], scoreboard.ranks[0][2], scoreboard.grades[0][3], scoreboard.ranks[0][3], scoreboard.grades[0][4], scoreboard.ranks[0][4], scoreboard.grades[0][5], scoreboard.ranks[0][5], scoreboard.final_grades[0], scoreboard.final_ranks[0]);
			}
		}
		else
		{
			if ( settings.print_scores_at_the_end == true )
			{
				System.out.println(
						"Score|Grade(Rank):\n" +
								"       SMax         STot         CMax         CTot         IMax         ITot |    Final - Name");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.printf("%3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d)  %3d|%3d(%2d) |  %3d(%2d) - %s\n", scoreboard.scores[iPlayer].data[0], scoreboard.grades[iPlayer][0], scoreboard.ranks[iPlayer][0], scoreboard.scores[iPlayer].data[1], scoreboard.grades[iPlayer][1], scoreboard.ranks[iPlayer][1], scoreboard.scores[iPlayer].data[2], scoreboard.grades[iPlayer][2], scoreboard.ranks[iPlayer][2], scoreboard.scores[iPlayer].data[3], scoreboard.grades[iPlayer][3], scoreboard.ranks[iPlayer][3], scoreboard.scores[iPlayer].data[4], scoreboard.grades[iPlayer][4], scoreboard.ranks[iPlayer][4], scoreboard.scores[iPlayer].data[5], scoreboard.grades[iPlayer][5], scoreboard.ranks[iPlayer][5], scoreboard.final_grades[iPlayer], scoreboard.final_ranks[iPlayer], players[iPlayer].name);
			}
			else
			{
				System.out.println(
						"Grade(Rank):\n" +
								"   SMax     STot     CMax     CTot     IMax     ITot |    Final - Name");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.printf("%3d(%2d)  %3d(%2d)  %3d(%2d)  %3d(%2d)  %3d(%2d)  %3d(%2d) |  %3d(%2d) - %s\n", scoreboard.grades[iPlayer][0], scoreboard.ranks[iPlayer][0], scoreboard.grades[iPlayer][1], scoreboard.ranks[iPlayer][1], scoreboard.grades[iPlayer][2], scoreboard.ranks[iPlayer][2], scoreboard.grades[iPlayer][3], scoreboard.ranks[iPlayer][3], scoreboard.grades[iPlayer][4], scoreboard.ranks[iPlayer][4], scoreboard.grades[iPlayer][5], scoreboard.ranks[iPlayer][5], scoreboard.final_grades[iPlayer], scoreboard.final_ranks[iPlayer], players[iPlayer].name);
			}
		}
	}
	
	@Override
	public void run()
	{
		Initialize();
		Start();
	}
}
