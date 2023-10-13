import java.awt.*;
import java.awt.image.BufferedImage;

    public class TrabajadorMandelbrot implements Runnable {

        private final BufferedImage imagen;
        private final int ANCHO;
        private final int ALTO;
        private final int MAX_ITER;
        private final int startY;
        private final int endY;

        public TrabajadorMandelbrot(int startY, int endY, BufferedImage imagen, int ANCHURA, int ALTURA, int MAX_ITER) {
            this.startY = startY;
            this.endY = endY;
            this.imagen = imagen;
            this.ANCHO = ANCHURA;
            this.ALTO = ALTURA;
            this.MAX_ITER = MAX_ITER;
        }


        @Override
        public void run() { //Pertenece a la interfaz Runnable

            for (int y = startY; y < endY; y++) {
                for (int x = 0; x < ANCHO; x++) {
                    double zx = 0;
                    double zy = 0;
                    double cx = (x - ANCHO / 2.0) * 4.0 / ANCHO;
                    double cy = (y - ALTO / 2.0) * 4.0 / ALTO;

                    int iteracciones = 0; //Contador de iteracciones para cada punto del plano complejo

                    while (zx * zx + zy * zy < 4.0 && iteracciones < MAX_ITER) {
                        double newZx = zx * zx - zy * zy + cx;
                        double newZy = 2.0 * zx * zy + cy;
                        zx = newZx;
                        zy = newZy;
                        iteracciones++;
                    }
                    int color = (iteracciones == MAX_ITER) ? 0x000000 : Color.HSBtoRGB((float) iteracciones / MAX_ITER, 1, 1);

                    synchronized (imagen) {
                        imagen.setRGB(x, y, color);
                    }
                }
            }
        }
    }
