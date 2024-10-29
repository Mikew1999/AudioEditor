package main;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import audio_edit.AudioEditor;

public class MainPanel extends JPanel implements ActionListener {
    int maxWidth = 500;
    int maxHeight = 750;

    static private final String newline = "\n";
    JButton openButton;
    JTextArea log;
    JFileChooser fc, folderChooser;
    FileNameExtensionFilter fileNameExtensionFilter;

    AudioEditor audioEditor;

    public MainPanel() {

        super(new BorderLayout());
        this.setPreferredSize(new Dimension(maxWidth, maxHeight));
        this.setBackground(Color.LIGHT_GRAY);

        // create text area for logs 
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        // create a file chooser
        fc = new JFileChooser();
        fileNameExtensionFilter = new FileNameExtensionFilter("Audio File", "wav", "mp3", "m4a");  // only allow these extensions
        fc.setFileFilter(fileNameExtensionFilter);

        // open button
        openButton = new JButton("Convert a file to wav...",
                                 createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(MainPanel.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filename = file.getName();
                log.append("Opening: " + filename + "." + newline);

                if (filename.length() < 5) {
                    log.append("File " + filename + " is invalid");
                } else {
                    String filepath = file.getAbsolutePath();
                    audioEditor = new AudioEditor(filepath);
                    String error = audioEditor.editAudioFile();
                    if (error.length() > 0) {
                        log.append(error);
                    } else {
                        log.append("Success!");
                    }
                }
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainPanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new MainPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}