package main;
import javax.swing.JFrame;

public class Main {

    static MainPanel mainPanel;
    public static void main(String[] args) throws Exception {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Audio Converter");
        window.setResizable(false);

        mainPanel = new MainPanel();
        
        window.add(mainPanel);
        window.pack();
        window.setLocationRelativeTo(null);  // centre window
        window.setVisible(true);
    }
}
