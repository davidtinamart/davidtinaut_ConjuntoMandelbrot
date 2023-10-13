import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RenderizadorMandelbrot extends JPanel {
        private final int ANCHO = 800;
        private final int ALTO = 600;
        private final int MAXIMO_ITERACCIONES = 1000;
        private final int TAMANO_POOL = 4;

        private BufferedImage imagen;
        private int numeroHilos = TAMANO_POOL;
        private ExecutorService executor;

        public RenderizadorMandelbrot() { // Constructor de la clase RenderizadorMandelbrot
            setPreferredSize(new Dimension(ANCHO, ALTO));
            initializeImage();
            createExecutor();
        }

        private void initializeImage() { // Crea una imagen de tipo BufferedImage con las dimensiones indicadas en el constructor de la clase BufferedImage (en este caso 800x600)
            imagen = new BufferedImage(ANCHO, ANCHO, BufferedImage.TYPE_INT_RGB);
        }

        private void createExecutor() { // Crea un pool de hilos con el numero de hilos que se le indique en el constructor de la clase ExecutorService (en este caso 4)
            executor = Executors.newFixedThreadPool(numeroHilos);
        }

        public void setNumThreads(int numHilos) { // Cambia el numero de hilos del pool de hilos creado en el metodo createExecutor() y vuelve a crear el pool de hilos con el nuevo numero de hilos
            this.numeroHilos = numHilos;
            executor.shutdown();
            createExecutor();
            repaint();
        }

        public void pintaMandelbrot() { // Crea un hilo por cada hilo del pool de hilos creado en el metodo createExecutor() y le pasa como parametro el
            // numero de hilo, el numero de hilo + 1, la imagen, el ancho, el alto y el numero maximo de iteracciones que se le pasa como parametro en el constructor de la clase TrabajadorMandelbrot (en este caso 1000)
            for (int i = 0; i < numeroHilos; i++) {
                int startY = i * (HEIGHT / numeroHilos);
                int endY = (i + 1) * (HEIGHT / numeroHilos);
                executor.execute(new TrabajadorMandelbrot(startY, endY, imagen, WIDTH, HEIGHT, MAXIMO_ITERACCIONES));
            }
        }

        @Override
        protected void paintComponent(Graphics g) { //Pertenece a la clase JPanel heredada
            super.paintComponent(g);
            g.drawImage(imagen, 0, 0, this);
        }


}
