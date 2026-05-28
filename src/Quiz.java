import java.util.ArrayList;
import java.util.List;

//Class: Quiz
//Creator: Bong Ming Meng (103541)
//Tester:
//Manages the quiz flow, questions, and scoring for the Quality Education SDG application.
public class Quiz implements Questionable{
    private List<Questionable> questions; // List of quiz questions
    private int currentQ; //Tracker for current question index
    private String type; //Type of quiz
    private int score; // User's current score
    private int totalPoints; // Total possible points for the quiz
    private boolean isFinished; // Flag to indicate if the quiz is finished

    // Constructor for Quiz class
    public Quiz(String type) {
        this.type = type;
        this.questions = new ArrayList<>();
        this.currentQ = 0;
        this.score = 0;
        this.totalPoints = 0;
        this.isFinished = false;
        loadQuestion(); // Load questions based on quiz type
    }

    public String getQuestion(){
        if (currentQ < questions.size()) {
            return questions.get(currentQ).getQuestionText();
        } 
        return "No more questions.";
    }

    public boolean checkQuestion(String a){
        if (currentQ < questions.size()) {
            boolean isCorrect = questions.get(currentQ).checkAnswer(a);
            if (isCorrect) {
                score += questions.get(currentQ).getPoints(); // Increment score by question points
            }
            currentQ++; // Move to next question
            if (currentQ >= questions.size()) {
                isFinished = true; // Mark quiz as finished if no more questions
            }
            return isCorrect;
        } 
        return false; // No more questions to check
    }

    public String getType() {
        return type;
    }

    public void loadQuestion(){
        questions.clear();

        if (type.equals("MCQ")){
            MCQuestion mcq = new MCQuestion();
            questions.addAll(mcq.getQuestions());
        }else if (type.equals("TF")){
            TFQuestion tf = new TFQuestion();
            questions.addAll(tf.getQuestions());
        }

        totalPoints = questions.size() * 10;
    }

    //Return motivational message based on score percentage
    public String getMotivationalMessage(){
        if (totalPoints == 0) {
            return "No questions loaded.";
        }
        double percentage = ((double) score / totalPoints) * 100;
        if (percentage >= 80) {
            return "Excellent work! You're making a great impact!";
        } else if (percentage >= 60) {
            return "That's good!";
        } else if (percentage >= 40) {
            return "Good try!";
        } else if (percentage >= 20) {
            return "You can do better!";
        } else {
            return "Don't give up!";
        }
    }

    // Resets the quiz to start again
    public void resetQuiz() {
            currentQ = 0;
            score = 0;
            isFinished = false;
            loadQuestion();
        }

    @Override
    public String getQuestionText(){
        return getQuestion();
    }

    @Override
    public boolean checkAnswer(String userAnswer){
        return checkQuestion(userAnswer);
    }

    @Override
    public String getCorrectAnswer(){
        if (currentQ < questions.size()){
            return questions.get(currentQ).getCorrectAnswer();
        }
        return"";
    }

    @Override
    public String getQuestionType(){
        return type;
    }

    @Override
    public int getPoints(){
        if (currentQ < questions.size()){
            return questions.get(currentQ).getPoints();
        }
        return 0;
    }

    // for GUI
    public int getScore() { return score; }
    public int getTotalPoints() { return totalPoints; }
    public int getCurrentQ() { return currentQ; }
    public int getTotalQuestions() { return questions.size(); }
    public boolean isFinished() { return isFinished; }
}
