package main;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class DictionaryGUI extends JPanel {

    private JButton search;
    private JList<String> listItem;
    private JTextField input;
    private JTextArea descriptionBox;
    private JScrollPane listItemScrollPane;
    private JScrollPane contentScollBar;
    private DefaultListModel<String> model;
    private Dictionary dict;
    private String[] words;

    public DictionaryGUI() {
        dict = new Dictionary();
        try {
            dict.loadDict();
        } catch (Exception e) {
            e.printStackTrace();
        }
        words = dict.getWordsAsArray();
        model = new DefaultListModel<>();
        for (String word : words) {
            model.addElement(word);
        }


        search = new JButton ("Tìm kiếm");
        input = new JTextField (5);
        descriptionBox = new JTextArea ();
        descriptionBox.setEditable(false);
        descriptionBox.setLineWrap(true);
        descriptionBox.setWrapStyleWord(true);
        listItem = new JList<>(model);
        listItemScrollPane = new JScrollPane(listItem);
        contentScollBar = new JScrollPane(descriptionBox,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listItemScrollPane.setBounds(10,45,240,320);

        setPreferredSize (new Dimension(667, 373));
        setLayout (null);

        add (search);
        add (input);
        add (contentScollBar);
        add(listItemScrollPane);

        search.setBounds (260, 10, 125, 25);
        listItem.setBounds (10, 45, 240, 315);
        input.setBounds (10, 10, 240, 25);
        descriptionBox.setBounds (260, 45, 400, 315);
        contentScollBar.setBounds(descriptionBox.getBounds());

        listItem.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()){
                JList source = (JList)event.getSource();
                if (source == null) {
                    return;
                }

                if (source.getSelectedValue() == null) {
                    return;
                }

                String selected = source.getSelectedValue().toString();
                translate(selected);
            }
        });

        input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAutoCompletedList(input.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAutoCompletedList(input.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateAutoCompletedList(input.getText());
            }
        });

        input.addActionListener(e -> translate(input.getText()));

        search.addActionListener(e -> {
            String searchTerm = input.getText();
            translate(searchTerm);
        });
    }

    private void updateAutoCompletedList(String string) {
        java.util.List<String> showList = new ArrayList<>();
        for (String word : words) {
            if (word.startsWith(string)) {
                showList.add(word);
            }
        }
        listItem.setListData(new Vector<>());
        model.clear();
        for (String word : showList) {
            model.addElement(word);
        }

        listItem.setModel(model);

    }

    private void translate(String word) {
        String meanings = dict.getDefinition(word);
        if(meanings == null) {
            descriptionBox.setText("Không tìm thấy.");
        } else {
            descriptionBox.setText(meanings);
        }
    }
}
