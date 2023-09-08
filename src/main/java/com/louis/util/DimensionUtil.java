package com.louis.util;


import java.awt.*;

/**
 * @date : 2021/9/24
 */
public class DimensionUtil {

    public static ScreenInfo getScreenInfo() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new ScreenInfo(screenSize.getWidth(), screenSize.getHeight());
    }


   /* public static void putStageToScreenCenter(Stage stage) {
        ScreenInfo screenInfo = getScreenInfo();
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();
        int x = (int) ((screenInfo.getWidth() - stageWidth) / 2);
        int y = (int) ((screenInfo.getHeight() - stageHeight) / 2);
        stage.setX(x);
        stage.setY(y);
    }*/

    public static class ScreenInfo {
        private double width;
        private double height;

        public ScreenInfo(double width, double height) {
            this.width = width;
            this.height = height;
        }

        public double getWidth() {
            return width;
        }

        public double getHeight() {
            return height;
        }
    }
}
