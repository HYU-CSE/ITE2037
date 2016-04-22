package l4g.customplayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 
 * @author Prev
 * ���� (Charles)
 * �� ĳ���ʹ� ������ �� ��������,�� �������� �ֽ��ϴ�
 * ������ �پ ���������� ���� ���� �����ϵ��� �����Ͽ����ϴ�
 *
 */
public class Player_Charles extends Player
{
	
	//���� ��ġ ��ǥ. ª�� �ڵ带 ���� ���μ���
	// X = myInfo.position.column
	// Y = myInfo.position.row
	private int nowX;
	private int nowY;
	
	
	// �� ũ��. ª�� �ڵ带 ���� ���� ����
	final int MAP_H = Constants.Classroom_Height;
	final int MAP_W = Constants.Classroom_Width;
	
	/**
	 * �� �̵��� ��ȭ�� �迭
	 * ��, ��, ��, ��, ���ڸ� ����
	 */
	final int[] di = {0, 0, -1, 1, 0};
	final int[] dj = {-1, 1, 0, 0, 0};
	
	
	/**
	 * ������ (Expectation)�� ���õ� ������
	 * Ÿ�Ϻ� ���� �÷��̾� 
	 * exp_surv: ���� ������
	 * exp_infs: ���� ������
	 * exp_corp: ���� ��ü
	 * 
	 * exp_surv_tmp �� exp_infs_tmp �� ���� �����͸� ����ϱ� ���� �ӽ÷� ���Ǵ� �����̴�
	 * EXP_REV �� ���� ����ġ��, ���� �����͸� ����ҽ� �ŷڵ��� ���ߴµ� ����Ѵ�(Expectation Revision value)
	 */
	double[][] exp_surv = new double[MAP_H][MAP_W];
	double[][] exp_infs = new double[MAP_H][MAP_W];
	double[][] exp_corp = new double[MAP_H][MAP_W];
	
	double[][] exp_surv_tmp = new double[MAP_H][MAP_W];
	double[][] exp_infs_tmp = new double[MAP_H][MAP_W];
	
	final double EXP_REVS = 0.7;
	
	
	/**
	 * �ʿ����� ���赵 ����ġ
	 * �߾��� 1�̸� �����ڸ��� e��
	 * �����ڸ��� ������ �ް��ϰ� ���������� ������ �����Լ� e^x�� ������ ������
	 */
	double[][] mapWeighting = new double[MAP_H][MAP_W];
	boolean[][] isMySights = new boolean[MAP_H][MAP_W];
	
	
	// �������϶� ������ �Ұ��ΰ�?
	//boolean attackWhenInfs = false;
	boolean trySurvive = true;
	
	
	/**
	 * ������
	 */
	public Player_Charles(int ID)
	{
		super(ID, "����");
		this.trigger_acceptDirectInfection = false;
		
		
		// ��� ���� �ʱ�ȭ
		for (int i=0; i<MAP_H; i++) {
			for (int j=0; j<MAP_W; j++) {
				exp_surv[i][j] = exp_infs[i][j] = exp_corp[i][j] = 0;
				isMySights[i][j] = false;
				
				int ti = (Constants.Classroom_Height / 2) - i;
				int tj = (Constants.Classroom_Width / 2) - j;
				mapWeighting[i][j] = Math.pow(Math.E,  Math.sqrt(ti * ti + tj * tj) / (Constants.Classroom_Width * 0.7) ); // 0.7�� 2/sqrt(2)�� �ٻ簪��
			}
		}
	}
	
	/**
	 * �� ���� ��ȿ�� ������ üũ�Ѵ�
	 * @param x: Col
	 * @param y: Row
	 */
	private boolean isValidPoint(int x, int y) {
		return !(x < 0 || y < 0 || y >= Constants.Classroom_Height || x >= Constants.Classroom_Width);
	}
	
	
	/**
	 * �ش����� �ʱ�ȭ��
	 * ������, �������� �������� �����ϴ°��� �ֿ� �����̴�
	 * ��� �� �Լ��� ���� �����ϰ�, ���� �߿��ϰ�, ���� �����ɸ���. 
	 */
	private void initThisTurn() {
		int i, j, k;
		nowX = this.myInfo.position.column;
		nowY = this.myInfo.position.row;
		
		ArrayList<PlayerInfo> survivorsWithinSight = new ArrayList<>();
		if (this.myInfo.state == StateCode.Survivor)
			survivorsWithinSight.add( this.myInfo );
		
		// ��������  �ʱ�ȭ���ְ�, ��ȿ �þ߸� ��ų� ���� �� �ְ� �غ��Ѵ� (������)
		for (i=0; i<MAP_H; i++) {
			for (j=0; j<MAP_W; j++) {
				exp_surv_tmp[i][j] = exp_infs_tmp[i][j] = 0;
				exp_corp[i][j] = 0;
				isMySights[i][j] = false;
				
				if (this.myInfo.state == StateCode.Survivor) {
					for ( PlayerInfo another : this.cells[i][j].Select_Players(pi -> pi.state == StateCode.Survivor) )
						if ( another.position.GetDistance(nowY, nowX) <= 2 )
							survivorsWithinSight.add(another);
				
				}else if (this.myInfo.state == StateCode.Infected) {
					if ( Math.abs(i-nowY) <= 2 && Math.abs(j-nowX) <=2 )
						isMySights[i][j] = true;
				
				}else if (this.myInfo.state == StateCode.Soul)
					isMySights[i][j] = true;
			}
		}
		
		
		for (i=0; i<MAP_H; i++) {
			for (j=0; j<MAP_W; j++) {
				CellInfo c = this.cells[i][j];
				
				// �����ڽ� ��ȿ�þ� üũ
				for (PlayerInfo pi : survivorsWithinSight) {
					if ( pi.position.GetDistance(i, j) <= 2 ) {
						isMySights[i][j] = true;
						break;
					}
				}
				
				double[] pCounts = {
					c.CountIf_Players((pi) -> pi.state == StateCode.Survivor ),
					c.CountIf_Players((pi) -> pi.state == StateCode.Infected )
				};
				
				
				for (k=0; k<5; k++) {
					int ni = i + di[k];
					int nj = j + dj[k];
					
					if (!isValidPoint(nj, ni)) continue;
					
					
					if (isMySights[i][j]) {
						exp_infs_tmp[ni][nj] += pCounts[1] * 0.2; // ����ü�� �߰ߵǾ����� �̵��� ���ڸ� Ȥ�� 4�������� ������ Ȯ���� ���� 20%
						if (k < 4) exp_surv_tmp[ni][nj] += pCounts[0] * 0.25; // �����ڰ� �߰ߵǾ����� �̵��� 4�������� ������ Ȯ���� ���� 25%
						
					}else {
						// �߰ߵ��� �ʾ����� ���������͸� Ȱ���Ѵ�
						//�̴� ���� �߰�ġ���� ������ ���־���ϸ� ������ �Ҹ��� ���ɼ��� �ֱ⿡ ����ġ(EXP_REV)�� �����ش�.
						exp_infs_tmp[ni][nj] += exp_infs[i][j] * 0.2 * EXP_REVS;
						if (k < 4) exp_surv_tmp[ni][nj] += exp_surv[i][j] * 0.25 * EXP_REVS;
					}
				}
				
				// ��ü ó��
				c.ForEach_Players( (pi -> {
					int i2 = pi.position.row, j2 = pi.position.column;
					
					if (pi.state == StateCode.Corpse && pi.transition_cooldown > 0) {
						exp_corp[i2][j2] += 1;
					
					}else if (pi.state == StateCode.Infected && pi.transition_cooldown == 0) {
						for (int x=0; x<5; x++)
							exp_corp[i2 + di[x]][j2 + dj[x]] += 0.2;
					}
				}) );
			}
		}
		
		// ������ �ݿ�
		for (i=0; i<MAP_H; i++) {
			for (j=0; j<MAP_W; j++) {
				exp_infs[i][j] = exp_infs_tmp[i][j];
				exp_surv[i][j] = exp_surv_tmp[i][j];
			}
		}
	}
	
	
	/**
	 * ��ó (��, ��, ��, ��) ������ �������� ����Ѵ�
	 * @param exps: �����ϱ� ���� ������ �迭�̴�. �����ڸ� ã������� exp_infs, �����ڸ� ã������� exp_surv�� ������ �ȴ�
	 * @param futureExpRevision: ���������Ͽ� �����ڳ� �����ڰ� �� �� �����ٵ� �� Ȯ���� ��������� �ұ�� �����̴�
	 * @param valueWhenOutOfMap: ���� ������� ��� �־����� �����Ҽ� �ִ�. risk��� MAX�� preference��� MIN�� �־��ָ� �����Ŵ�
	 * @return [��,��,��,��] ������ ������갪 �迭�� �����Ѵ�
	 */
	private double[] getNearExpects(double exps[][], double futureExpRevision, double valueWhenOutOfMap, boolean useMapWeighting) {
		double exp[] = new double[4];  //��, ��, ��, �� ����
		for (int k=0; k<4; k++) {
			int ei = nowY + di[k]; int ej = nowX + dj[k];
			if (!isValidPoint(ej, ei)) continue;
			
			if (useMapWeighting)
				exp[k] = exps[ei][ej] * mapWeighting[ei][ej];
			
			for (int i=0; i<4; i++) {
				if (!isValidPoint(ej + dj[i], ei + di[i])) continue;
				exp[k] += exps[ei + di[i]][ej + dj[i]] * futureExpRevision;
			}
		}
		
		// ������ ������� ���ϰ�
		if (nowX == 0) exp[0] = valueWhenOutOfMap;
		if (nowY == 0) exp[2] = valueWhenOutOfMap;
		if (nowX == Constants.Classroom_Width-1) exp[1] = valueWhenOutOfMap;
		if (nowY == Constants.Classroom_Height-1) exp[3] = valueWhenOutOfMap;
		
		return exp;
	}
	
	/**
	 * �������� ���� �˸��� ������ ��´�
	 * 3���� ������� �� �� �ִ�
	 * 
	 * ���� ����
	 *  @param type: "max"�Ǵ� "min"�� �־��ش�. "max"�̸� �������� ���� ū������ ���� "min"�̸� ���������� ����. (Boolean type�� �ƴ� ������ �������� ����)
	 *  
	 * ù��° �޼���
	 *  @param nearExp: getNearExpects �޼���� ȣ��� ���� ������ �ȴ�
	 *  
	 * �μ���° �޼���� getNearExpects ���ڸ� �޾ƿ´ٰ� ���� �ȴ�.
	 */
	private DirectionCode getDirectionByExp(String type, double nearExp[]) {
		DirectionCode dc = null;
		double val = (type == "min" ? Double.MAX_VALUE : -Double.MAX_VALUE);
		
		for (int k=0; k<4; k++) {
			if ((type == "min" && nearExp[k] < val) || type == "max" && nearExp[k] > val) {
				val = nearExp[k];
				dc = DirectionCode.values()[k];
			}
		}
		return dc;
	}
	private DirectionCode getDirectionByExp(String type, double exps[][], double futureExpRevision, boolean useMapWeighting) {
		double valueWhenOutOfMap = (type == "min" ? Double.MAX_VALUE : -Double.MAX_VALUE);
		return getDirectionByExp(type, getNearExpects(exps, futureExpRevision, valueWhenOutOfMap, useMapWeighting));
	}
	private DirectionCode getDirectionByExp(String type, double exps[][], double futureExpRevision) {
		return getDirectionByExp(type, exps, futureExpRevision, true);
	}
	
	
	
	
	
	@Override
	public DirectionCode Survivor_Move()
	{
		initThisTurn();
		
		if (this.myScore.survivor_max > 60)
			trySurvive = false;
		
		
		if (this.turnInfo.turnNumber < 10) {
			if (this.trigger_acceptDirectInfection == true) {
				return getDirectionByExp("max", exp_surv, 0.1);
			}else {
				return getDirectionByExp("min", exp_surv, 0.05);
			}
		
		}else if (trySurvive) {
			// �����ڰ� ������ �����ڰ� ���������� �̵��ϰ� �Ѵ�
			double risks[][] = new double[MAP_H][MAP_W];
			for (int i=0; i<MAP_H; i++) 
				for (int j=0; j<MAP_W; j++)
					risks[i][j] = exp_infs[i][j] - exp_surv[i][j] * 0.57;
			
			return getDirectionByExp("min", risks, 0.04); //map weighting ������ min�� Ȱ���ؾ���
			
		}else {
			return getDirectionByExp("max", exp_infs, 0.04);
		}
		
	}

	@Override
	public void Corpse_Stay()
	{
		initThisTurn();
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		initThisTurn();
		
		if (exp_corp[nowX][nowY] >= 0.2)
			return getDirectionByExp("min", exp_corp, 0, false);
		else
			return DirectionCode.Stay; // ��ȭ�⵵

	}

	@Override
	public void Soul_Stay()
	{
		initThisTurn();
	}

	@Override
	public Point Soul_Spawn()
	{
		//initThisTurn();
		
		int i, j, k;
		
		if ( turnInfo.turnNumber == 0 )
			return new Point(Constants.Classroom_Width / 2, Constants.Classroom_Height / 2);
		
		else {
			
			Double maxExp = 1 - Double.MAX_VALUE;
			Point p = new Point(0, 0);
			
			if ((this.myScore.survivor_max < 25 && this.myScore.survivor_total < 200) && ((120 - this.turnInfo.turnNumber) - this.myScore.survivor_max > 40)) {
				// �߰������� �ø��� ���������� �������Ѵ�
				
				// ����ġ�� 5������ �ݺ����� ���� �����Ѱ��� ã�´�
				for (int repeat=0; repeat<5; repeat++) {
					for (i=0; i<MAP_H; i++)
						Arrays.fill(exp_infs_tmp[i], 0);
					
					for (i=0; i<MAP_H; i++) {
						for (j=0; j<MAP_W; j++) {
							exp_infs_tmp[i][j] = exp_infs[i][j] * 0.8;
							for (k=0; k<4; k++) {
								if (!isValidPoint(j+dj[k], i+di[k])) continue;
								exp_infs_tmp[i + di[k]][j + dj[k]] += exp_infs[i][j] * 0.2;
							}
						}
					}
					
					for (i=0; i<MAP_H; i++)
						for (j=0; j<MAP_W; j++)
							exp_infs[i][j] = exp_infs_tmp[i][j];
				}
				
				
				for (i=0; i<MAP_H; i++) {
					for (j=0; j<MAP_W; j++) {
						double d = exp_surv[i][j] * 0.05 - (exp_infs[i][j] * mapWeighting[i][j]);
						
						if (d > maxExp) {
							maxExp = d;
							p.row = i; p.column = j;
						}
					}
				}
				
				trySurvive = true;
				
			}else {
				// ���� ����ü�� ���������� ����Ǵ� Ÿ�Ͽ��� �������Ѵ�.
				for (i=0; i<MAP_H; i++) {
					for (j=0; j<MAP_W; j++) {
						if (exp_infs[i][j] > maxExp) {
							maxExp = exp_infs[i][j];
							p.row = i; p.column = j;
						}
					}
				}
				
				trySurvive = false;
			}
			return p;
		}
	}
}