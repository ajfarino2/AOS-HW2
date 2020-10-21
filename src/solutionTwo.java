import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class solutionTwo {

    public static void main (String[] args) {
        int numOfThreads = 1000;
        long[] timeArray = new long[10];

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            Thread[] threads = new Thread[numOfThreads];
            Map<String, AtomicInteger> letterMap = new ConcurrentHashMap<>();

            try (BufferedReader in = new BufferedReader(new FileReader("inputFile.txt"))) {
                StringBuilder sb = new StringBuilder();

                String inputString = in.readLine();
                while (inputString != null) {
                    sb.append(inputString).append("\n");
                    inputString = in.readLine();
                }

                inputString = sb.toString();
                //System.out.println(inputString.length());
                String[] chunkedString = splitString(inputString, numOfThreads);


                for (int counter = 0; counter < numOfThreads; counter++) {
                    if (chunkedString[counter] == null) {
                        break;
                    }
                    threads[counter] = new Thread(chunkedString[counter]) {
                        public synchronized void run() {
                            int counter;
                            char character;
                            for (counter = 0; counter < getName().length(); counter++) {
                                character = Character.toLowerCase(getName().charAt(counter));
                                if (Character.isLetter(character)) {
                                    letterMap.putIfAbsent(String.valueOf(character), new AtomicInteger(0));
                                    letterMap.get(String.valueOf(character)).getAndIncrement();
                                }
                            }

                            Thread.yield();
                            notify();
                        }
                    };
                }

                //letterMap.forEach((key, value) -> System.out.println(key + ":" + value));
                for (int j = 0; j < numOfThreads; j++) {
                    try {
                        threads[j].start();
                        threads[j].join();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    } catch (NullPointerException np) {
                        break;
                    }
                }

            } catch (IOException e) {
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

        return splitString;

    }

}
