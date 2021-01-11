package pt.upskills.projeto.gui;

import pt.upskills.projeto.rogue.utils.Position;

public class FireInv implements ImageTile{
    private Position position;

    public FireInv(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
