import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static int M, N, K;

    static int[][] ddang;

    static int[] dirX = {0, 0, -1, 1}; // X축(좌우)
    static int[] dirY = {-1, 1, 0, 0}; // Y축(상하)

    static int size;  // 현재 영역의 넓이

    static ArrayList<Integer> result; // 탐색된 영역 넓이

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken()); // y축
        N = Integer.parseInt(st.nextToken()); // x축
        K = Integer.parseInt(st.nextToken()); // 사각형 개수

        ddang = new int[M][N];
        result = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            int x1 = Integer.parseInt(st.nextToken()); // 왼쪽 아래 x
            int y1 = Integer.parseInt(st.nextToken()); // 왼쪽 아래 y
            int x2 = Integer.parseInt(st.nextToken()); // 오른쪽 위 x
            int y2 = Integer.parseInt(st.nextToken()); // 오른쪽 위 y

            // (x1, y1) ~ (x2, y2) 사각형을 1로 채우기
            // 0은 빈칸(탐색 가능), 1은 직사각형으로 채워진 영역(탐색 불가)
            for (int y = y1; y < y2; y++) {
                for (int x = x1; x < x2; x++) {
                    ddang[y][x] = 1;
                }
            }
        }

        // 전체 지도 탐색하여 0인 부분에서 DFS 실행 (새로운 영역 탐색 시작)
        for (int i = 0; i < M; i++) {      // y축 순회
            for (int j = 0; j < N; j++) {  // x축 순회
                if (ddang[i][j] == 0) {    // 아직 방문하지 않은 빈 공간이면
                    size = 1;             // 새 영역의 넓이 초기화
                    dfs(i, j);            // DFS 탐색 시작
                    result.add(size);     // 탐색이 끝난 영역의 넓이 저장
                }
            }
        }

        // 넓이 오름차순 정렬
        Collections.sort(result);

        StringBuilder sb = new StringBuilder();
        sb.append(result.size()).append("\n"); // 영역 개수
        for (int r : result) {
            sb.append(r).append(" "); // 각 영역의 넓이
        }

        System.out.println(sb);
    }

    // DFS 함수: (y, x) 좌표를 기준으로 연결된 0들을 탐색
    public static void dfs(int y, int x) {
        ddang[y][x] = 1; // 방문 처리 (1로 변경)

        // 4방향 탐색
        for (int i = 0; i < 4; i++) {
            int nx = dirX[i] + x; // 새 x 좌표
            int ny = dirY[i] + y; // 새 y 좌표

            // 새 좌표가 유효한 범위이고, 아직 방문하지 않은 곳이면
            if (nx >= 0 && ny >= 0 && nx < N && ny < M && ddang[ny][nx] == 0) {
                size++;             // 넓이 증가
                dfs(ny, nx);        // 재귀적으로 탐색 계속
            }
        }
    }
}
