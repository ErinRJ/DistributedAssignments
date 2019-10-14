import java.io.*; 
import java.rmi.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FileClient implements ActionListener{
    //swing value for main page (mainFrame)
    static JFrame mainFrame;
    static JLabel result;
    static FileInterface fi;

    public static void main(String argv[]) {
        FileClient fc = new FileClient();

        //create the main page
        createMainPage(fc);
        mainFrame.setVisible(true);

        //connect to the server
        try {
            //connect to the registry
            fi = (FileInterface) Naming.lookup(FileInterface.SERVICENAME);
            //call function and print out result
            result.setText("Connection established");

            } catch(Exception e) {
            System.err.println("FileServer exception: "+ e.getMessage());
            e.printStackTrace();
        }
   }

   public static void createMainPage(FileClient fc){
        //create the frame
        mainFrame = new JFrame("Main Page");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,300);

        //add buttons for each of the five functions in one panel
        JPanel buttonPanel = new JPanel();
        JButton btn1 = new JButton("Button 1");
        JButton btn2 = new JButton("Button 2");
        JButton btn3 = new JButton("Button 3");
        JButton btn4 = new JButton("Button 4");
        JButton btn5 = new JButton("Button 5");
        btn1.addActionListener(fc);
        btn2.addActionListener(fc);
        btn3.addActionListener(fc);
        btn4.addActionListener(fc);
        btn5.addActionListener(fc);
        //add buttons to the panel
        buttonPanel.add(btn1);
        buttonPanel.add(btn2);
        buttonPanel.add(btn3);
        buttonPanel.add(btn4);
        buttonPanel.add(btn5);


        //create second panel for output from implementation class
        JPanel outputPanel = new JPanel();
        //add label to panel
        result = new JLabel("Result here");
        outputPanel.add(result);

       //add the panels to the frame
       mainFrame.add(buttonPanel);
       mainFrame.add(outputPanel);

       mainFrame.getContentPane().add(BorderLayout.NORTH, buttonPanel);
       mainFrame.getContentPane().add(BorderLayout.CENTER, outputPanel);

   }

    public void actionPerformed(ActionEvent evt){
        String event = evt.getActionCommand();
        try {
            if (event.contentEquals("Button 1")) {
                String result = fi.button1();
                System.out.println(result);
            } else if (event.contentEquals("Button 2")) {
                String result = fi.button2();
                System.out.println(result);
            } else if (event.contentEquals("Button 3")) {
                String result = fi.button3();
                System.out.println(result);
            } else if (event.contentEquals("Button 4")) {
                String result = fi.button4();
                System.out.println(result);
            } else if (event.contentEquals("Button 5")) {
                String result = fi.button5();
                System.out.println(result);
            }
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }
}