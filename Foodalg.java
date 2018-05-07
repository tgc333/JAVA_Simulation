package Software;

import java.util.*;

class customer {
	Random r = new Random();
	Foodlist f = new Foodlist();

	customer() {
	};

	customer(int arvt) {
		arriveTime = arvt;
		cookTime = f.food.CookTime;
		menu = f.food.name;
	}

	int arriveTime; // ���� ���� �ð�
	int orderTime; // ���� �ֹ� �ð�
	int cookTime; // �ֹ��� ���� ���� �ð�
	String menu; // ���� �̸�
}

class Foodsimu {
	Foodsimu() {
		Random r = new Random();
		Queue<customer> korres = new LinkedList<customer>();
		Queue<customer> wesres = new LinkedList<customer>();
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		HashMap<String, Integer> ph = new HashMap<String, Integer>();
		Set<String> keys = h.keySet();
		Set<String> pkeys = ph.keySet(); // �޴� ���� ����� ���� �ؽø�
		int businessTime = 540; // �����ϴ� �ð� (��)

		double wesaverageStayTime = 0, wespeakaverageStayTime = 0; // ��� �ð� ���
		double koraverageStayTime = 0, korpeakaverageStayTime = 0;
		double averageServeTime = 0, peakaverageServeTime = 0;
		double wesaverageServeTime = 0, wespeakaverageServeTime = 0; // ��� �������� ���
		double koraverageServeTime = 0, korpeakaverageServeTime = 0; // �ѽ� ���

		int wescustomcount = 0, wespeakcustomcount = 0; // ��� ��
		int korcustomcount = 0, korpeakcustomcount = 0; // �ѽ� ��
		int customcount = 0; // �� ���� ��
		int leavecustomcount = 0;
		int wesprofit = 0, wespeakprofit = 0; // ��� ����
		int korprofit = 0, korpeakprofit = 0; // �ѽ� ����
		int weslongestStay = 0, wespeaklongestStay = 0; // ��� ���� ��� �ð�
		int korlongestStay = 0, korpeaklongestStay = 0; // �ѽ� ���� ��� �ð�
		int profit = 0; // �� ����
		int cost = 0; // ����
		int prob; // �մ� ���� Ȯ��
		int peakflag;

		for (int time = 0; time < businessTime; time++) {
			if ((time > 60 && time < 180) || (time > 420 && time < 540)) {
				prob = r.nextInt(2); // �� ���� Ȯ��
				peakflag = 1;
			} else {
				prob = r.nextInt(5);
				peakflag = 0;
			}

			if (prob == 0) { // �� ����
				customer c = new customer(time);
				if (c.f.foodnum <= 5) { // �ѽ�
					if (!korres.isEmpty() && korres.peek().arriveTime + 15 < time) {
						leavecustomcount++;
						System.out.println("	" + (time / 60 + 11) + "�� " + time % 60 + "�п� �մ��� �������ϴ�.");
					} else {
						if (korres.isEmpty()) { // ��� �մ��� ���� ��
							korres.offer(c);
							korres.peek().orderTime = time;
						} else {
							korres.offer(c);
						}
					}
				} else {
					if (!wesres.isEmpty() && wesres.peek().arriveTime + 15 < time) {
						leavecustomcount++;
						System.out.println("	" + (time / 60 + 11) + "�� " + time % 60 + "�п� �մ��� �������ϴ�.");
					} else {
						if (wesres.isEmpty()) { // ��� �մ��� ���� ��
							wesres.offer(c);
							wesres.peek().orderTime = time;
						} else {
							wesres.offer(c);
						}
					}
				}
			}

			if (!korres.isEmpty()) { // ��� �մ��� ���� ��
				if ((korres.peek().cookTime + korres.peek().orderTime) <= time) { // �ֹ��ð�+�����ð� <= ���� �ð�
					if (peakflag == 1) { // ��ũ Ÿ��
						korpeakaverageServeTime += time - korres.peek().arriveTime;
						korpeakaverageStayTime += korres.peek().orderTime - korres.peek().arriveTime;
						if (korpeaklongestStay < time - korres.peek().arriveTime)
							korpeaklongestStay = time - korres.peek().arriveTime; // ���� ���ð�
						korpeakprofit += korres.peek().f.food.price; // ���� ����
						if (ph.containsKey(korres.peek().menu)) // �޴� ����
							ph.put(korres.peek().menu, ph.get(korres.peek().menu) + 1);
						else
							ph.put(korres.peek().menu, 1);
						korpeakcustomcount++; // �� �� ����
					} else { // ��ũ Ÿ�� �ƴҶ�
						koraverageServeTime += time - korres.peek().arriveTime;
						koraverageStayTime += korres.peek().orderTime - korres.peek().arriveTime;
						if (korlongestStay < time - korres.peek().arriveTime)
							korlongestStay = time - korres.peek().arriveTime; // ���� ���ð�
						korprofit += korres.peek().f.food.price; // ���� ����

						if (h.containsKey(korres.peek().menu)) // �޴� ����
							h.put(korres.peek().menu, h.get(korres.peek().menu) + 1);
						else
							h.put(korres.peek().menu, 1);
						korcustomcount++; // �� �� ����
					}
					cost += korres.peek().f.food.cost; // ���� ���
					korres.poll(); // �մ����� ���� ����
					if (!korres.isEmpty())
						korres.peek().orderTime = time; // ���� �մ��� �ֹ� �ð� = ���� �ð�
				}
			}

			if (!wesres.isEmpty()) { // ��� �մ��� ���� ��
				if ((wesres.peek().cookTime + wesres.peek().orderTime) <= time) { // �ֹ��ð�+�����ð� <= ���� �ð�
					if (peakflag == 1) { // ��ũ Ÿ��
						wespeakaverageServeTime += time - wesres.peek().arriveTime;
						wespeakaverageStayTime += wesres.peek().orderTime - wesres.peek().arriveTime;
						if (wespeaklongestStay < time - wesres.peek().arriveTime)
							wespeaklongestStay = time - wesres.peek().arriveTime; // ���� ���ð�
						wespeakprofit += wesres.peek().f.food.price; // ���� ����
						if (ph.containsKey(wesres.peek().menu)) // �޴� ����
							ph.put(wesres.peek().menu, ph.get(wesres.peek().menu) + 1);
						else
							ph.put(wesres.peek().menu, 1);
						wespeakcustomcount++; // �� �� ����
					} else { // ��ũ Ÿ�� �ƴҶ�
						wesaverageServeTime += time - wesres.peek().arriveTime;
						wesaverageStayTime += wesres.peek().orderTime - wesres.peek().arriveTime;
						if (weslongestStay < time - wesres.peek().arriveTime)
							weslongestStay = time - wesres.peek().arriveTime; // ���� ���ð�
						wesprofit += wesres.peek().f.food.price; // ���� ����

						if (h.containsKey(wesres.peek().menu)) // �޴� ����
							h.put(wesres.peek().menu, h.get(wesres.peek().menu) + 1);
						else
							h.put(wesres.peek().menu, 1);
						wescustomcount++; // �� �� ����
					}
					cost += wesres.peek().f.food.cost; // ���� ���
					wesres.poll(); // �մ����� ���� ����
					if (!wesres.isEmpty())
						wesres.peek().orderTime = time; // ���� �մ��� �ֹ� �ð� = ���� �ð�
				}
			}
		}

		averageServeTime = wesaverageServeTime + koraverageServeTime + wespeakaverageServeTime
				+ korpeakaverageServeTime;
		customcount = wescustomcount + korcustomcount + wespeakcustomcount + korpeakcustomcount;
		profit = wesprofit + korprofit + wespeakprofit + korpeakprofit;
		System.out.println();
		System.out.println("	----�ùķ��̼� ���----");
		System.out.println("	�� �� �� : " + customcount);
		System.out.println("	����� : " + profit);
		System.out.println("	������ : " + (profit - cost));
		System.out.printf("	��� ���� �ð� : %.2f\n", averageServeTime / customcount);
		System.out.println("	���� �� �� : " + leavecustomcount);
		System.out.println();
		System.out.println("	----���----");
		System.out.println("	��� �� �� : " + wescustomcount);
		System.out.println("	��� ����� : " + wesprofit);
		System.out.printf("	��� ���� �ð� : %.2f\n", wesaverageServeTime / wescustomcount);
		System.out.printf("	��� ��� �ð� : %.2f\n", wesaverageStayTime / wescustomcount);
		System.out.println("	��� ���� ���ð� : " + weslongestStay + "��");
		System.out.println();
		System.out.println("	�ѽ� �� �� : " + korcustomcount);
		System.out.println("	�ѽ� ����� : " + korprofit);
		System.out.printf("	��� ���� �ð� : %.2f\n", koraverageServeTime / korcustomcount);
		System.out.printf("	��� ��� �ð� : %.2f\n", koraverageStayTime / korcustomcount);
		System.out.println("	�ѽ� ���� ���ð� : " + korlongestStay + "��");
		System.out.println();
		System.out.println("	----�� �޴� �Ǹ� ��----");
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			System.out.println("	" + key + " : " + h.get(key) + "��");
		}
		System.out.println();
		System.out.println();
		System.out.println("	----��ũ----");
		System.out.println("	��� �� �� : " + wespeakcustomcount);
		System.out.println("	��� ����� : " + wespeakprofit);
		System.out.printf("	��� ���� �ð� : %.2f\n", wespeakaverageServeTime / wespeakcustomcount);
		System.out.printf("	��� ��� �ð� : %.2f\n", wespeakaverageStayTime / wespeakcustomcount);
		System.out.println("	��� ���� ���ð� : " + wespeaklongestStay + "��");
		System.out.println();
		System.out.println("	�ѽ� �� �� : " + korpeakcustomcount);
		System.out.println("	�ѽ� ����� : " + korpeakprofit);
		System.out.printf("	��� ���� �ð� : %.2f\n", korpeakaverageServeTime / korpeakcustomcount);
		System.out.printf("	��� ��� �ð� : %.2f\n", korpeakaverageStayTime / korpeakcustomcount);
		System.out.println("	�ѽ� ���� ���ð� : " + korpeaklongestStay + "��");
		System.out.println();
		System.out.println("	----�� �޴� �Ǹ� ��----");
		for (Iterator it = pkeys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			System.out.println("	" + key + " : " + ph.get(key) + "��");
		}
	}
}

public class Foodalg {
	public static void main(String[] args) {
		Foodsimu f;
		for (int day = 0; day < 5; day++)
			f = new Foodsimu();
	}
}