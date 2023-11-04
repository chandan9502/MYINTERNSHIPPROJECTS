import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class HangmanGame extends JFrame {
    private String[] words = {"computer", "programming", "java", "hangman", "project", "game"};
    private String selectedWord;
    private StringBuilder currentWord;
    private int maxAttempts = 6;
    private int attemptsLeft;
    
    private JLabel wordLabel;
    private JTextField letterField;
    private JButton guessButton;
    private JLabel attemptsLabel;
    
    public HangmanGame() {
        super("Hangman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        wordLabel = new JLabel();
        add(wordLabel, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        letterField = new JTextField(1);
        guessButton = new JButton("Guess");
        attemptsLabel = new JLabel();
        
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });
        
        inputPanel.add(new JLabel("Enter a letter: "));
        inputPanel.add(letterField);
        inputPanel.add(guessButton);
        inputPanel.add(attemptsLabel);
        
        add(inputPanel, BorderLayout.SOUTH);
        
        initializeGame();
    }
    
    private void initializeGame() {
        selectedWord = selectRandomWord();
        currentWord = new StringBuilder(selectedWord.length());
        for (int i = 0; i < selectedWord.length(); i++) {
            currentWord.append("_");
        }
        
        attemptsLeft = maxAttempts;
        
        updateWordLabel();
        letterField.setText("");
        letterField.setEnabled(true);
        guessButton.setEnabled(true);
        
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
    }
    
    private String selectRandomWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }
    
    private void processGuess() {
        String guess = letterField.getText().toLowerCase();
        
        if (guess.length() == 1) {
            boolean found = false;
            for (int i = 0; i < selectedWord.length(); i++) {
                if (selectedWord.charAt(i) == guess.charAt(0)) {
                    currentWord.setCharAt(i, guess.charAt(0));
                    found = true;
                }
            }
            
            if (!found) {
                attemptsLeft--;
            }
            
            updateWordLabel();
            
            if (currentWord.toString().equals(selectedWord)) {
                wordLabel.setText("Congratulations! You've guessed the word: " + selectedWord);
                letterField.setEnabled(false);
                guessButton.setEnabled(false);
            }
            
            if (attemptsLeft <= 0) {
                wordLabel.setText("You've run out of attempts. The word was: " + selectedWord);
                letterField.setEnabled(false);
                guessButton.setEnabled(false);
            }
            
            attemptsLabel.setText("Attempts left: " + attemptsLeft);
        }
        
        letterField.setText("");
    }
    
    private void updateWordLabel() {
        wordLabel.setText("Current word: " + currentWord.toString());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanGame game = new HangmanGame();
            game.setVisible(true);
        });
    }
}
