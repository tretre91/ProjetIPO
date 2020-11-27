package environment;

import java.awt.*;

public class SpecialCase {
    private int absc;
    private final CaseType type;
    private Color color;

    public SpecialCase(CaseType type, int absc) {
        this.absc = absc;
        this.type = type;
        switch (this.type) {
            case ice -> color = new Color(157, 225,250);
            case trap -> color = Color.red;
            case wall -> color = new Color(61, 61, 61);
            case bonus -> color = Color.orange;
        }
    }

    public CaseType getType() {
        return type;
    }

    public Color getColor() {
        return color;
    }

    public int getAbsc() {
        return absc;
    }
}
