import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordleResponse {
    char c;
    int index;
    LetterResponse resp;

    public WordleResponse(char c, int index, LetterResponse resp) {
        this.c = c;
        this.index = index;
        this.resp = resp;
    }
}
