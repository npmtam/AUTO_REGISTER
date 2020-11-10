package UI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.Date;

public class TestJtextarea extends JFrame {
    /**
     * The text area which is used for displaying logging information.
     */
    private JTextArea textArea;

    private JButton buttonStart = new JButton("Run");
    private JButton buttonClear = new JButton("Stop");
    private JLabel siteTypeLbl = new JLabel("Loại trang: ");
    private String[] listSelecting = {"VuaKing", "FA88", "Game789"};
    private JComboBox siteTypeCombobox = new JComboBox(listSelecting);
    private JTextField urlTxt = new JTextField("URL");

    private PrintStream standardOut;

    public TestJtextarea() {
        super("Demo printing to JTextArea");

        textArea = new JTextArea(50, 10);
        textArea.setEditable(false);
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));

        // keeps reference of standard output stream
        standardOut = System.out;

        // re-assigns standard output stream and error output stream
        System.setOut(printStream);
        System.setErr(printStream);

        // creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        add(buttonStart, constraints);

//        constraints.gridx = 1;
//        add(buttonClear, constraints);

//        constraints.gridx = 1;
//        constraints.insets = new Insets(10, 10, 10, 10);
//        add(siteTypeLbl, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(siteTypeCombobox, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(10, 80, 10, 10);
        urlTxt.setBounds(30,30,150,30);
        add(urlTxt, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 3.0;
        constraints.weighty = 4.0;
        add(new JScrollPane(textArea), constraints);

        // adds event handler for button Start
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                printLog();
            }
        });

        // adds event handler for button Clear
        buttonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // clears the text area
                try {
                    textArea.getDocument().remove(0,
                            textArea.getDocument().getLength());
                    standardOut.println("Text area cleared");
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 320);
        setLocationRelativeTo(null);    // centers on screen
    }

    /**
     * Prints log statements for testing in a thread
     */
    private void printLog() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Time now is " + (new Date()));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * Runs the program
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TestJtextarea().setVisible(true);
            }
        });
    }

}
