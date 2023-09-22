import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGame {
    private Random random;
    private int secretNumber;
    private int maxRange = 100;
    private int maxAttempts = 10;
    private int startingPoints = 100;
    private int pointsPerAttempt = 10;
    private int playerPoints;
    private int attempts;

    private JFrame frame;
    private JPanel panel;
    private JLabel titleLabel, instructionLabel, resultLabel, pointsLabel;
    private JTextField guessField;
    private JButton guessButton;

    public NumberGuessingGame() {
        random = new Random();
        secretNumber = random.nextInt(maxRange) + 1;
        playerPoints = startingPoints;

        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        titleLabel = new JLabel("Welcome to the Number Guessing Game!");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        instructionLabel = new JLabel("Guess a number between 1 and " + maxRange + ":");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        guessField = new JTextField();
        guessField.setHorizontalAlignment(SwingConstants.CENTER);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        pointsLabel = new JLabel("Points: " + playerPoints);
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        resultLabel = new JLabel("");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel);
        panel.add(instructionLabel);
        panel.add(guessField);
        panel.add(guessButton);
        panel.add(pointsLabel);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(resultLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void checkGuess() {
        try {
            int playerGuess = Integer.parseInt(guessField.getText());
            attempts++;

            if (playerGuess == secretNumber) {
                resultLabel.setText("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                playerPoints -= (maxAttempts - attempts) * pointsPerAttempt;
                pointsLabel.setText("Points: " + playerPoints);
                guessButton.setEnabled(false);
            } else if (playerGuess < secretNumber) {
                resultLabel.setText("Too low. Try again.");
                playerPoints -= pointsPerAttempt; // Deduct points for each attempt
                pointsLabel.setText("Points: " + playerPoints);
            } else {
                resultLabel.setText("Too high. Try again.");
                playerPoints -= pointsPerAttempt; // Deduct points for each attempt
                pointsLabel.setText("Points: " + playerPoints);
            }

            if (attempts >= maxAttempts) {
                resultLabel.setText("Sorry, you've reached the maximum number of attempts. The secret number was " + secretNumber + ".");
                guessButton.setEnabled(false);
            }

            guessField.setText("");
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}
