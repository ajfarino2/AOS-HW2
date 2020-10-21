import java.io.*;
import java.util.*;

public class iterativeFreq {

    static int SIZE = 256;
    public static int[] countLetters(String filename) {
        int[] freq = new int[SIZE];
        File input = new File(filename);
        try (Scanner in = new Scanner(input)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                int counter;
                char character;
                for (counter = 0; counter < line.length(); counter++) {
                    character = Character.toLowerCase(line.charAt(counter));
                    if (Character.isLetter(character)) {
                        freq[character]++;
                    }
                }
            }
            for (int counter = 0; counter < 256; counter++) {
                if (freq[counter] != 0) {
                    System.out.println((char) counter + " : " + freq[counter]);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return freq;
    }

    public static void main (String [] args) {
        long [] timeArray = new long[10];
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            int [] finalFreqArray = countLetters("inputFile.txt");
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
        System.out.println('\n' + "Time Average: " + average);

    }










}