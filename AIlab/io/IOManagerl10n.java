package AIlab.io;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class IOManagerl10n {
    private final String PATH;
    private final String LANGUAGE;
    private final HashMap<String, String> dictionary = new HashMap<>();

    public IOManagerl10n (String path, String language) {
        this.PATH = path;
        this.LANGUAGE = language;
    }

    public boolean parseMessageFile() {
        //TODO Opens file and processes lines via _parseMessages()
        try {
            Scanner scanner = new Scanner(new File(PATH));
            parseMessages(scanner);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    private void parseMessages(Scanner scanner) {
        // найти первую строку с [слово]
        // найти слово нужного языка язык=перевод
        // добавить в словарь пару слово-перевод
        String currentTitleWord = null, currentTranslationWord;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("[") && line.endsWith("]")) {
                currentTitleWord = line.substring(1,line.length()-1);
                continue;
            }
            if (line.startsWith(LANGUAGE+"=")) {
                currentTranslationWord = line.substring(LANGUAGE.length()+1);
                if (currentTitleWord != null)
                    dictionary.put(currentTitleWord, currentTranslationWord);
            }
        }
    }

    public void printAll() {
        StringBuilder stringBuilder = new StringBuilder();
        dictionary.keySet().forEach(
                (String keyWord) -> {
                    String valueWord = dictionary.get(keyWord);
                    stringBuilder.append(keyWord).append(" -> ").append(valueWord).append("\n");
                }
        );
        System.out.println(stringBuilder);
    }

    public String getMessage(String messageCode) {
        String message = "<blank message - no translation for [" + messageCode + "] found>";
        if (dictionary.containsKey(messageCode)) {
            message = dictionary.get(messageCode);
        }
        return message;
    }
}
