# :computer: Ford-Fulkerson(Network Flow)






---
---
## :page_with_curl: ordered
### 1.  Network Flow 
●개념<br>
●용어<br>
●조건<br>
●에드몬드 카프(Edmonds-Karp)<br>
●포드 풀커슨(Ford-Fulkerson)

### 2. Ford-Fulkerson Algorithm
●구현 방법<br>
●실제 구현 with JAVA<br>
●단점 / 최악의 상황<br>
●성능 분석<br>




<br><br>

# :one: Network Flow란?
**Network Flow**란 어느 한 시작지점에서 다른 지점까지 모든 경로를 탐색하며 유체가 얼마나 많이 흐르고 있는가를 측정할 수 있는 알고리즘이다.<br>
즉 , 특정한 지점에서 다른 지점으로, 얼마나 많은 유량(flow)을 동시에 보낼 수 있는지 계산하는 것이다. <br>
이러한 방법은 도로망의 교통 흐름,교통 체증을 분석하거나 전자 회로의 전류,배수관의 흐르는 유체 등을 조사할때 많이 사용이 된다.<br>
이것을 우리는 '최대 유량 문제(Max Flow)' 라고도 하는데, 이 Network Flow는 최적의 답을 구하는데 있어서 <br> 
**에드몬드 카프(Edmonds-Karp)** 과 **포드 풀커슨(Ford-Fulkerson)** 이라는 알고리즘을 주로 사용하는데 , 이 보고서에서는 <br>
**포드 풀커슨(Ford-Fulkerson)** 알고리즘을 주로 다룰 것이고 , 이것을 설명하기 위해선 Network Flow가 무엇인지에 대해 다뤄야 할 필요가 있다.
<br><br><br>
 이제 간단한 그림을 통해 Network Flow의 개념에 대해서 좀 더 쉽고 자세히 설명하겠다.
![캡처](https://user-images.githubusercontent.com/101388379/165601597-e1ad1587-f9d1-4859-b127-745af03f4aa8.PNG)
<br><br>

---
**●용어** <br>
먼저 용어에 대해 먼저 설명하려고 한다. 위 그림은 아주 간단한 Network Flow 문제이다.
이것은 A와B를 거쳐 논밭에 물을 공급하는 배수시설을 간단한 그림으로 나타낸 것이다.
우리는 Network Flow와 Ford-Fulkerson 을 알아보며 이러한 형태의 그림을 많이 보게 될텐데 ,
그림에서 처음에 모든 물을 공급해주는 지점을 우리는 **소스(Source)** 라고 부르고,
공급해 주는 지점이 있으니 모든 물을 공급받는 지점도 있을 것이다. 이 지점을 우리는 **싱크(Sink)** 라고 부른다.
우리가 최대 유량을 구하기 위해서는 경로마다 물을 최대로 담을 수 있는 양을 알아야 하는데 , 우리는 이런 간선의 최대치를 **용량(Capacity)** ,
그리고 흐르고 있는 실제 양을 **유량(flow)** 이라고 부른다. 그림에서는 '공급원 → A → B → 논밭' , 이러한 경로로 물이 흐르는데 ,
이렇게 소스에서 싱크로 유량을 보낼 수 있는 경로를 **증가경로** 라고 한다. 
그림에서 보면 각 간선들 마다 0/3 , 0/2 , 0/3 과 같은 표시가 있는것을 볼 수 있는데 , 왼쪽 숫자는 흐르고있는 유량의 양을 나타내고
오른쪽 숫자는 그 간선에 흐를 수 있는 최대유량을 나타내는 것이다. 즉 0/3 은 그 간선에서 실제 흐르고 있는 유량 0 , 최대 흐를 수 있는 유량 3 이라고 해석 하면 된다.


설명한 용어들을 표로 정리해보았다. 
|용어|뜻|
|---|---|
|**용량(Capacity)**|c(u, v) : 정점 u 에서 v 로 전송할 수 있는 최대 용량|
|**유량(Flow)**|f(u, v) : 정점 u 에서 v 로 실제 흐르고 있는 유량|
|**소스(Source)**|s : 모든 유량이 시작되는 정점|
|**싱크(Sink)**|t : 모든 유량이 도착하는 정점|
|**증가경로**|소스에서 싱크로 유량을 보낼 수 있는 경로|

---
그렇다면 이제 위 그림에서는 얼마의 유량을 보낼 수 있을지 한번 알아보도록 하자. <br>
소스 → A 의 간선은 용량 3을 가지고 있다. 그렇므로 3만큼 **유체를 흘려보도록 하자.**
![11](https://user-images.githubusercontent.com/101388379/165598113-b99b4215-ae87-4d2c-be68-57c745c3dc6f.PNG)

유체를 3만큼 흘려보았을때 , 첫번째 간선은 무사히 통과하지만 A에서 B로 가는 두번째 간선은 통과하지 못한다. 
A → B 간선의 용량이 2이기 때문에 , 3만큼의 유체가 흘러 들어 갈 수 없기 때문이다. 
다른 증가경로가 존재하지 않기 때문에 A → B 경로를 통과하지 않으면 논밭으로 물이 공급 될 수 없다.
그렇기 때문에 3개의 간선 중 최소 용량을 가지고 있는 A → B 두번째 간선의 용량 만큼만 유체를 공급해야 할 것이다.
최소 용량을 가진 간선을 통과한다면 다른 모든 간선들은 당연히 통과가 될 것이기 때문이다.

**그렇다면 다시 2만큼의 유체를 흘려준다면?**

![22](https://user-images.githubusercontent.com/101388379/165601567-274646cb-c467-45a1-9f4a-76e6f1e02315.PNG)

유체가 모든 간선들을 통과하면서 무사히 논밭에 물이 공급되었다. 이것으로 최대 2만큼의 유체가 흐를 수 있는것을 알 수 있고,
소스에서 싱크까지 유체가 도달할 때, **최대 유체의 양은 결국 증가경로의 간선들 중 , 최소값을 가진 간선의 용량만큼만 흘려줘야 하는 것을 알 수 있다.**
위 그림은 증가경로도 하나뿐이고 , 이해를 위해 사용한 너무 간단하고 쉬운 Network Flow 였다. 이제 복잡하고 증가경로도 다양한 경우들에 대해서 알아보도록 하자.

---

**●조건** <br>
들어가기 앞서 Network Flow가 성립하기위한 몇가지 조건을 알아보고 들어가야 한다. 위에 용어표에서 보았듯이 <br>
**" c(u, v) : 정점 u 에서 v 로 전송할 수 있는 최대 용량 / f(u, v) : 정점 u 에서 v 로 실제 흐르고 있는 유량 "** 이라고 할때 , <br>
앞으로 나올 3가지의 조건을 만족해야 한다. <br><br>
첫번째는 **'용량의 제한'** 이다. 각 간선에 흐르는 유량은 그 간선의 용량을 넘을 수 없다는 조건이다.
위 그림에서 우리가 3의 유량을 흘렸을때 , 2의 용량을 가진 두번째 간선을 통과하지 못했기 때문에 물이 공급 되지 않았다. 이것으로 우리는 첫번째 조건에 대해
쉽게 알고 넘어갔다.<br><br>
두번째 조건은 **'유량의 보존'** 이다. 쉽게 말해 결국 소스에서부터 공급된 유량의 양이 싱크에서 공급받은 유량의 양과 같다는 말이다.
회로에서도 KCL의 법칙 , KVL의 법칙 , 텔레진의 법칙 등 나가는 양과 들어오는 양의 값이 같아야한다는 다양한 법칙이 있다. 이것은 Network Flow 에서도 마찬가지이다.<br><br>
세번째 조건은 **'유량의 대칭'** 이다. 이것이 Network Flow , 우리가 앞으로 중요하게 다룰 포드 풀커슨 알고리즘(Ford-Fulkerson Algorithm)의 핵심 아이디어 이다.<br>
a → b 간선에서 유량이 10만큼 흐른다면 , 이것을 우리는 b → a라는 가상의 간선에서 -10만큼의 유량이 흐른다고 생각하는 것이다. b → a 간선의 용량은 0이라고 가정한다.
이것이 되는 이유는 , 실제 b → a는 존재하지 않기 때문에 용량을 0이라고 가정해서 가상의 간선을 만든 것이고 , 용량이 0이기에 가상의 간선을 생성해도 논리적으로 , 실제 결과에 영향이 없기 때문에다. 왜 이러한 조건이 필요한지에 대해서와 자세한 내용은 밑에 포드 풀커슨 알고리즘(Ford-Fulkerson Algorithm)을 설명할때 알아보도록 하자.  

설명한 조건들을 표로 정리해보았다. 
|조건|뜻|
|---|---|
|**용량의 제한**|각 간선에 흐르는 유량은 그 간선의 용량을 넘을 수 없다.  f(a, b) <= c(a, b) |
|**유량의 보존**|어떤 정점을 기준으로 , 해당 정점에 들어오는 유량의 합과 나가는 유량의 합은 같다|
|**유량의 대칭**|어느 한 간선에 x만큼의 유량이 흘렀다면 , 반대방향으로 -x의 유량이 흘렀다고 가정하는것 , f(a, b) = -f(b, a) |

---

**이번에는 새로운 Network Flow의 그림을 가지고 왔다.** 
![33](https://user-images.githubusercontent.com/101388379/165632026-d2cc3147-b362-4efd-a7a2-8a7bccca00cb.PNG)

**이 그림에서 최대로 흐르는 유량의 양은 얼마일까?** 

![55](https://user-images.githubusercontent.com/101388379/165631870-7cd9282a-cb4f-4bf7-a164-1224b34135b3.PNG)

먼저 Source → A → Sink의 증가경로를 하나 생각한다. 이 경로에서 최소용량을 가진 간선의 값이 1이므로 1의 유량이 흐를 수 있다.<br>
그리고 Source → C → Sink의 경로로도 흐를 수 있다.

**더 이상 찾을 수 있는 증가경로가 없기에 , 이 그림에서 최대로 흐를 수 있는 유량의 양은 2이고 , 실제로 정답이다.
컴퓨터가 항상 이런 최적의 경로를 먼저 탐색하여 정답을 맞추면 좋겠지만, 항상 그럴 수 만은 없다.**

![66](https://user-images.githubusercontent.com/101388379/165632646-e95c7058-39cf-443f-8814-bf87c3f19312.PNG)

**하지만 이 그림에서처럼 유량은 Source → A → C  → Sink 의 경로로도 흐를 수 있고, 이 같은 경우 Source → A / A → C /C → Sink 간선들의 유량이 최대치이기 때문에, 더 이상 흐를 수 있는 증가 경로가 없어서 최대 유량의 값이 1이 되어버리고 만다. 이것은 오답이다. 컴퓨터가 <br> 만약 Source → A → Sink / Source → C → Sink 의 경로보다
Source → A → C  → Sink를 먼저 탐색 하게 되면 Source → B 에 추가로 연결된 경로를 찾지 못하고 , 프로그램이 중지된다.** <br>

---

**이러한 문제점을 어떻게 해결하고 보완 할 수 있을까?**

이때 우리는 위에서 잠시 설명한 '유량의 대칭' 법칙과 <br>
에드몬드 카프(Edmonds-Karp) 알고리즘과 포드 풀커슨(Ford-Fulkerson) 알고리즘을 통해 효율적으로 유량의 최댓값을 구할 수 있다.
밑에서 더 자세하게 설명하도록 하겠다. <br><br>


 :mag:유량의 대칭법칙 / 최대 유량 알고리즘 종류
---

위 그림에서 우리는 Source → A → C  → Sink 경로로 유량이 흐를때 , Source → A / A → C /C → Sink 간선들의 유량이 최대치이기 때문에
더 이상 유량이 흐를 수 있는 증가경로를 찾지못하고 , 프로그램이 중지되는것을 보아야만 했다. 하지만 위에서 언급했던 **'유량의 대칭법칙'** 을 사용한다면
저 상태에서도 우리는 다시 최대 유량을 구할 수 있게 된다. 

![77](https://user-images.githubusercontent.com/101388379/165640266-3ddd111f-9434-4393-aa41-19eebecdb453.PNG)

A → C 간선으로 1의 유량이 흘렀다는 것은 , 반대로 말하면 C → A 간선으로 -1의 유량이 흘렀다는 것과 같은 말이다. 용량이 0인 가상의 간선 C → A을 만들었고 , -1의 유량을 흘려줬기 때문에 우리는 C → A 간선에 잔여용량이 남아 +1 만큼의 유량을 한번 더 흘릴 수 있게 된다.<br> 
(여기서 C → A 와 같은 가상의 간선을 우리는 '역간선' 이라고 한다.)

![88](https://user-images.githubusercontent.com/101388379/165640794-e2585fc4-b3e3-40bd-8fff-6d25933de69d.PNG)

C → A의 간선이 열렸으므로 위 그림처럼 우리는 역간선을 이용하여 Source → C → A → Sink 의 증가경로에 +1의 유량을 한번 더 흘려줄 수 있다. 그렇다면 
최대 유량이 2라는 정답이 나오게 된다. 우리는 이렇게 **'유량의 대칭법칙'** 을 이용해 역간선을 생성하고 최대 유량을 구하는 알고리즘을 구성하게 될 것이다.

---
# 최대 유량 알고리즘 종류 

컴퓨터가 잔여 용량이 남은 경로를 탐색하는 방법은 두 가지가 있다. **BFS(너비 우선 탐색) 와 DFS(깊이 우선 탐색) 이다.** 
BFS(너비 우선 탐색)을 사용하여 탐색을 한다면 그것은 **에드몬드 카프(Edmonds-Karp) 알고리즘**을 사용한 것이고 , 
DFS(깊이 우선 탐색)을 사용하여 탐색을 한다면 그것은 **포드 풀커슨(Ford-Fulkerson) 알고리즘**을 사용한 것이다. 
두 종류의 알고리즘의 차이점은 탐색을 진행 하느냐이다. 

우선 두가지 탐색법이 무엇인지 그림을 통해 간단하게 알아보겠다. 

![깊이 우선 탐색](https://user-images.githubusercontent.com/101388379/165643978-e5192b5e-c8f1-4833-8ddd-d06b31c9a1ce.PNG)
![너비우선탐색](https://user-images.githubusercontent.com/101388379/165643992-5ca59832-c6e1-4b8e-af58-e83a6312ca33.PNG)

**깊이에 우선한다는 것**은 현재 간선에서 이동 가능한 다음 레벨의 간선을 확인한 후, 곧바로 현재 간선 위치를 선택된 다음의
간선으로 변경한다는 것이다. 이때 , 다음 간선에서 원하는 결과를 찾지 못할 때 , 돌아올 수 있도록 현재 위치를 스택(Stack)
을 사용하여 기억해야 한다. 그래서 보통 문제 해결에 있어 재귀함수의 형태를 사용하는 편이며 , 이전의 부모노드로 돌아오는
과정을 백트래킹(Back-Tracking)이라고 한다.<br><br>

**넓이에 우선한다는 것**은 현재 간선에서 이동 가능한(연결된) 다음 레벨의 간선들을 확인한 후, 다음 레벨의 간선들을 모두 큐(Queue)에 담는다. 이후, 큐에 담긴 간선 위치들을 하나씩 꺼내며 현재 간선 위치를 변경한다는 것입니다. 이에 따라 그래프의 레벨 순서대로 모든 간선들을 탐색하게 된다.<br><br>

후에 성능분석을 진행하며 , 어느 알고리즘이 더 효율적인지 둘의 차이를 비교하겠지만, <br>
지금은 처음 의도한 바와 같이 DFS(깊이 우선 탐색) 을 사용하는 포드 풀커슨(Ford-Fulkerson) 알고리즘을 먼저 집중적으로 다뤄보려고 한다.
 <br><br><br>
 
 
 # :two: 포드 풀커슨(Ford-Fulkerson) 알고리즘
### ●구현방법 <br>
먼저 포드 풀커슨(Ford-Fulkerson) 알고리즘을 진행 하는 순서는 다음과 같이 정리 할 수 있다.
1. 존재하는 모든 간선과 역간선의 유량을 0으로 초기화 한다.
2. 소스에서 싱크로 갈 수 있는, 잔여 용량이 남은 경로를 **DFS**로 탐색한다.
3. 해당 경로에 존재하는 간선들의 잔여 용량 중, 가장 작은 값을 유량으로 흘려보낸다.
4. 해당 유량에 음수값을 취한 후 , 역방향 간선에도 흘려보낸다. **(유량의 대칭법칙)**
5. 잔여 용량이 남은 경로가 존재하지 않을때까지 위 과정을 반복한다.<br><br>

**만약 2번에서 탐색을 BFS(너비 우선 탐색)으로 진행하였다면 그것은 에드몬드 카프(Edmonds-Karp) 알고리즘이 된다.**
<br><br>



---
## 실제코드
```java
public class FordFulkerson {
    private static int size = 702;
    public static void main(String[] args) throws Exception {
       
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());

        int Capacity[][] = new int[size][size];
        for(int i = 0 ; i < n ; i ++) {
            st = new StringTokenizer(br.readLine());
            int s = st.nextToken().charAt(0) -'A';
            int e = st.nextToken().charAt(0) -'A';
            int f = Integer.parseInt(st.nextToken());
            Capacity[s][e] += f;
            Capacity[e][s] += f;
        }

        // end of input
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
                // 부모 -> 자식으로 flowamount가 흐르면 자식->부모로 -flowamount가 흐른다.
                flow[parent[s]][s] +=flowamount;
                flow[s][parent[s]] -=flowamount;
            }
            res+=flowamount;

        }
        return res;

    }
} 
```

**코드를 통해 복잡한 NetworkFlow 문제를 한번 풀어보며 , 코드가 옳게 작성 되었는지 확인해보자.**
![슬라이드0001](https://user-images.githubusercontent.com/101388379/165650937-3fa37eb4-fe66-499e-b0f8-6bfd01fec331.png)

**위와 같은 그래프가 주어졌다. S는 Source , T는 Sink 이다.**
**위 그래프를** **"유량의 대칭법칙"** **을 이용하여 최대 유량의 값을 찾아보면**
![슬라이드0011](https://user-images.githubusercontent.com/101388379/165651285-204a66b8-696b-4b2c-a9c1-942867196144.png)

![슬라이드0012](https://user-images.githubusercontent.com/101388379/165651362-0eea5e1f-119c-442b-ab5c-8589a57d8ce6.png)
    <br> **2 → 3 역간선을 만들어주고 , S → 1 → 2 → 3 → 4 → T로 다시 흘려주는 것을 볼 수 있다.**
**그 결과 최대 유량의 값을 20이 나온다는 것을 확인 할 수 있다. 이 문제를 코드를 통해 다시 풀고 결과를 확인해보자.**
![결과 2](https://user-images.githubusercontent.com/101388379/165653353-63dfdc6c-9cbd-4f5d-b3dc-fb217a88b8a7.PNG)

실제로 계산한 결과와 같은 값이 나온다는 것을 알 수 있다.

---

### ●포드 풀커슨(Ford-Fulkerson) 알고리즘 단점 / 최악의 상황

![img](https://user-images.githubusercontent.com/101388379/165657521-03ce74e7-cffe-481b-b2ea-a1cf5a8c0214.png)<br>
**이 그래프가 포드 풀커슨 알고리즘 , 최악의 상황을 만드는 그래프이다.**

**이 그래프의 최대 유량 값을 코드를 통해 구해본다면** <br>

![캡처66](https://user-images.githubusercontent.com/101388379/165657776-bfac6033-c70a-4e4a-8eef-f5e55314b0e3.PNG)<br>
**2000이란 값이 나오고 , 실제로 정답이다. 프로그래밍을 통해 본다면 아무 문제가 없어보이지만,**
**실제 2000이란 값이 도달하기까지의 수행과정에 있다. 그것은 바로 수행 횟수이다.**  <br><br>

### ●위 그림에서, DFS 를 이용한 증가 경로 탐색은 아래와 같다. <br>
A→B→C→D (1의 유량 보냄) <br>
A→C→B→D (역간선을 이용해 1의 유량 보냄) <br>
A→B→C→D (1의 유량 보냄) <br>
A→C→B→D (역간선을 이용해 1의 유량 보냄) <br>
반복 <br><br>

위에 보면 알 수 있듯이  DFS(깊이 우선 탐색)을 탐색기법으로 사용하는 **포드 풀커슨 알고리즘**은
증가경로 한개당 하나의 유량 밖에 보낼 수가 없다. 즉 DFS를 유량의 수만큼 사용해야하는 경우가 생기게 된다.
위에서 우리는 프로그래밍을 통해 손쉽게 최대 유량이 2000이라는 것을 알아냈지만 , 실제로 컴퓨터는 총 2000번 증가경로에
유량을 흘려보낸 것이다.



