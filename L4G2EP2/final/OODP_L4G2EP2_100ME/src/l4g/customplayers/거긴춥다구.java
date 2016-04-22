package l4g.customplayers;

import java.util.Arrays;

import l4g.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author 양승호 2013011060
 *
 */
public class 거긴춥다구 extends Player {
    // TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
    // 별개입니다.
    public 거긴춥다구(int ID) {

	// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	super(ID, "거긴춥다구");

	// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
	// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
	// 고정시켜놔도 됩니다.
	this.trigger_acceptDirectInfection = false;

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


   /* 지극히 평범한 전략.
    * 원래 평범하게 사는 것이 가장 어려운 법이다.
    * 각 방향으로부터 좀비의 위치를 받아 위험도 risk를 반환한다.
    * 반환된 risk를 토대로 risk가 가장 적은 방향으로 Survivor은 이동하게 된다.
    */
    int riskNorth(StateCode Inf) {
	int risk = 0;
	
	// 맵 밖으로 나가면 안된다.
	if ( myInfo.position.row == 0) {
	    risk += 1000;
	    return risk;
	}
	
	
	if (myInfo.position.row - 1 >= 0) {
	    // 시체가 있는 곳으로는 절대 가지 않는다.
	    if (cells[myInfo.position.row - 1][myInfo.position.column]
		    .CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		risk += 50;
	    
	    // 내 바로 옆에 Infected가 있어도 그렇게까지 큰 위협은 되지 못한다. 
	    // 그 Infected 역시 이동할 수 있기 때문에 나 역시 Infected의 위치로 이동할 수 있다.
	    else if (cells[myInfo.position.row - 1][myInfo.position.column]
		    .CountIf_Players(player -> player.state == Inf) > 0)
		risk += 8;
	}
	
	// cells에 Infected의 여부를 읽어들여 risk를 계산한다.
	
	for (int i = -3, j = -1 ; i < 4 ; i++) {
	    if ( myInfo.position.row + j < 0 || myInfo.position.row + j > 12 || myInfo.position.column + i < 0 ||
			myInfo.position.column + i > 12) continue;

	    if (i < 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0 )
		    risk++;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk++;
		}
		j--;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk++;
		}
		j++;
	    }
	}

	for (int i = -2, j = -1; i < 3; i++) {
	    if ( myInfo.position.row + j < 0 || myInfo.position.row + j > 12 || myInfo.position.column + i < 0 ||
			myInfo.position.column + i > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 3;
		}
		j--;
	    }
	    else if (i >= 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 3;
		}
		j++;
	    }
	}
	for (int i = -1, j = -1; i < 2; i++) {
	    if ( myInfo.position.row + j < 0 || myInfo.position.row + j > 12 || myInfo.position.column + i < 0 ||
			myInfo.position.column + i > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 10;
		}
		j--;
		}
	    else if (i >= 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 10;
		}
		j++;
	    }
	}
	return risk;
    }
    int riskWest(StateCode Inf) {
	int risk = 0;
	if ( myInfo.position.column == 0) {
	    risk += 1000;
	    return risk;
	}
	if (myInfo.position.column - 1 >= 0) {
	    if (cells[myInfo.position.row][myInfo.position.column - 1]
		    .CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		risk += 50;
	    else if (cells[myInfo.position.row][myInfo.position.column - 1]
		    .CountIf_Players(player -> player.state == Inf) > 0)
		risk += 8;
	}
	for (int i = -3, j = -1; i < 4; i++) {
	    if ( myInfo.position.row + i < 0 || myInfo.position.row + i > 12 || myInfo.position.column + j < 0 ||
			myInfo.position.column + j > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk++;
		}
		j--;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk++;
		}
		j++;
	    }
	}

	for (int i = -2, j = -1; i < 3; i++) {
	    if ( myInfo.position.row + i < 0 || myInfo.position.row + i > 12 || myInfo.position.column + j < 0 ||
			myInfo.position.column + j > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 3;
		}
		j--;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 3;
		}
		j++;
	    }
	}
	for (int i = -1, j = -1; i < 2; i++) {
	    if ( myInfo.position.row + i < 0 || myInfo.position.row + i > 12 || myInfo.position.column + j < 0 ||
			myInfo.position.column + j > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 10;
		}
		j--;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 10;
		}
		j++;
	    }
	}
	return risk;
    }
    int riskSouth(StateCode Inf) {
	int risk = 0;
	if ( myInfo.position.row == 12) {
	    risk += 1000;
	    return risk;
	}
	if (myInfo.position.row + 1 <= 12) {
	    if (cells[myInfo.position.row + 1][myInfo.position.column]
		    .CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		risk += 50;
	    else if (cells[myInfo.position.row + 1][myInfo.position.column]
		    .CountIf_Players(player -> player.state == Inf) > 0)
		risk += 8;
	}
	for (int i = -3, j = 1; i < 4; i++) {
	    if ( myInfo.position.row + j < 0 || myInfo.position.row + j > 12 || myInfo.position.column + i < 0 ||
			myInfo.position.column + i > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk++;
		}
		j++;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk++;
		}
		j--;
	    }
	}

	for (int i = -2, j = 1; i < 3; i++) {
	    if ( myInfo.position.row + j < 0 || myInfo.position.row + j > 12 || myInfo.position.column + i < 0 ||
			myInfo.position.column + i > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 3;
		}
		j++;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 3;
		}
		j--;
	    }
	}
	for (int i = -1, j = 1; i < 2; i++) {
	    if ( myInfo.position.row + j < 0 || myInfo.position.row + j > 12 || myInfo.position.column + i < 0 ||
			myInfo.position.column + i > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 10;
		}
		j++;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + j][myInfo.position.column + i]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + j][myInfo.position.column + i]++;
		    risk += 10;
		}
		j--;
	    }
	}
	return risk;
    }
    int riskEast(StateCode Inf) {
	int risk = 0;
	if ( myInfo.position.column == 12) {
	    risk += 1000;
	    return risk;
	}
	if (myInfo.position.column + 1 <= 12) {
	    if (cells[myInfo.position.row][myInfo.position.column + 1]
		    .CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		risk += 50;
	    else if (cells[myInfo.position.row][myInfo.position.column + 1]
		    .CountIf_Players(player -> player.state == Inf) > 0)
		risk += 8;
	}
	for (int i = -3, j = 1; i < 4; i++) {
	    if ( myInfo.position.row + i < 0 || myInfo.position.row + i > 12 || myInfo.position.column + j < 0 ||
			myInfo.position.column + j > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk++;
		}
		j++;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk++;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk++;
		}
		j--;
	    }
	}

	for (int i = -2, j = 1; i < 3; i++) {
	    if ( myInfo.position.row + i < 0 || myInfo.position.row + i > 12 || myInfo.position.column + j < 0 ||
			myInfo.position.column + j > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 3;
		}
		j++;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 3;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 3;
		}
		j--;
	    }
	}
	for (int i = -1, j = 1; i < 2; i++) {
	    if ( myInfo.position.row + i < 0 || myInfo.position.row + i > 12 || myInfo.position.column + j < 0 ||
			myInfo.position.column + j > 12) continue;
	    if (i < 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 10;
		}
		j++;
	    } else if (i >= 0) {
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == Inf) > 0)
		    risk += 10;
		if (cells[myInfo.position.row + i][myInfo.position.column + j]
			.CountIf_Players(player -> player.state == StateCode.Corpse) > 0) {
		    maxSichae[myInfo.position.row + i][myInfo.position.column + j]++;
		    risk += 10;
		}
		j--;
	    }
	}
	return risk;
    }

    
    @Override
    public DirectionCode Survivor_Move() {
	
	/* 처음 10턴까지는 Survivor은 피해다닌다.
	 * 좀비 아포칼립스에서 제일 조심해야 할 것은 사람이다.
	 */
	if ( turnInfo.turnNumber <= 10 ) {
	    int riskN = riskNorth(StateCode.Survivor), riskE = riskEast(StateCode.Survivor),
			riskS = riskSouth(StateCode.Survivor), riskW = riskWest(StateCode.Survivor);
	    int[] riskArray = {riskN, riskE, riskS, riskW};
	    DirectionCode result = DirectionCode.Up;
	    Arrays.sort(riskArray);
	    if (riskArray[0] == riskN) result = DirectionCode.Up;
	    else if (riskArray[0] == riskE)result = DirectionCode.Right;
	    else if (riskArray[0] == riskS)result = DirectionCode.Down;
	    else if (riskArray[0] == riskW)result = DirectionCode.Left;
	    return result;
	}
	
	else {
	int riskN = riskNorth(StateCode.Infected), riskE = riskEast(StateCode.Infected),
		riskS = riskSouth(StateCode.Infected), riskW = riskWest(StateCode.Infected);
	int[] riskArray = {riskN, riskE, riskS, riskW};
	DirectionCode result = DirectionCode.Up; // 추운 곳으로 갈수록 Infected의 움직임이 둔해지므로 default는 Up이다
	
	Arrays.sort(riskArray);
	if ( riskArray[0] == riskN ) result = DirectionCode.Up;
	else if ( riskArray[0] == riskE ) result = DirectionCode.Right;
	else if ( riskArray[0] == riskS ) result = DirectionCode.Down;
	else if ( riskArray[0] == riskW ) result = DirectionCode.Left;
	return result;
	}
    }

    @Override
    public void Corpse_Stay() {
	// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
    }

    @Override
    public DirectionCode Infected_Move() {
	// 감염되어 있는 동안 시체폭탄을 준비한다. 맵 상의 각 위치의 시체 발생 빈도를 기록해놓는다.
	for (int i = 0; i < 13; i++)
	    for (int j = 0; j < 13; j++)
		if ( cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse) > 0 )
		    maxSichae[i][j]++;
	
	// 세상 모든 Infected가 다른 사람들을 죽이는 것보다 스스로 죽는 것을 택한다면 세상은 좀 더 살기 좋아질 것이다.
	return DirectionCode.Stay;
    }

    @Override
    public void Soul_Stay() {
	if (turnInfo.turnNumber == 0) {
	    /*
	     * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
	     * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
	     */
	    maxSichae = new int[13][13];
	    for (int i = 0; i < 13; i++)
		for (int j = 0; j < 13; j++)
		    maxSichae[i][j] = 0;
	    sichae = 0;
	}
    }

    @Override
    public Point Soul_Spawn() {
	int result_row = 6, result_column = 6;
	int maxSRow = 0, maxSCol = 0;
	for (int i = 0; i < 13; i++) {
	    for (int j = 0; j < 13; j++) {
		if ( cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse) > 0 )
		    maxSichae[i][j]++;
	    }
	}
	
	// 게임 중반까지는 생존 점수로 점수를 버는 것이 더 ㄱㅇㄷ일 것이라고 판단. 생존에 중점을 두었다.
	if (1 < turnInfo.turnNumber && turnInfo.turnNumber < 80) {
	    if (cells[6][6].CountIf_Players(player -> player.state == StateCode.Infected) == 0) {
		result_row = 6;
		result_column = 6;
	    } else if (cells[6][5].CountIf_Players(player -> player.state == StateCode.Infected) == 0) {
		result_row = 6;
		result_column = 5;
	    } else if (cells[5][6].CountIf_Players(player -> player.state == StateCode.Infected) == 0) {
		result_row = 5;
		result_column = 6;
	    } else if (cells[5][5].CountIf_Players(player -> player.state == StateCode.Infected) == 0) {
		result_row = 5;
		result_column = 5;
	    }
	}
	
	// 게임 후반에는 국가가 망하고, 사회가 무너지고, 학교가 파괴되었을 정도로 Infected가 많아졌으리라 판단된다.
	// 따라서 시체 점수에 비중을 두었다.
	else if ( turnInfo.turnNumber >= 80) {
	    
	    // 중반까지의 맵 상의 최대 시체 발생 빈도 데이터를 이용해 시체폭탄을 노려본다.
	    for (int i = 0; i < 13; i++) {
		for (int j = 0; j < 13; j++) {
		    if ( maxSichae[i][j] > sichae ) {
			sichae = maxSichae[i][j];
			maxSRow = i;
			maxSCol = j;
		    }
		}
	    }
	    result_row = maxSRow;
	    result_column = maxSCol;
	}
	// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
	return new Point(result_row, result_column);
    }
    public int[][] maxSichae;
    public int sichae;
}
