package com.egovoryn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListForPathfinder<T> implements Comparable<ListForPathfinder<T>> {
    List<PathNode> pathNodesList = new ArrayList<>();

    public ListForPathfinder() {
    }

    public ListForPathfinder(ListForPathfinder<PathNode> list) {
        this.pathNodesList.addAll(list.pathNodesList);
    }

    public void add(PathNode cell) {
        pathNodesList.add(cell);
    }

    @Override
    public String toString() {
        return "ListForPathfinder{" +
                "cellList=" + pathNodesList +
                '}';
    }

    @Override
    public int compareTo(ListForPathfinder<T> o) {
//        int pathCostThis = 0, pathCostInput = 0;
//        for (Cell cell : this.cellList) {
//            pathCostThis += cell.getPathCost();
//        }
//        for (Cell cell : o.cellList) {
//            pathCostInput += cell.getPathCost();
//        }
//        return pathCostThis - pathCostInput;

        int pathCostThis = this.pathNodesList.get(this.pathNodesList.size() - 1).getPathCost();
        int pathCostInput = o.pathNodesList.get(o.pathNodesList.size() - 1).getPathCost();
        return pathCostThis - pathCostInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListForPathfinder<?> that = (ListForPathfinder<?>) o;
        return Objects.equals(pathNodesList, that.pathNodesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathNodesList);
    }

    public PathNode get(int i) {
        return pathNodesList.get(i);
    }

    public void remove(int i) {
        pathNodesList.remove(i);
    }

    public boolean isEmpty() {
        return pathNodesList.isEmpty();
    }

    public int size() {
        return pathNodesList.size();
    }


}
