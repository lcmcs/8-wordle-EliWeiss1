import java.util.*;

public class Main {
    public static boolean properName(String s) {
        return s.matches("[A-Z][a-z]+");
    }

    public static boolean integer(String s) {
        return s.matches("[+-]?[0-9]*\\.?[0-9]+");
    }

    public static boolean ancestor(String s) {
        return s.matches("(great-)*grand(mother|father)|mother|father");
    }

    public static boolean palindrome(String s) {
        return s.matches("(?i)([a-z])([a-z])([a-z])([a-z])([a-z])\\5\\4\\3\\2\\1");
    }

    public static List<String> wordleMatches(List<List<WordleResponse>> responses) throws Exception {
        List<String> wordList = loadWordList();
        Set<Character> bannedLetters = new HashSet<>();
        Set<Character> requiredLetters = new HashSet<>();
        Map<Integer, Character> correctPositions = new HashMap<>();
        Map<Integer, Set<Character>> bannedAtPosition = new HashMap<>();

        for (List<WordleResponse> guess : responses) {
            for (WordleResponse wr : guess) {
                if (wr.resp == LetterResponse.WRONG_LETTER) {
                    bannedLetters.add(wr.c);
                } else if (wr.resp == LetterResponse.WRONG_LOCATION) {
                    requiredLetters.add(wr.c);
                    if (!bannedAtPosition.containsKey(wr.index)) {
                        bannedAtPosition.put(wr.index, new HashSet<>());
                    }
                    bannedAtPosition.get(wr.index).add(wr.c);
                } else if (wr.resp == LetterResponse.CORRECT_LOCATION) {
                    correctPositions.put(wr.index, wr.c);
                }
            }
        }

        List<String> matches = new ArrayList<>();
        for (String word : wordList) {

            boolean valid = true;

            // Check banned letters
            for (char c : bannedLetters) {
                if (word.indexOf(c) != -1) { valid = false; break; }
            }

            // Check required letters
            for (char c : requiredLetters) {
                if (word.indexOf(c) == -1) { valid = false; break; }
            }

            // Check correct positions (green)
            for (Map.Entry<Integer, Character> e : correctPositions.entrySet()) {
                if (word.charAt(e.getKey()) != e.getValue()) { valid = false; break; }
            }

            // Check banned at position (yellow)
            for (Map.Entry<Integer, Set<Character>> e : bannedAtPosition.entrySet()) {
                if (e.getValue().contains(word.charAt(e.getKey()))) { valid = false; break; }
            }

            if (valid) matches.add(word);
        }

        return matches;
    }


    public static List<String> loadWordList() throws Exception {
        String url = "https://gist.githubusercontent.com/dracos/dd0668f281e685bad51479e5acaadb93/raw/6bfa15d263d6d5b63840a8e5b64e04b382fdb079/valid-wordle-words.txt";
        Scanner scanner = new Scanner(new java.net.URL(url).openStream());
        List<String> words = new ArrayList<>();
        while (scanner.hasNextLine()) {
            words.add(scanner.nextLine().trim().toUpperCase());
        }
        scanner.close();
        return words;
    }
}
