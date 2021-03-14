import javafx.application.Platform;

public class GameLoop implements Runnable {
    final public static NotReentrantLock PROCESS = new NotReentrantLock();

    @Override
    public void run() {
        long time = System.currentTimeMillis() + 1000;
        int fps = 0;
        while (!Thread.interrupted()) {

            //Display.drawFrame();

            //	Display.drawFrameOnImage();
            try {

                PROCESS.lock();
                Platform.runLater(() -> {
                    Display.drawTexturedFrame();
//                    Display.drawFrameOnImage();

                });
            } catch (Exception ex) {
                System.out.println(ex);
                PROCESS.unlock();
            }

            fps++;
            if (System.currentTimeMillis() > time) {
                final int fp = fps;
                Platform.runLater(() ->
                {
                    System.out.println(fp);
                    Display.setFps(fp);
                });
                fps = 0;
                time = System.currentTimeMillis() + 1000;
            }


//            try {
//                Thread.sleep(50); //50 for 700x500
//            } catch (InterruptedException ex) {
//
//            }
        }

    }

}
