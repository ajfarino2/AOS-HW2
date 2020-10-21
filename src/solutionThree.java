import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class solutionThree {

    public static void experimentThree() {
        int numOfThreads = 1000;
        long[] timeArray = new long[10];

        for (int i = 0; i < 10; i++) {
            Map<String, Integer> letterMap = new HashMap<>();
            long start = System.currentTimeMillis();
            Thread[] threads = new Thread[numOfThreads];
            try (BufferedReader in = new BufferedReader(new FileReader("inputFile.txt"))) {

                int threadsInUse = 0;
                String line;

                while ((line = in.readLine()) != null) {
                    threads[threadsInUse] = new Thread(line) {
                        public synchronized void run() {
                            int counter;
                            char character;
                            for (counter = 0; counter < getName().length(); counter++) {
                                character = Character.toLowerCase(getName().charAt(counter));
                                int value;
                                if (Character.isLetter(character) && letterMap.containsKey(String.valueOf(character))) {
                                    value = letterMap.get(String.valueOf(character));
                                    value += 1;
                                    letterMap.put(String.valueOf(character), value);
                                } else if (Character.isLetter(character)) {
                                    value = 0;
                                    value += 1;
                                    letterMap.put(String.valueOf(character), value);
                                }
                            }

                            Thread.yield();
                            this.notify();
                        }
                    };
                    threads[threadsInUse].start();
                    threadsInUse += 1;

                    if (threadsInUse == numOfThreads) {
                        for (int l = 0; l < numOfThreads; l++) {
                            threads[l].join();
                        }
                        threadsInUse = 0;
                    }

                }
                long finish = System.currentTimeMillis();
                long timeDiff = finish - start;
                System.out.println("TIMEDIFF------> " + timeDiff);
                timeArray[i] = timeDiff;
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
            long sum = 0;
            for (int num = 0; num < timeArray.length; num++) {
                sum += timeArray[num];
            }
            long average = sum / timeArray.length;
            System.out.println('\n');
            System.out.println('\n');
            System.out.println("Time Average: " + average);

    }




    public static void main (String[] args) {
        experimentThree();
    }


}

