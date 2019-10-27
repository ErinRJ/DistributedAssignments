import java.io.*; 
import java.rmi.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FileClient implements ActionListener{
    //swing value for main page (mainFrame)
    static JFrame mainFrame;
    static JFrame viewPostsFrame;
    static JLabel result;
    static FileInterface fi;


    //swing values for the new post frame
    static JFrame newPostFrame;
    static JTextArea locT;
    static JTextArea dogBT;
    static JTextArea durationT;

    public static void main(String argv[]) {
        //button action handler
        FileClient fc = new FileClient();
        //create the main page
        createMainPage(fc);
        createNewPostPage(fc);
        createViewPostsPage(fc);

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
        mainFrame = new JFrame("Pet Sitters");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,300);

        //add buttons for each of the five functions in one panel
        JPanel buttonPanel = new JPanel();
        JButton createBtn = new JButton("Create New Posting");
        JButton viewBtn = new JButton("View Available Postings");
        createBtn.addActionListener(fc);
        viewBtn.addActionListener(fc);
        //add buttons to the panel
        buttonPanel.add(createBtn);
        buttonPanel.add(viewBtn);


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

   //create the frame to create new postings
    public static void createNewPostPage(FileClient fc) {
        //create frame with designated sizes
        newPostFrame = new JFrame("Pet Sitters: Create New Posting");
        newPostFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newPostFrame.setSize(800,300);

        //create the information panel
        JPanel infoPanel = new JPanel();

        //create the labels and input areas
        JLabel inst = new JLabel("Please enter the following information: ");
        JLabel loc = new JLabel("Location: ");
        locT = new JTextArea(1,8);
        JLabel dogB = new JLabel("Dog Breed:");
        dogBT = new JTextArea(1,8);
        JLabel duration = new JLabel("Dates: ");
        durationT = new JTextArea(1,8);

        //add the labels and text areas to the panel
        infoPanel.add(inst);
        infoPanel.add(loc);
        infoPanel.add(locT);
        infoPanel.add(dogB);
        infoPanel.add(dogBT);
        infoPanel.add(duration);
        infoPanel.add(durationT);

        //panel for submit button
        JPanel submitPanel = new JPanel();
        //add a button to the panel
        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.addActionListener(fc);
        submitPanel.add(submitBtn);

        //add the panels to the frame
        newPostFrame.add(infoPanel);
        newPostFrame.add(submitPanel);
        //organize the panels on the frame
        newPostFrame.getContentPane().add(BorderLayout.NORTH, infoPanel);
        newPostFrame.getContentPane().add(BorderLayout.CENTER, submitPanel);
    }

    //create the frame to view all postings
    public static void createViewPostsPage(FileClient fc) {
        //create frame with designated sizes
        viewPostsFrame = new JFrame("Pet Sitters: View Available Postings");
        viewPostsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewPostsFrame.setSize(800,300);
    }

    public void actionPerformed(ActionEvent evt){
        String event = evt.getActionCommand();
        try {
            if (event.contentEquals("Create New Posting")){
                newPostFrame.setVisible(true);
            } else if (event.contentEquals("View Available Postings")) {
                viewPostsFrame.setVisible(true);
            } else if (event.contentEquals("SUBMIT")) {
                System.out.println("New posting information submitted");
                //send the information to the server
                fi.createPost(locT.getText(), dogBT.getText(), durationT.getText());
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