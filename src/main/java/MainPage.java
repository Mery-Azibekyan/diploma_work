
import io.github.bonigarcia.wdm.WebDriverManager;
import keeptoo.KButton;
import keeptoo.KGradientPanel;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.*;
import java.text.NumberFormat;
import java.util.*;
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
    private String[] typesList = {"Check for broken links", "Functional Test by recording", "Validate the UI", "Check for XSS attack"};

    private static WebDriver driver;

    public MainPage() {
        setTitle("Testing Project");
        setBounds(20, 20, 1200, 700);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        kPanel = new KGradientPanel();
        kPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.setContentPane(kPanel);
        Color startColor = new Color(10, 102, 102);
        Color endColor = new Color(175, 193, 204);
        kPanel.kStartColor = startColor;
        kPanel.kEndColor = endColor;
        kPanel.kGradientFocus = 200;

        projectName = new JLabel("ProjectName");
        projectName.setForeground(new Color(204, 204, 204));
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
        c.insets = new Insets(20, 100, 0, 0);
        c.gridy = 2;
        c.gridx = 0;
        c.gridwidth = 1;
        c.weightx = 0.5;
        URL.setForeground(Color.WHITE);
        URL.setFont(new Font("Arial", Font.PLAIN, 14));
        URL.setSize(100, 20);
        kPanel.add(URL, c);

        tfURL = new JTextField("https://fb.com");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 0, 0, 100);
        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        tfURL.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        tfURL.setOpaque(false);
        tfURL.setFont(new Font("Arial", Font.PLAIN, 14));
        tfURL.setForeground(Color.WHITE);
        tfURL.setSize(250, 20);
        kPanel.add(tfURL, c);

        types = new JLabel("Testing Type");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(70, 100, 0, 0);
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
        c.insets = new Insets(70, 0, 0, 100);
        c.gridy = 3;
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        testTypes.setOpaque(false);
        JTextField boxField = (JTextField) testTypes.getEditor().getEditorComponent();
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
        c.insets = new Insets(0, 0, 0, 20);
        kPanel.add(res, c);
        setVisible(true);

        kButton.addActionListener(e -> {
            if (e.getSource() == kButton) {
                if (!tfURL.getText().isEmpty()) {
                    final String url = tfURL.getText();
                    if (isValidURL(url)) {
                        String type = testTypes.getSelectedItem().toString();
                        switch (type) {
                            case "Check for broken links":
                                checkForBrokenLinks(url);
                                break;
                            case "Functional Test by recording":
                                openNewFrameForRecording(url);
                                break;
                            case "Validate the UI":
                                openNewFrameForScreenshot(url);
                                break;
                        }
                    } else {
                        res.setText("Please provide valid URL");
                    }
                } else {
                    res.setText("Please provide the URL");
                }
            }
        });
    }

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @lombok.SneakyThrows
    public void checkForBrokenLinks(String url) {
        String homePage = url;
        String nestedUrl = "";
        HttpURLConnection huc = null;
        int respCode = 200;

        Map<String, String> results = new HashMap<>();


        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(homePage);

        List<WebElement> links = driver.findElements(By.tagName("a"));
        Iterator<WebElement> it = links.iterator();

        JFrame resultFrame = new JFrame();
        resultFrame.setTitle("Results");
        resultFrame.setBounds(10, 10, 900, 600);
        resultFrame.setVisible(true);
        Container c = resultFrame.getContentPane();
        while (it.hasNext()) {

            nestedUrl = it.next().getAttribute("href");
            if (nestedUrl == null || nestedUrl.isEmpty()) {
                System.out.println("URL is either not configured for anchor tag or it is empty");
                results.put(nestedUrl, "Not Configured");
                continue;
            }
            try {
                huc = (HttpURLConnection) (new URL(nestedUrl).openConnection());
                huc.setRequestMethod("HEAD");
                huc.connect();
                respCode = huc.getResponseCode();
                if (respCode >= 400) {
                    System.out.println(nestedUrl + " is a broken link");
                    results.put(nestedUrl, "Broken link");
                } else {
                    System.out.println(nestedUrl + " is a valid link");
                    results.put(nestedUrl, "Valid link");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        driver.quit();

        String[][] data = new String[results.size()][];
        int ii = 0;
        for (Map.Entry<String, String> entry : results.entrySet()) {
            data[ii++] = new String[]{entry.getKey(), entry.getValue()};
        }

        String column[] = {"URL", "Status"};
        final DefaultTableModel model = new DefaultTableModel(data, column);
        JTable jt = new JTable(model);
        jt.setRowHeight(30);
        jt.setBounds(30, 40, 1000, 800);
        JScrollPane sp = new JScrollPane(jt);
        resultFrame.add(sp);
        resultFrame.setSize(300, 400);
        resultFrame.setVisible(true);
    }


    public void openNewFrameForRecording(String url) {
        JFrame infoFrame = new JFrame();
        infoFrame.setTitle("Function Test by recording");
        infoFrame.setBounds(300, 90, 900, 600);
        infoFrame.setVisible(true);
        Container c = infoFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        JLabel URL = new JLabel("<html><b>URL:<b> " + url);
        cons.gridy = 0;
        cons.gridx = 1;

        JLabel info = new JLabel("<html>To start the testing click on the Start button and" +
                "then do actions that you want to test. <br>After making all needed actions <b><br>CLOSE THE RECORDING BROWSER AND " +
                "CLICK ON THE STOP AND SAVE BUTTON</b>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cons.gridy = 1;
        cons.gridx = 0;
        cons.gridwidth = 2;
        cons.weightx = 1.0;
        cons.anchor = GridBagConstraints.PAGE_END;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(0, 50, 40, 50);
        c.add(info, cons);

        KButton start = new KButton();
        start.setText("Start Recording");
        start.kAllowGradient = false;
        start.kBackGroundColor = new Color(29, 92, 92, 178);
        start.kBorderRadius = 40;
        start.setFont(new Font("Arial", Font.PLAIN, 18));
        start.kHoverColor = new Color(17, 62, 62, 178);
        start.kPressedColor = new Color(29, 92, 92, 178);
        start.kHoverForeGround = Color.WHITE;
        start.setBorder(null);
        cons.gridy = 2;
        cons.gridx = 0;
        cons.insets = new Insets(20, 50, 0, 25);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridwidth = 1;
        c.add(start, cons);

        KButton stop = new KButton();
        stop.setText("Stop and Save");
        stop.kAllowGradient = false;
        stop.kBackGroundColor = new Color(115, 3, 0, 237);
        stop.kBorderRadius = 40;
        stop.setFont(new Font("Arial", Font.PLAIN, 18));
        stop.kHoverColor = new Color(160, 36, 37, 178);
        stop.kPressedColor = new Color(179, 33, 14, 255);
        stop.kHoverForeGround = Color.WHITE;
        stop.setBorder(null);
        cons.gridy = 2;
        cons.gridx = 1;
        cons.insets = new Insets(20, 25, 0, 50);
        c.add(stop, cons);

        start.addActionListener(e -> startRecording(url));
        stop.addActionListener(e -> stopAndSaveRecording());
    }


    @lombok.SneakyThrows
    public static void startRecording(String url) {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addExtensions(new File("src/main/resources/extension_3_17_0_0.crx"));
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(chromeOptions);
        driver.get("chrome-extension://mooikfkahbdckldjjndioackbalphokd/index.html");
        driver.findElement(By.linkText("Record a new test in a new project")).click();
        driver.findElement(By.cssSelector("input[name=\"projectName\"]")).sendKeys("Test" + Keys.ENTER);
//        Thread.sleep(5000);
        driver.findElement(By.cssSelector("input[name=\"baseUrl\"]")).sendKeys(url + Keys.ENTER);
    }


    public static void stopAndSaveRecording() {

        driver.findElement(By.cssSelector("button.btn-action.active.si-record")).click();
        driver.findElement(By.cssSelector("input[name=\"test caseName\"]")).sendKeys("Test1" + Keys.ENTER);
        driver.quit();
    }

    public static void playbackRecording() {

    }


    public void openNewFrameForScreenshot(String url) {
        JFrame infoFrame = new JFrame();
        infoFrame.setTitle("UI test by screenshot");
        infoFrame.setBounds(300, 90, 900, 600);
        infoFrame.setVisible(true);
        Container c = infoFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();

        JLabel info = new JLabel("<html>The test will take the screenshot of your provided website<br>" +
                " and then will compare that screenshot with your required result. <br>If you are using this system the first time, the screenshot will be saved <br>" +
                "like a required result.<br><b>Please provide the permissible percentage of difference" +
                "between screenshots and click START<b>");
        info.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cons.gridy = 0;
        cons.gridx = 0;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(0, 50, 40, 50);
        c.add(info, cons);

        JLabel percentLabel = new JLabel("Permissible percentage of difference");
        percentLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        NumberFormat percentFormat;
        percentFormat = NumberFormat.getNumberInstance();
        percentFormat.setMinimumFractionDigits(3);
        JFormattedTextField percentField = new JFormattedTextField(percentFormat);
        percentField.setValue(0.1);

        cons.gridy = 1;
        cons.gridx = 0;
        cons.gridwidth = 1;
        c.add(percentLabel, cons);
        cons.gridx = 1;
        c.add(percentField, cons);


        KButton start = new KButton();
        start.setText("Start Testing");
        start.kAllowGradient = false;
        start.kBackGroundColor = new Color(29, 92, 92, 178);
        start.kBorderRadius = 40;
        start.setFont(new Font("Arial", Font.PLAIN, 18));
        start.kHoverColor = new Color(17, 62, 62, 178);
        start.kPressedColor = new Color(29, 92, 92, 178);
        start.kHoverForeGround = Color.WHITE;
        start.setBorder(null);
        cons.gridy = 2;
        cons.gridx = 0;
        cons.gridwidth = 2;
        cons.insets = new Insets(20, 50, 0, 25);
        cons.fill = GridBagConstraints.HORIZONTAL;
        c.add(start, cons);

        start.addActionListener(e -> {infoFrame.dispose(); makeScreenshots(url, ((Number)percentField.getValue()).doubleValue());});
    }

    public void openNewFrameForResults(Double diff) {
        JFrame infoFrame = new JFrame();
        infoFrame.setTitle("UI test results");
        infoFrame.setBounds(300, 90, 900, 600);
        infoFrame.setVisible(true);
        Container c = infoFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        JLabel info = new JLabel();

        if(diff == 0){
            info.setText("Your website UI is okay. There is no difference between screenshots.");
            c.add(info, cons);
        } else {
        info = new JLabel("<html>There are some issues in your website UI.<br>" +
                "The difference between screenshots are: ");
        JLabel result = new JLabel(diff.toString());
        info.setFont(new Font("SansSerif", Font.BOLD, 24));
        cons.gridy = 0;
        c.add(info, cons);
        cons.gridy = 1;
        c.add(result, cons);
        }


    }



    @Test
    public void makeScreenshots(String url, Double diffInPercent) {

//        List<WebElement> links = driver.findElements(By.tagName("a"));
//        Iterator<WebElement> it = links.iterator();
//        String nestedUrl = null;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        final String urlWithoutSymbols = url.replaceAll("://", "");
        System.out.println(urlWithoutSymbols);
        takeScreenshot(urlWithoutSymbols + "_result.png");
        double diff = compareImages(urlWithoutSymbols + "_result.png", urlWithoutSymbols + "_golden.png", diffInPercent);
//        while(it.hasNext()){
//
//            nestedUrl = it.next().getAttribute("href");
//            if(nestedUrl == null || nestedUrl.isEmpty()){
//                System.out.println("URL is either not configured for anchor tag or it is empty");
//            }
//
//        }
        if(diff <= diffInPercent){
            diff = 0;
            removeFile(urlWithoutSymbols + "_result.png");
        }
        driver.quit();
        openNewFrameForResults(diff);

    }


    public void takeScreenshot(String current) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(current));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFile(String pathString) {
        Path path = Paths.get(pathString);

        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }

    protected static double getImgCmpDiffPercent(BufferedImage goldenData, BufferedImage resultData) {
        int goldenWidth = goldenData.getWidth();
        int goldenHeight = goldenData.getHeight();
        int resultWidth = resultData.getWidth();
        int resultHeight = resultData.getHeight();
        if (goldenWidth != resultWidth || goldenHeight != resultHeight) {
            throw new IllegalArgumentException(String.format("Images must have the same dimensions: (%d,%d) vs. (%d,%d)", goldenWidth, goldenHeight, resultWidth, resultHeight));
        }

        long diff = 0;
        for (int y = 0; y < goldenHeight; y++) {
            for (int x = 0; x < goldenWidth; x++) {
                diff += pixelDiff(goldenData.getRGB(x, y), resultData.getRGB(x, y));
            }
        }
        long maxDiff = 3L * 255 * goldenWidth * goldenHeight;

        return 100.0 * diff / maxDiff;
    }

    private static int pixelDiff(int goldenPixelRGB, int resultPixelRGB) {
        int goldenRed = (goldenPixelRGB >> 16) & 0xff;
        int goldenGreen = (goldenPixelRGB >> 8) & 0xff;
        int goldenBlue = goldenPixelRGB & 0xff;
        int resultRed = (resultPixelRGB >> 16) & 0xff;
        int resultGreen = (resultPixelRGB >> 8) & 0xff;
        int resultBlue = resultPixelRGB & 0xff;
        return Math.abs(goldenRed - resultRed) + Math.abs(goldenGreen - resultGreen) + Math.abs(goldenBlue - resultBlue);
    }

    public double compareImages(String resultPath, String goldenPath, double diffInPercent) {
        BufferedImage golden, result;
        double diff = 0;
        try {
            result = ImageIO.read(new File(resultPath));
            final File goldenFile = new File(goldenPath);
            if (!goldenFile.exists()) {
                FileUtils.copyFile(new File(resultPath), goldenFile);
            }
            golden = ImageIO.read(goldenFile);
            diff = getImgCmpDiffPercent(golden, result);

//            assertThat(diff).as("Difference between baseline and checkpoint").isEqualTo(diffInPercent);
//            removeFile(resultPath);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return diff;
    }

}

