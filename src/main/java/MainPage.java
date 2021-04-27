import keeptoo.Drag;
import keeptoo.KButton;
import keeptoo.KGradientPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainPage extends JFrame {

    private KGradientPanel kPanel;
    private KButton kButton;
    private JLabel title;
    private JLabel URL;
    private JTextField tfURL;
    private JLabel types;
    private JLabel res;
    private JComboBox testTypes;
    private JLabel projectName;
    private String[] typesList =  {"Check for broken links","Validate the UI", "Check for XSS attack"};

    public MainPage(){
        setTitle("Testing Project");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        kPanel = new KGradientPanel();
        kPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.setContentPane(kPanel);
        Color startColor = new Color(10,102,102);
        Color endColor = new Color(155,132,204);
        kPanel.kStartColor = startColor;
        kPanel.kEndColor = endColor;
        kPanel.kGradientFocus = 200;

        projectName = new JLabel("ProjectName");
        projectName.setForeground(new Color(204,204,204));
        projectName.setFont(new Font("DialogInput", Font.PLAIN, 14));
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weighty = 1.0;
        c.insets = new Insets(30, 30, 20, 0);

        kPanel.add(projectName, c);

        title = new JLabel("Please fill in testing information");
        c.gridy = 1;
        c.gridx = 1;
        c.insets = new Insets(0, 0, 30, 0);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.PLAIN, 16));
        title.setSize(300, 30);
        kPanel.add(title, c);

        URL = new JLabel("URL");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20,100,0,0);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        URL.setForeground(Color.WHITE);
        URL.setFont(new Font("Arial", Font.PLAIN, 14));
        URL.setSize(100, 20);
        kPanel.add(URL, c);

        tfURL = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20,0,0,100);
        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        tfURL.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        tfURL.setOpaque(false);
        tfURL.setFont(new Font("Arial", Font.PLAIN, 14));
        tfURL.setForeground(Color.WHITE);
        tfURL.setSize(250, 20);
        kPanel.add(tfURL,c);

        types = new JLabel("Testing Type");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(70,100,0,0);
        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        types.setForeground(Color.WHITE);
        types.setFont(new Font("Arial", Font.PLAIN, 14));
        types.setSize(100, 20);
        kPanel.add(types, c);

        testTypes = new JComboBox(typesList);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(70,0,0,100);
        c.gridy = 3;
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        testTypes.setOpaque(false);
        JTextField boxField = (JTextField)testTypes .getEditor().getEditorComponent();
        boxField.setBorder(BorderFactory.createEmptyBorder());
        testTypes.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.WHITE));
        testTypes.setBackground(new Color(0, 0, 0, 0));
        testTypes.setFocusable(false);
        testTypes.setFont(new Font("Arial", Font.PLAIN, 14));
        testTypes.setForeground(Color.WHITE);
        testTypes.setSize(250, 20);
        kPanel.add(testTypes, c);

        kButton = new KButton();
        kButton.setText("Start Testing");
        kButton.kAllowGradient = false;
        kButton.kBackGroundColor = new Color(29, 92, 92, 178);
        kButton.kBorderRadius = 40;
        kButton.setSize(200, 40);
        kButton.setFont(new Font("Arial", Font.PLAIN, 18));
        kButton.kHoverColor = new Color(17, 62, 62, 178);
        kButton.kPressedColor = new Color(29, 92, 92, 178);

        kButton.kHoverForeGround = Color.WHITE;
        kButton.setBorder(null);
        c.gridy = 4;
        c.gridx = 1;
        kPanel.add(kButton, c);

        res = new JLabel("");
        res.setFont(new Font("SansSerif", Font.PLAIN, 16));
        res.setForeground(new Color(120, 64, 64));
        c.gridy = 5;
        c.gridx = 1;
        c.insets = new Insets(0,0,0,20);
        kPanel.add(res,c);
        setVisible(true);

        kButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == kButton) {
                    if (!tfURL.getText().isEmpty()) {
                        String url = tfURL.getText();
                        if(isValidURL(url)){
                            String data
                                    = "URL : "
                                    + tfURL.getText() + "\n"
                                    + "Testing Type : "
                                    + testTypes.getSelectedItem() + "\n";
                            res.setText(data);
                        } else {
                            res.setText("Please provide valid URL");
                        }
                    }
                    else {
                        res.setText("Please provide the URL");
                    }
                }
            }
        });
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


}

