import java.io.*;
import java.util.*;

public class solutionOne {

    public static void main (String[] args) {
        int numOfThreads = 12;
        long [] timeArray = new long[10];

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            letterThreadOne [] threads = new letterThreadOne[numOfThreads];
            Map<String, Integer> letterMap = new HashMap<>();
            try (BufferedReader in = new BufferedReader(new FileReader("inputFile.txt"))) {
                StringBuilder sb = new StringBuilder();

                String inputString = in.readLine();
                while (inputString != null) {
                    sb.append(inputString).append("\n");
                    inputString = in.readLine();
                }

                inputString = sb.toString();

                String[] chunkedString = splitString(inputString, numOfThreads);


                for (int counter = 0; counter < numOfThreads; counter++) {
                    threads[counter] = new letterThreadOne(chunkedString[counter]);
                }

                //letterMap.forEach((key, value) -> System.out.println(key + ":" + value));

                for (int j = 0; j < numOfThreads; j++) {
                    try {
                        threads[j].start();
                    } catch (NullPointerException np) {
                        System.out.println("TEST");
                        break;
                    }
                }
                for (int j = 0; j < numOfThreads; j++) {
                    try {
                        threads[j].join();
                    } catch (InterruptedException e) {
                        System.out.println("TEST");
                        e.printStackTrace();
                    } catch (NullPointerException np) {
                        System.out.println("TEST");
                        break;
                    }
                }

                for (int j = 0; j < numOfThreads; j++) {
                    letterMap.putAll(threads[j].getLocalMap());
                }

                letterMap.forEach((key, value) -> System.out.println(key + ":" + value));


            } catch (IOException e) {
                System.out.println("TEST");
                e.printStackTrace();
            }
            long finish = System.currentTimeMillis();
            long timeDiff = finish - start;
            timeArray[i] = timeDiff;
        }
        long sum = 0;
        for (long l : timeArray) {
            sum = sum + l;
        }
        long average = sum/timeArray.length;
        System.out.println('\n');
        System.out.println('\n');
        System.out.println("Time Average: " + average);
    }

    public static String[] splitString (String str, int numOfThreads) {
        int lengthOfString = str.length();
        String[] splitString = new String[numOfThreads];

        int loc = 0;
        int chars = (int) Math.ceil((double) lengthOfString / (double) numOfThreads);

        for (int counter = 0; counter < lengthOfString; counter += chars) {
            String part = str.substring(counter, Math.min(counter+chars, lengthOfString));
            splitString[loc] = part;
            loc += 1;
        }

        for (String s : splitString) {
            if (s == null) {
                System.out.println("NULL");
            }
        }
        return splitString;

    }


}


