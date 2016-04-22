package l4g.customplayers;

import java.util.Arrays;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author ���ȣ 2013011060
 *
 */
public class �ű���ٱ� extends Player {
    // TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
    // �����Դϴ�.
    public �ű���ٱ�(int ID) {

	// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	super(ID, "�ű���ٱ�");

	// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
	// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
	// �������ѳ��� �˴ϴ�.
	this.trigger_acceptDirectInfection = false;

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


   /* ������ ����� ����.
    * ���� ����ϰ� ��� ���� ���� ����� ���̴�.
    * �� �������κ��� ������ ��ġ�� �޾� ���赵 risk�� ��ȯ�Ѵ�.
    * ��ȯ�� risk�� ���� risk�� ���� ���� �������� Survivor�� �̵��ϰ� �ȴ�.
    */
    int riskNorth(StateCode Inf) {
	int risk = 0;
	
	// �� ������ ������ �ȵȴ�.
	if ( myInfo.position.row == 0) {
	    risk += 1000;
	    return risk;
	}
	
	
	if (myInfo.position.row - 1 >= 0) {
	    // ��ü�� �ִ� �����δ� ���� ���� �ʴ´�.
	    if (cells[myInfo.position.row - 1][myInfo.position.column]
		    .CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
		risk += 50;
	    
	    // �� �ٷ� ���� Infected�� �־ �׷��Ա��� ū ������ ���� ���Ѵ�. 
	    // �� Infected ���� �̵��� �� �ֱ� ������ �� ���� Infected�� ��ġ�� �̵��� �� �ִ�.
	    else if (cells[myInfo.position.row - 1][myInfo.position.column]
		    .CountIf_Players(player -> player.state == Inf) > 0)
		risk += 8;
	}
	
	// cells�� Infected�� ���θ� �о�鿩 risk�� ����Ѵ�.
	
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
	
	/* ó�� 10�ϱ����� Survivor�� ���شٴѴ�.
	 * ���� ����Į�������� ���� �����ؾ� �� ���� ����̴�.
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
	DirectionCode result = DirectionCode.Up; // �߿� ������ ������ Infected�� �������� �������Ƿ� default�� Up�̴�
	
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
	// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
    }

    @Override
    public DirectionCode Infected_Move() {
	// �����Ǿ� �ִ� ���� ��ü��ź�� �غ��Ѵ�. �� ���� �� ��ġ�� ��ü �߻� �󵵸� ����س��´�.
	for (int i = 0; i < 13; i++)
	    for (int j = 0; j < 13; j++)
		if ( cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse) > 0 )
		    maxSichae[i][j]++;
	
	// ���� ��� Infected�� �ٸ� ������� ���̴� �ͺ��� ������ �״� ���� ���Ѵٸ� ������ �� �� ��� ������ ���̴�.
	return DirectionCode.Stay;
    }

    @Override
    public void Soul_Stay() {
	if (turnInfo.turnNumber == 0) {
	    /*
	     * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
	     * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
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
	
	// ���� �߹ݱ����� ���� ������ ������ ���� ���� �� �������� ���̶�� �Ǵ�. ������ ������ �ξ���.
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
	
	// ���� �Ĺݿ��� ������ ���ϰ�, ��ȸ�� ��������, �б��� �ı��Ǿ��� ������ Infected�� ������������ �Ǵܵȴ�.
	// ���� ��ü ������ ������ �ξ���.
	else if ( turnInfo.turnNumber >= 80) {
	    
	    // �߹ݱ����� �� ���� �ִ� ��ü �߻� �� �����͸� �̿��� ��ü��ź�� �������.
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
	// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	return new Point(result_row, result_column);
    }
    public int[][] maxSichae;
    public int sichae;
}
