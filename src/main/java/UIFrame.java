import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;


public class UIFrame extends JFrame implements ActionListener {

    private Container c;
    private JLabel title;
    private JLabel URL;
    private JTextField tfURL;
    private JLabel types;
    private JButton sub;
    private JTextArea tout;
    private JLabel res;
    private JComboBox testTypes;
    private String[] typesList =  {"Check for broken links","Validate the UI", "Check for XSS attack"};

    public UIFrame()
    {
        setTitle("Testing Project");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Please fill in testing information");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        URL = new JLabel("URL");
        URL.setFont(new Font("Arial", Font.PLAIN, 14));
        URL.setSize(100, 20);
        URL.setLocation(100, 100);
        c.add(URL);

        tfURL = new JTextField();
        tfURL.setFont(new Font("Arial", Font.PLAIN, 14));
        tfURL.setSize(250, 20);
        tfURL.setLocation(200, 100);
        c.add(tfURL);


        types = new JLabel("Testing Type");
        types.setFont(new Font("Arial", Font.PLAIN, 14));
        types.setSize(100, 20);
        types.setLocation(100, 150);
        c.add(types);

        testTypes = new JComboBox(typesList);
        testTypes.setFont(new Font("Arial", Font.PLAIN, 14));
        testTypes.setSize(250, 20);
        testTypes.setLocation(200, 150);
        c.add(testTypes);

        sub = new JButton("Start Testing");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(200, 20);
        sub.setLocation(160, 250);
        sub.addActionListener(this);
        c.add(sub);

//        reset = new JButton("Reset");
//        reset.setFont(new Font("Arial", Font.PLAIN, 15));
//        reset.setSize(100, 20);
//        reset.setLocation(270, 300);
//        reset.addActionListener(this);
//        c.add(reset);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 170);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        c.add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 16));
        res.setSize(500, 25);
        res.setLocation(160, 500);
        c.add(res);

//        resadd = new JTextArea();
//        resadd.setFont(new Font("Arial", Font.PLAIN, 15));
//        resadd.setSize(200, 75);
//        resadd.setLocation(580, 175);
//        resadd.setLineWrap(true);
//        c.add(resadd);

        setVisible(true);
    }

    public static boolean isValidURL(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == sub) {
            if (!tfURL.getText().isEmpty()) {
                String url = tfURL.getText();
                if(isValidURL(url)){
                    String data
                            = "URL : "
                            + tfURL.getText() + "\n"
                            + "Testing Type : "
                            + testTypes.getSelectedItem() + "\n";

                    res.setText("");
                    tout.setText(data);
                    tout.setEditable(false);
                } else {
                    tout.setText("");
                    res.setForeground(Color.RED);
                    res.setText("Please provide valid URL");
                }
            }
            else {
                tout.setText("");
                res.setForeground(Color.RED);
                res.setText("Please provide the URL");
            }
        }
    }
}
//    JFrame frame = new JFrame("Diploma project");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        content.setBackground(Color.LIGHT_GRAY);
//        content.setBorder(new EmptyBorder(20, 20, 20, 20));
//        frame.setContentPane(content);
//
////        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
////        frame.add(panel);
//
////        GridBagConstraints gbc = new GridBagConstraints();
////        gbc.gridx = 0;
////        gbc.gridy = 0;
//
//    JLabel l1 = new JLabel("Check");
//    JLabel l2 = new JLabel("fields in");
//    JLabel l3 = new JLabel("for");
//    JTextField fieldType = new JTextField(10);
//    JTextField url = new JTextField(50);
//    String types[] =  {"XSS attack","Functional Test"};
//    JComboBox testType = new JComboBox(types);
//
//        fieldType.addFocusListener(new FocusListener() {
//        @Override
//        public void focusGained(FocusEvent e) {
//            if (fieldType.getText().equals("Enter field type")) {
//                fieldType.setText("");
//                fieldType.setForeground(Color.BLACK);
//            }
//        }
//        @Override
//        public void focusLost(FocusEvent e) {
//            if (fieldType.getText().isEmpty()) {
//                fieldType.setForeground(Color.GRAY);
//                fieldType.setText("Enter field type");
//            }
//        }
//    });
//
//        frame.add(l1);
//        frame.add(fieldType);
//        frame.add(l2);
//        frame.add(url);
//        frame.add(l3);
//        frame.add(testType);
//
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
////        frame.add(panel);
////        frame.setVisible(true);

