import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Display extends Application {
    static int ResolutionX = 700;
    static int ResolutionY = 500;
//    static int ResolutionX = 1800;
//    static int ResolutionY = 900;
    static int pixelWidth = 2;
    static int DepthOfView = 100;
    static double speed = 0.1;
    static double FOV = Math.PI / 18 * 10;
    static double FOVofPixel = FOV / ResolutionX;

    static double x = 0.5;
    static double y = 0.5;
    static double Direction = Math.PI / 2 - FOV / 2;

    Thread loop;

    static GraphicsContext gc;

    static PixelWriter writer;
    static ImageView viewOut;
    static WritableImage output;

    static Label Fps;

    public void start(Stage mainStage) {
        //init
        StackPane rootNode = new StackPane();
        rootNode.setAlignment(Pos.CENTER);
        Scene mainScene = new Scene(rootNode, ResolutionX + 100, ResolutionY + 100);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Engine");

//		Label ll = new Label();

        Label coordinates = new Label("x:" + x + "y:" + y);
        coordinates.setAlignment(Pos.BASELINE_CENTER);
        coordinates.setTextFill(Color.BLACK);
        coordinates.setTranslateY(-240);
        Label showDir = new Label("DIR:" + Direction);
        showDir.setAlignment(Pos.CENTER);
        showDir.setTranslateY(240);
        showDir.setTextFill(Color.RED);
        Fps = new Label("FPS:" + 0);
        Fps.setAlignment(Pos.TOP_LEFT);
        Fps.setTranslateY(-240);
        Fps.setTranslateX(-320);
        Fps.setTextFill(Color.BLACK);

        //Canvas
        Canvas outp = new Canvas(ResolutionX, ResolutionY);
        gc = outp.getGraphicsContext2D();
        //IMAGE OUTput
        output = new WritableImage(ResolutionX, ResolutionY);
        writer = output.getPixelWriter();
        viewOut = new ImageView(output);

        //Controls
        mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent key) {
                String KEY = key.getCode().toString();
                point position;
                System.out.println(KEY);
                switch (KEY) {
                    case ("W"):
                        position = main.moveToPoint(x, y, Direction + FOV / 2, speed);
                        x = position.x;
                        y = position.y;
                        coordinates.setText("x:" + x + "y:" + y);
                        System.out.println(x);
                        break;
                    case ("D"):
                        position = main.moveToPoint(x, y, Direction + main.half + FOV / 2, speed);
                        x = position.x;
                        y = position.y;
                        coordinates.setText("x:" + x + "y:" + y);
                        break;
                    case ("A"):
                        position = main.moveToPoint(x, y, Direction - main.half + FOV / 2, speed);
                        x = position.x;
                        y = position.y;
                        coordinates.setText("x:" + x + "y:" + y);
                        break;
                    case ("S"):
                        position = main.moveToPoint(x, y, Direction + Math.PI + FOV / 2, speed);
                        x = position.x;
                        y = position.y;
                        coordinates.setText("x:" + x + "y:" + y);
                        break;
                    case ("RIGHT"):
                        Direction = Direction + 0.1;
                        if (Direction > main.half * 3) {
                            Direction -= 2 * Math.PI;
                        }
                        showDir.setText("DIR:" + Math.toDegrees(Direction + FOV / 2));
                        break;
                    case ("LEFT"):
                        Direction = Direction - 0.1;
                        if (Direction < -main.half) {
                            Direction += 2 * Math.PI;
                        }
                        showDir.setText("DIR:" + Math.toDegrees(Direction + FOV / 2));
                        break;
                }
            }
        });

        //draw

        loop = new Thread(new GameLoop());
        loop.start();


        rootNode.getChildren().addAll(outp, viewOut, coordinates, showDir, Fps);
//		rootNode.getChildren().addAll(outp,viewOut);
        mainStage.show();


    }

    ;

    public static void drawFrameOnImage() {
        for (int pixel = 0; pixel < ResolutionX; pixel++) {
            double length = main.inspect(x, y, Direction + FOVofPixel * pixel, DepthOfView);
            int hegightOfPixel = (int) (ResolutionY / length / 2);
            int VerticalCorrect = (ResolutionY - hegightOfPixel) / 2;
            int MinVerticalCorrect = ResolutionY - (ResolutionY - hegightOfPixel) / 2;

            for (int pixelY = 0; pixelY < ResolutionY; pixelY++) {
                if (pixelY > VerticalCorrect && pixelY < MinVerticalCorrect) {
                    writer.setColor(pixel, pixelY, Color.BLUE);
                } else {
                    writer.setColor(pixel, pixelY, Color.WHITE);
                }
            }

//            Platform.runLater(() -> {
                        viewOut.setImage(output);
//                    });

        }
        GameLoop.PROCESS.unlock();
    }

    ;


    public static void drawFrame() {
        gc.setFill(Color.WHITE);
//		Display.gc.fillRect(0, 0, ResolutionX-1, ResolutionY-1);
        for (int pixel = 0; pixel < ResolutionX; pixel++) {
            double length = main.inspect(x, y, Direction + FOVofPixel * pixel, DepthOfView);
            double hegightOfPixel = ResolutionY / length / 2;
            double VerticalCorrect = (ResolutionY - hegightOfPixel) / 2;

            ////??????
            int pix = pixel;
			/*Platform.runLater(() -> 
			Display.gc.clearRect(pix, 0, pixelWidth, ResolutionY));*/
//            Platform.runLater(() -> {
                        gc.setFill(Color.WHITE);
                        Display.gc.fillRect(pix, 0, pixelWidth, ResolutionY);
//
                        gc.setFill(Color.BLACK);
                        Display.gc.fillRect(pix, VerticalCorrect, pixelWidth, hegightOfPixel);
//                    });
        }

        GameLoop.PROCESS.unlock();
    }

    public static void drawTexturedFrame() {

        WallEnterance result;
        double heightOfPixel;
        double VerticalCorrect;
        double TexturePixel;
        double lenght;
        int Argb;

        Textures texture;
        PixelReader reader;
        width:
        for (int pixel = 0; pixel < ResolutionX; pixel++) {
            result = main.inspectWithTexture(x, y, Direction + FOVofPixel * pixel, DepthOfView);
            //lenght = main.FishEye(result, FOVofPixel*pixel).length;
            texture = Textures.getTextureByID(result.texture);
            reader = texture.reader;
            lenght = result.length;
            heightOfPixel = ResolutionY / lenght / 2;
            VerticalCorrect = (ResolutionY - heightOfPixel) / 2;
            TexturePixel = texture.img.getHeight() / heightOfPixel;
            for (int i = 0; i < VerticalCorrect; i++) {
                writer.setColor(pixel, i, Color.BLUE);
            }
            height:
            for (int pixY = 0; pixY < heightOfPixel; pixY++) {
                if (pixY + VerticalCorrect < 0 || pixY + VerticalCorrect > ResolutionY) {
                    continue height;
                }

                //
                //
                try {

                    //Argb=reader.getColor((int)(result.correction*texture.img.getWidth()),(int)(TexturePixel*pixY-1);
                    Argb = reader.getArgb((int) (result.correction * texture.img.getWidth()), (int) (TexturePixel * pixY));
                    writer.setArgb(pixel, (int) (pixY + VerticalCorrect), Argb);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            for (int i2 = (int) (heightOfPixel + VerticalCorrect); i2 < ResolutionY; i2++) {
                writer.setColor(pixel, i2, Color.BLACK);
            }

            viewOut.setImage(output);
        }
        GameLoop.PROCESS.unlock();
    }


    public void stop() {
        loop.stop();
    }

    public static void setFps(int fps) {
        Fps.setText("FPS: " + fps);
    }

    public static void main(String[] args) {

        //main.map[0][0]=1;
        main.map[1][1] = 1;
        main.map[0][2] = 1;
        main.map[3][2] = 1;
        main.map[3][0] = 1;
        main.map[5][2] = 1;
        main.map[5][0] = 1;
        main.map[6][1] = 1;
        main.map[6][2] = 1;
        main.map[8][2] = 1;
        main.map[8][1] = 1;
        main.map[8][0] = 1;
        main.map[9][1] = 1;
        main.map[10][1] = 1;
        main.map[10][2] = 1;
        main.map[10][0] = 1;
        main.map[9][4] = 2;

        Display.launch(args);
        point debug;
        System.out.println(Math.toDegrees(3.1707));
        System.out.println(x + ":" + y);
        debug = main.moveToPoint(-1.07, 2.5, 3.17, 0.1);                          //main.moveToPoint(x, y, Direction + FOV/2,speed);
        System.out.println(Direction + FOV / 2);
        System.out.println(debug.x + ":" + debug.y);
        System.out.println(main.checkMap((int) debug.x, (int) debug.y));
    }
}


