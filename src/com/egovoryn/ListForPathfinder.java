package com.egovoryn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListForPathfinder<T> implements Comparable<ListForPathfinder<T>> {
    List<Cell> cellList = new ArrayList<>();

    public ListForPathfinder() {
    }

    public ListForPathfinder(ListForPathfinder<Cell> list) {
        this.cellList.addAll(list.cellList);
    }

    public void add(Cell cell) {
        cellList.add(cell);
    }

    @Override
    public String toString() {
        return "ListForPathfinder{" +
                "cellList=" + cellList +
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

        int pathCostThis = this.cellList.get(this.cellList.size() - 1).getPathCost();
        int pathCostInput = o.cellList.get(o.cellList.size() - 1).getPathCost();
        return pathCostThis - pathCostInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListForPathfinder<?> that = (ListForPathfinder<?>) o;
        return Objects.equals(cellList, that.cellList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellList);
    }

    public Cell get(int i) {
        return cellList.get(i);
    }

    public void remove(int i) {
        cellList.remove(i);
    }

    public boolean isEmpty() {
        return cellList.isEmpty();
    }

    public int size() {
        return cellList.size();
    }


}
