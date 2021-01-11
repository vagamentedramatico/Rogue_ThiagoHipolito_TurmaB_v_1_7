package pt.upskills.projeto.gui;

import pt.upskills.projeto.rogue.utils.Position;

public class GreenTile implements ImageTile {
    private Position position;

    public GreenTile(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Green";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
