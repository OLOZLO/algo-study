package _210223.prog42893;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main_jyj {

	public int solution(String word, String[] pages) {
		int answer = 0;

		// 검색어 글자수
		int wsize = word.length();

		// url에 해당하는 인덱스를 쉽게 찾기 위해서 key = url, value = 인덱스로 하는 HashMap 생성
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		// 정렬을 통해 가장 매칭 점수가 높은 페이지를 찾기위해 list로 객체 Page 저장
		List<Page> pageList = new ArrayList<Page>();

		word = word.toLowerCase();

		for (int i = 0; i < pages.length; i++) {
			String str = pages[i] = pages[i].toLowerCase();
			// URL을 구해보자
			int mid = 0;
			int positionLeft = 0;
			int positionRight = 0;

			// url에 메타 태그안에 있다면 false 없으면 true
			// meta태그안에 url이 있는 경우까지 while문 실행
			while (mid <= positionLeft) {
				// 메타의 위치를 찾아서 반환 , 두번째 파라미터가 검색을 시작하는 위치
				// 하나씩 증가 시켜서 앞으로 진행할 수 있게 한다.
				positionLeft = str.indexOf("<meta", positionLeft + 1);

				// meta 태그의 끝을 positionRihgt에 저장
				positionRight = str.indexOf(">", positionLeft);

				// url을 뒤에서 부터 찾아보자
				mid = str.lastIndexOf("https://", positionRight);
			}

			// url을 추출해보자
			// url의 마지막 위치는 큰따음표를 만날때 까지
			positionRight = str.indexOf("\"", mid);
			String url = str.substring(mid, positionRight);

			// 검색어를 찾아서 카운팅을 해보자
			positionLeft = str.indexOf("<body>", positionRight);

			// 기본점수
			int basic = 0;
			for (int start = positionLeft;;) {
				start = str.indexOf(word, start + 1);

				// 만약에 찾지 못했다면
				if (start == -1)
					break;
				// 만약 찾았다면
				if (!Character.isLetter(str.charAt(start - 1)) && !Character.isLetter(str.charAt(start + wsize))) {
					basic++;
					start += wsize;
				}

			}

			// 외부 링크 수
			int link = 0;
			for (int start = positionLeft;;) {
				start = str.indexOf("<a href", start + 1);
				if (start == -1)
					break;
				link++;
			}

			pageMap.put(url, i);
			// 기본 점수를 일단 넣어두고 나중에 연결된 링크에 따라 링크 점수를 더해주자
			pageList.add(new Page(i, basic, link, (double) basic));

		}

		// 링크 점수를 구해보자

		for (int j = 0; j < pages.length; j++) {
			String str = pages[j];

			for (int positionLeft = 0, positionRight = 0;;) {
				positionLeft = str.indexOf("<a href", positionRight);
				if (positionLeft == -1)
					break;
				// 하이퍼 링크의 url을 추출해보자
				positionLeft = str.indexOf("https://", positionLeft);
				positionRight = str.indexOf("\"", positionLeft);
				String linkUrl = str.substring(positionLeft, positionRight);

				// 해당하는 페이지에 링크 점수를 더해주자
				// 링크 url과 같은 url을 찾아 인덱스 반환
				Integer value = pageMap.get(linkUrl);

				if (value != null) {
					// 매칭점수를 갱신해주자
					pageList.get(value).score += (double) pageList.get(j).basic / pageList.get(j).link;
				}

			}
		}

		pageList.sort(new Comparator<Page>() {

			@Override
			public int compare(Page o1, Page o2) {
				if (o1.score == o2.score) {
					return o1.idx - o2.idx;
				} else if (o1.score < o2.score)
					return 1;
				return -1;
			}
		});

		answer = pageList.get(0).idx;

		return answer;
	}

	public class Page {
		// 인덱스
		int idx;
		// 기본점수 , 외부 링크 수
		int basic, link;
		// 매칭점수
		double score;

		public Page(int idx, int basic, int link, double score) {
			this.idx = idx;
			this.basic = basic;
			this.link = link;
			this.score = score;
		}
	}
}
