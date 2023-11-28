import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplashScreen {
    private JPanel splashScreen;
    private JLabel txtTitle;
    private JLabel txtHeader;
    private JProgressBar progressBar1;
    private JLabel imageLabel;

    private final Timer timer;
    private int progressValue = 0;
    private JFrame splashFrame;
    private MainScreen mainScreen;

    public SplashScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        // Initialize components
        initComponents();

        // Create a timer to simulate loading progress
        timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progressBar1.setValue(progressValue);
                if (progressValue == 100) {
                    txtHeader.setText("Loading Complete!");
                    timer.stop();
                    showMainScreen(); // Call a method to show the Main Screen
                    splashFrame.dispose(); // Close the splash screen window
                }
                progressValue += 1;
            }
        });

        // Start the timer
        timer.start();
    }

    private void initComponents() {
        // Set up layout for the splash screen panel
        splashScreen.setLayout(new BorderLayout());

        // Set properties for the title label
        txtTitle = new JLabel("Pemantau Budget");
        txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
        splashScreen.add(txtTitle, BorderLayout.NORTH);

        // Add image to the center of the layout
        ImageIcon icon = new ImageIcon("src/expenses.png");
        imageLabel = new JLabel(icon);
        splashScreen.add(imageLabel, BorderLayout.CENTER);

        // Set properties for the header label
        txtHeader = new JLabel("Loading...");
        txtHeader.setHorizontalAlignment(SwingConstants.CENTER);
        splashScreen.add(txtHeader, BorderLayout.SOUTH);

        // Set properties for the progress bar
        progressBar1 = new JProgressBar();
        progressBar1.setStringPainted(true); // Show percentage on the progress bar
        splashScreen.add(progressBar1, BorderLayout.SOUTH);
    }

    private void showMainScreen() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainScreen.showMainScreen(); // Show the Main Screen
            }
        });
    }

    public static void main(String[] args) {
        // Create and show the SplashScreen
        MainScreen mainScreen = new MainScreen();
        SplashScreen splashScreen = new SplashScreen(mainScreen);

        JFrame frame = new JFrame("Splash Screen");
        frame.setContentPane(splashScreen.splashScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the splash screen frame size
        frame.setSize(1200, 800); // Adjust the size as needed

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set the frame location to the center of the screen
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        frame.setVisible(true);

        // Store the splash screen frame reference for later disposal
        splashScreen.splashFrame = frame;
    }
}
