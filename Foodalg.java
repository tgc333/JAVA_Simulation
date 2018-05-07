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

	int arriveTime; // 가게 도착 시간
	int orderTime; // 음식 주문 시간
	int cookTime; // 주문한 음식 조리 시간
	String menu; // 음식 이름
}

class Foodsimu {
	Foodsimu() {
		Random r = new Random();
		Queue<customer> korres = new LinkedList<customer>();
		Queue<customer> wesres = new LinkedList<customer>();
		HashMap<String, Integer> h = new HashMap<String, Integer>();
		HashMap<String, Integer> ph = new HashMap<String, Integer>();
		Set<String> keys = h.keySet();
		Set<String> pkeys = ph.keySet(); // 메뉴 개수 출력을 위한 해시맵
		int businessTime = 540; // 영업하는 시간 (분)

		double wesaverageStayTime = 0, wespeakaverageStayTime = 0; // 대기 시간 평균
		double koraverageStayTime = 0, korpeakaverageStayTime = 0;
		double averageServeTime = 0, peakaverageServeTime = 0;
		double wesaverageServeTime = 0, wespeakaverageServeTime = 0; // 양식 조리까지 평균
		double koraverageServeTime = 0, korpeakaverageServeTime = 0; // 한식 평균

		int wescustomcount = 0, wespeakcustomcount = 0; // 양식 고객
		int korcustomcount = 0, korpeakcustomcount = 0; // 한식 고객
		int customcount = 0; // 총 고객의 수
		int leavecustomcount = 0;
		int wesprofit = 0, wespeakprofit = 0; // 양식 이익
		int korprofit = 0, korpeakprofit = 0; // 한식 이익
		int weslongestStay = 0, wespeaklongestStay = 0; // 양식 최장 대기 시간
		int korlongestStay = 0, korpeaklongestStay = 0; // 한식 최장 대기 시간
		int profit = 0; // 총 이익
		int cost = 0; // 원가
		int prob; // 손님 도착 확률
		int peakflag;

		for (int time = 0; time < businessTime; time++) {
			if ((time > 60 && time < 180) || (time > 420 && time < 540)) {
				prob = r.nextInt(2); // 고객 도착 확률
				peakflag = 1;
			} else {
				prob = r.nextInt(5);
				peakflag = 0;
			}

			if (prob == 0) { // 고객 도착
				customer c = new customer(time);
				if (c.f.foodnum <= 5) { // 한식
					if (!korres.isEmpty() && korres.peek().arriveTime + 15 < time) {
						leavecustomcount++;
						System.out.println("	" + (time / 60 + 11) + "시 " + time % 60 + "분에 손님이 떠났습니다.");
					} else {
						if (korres.isEmpty()) { // 대기 손님이 없을 때
							korres.offer(c);
							korres.peek().orderTime = time;
						} else {
							korres.offer(c);
						}
					}
				} else {
					if (!wesres.isEmpty() && wesres.peek().arriveTime + 15 < time) {
						leavecustomcount++;
						System.out.println("	" + (time / 60 + 11) + "시 " + time % 60 + "분에 손님이 떠났습니다.");
					} else {
						if (wesres.isEmpty()) { // 대기 손님이 없을 때
							wesres.offer(c);
							wesres.peek().orderTime = time;
						} else {
							wesres.offer(c);
						}
					}
				}
			}

			if (!korres.isEmpty()) { // 대기 손님이 있을 때
				if ((korres.peek().cookTime + korres.peek().orderTime) <= time) { // 주문시간+조리시간 <= 지금 시간
					if (peakflag == 1) { // 피크 타임
						korpeakaverageServeTime += time - korres.peek().arriveTime;
						korpeakaverageStayTime += korres.peek().orderTime - korres.peek().arriveTime;
						if (korpeaklongestStay < time - korres.peek().arriveTime)
							korpeaklongestStay = time - korres.peek().arriveTime; // 최장 대기시간
						korpeakprofit += korres.peek().f.food.price; // 이익 증가
						if (ph.containsKey(korres.peek().menu)) // 메뉴 개수
							ph.put(korres.peek().menu, ph.get(korres.peek().menu) + 1);
						else
							ph.put(korres.peek().menu, 1);
						korpeakcustomcount++; // 고객 수 증가
					} else { // 피크 타임 아닐때
						koraverageServeTime += time - korres.peek().arriveTime;
						koraverageStayTime += korres.peek().orderTime - korres.peek().arriveTime;
						if (korlongestStay < time - korres.peek().arriveTime)
							korlongestStay = time - korres.peek().arriveTime; // 최장 대기시간
						korprofit += korres.peek().f.food.price; // 이익 증가

						if (h.containsKey(korres.peek().menu)) // 메뉴 개수
							h.put(korres.peek().menu, h.get(korres.peek().menu) + 1);
						else
							h.put(korres.peek().menu, 1);
						korcustomcount++; // 고객 수 증가
					}
					cost += korres.peek().f.food.cost; // 원가 계산
					korres.poll(); // 손님한테 음식 제공
					if (!korres.isEmpty())
						korres.peek().orderTime = time; // 다음 손님의 주문 시간 = 지금 시간
				}
			}

			if (!wesres.isEmpty()) { // 대기 손님이 있을 때
				if ((wesres.peek().cookTime + wesres.peek().orderTime) <= time) { // 주문시간+조리시간 <= 지금 시간
					if (peakflag == 1) { // 피크 타임
						wespeakaverageServeTime += time - wesres.peek().arriveTime;
						wespeakaverageStayTime += wesres.peek().orderTime - wesres.peek().arriveTime;
						if (wespeaklongestStay < time - wesres.peek().arriveTime)
							wespeaklongestStay = time - wesres.peek().arriveTime; // 최장 대기시간
						wespeakprofit += wesres.peek().f.food.price; // 이익 증가
						if (ph.containsKey(wesres.peek().menu)) // 메뉴 개수
							ph.put(wesres.peek().menu, ph.get(wesres.peek().menu) + 1);
						else
							ph.put(wesres.peek().menu, 1);
						wespeakcustomcount++; // 고객 수 증가
					} else { // 피크 타임 아닐때
						wesaverageServeTime += time - wesres.peek().arriveTime;
						wesaverageStayTime += wesres.peek().orderTime - wesres.peek().arriveTime;
						if (weslongestStay < time - wesres.peek().arriveTime)
							weslongestStay = time - wesres.peek().arriveTime; // 최장 대기시간
						wesprofit += wesres.peek().f.food.price; // 이익 증가

						if (h.containsKey(wesres.peek().menu)) // 메뉴 개수
							h.put(wesres.peek().menu, h.get(wesres.peek().menu) + 1);
						else
							h.put(wesres.peek().menu, 1);
						wescustomcount++; // 고객 수 증가
					}
					cost += wesres.peek().f.food.cost; // 원가 계산
					wesres.poll(); // 손님한테 음식 제공
					if (!wesres.isEmpty())
						wesres.peek().orderTime = time; // 다음 손님의 주문 시간 = 지금 시간
				}
			}
		}

		averageServeTime = wesaverageServeTime + koraverageServeTime + wespeakaverageServeTime
				+ korpeakaverageServeTime;
		customcount = wescustomcount + korcustomcount + wespeakcustomcount + korpeakcustomcount;
		profit = wesprofit + korprofit + wespeakprofit + korpeakprofit;
		System.out.println();
		System.out.println("	----시뮬레이션 결과----");
		System.out.println("	총 고객 수 : " + customcount);
		System.out.println("	매출액 : " + profit);
		System.out.println("	순이익 : " + (profit - cost));
		System.out.printf("	평균 서비스 시간 : %.2f\n", averageServeTime / customcount);
		System.out.println("	떠난 고객 수 : " + leavecustomcount);
		System.out.println();
		System.out.println("	----평시----");
		System.out.println("	양식 고객 수 : " + wescustomcount);
		System.out.println("	양식 매출액 : " + wesprofit);
		System.out.printf("	평균 서비스 시간 : %.2f\n", wesaverageServeTime / wescustomcount);
		System.out.printf("	평균 대기 시간 : %.2f\n", wesaverageStayTime / wescustomcount);
		System.out.println("	양식 최장 대기시간 : " + weslongestStay + "분");
		System.out.println();
		System.out.println("	한식 고객 수 : " + korcustomcount);
		System.out.println("	한식 매출액 : " + korprofit);
		System.out.printf("	평균 서비스 시간 : %.2f\n", koraverageServeTime / korcustomcount);
		System.out.printf("	평균 대기 시간 : %.2f\n", koraverageStayTime / korcustomcount);
		System.out.println("	한식 최장 대기시간 : " + korlongestStay + "분");
		System.out.println();
		System.out.println("	----각 메뉴 판매 수----");
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			System.out.println("	" + key + " : " + h.get(key) + "개");
		}
		System.out.println();
		System.out.println();
		System.out.println("	----피크----");
		System.out.println("	양식 고객 수 : " + wespeakcustomcount);
		System.out.println("	양식 매출액 : " + wespeakprofit);
		System.out.printf("	평균 서비스 시간 : %.2f\n", wespeakaverageServeTime / wespeakcustomcount);
		System.out.printf("	평균 대기 시간 : %.2f\n", wespeakaverageStayTime / wespeakcustomcount);
		System.out.println("	양식 최장 대기시간 : " + wespeaklongestStay + "분");
		System.out.println();
		System.out.println("	한식 고객 수 : " + korpeakcustomcount);
		System.out.println("	한식 매출액 : " + korpeakprofit);
		System.out.printf("	평균 서비스 시간 : %.2f\n", korpeakaverageServeTime / korpeakcustomcount);
		System.out.printf("	평균 대기 시간 : %.2f\n", korpeakaverageStayTime / korpeakcustomcount);
		System.out.println("	한식 최장 대기시간 : " + korpeaklongestStay + "분");
		System.out.println();
		System.out.println("	----각 메뉴 판매 수----");
		for (Iterator it = pkeys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			System.out.println("	" + key + " : " + ph.get(key) + "개");
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