import java.lang.reflect.GenericArrayType;
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
    private List<List<Integer>> adjecencyList;

    //конструктор и инициализация
    public EulerianPath(List<List<Integer>> graph){
        if (graph == null) throw new IllegalArgumentException("GRAPH CANNOT BE NULL");

        this.vertices = graph.size();
        this.adjecencyList = graph;
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
            for (int to : adjecencyList.get(from)){
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
            if (outDegree[i]>=0){
                start=i;
            }
        }
        return start;
    }

    private void performDFS(int vertex){
        //докато текущият връх има изходящи ребра
        while(outDegree[vertex]>0){
            //vzemam sledvashto rebro ot kraya na spisaka
            int nextVertex=adjecencyList.get(vertex).get(--outDegree[vertex]);
            //продължавам рекурсивно
            performDFS(nextVertex);
        }

        //няма повече ребра и добавям върха към пътя
        path.addFirst(vertex);
    }

    


}