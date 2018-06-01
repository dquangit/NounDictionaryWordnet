package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Synsets {

    private static final String DICT_FORMAT = "* Từ đồng nghĩa: %s\n- %s\n- %s";

    private String synsetsId;
    private List<String> lemma;
    private String meanings;
    private String gloss;

    public List<String> getLemma() {
        return lemma;
    }

    public Synsets setLemma(List<String> lemma) {
        this.lemma = lemma;
        return this;
    }

    public String getMeanings() {
        return meanings;
    }

    public Synsets setMeanings(String meanings) {
        this.meanings = meanings;
        return this;
    }

    public String getGloss() {
        return gloss;
    }

    public Synsets setGloss(String gloss) {
        this.gloss = gloss;
        return this;
    }

    public String getSynsetsId() {
        return synsetsId;
    }

    public Synsets setSynsetsId(String synsetsId) {
        this.synsetsId = synsetsId;
        return this;
    }

    public static Synsets synsetsFromString(String string) {
        String[] stringArrays = string.split("\\|");
        if (stringArrays.length < 5) {
            return null;
        }

        String[] lemmas = stringArrays[1].split(",");
        List<String> lemmaList = new ArrayList<>();
        for (String item : lemmas) {
            lemmaList.add(item.replace("_", " ").trim());
        }

        return new Synsets()
                .setSynsetsId(stringArrays[0])
                .setMeanings(stringArrays[3])
                .setGloss(stringArrays[4])
                .setLemma(lemmaList);
    }

    public String getDictionaryFormat() {
        String listLemma = lemma.toString()
                .replace("]", "")
                .replace("[","");
        return String.format(Locale.getDefault(), DICT_FORMAT, listLemma, meanings, gloss);
    }

    @Override
    public String
    toString() {
        return "Synsets{" +
                "synsetsId='" + synsetsId + '\'' +
                ", lemma=" + lemma +
                ", meanings='" + meanings + '\'' +
                ", gloss='" + gloss + '\'' +
                '}';
    }
}
