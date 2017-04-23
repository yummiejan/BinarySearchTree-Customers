package View;

import Control.MainController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class InteractionPanelHandler {
    private JPanel panel;
    private JButton displayTreeButton;
    private JTextArea output;
    private JButton traverseButton;
    private JTextField nameTextField;
    private JTextField salesTextField;
    private JButton insertButton;
    private JButton searchSalesButton;
    private JButton deleteButton;
    private JButton searchNameButton;
    private JButton findLastNameButton;
    private JButton sumUpSalesButton;
    private JButton listUpperNamesButton;
    private JButton surpriseSurpriseButton;
    private JTextField sortedByTextfield;

    private MainController mainController;
    private DrawingPanel treeDisplayPanel;

    public InteractionPanelHandler(MainController mainController, DrawingPanel treeDisplayPanel) {
        this.mainController = mainController;
        this.treeDisplayPanel = treeDisplayPanel;
        createJObjects();
    }

    private void createJObjects(){
        displayTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainController.showTree(treeDisplayPanel);
                showText("Der Baum wird nun dargestellt. Eventuell fand eine Aktualisierung statt.");
            }
        });

        traverseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showText(mainController.traverse());
            }
        });
        findLastNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAnswer(mainController.searchLastName());
            }
        });
        sumUpSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showText("Ermittelter Gesamt-Umsatz: " + String.valueOf(mainController.sumUpSales()));
            }
        });
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameTextField.getText().equals("") && !salesTextField.getText().equals("")){
                    String name = nameTextField.getText();
                    int sales = Integer.parseInt(salesTextField.getText());
                    boolean inserted = mainController.insert(name,sales);
                    if(inserted){
                        mainController.showTree(treeDisplayPanel);
                        showText("Der Kunde " + name + " mit dem Umsatz " + sales + " wurde dem Baum hinzugefügt.");
                    }else{
                        showText("Es befindet sich bereits ein Kunde mit dem Namen " + name + " in der Datenmenge.");
                    }
                }else{
                    showText("Bitte die Textfelder ausfüllen.");
                }
            }
        });
        searchNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameTextField.getText().equals("")){
                    String name = nameTextField.getText();
                    String[] answer = mainController.searchName(name);
                    handleAnswer(answer);
                }else{
                    showText("Bitte den zu suchenden Namen eintragen.");
                }
            }
        });
        searchSalesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!salesTextField.getText().equals("")) {
                    int sales = Integer.parseInt(salesTextField.getText());
                    String[] answer = mainController.searchSales(sales);
                    handleAnswer(answer);
                }else{
                    showText("Bitte den zu suchenden Umsatz eintragen.");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!nameTextField.getText().equals("")) {
                    String name = nameTextField.getText();
                    boolean deleted = mainController.delete(name);
                    if(deleted){
                        mainController.showTree(treeDisplayPanel);
                        showText("Der Kunde " + name + " aus dem Baum entfernt.");
                    }else{
                        showText("Es befindet sich bereits ein Kunde mit dem Namen " + name + " in der Datenmenge.");
                    }
                }else{
                    showText("Bitte den zu löschenden Kunden eintragen.");
                }
            }
        });
        listUpperNamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = "";
                if(!nameTextField.getText().equals("")){
                    name = nameTextField.getText();
                }
                String[][] answerArray = mainController.listUpperNames(name);
                for(int i = 0; i < answerArray.length; i++){
                    handleAnswer(answerArray[i]);
                }
            }
        });

        surpriseSurpriseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findLastNameButton.setEnabled(!findLastNameButton.isEnabled());
                insertButton.setEnabled(!insertButton.isEnabled());
                searchNameButton.setEnabled(!searchNameButton.isEnabled());
                deleteButton.setEnabled(!deleteButton.isEnabled());
                mainController.surprise();
                mainController.showTree(treeDisplayPanel);
                if(sortedByTextfield.getText().equals("Name")) {
                    sortedByTextfield.setText("Sales");
                    showText("Customers are now sorted by: " + sortedByTextfield.getText());
                }else{
                    sortedByTextfield.setText("Name");
                    showText("Customers are now sorted by: " + sortedByTextfield.getText());
                }
            }
        });
    }

    public JPanel getPanel(){
        return panel;
    }

    private void showText(String text){
        output.setText(output.getText() + "\n" + text);
    }

    private void handleAnswer(String[] answer){
        if(answer.length == 2){
            nameTextField.setText(answer[0]);
            salesTextField.setText(answer[1]);
            showText("Es wurde der Kunde " + answer[0] + " mit dem Umsatz " + answer[1] + " gefunden.");
        }else{
            showText(answer[0]);
        }
    }
}
