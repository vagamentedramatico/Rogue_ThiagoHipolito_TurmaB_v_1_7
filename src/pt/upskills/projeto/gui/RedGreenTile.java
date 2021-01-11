package pt.upskills.projeto.gui;

import pt.upskills.projeto.rogue.utils.Position;

public class RedGreenTile implements ImageTile {
    private Position position;

    public RedGreenTile(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "RedGreen";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
