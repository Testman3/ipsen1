package Controller;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.ScrollEvent;

public class MenuController {
    final double SCALE_DELTA = 1.15;
    double scaleFactor;

    public double zoomVeld(Group group){
        new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();

                if (event.getDeltaY() == 0) {
                    return;
                }

                scaleFactor =
                        (event.getDeltaY() > 0)
                                ? SCALE_DELTA
                                : 1 / SCALE_DELTA;
            }
        };

        double scale = group.getScaleX() * scaleFactor;
        return scale;

    }


    public void test(Group group){
        double scale = group.getScaleX() * 1.1;
    }






}
