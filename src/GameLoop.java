import javafx.application.Platform;

public class GameLoop implements Runnable {


    @Override
    public void run() {
        long time = System.currentTimeMillis() + 1000;
        int fps = 0;
        while (!Thread.interrupted()) {

            //Display.drawFrame();

            //	Display.drawFrameOnImage();
            try {
                Platform.runLater(() -> {
                    Display.drawTexturedFrame();
                });

            } catch (Exception ex) {
                System.out.println(ex);
            }
            fps++;
            if (System.currentTimeMillis() > time) {
                final int fp = fps;
                Platform.runLater(() ->
                {
                    Display.setFps(fp);
                });
                fps = 0;
                time = System.currentTimeMillis() + 1000;
            }

            try {
                Thread.sleep(60); //50 for 700x500
            } catch (InterruptedException ex) {

            }
        }

    }

}
