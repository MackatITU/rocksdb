import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class CritCleaner {

    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Script must be run with 2 arguments.");
            System.out.println("First argument is input file, second argument is output file.");
            System.out.println("Example format: MutexLock 0   on each line.");
            return;
        }

        File inputFile = new File(args[0]);
        String outputFile = args[1];

        HashMap<String,Integer> critMap = FillCritMap(inputFile);
        WriteCritMapToFile(outputFile,critMap);
    }

    //Method to write the critical section map to a file
    public static void WriteCritMapToFile(String outputFile,  HashMap<String,Integer> critMap){
        try {
            FileWriter fileWriter = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            ArrayList<String> keys = SetToList(critMap.keySet());

            for (int i = 0; i < keys.size(); i++) {
                String critSection = keys.get(i);
                int value = critMap.get(critSection);
                printWriter.printf(critSection + " " + value);
                printWriter.println();
            }
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Method to fill simple Map/dictionary up with critical section string names and a corresponding times-hit value
    public static HashMap<String,Integer> FillCritMap(File filepath){
        HashMap<String,Integer> critMap = new HashMap<>();
        try {
            Scanner scanner = new Scanner(filepath);

            while(scanner.hasNextLine()){
                String currentLine = scanner.nextLine();
                if (currentLine.isEmpty()) continue;

                String[] info = currentLine.split(" ");
                String critcalSection = info[0];
                //System.out.println(currentLine);

                if (critMap.containsKey(info[0])) {
                    int oldVal = critMap.get(critcalSection);
                    critMap.put(critcalSection,oldVal+1);
                } else {
                    critMap.put(critcalSection,1);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return critMap;
    }

    //Simple method to convert Java's Set to Arraylist.
    public static ArrayList<String> SetToList(Set<String> input){
        ArrayList<String> aList = new ArrayList<>(input.size());
        for (String x : input)
            aList.add(x);
        return aList;
    }
}