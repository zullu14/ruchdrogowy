package gra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;

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
                JLabel punktacja = new MyLabel("PUNKTY: "+(poleGry.getPunkty()));
                JLabel predkosc = new MyLabel("PRĘDKOŚĆ: "+(poleGry.getPredkosc()));
                menubar.setLayout(new BoxLayout(menubar, BoxLayout.LINE_AXIS));
                menubar.add(punktacja); //probne
                menubar.add(Box.createRigidArea(new Dimension(100, 0)));
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

                Timer timer0 = new Timer(100, new ActionListener() {
                    int v0 = 20;
                    int p0 = 0;
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
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

                b_start.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        start.setVisible(false);
                        o.setVisible(true);
                        poleGry.createBufferStrategy(2); //TESTOWO
                        poleGry.firstLevel();
                        timer0.start();


                    } //actionEvent
                }); //actionListener

                mButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        poleGry.freezeLevel();
                        timer0.stop();
                        o.setVisible(false);
                        oknoMenu.setVisible(true);

                    }
                }); //actionListener

                resumeB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        oknoMenu.setVisible(false);
                        o.setVisible(true);
                        poleGry.createBufferStrategy(2); //TESTOWO
                        poleGry.resumeLevel();
                        timer0.start();
                    }
                });

                newGameB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        oknoMenu.setVisible(false);
                        o.setVisible(true);
                        poleGry.createBufferStrategy(2); //TESTOWO
                        poleGry.resetLevel();
                        timer0.restart();

                    }
                });

                showStatsB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        // dodać obsługę wyświetlania wyników
                    }
                });

                finishB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        oknoMenu.setVisible(false);
                        System.exit(0);
                    }
                });

            } //run
        }); //invokeLater

    } //main

} //class Main

