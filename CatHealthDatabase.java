import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CatHealthDatabase implements ActionListener {
    private JFrame frame;
    private JTextField nameField;
    private JTextField weightField;
    private JTextField vaccinationField;
    private JTextField vaccineDetailsField;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;

    public CatHealthDatabase() {
        frame = new JFrame("Cat Health Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));

        JLabel nameLabel = new JLabel("Name: ");
        nameField = new JTextField(10);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        JLabel weightLabel = new JLabel("Weight (lbs): ");
        weightField = new JTextField(10);
        inputPanel.add(weightLabel);
        inputPanel.add(weightField);

        JLabel vaccinationLabel = new JLabel("Number of Vaccinations: ");
        vaccinationField = new JTextField(10);
        inputPanel.add(vaccinationLabel);
        inputPanel.add(vaccinationField);

        JLabel vaccidetailsLabel = new JLabel("Names of Vaccines: ");
        vaccineDetailsField = new JTextField(10);
        inputPanel.add(vaccidetailsLabel);
        inputPanel.add(vaccineDetailsField);

        checkBox1 = new JCheckBox("Vaccinate Cat Yearly");
        checkBox2 = new JCheckBox("Regular Vet Visits");
        inputPanel.add(checkBox1);
        inputPanel.add(checkBox2);

        JButton button = new JButton("Submit");
        button.addActionListener(this);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Submit")) {
            String name = nameField.getText();
            String weight = weightField.getText();
            String vaccinations = vaccinationField.getText();
            String vaccineDetails = vaccineDetailsField.getText();
            boolean isVaccinatedYearly = checkBox1.isSelected();
            boolean isRegularVisits = checkBox2.isSelected();

            // Input validation
            if (name.isEmpty() || weight.isEmpty() || vaccinations.isEmpty() || vaccineDetails.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double catWeight = Double.parseDouble(weight);
                int numOfVaccinations = Integer.parseInt(vaccinations);
                // Saving data
                CatHealth cat = new CatHealth(name, catWeight, numOfVaccinations, vaccineDetails, isVaccinatedYearly, isRegularVisits);
                saveDataToFile(cat);
                JOptionPane.showMessageDialog(frame, "Data saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for weight and vaccinations.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveDataToFile(CatHealth record) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("catHealth.txt", true))) {
            writer.println("Name: " + record.getName());
            writer.println("Weight: " + record.getWeight() + " lbs");
            writer.println("Number of Vaccinations: " + record.getVaccinations());
            writer.println("Names of vaccines: " + record.getVaccineDetails());
            writer.println("Vaccinated Yearly: " + record.isVaccinatedYearly());
            writer.println("Regular Vet Visits: " + record.isRegularVisits());
            writer.println();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CatHealthDatabase::new);
    }
}

class CatHealth {
    private String name;
    private double weight;
    private int vaccinations;
    private String vaccineDetails;
    private boolean isVaccinatedYearly;
    private boolean isRegularVisits;

    public CatHealth(String name, double weight, int vaccinations, String vaccineDetails, boolean isVaccinatedYearly, boolean isRegularVisits) {
        this.name = name;
        this.weight = weight;
        this.vaccinations = vaccinations;
        this.vaccineDetails = vaccineDetails;
        this.isVaccinatedYearly = isVaccinatedYearly;
        this.isRegularVisits = isRegularVisits;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getVaccinations() {
        return vaccinations;
    }

    public String getVaccineDetails() {
        return vaccineDetails;
    }

    public boolean isVaccinatedYearly() {
        return isVaccinatedYearly;
    }

    public boolean isRegularVisits() {
        return isRegularVisits;
    }
}

