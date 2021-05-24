package ru.drobyazko;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataBundle {
    private List<String> stringList;

    public DataBundle(String firstString, String secondString, String thirdString) {
        stringList = new ArrayList<>();
        stringList.add(firstString);
        stringList.add(secondString);
        stringList.add(thirdString);
    }

    public String getString(int index) {
        return stringList.get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBundle that = (DataBundle) o;
        return stringList.equals(that.stringList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringList);
    }

    @Override
    public String toString() {
        return getString(0) + ";" + getString(1) + ";" + getString(2);
    }

    public String getFirstString() {
        return stringList.get(0);
    }

    public String getSecondString() {
        return stringList.get(1);
    }

    public String getThirdString() {
        return stringList.get(2);
    }

}
