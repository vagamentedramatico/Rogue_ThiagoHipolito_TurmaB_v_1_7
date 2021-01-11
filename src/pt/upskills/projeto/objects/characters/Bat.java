package pt.upskills.projeto.objects.characters;

import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.rogue.utils.Position;

import java.util.Observable;
import java.util.Observer;

public class Bat extends Flying implements ImageTile, Observer {

    public Bat(Position position) {
        super(position);
        this.hp = 2;
        this.dmg = 2;
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
        return "Bat";
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
        super.update(o, arg);
    }
}
