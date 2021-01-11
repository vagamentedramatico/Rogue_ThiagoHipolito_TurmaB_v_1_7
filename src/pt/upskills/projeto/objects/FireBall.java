package pt.upskills.projeto.objects;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.FireTile;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.characters.Enemy;
import pt.upskills.projeto.rogue.utils.Position;

public class FireBall implements FireTile {
    private Position position;

    public FireBall(Position position) {
        this.position = position;
    }

    @Override
    public boolean validateImpact() {
        // return false quando tocar em algum objeto
        for(ImageTile tile: Engine.tiles) {
            if(tile instanceof Wall) {
                if(tile.getPosition().equals(position)) {
                    Engine.tiles.remove(this);
                    return false;
                }
            }
            // fireball damage based on enemy damage
            if(tile instanceof Enemy) {
                if(tile.getPosition().equals(position)) {
                    Engine.tiles.remove(this);
                    ((Enemy) tile).takeDamage(((Enemy) tile).getDmg());
                    System.out.println("Enemy " + ((Enemy) tile).getHp());
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
