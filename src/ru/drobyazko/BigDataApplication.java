package ru.drobyazko;

import java.io.*;
import java.util.*;

public class BigDataApplication {

    public static void main(String[] args) {

        File csvFile = new File("src/ru/drobyazko/lng.csv");
        if(!csvFile.exists()) {
            return;
        }

        Set<DataBundle> dataSet = new HashSet<>();

        try (BufferedReader csvReader  = new BufferedReader(new FileReader(csvFile))) {

            String row;
            while( (row = csvReader.readLine()) != null) {
                if(row.matches("\"\\d*\";\"\\d*\";\"\\d*\"")) {
                    String[] dataArray = row.split(";");

                    for (String dataElement: dataArray) {
                        dataElement = dataElement.replaceAll("\"", "");
                    }

                    DataBundle dataBundle = new DataBundle(dataArray[0], dataArray[1], dataArray[2]);

                    dataSet.add(dataBundle);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dataSet.size());

        List<DataBundle> dataList = new ArrayList<>(dataSet);
        Collections.sort(dataList, Comparator.comparing(DataBundle::getFirstString)
                .thenComparing(DataBundle::getSecondString)
                .thenComparing(DataBundle::getThirdString));

        List<Group> groupList = new ArrayList<>();
        HashMap<String, Integer> firstDataGroupMap = new HashMap<>();
        HashMap<String, Integer> secondDataGroupMap = new HashMap<>();
        HashMap<String, Integer> thirdDataGroupMap = new HashMap<>();
        // вот отсюда начинается всякая фигня, я сначала хотел переборчиком попробовать
        // ожидаемо, не очень хорошо получилось, надо уложиться в <30 сек
        int groupNumber = 1;
        for (DataBundle dataBundle:dataList) {
            if (firstDataGroupMap.containsKey(dataBundle.getFirstString())
                    || secondDataGroupMap.containsKey(dataBundle.getSecondString())
                    || thirdDataGroupMap.containsKey(dataBundle.getThirdString())) {
                firstDataGroupMap.put(dataBundle.getFirstString(), groupNumber);
                secondDataGroupMap.put(dataBundle.getSecondString(), groupNumber);
                thirdDataGroupMap.put(dataBundle.getThirdString(), groupNumber);
            }
            firstDataGroupMap.put(dataBundle.getFirstString(), groupNumber);
            secondDataGroupMap.put(dataBundle.getSecondString(), groupNumber);
            thirdDataGroupMap.put(dataBundle.getThirdString(), groupNumber);
            ++groupNumber;
            /*boolean isSolved = false;

            for (Group group: groupList) {
                if(group.poolContains(dataBundle)) {
                    group.add(dataBundle);
                    isSolved = true;
                    break;
                }
            }
            if(!isSolved) {
                groupList.add(new Group());
                groupList.get(groupList.size() - 1).add(dataBundle);
            }*/

        }
        System.out.println(groupList.size());
    }

}
