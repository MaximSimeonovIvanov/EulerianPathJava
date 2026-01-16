import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EulerianPath {
    //основни глобални променливи
    private final int vertices;
    private int edges;
    //данни за степените
    private int[] inDegree;
    private int[] outDegree;
    //това е за пътя
    private LinkedList<Integer> path;
    //графът чрез списък на съседство
    private List<List<Integer>> adjacencyList;

    //конструктор и инициализация
    public EulerianPath(List<List<Integer>> graph){
        if (graph == null) throw new IllegalArgumentException("GRAPH CANNOT BE NULL");

        this.vertices = graph.size();
        this.adjacencyList = graph;
        this.path = new LinkedList<>();
    }

    //главния публичен метод определя последователността на иперациите
    public int[] findEulerianPath(){
        //podgotovkata zapochva s izchislyqvane na stepenite
        calculateDegrees();
        //проверка дали съществува Ойлеров път
        if (!hasEulerianPath()){
            return null; //връща null ако няма
        }

        //намирам начален връх
        int startVertex = findStartingVertex();

        //намирма пътя чрез Depth first search
        performDFS(startVertex);

        //проверявам дали съм обходил всички ребра
        if (path.size() != edges + 1){
            return null;  //графут не е свързан
        }

        //връщам резултата в удобен формат
        return convertPathToArray();
    }

    //алгоритъмът започва с изчисляването на входящи и изходящи степени
    private void calculateDegrees(){
        inDegree = new int[vertices];
        outDegree = new int[vertices];
        edges = 0;

        for (int from =0; from<vertices; from++){
            for (int to : adjacencyList.get(from)){
                //увеличавам входящата степен на to
                inDegree[to]++;
                //увеличавам изходящата степен на from
                outDegree[from]++;
                //броя общия брой ребра
                edges++;
            }
        }
    }

    //proverka dali sashtestvuva Oilerov pat
    private boolean hasEulerianPath(){
        //ако няма ребра, няма и път
        if (edges == 0) return false;

        int startCandidates = 0; //върхове с out > in са входни точки
        int endCandidates = 0;   //in > out са изходни точки

        for (int i = 0; i < vertices; i++){
            int difference = outDegree[i] - inDegree[i];

            //ako razlikata e tvarde golyama, nyama Oilerov pat
            if (difference > 1 || difference < -1){
                return false;
            }
            if (difference == 1){
                startCandidates++; //този връх може да е начало на Ойлеровия път
            } else if (difference == -1){
                endCandidates++;   //този може да е край
            }
        }

        //1)ако всичко in = висчки out -> това е цикъл
        //2)ако има 1 начален и 1 завършващ връх -> това е път
        return (startCandidates==0 && endCandidates==0) ||
                (startCandidates==1 && endCandidates==1);
    }

    //след като знам, че има път, трябва да знам от къде да започна
    private int findStartingVertex(){
        int start = 0;

        for (int i=0;i<vertices;i++){
            //ako има връх с out-in=1, той трябва да е начало
            if (outDegree[i] - inDegree[i] ==1){
                return i;
            }
            //иначе започвам от 1я връх с изходящи ребра
            if (outDegree[i]>0){
                start=i;
            }
        }
        return start;
    }

    private void performDFS(int vertex){
        //докато текущият връх има изходящи ребра
        while(outDegree[vertex]>0){
            //vzemam sledvashto rebro ot kraya na spisaka
            int nextVertex=adjacencyList.get(vertex).get(--outDegree[vertex]);
            //продължавам рекурсивно
            performDFS(nextVertex);
        }

        //няма повече ребра и добавям върха към пътя
        path.addFirst(vertex);
    }

    //преобразувам резултата в ъдобен формат
    private int[] convertPathToArray(){
        int[] result = new int[path.size()];
        int index=0;

        for (int vertex : path){
            result[index++]=vertex;
        }
        return result;
    }

    //създавам граф
    public static List<List<Integer>> createEmptyGraph(int vertices){
        List<List<Integer>> graph = new ArrayList<>(vertices);
        for (int i=0;i<vertices;i++){
            graph.add(new ArrayList<>());
        }
        return graph;
    }

    public static void addEdge(List<List<Integer>> graph, int from, int to){
        graph.get(from).add(to);
    }



    //PROBA
    public static void main(String[] args){
        List<List<Integer>> graph = createEmptyGraph(4);
        addEdge(graph,0,1);
        addEdge(graph,1,2);
        addEdge(graph,2,3);
        addEdge(graph,3,0);
        addEdge(graph,0,2);
        addEdge(graph,2,0);

        EulerianPath solver = new EulerianPath(graph);
        int[] path = solver.findEulerianPath();

        if (path!=null){
            System.out.print("Ойлеров път: ");
            for (int vertex : path){
                System.out.print(vertex+" ");
            }
            System.out.println();
        } else {
            System.out.println("Няма Ойлеров път.");
        }

        System.out.println();
        System.out.println("Тест 1: Граф с Ойлеров цикъл");
        List<List<Integer>> graph1 = createEmptyGraph(3);
        addEdge(graph1, 0, 1);
        addEdge(graph1, 1, 2);
        addEdge(graph1, 2, 0);

        EulerianPath solver1 = new EulerianPath(graph1);
        int[] path1 = solver1.findEulerianPath();
        System.out.println("Резултат: " + (path1 != null ? "Има път" : "Няма път"));

        System.out.println("\nТест 2: Граф с Ойлеров път");
        List<List<Integer>> graph2 = createEmptyGraph(4);
        addEdge(graph2, 0, 1);
        addEdge(graph2, 1, 2);
        addEdge(graph2, 2, 3);
        addEdge(graph2, 3, 1);

        EulerianPath solver2 = new EulerianPath(graph2);
        int[] path2 = solver2.findEulerianPath();
        System.out.println("Резултат: " + (path2 != null ? "Има път" : "Няма път"));
    }

}