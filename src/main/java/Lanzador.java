import javax.swing.*;
import java.awt.*;

public class Lanzador {
    private static final int THREAD_POOL_SIZE = 4; // Aqui se define el numero de hilos

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Conjunto de Mandelbrot");
            RenderizadorMandelbrot renderer = new RenderizadorMandelbrot();
            frame.add(renderer);

            JSpinner spinner = new JSpinner(new SpinnerNumberModel(THREAD_POOL_SIZE, 1, 8, 1));
            spinner.addChangeListener(e -> {
                int numeroHilos = (int) spinner.getValue();
                renderer.setNumThreads(numeroHilos);
                renderer.pintaMandelbrot();
            });

            JPanel controlPanel = new JPanel();
            controlPanel.add(new JLabel("Numero de hilos "));
            controlPanel.add(spinner);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
