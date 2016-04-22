package l4g.customplayers;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Ramyeon extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Ramyeon(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "���ġ��ζ��6����");

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

	static final int GoRight = 0;
	static final int GoDown = 1;
	static final int GoLeft = 2;
	static final int GoUp = 3;
	static final int Stay=4;

	int state=GoRight;

	/**
	 * �������� ��,��Ȳ�� ���� ������ �������ִ� �Լ�
	 */
	void Survive_UpdateState() {		
		int checkN=0,checkE=0,checkS=0,checkW=0,My_row=this.myInfo.position.row,My_col=this.myInfo.position.column,Height=Constants.Classroom_Height,Width=Constants.Classroom_Width;
		if(My_row>=1&&My_row<=Height-2&&My_col>=1&&My_col<=Width-2) {	//��� �������� 1ĭ�� �̵��ص� �迭 ũ�⿡ ���� �ȳ�ġ�� ��Ȳ�̸�
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//�ٷ� ���� ������
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//�ٷ� �Ʒ��� ������
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//�ٷ� ���ʿ� ������
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//�ٷ� �����ʿ� ������
		}
		else if(My_row==0&&My_col>=1&&My_col<=Width-2) {		//�� ���ʿ� �־ �������� �̵� �Ұ��� �ϰ� �ٸ����� �� �����ϸ�
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//�ٷ� �Ʒ��� ������
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//�ٷ� ���ʿ� ������
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//�ٷ� �����ʿ� ������
		}
		else if(My_row==0&&My_col==0) {	//(0,0�̸�)
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//�ٷ� �����ʿ� ������
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//�ٷ� �Ʒ��� ������
		}
		else if(My_row==0&&My_col==Width-1)	{ //������ �� �����̿� ������
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//�ٷ� ���ʿ� ������
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//�ٷ� �Ʒ��� ������
		}
		else if(My_row>=1&&My_row<=Height-2&&My_col==Width-1) {	//�� �����ʿ� �־ ���������� �̵� �Ұ����ϰ� �ٸ����� �� �����ϸ�
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//�ٷ� ���� ������
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//�ٷ� �Ʒ��� ������
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//�ٷ� ���ʿ� ������
		}
		else if(My_row==Height-1&&My_col>=1&&My_col<=Width-2) { //�� �Ʒ��ʿ� �־ �Ʒ������� �̵� �Ұ����ϰ� �ٸ����� �� �����ϸ�
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//�ٷ� ���� ������
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//�ٷ� ���ʿ� ������
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//�ٷ� �����ʿ� ������
		}
		else if(My_row==Height-1&&My_col==0) {		//���� �Ʒ� �����̿� ������
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//�ٷ� �����ʿ� ������
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//�ٷ� ���� ������
		}
		else if(My_row==Height-1&&My_col==Height-1) {	//������ �Ʒ� �����̿� ������
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//�ٷ� ���� ������
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//�ٷ� ���ʿ� ������
		}
		else if(My_row>=1&&My_row<=Height-2&&My_col==0) {	//�� ���ʿ� �־ �������� �̵� �Ұ��� �ϰ� �ٸ����� �� �����ϸ�
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//�ٷ� ���� ������
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//�ٷ� �Ʒ��� ������
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//�ٷ� �����ʿ� ������
		}
		//1ĭ�� ������ ����
		
		if(My_row>=2&&My_row<=Height-3&&My_col>=2&&My_col<=Width-3) {	//��� �������� 1ĭ�� �̵��ص� �迭 ũ�⿡ ���� �ȳ�ġ�� ��Ȳ�̸�
			//���� ������ ���� ������
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			//���� ������ ���� ������
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			//���� ������ ���� ������
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			//���� ������ ���� ������
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
		}
		else if(My_row>=0&&My_row<=1&&My_col>=2&&My_col<=Width-3) {	//���� ���� �̵��� ������ ������
			//���� ������ ���� ������
			if(My_row==1) {
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				//���� ������ ���� ������
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				//���� ������ ���� ������
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				//���� ������ ���� ������
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {	//My_row==0
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=0&&My_col<=1) { //���� ������ �κ�
			if(My_row==0) {
				if(My_col==0) {
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
				else {
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
			}
			else if(My_row==1) {
				if(My_col==0) {
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=Width-2&&My_col<=Width-1) {	//������ ������ �κ�
			if(My_row==0) {
				if(My_col==Width-2) {
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
				else {
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
			else {
				if(My_col==Width-2) {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col>=Width-2&&My_col<=Width-1) {	//���� ���� �̵��� ������ ������
			if(My_col==Width-2) {
			//���� ������ ���� ������
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			//���� ������ ���� ������
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			//���� ������ ���� ������
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			//���� ������ ���� ������
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {	//My_col==Width-1
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			}
		}
		
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=2&&My_col<=Width-3) {	//���� ���� �̵��� ������ ������
			if(My_row==Height-2) {
				//���� ������ ���� ������
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				//���� ������ ���� ������
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				//���� ������ ���� ������
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				//���� ������ ���� ������
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			}
		}
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=0&&My_col<=1) {		//���� �Ʒ� ������
			if(My_row==Height-1) {
				if(My_col==0) {
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
			}
			else {	//My_row==Height-2
				if(My_col==0) {
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=Width-2&&My_col<=Width-1) {	//������ �Ʒ� ������
			if(My_row==Height-1) {
				if(My_col==Width-2) {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				}
			}
			else {
				if(My_col==Width-2) {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col>=0&&My_col<=1) {	//���� ���� �̵��� ������ ������
			if(My_col==1) {
			//���� ������ ���� ������
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			//���� ������ ���� ������
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			//���� ������ ���� ������
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			//���� ������ ���� ������
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			}
		}
		//�þ� �ȿ��� ����� ���� �� �ľ��� ����. ���� �� �߿��� ��Ȳ�� ���� ���� ���� ���� �̵� �������� �����ؾ���
		int[] Zombies=new int[4];
		Zombies[0]=checkN;
		Zombies[1]=checkE;
		Zombies[2]=checkS;
		Zombies[3]=checkW;
		String[] Positions=new String[4];
		Positions[0]="N";
		Positions[1]="E";
		Positions[2]="S";
		Positions[3]="W";
		int tmpi;
		String tmps;
		for(int i=0;i<4;++i) {
			for(int j=i;j<4;++j) {
				if(Zombies[i]<Zombies[j]) {
					tmpi=Zombies[i];
					Zombies[i]=Zombies[j];
					Zombies[j]=tmpi;
					tmps=Positions[i];
					Positions[i]=Positions[j];
					Positions[j]=tmps;
				}
			}
		}
		//������� ���� �� ������� �պ��� �Էµ�
		if(checkN==0&&checkE==0&&checkS==0&&checkW==0) {	//4���⿡ �ƹ��� ���� ������.. �ִ��� ��������� �̵���Ű���� �Ҳ���
				if(My_row<Height/2&&My_col<Width/2) {		//2��и� ��ġ��
					if(My_row==0) state=GoDown;	//���� 0�̸� �Ʒ��� ��������
					else if(My_col==0) state=GoRight;	//���� 0�̸� ���������� ������
					else if(My_row>=My_col) {
						state=GoRight;
					}
					else {
						state=GoDown;
					}
				}
				else if(My_row<Height/2&&My_col>=Width/2) {	//1��и� ��ġ��
					if(My_row==0) state=GoDown;
					else if(My_col==Width-1) state=GoLeft;
					else if(My_row>=My_col-Width/2) {
						state=GoLeft;
					}
					else {
						state=GoDown;
					}
				}
				else if(My_row>=Height/2&&My_col<Width/2) {	//3��и� ��ġ��
					if(My_row==Height-1) state=GoUp;
					else if(My_col==0) state=GoRight;
					else if(Width/2-My_col>=My_row-Height/2) {
						state=GoRight;
					}
					else {
						state=GoUp;
					}
				}
				else {	//4��и� ��ġ��
					if(My_row==Height-1) state=GoUp;
					else if(My_col==Width-1) state=GoLeft;
					else if(My_row-Height/2>=My_col-Width/2) {
						state=GoUp;
					}
					else {
						state=GoLeft;
					}
				}
		}
		else {	//���� �þ߿� ��Ÿ����
			if(My_row>=1&&My_row<=Height-2&&My_col>=1&&My_col<=Width-2)	{	//�������� ���� �̵� �����ϸ�
				if(checkN!=0) {	//�������� ���� ������
					if(checkE==0&&checkS==0&&checkW==0) {	//���ʻ��� �� ���� ������
						if(My_row<=Height/2) state=GoDown; //���� �� ���̰� ���ݺ��� ������ �Ʒ��� �̵�
						else {
							if(My_col<=Width/2) state=GoRight;	//���� ���� ���ݺ��� ������ ������ �̵�
							else state=GoLeft;	
						}
					}
					else if(checkE==0&&checkS==0) {	//���� �������� ���� ������
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0&&checkW==0) {	//���� �������� �� �� ������
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					else if(checkW==0&&checkS==0) {	//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;		//���ʸ� ���� ������
					else if(checkS==0) state=GoDown;	//���ʸ� �� �� ������
					else if(checkW==0) state=GoLeft;	//���ʸ� �� �� ������
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkE!=0) {	//�������� ���� ������
					if(checkN==0&&checkS==0&&checkW==0) {	//���ʻ��� �� ���� ������
						if(My_col>=Height/2) state=GoLeft; //���� ���� ���ݺ��� ũ�� �������� �̵�
						else {
							if(My_row<=Height/2) state=GoDown;	//���� ���� ���ݺ��� ������ �Ʒ��� �̵�
							else state=GoUp;	
						}
					}
					else if(checkN==0&&checkS==0) {	//���� �������� ���� ������
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkN==0&&checkW==0) {	//���� �������� �� �� ������
						if(My_row>=My_col) state=GoUp;
						else state=GoLeft;
					}
					else if(checkW==0&&checkS==0) {	//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkN==0) state=GoUp;		//���ʸ� ���� ������
					else if(checkS==0) state=GoDown;	//���ʸ� �� �� ������
					else if(checkW==0) state=GoLeft;	//���ʸ� �� �� ������
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkS!=0) {	//�������� ���� ������
					if(checkE==0&&checkN==0&&checkW==0) {	//���ʻ��� �� ���� ������
						if(My_row>=Height/2) state=GoUp; //���� �� ���̰� ���ݺ��� ũ�� ���� �̵�
						else {
							if(My_col<=Width/2) state=GoRight;	//���� ���� ���ݺ��� ������ ������ �̵�
							else state=GoLeft;	
						}
					}
					else if(checkE==0&&checkN==0) {	//���� �������� ���� ������
						if(My_row>=My_col) state=GoUp;
						else state=GoRight;
					}
					else if(checkE==0&&checkW==0) {	//���� �������� �� �� ������
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					else if(checkW==0&&checkN==0) {	//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoUp;
					}
					else if(checkE==0) state=GoRight;		//���ʸ� ���� ������
					else if(checkN==0) state=GoUp;	//���ʸ� �� �� ������
					else if(checkW==0) state=GoLeft;	//���ʸ� �� �� ������
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkW!=0) {	//�������� ���� ������
					if(checkE==0&&checkS==0&&checkN==0) {	//���ʻ��� �� ���� ������
						if(My_col<=Width/2) state=GoRight; //���� ���� ���ݺ��� ������ ���������� �̵�
						else {
							if(My_row<=Height/2) state=GoDown;	//���� ���� ���ݺ��� ������ �Ʒ��� �̵�
							else state=GoUp;	
						}
					}
					else if(checkE==0&&checkS==0) {	//���� �������� ���� ������
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0&&checkN==0) {	//���� �������� �� �� ������
						if(My_col<=My_row) state=GoUp;
						else state=GoRight;
					}
					else if(checkN==0&&checkS==0) {	//���� �������� �� �� ������
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;		//���ʸ� ���� ������
					else if(checkS==0) state=GoDown;	//���ʸ� �� �� ������
					else if(checkN==0) state=GoUp;	//���ʸ� �� �� ������
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
			}
			else if(My_row==0&&My_col>=1&&My_col<=Width-2) {	//�� ����⿡ ������
				if(checkE!=0) {	//���ʿ� ���� ������
					if(checkW==0&&checkS==0) {		//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkS==0) state=GoDown;	//���ʸ� �� �� ������
					else if(checkW==0) state=GoLeft;	//���ʸ� �� �� ������
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkW!=0) {	//���ʿ� ���� ������
					if(checkE==0&&checkS==0) {	//���� �������� ���� ������
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;
					else if(checkS==0) state=GoDown;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else {	//���ʿ� ���� ������
					 if(checkE==0&&checkW==0) {	//���� �������� �� �� ������
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					 else if(checkE==0) state=GoRight;
					 else if(checkW==0) state=GoLeft;
					 else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
			}
			else if(My_row==0&&My_col==0) {	//���� ���� ������
				if(checkE!=0) {	//�������� ���� ������
					if(checkS==0) state=GoDown;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					}
				}
				else {	//�������� ���� ������
					if(checkE==0) state=GoRight;
					else {
						if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					}
				}
			}
			else if(My_row==0&&My_col==Width-1) {//�� ������ ������
				if(checkW!=0) {
					if(checkS==0) state=GoDown;
					else {
						if(Positions[0]=="W") state=GoLeft;
						else state=GoDown;
					}
				}
				else {
					if(checkW==0) state=GoLeft;
					else {
						if(Positions[0]=="W") state=GoLeft;
						else state=GoDown;
					}
				}
			}
			else if(My_row>=1&&My_row<=Height-2&&My_col==Width-1) {	//�� �����ʿ� ������
				if(checkN!=0) {	//���ʿ� ���� ������
					if(checkW==0&&checkS==0) {		//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkW==0) state=GoLeft;
					else if(checkS==0) state=GoDown;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}		
				}
				else if(checkW!=0) {	//���ʿ� ���� ������
					if(checkN==0&&checkS==0) {	//���� �������� �� �� ������
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkN==0) state=GoUp;
					else if(checkS==0) state=GoDown;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}		
				}
				else {	//���ʿ� ���� ������
					if(checkW==0&&checkN==0) {	//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoUp;
					}
					else if(checkW==0) state=GoLeft;
					else if(checkN==0) state=GoUp;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}	
				}
			}
			else if(My_row==Height-1&&My_col>=1&&My_col<=Width-2) {	//�� �Ʒ��ʿ� ������
				if(checkN!=0) {	//���ʿ� ���� ������
					if(checkE==0&&checkW==0) {	//���� �������� �� �� ������
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					else if(checkE==0) state=GoRight;
					else if(checkW==0) state=GoLeft;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoLeft;
					}	
				}
				else if(checkW!=0) {	//���ʿ� ���� ������
					if(checkE==0&&checkN==0) {	//���� �������� �� �� ������
						if(My_col<=My_row) state=GoUp;
						else state=GoRight;
					}
					else if(checkE==0) state=GoRight;
					else if(checkN==0) state=GoUp;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoLeft;
					}	
				}
				else {	//���ʿ� ���� ������
					 if(checkW==0&&checkN==0) {	//���� �������� �� �� ������
						if(My_col>=My_row) state=GoLeft;
						else state=GoUp;
					}
					 else if(checkN==0) state=GoUp;
					 else if(checkW==0) state=GoLeft;
					 else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoLeft;
					} 
				}
			}
			else if(My_row==Height-1&&My_col==0) {		//�Ʒ� ���� ������
				if(checkN!=0) {	//���ʿ� ���� ������
					if(checkE==0) state=GoRight;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoRight;
					}
				}
				else { //���ʿ� ���� ������
					if(checkN==0) state=GoUp;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoRight;
					}
				}
			}
			else if(My_row==Height-1&&My_col==Width-1) {		//�Ʒ� ������ ������
				if(checkN!=0) {	//���ʿ� ���� ������
					if(checkW==0) state=GoLeft;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoLeft;
					}
				}
				else { 	//���ʿ� ���� ������
					if(checkN==0) state=GoUp;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoLeft;
					}
				}
			}
			else if(My_row>=1&&My_row<=Height-2&&My_col==0) {	//�� ���ʿ� ������
				if(checkN!=0) {	//���ʿ� ���� ������
					if(checkE==0&&checkS==0) {	//���� �������� ���� ������
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;
					else if(checkS==0) state=GoDown;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					} 
				}
				else if(checkE!=0) {	//���ʿ� ���� ������
					if(checkN==0&&checkS==0) {	//���� �������� �� �� ������
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkN==0) state=GoUp;
					else if(checkS==0) state=GoDown;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					} 
				}
				else {	//���ʿ� ���� ������
					if(checkE==0&&checkN==0) {	//���� �������� �� �� ������
						if(My_col<=My_row) state=GoUp;
						else state=GoRight;
					}
					else if(checkE==0) state=GoRight;
					else if(checkN==0) state=GoUp;
					else {	//�� �� �� ������.. -> ���� ���� ���� ������ ����
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					}
				}
			}
		}
	}
	
	/**
	 * ���� �Ǿ��� �� ��Ȳ�� ���� �����̴� ���� ���Ұ�
	 */
	void Infected_UpdateState() {
		int ChooseCheck=0;
		int checkN=0,checkE=0,checkS=0,checkW=0,My_row=this.myInfo.position.row,My_col=this.myInfo.position.column,Height=Constants.Classroom_Height,Width=Constants.Classroom_Width;
		if(turnInfo.turnNumber<=25) {	//���� 25���ϸ� �⺻������ �ڻ��Ұ���
			state=Stay;	//�׳� 25�� �̳��� �����ȰŸ� �ڻ�
		}
		else {//���� 25���� ũ�� ������ �ٴϱ�
		if(My_row>=2&&My_row<=Height-3&&My_col>=2&&My_col<=Width-3) {	//���� ���µ� �ƹ��� ������ �ȵǴ� ����
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==1&&My_col>=2&&My_col<=Width-3) {	//���� �α� Ȯ�ο� ���� �ִ� ��
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_row-1;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-1;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==0&&My_col>=2&&My_col<=Width-3) {	//���� �α� Ȯ�ο� ���� �ִ� ��
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_row;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==Width-2) {	//�������� �̵� �����ִ� �� üũ
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
				
			for(int j=My_col-2;j<=My_col+1;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_col-2;j<=My_col+1;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==Width-1) {
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			for(int j=My_col-2;j<=My_col;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_col-2;j<=My_col;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==Height-2&&My_col>=2&&My_col<=Width-3) {	//�Ʒ��� üũ�� �����ִ� ���̸�
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			
			for(int j=My_col-2;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+1;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_row-2;j<=My_row+1;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==Height-1&&My_col>=2&&My_col<=Width-3) {	//�Ʒ��� üũ�� �����ִ� ���̸�
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_row-2;j<=My_row;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==1) {
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			
			for(int j=My_col-1;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-1;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==0) {
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_col;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=0&&My_col<=1) {	//���� �� ������ �κ�
			if(My_row==0) {
				if(My_col==0) {	//(0,0) �κ�
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(0,1)�κ�
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-1;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
			}
			else {
				if(My_col==0) {	//(1,0)�� �κ�
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(1,1)�� �κ�
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-1;j<=My_col+2;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}					
				}
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=Width-2&&My_col<=Width-1) {	//������ ������ �κ�
			if(My_row==0) {
				if(My_col==Width-2) {	//(0,Width-2)
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(0,Width-1)
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
			else {	//My_row==1
				if(My_col==Width-2) {	//(1,Width-2)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(1,Width-1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2ĭ �Ʒ����� ������ �����ڵ� ����ִ��� üũ
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
		}
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=Width-2&&My_col<=Width-1) {	//������ �Ʒ� ������
			if(My_row==Height-2) {	
				if(My_col==Width-2) {	//(Height-2,Width-2)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(Height-2,Width-1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
			else {	//My_row==Height-1
				if(My_col==Width-2) {	//(Height-1,Width-2)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(Height-1,Width-1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2ĭ ������ ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
		}
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=0&&My_col<=1) {		//���� �Ʒ� ������
			if(My_row==Height-2) {	
				if(My_col==0) {	//(Height-2,0)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(Height-2,1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //�ٷ� �Ʒ��ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7�� ���⿡ ������ ������
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5�� ���⿡ ������ ������
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					
					for(int j=My_col-1;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
			}
			else {	//My_row==Height-1
				if(My_col==0) {	//(Height-1,0)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(Height-1,1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //�ٷ� ���� ������ ������ üũ
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //�ٷ� �����ʿ� ������ ������ üũ
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //�ٷ� ���ʿ� ������ ������ üũ
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1�� ���⿡ ������ ������
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11�� ���⿡ ������ ������
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					for(int j=My_col-1;j<=My_col+2;++j) {	//2ĭ ���� ���ʿ��� �����ڵ� ����ִ��� üũ
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2ĭ �������� ������ �����ڵ� ����ִ��� üũ
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
			}
		}
		//Check�� ��Ȳ�� ���߾� ���� �� ���ѳ���.. ���� �̰� ���� �̵� ���� �����ϴ� �۾�
		int[] Survivors=new int[4];
		Survivors[0]=checkN;
		Survivors[1]=checkE;
		Survivors[2]=checkS;
		Survivors[3]=checkW;
		String[] Positions=new String[4];
		Positions[0]="N";
		Positions[1]="E";
		Positions[2]="S";
		Positions[3]="W";
		int tmpi;
		String tmps;
		for(int i=0;i<4;++i) {
			for(int j=i;j<4;++j) {
				if(Survivors[i]<Survivors[j]) {
					tmpi=Survivors[i];
					Survivors[i]=Survivors[j];
					Survivors[j]=tmpi;
					tmps=Positions[i];
					Positions[i]=Positions[j];
					Positions[j]=tmps;
				}
			}
		}
		//���� ū�� ������ ������ �����س���
		if(Survivors[0]==0) {	//�����ڰ� �ֺ��� �ƹ��� ������
			if(My_row<Height/2&&My_col<Width/2) {		//2��и� ��ġ��
				if(My_row<=My_col) state=GoRight;
				else state=GoDown;
			}
			else if(My_row<Height/2&&My_col>=Width/2) {	//1��и� ��ġ��
				if(My_row<=My_col-Width/2) {
					state=GoLeft;
				}
				else state=GoDown;
			}
			else if(My_row>=Height/2&&My_col<Width/2) {	//3��и� ��ġ��
				if(My_row-Height/2>=My_col) state=GoRight;
				else state=GoUp;
			}
			else {	//4��и� ��ġ��
				if(My_row<=My_col) state=GoLeft;
				else state=GoUp;
			}
		}
		else {
			switch(Positions[0]) {
			case "N":
				state=GoUp;
				break;
			case "E":
				state=GoRight;
				break;
			case "W":
				state=GoLeft;
				break;
			case "S":
				state=GoDown;
				break;
			}
		}
	}
	}


	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = DirectionCode.Right;
		
		Survive_UpdateState();
		
		switch(state) {
		case GoRight:
			result=DirectionCode.Right;
			break;
		case GoDown:
			result=DirectionCode.Down;
			break;
		case GoLeft:
			result=DirectionCode.Left;
			break;
		case GoUp:
			result=DirectionCode.Up;
			break;
		}
		
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = DirectionCode.Right;
		
		Infected_UpdateState();
		
		switch(state) {
		case GoRight:
			result=DirectionCode.Right;
			break;
		case GoDown:
			result=DirectionCode.Down;
			break;
		case GoLeft:
			result=DirectionCode.Left;
			break;
		case GoUp:
			result=DirectionCode.Up;
			break;
		case Stay:
			result=DirectionCode.Stay;
		}
		

		//TODO �⵵ �帱�� ���� �����ؾ���
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return result;
	}

	@Override
	public void Soul_Stay()
	{
	}

	@Override
	public Point Soul_Spawn()
	{
		int Height=Constants.Classroom_Height,Width=Constants.Classroom_Width;
		int result_row = 0;
		int result_col = 0;
		int ZombiesCount=0;
		int BestNumber=0, WorstNumber=9;
		if(turnInfo.turnNumber==0) {	//ù���̸� �� ������� �����ϵ��� ����
			result_row=Height/2;
			result_col=Width/2;
		}
		else {	//���߿� ��Ȱ�̸�
			for(int i=1;i<Height-1;++i) {	 //�ֺ� �˻��ؼ� ���� ���� ���� �ٱ۰Ÿ��� �� ã������
				for(int j=1;j<Width-1;++j) {
					if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(ZombiesCount>BestNumber) BestNumber=ZombiesCount;	//���� ���� ���� �� ����
					ZombiesCount=0;
				}
			}
			for(int i=1;i<Height-1;++i) {	 //�ֺ� �˻��ؼ� ���� ���� ���� �ٱ۰Ÿ��� �� ã������
				for(int j=1;j<Width-1;++j) {
					if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(ZombiesCount<WorstNumber) WorstNumber=ZombiesCount;	//���� ���� ���� �� ����
					ZombiesCount=0;
				}
			}
			if(BestNumber>=5) {	//�Ϻη� ���� ��ġ�� �ִٸ�
				for(int i=1;i<Height-1;++i) {
					for(int j=1;j<Width-1;++j) {
						if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(ZombiesCount==BestNumber) {		//���� �ֺ��� ���� ���� ���� �߰ߵǸ� �װ����� ������
							result_row=i;
							result_col=j;
							break;
						}
						ZombiesCount=0;
					}
				}
			}
			else {	//��ü ���� ���� ������ ���� �� ���Ƽ� �� ��ƾ߰ڴ� ������
				for(int i=1;i<Height-1;++i) {
					for(int j=1;j<Width-1;++j) {
						if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(ZombiesCount==WorstNumber) {		//���� �ֺ��� ���� ���� ���� �߰ߵǸ� �װ����� ������
							result_row=i;
							result_col=j;
							break;
						}
						ZombiesCount=0;
					}
				}
			}
		}
		state=GoRight;
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return new Point(result_row, result_col);
	}
}
