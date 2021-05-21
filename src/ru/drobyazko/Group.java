package ru.drobyazko;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Group {

    private List<DataBundle> dataList;
    private HashSet<String> firstStringPool;
    private HashSet<String> secondStringPool;
    private HashSet<String> thirdStringPool;

    public Group() {
        dataList = new ArrayList<>();
        firstStringPool = new HashSet<>();
        secondStringPool = new HashSet<>();
        thirdStringPool = new HashSet<>();
    }

    public void add(DataBundle dataBundle) {
        dataList.add(dataBundle);
        firstStringPool.add(dataBundle.getFirstString());
        secondStringPool.add(dataBundle.getSecondString());
        thirdStringPool.add(dataBundle.getThirdString());
    }

    public boolean poolContains(DataBundle dataBundle) {
        return (dataBundle.getFirstString() != "" && firstStringPool.contains(dataBundle.getFirstString()))
                || (dataBundle.getSecondString() != "" && secondStringPool.contains(dataBundle.getSecondString()))
                || (dataBundle.getThirdString() != "" && thirdStringPool.contains(dataBundle.getThirdString()));
    }

}
