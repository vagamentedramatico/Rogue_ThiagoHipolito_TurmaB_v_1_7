package pt.upskills.projeto.objects.characters;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observer;

public abstract class Character implements ImageTile, Observer {
    public Position position;
    public int hp;
    public int dmg;

    public Character(Position position) {
        this.position = position;
        }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDmg() {
        return dmg;
    }

    public abstract int takeDamage(int dmg);
}
