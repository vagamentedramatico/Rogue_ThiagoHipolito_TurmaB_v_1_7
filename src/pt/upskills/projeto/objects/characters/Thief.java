package pt.upskills.projeto.objects.characters;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.gui.ImageMatrixGUI;
import pt.upskills.projeto.gui.ImageTile;
import pt.upskills.projeto.objects.Item;
import pt.upskills.projeto.objects.Wall;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Thief extends Walking implements ImageTile, Observer {

    public Thief(Position position) {
        super(position);
        this.hp = 3;
        this.dmg = 0;
    }

    @Override
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }


    // ABILITIES /////////////////

    //////////////////////////////

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public String getName() {
        return "Thief";
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
        steal();
    }
}





