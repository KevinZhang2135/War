import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        // creating instance of JFrame
        JFrame window = new JFrame("Platformer");

        // closing the window will close app
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // prevents the screen from being resized

        Screen screen = new Screen();
        window.add(screen); // adds component to window

        window.pack(); // packs components into the window with the components' preferred sizes
        window.setVisible(true); // displays frame on screen

        screen.startGameThread();

    }
}