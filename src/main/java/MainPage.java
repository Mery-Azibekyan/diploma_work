
import autoitx4java.AutoItX;
import io.github.bonigarcia.wdm.WebDriverManager;
import keeptoo.KButton;
import keeptoo.KGradientPanel;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;

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
    private String[] typesList = {"Functional Test by recording", "Check for broken links and images", "Validate the UI by screenshot", "Check for XSS attack"};

    private static WebDriver driver;
//    private static final String DISABLE_XSS_AUDITOR = "--disable-xss-auditor";

    private WebDriver setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(1295, 843));
        return driver;
    }

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
//
//        projectName = new JLabel("ProjectName");
//        projectName.setForeground(new Color(204, 204, 204));
//        projectName.setFont(new Font("DialogInput", Font.PLAIN, 14));
//        c.gridy = 0;
//        c.gridx = 0;
//        c.anchor = GridBagConstraints.FIRST_LINE_START;
//        c.weighty = 1.0;
//        c.insets = new Insets(30, 30, 20, 0);
//
//        kPanel.add(projectName, c);

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
                            case "Check for broken links and images":
                                checkForBrokenLinksAndImages(url);
                                break;
                            case "Functional Test by recording":
                                openNewFrameForRecording(url);
                                break;
                            case "Validate the UI by screenshot":
                                openNewFrameForScreenshot(url);
                                break;
                            case "Check for XSS attack":
                                checkForXSSAttack(url);
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
    public void checkForBrokenLinksAndImages(String url) {
        //https://the-internet.herokuapp.com/broken_images
        String homePage = url;
        driver = setupDriver();
        driver.get(homePage);

        List<String> listOfBrokenLinks = driver.findElements(By.xpath("//*[@href]"))
                .stream()
                .parallel()
                .map(e -> e.getAttribute("href"))
                .filter(href -> LinkUtil.getResponseCode(href) != 200)
                .collect(Collectors.toList());


        List<String> listOfBrokenImages = driver.findElements(By.xpath("//*[@src]"))
                .stream()
                .parallel()
                .map(e -> e.getAttribute("src"))
                .filter(href -> LinkUtil.getResponseCode(href) != 200)
                .collect(Collectors.toList());
        driver.quit();
        final int brokenLinksCount = listOfBrokenLinks.size();
        final int brokenImagesCount = listOfBrokenImages.size();
        JFrame resultFrame = new JFrame();
        resultFrame.setTitle("Results");
        resultFrame.setBounds(10, 10, 900, 600);
        resultFrame.setVisible(true);
        Container c = resultFrame.getContentPane();

        String[] data1 = listOfBrokenLinks.toArray(new String[0]);
        final DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn(brokenLinksCount + " URLs are broken in your website", data1);
        JTable jt1 = new JTable(model1);
        jt1.setRowHeight(30);
        GridLayout grid = new GridLayout(0, 1, 30, 20);
        JPanel jPanelTable = new JPanel(grid);
        jt1.setFont(new Font("SansSerif", Font.PLAIN, 20));
        jt1.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20));
        jt1.getTableHeader().setForeground(new Color(103, 13, 14));
        jPanelTable.add(jt1.getTableHeader(), BorderLayout.NORTH);
        jPanelTable.add(jt1, BorderLayout.CENTER);

        String[] data2 = listOfBrokenImages.toArray(new String[0]);
        final DefaultTableModel model2 = new DefaultTableModel();
        model2.addColumn(brokenImagesCount + " images are broken in your website", data2);
        JTable jt2 = new JTable(model2);
        jt2.setRowHeight(30);
        jt2.setFont(new Font("SansSerif", Font.PLAIN, 20));
        jt2.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20));
        jt2.getTableHeader().setForeground(new Color(103, 13, 14));
        jPanelTable.add(jt2.getTableHeader(), BorderLayout.NORTH);
        jPanelTable.add(jt2, BorderLayout.CENTER);
        JScrollPane sp = new JScrollPane(jPanelTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBounds(10, 200, 1000, 800);
        resultFrame.add(sp);
        resultFrame.setVisible(true);
    }


    public void openNewFrameForRecording(String url) {
        JFrame infoFrame = new JFrame();
        BufferedImage backImage;
        try {
            backImage = ImageIO.read(new File("img1.png"));
            infoFrame.setContentPane(new ImagePanel(backImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        infoFrame.setTitle("Function Test by recording");
        infoFrame.setBounds(20, 20, 900, 600);
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
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("input[name=\"baseUrl\"]")).sendKeys(url + Keys.ENTER);
    }


    @lombok.SneakyThrows
    public void stopAndSaveRecording() {

        driver.findElement(By.cssSelector("button.btn-action.active.si-record")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[name=\"test caseName\"]")).sendKeys("Test1" + Keys.ENTER);
        Thread.sleep(3000);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[1]/div/div[2]/span/button[2]")).click();
//        wait.until(
//                ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelec)));
//        wait.until(
//                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelec)));
//        wait.until(
//                ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelec)));
//        driver.findElement(By.cssSelector(cssSelec)).click();


        driver.quit();
//        AutoItX autoItX = new AutoItX();
//        autoItX.controlGetFocus("Save As");
//        autoItX.send("{ENTER}");
        openNewFrameForInputs();
    }

    public static void playbackRecording() {

    }


    public void openNewFrameForScreenshot(String url) {
        JFrame infoFrame = new JFrame();
        BufferedImage backImage;
        try {
            backImage = ImageIO.read(new File("img1.png"));
            infoFrame.setContentPane(new ImagePanel(backImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        infoFrame.setTitle("UI test by screenshot");
        infoFrame.setBounds(20, 20, 900, 600);
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

        start.addActionListener(e -> {
            infoFrame.dispose();
            makeScreenshots(url, ((Number) percentField.getValue()).doubleValue());
        });
    }

    public void openNewFrameForResults(Double diff) {
        JFrame infoFrame = new JFrame();
        BufferedImage backImage;
        try {
            backImage = ImageIO.read(new File("img1.png"));
            infoFrame.setContentPane(new ImagePanel(backImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        infoFrame.setTitle("UI test results");
        infoFrame.setBounds(20, 20, 900, 600);
        infoFrame.setVisible(true);
        Container c = infoFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        JLabel info = new JLabel();

        if (diff == 0) {
            info.setText("Your website UI is okay. There is no difference between screenshots.");
            c.add(info, cons);
        } else {
            info = new JLabel("<html>There are some issues in your website UI.<br>" +
                    "The difference between screenshots are: ");
        }
        JLabel result = new JLabel(diff.toString() + "%");
        info.setFont(new Font("SansSerif", Font.BOLD, 20));
        result.setFont(new Font("SansSerif", Font.BOLD, 36));
        cons.gridy = 0;
        c.add(info, cons);
        cons.gridy = 1;
        c.add(result, cons);

    }

    public void makeScreenshots(String url, Double diffInPercent) {

        driver = setupDriver();
        driver.get(url);
        List<String> list = new ArrayList<>();
        list.add(url);
        list.addAll(driver.findElements(By.xpath("//*[@href]"))
                .stream()
                .parallel()
                .map(e -> e.getAttribute("href"))
                .collect(Collectors.toList()));
        double diffSum = 0;
//        System.out.println(list);
        for (String nestedUrl : list) {
            if (nestedUrl == null || nestedUrl.isEmpty()) {
                System.out.println("URL is either not configured for anchor tag or it is empty");
                continue;
            }
            driver.get(nestedUrl);
            String urlWithoutSymbols = nestedUrl.replaceAll("[:/?|*<>\"]", "");
//            urlWithoutSymbols = urlWithoutSymbols.replaceAll("/", "");
//            urlWithoutSymbols = urlWithoutSymbols.replaceAll("\\?", "");
            System.out.println(urlWithoutSymbols);
            takeScreenshot(urlWithoutSymbols + "_result.png");
            double diff = compareImages(urlWithoutSymbols + "_result.png", urlWithoutSymbols + "_golden.png");
            if (diff <= diffInPercent) {
                diff = 0;
                removeFile(urlWithoutSymbols + "_result.png");
            }
            diffSum += diff;
        }
        driver.quit();
        openNewFrameForResults(round(diffSum, 2));

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

    public double compareImages(String resultPath, String goldenPath) {
        BufferedImage golden, result;
        double diff = 0;
        File goldenFile;
        try {
            result = ImageIO.read(new File(resultPath));
            goldenFile = new File(goldenPath);
            if (!goldenFile.exists()) {
                FileUtils.copyFile(new File(resultPath), goldenFile);
            }
            golden = ImageIO.read(goldenFile);
            diff = getImgCmpDiffPercent(golden, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diff;
    }

    public void checkForXSSAttack(String url) {

        WebDriverManager.chromedriver().setup();
        driver = setupDriver();
//        driver = new ChromeDriver(getChromeCapabilities());
        driver.get("https://xss-game.appspot.com/level1/frame");
        List<WebElement> forms = driver.findElements(By.xpath("//form"))
                .stream()
                .parallel()
                .collect(Collectors.toList());

        for (WebElement e : forms) {
            List<WebElement> inputs = e.findElements(By.xpath("//input"))
                    .stream()
                    .parallel()
                    .filter(element -> element.getAttribute("type").matches("text|password|email|number|url|search|tel"))
                    .collect(Collectors.toList());
            inputs.addAll(new ArrayList<>(e.findElements(By.xpath("//textarea"))));

            String XSS_CONTENT = "<script>alert('1');</script>";
            for (WebElement input : inputs) {
                input.sendKeys(XSS_CONTENT);
            }
            e.submit();
            boolean alert = isAlertDisplayed(driver);
            driver.quit();
            openNewFrameForXSSResult(alert);
        }
//        System.out.println(forms);
    }

    //
//    private DesiredCapabilities getChromeCapabilities() {
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
//        return capabilities;
//    }
//
//    private ChromeOptions getChromeOptions() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments(getChromeSwitches());
//        return options;
//    }
//
//    private List<String> getChromeSwitches() {
//        List<String> chromeSwitches = new ArrayList<>();
//        chromeSwitches.add(DISABLE_XSS_AUDITOR);
//        return chromeSwitches;
//    }
//
    public boolean isAlertDisplayed(WebDriver driver) {
        boolean foundAlert;
        WebDriverWait wait = new WebDriverWait(driver, 5);
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            foundAlert = true;
        } catch (TimeoutException e) {
            foundAlert = false;
        }
        return foundAlert;
    }

    public void openNewFrameForXSSResult(boolean foundAlert) {
        JFrame infoFrame = new JFrame();
        BufferedImage backImage;
        try {
            backImage = ImageIO.read(new File("img1.png"));
            infoFrame.setContentPane(new ImagePanel(backImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        infoFrame.setTitle("XSS attack test results");
        infoFrame.setBounds(20, 20, 900, 600);
        infoFrame.setVisible(true);
        Container c = infoFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        JLabel info = new JLabel();

        if (foundAlert == false) {
            info.setText("Your provided web page is protected from XSS attack!!!");
            info.setForeground(new Color(8, 96, 17));
        } else {
            info.setText("<html>Oops! Your web page has some security issues. <br>There was detected an alert during the XSS attack test");
            info.setForeground(new Color(160, 36, 37, 208));
        }
        info.setFont(new Font("SansSerif", Font.BOLD, 28));
        c.add(info, cons);
    }

    @Test
    final Map<String, String> getInputs() {

        final String path = "src/main/resources/Test1.side";
        final String file = generateStringFromResource(path);
        List<String> values = from(file).getList("tests[0].commands.findAll{it.command == 'type'}.value");
        List<ArrayList> keysList = from(file).getList("tests[0].commands.findAll{it.command == 'type'}.targets");
        ArrayList<String> keys = new ArrayList<>();
        for (ArrayList key : keysList) {
            List<ArrayList> temp = (List<ArrayList>) key.get(1);
            System.out.println(temp.get(0));
            keys.add(String.valueOf(temp.get(0)));
        }

        Map<String, String> dataMap = new HashMap<String, String>();
        for (int i = 0; i < values.size(); i++) {
            dataMap.put(keys.get(i).toString(), values.get(i).toString());
        }
        System.out.println(dataMap);

        return dataMap;
    }


    @Test
    public void openNewFrameForInputs() {
        Map<String, String> data = getInputs();
        JFrame infoFrame = new JFrame();
        kPanel = new KGradientPanel();
        kPanel.setLayout(new GridBagLayout());
        kPanel.setSize(800, 600);
        GridBagConstraints cons = new GridBagConstraints();
        infoFrame.setContentPane(kPanel);
        Color startColor = new Color(10, 150, 151, 202);
        Color endColor = new Color(175, 193, 204);
        kPanel.kStartColor = startColor;
        kPanel.kEndColor = endColor;
        kPanel.kGradientFocus = 200;
        infoFrame.setTitle("User inputs");
        infoFrame.setBounds(20, 20, 1200, 600);
        infoFrame.setVisible(true);
        Container c = infoFrame.getContentPane();
        c.setLayout(new GridBagLayout());
        JLabel info = new JLabel("<html>There is your input actions. Please provide the valid values for the following fields." +
                "<br>The system will generate new tests with your provided values.<br>" +
                "<br><b> NOTE: The values should be separated by comma </b>");
        cons.gridy = 0;
        cons.gridx = 0;
        cons.gridwidth = 2;
        c.add(info, cons);
        int y = 1;
        List<JTextField> list = new ArrayList<JTextField>();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            JLabel key = new JLabel(entry.getKey());
            cons.gridy = y++;
            cons.gridx = 0;
            cons.gridwidth = 1;
            cons.fill = GridBagConstraints.HORIZONTAL;
            cons.insets = new Insets(10, 0, 20, 5);
            kPanel.add(key, cons);
            JTextField value = new JTextField(entry.getValue());
            cons.gridx = 1;
            cons.fill = GridBagConstraints.HORIZONTAL;
            cons.insets = new Insets(10, 10, 20, 5);
            value.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
            value.setOpaque(false);
            value.setFont(new Font("Arial", Font.PLAIN, 14));
            value.setForeground(Color.WHITE);
            value.setSize(300, 20);
            kPanel.add(value, cons);
            list.add(value);
        }
        KButton test = new KButton();
        test.setText("Generate Tests");
        test.kAllowGradient = false;
        test.kBackGroundColor = new Color(29, 92, 92, 178);
        test.kBorderRadius = 40;
        test.setFont(new Font("Arial", Font.PLAIN, 18));
        test.kHoverColor = new Color(17, 62, 62, 178);
        test.kPressedColor = new Color(29, 92, 92, 178);
        test.kHoverForeGround = Color.WHITE;
        test.setBorder(null);
        cons.gridy = y;
        cons.gridx = 0;
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.HORIZONTAL;
        kPanel.add(test, cons);
        test.addActionListener(e -> {
            try {
                generateTests(list, data);
            } catch (ParseException | IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Test
    public void generateTests(List<JTextField> list, Map<String, String> data) throws ParseException, IOException {
        final String path = "src/main/resources/Test1.side";
        String file = generateStringFromResource(path);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(file);

//        final File file = new File(path);
//
//        try {
//            int i = 0;
//            for (Map.Entry<String, String> entry : data.entrySet()){
//                Scanner scanner = new Scanner(file);
//                int lineNum = 0;
//                while (scanner.hasNextLine()) {
//                    String line = scanner.nextLine();
//                    lineNum++;
//                    if (line.contains(entry.getKey())) {
//                        line.replace(entry.getValue(), list.get(i++).getText());
//                        System.out.println(line);
//                    }
//                }
//                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.addExtensions(new File("src/main/resources/extension_3_17_0_0.crx"));
//                WebDriverManager.chromedriver().setup();
//                driver = new ChromeDriver(chromeOptions);
//                driver.get("chrome-extension://mooikfkahbdckldjjndioackbalphokd/index.html");
//
//
//            }
//            //now read the file line by line...
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
////        }
        JSONArray tests = (JSONArray) json.get("tests");
        JSONObject objectInsideTests = (JSONObject) tests.get(0);
        JSONArray commands = (JSONArray) objectInsideTests.get("commands");
//        System.out.println(tests);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            for (int i = 0; i < commands.size(); i++) {
                JSONObject object = (JSONObject) commands.get(i);
                JSONArray targets = (JSONArray) object.get("targets");
                if (targets.size() > 0) {
                    JSONArray targetOfName = (JSONArray) targets.get(1);
                    String target = targetOfName.get(0).toString();
                    if ( target == entry.getKey()) {
                        object.put("value", entry.getValue());
                        break;
                    }
                }
                System.out.println(object);
            }
//            System.out.println(json);
        }

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String generateStringFromResource(final String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}




