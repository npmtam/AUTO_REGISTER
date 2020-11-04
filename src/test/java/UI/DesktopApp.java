package UI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.IOException;

public class DesktopApp {

    public static void main(String[] args){
        //Create windows
        JFrame frame = new JFrame();


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
        sleepTimeLabel.setBounds(300, 100, 150,20);

        //Time sleep input
        JTextField sleepTimeInput = new JTextField();
        sleepTimeInput.setBounds(300,120, 150, 20);

        //Status Label
        JLabel statusLabel = new JLabel("Status");
        statusLabel.setBounds(30, 220, 300,30);

        //Run button
        JButton runBtn = new JButton("Run");
        runBtn.setBounds(190, 170, 100, 30);

        //Button action
        runBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Validate input
                String browser = "chrome";
                String test = "Sites.VuaKing";
                String url = urlTextbox.getText();
                String count = countInput.getText();
                String sleep = sleepTimeInput.getText();
                if(url.contains("http")){
                    statusLabel.setText("URL khong nen chua http");
                }
                if (!count.matches("\\d+")){
                    statusLabel.setText("Vui lòng chỉ nhập số");
                } else if(!sleep.matches("\\d+")){
                    statusLabel.setText("Vui lòng chỉ nhập số");
                }
                if(Integer.parseInt(sleep)>10){
                    statusLabel.setText("Thời gian chờ không nên quá 10 giây");
                }

                //Run test
                try {
                      Process process = Runtime.getRuntime().exec("mvn.cmd clean test -Dbrowser=\"" + browser + "\" -Durl=\"" + url +"\" -DinvocationCount=\"" + count + "\" -DsleepAfterTest=\"" + sleep + "\" -Dtest=" + test);

                } catch (IOException error) {
                    error.printStackTrace();
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
        frame.setTitle("Auto Bet sites");
        frame.setSize(500,300);
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
