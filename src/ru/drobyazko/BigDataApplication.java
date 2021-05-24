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

        List<DataBundle> dataList = new ArrayList<>(dataSet);
        Collections.sort(dataList, Comparator.comparing(DataBundle::getFirstString)
                .thenComparing(DataBundle::getSecondString)
                .thenComparing(DataBundle::getThirdString));

        Map<Integer, List<DataBundle>>  groupMap = new HashMap<>();
        List<Map<String, Integer>> stringMapList = new ArrayList<>();

        stringMapList.add(new HashMap<>());
        stringMapList.add(new HashMap<>());
        stringMapList.add(new HashMap<>());

        for (DataBundle dataBundle:dataList) {
            int putToGroupNumber = groupMap.size();

            if(stringMapList.get(0).containsKey(dataBundle.getFirstString())
                    || stringMapList.get(1).containsKey(dataBundle.getSecondString())
                    || stringMapList.get(2).containsKey(dataBundle.getThirdString())) {

                List<Integer> putToGroupNumberList = new ArrayList<>();
                putToGroupNumberList.add(groupMap.size());
                putToGroupNumberList.add(groupMap.size());
                putToGroupNumberList.add(groupMap.size());

                for (int i = 0; i < stringMapList.size(); ++i) {
                    if(stringMapList.get(i).get(dataBundle.getString(i)) != null
                            && dataBundle.getString(i) != "\"\"") {
                        putToGroupNumberList.set(i, stringMapList.get(i).get(dataBundle.getString(i)));
                    }
                }

                putToGroupNumber = Integer.min(putToGroupNumberList.get(0), putToGroupNumberList.get(1));
                putToGroupNumber = Integer.min(putToGroupNumber, putToGroupNumberList.get(2));

                if(putToGroupNumber != putToGroupNumberList.get(0)
                        && putToGroupNumberList.get(0) != groupMap.size()
                        && groupMap.get(putToGroupNumber) != groupMap.get(putToGroupNumberList.get(1))) {
                    groupMap.get(putToGroupNumber).addAll(groupMap.get(putToGroupNumberList.get(0)));
                    groupMap.replace(putToGroupNumberList.get(0), groupMap.get(putToGroupNumber));
                }

                if(putToGroupNumber != putToGroupNumberList.get(1)
                        && putToGroupNumberList.get(1) != groupMap.size()
                        && groupMap.get(putToGroupNumber) != groupMap.get(putToGroupNumberList.get(1))) {
                    groupMap.get(putToGroupNumber).addAll(groupMap.get(putToGroupNumberList.get(1)));
                    groupMap.replace(putToGroupNumberList.get(1), groupMap.get(putToGroupNumber));
                }

                if(putToGroupNumber != putToGroupNumberList.get(2)
                        && putToGroupNumberList.get(2) != groupMap.size()
                        && groupMap.get(putToGroupNumber) != groupMap.get(putToGroupNumberList.get(2))) {
                    groupMap.get(putToGroupNumber).addAll(groupMap.get(putToGroupNumberList.get(2)));
                    groupMap.replace(putToGroupNumberList.get(2), groupMap.get(putToGroupNumber));
                }

            }

            if(!groupMap.containsKey(putToGroupNumber)) {
                groupMap.put(putToGroupNumber, new ArrayList<>());
            }

            groupMap.get(putToGroupNumber).add(dataBundle);
            if(!dataBundle.getString(0).equals("\"\"")) {
                stringMapList.get(0).put(dataBundle.getString(0), putToGroupNumber);
            }
            if(!dataBundle.getString(1).equals("\"\"")) {
                stringMapList.get(1).put(dataBundle.getString(1), putToGroupNumber);
            }
            if(!dataBundle.getString(2).equals("\"\"")) {
                stringMapList.get(2).put(dataBundle.getString(2), putToGroupNumber);
            }

        }

        Set<List<DataBundle>> dataBundleListHashSet = new HashSet<>();
        for(int i = 0; i < groupMap.size(); ++i) {
            dataBundleListHashSet.add(groupMap.get(i));
        }

        List<List<DataBundle>> resultList = new ArrayList<>(dataBundleListHashSet);
        Collections.sort(resultList, Comparator.comparing(List::size, Comparator.reverseOrder()));

        File csvResultsFile = new File("src/ru/drobyazko/results.csv");
        if(!csvResultsFile.exists()) {
            return;
        }

        try (BufferedWriter csvWriter  = new BufferedWriter(new FileWriter(csvResultsFile))) {
            int size = 0;
            for(int i = 0; i < resultList.size(); ++i) {
                if(resultList.get(i).size() > 1) {
                    ++size;
                }
            }
            csvWriter.write(String.valueOf(size));
            csvWriter.newLine();

            for(int i = 0; i < resultList.size(); ++i) {
                csvWriter.write("Group " + (int)(i + 1));
                csvWriter.newLine();
                for(int j = 0; j < resultList.get(i).size(); ++j) {
                    csvWriter.write(String.valueOf(resultList.get(i).get(j)));
                    csvWriter.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
