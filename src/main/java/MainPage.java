import io.github.bonigarcia.wdm.WebDriverManager;
import keeptoo.KButton;
import keeptoo.KGradientPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;


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
    private String[] typesList =  {"Check for broken links","Functional Test by recording", "Validate the UI", "Check for XSS attack"};

    private static WebDriver driver;

    public MainPage(){
        setTitle("Testing Project");
        setBounds(20, 20, 1200, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        kPanel = new KGradientPanel();
        kPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.setContentPane(kPanel);
        Color startColor = new Color(10,102,102);
        Color endColor = new Color(175, 193,204);
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
        res.setFont(new Font("SansSerif", Font.BOLD, 16));
        res.setForeground(new Color(160, 36, 37, 208));
        c.gridy = 5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(0,0,0,20);
        kPanel.add(res,c);
        setVisible(true);

        kButton.addActionListener(e -> {
            if (e.getSource() == kButton) {
                if (!tfURL.getText().isEmpty()) {
                    final String url = tfURL.getText();
                    if(isValidURL(url)){
                        String type = testTypes.getSelectedItem().toString();
                        switch (type){
                            case "Check for broken links": checkForBrokenLinks(url); break;
                            case "Functional Test by recording": startRecording(url); break;
                        }
                        //res.setText(data);
                    } else {
                        res.setText("Please provide valid URL");
                    }
                }
                else {
                    res.setText("Please provide the URL");
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

    @lombok.SneakyThrows
    public static void startRecording(String url){

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions (new File("src/main/resources/extension_3_17_0_0.crx"));
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get("chrome-extension://mooikfkahbdckldjjndioackbalphokd/index.html");
        driver.findElement(By.linkText("Record a new test in a new project")).click();
        driver.findElement(By.cssSelector("input[name=\"projectName\"]")).sendKeys("Test" + Keys.ENTER);
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("input[name=\"baseUrl\"]")).sendKeys(url + Keys.ENTER);
    }

    public void checkForBrokenLinks(String url){
        String homePage = url;
        String nestedUrl = "";
        HttpURLConnection huc = null;
        int respCode = 200;

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(homePage);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        Iterator<WebElement> it = links.iterator();

        while(it.hasNext()){

            nestedUrl = it.next().getAttribute("href");
            if(nestedUrl == null || nestedUrl.isEmpty()){
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }

            try {
                huc = (HttpURLConnection)(new URL(nestedUrl).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                respCode = huc.getResponseCode();
                if(respCode >= 400){
                    System.out.println(nestedUrl+" is a broken link");
                }
                else{
                    System.out.println(nestedUrl+" is a valid link");
                }
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
        driver.quit();
        JFrame resultFrame = new JFrame();
        resultFrame.setTitle("Results");
        resultFrame.setBounds(300, 90, 900, 600);
        resultFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        resultFrame.setVisible(true);
        Container c = resultFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        JLabel labelUrl = new JLabel("url erkar erkaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaar");
        cons.anchor = GridBagConstraints.FIRST_LINE_START;
        cons.weighty = 1.0;
        cons.insets = new Insets(20,10,0,0);
        cons.gridy = 0;
        cons.gridx = 0;
        cons.gridwidth = 1;

        labelUrl.setFont(new Font("Arial", Font.PLAIN, 14));
        labelUrl.setSize(100, 20);
        c.add(labelUrl, cons);
        JLabel labelStatus = new JLabel("valid or not valid");
        cons.gridx = 1;
        labelStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        labelStatus.setSize(100, 20);
        c.add(labelStatus, cons);
    }

    public static void stopAndSaveRecording(){

    }

    public static void playbackRecording() {

    }


}

