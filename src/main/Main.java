package main;
import javax.swing.JFrame;

public class Main {

    static MainPanel mainPanel;
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Audio Converter");
        window.setResizable(false);

        System.out.println(System.getProperty("user.dir"));

        mainPanel = new MainPanel();

        window.add(mainPanel);
        window.pack();

        window.setLocationRelativeTo(null);  // centre window
        window.setVisible(true);

    }
}
