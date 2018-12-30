package gra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Ranking extends JFrame {

    private File rankFile = new File("ranked.txt");
    private final static String newline = "\n";
    private Font font = new Font("Calibri", Font.BOLD, 40);
    private int punkty;

    Ranking(String nazwa, int pkt) {
        super(nazwa);
        this.punkty = pkt;
        setSize(1280,1024);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel headerPanel = new JPanel();
        JTextField wpis = new JTextField(40);
        wpis.setFont(font);
        JLabel label1 = new JLabel("Wpisz nazwę gracza: ");
        label1.setFont(font);
        label1.setPreferredSize(new Dimension(400, 40));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
        headerPanel.add(label1);
        headerPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        headerPanel.add(wpis);

        JTextArea textArea = new JTextArea(10, 1);
        textArea.setEditable(false);
        textArea.setFont(font);
        wpis.addActionListener(actionEvent -> {
            String text = wpis.getText();
            saveScore(text +" : "+ punkty);
            textArea.setText(null);
            for(String str : readScore()) {
                textArea.append(str + newline);
            }
        });
        for(String str : readScore()) {
            textArea.append(str + newline);
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    } //Ranking

    private void saveScore(String stats) {
        try{
            if(rankFile.exists()){
                BufferedWriter zapis = new BufferedWriter(new FileWriter("ranked.txt", true));
                zapis.append(newline + stats);
                zapis.close();
            }
            //else rankFile.createNewFile();
        } catch (Exception e){
            System.out.println("IO exceptiion "+e);
        }
    }

    private ArrayList<String> readScore() {
        ArrayList<String> result = new ArrayList<>();

        try (Scanner s = new Scanner(new FileReader("ranked.txt"))) {
            while (s.hasNext()) {
                result.add(s.nextLine());
            }
            return result;
        } catch (Exception e) {
            System.out.println("IO exceptiion "+e);
        }
        return null;
    }

    void setPunkty(int punkty) {
        this.punkty = punkty;
    }


}
