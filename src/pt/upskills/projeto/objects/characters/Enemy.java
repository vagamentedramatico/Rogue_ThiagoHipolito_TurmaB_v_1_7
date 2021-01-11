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
import java.util.Observer;

public abstract class Enemy extends Character implements ImageTile, Observer {

    public Enemy(Position position) {
        super(position);
    }

    @Override
    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }

    @Override
    public void setDmg(int dmg) {
        super.setDmg(dmg);
    }

    @Override
    public int getHp() {
        return super.getHp();
    }

    @Override
    public void setHp(int hp) {
        super.setHp(hp);
    }

    @Override
    public int getDmg() {
        return super.getDmg();
    }

    @Override
    public int takeDamage(int dmg) {
        for (ImageTile tile : Engine.tiles) {
            if (tile instanceof Hero) {
                return hp = hp - ((Hero) tile).getDmg();
            }
        } return hp;
    }

    // random for idle

    private int randValue() {
        int n;
        if (Math.random() <= 0.33)
            n = -1;
        else if (Math.random() > 0.33 && Math.random() <= 0.66)
            n = 0;
        else
            n = 1;
        return n;
    }

    // INVENTORY /////////////////
    List<Item> enemyInventory = new ArrayList<>();

    public List<Item> getEnemyInventory() {
        return enemyInventory;
    }

    public void setEnemyInventory(List<Item> enemyInventory) {
        this.enemyInventory = enemyInventory;
    }
    //////////////////////////////

    public void movement(int n, Object arg) {
        Integer keyCode = (Integer) arg;

        Position posS = position.plus(Direction.DOWN.asVector());
        Position posE = position.plus(Direction.RIGHT.asVector());
        Position posW = position.plus(Direction.LEFT.asVector());
        Position posN = position.plus(Direction.UP.asVector());

        Position posNE = position.plus(new Vector2D(1, -1));
        Position posNW = position.plus(new Vector2D(-1, -1));
        Position posSE = position.plus(new Vector2D(1, 1));
        Position posSW = position.plus(new Vector2D(-1, 1));

        Position samePosition = position.plus(new Vector2D(0, 0));

        Position posRand = position.plus(new Vector2D(randValue(), randValue()));

        List<ImageTile> copyTiles = new ArrayList(Engine.tiles);
        for (ImageTile tile : copyTiles) {
            if (tile instanceof Hero) {

                Position heroPosition = tile.getPosition();

                // idle

                if ((position.getX() - heroPosition.getX()) > n ||
                        (position.getX() - heroPosition.getX()) < -n ||
                        (position.getY() - heroPosition.getY()) > n ||
                        (position.getY() - heroPosition.getY()) < -n) {
                    position = posRand;
                } else {

                    // chase

                    if (heroPosition.getX() == position.getX()) {
                        if (heroPosition.getY() < position.getY()) {
                            position = posN;
                        } else {
                            position = posS;
                        }
                    }

                    if (heroPosition.getY() == position.getY()) {
                        if (heroPosition.getX() < position.getX()) {
                            position = posW;
                        } else {
                            position = posE;
                        }
                    }
                    if (heroPosition.getX() == position.getX()) {
                        if (heroPosition.getY() < position.getY()) {
                            position = posN;
                        } else {
                            position = posS;
                        }
                    }

                    if (heroPosition.getX() < position.getX() &&
                            heroPosition.getY() > position.getY()) {
                        position = posSW;
                    }
                    if (heroPosition.getX() > position.getX() &&
                            heroPosition.getY() < position.getY()) {
                        position = posNE;
                    }
                    if (heroPosition.getX() < position.getX() &&
                            heroPosition.getY() < position.getY()) {
                        position = posNW;
                    }
                    if (heroPosition.getX() > position.getX() &&
                            heroPosition.getY() > position.getY()) {
                        position = posSE;
                    }
                }
            }
        }

        // wall

        for (ImageTile tile : copyTiles) {
            if (tile instanceof Wall) {
                if (position.equals(tile.getPosition())) {
                    position = samePosition;
                }
            }
        }

        // stop at fireball

        if (keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_LEFT ||
                keyCode == KeyEvent.VK_RIGHT ||
                keyCode == KeyEvent.VK_UP) {
            position = samePosition;
        }
    }

    public void attack () {
        Position samePosition = position.plus(new Vector2D(0, 0));
        List<ImageTile> copyTiles = new ArrayList(Engine.tiles);
        for (ImageTile tile : copyTiles) {
            if (tile instanceof Hero) {
                Position heroPosition = tile.getPosition();
                if (position.equals(heroPosition)) {
                    position = samePosition;
                    ((Hero) tile).takeDamage(getDmg());
                    System.out.println("Hero " + ((Hero) tile).getHp());
                    break;
                }
            }
        }
    }
    public void steal() {
        Position samePosition = position.plus(new Vector2D(0, 0));
        List<ImageTile> copyTiles = new ArrayList(Engine.tiles);

        for (ImageTile tile : copyTiles) {
            if (tile instanceof Hero) {

                Position heroPosition = tile.getPosition();
                Item lastItem = ((Hero) tile).inventory.get(((Hero) tile).inventory.size() - 1);

                if (position.equals(heroPosition)) {
                    position = samePosition;
                    if (((Hero) tile).inventory.size() > 0 && enemyInventory.size() < 2) {
                        enemyInventory.add(lastItem);
                        ((Hero) tile).inventory.remove(lastItem);
                    } else {
                        attack();
                    }
                } break;
            }
        }
    }
    public void drop() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Item lastItem = enemyInventory.get(enemyInventory.size() - 1);
        if (enemyInventory.size() > 0) {
            lastItem.setPosition(position);
            gui.addImage(lastItem);
            Engine.tiles.add(lastItem);
            enemyInventory.remove(lastItem);
        }
    }
}