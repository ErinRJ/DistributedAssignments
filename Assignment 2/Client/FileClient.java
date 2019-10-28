import java.rmi.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FileClient implements ActionListener{
    //swing value for main page (mainFrame)
    static JFrame mainFrame;
    static JFrame viewPostsFrame;
    static JFrame viewMyPostsFrame;
    static JFrame viewJobs;
    static JLabel result;
    static JTextArea usernameT;
    static FileInterface fi;
    static String username;

    //swing values for the new post frame
    static JFrame newPostFrame;
    static JTextArea locT;
    static JTextArea dogBT;
    static JTextArea durationT;

    //swing values for the view posts frame
    static JTextArea selectT;


    //button action handler
    static FileClient fc = new FileClient();

    public static void main(String argv[]) {

        //create the main page
        createMainPage(fc);
        createNewPostPage(fc);

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

        //panel for person's username
        JPanel usernamePanel = new JPanel();
        JLabel usernameLabel = new JLabel("Please Enter Username");
        usernameT = new JTextArea(1,10);
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameT);

        //add buttons for each of the five functions in one panel
        JPanel buttonPanel = new JPanel();
        JButton createBtn = new JButton("Create New Posting");
        JButton viewBtn = new JButton("View Available Postings");
        JButton viewMyPosts = new JButton("View My Postings");
        JButton viewMyJobs = new JButton("View My Jobs to do");
        createBtn.addActionListener(fc);
        viewBtn.addActionListener(fc);
        viewMyPosts.addActionListener(fc);
        viewMyJobs.addActionListener(fc);
        //add buttons to the panel
        buttonPanel.add(createBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(viewMyPosts);
        buttonPanel.add(viewMyJobs);


        //create second panel for output from implementation class
        JPanel outputPanel = new JPanel();
        //add label to panel
        result = new JLabel("Communication result here");
        outputPanel.add(result);

       //add the panels to the frame
       mainFrame.add(usernamePanel);
       mainFrame.add(buttonPanel);
       mainFrame.add(outputPanel);

       //organize panels on the frame
       mainFrame.getContentPane().add(BorderLayout.NORTH, usernamePanel);
       mainFrame.getContentPane().add(BorderLayout.CENTER, buttonPanel);
       mainFrame.getContentPane().add(BorderLayout.SOUTH, outputPanel);
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

        //panel for back button
        JPanel backPanel = new JPanel();
        //add a button to the panel
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(fc);
        backPanel.add(backBtn);

        //add the panels to the frame
        newPostFrame.add(infoPanel);
        newPostFrame.add(submitPanel);
        //organize the panels on the frame
        newPostFrame.getContentPane().add(BorderLayout.NORTH, infoPanel);
        newPostFrame.getContentPane().add(BorderLayout.CENTER, submitPanel);
        newPostFrame.getContentPane().add(BorderLayout.SOUTH, backPanel);
    }

    //create the frame to view all postings
    public static void createViewPostsPage(FileClient fc) throws RemoteException {
        //create frame with designated sizes
        viewPostsFrame = new JFrame("Pet Sitters: View Available Postings");
        viewPostsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewPostsFrame.setSize(800,1000);
        Container content = viewPostsFrame.getContentPane();

        //the panel for posting selection
        JPanel selectionPanel = new JPanel();
        JLabel selectLabel = new JLabel("Enter the posting # to take");
        selectT = new JTextArea(1,8);
        JButton selectBtn = new JButton("Accept Job");
        selectBtn.addActionListener(fc);
        //add the elements to the panel
        selectionPanel.add(selectLabel);
        selectionPanel.add(selectT);
        selectionPanel.add(selectBtn);


        //the table panel
        JPanel tablePanel = new JPanel();

        //get available postings from the database
        String[] positions = fi.viewPosts();
        System.out.println("Received from the server: " + Arrays.deepToString(positions));

        System.out.println("Posts available in database");
        //each row in this array = one posting
        //separate these by the commas and put into a 2d array
        Object[][] rows = new Object[positions.length][5];
        for(int i = 0; i< positions.length; i++){
            Object[] split = positions[i].split(",");

            //position id
            rows[i][0] = split[0];
            //location
            rows[i][1] = split[1];
            //dog breed
            rows[i][2] = split[2];
            //duration
            rows[i][3] = split[3];
            //availability
            rows[i][4] = split[4];
        }
        System.out.println(Arrays.deepToString(rows));
        //display nicely:
        //declare column headings
        Object columns[] = { "Posting ID", "Location", "Dog Breed", "Duration", "Job Taken?" };

        //initiate a table model, put in 2d array of rows, and 1d columns array
        DefaultTableModel tableModel = new DefaultTableModel(rows, columns);
        JTable table = new JTable(tableModel);

        //set table properties
        table.setRowHeight(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablePanel.add(new JScrollPane(table));
        //panel for back button
        JPanel backPanel = new JPanel();
        //add a button to the panel
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(fc);
        backPanel.add(backBtn);

        //add the panels to the frame
        viewPostsFrame.add(tablePanel);
        viewPostsFrame.add(selectionPanel);
        viewPostsFrame.getContentPane().add(BorderLayout.NORTH, selectionPanel);
        viewPostsFrame.getContentPane().add(BorderLayout.CENTER, tablePanel);
        viewPostsFrame.getContentPane().add(BorderLayout.SOUTH, backPanel);
    }

    //create the frame to view all postings
    public static void createViewMyPostsPage(FileClient fc, Object[] postings) throws RemoteException {
        //create frame with designated sizes
        viewMyPostsFrame = new JFrame("Pet Sitters: View My Postings");
        viewMyPostsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewMyPostsFrame.setSize(800,1000);
        Container content = viewMyPostsFrame.getContentPane();

        //the table panel
        JPanel tablePanel = new JPanel();
        //convert the objects array to a string array
        String[] stringArray = Arrays.copyOf(postings, postings.length, String[].class);

        System.out.println("Received from the server: " + Arrays.deepToString(postings));

        //each row in this array = one posting
        //separate these by the commas and put into a 2d array
        Object[][] rows = new Object[postings.length][7];
        for(int i = 0; i< postings.length; i++){
            String[] split = stringArray[i].split(",");
            //position id
            rows[i][0] = split[0];
            //location
            rows[i][1] = split[1];
            //dog breed
            rows[i][2] = split[2];
            //duration
            rows[i][3] = split[3];
            //owner
            rows[i][4] = split[4];
            //availability
            rows[i][5] = split[5];
            //sitter
            rows[i][6] = split[6];

        }
        System.out.println(Arrays.deepToString(rows));
        //display nicely:
        //declare column headings
        Object columns[] = { "Posting ID", "Location", "Dog Breed", "Duration", "Owner", "Job Taken?", "Sitter" };

        //initiate a table model, put in 2d array of rows, and 1d columns array
        DefaultTableModel tableModel = new DefaultTableModel(rows, columns);
        JTable table = new JTable(tableModel);

        //set table properties
        table.setRowHeight(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablePanel.add(new JScrollPane(table));
        //panel for back button
        JPanel backPanel = new JPanel();
        //add a button to the panel
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(fc);
        backPanel.add(backBtn);

        //add the panels to the frame
        viewMyPostsFrame.add(tablePanel);
        viewMyPostsFrame.getContentPane().add(BorderLayout.CENTER, tablePanel);
        viewMyPostsFrame.getContentPane().add(BorderLayout.SOUTH, backPanel);
    }

    //create the frame to view all postings
    public static void createViewJobsTakenPage(FileClient fc, Object[] jobs) throws RemoteException {
        //create frame with designated sizes
        viewJobs = new JFrame("Pet Sitters: View My Postings");
        viewJobs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewJobs.setSize(800,1000);
        Container content = viewJobs.getContentPane();

        //the table panel
        JPanel tablePanel = new JPanel();
        //convert the objects array to a string array
        String[] stringArray = Arrays.copyOf(jobs, jobs.length, String[].class);

        System.out.println("Received from the server: " + Arrays.deepToString(jobs));

        //each row in this array = one posting
        //separate these by the commas and put into a 2d array
        Object[][] rows = new Object[jobs.length][7];
        for(int i = 0; i< jobs.length; i++){
            String[] split = stringArray[i].split(",");
            //position id
            rows[i][0] = split[0];
            //location
            rows[i][1] = split[1];
            //dog breed
            rows[i][2] = split[2];
            //duration
            rows[i][3] = split[3];
            //owner
            rows[i][4] = split[4];
            //availability
            rows[i][5] = split[5];
            //sitter
            rows[i][6] = split[6];

        }
        System.out.println(Arrays.deepToString(rows));
        //display nicely:
        //declare column headings
        Object columns[] = { "Posting ID", "Location", "Dog Breed", "Duration", "Owner", "Job Taken?", "Sitter" };

        //initiate a table model, put in 2d array of rows, and 1d columns array
        DefaultTableModel tableModel = new DefaultTableModel(rows, columns);
        JTable table = new JTable(tableModel);

        //set table properties
        table.setRowHeight(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        tablePanel.add(new JScrollPane(table));
        //panel for back button
        JPanel backPanel = new JPanel();
        //add a button to the panel
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(fc);
        backPanel.add(backBtn);

        //add the panels to the frame
        viewJobs.add(tablePanel);
        viewJobs.getContentPane().add(BorderLayout.CENTER, tablePanel);
        viewJobs.getContentPane().add(BorderLayout.SOUTH, backPanel);
    }




    //listener for each button click
    public void actionPerformed(ActionEvent evt){
        String event = evt.getActionCommand();
        try {
            if (event.contentEquals("Create New Posting")){
                username = usernameT.getText();
                newPostFrame.setVisible(true);
            } else if (event.contentEquals("View Available Postings")) {
                username = usernameT.getText();
                createViewPostsPage(fc);
                viewPostsFrame.setVisible(true);
            } else if (event.contentEquals("SUBMIT")) {
                System.out.println("New posting information submitted");
                String user = username;
                //send the information to the server
                fi.createPost(locT.getText(), dogBT.getText(), durationT.getText(), user);
                locT.setText("");
                dogBT.setText("");
                durationT.setText("");
            } else if (event.contentEquals("BACK")) {
                newPostFrame.dispose();
                viewPostsFrame.dispose();
                viewMyPostsFrame.dispose();
                viewJobs.dispose();
            } else if (event.contentEquals("Accept Job")) {
                String job = selectT.getText();
                String user = username;
                int jobInt = Integer.parseInt(job);
                System.out.println(job + " has been accepted");
                //remove the job from the available database
                fi.removePost(jobInt, user);
                //refresh the frame
                viewPostsFrame.dispose();

            } else if (event.contentEquals("View My Jobs to do")) {
                System.out.println("This person wants to see their posts");
                username = usernameT.getText();
                //get available postings from the database
                Object[] jobs = fi.viewJobsTaken(username);
                createViewJobsTakenPage(fc, jobs);
                viewJobs.setVisible(true);
            }
            else if (event.contentEquals("View My Postings")) {
                System.out.println("This person wants to see the jobs they've taken");
                username = usernameT.getText();
                //get available postings from the database
                Object[] postings = fi.viewPersonalPosts(username);
                createViewMyPostsPage(fc, postings);
                viewMyPostsFrame.setVisible(true);
            }
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }
}