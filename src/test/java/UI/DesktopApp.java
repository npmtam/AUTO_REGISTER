package UI;

import commons.Constants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.*;
import java.util.Scanner;

public class DesktopApp {
    private static JTextArea console;
    private static JScrollPane scrollPane;
    private static String logFile = System.getProperty("user.dir") + "\\logs\\AutoBetSites.log";
    private static PrintStream standarOut;

    public static void main(String[] args) {
        //Create windows
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        //Label type of site
        JLabel typeLabel = new JLabel("Loại trang: ");
        typeLabel.setBounds(30, 20, 100, 20);

        //Create options in combobox
        String[] listSelecting = {"VuaKing", "FA88", "Game789"};
        //Create combobox
        JComboBox comboBox = new JComboBox(listSelecting);
        comboBox.setBounds(30, 40, 150, 20);
        comboBox.setSelectedIndex(0);

        //URL label
        JLabel urlLabel = new JLabel("URL:");
        urlLabel.setBounds(300, 30, 150, 20);

        //URL Textbox
        JTextField urlTextbox = new JTextField();
        urlTextbox.setBounds(300, 50, 150, 20);

        //Invocation count label
        JLabel countLabel = new JLabel("Số lượng account:");
        countLabel.setBounds(30, 100, 150, 20);

        //Invacation count textbox
        JTextField countInput = new JTextField();
        countInput.setBounds(30, 120, 150, 20);

        //Time sleep label
        JLabel sleepTimeLabel = new JLabel("Thời gian chờ sau khi tạo:");
        sleepTimeLabel.setBounds(300, 100, 150, 20);

        //Time sleep input
        JTextField sleepTimeInput = new JTextField();
        sleepTimeInput.setBounds(300, 120, 150, 20);

        //Status Label
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(30, 220, 300, 30);

        //Run button
        JButton runBtn = new JButton("Run");
        runBtn.setBounds(190, 170, 100, 30);


        //Scroll pane
        scrollPane = new JScrollPane();
//        scrollPane.setPreferredSize(new Dimension(440, 100));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Text area to show the console
        console = new JTextArea(30, 10);
//        console.setBounds(500, 50, 440, 200);
        console.setEditable(false);
        console.setLineWrap(true);
        PrintStream printStream = new PrintStream(new CustomOutputStream(console));
        standarOut = System.out;
        System.setOut(printStream);
        System.setErr(printStream);

        scrollPane.setBounds(500, 50, 440, 200);
        scrollPane.getViewport().add(console);


        //Auto scroll follow the log
//        DefaultCaret caret = (DefaultCaret) console.getCaret();
//        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        //Button action
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String browser = "chrome";
                String test = "Sites.VuaKing";
                String url = urlTextbox.getText();

                //Get domain
                String[] urlInputted = urlTextbox.getText().split("//");
                String urlForCmd = urlInputted[1];

                String count = countInput.getText();
                String sleep = sleepTimeInput.getText();

                if (!url.contains("http://")) {
                    statusLabel.setText("Vui lòng nhập đúng URL");
                }
                if (!count.matches("\\d+")) {
                    statusLabel.setText("Vui lòng chỉ nhập số");
                } else if (!sleep.matches("\\d+")) {
                    statusLabel.setText("Vui lòng chỉ nhập số");
                }
                if (Integer.parseInt(sleep) > 10) {
                    statusLabel.setText("Thời gian chờ không nên quá 10 giây");
                }

                //Run test
                try {
//                    System.out.println("mvn.cmd clean test -Dbrowser=\"" + browser + "\" -Durl=\"" + urlForCmd + "\" -DinvocationCount=\"" + count + "\" -DsleepAfterTest=\"" + sleep + "\" -Dtest=" + test);
                    Process process = Runtime.getRuntime().exec("mvn.cmd clean test -Dbrowser=\"" + browser + "\" -Durl=\"" + urlForCmd + "\" -DinvocationCount=\"" + count + "\" -DsleepAfterTest=\"" + sleep + "\" -Dtest=" + test);
                    System.out.println(Constants.ACCOUNTS_SUCCESS);

                    //Print console to JTextarea
                    InputStream stderr = process.getErrorStream();
                    InputStreamReader isr = new InputStreamReader(stderr);
                    BufferedReader br = new BufferedReader(isr);
                    String line = null;

                    //Read log file
                    FileInputStream fileInputStream = new FileInputStream(logFile);
                    Scanner scanner = new Scanner(fileInputStream);

                    while ((line = br.readLine()) != null || scanner.hasNextLine()) {
//                        console.append(line);
                          standarOut.println(line);
                          standarOut.println(scanner.nextLine());
//                        console.append(scanner.nextLine());
                        if (line.contains("Exception")) {
                            statusLabel.setText("Có lỗi xảy ra");
                        }
//                        console.setCaretPosition(console.getText().length());
                        console.setCaretPosition(console.getDocument().getLength());
                    }


                } catch (IOException error) {
                    error.printStackTrace();
                    statusLabel.setText("Đã có lỗi xảy ra " + error);
                    System.out.println("Đã có lỗi xảy ra: " + error);
                }
            }
        });


        frame.add(typeLabel);
        frame.add(comboBox);
        frame.add(urlTextbox);
        frame.add(urlLabel);
        frame.add(countLabel);
        frame.add(countInput);
        frame.add(sleepTimeLabel);
        frame.add(sleepTimeInput);
        frame.add(runBtn);
        frame.add(statusLabel);
//        frame.add(console);
        frame.add(scrollPane);
        frame.setTitle("Auto Bet sites");
        frame.setSize(1000, 300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void consoleLog() {
        Exception ex = new Exception("something went wrong");
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        System.out.println("stacktrace = " + stacktrace);
    }

}
