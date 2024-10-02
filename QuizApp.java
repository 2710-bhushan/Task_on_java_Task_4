import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuizApp extends JFrame {
    private String[][] questions = {
        {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "1"},
        {"What is 2 + 2?", "3", "4", "5", "6", "2"},
        {"What is the largest planet?", "Earth", "Mars", "Jupiter", "Saturn", "3"}
    };
    
    private int currentQuestion = 0;
    private int score = 0;
    private int timeLeft = 10; // Time per question in seconds
    private Timer timer;

    private JLabel questionLabel;
    private JRadioButton[] options = new JRadioButton[4];
    private ButtonGroup group;
    private JLabel timerLabel;
    private JButton submitButton;
    private JLabel resultLabel;

    public QuizApp() {
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel("Question will appear here");
        add(questionLabel);

        group = new ButtonGroup();
        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            add(options[i]);
        }

        timerLabel = new JLabel("Time left: " + timeLeft);
        add(timerLabel);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitAction());
        add(submitButton);

        resultLabel = new JLabel("");
        add(resultLabel);

        displayQuestion();

        timer = new Timer(1000, new TimerAction());
        timer.start();
    }

    private void displayQuestion() {
        if (currentQuestion < questions.length) {
            String[] currentQ = questions[currentQuestion];
            questionLabel.setText(currentQ[0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(currentQ[i + 1]);
                options[i].setSelected(false);
            }
            timeLeft = 10;
            timerLabel.setText("Time left: " + timeLeft);
        } else {
            showResults();
        }
    }

    private void showResults() {
        timer.stop();
        StringBuilder resultSummary = new StringBuilder("Quiz over! Your score: " + score + "\n\n");
        for (int i = 0; i < questions.length; i++) {
            String[] q = questions[i];
            resultSummary.append("Q").append(i + 1).append(": ").append(q[0]).append("\n");
            resultSummary.append("Correct answer: ").append(q[Integer.parseInt(q[5])]).append("\n\n");
        }
        JOptionPane.showMessageDialog(this, resultSummary.toString());
        System.exit(0);
    }

    private class SubmitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] currentQ = questions[currentQuestion];
            int correctAnswer = Integer.parseInt(currentQ[5]);

            for (int i = 0; i < options.length; i++) {
                if (options[i].isSelected() && (i + 1) == correctAnswer) {
                    score++;
                }
            }
            currentQuestion++;
            displayQuestion();
        }
    }

    private class TimerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft);
            if (timeLeft == 0) {
                currentQuestion++;
                displayQuestion();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp quiz = new QuizApp();
            quiz.setVisible(true);
        });
    }
}
