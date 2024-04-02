/**
 * @author Rand7Y9Z@gmail.com
 * @since 2024
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;


public class GUI extends JFrame implements ActionListener, KeyListener {

    private boolean showCalc = true;
    private boolean showResults = false;

    //------------------------------------------------------------------------------------------------------------------
    int[] bgC = Config.getBgColor();
    Color bgColor = new Color(bgC[0], bgC[1], bgC[2]);
    int[] bgC2 = Config.getBgColor2();
    Color bgColor2 = new Color(bgC2[0], bgC2[1], bgC2[2]);
    int[] bgC3 = Config.getBgColor3();
    Color bgColor3 = new Color(bgC3[0], bgC3[1], bgC3[2]);

    int[] acC = Config.getAcColor();
    Color acColor = new Color(acC[0], acC[1], acC[2]);

    int[] fC = Config.getFontColor();
    Color fontColor = new Color(fC[0], fC[1], fC[2]);


    protected int updateTime = Config.getUpdateTime();

    /**
     * creates the calc input field
     *
     * @return is the created input field
     */
    private JTextField createInputField() {
        JTextField ret = new JTextField();

        ret.setHorizontalAlignment(JTextField.LEFT);
        ret.setBackground(acColor);
        ret.setForeground(fontColor);
        ret.setFont(new Font("Calibri", Font.BOLD, 70));
        ret.setEditable(true);
        ret.addKeyListener(this);
        ret.setFocusable(true);
        ret.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        ret.setVisible(true);

        return ret;
    }

    private Font buttonFont = new Font("Calibri", Font.BOLD, 48);

    /**
     * creates a button for the calc panel
     *
     * @param DisplayedName       the name to be displayed on the button
     * @param defaultActiveStatus the default active status of the button
     * @return is the created button
     */
    private JButton createButton(String DisplayedName, boolean defaultActiveStatus) {
        JButton ret = new JButton();

        ret.addActionListener(this);
        ret.setText(DisplayedName);
        ret.setFocusable(false);
        ret.setFont(buttonFont);
        ret.setForeground(fontColor);
        ret.setBackground(bgColor);
        ret.setHorizontalTextPosition(SwingConstants.CENTER);
        Border roundedBorder = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);
        ret.setBorder(BorderFactory.createCompoundBorder(roundedBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        ret.setEnabled(defaultActiveStatus);

        return ret;
    }

    /**
     * creates a button for the top of row of the calc panel
     *
     * @param DisplayedName       the name to be displayed on the button
     * @param defaultActiveStatus the default active status of the button
     * @return is the created top button
     */
    private JButton createTopButton(String DisplayedName, boolean defaultActiveStatus) {
        JButton ret = new JButton() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, (int) (topButtonsPanel.getHeight() * 1.2));
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };

        ret.addActionListener(this);
        ret.setFont(buttonFont);
        ret.setText(DisplayedName);

        ret.setFocusable(false);
        ret.setFont(buttonFont);
        ret.setForeground(fontColor);
        ret.setBackground(bgColor);
        ret.setHorizontalTextPosition(SwingConstants.CENTER);
        Border roundedBorder = BorderFactory.createMatteBorder(1, 1, 2, 1, Color.BLACK);
        ret.setBorder(BorderFactory.createCompoundBorder(roundedBorder, BorderFactory.createEmptyBorder(5, 0, 0, 0)));
        ret.setEnabled(defaultActiveStatus);

        return ret;
    }

    JButton[] buttons = new JButton[]{
            createButton("E", true),
            createButton("1", true),
            createButton("4", true),
            createButton("7", true),
            createButton("+", false),
            createButton("×", false),
            createButton("sin", true),
            createButton("2", true),
            createButton("5", true),
            createButton("8", true),
            createButton("-", true),
            createButton("/", false),
            createButton("cos", true),
            createButton("3", true),
            createButton("6", true),
            createButton("9", true),
            createButton("^", false),
            createButton("%", false),
            createButton("tan", true),
            createButton("π", true),
            createButton("0", true),
            createButton(".", false),
            createButton("√", true),
            createButton("!", false),
            createButton("log", true),
            createButton("|", true),
            createButton("(", true),
            createButton(")", false),
            createButton("=", true),
            createButton("", true)

    };

    final String[] topButtonsLabels = new String[]{"Clear All", "Copy", "Paste", "DEG"};
    JButton[] topButtons = new JButton[]{
            createTopButton(topButtonsLabels[0], true),
            createTopButton(topButtonsLabels[1], true),
            createTopButton(topButtonsLabels[2], true),
            createTopButton(topButtonsLabels[3], true)
    };

    JPanel buttonsPanel = createButtonPanel();
    JPanel topButtonsPanel = createTopButtonPanel();


    //----------------------------------------------------------------------------------------------

    JPanel panelCalculator = new JPanel();
    JTextField inputFieldForCalculator = createInputField();

    //------------------------------------------------------------------------------------------------------------------

    /**
     * creates the button panel for the calc panel
     *
     * @return is the created button panel
     */
    private JPanel createButtonPanel() {
        JPanel Panel = new JPanel();
        Panel.setBackground(bgColor3);
        Panel.setLayout(new GridLayout(5, 10, 10, 10));

        Arrays.stream(buttons, 0, buttons.length).forEach(Panel::add);
        ImageIcon DelIcon = new ImageIcon("images/DEL.png");
        buttons[29].setIcon(DelIcon);
        Panel.add(buttons[29]);
        Panel.setBorder(new EmptyBorder(10, 10, 10, 20));

        return Panel;
    }

    /**
     * creates the top button panel for the calc panel
     *
     * @return is the created top button panel
     */
    private JPanel createTopButtonPanel() {
        JPanel Panel = new JPanel();

        Panel.setBackground(bgColor3);
        Panel.setLayout(new GridLayout(1, topButtons.length, 0, 0));
        Panel.setBorder(new EmptyBorder(1, 0, 0, 0));
        Arrays.stream(topButtons, 0, topButtons.length).forEach(Panel::add);

        return Panel;
    }

    //------------------------------------------------------------------------------------------------------------------7

    JPanel panelShowResults = createResultsPanel();
    JTextArea textAreaOfPanelShowResults;

    private JPanel createResultsPanel() {
        JPanel ret = new JPanel();
        ret.setBackground(bgColor);
        ret.setLayout(new BorderLayout());
        ret.setBorder(new EmptyBorder(0, 0, 0, 0));

        textAreaOfPanelShowResults = new JTextArea();
        textAreaOfPanelShowResults.setBounds(10, 10, 10, 10);
        textAreaOfPanelShowResults.setEditable(false);
        textAreaOfPanelShowResults.setBackground(bgColor);
        textAreaOfPanelShowResults.setForeground(fontColor);
        textAreaOfPanelShowResults.setFont(new Font("Calibri", Font.BOLD, 20));
        textAreaOfPanelShowResults.setText(Logs.readResults());

        JScrollPane out = new JScrollPane(textAreaOfPanelShowResults);
        out.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        out.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        ret.add(textAreaOfPanelShowResults);
        ret.setVisible(showResults);

        return ret;
    }


    //------------------------------------------------------------------------------------------------------------------

    JMenuItem resetLogsOption = new JMenuItem("Reset Logs");
    JMenuItem reloadOption = new JMenuItem("Reload the program");
    JMenuItem closeOption = new JMenuItem("Close Program");
    JMenuItem showCalResultsOption = new JMenuItem("Show Calculator Results");

    //------------------------------------------------------------------------------------------------------------------

    private JMenuBar createMenuBar() {
        JMenuBar ret = new JMenuBar();
        ret.setPreferredSize(new java.awt.Dimension(this.getWidth(), 27));
        ret.setBackground(bgColor2);
        ret.setBounds(0, 0, this.getWidth(), 50);
        ret.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        ret.setVisible(true);

        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.setBackground(bgColor2);
        resetLogsOption.addActionListener(this);
        resetLogsOption.setBackground(bgColor2);
        resetLogsOption.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        reloadOption.addActionListener(this);
        reloadOption.setBackground(bgColor2);
        reloadOption.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        closeOption.addActionListener(this);
        closeOption.setBackground(bgColor2);
        closeOption.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
        optionsMenu.add(resetLogsOption);
        optionsMenu.add(reloadOption);
        optionsMenu.add(closeOption);
        ret.add(optionsMenu);

        JMenu calculatorModesMenu = new JMenu("Calculator Modes");
        ret.add(calculatorModesMenu);

        JMenu helpMenu = new JMenu("Help");
        ret.add(helpMenu);

        JMenu showResultsMenu = new JMenu("Show history");
        showResultsMenu.setBackground(bgColor2);
        showCalResultsOption.addActionListener(this);
        showCalResultsOption.setBackground(bgColor2);
        showCalResultsOption.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));
        showResultsMenu.add(showCalResultsOption);
        ret.add(showResultsMenu);


        return ret;
    }

    protected GUI() throws RuntimeException {

        this.setTitle(Config.getName());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(Config.getHeightAndWidth()[1], Config.getHeightAndWidth()[0]);
        this.setMinimumSize(new Dimension(395, 570));
        this.setLayout(null);
        this.setFocusable(false);

        ImageIcon img = new ImageIcon("images/icon.png");
        this.setIconImage(img.getImage());
        this.getContentPane().setBackground(new Color(37, 37, 37));

        this.setJMenuBar(createMenuBar());

        //--------------------------------------------------------------------------------------------------------------
        Thread ThreadForCalc = getThreadForGUIUpdate();
        ThreadForCalc.setDaemon(true);
        ThreadForCalc.start();

        panelCalculator.setBackground(bgColor);
        panelCalculator.setLayout(new BorderLayout());
        panelCalculator.add(topButtonsPanel, BorderLayout.NORTH);
        panelCalculator.add(buttonsPanel, BorderLayout.CENTER);
        panelCalculator.add(inputFieldForCalculator);
        panelCalculator.setVisible(showCalc);


        this.add(panelCalculator);
        this.add(panelShowResults);

        this.pack();
        this.setVisible(true);
        Logs.writeToLog(String.format("GUI has been loaded successfully with dimensions: %dx%d", panelCalculator.getWidth(), panelCalculator.getHeight()) + System.lineSeparator());
    }

    public void closeFrame() {
        Logs.writeToLog("Program has been closed manually");
        this.dispose();
    }

    public void reloadFrame() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            try {
                new GUI();
            } catch (RuntimeException e) {
                Logs.writeToLog("could not reload GUI -> " + e.getMessage());
            }
        });
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * creates a thread for gui update for calc panel
     *
     * @return is the thread
     */
    private Thread getThreadForGUIUpdate() {
        Runnable updater = () -> {
            long time = System.currentTimeMillis();
            do {
                if (time + updateTime >= System.currentTimeMillis()) {
                    buttonFont = new Font("Calibri", Font.BOLD, (int) ((panelCalculator.getHeight() * 0.95) * (panelCalculator.getWidth() * 0.65)) / 6700);
                    for (JButton button : buttons) {
                        button.setFont(buttonFont);
                    }
                    for (JButton button : topButtons) {
                        button.setFont(buttonFont);
                    }
                    Dimension panelSize = new Dimension(this.getWidth(), (int) (this.getHeight() * 0.92));

                    panelCalculator.setBounds(0, 0, panelSize.width, panelSize.height);
                    topButtonsPanel.setBounds(0, 0, panelCalculator.getWidth(), (int) (panelCalculator.getHeight() * 0.06));
                    inputFieldForCalculator.setBounds(0, (int) (panelCalculator.getHeight() * 0.07), panelCalculator.getWidth(), (int) (panelCalculator.getHeight() * 0.28));
                    buttonsPanel.setBounds(0, (int) (panelCalculator.getHeight() * 0.35) - 1, panelCalculator.getWidth(), (int) (panelCalculator.getHeight() * 0.63));

                    panelShowResults.setBounds(0, 0, panelSize.width, panelSize.height);

                }
                time = System.currentTimeMillis();


            } while (true);
        };

        return new Thread(updater);
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == resetLogsOption) {
            Logs.resetLogs();
        } else if (e.getSource() == reloadOption){
            reloadFrame();
        } else if (e.getSource() == closeOption){
            closeFrame();

        }else if (e.getSource() == showCalResultsOption) {
            showCalc = !showCalc;
            showResults = !showResults;
            showCalResultsOption.setText(!showResults ? "Show Calculator Results" : "Hide Calculator Results");
            Logs.writeToLog(String.format("showCalc: %b, showResults: %b", showCalc, showResults));
            textAreaOfPanelShowResults.setText(Logs.readResults());
            panelCalculator.setVisible(showCalc);
            panelShowResults.setVisible(showResults);
        }

        if (showCalc) {
            Main.calInput = inputFieldForCalculator.getText();
            if (contains(Main.calInput, Cal.getPossibleErrors())) {
                Main.calInput = "";
            }

            if (e.getSource() == topButtons[0]) Main.calInput = "";

            topButtons[1].setText("Copy");
            if (e.getSource() == topButtons[1]) {
                try {
                    copyOutOfCalInput();
                } catch (Exception ex) {
                    Logs.writeToLog("failed to copy content out of calc input -> " + ex.getMessage());
                }
            }

            if (e.getSource() == topButtons[2]) {
                try {
                    pasteIntoCalInput();
                } catch (Exception ex) {
                    Logs.writeToLog("failed to paste content into calc input -> " + ex.getMessage());
                }
            }

            if (e.getSource() == topButtons[3]) setDEGAndRAD();

            if (e.getSource() == buttons[28]) manageCalcInput();

            if (e.getSource() == buttons[29]) doDELInCal();

            String[] addingChars = {"E", "1", "4", "7", "+", "*", "sin(", "2", "5", "8", "-", "/", "cos(", "3", "6", "9", "^", "%", "tan(", "π", "0", ".", "√(", "!", "log(", "|", "(", ")", "=", ""};
            IntStream.range(0, buttons.length - 2).forEach(i -> Main.calInput += e.getSource() == buttons[i] ? addingChars[i] : "");

            inputFieldForCalculator.setText(Main.calInput);

            checkOnButtons();

            //System.out.println(Main.calInput);
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (showCalc) {
            Main.calInput = inputFieldForCalculator.getText();
            manageKeyInputsForCalcPanel(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * manages key inputs when the calc panel is shown
     *
     * @param e the key event to be managed
     */
    private void manageKeyInputsForCalcPanel(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            manageCalcInput();
        }

        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            Main.calInput = "";
            inputFieldForCalculator.setText(Main.calInput);
        }

        if (e.getKeyCode() == KeyEvent.VK_INSERT) {
            try {
                pasteIntoCalInput();
            } catch (UnsupportedFlavorException | IOException ex) {
                Logs.writeToLog("failed to paste content into calc input -> " + ex.getMessage());
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * copies the content out of the calc input field
     *
     * @throws RuntimeException if the content can't be copied
     */
    private void copyOutOfCalInput() throws RuntimeException {
        String str = Main.calInput;
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection s = new StringSelection(str);
        clip.setContents(s, s);
        topButtons[1].setText("Copied");
        Logs.writeToLog("copied content from calc input successfully");
    }

    /**
     * pastes the content into the calc input field
     *
     * @throws UnsupportedFlavorException if the flavor is not supported
     * @throws IOException                if an I/O error occurs (with getTransferData)
     */
    private void pasteIntoCalInput() throws UnsupportedFlavorException, IOException {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        Main.calInput = Main.calInput + t.getTransferData(DataFlavor.stringFlavor);
        inputFieldForCalculator.setText(Main.calInput);
        Logs.writeToLog("pasted content into calc input successfully");
    }

    /**
     * sets the DEG and RAD mode when the button is clicked
     */
    private void setDEGAndRAD() {
        Main.isDEG = !Main.isDEG;
        topButtons[3].setText(Main.isDEG ? "DEG" : "RAD");
    }

    /**
     * manages the calc input and writes the result to the results log file (and shows the result in the calc input field)
     */
    private void manageCalcInput() {
        String s = Main.calInput;

        Cal C = new Cal(Main.calInput, Main.isDEG);
        Main.calInput = C.getValue();
        inputFieldForCalculator.setText(Main.calInput);

        Logs.writeToResults(C.throwsError() ? C.getValue() : String.format("%s = %s", s, C.getValue()));

    }

    /**
     * checks if the buttons should be enabled or disabled based on the content of the calc input field
     */
    private void checkOnButtons() {
        buttons[0].setEnabled(isAllowedEPlacement(Main.calInput));
        buttons[1].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[2].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[3].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[4].setEnabled(isAllowedOperatorPlacement(Main.calInput));
        buttons[5].setEnabled(isAllowedOperatorPlacement(Main.calInput));

        buttons[6].setEnabled(isAllowedSinCosTanLogPiPlacement(Main.calInput));
        buttons[7].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[8].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[9].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[11].setEnabled(isAllowedOperatorPlacement(Main.calInput));

        buttons[12].setEnabled(isAllowedSinCosTanLogPiPlacement(Main.calInput));
        buttons[13].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[14].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[15].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[16].setEnabled(isAllowedOperatorPlacement(Main.calInput));
        buttons[17].setEnabled(isAllowedOperatorPlacement(Main.calInput));

        buttons[18].setEnabled(isAllowedSinCosTanLogPiPlacement(Main.calInput));
        buttons[19].setEnabled(isAllowedSinCosTanLogPiPlacement(Main.calInput));
        buttons[20].setEnabled(isAllowedNumberPlacement(Main.calInput));
        buttons[21].setEnabled(isAllowedCommaPlacement(Main.calInput));
        buttons[22].setEnabled(isAllowedRootPlacement(Main.calInput));
        buttons[23].setEnabled(isAllowedFactorialPlacement(Main.calInput));

        buttons[24].setEnabled(isAllowedSinCosTanLogPiPlacement(Main.calInput));
        buttons[26].setEnabled(isAllowedOpenBracketPlacement(Main.calInput));
        buttons[27].setEnabled(isAllowedCloseBracketPlacement(Main.calInput));
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * checks if a comma can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if a comma can be placed, false otherwise
     */
    private boolean isAllowedCommaPlacement(String s) {
        if (s.isEmpty() || !"1234567890".contains(String.valueOf(s.charAt(s.length() - 1)))) return false;

        int i = s.length() - 1;
        StringBuilder t = new StringBuilder();
        while (i >= 0 && "1234567890.".contains(String.valueOf(s.charAt(i)))) {
            t.append(s.charAt(i));
            i--;
        }
        return !t.toString().contains(".");
    }

    /**
     * checks if an operator can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if an operator can be placed, false otherwise
     */
    private boolean isAllowedOperatorPlacement(String s) {
        return !s.isEmpty() && !"./*-+%^(".contains(String.valueOf(s.charAt(s.length() - 1)));
    }

    /**
     * checks if 'E' can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if 'E' can be placed, false otherwise
     */
    private boolean isAllowedEPlacement(String s) {
        return s.isEmpty() || "1234567890/*-+%(^π".contains(String.valueOf(s.charAt(s.length() - 1)));
    }

    /**
     * checks if sin, cos, tan, log, or pi can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if sin, cos, tan, log, or pi can be placed, false otherwise
     */
    private boolean isAllowedSinCosTanLogPiPlacement(String s) {
        try {
            return "+-*/%^(".contains(String.valueOf(s.charAt(s.length() - 1)));
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * checks if a number can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if a number can be placed, false otherwise
     */
    private boolean isAllowedNumberPlacement(String s) {
        return s.isEmpty() || !"!)".contains(String.valueOf(s.charAt(s.length() - 1)));
    }

    /**
     * checks if a factorial can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if a factorial can be placed, false otherwise
     */
    private boolean isAllowedFactorialPlacement(String s) {
        return !s.isEmpty() && "1234567890".contains(String.valueOf(s.charAt(s.length() - 1)));
    }

    /**
     * checks if a root can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if a root can be placed, false otherwise
     */
    private boolean isAllowedRootPlacement(String s) {
        try {
            return "1234567890+-*/%^π".contains(String.valueOf(s.charAt(s.length() - 1)));
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * checks if an open bracket can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if an open bracket can be placed, false otherwise
     */
    private boolean isAllowedOpenBracketPlacement(String s) {
        return s.isEmpty() || !")!".contains(String.valueOf(s.charAt(s.length() - 1)));
    }

    /**
     * checks if a close bracket can be placed at the end of the given string
     *
     * @param s the string to check
     * @return true if a close bracket can be placed, false otherwise
     */
    private boolean isAllowedCloseBracketPlacement(String s) {
        return (countInstances(s, ')') < countInstances(s, '('));
    }

    /**
     * performs the delete operation in the calc input field
     */
    private void doDELInCal() {
        if (!Main.calInput.isEmpty()) {
            if (Main.calInput.length() >= 4 && Main.calInput.charAt(Main.calInput.length() - 1) == '(' && (Main.calInput.charAt(Main.calInput.length() - 2) == 'n' || Main.calInput.charAt(Main.calInput.length() - 2) == 's')) {
                Main.calInput = Main.calInput.substring(0, Main.calInput.length() - 4);
            } else if (Main.calInput.length() >= 2 && Main.calInput.charAt(Main.calInput.length() - 1) == '(' && Main.calInput.charAt(Main.calInput.length() - 2) == '√') {
                Main.calInput = Main.calInput.substring(0, Main.calInput.length() - 2);
            } else {
                Main.calInput = Main.calInput.substring(0, Main.calInput.length() - 1);
            }
        }
        inputFieldForCalculator.setText(Main.calInput);
    }

    //------------------------------------------------------------------------------------------------------------------

    /**
     * counts instances
     *
     * @param s      the string
     * @param search the character to search for
     * @return the number of instances
     */
    private int countInstances(String s, char search) {
        return (int) IntStream.range(0, s.length()).filter(i -> s.charAt(i) == search).count();
    }

    /**
     * checks if a string contains any of the specified strings
     *
     * @param s the string
     * @param a the array of strings to search for
     * @return true if the string array contains any of the specified strings, false otherwise
     */
    private boolean contains(String s, String... a) {
        return Arrays.asList(a).contains(s);
    }

}


