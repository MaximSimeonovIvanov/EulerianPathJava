//Finding an Eulerian path in a graph

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EulerianPathDirectedEdgesAdjacencyList {
    private int edgeCount; //брой ребра
    private final int n; //брой върхове
    private int[] in, out; //степени на "вън" и вътре
    private LinkedList<Integer> path; //резултат
    private List<List<Integer>> graph; //граф

    //конструктор
    public EulerianPathDirectedEdgesAdjacencyList(List<List<Integer>> graph){
        //initialize
    }

    //това е "интерфейсът на класа", който определя последователността на операциите
    public int[] getEulerianPath(){
        /*set up();                     podgotovka
        if(!graphHasEulerianPath())     proverka na neobhodimite uslovia
            return null;
        dfs(findStartNode));            namiram patya

        proverka za palno obhojdane
        if(path.size() != edgeCount+1)
            return null;

        преобразувам резултата
        return convertPathToArray();
        */
    }
}
