package pt.upskills.projeto.objects.characters;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observable;
import java.util.Observer;

public class Skeleton extends Walking implements ImageTile, Observer {

    public Skeleton(Position position) {
        super(position);
        this.hp = 4;
        this.dmg = 1;
    }

    @Override
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getDmg() {
        return dmg;
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public int takeDamage(int dmg) {
        return super.takeDamage(dmg);
    }

    @Override
    public void update(Observable o, Object arg) {
        movement(4, arg);
        attack();
    }
}





