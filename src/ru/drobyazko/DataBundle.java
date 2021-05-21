package ru.drobyazko;

import java.util.Objects;

public class DataBundle {
    private String firstString;
    private String secondString;
    private String thirdString;

    public DataBundle(String firstString, String secondString, String thirdString) {
        this.firstString = firstString;
        this.secondString = secondString;
        this.thirdString = thirdString;
    }

    public String getFirstString() {
        return firstString;
    }

    public String getSecondString() {
        return secondString;
    }

    public String getThirdString() {
        return thirdString;
    }

    @Override
    public String toString() {
        return "DataBundle{" +
                "firstString='" + firstString + '\'' +
                ", secondString='" + secondString + '\'' +
                ", thirdString='" + thirdString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBundle that = (DataBundle) o;
        return firstString.equals(that.firstString)
                && secondString.equals(that.secondString)
                && thirdString.equals(that.thirdString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstString, secondString, thirdString);
    }
}
