package android.jjdeveloper.com.lienzo;

import android.graphics.Path;

public class Pincel {

    public int color;
    public boolean blur;
    public int strokeWidth;
    public Path path;

    public Pincel(int color, boolean blur, int strokeWidth, Path path) {
        this.color = color;
        this.blur = blur;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }
}
