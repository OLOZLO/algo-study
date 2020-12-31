package hash.prog42579;

import java.util.*;

public class Main_girawhale {
    public int[] solution(String[] genres, int[] plays) {
        int N = genres.length;
        Map<String, Integer> genreTimes = new HashMap<>();
        Map<String, PriorityQueue<int[]>> genreMap = new HashMap<>();

        for (int i = 0; i < N; i++) {
            genreTimes.put(genres[i], genreTimes.getOrDefault(genres[i], 0) + plays[i]);
            genreMap.computeIfAbsent(genres[i], k ->
                    new PriorityQueue<>((o1, o2) -> o1[1] != o2[1] ? Integer.compare(o2[1], o1[1]) : Integer.compare(o1[0], o2[0]))).add(new int[]{i, plays[i]});
        }

        List<Map.Entry<String, Integer>> list = new ArrayList<>(genreTimes.entrySet());
        Collections.sort(list, (o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));

        List<Integer> ans = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            int cnt = 0;
            PriorityQueue<int[]> que = genreMap.get(entry.getKey());

            while (!que.isEmpty() && cnt < 2) {
                ans.add(que.poll()[0]);
                cnt++;
            }
        }

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

}
