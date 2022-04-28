import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class FordFulkerson {
    private static int size = 702;
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        int Capacity[][] = new int[size][size]; //간선의 용량과 유량을 저장할 배열을 생성한다.
        for(int i = 0 ; i < n ; i ++) {
            st = new StringTokenizer(br.readLine());
            int s = st.nextToken().charAt(0) -'A';
            int e = st.nextToken().charAt(0) -'A';
            int f = Integer.parseInt(st.nextToken());
            Capacity[s][e] += f;
            Capacity[e][s] += f;
        }


        System.out.println(networkflow('A'-'A','Z'-'A',Capacity));
    }

    private static int networkflow(int start, int end, int[][] capacity) {

        int size = capacity.length;

        int flow[][] = new int[size][size];

        ArrayDeque<Integer> dq = new ArrayDeque<>();
        int res = 0;
        while(true) {
            dq.clear();
            int parent[] = new int[size];
            for(int i = 0 ; i < size ; i++) {parent[i] = -1;}
            dq.add(start); parent[start] = start;
            while(!dq.isEmpty() && parent[end] == -1) {
                int now = dq.poll();
                for(int i = 0 ; i < size ; i++) {
                    if(capacity[now][i]-flow[now][i]>0 && parent[i]== -1) {

                        dq.add(i);
                        parent[i] = now;
                    }
                }
            }


            if(parent[end]==-1) {
                break;

            }


            int flowamount = 2147000000;

            for(int s = end ; s != start ; s = parent[s]) {

                flowamount = Math.min(flowamount, capacity[parent[s]][s] - flow[parent[s]][s]);
            }

            for(int s = end ; s!= start ; s = parent[s]) {

                flow[parent[s]][s] +=flowamount;
                flow[s][parent[s]] -=flowamount;
            }
            res+=flowamount;

        }
        return res;

    }
}