package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.characters.Hero;
import pt.upskills.projeto.rogue.utils.Position;

public class Door implements ImageTile {

    private Position position;
    private boolean isOpen;

    public Door(Position position) { this.position = position; }

    public boolean isOpen() {
        for(ImageTile tile: Engine.tiles) {
            if(tile instanceof Hero) {
                if(tile.getPosition().equals(position)) {// && (Item item: hero.getInventory()) {

                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getName() {
        if (isOpen()){
        return "DoorOpen";}
        else {
            return "DoorClosed";
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }
}

