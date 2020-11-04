package UI;

import javax.swing.*;
import java.awt.event.FocusEvent;

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




        frame.add(typeLabel);
        frame.add(comboBox);
        frame.add(urlTextbox);
        frame.add(urlLabel);
        frame.add(countLabel);
        frame.add(countInput);
        frame.add(sleepTimeLabel);
        frame.add(sleepTimeInput);
        frame.setTitle("Auto Bet sites");
        frame.setSize(500,250);
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
