package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame ("Từ điển danh từ Wordnet");
        frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add (new DictionaryGUI());
        frame.pack();
        frame.setVisible (true);
    }

}
