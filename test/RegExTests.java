import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class RegExTests {
    //proper name
    @Test
    public void testProperName_valid() {
        assertTrue(Main.properName("John"));
    }

    @Test
    public void testProperName_invalid() {
        assertFalse(Main.properName("john"));
        assertFalse(Main.properName("JOHN"));
        assertFalse(Main.properName(""));
    }

    //integer
    @Test
    public void testInteger_valid() {
        assertTrue(Main.integer("12"));
        assertTrue(Main.integer("43.23"));
        assertTrue(Main.integer("-34.5"));
        assertTrue(Main.integer("+98.7"));
        assertTrue(Main.integer("0"));
    }

    @Test
    public void testInteger_invalid() {
        assertFalse(Main.integer("abc"));
        assertFalse(Main.integer("5."));
        assertFalse(Main.integer(""));
    }

    //ancestors
    @Test
    public void testAncestor_valid() {
        assertTrue(Main.ancestor("mother"));
        assertTrue(Main.ancestor("grandfather"));
        assertTrue(Main.ancestor("great-great-grandmother"));
    }

    @Test
    public void testAncestor_invalid() {
        assertFalse(Main.ancestor("sister"));
        assertFalse(Main.ancestor("great-mother")); // no "grand"
        assertFalse(Main.ancestor(""));
    }

    //palindrome
    @Test
    public void testPalindrome_valid() {
        assertTrue(Main.palindrome("asdfggfdsa"));
        assertTrue(Main.palindrome("ASDFggfdsa")); // case insensitive
    }

    @Test
    public void testPalindrome_invalid() {
        assertFalse(Main.palindrome("abcdefghij")); // not a palindrome
        assertFalse(Main.palindrome("abba"));       // wrong length
    }



    //wordle
    private List<WordleResponse> buildGuess(String word, LetterResponse[] responses) {
        List<WordleResponse> guess = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            guess.add(new WordleResponse(word.charAt(i), i, responses[i]));
        }
        return guess;
    }

    @Test
    public void testWordleMatches_allGray() throws Exception {
        List<WordleResponse> guess = buildGuess("TRAIN", new LetterResponse[]{
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER
        });

        List<String> results = Main.wordleMatches(List.of(guess));

        for (String word : results) {
            assertFalse(word.contains("T") || word.contains("R") ||
                    word.contains("A") || word.contains("I") || word.contains("N"));
        }
    }

    @Test
    public void testWordleMatches_green() throws Exception {
        List<WordleResponse> guess = buildGuess("SHARK", new LetterResponse[]{
                LetterResponse.CORRECT_LOCATION,
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER,
                LetterResponse.WRONG_LETTER
        });

        List<String> results = Main.wordleMatches(List.of(guess));

        for (String word : results) {
            assertEquals('S', word.charAt(0));
        }
    }
}
