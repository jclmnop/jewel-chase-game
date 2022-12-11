package App;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Static class which retrieves the current message of the day.
 *
 * @author Jonny
 * @version 1.0
 */
public class MessageOfTheDay {
    private static final String BASE_API = "http://cswebcat.swan.ac.uk/";
    private static final URI PUZZLE_API = URI.create(BASE_API + "puzzle");
    private static final String MESSAGE_API = BASE_API + "message?solution=";
    private static final HttpClient client = HttpClient.newBuilder().build();

    private MessageOfTheDay() {};

    /**
     * Retrieves the current message of the day from API.
     * @return String containing message of the day.
     * @throws IOException If there's an I/O error during http request.
     * @throws InterruptedException If http requests are interrupted.
     */
    public static String getMessageOfTheDay() throws IOException, InterruptedException {
        String puzzle = MessageOfTheDay.fetchPuzzleString();
        String solution = MessageOfTheDay.solvePuzzle(puzzle);

        return fetchMessageOfTheDay(solution);
    }

    private static String fetchPuzzleString() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(PUZZLE_API)
            .GET()
            .build();
        HttpResponse<String> puzzleResponse = MessageOfTheDay.client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );
        return puzzleResponse.body();
    }

    private static String solvePuzzle(String puzzleString) {
        char[] chars = puzzleString.toCharArray();
        boolean shiftBackwards = true;
        for (int i = 0; i < chars.length; i++) {
            int offset = shiftBackwards ? -i - 1 : i + 1;
            chars[i] = shiftCharacter(chars[i], offset);
            shiftBackwards = !shiftBackwards;
        }
        String solution = new String(chars) + "CS-230";
        return solution.length() + solution;
    }

    private static char shiftCharacter(char character, int offset) {
        final int START = 65;
        final int END = 90;

        int characterNum = character + offset;

        if (characterNum < START) {
            int difference = START - characterNum - 1;
            characterNum = END - difference;
        } else if (characterNum > END) {
            int difference = characterNum - END - 1;
            characterNum = START + difference;
        }

        return (char) characterNum;
    }

    private static String fetchMessageOfTheDay(String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(MESSAGE_API + query))
            .GET()
            .build();
        HttpResponse<String> puzzleResponse = MessageOfTheDay.client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );
        return puzzleResponse.body().split(" \\(")[0];
    }
}
