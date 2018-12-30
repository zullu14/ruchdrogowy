package gra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends JFrame {

    Main(String nazwa) {
        super(nazwa);
        setSize(1280,1024);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame start = new Main("Ruch Drogowy");
                JPanel controlPanel = new JPanel();
                controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
                controlPanel.add(Box.createRigidArea(new Dimension(0,350)));
                JButton b_start = new ChoiceButton("ROZPOCZNIJ");
                controlPanel.add(b_start);
                start.add(controlPanel, BorderLayout.CENTER);
                start.setVisible(true);

                Okno o = new Okno("Rozgrywka");
                Plansza poleGry = new Plansza();
                o.add(poleGry, BorderLayout.CENTER);

                JPanel menubar =new JPanel();
                JLabel poziom = new MyLabel("POZIOM: "+(poleGry.getLevel()));
                JLabel punktacja = new MyLabel("PUNKTY: "+(poleGry.getPunkty()));
                JLabel predkosc = new MyLabel("PRĘDKOŚĆ: "+(poleGry.getPredkosc()));
                menubar.setLayout(new BoxLayout(menubar, BoxLayout.LINE_AXIS));
                menubar.add(poziom); //probne
                menubar.add(Box.createRigidArea(new Dimension(80, 0)));
                menubar.add(punktacja); //probne
                menubar.add(Box.createRigidArea(new Dimension(80, 0)));
                menubar.add(predkosc); //probne
                menubar.add(Box.createHorizontalGlue());
                JButton mButton = new MenuButton("MENU");
                menubar.add(mButton);
                o.add(menubar, BorderLayout.PAGE_END);
                o.setVisible(false);

                JFrame oknoMenu = new Main("MENU");
                JPanel menuPanel = new JPanel();
                menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
                JButton resumeB = new ChoiceButton("WRÓĆ DO GRY");
                JButton newGameB = new ChoiceButton("NOWA GRA");
                JButton showStatsB= new ChoiceButton("RANKING");
                JButton finishB = new ChoiceButton("ZAKOŃCZ GRĘ");
                menuPanel.add(Box.createRigidArea(new Dimension(0, 60)));
                menuPanel.add(resumeB);
                menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                menuPanel.add(newGameB);
                menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                menuPanel.add(showStatsB);
                menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                menuPanel.add(finishB);
                oknoMenu.add(menuPanel, BorderLayout.CENTER);

                /* Okno z rankingiem */
                Ranking ranking = new Ranking("Ranking", poleGry.getPunkty());
                JButton rankMenuButton = new MenuButton("MENU");
                JPanel menuButtonBar = new JPanel();
                menuButtonBar.setLayout(new BoxLayout(menuButtonBar, BoxLayout.LINE_AXIS));
                menuButtonBar.add(Box.createHorizontalGlue());
                menuButtonBar.add(rankMenuButton);
                ranking.add(menuButtonBar, BorderLayout.PAGE_END);
                ranking.setVisible(false);


                Timer timer0 = new Timer(100, new ActionListener() {
                    int lev0 = 1;
                    int v0 = 20;
                    int p0 = 0;
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        // gdy zmieni się poziom
                        if(lev0 != poleGry.getLevel()) {
                            poziom.setText("POZIOM: "+(poleGry.getLevel()));
                            lev0 = poleGry.getLevel();
                        }
                        // gdy zmieni się prędkość
                        if(v0 != poleGry.getPredkosc()) {
                            predkosc.setText("PRĘDKOŚĆ: "+(poleGry.getPredkosc()));
                            v0 = poleGry.getPredkosc();
                        }
                        // gdy zmienią się punkty
                        if(p0 != poleGry.getPunkty()) {
                            punktacja.setText("PUNKTY: "+(poleGry.getPunkty()));
                            p0 = poleGry.getPunkty();
                        }

                    }
                });

                //actionEvent
                b_start.addActionListener(actionEvent -> {

                    start.setVisible(false);
                    o.setVisible(true);
                    poleGry.createBufferStrategy(2);
                    poleGry.firstLevel();
                    timer0.start();


                }); //actionListener

                mButton.addActionListener(actionEvent -> {

                    poleGry.freezeLevel();
                    timer0.stop();
                    o.setVisible(false);
                    oknoMenu.setVisible(true);

                }); //actionListener

                resumeB.addActionListener(actionEvent -> {

                    oknoMenu.setVisible(false);
                    o.setVisible(true);
                    poleGry.createBufferStrategy(2);
                    poleGry.resumeLevel();
                    timer0.start();
                });

                newGameB.addActionListener(actionEvent -> {

                    oknoMenu.setVisible(false);
                    o.setVisible(true);
                    poleGry.createBufferStrategy(2);
                    poleGry.resetLevel();
                    timer0.restart();

                });

                showStatsB.addActionListener(actionEvent -> {

                    oknoMenu.setVisible(false);
                    ranking.setPunkty(poleGry.getPunkty());
                    ranking.setVisible(true);
                });

                rankMenuButton.addActionListener(actionEvent -> {

                    ranking.setVisible(false);
                    oknoMenu.setVisible(true);

                }); //actionListener

                finishB.addActionListener(actionEvent -> {

                    oknoMenu.setVisible(false);
                    System.exit(0);
                });

            } //run
        }); //invokeLater

    } //main
} //class Main

