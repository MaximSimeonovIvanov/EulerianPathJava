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
}