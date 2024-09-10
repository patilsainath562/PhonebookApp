import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PhonebookApp {
    public static void main(String[] args) {
        // Create a JFrame (window)
        JFrame frame = new JFrame("Phonebook App");
        frame.setLayout(new FlowLayout());

        // Create buttons
        JButton insertButton = new JButton("Insert Contact");
        /*JButton searchButton = new JButton("Search Contact");*/
        JButton phonesearchbuton = new JButton("Phone Search");
        JButton editphonenumber= new JButton("Phone");
        JButton deletephone = new JButton("Delete Phone");
        JButton closeButton = new JButton("Close");
        JTextField searchField = new JTextField(15);
        JTextArea resultArea = new JTextArea(5,20);
        resultArea.setEditable(false);

        // Add buttons to frame
        frame.add(insertButton);
        /*frame.add(searchButton);*/
        frame.add(phonesearchbuton);
        frame.add(editphonenumber);
        frame.add(deletephone); 
        frame.add(new JLabel("Search:"));
        frame.add(searchField);
        frame.add(new JScrollPane(resultArea)); // Add a scroll pane for the result area




        frame.add(closeButton);

        // Set frame properties
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Insert Contact action
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user to enter name and phone number
                String name = JOptionPane.showInputDialog("Enter name:");
                String phone = JOptionPane.showInputDialog("Enter phone number:");

                if (name != null && phone != null) {
                    // Append name and phone number to respective files
                    try (FileWriter writer = new FileWriter("name.txt", true)) {
                        writer.write(name + "\n");
                        try (FileWriter phoneWriter = new FileWriter("phone.txt", true)) {
                            phoneWriter.write(phone + "\n");
                            JOptionPane.showMessageDialog(null, "Successfully added the contact.");
                        }
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "An error occurred while saving the contact.");
                        ioException.printStackTrace();
                    }
                }
            }
        });
// Search Contact action
/*searchButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt user to enter the starting letter or name to search
        String name = JOptionPane.showInputDialog("Enter the starting letter(s) to search:");

        if (name != null) {
            // To store matching results
            StringBuilder matchingNames = new StringBuilder();
            StringBuilder matchingPhones = new StringBuilder();

            // Search for names starting with the entered letter(s) in the "name.txt" file
            try (BufferedReader nameReader = new BufferedReader(new FileReader("name.txt"))) {
                BufferedReader phoneReader = new BufferedReader(new FileReader("phone.txt"));
                String nameLine;
                String phoneLine;
                
                while ((nameLine = nameReader.readLine()) != null && (phoneLine = phoneReader.readLine()) != null) {
                    // Check if the name starts with the entered string
                    if (nameLine.toLowerCase().startsWith(name.toLowerCase())) {
                        matchingNames.append(nameLine).append("\n");
                        matchingPhones.append(phoneLine).append("\n");
                    }
                }
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "An error occurred while searching the contact.");
                ioException.printStackTrace();
            }

            // If there are matching results, display them
            if (matchingNames.length() > 0) {
                JOptionPane.showMessageDialog(null, "Matching Contacts:\n" + matchingNames.toString() + "\nPhones:\n" + matchingPhones.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No contacts found starting with '" + name + "'.");
            }
        }
    }
});*/



searchField.getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
        performSearch();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        performSearch();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        performSearch();
    }

    // Method to perform search as the user types in the JTextField
    public void performSearch() {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            resultArea.setText("");
            return;
        }

        StringBuilder matchingNames = new StringBuilder();
        StringBuilder matchingPhones = new StringBuilder();

        // Search for names starting with the entered text in the "name.txt" file
        try (BufferedReader nameReader = new BufferedReader(new FileReader("name.txt"))) {
            BufferedReader phoneReader = new BufferedReader(new FileReader("phone.txt"));
            String nameLine;
            String phoneLine;

            while ((nameLine = nameReader.readLine()) != null && (phoneLine = phoneReader.readLine()) != null) {
                if (nameLine.toLowerCase().startsWith(searchText.toLowerCase())) {
                    matchingNames.append(nameLine).append("\n");
                    matchingPhones.append(phoneLine).append("\n");
                }
            }
        } catch (IOException ioException) {
            resultArea.setText("An error occurred while searching.");
            ioException.printStackTrace();
        }

        // Display matching contacts and phone numbers in the JTextArea
        if (matchingNames.length() > 0) {
            resultArea.setText("Matching Contacts:\n" + matchingNames.toString() + "\nPhones:\n" + matchingPhones.toString());
        } else {
            resultArea.setText("No contacts found.");
        }
    }
});




                // phonesesarch butotn 
        phonesearchbuton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String name = JOptionPane.showInputDialog("Enter Phone Number");
                if (name != null) {
                    int linenumber = 0;
                    int checklinenumber = 0;

                    // Search for the name in the "name.txt" file
                    try (BufferedReader bufferedReader = new BufferedReader(new FileReader("phone.txt"))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            linenumber++;
                            if (line.equals(name)) {
                                checklinenumber = linenumber;
                                break;
                            }
                        }
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(null, "An error occurred while searching the contact.");
                        ioException.printStackTrace();
                    }

                    // If the name was found, search for the corresponding phone number
                    if (checklinenumber > 0) {
                        try (BufferedReader bufferedReader1 = new BufferedReader(new FileReader("name.txt"))) {
                            int currentLine = 0;
                            String phoneLine;
                            while ((phoneLine = bufferedReader1.readLine()) != null) {
                                currentLine++;
                                if (currentLine == checklinenumber) {
                                    JOptionPane.showMessageDialog(null, "Phone: " + phoneLine);
                                    break;
                                }
                            }
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(null, "An error occurred while retrieving the phone number.");
                            ioException.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Name not found.");
                    }
                }




            }
        });

         // Edit Phone Number action
    editphonenumber.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt user to enter the name and the new phone number
        String name = JOptionPane.showInputDialog("Enter name to edit:");
        String newPhone = JOptionPane.showInputDialog("Enter new phone number:");

        if (name != null && newPhone != null) {
            int linenumber = 0;
            int checklinenumber = 0;

            // Search for the name in the "name.txt" file
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("name.txt"))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    linenumber++;
                    if (line.equals(name)) {
                        checklinenumber = linenumber;
                        break;
                    }
                }
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "An error occurred while searching the contact.");
                ioException.printStackTrace();
            }

            if (checklinenumber > 0) {
                // Update the phone number in the "phone.txt" file
                try {
                    // Read all lines from phone.txt
                    File phoneFile = new File("phone.txt");
                    BufferedReader reader = new BufferedReader(new FileReader(phoneFile));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    int currentLine = 0;

                    while ((line = reader.readLine()) != null) {
                        currentLine++;
                        if (currentLine == checklinenumber) {
                            // Replace the phone number at the specific line
                            stringBuilder.append(newPhone).append("\n");
                        } else {
                            stringBuilder.append(line).append("\n");
                        }
                    }
                    reader.close();

                    // Write the updated content back to phone.txt
                    FileWriter writer = new FileWriter(phoneFile);
                    writer.write(stringBuilder.toString());
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Phone number updated successfully.");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "An error occurred while updating the phone number.");
                    ioException.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Name not found.");
            }
        }
    }
});

// Delete Phone action
deletephone.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Prompt user to enter the name to delete
        String name = JOptionPane.showInputDialog("Enter the name to delete:");

        if (name != null) {
            int linenumber = 0;
            int checklinenumber = 0;

            // Search for the name in the "name.txt" file
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("name.txt"))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    linenumber++;
                    if (line.equals(name)) {
                        checklinenumber = linenumber;
                        break;
                    }
                }
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "An error occurred while searching for the contact.");
                ioException.printStackTrace();
            }

            // If the name was found, delete the contact
            if (checklinenumber > 0) {
                try {
                    // Remove the entry from "name.txt"
                    File nameFile = new File("name.txt");
                    BufferedReader nameReader = new BufferedReader(new FileReader(nameFile));
                    StringBuilder nameBuilder = new StringBuilder();
                    String nameLine;
                    int currentLine = 0;

                    while ((nameLine = nameReader.readLine()) != null) {
                        currentLine++;
                        if (currentLine != checklinenumber) {
                            nameBuilder.append(nameLine).append("\n");
                        }
                    }
                    nameReader.close();

                    // Write the updated content back to name.txt
                    FileWriter nameWriter = new FileWriter(nameFile);
                    nameWriter.write(nameBuilder.toString());
                    nameWriter.close();

                    // Remove the corresponding phone entry from "phone.txt"
                    File phoneFile = new File("phone.txt");
                    BufferedReader phoneReader = new BufferedReader(new FileReader(phoneFile));
                    StringBuilder phoneBuilder = new StringBuilder();
                    String phoneLine;
                    currentLine = 0;

                    while ((phoneLine = phoneReader.readLine()) != null) {
                        currentLine++;
                        if (currentLine != checklinenumber) {
                            phoneBuilder.append(phoneLine).append("\n");
                        }
                    }
                    phoneReader.close();

                    // Write the updated content back to phone.txt
                    FileWriter phoneWriter = new FileWriter(phoneFile);
                    phoneWriter.write(phoneBuilder.toString());
                    phoneWriter.close();

                    JOptionPane.showMessageDialog(null, "Contact deleted successfully.");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "An error occurred while deleting the contact.");
                    ioException.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Name not found.");
            }
        }
    }
});


                // Close button action
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the GUI window
            }
        });
    }
}
