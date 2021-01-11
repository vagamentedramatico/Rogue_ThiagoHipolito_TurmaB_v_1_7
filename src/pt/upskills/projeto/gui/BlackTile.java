package pt.upskills.projeto.gui;

import pt.upskills.projeto.rogue.utils.Position;

public class BlackTile implements ImageTile {
    private Position position;

    public BlackTile(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Black";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
