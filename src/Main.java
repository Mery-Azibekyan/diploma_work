import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.nio.channels.FileLock;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER));
        content.setBackground(Color.LIGHT_GRAY);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        frame.setContentPane(content);

//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        frame.add(panel);

//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;

        JLabel l1 = new JLabel("Check");
        JLabel l2 = new JLabel("fields in");
        JLabel l3 = new JLabel("for");
        JTextField fieldType = new JTextField(10);
        JTextField url = new JTextField(50);
        String types[] =  {"XSS attack","Functional Test"};
        JComboBox testType = new JComboBox(types);

        fieldType.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (fieldType.getText().equals("Enter field type")) {
                    fieldType.setText("");
                    fieldType.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (fieldType.getText().isEmpty()) {
                    fieldType.setForeground(Color.GRAY);
                    fieldType.setText("Enter field type");
                }
            }
        });

        frame.add(l1);
        frame.add(fieldType);
        frame.add(l2);
        frame.add(url);
        frame.add(l3);
        frame.add(testType);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        frame.add(panel);
//        frame.setVisible(true);
    }

}

