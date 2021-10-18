package FlensburgData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FlensburgGUI extends JFrame {

    /** Main user interface frame */
    private JFrame mainFrame;

    /** Search user interface dialog */
    private JDialog searchDialog;

    /** Add user interface dialog */
    private JDialog addDialog;

    /** Added user interface dialog */
    private JDialog addedDialog;

    /** Found user interface dialog */
    private JDialog foundDialog;

    /** Edited user interface dialog */
    private JDialog editedDialog;

    /** JButton for Method Search */
    private JButton searchButton;

    /** JButton for Method Add */
    private JButton addButton;

    /** JButton for Method Edit */
    private JButton editButton;

    /** JButton for Method Cancel */
    private JButton cancelButton;

    /** JButton for Method Remove */
    private JButton removeButton;

    /** JButton for Method Confirm edit */
    private JButton confirmButton;

    /** JLabel for TextField nachname */
    private JLabel nachnameLabel;

    /** JTextField nachname */
    private JTextField nachnameChange;

    /** JLabel for TextField vorname */
    private JLabel vornameLabel;

    /** JTextField vorname */
    private JTextField vornameChange;

    /** JLabel for TextField personalausweisnummer */
    private JLabel ausweisLabel;

    /** JTextField personalausweisnummer */
    private JTextField ausweisChange;

    /** JLabel for TextField kennzeichnen */
    private JLabel kfzLabel;

    /** JTextField kennzeichnen */
    private JTextField kfzChange;

    /** JLabel for TextField anzahl strafpunkte */
    private JLabel strafeLabel;

    /** JTextField anzahl strafpunkte */
    private JTextField strafeChange;

    /** JTextField array */
    private JTextField[] textFieldArray;

    /** Empty JLabel for graphic */
    private JLabel emptyLabel1 = new JLabel("-");

    /** FlensburgDatabase */
    private FlensburgDatabase databank;

    /**
     * Constructor
     * JFrame Constructor and setupGUI method is run
     */

    public FlensburgGUI(){
        super();
        setupGUI();
    }

    /**
     * Closes the GUI -- Listener action for the EXIT button
     */
    private void doExit() {
        System.exit(0);
    }

    /**
     * Creates the Menu Bar for all Frames and Dialogs
     */
    private void createMenuBar()
    {
        // Create Menubar
        JMenuBar menubar = new JMenuBar();
        mainFrame.setJMenuBar(menubar);

        // Create File menu
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);

        // Create Exit menuitem in File menu
        JMenuItem exitEntry = new JMenuItem("Exit");
        exitEntry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doExit();
            }
        });
        fileMenu.add(exitEntry);
    }

    /**
     * Creates the Buttons for mainframe
     */
    public void createButtons(){
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAddFrame();
            }
        });

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createSearchFrame();
            }
        });
    }

    /**
     * Creates the TextFields for addedDialog, editedDialog and foundDialog
     * @param person The element used to determine what is found in the TextFields
     * @return an Array that holds all the JTextFields
     */
    public JTextField[] createTextFields(FlensburgPerson person){
        nachnameLabel = new JLabel("Nachname");
        nachnameChange = new JTextField(person.getNachname());
        vornameLabel = new JLabel("Vorname");
        vornameChange = new JTextField(person.getVorname());
        ausweisLabel = new JLabel("Personalausweisnummer");
        ausweisChange = new JTextField(Integer.toString(person.ausweisHashCode()));
        kfzLabel = new JLabel("Kennzeichnen");
        kfzChange = new JTextField(person.getKfz());
        strafeLabel = new JLabel("Anzahl Strafpunkte");
        strafeChange = new JTextField(Integer.toString(person.getStrafe()));

        textFieldArray = new JTextField[5];
        textFieldArray[0] = nachnameChange;
        textFieldArray[1] = vornameChange;
        textFieldArray[2] = ausweisChange;
        textFieldArray[3] = kfzChange;
        textFieldArray[4] = strafeChange;

        for(JTextField a: textFieldArray){
            a.setEditable(false);
        }

        return textFieldArray;
    }

    /**
     * Creates the mainframe of the GUI
     */
    public void setupGUI() {
        // Create the frame
        mainFrame = new JFrame("Welcome");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        databank = new FlensburgDatabase();

        // Create the menu bar
        createMenuBar();

        // Create the Buttons
        createButtons();

        // Create the layout
        mainFrame.setPreferredSize(new Dimension(400, 150));
        GridLayout theLayout = new GridLayout(1, 2);
        theLayout.setHgap(30);
        theLayout.setVgap(20);
        mainFrame.setLayout(theLayout);

        // Add Buttons
        mainFrame.add(addButton);
        mainFrame.add(searchButton);

        // Make frame visible
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    /**
     * Creates the addDialog of the GUI - if the Button add was pressed in mainframe
     * Here the user can add a FlensburgPerson to the database
     */

    private void createAddFrame(){
        // Create the frame
        addDialog = new JDialog();
        addDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        // Create the menu bar
        createMenuBar();

        //Setup
        addDialog.setPreferredSize(new Dimension(400, 300));
        addDialog.setLocation(400,0);
        GridLayout addLayout = new GridLayout(6, 2);
        addLayout.setHgap(30);
        addLayout.setVgap(20);
        addDialog.setLayout(addLayout);

        //Labels and TextFields
        nachnameLabel = new JLabel("Nachname");
        nachnameChange = new JTextField();
        vornameLabel = new JLabel("Vorname");
        vornameChange = new JTextField();
        ausweisLabel = new JLabel("Personalausweisnummer");
        ausweisChange  = new JTextField();
        kfzLabel = new JLabel("Kennzeichnen");
        kfzChange = new JTextField();
        strafeLabel = new JLabel("Anzahl Strafpunkte");
        strafeChange = new JTextField();

        //add Button
        JButton addNow = new JButton("Add");
        addNow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nachnameChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(addDialog, "The Nachname field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (vornameChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(addDialog, "The Vorname field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (ausweisChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(addDialog, "The Personalausweisnummer field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (kfzChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(addDialog, "The Kennzeichnen field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Integer.valueOf(ausweisChange.getText());
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(addDialog, "Personalausweisnummer is not an integer", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (strafeChange.getText().isEmpty()){
                        FlensburgPerson person = new FlensburgPerson(vornameChange.getText(), nachnameChange.getText(), Integer.valueOf(ausweisChange.getText()), kfzChange.getText(), 0);
                        databank.add(person);
                        createAddedFrame(person);
                        addDialog.setVisible(false);
                    } else {
                        try {
                            Integer.parseInt(strafeChange.getText());
                        }catch (NumberFormatException nfe){
                            JOptionPane.showMessageDialog(addDialog, "Anzahl Strafpunkte  is not an integer", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        FlensburgPerson person = new FlensburgPerson(vornameChange.getText(), nachnameChange.getText(), Integer.valueOf(ausweisChange.getText()), kfzChange.getText(), Integer.parseInt(strafeChange.getText()));
                        databank.add(person);
                        createAddedFrame(person);
                        addDialog.setVisible(false);
                    }
                }
            }
        });

        //add Labels,TextFields and Button
        addDialog.add(nachnameLabel);
        addDialog.add(nachnameChange);
        addDialog.add(vornameLabel);
        addDialog.add(vornameChange);
        addDialog.add(ausweisLabel);
        addDialog.add(ausweisChange);
        addDialog.add(kfzLabel);
        addDialog.add(kfzChange);
        addDialog.add(strafeLabel);
        addDialog.add(strafeChange);
        addDialog.add(emptyLabel1);
        addDialog.add(addNow);

        // Make frame visible
        addDialog.pack();
        addDialog.setVisible(true);

    }

    /**
     * Creates the searchDialog of the GUI - if the Button search was pressed in mainframe
     * Here the user can search for a FlensburgPerson in the database (using personalausweisnummer or kennzeichnen)
     * Error fields appear if the person doesn't exist, if the search fields are empty, or if the personalausweisnummer isn't an integer
     */
    private void createSearchFrame(){
        // Create the frame
        searchDialog = new JDialog();
        searchDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        // Create the menu bar
        createMenuBar();

        //Setup
        searchDialog.setPreferredSize(new Dimension(400, 300));
        searchDialog.setLocation(400,0);
        GridLayout addLayout = new GridLayout(4, 2);
        addLayout.setHgap(30);
        addLayout.setVgap(20);
        searchDialog.setLayout(addLayout);

        //Labels and TextFields
        JLabel kfzLabel = new JLabel("Kennzeichnen");
        JTextField kfzSearch = new JTextField();
        JLabel ausweisLabel = new JLabel("Personalausweisnummer");
        JTextField ausweisSearch = new JTextField();

        //Search Button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (kfzSearch.getText().isEmpty() && ausweisSearch.getText().isEmpty()){
                    JOptionPane.showMessageDialog(searchDialog, "Not enough Information", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (! kfzSearch.getText().isEmpty()){
                    FlensburgPerson test = databank.search(kfzSearch.getText());
                    if (test != null) {
                        createFoundFrame(test);
                        searchDialog.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(searchDialog, kfzSearch.getText() + " has not been found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    try{
                        Integer.valueOf(ausweisSearch.getText());
                    } catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(searchDialog, "Personalausweisnummer is not an integer", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    FlensburgPerson test = databank.search(Integer.valueOf(ausweisSearch.getText()));
                    if (test != null) {
                        createFoundFrame(test);
                        searchDialog.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(searchDialog, ausweisSearch.getText() + " has not been found", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //Add Labels, TextFields and Button
        searchDialog.add(kfzLabel);
        searchDialog.add(kfzSearch);
        searchDialog.add(ausweisLabel);
        searchDialog.add(ausweisSearch);
        searchDialog.add(emptyLabel1);
        searchDialog.add(searchButton);

        // Make frame visible
        searchDialog.pack();
        searchDialog.setVisible(true);
    }

    /**
     * Creates the foundDialog of the GUI - if the person was search in the searchDialog
     * Here the user can remove or edit the found person
     * @param person The person searched for in the searchDialog
     */
    private void createFoundFrame(FlensburgPerson person){
        // Create the frame
        foundDialog = new JDialog();
        foundDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        // Create the menu bar
        createMenuBar();

        //Setup
        foundDialog.setPreferredSize(new Dimension(400, 300));
        foundDialog.setLocation(400,0);
        GridLayout addLayout = new GridLayout(8, 2);
        addLayout.setHgap(30);
        addLayout.setVgap(20);
        foundDialog.setLayout(addLayout);

        //Labels and TextFields
        textFieldArray = createTextFields(person);

        //Remove Button
        removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                databank.remove(person.getKfz());
                JOptionPane.showMessageDialog(foundDialog, person.getVorname()+ " " + person.getNachname() + " has been removed", "Remove Succesful",JOptionPane.OK_OPTION);
                foundDialog.setVisible(false);
            }
        });

        //Confirm Button to confirm changes
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (nachnameChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(foundDialog, "The Nachname field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (vornameChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(foundDialog, "The Vorname field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (ausweisChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(foundDialog, "The Personalausweisnummer field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (kfzChange.getText().isEmpty()){
                    JOptionPane.showMessageDialog(foundDialog, "The Kennzeichnen field is empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Integer.valueOf(ausweisChange.getText());
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(foundDialog, "Personalausweisnummer is not an integer", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    try {
                        Integer.parseInt(strafeChange.getText());
                    }catch (NumberFormatException nfe){
                        JOptionPane.showMessageDialog(foundDialog, "Anzahl Strafpunkte is not an integer", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    FlensburgPerson newPerson = new FlensburgPerson(vornameChange.getText(), nachnameChange.getText(), Integer.valueOf(ausweisChange.getText()), kfzChange.getText(), Integer.parseInt(strafeChange.getText()));
                    if (newPerson.equals(person)){
                        JOptionPane.showMessageDialog(foundDialog, "No changes were made", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        databank.remove(person.getKfz());
                        databank.add(newPerson);
                        createEditedFrame(newPerson);
                        foundDialog.setVisible(false);
                    }
                }
            }
        });
        confirmButton.setEnabled(false);

        //Cancel Button to abort changes
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nachnameChange.setText(person.getNachname());
                vornameChange.setText(person.getVorname());
                ausweisChange.setText(Integer.toString(person.getAusweis()));
                kfzChange.setText(person.getKfz());
                strafeChange.setText(Integer.toString(person.getStrafe()));
                for (JTextField a: textFieldArray){
                    a.setEditable(false);
                }
                confirmButton.setEnabled(false);
                cancelButton.setEnabled(false);
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        });
        cancelButton.setEnabled(false);

        //Edit Button allows the Text Fields to be changed and the buttons Confirm, Cancel to be used
        editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JTextField a: textFieldArray){
                    a.setEditable(true);
                }
                confirmButton.setEnabled(true);
                cancelButton.setEnabled(true);
                editButton.setEnabled(false);
                removeButton.setEnabled(false);
            }
        });

        //Add Labels,TextFields and Buttons
        foundDialog.add(nachnameLabel);
        foundDialog.add(nachnameChange);
        foundDialog.add(vornameLabel);
        foundDialog.add(vornameChange);
        foundDialog.add(ausweisLabel);
        foundDialog.add(ausweisChange);
        foundDialog.add(kfzLabel);
        foundDialog.add(kfzChange);
        foundDialog.add(strafeLabel);
        foundDialog.add(strafeChange);
        foundDialog.add(removeButton);
        foundDialog.add(editButton);
        foundDialog.add(confirmButton);
        foundDialog.add(cancelButton);


        // Make frame visible
        foundDialog.pack();
        foundDialog.setVisible(true);
    }

    /**
     * Creates the addedDialog of the GUI - if the Button add was pressed in addDialog
     * Here the user can see the FlensburgPerson that has been added to the database
     * @param person the FlensburgPerson who has just been added
     */
    private void createAddedFrame(FlensburgPerson person){
        // Create the frame
        addedDialog = new JDialog();
        addedDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        // Create the menu bar
        createMenuBar();

        //Setup
        addedDialog.setPreferredSize(new Dimension(400, 300));
        addedDialog.setLocation(400,0);
        GridLayout addLayout = new GridLayout(8, 2);
        addLayout.setHgap(30);
        addLayout.setVgap(20);
        addedDialog.setLayout(addLayout);

        //Labels and TextFields
        textFieldArray = createTextFields(person);

        JLabel hasBeenAdded = new JLabel (person.getVorname() + " " + person.getNachname() + " has been added");

        //Add Labels and TextFields
        addedDialog.add(nachnameLabel);
        addedDialog.add(nachnameChange);
        addedDialog.add(vornameLabel);
        addedDialog.add(vornameChange);
        addedDialog.add(ausweisLabel);
        addedDialog.add(ausweisChange);
        addedDialog.add(kfzLabel);
        addedDialog.add(kfzChange);
        addedDialog.add(strafeLabel);
        addedDialog.add(strafeChange);
        addedDialog.add(hasBeenAdded);

        // Make frame visible
        addedDialog.pack();
        addedDialog.setVisible(true);
    }

    /**
     * Creates the editedDialog of the GUI - if the Button confirm was pressed in foundDialog
     * Here the user can see the FlensburgPerson that has been edited
     * @param person The newly edited FlensburgPerson
     */
    private void createEditedFrame(FlensburgPerson person){
        // Create the frame
        editedDialog = new JDialog();
        editedDialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        // Create the menu bar
        createMenuBar();

        editedDialog.setPreferredSize(new Dimension(400, 300));
        editedDialog.setLocation(400,0);
        GridLayout addLayout = new GridLayout(8, 2);
        addLayout.setHgap(30);
        addLayout.setVgap(20);
        editedDialog.setLayout(addLayout);

        textFieldArray = createTextFields(person);

        JLabel hasBeenEdited = new JLabel (person.getVorname() + " " + person.getNachname() + " has been edited");

        editedDialog.add(nachnameLabel);
        editedDialog.add(nachnameChange);
        editedDialog.add(vornameLabel);
        editedDialog.add(vornameChange);
        editedDialog.add(ausweisLabel);
        editedDialog.add(ausweisChange);
        editedDialog.add(kfzLabel);
        editedDialog.add(kfzChange);
        editedDialog.add(strafeLabel);
        editedDialog.add(strafeChange);
        editedDialog.add(hasBeenEdited);

        // Make frame visible
        editedDialog.pack();
        editedDialog.setVisible(true);
    }
}
