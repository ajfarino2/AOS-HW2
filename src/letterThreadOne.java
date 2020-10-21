import java.util.HashMap;
import java.util.Map;

public class letterThreadOne extends Thread{
        static HashMap<String, Integer> localLetterMap;
        String inputString;

        letterThreadOne(String chars) {
            if (chars == null) {
                System.out.println("NULL INPUT AHHHH");
            }
            inputString = chars;
            localLetterMap = new HashMap<String, Integer>();
        }

        public synchronized void run() {
            int counter;
            char character;
            int value = 0;
            for (counter = 0; counter < inputString.length(); counter++) {
                character = inputString.charAt(counter);

                character = Character.toLowerCase(inputString.charAt(counter));
                if (Character.isLetter(character) && localLetterMap.containsKey(String.valueOf(character))) {
                    try {
                        value = localLetterMap.get(String.valueOf(character));
                    } catch (Exception e) {
                        System.out.println("TEST VALUE HERE");
                        System.out.println(value);
                        continue;
                    }
                    value += 1;
                    localLetterMap.put(String.valueOf(character), value);
                } else if (Character.isLetter(character)) {
                    value = 0;
                    value += 1;
                    localLetterMap.put(String.valueOf(character), value);
                }
            }
        }
        
        public HashMap<String, Integer> getLocalMap() {
            return localLetterMap;
        }
    }

