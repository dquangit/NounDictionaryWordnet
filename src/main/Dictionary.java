package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Dictionary {

    private static final String DICT_PATH = "data/data.txt";

    private Map<String, Set<Synsets>> data;

    public String getDefinition(String word){
        return getDefinition(data.get(word));
    }

    private String getDefinition(Set<Synsets> synsets) {
        if (synsets == null) {
            return "Không tìm thấy.";
        }
        StringBuilder result = new StringBuilder();
        for (Synsets item : synsets) {
            result.append("\r\n");
            result.append(item.getDictionaryFormat());
            result.append("\r\n");
        }
        return result.toString();
    }

    public String[] getWordsAsArray(){
        String[] result = new String[data.size()];
        int counter = 0;
        for (String word : this.data.keySet()) {
            result[counter] = word;
            counter++;
        }
        Arrays.sort(result);
        return result;
    }

    public void loadDict() throws IOException {
        data = new HashMap<>();
        List<Synsets> listData = loadSynsets();
        for (Synsets item : listData) {
            for (String word : item.getLemma()) {
                Set<Synsets> synsetsList = data.get(word);
                if (synsetsList == null) {
                    synsetsList = new HashSet<>();
                }
                synsetsList.add(item);
                data.put(word, synsetsList);
            }
        }
    }

    private List<Synsets> loadSynsets() throws IOException {
        Path filePath = Paths.get(DICT_PATH);
        Stream<String> stringStream = Files.lines(filePath);
        List<Synsets> result = new ArrayList<>();
        stringStream.forEach(s -> {
            Synsets synsets = Synsets.synsetsFromString(s);
            if (synsets != null) {
                result.add(synsets);
            }
        });
        return result;
    }
}