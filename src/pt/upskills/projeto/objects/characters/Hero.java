package pt.upskills.projeto.objects.characters;

import pt.upskills.projeto.game.Engine;
import pt.upskills.projeto.game.FireBallThread;
import pt.upskills.projeto.gui.*;
import pt.upskills.projeto.objects.*;
import pt.upskills.projeto.rogue.utils.Direction;
import pt.upskills.projeto.rogue.utils.Position;
import pt.upskills.projeto.rogue.utils.Vector2D;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Hero extends Character implements ImageTile, Observer {

    private final int maxHP = 8;

    public Hero(Position position) {
        super(position);
        this.hp = 8;
        this.dmg = 1;
    }

    @Override
    public String getName() {
        return "Hero";
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
        this.dmg = dmg;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public int getDmg() {
        return dmg;
    }

    public int getMaxHP() {
        return maxHP;
    }

    @Override
    public int takeDamage(int dmg) {
        for (ImageTile tile : Engine.tiles) {
            if (tile instanceof Enemy) {
                return hp = hp - ((Enemy) tile).getDmg();
            }
        } return hp;
    }

    List<Item> inventory = new ArrayList<>();

    public List<Item> getInventory() {
        return inventory;
    }

    List<String> hotPocket = new ArrayList<>();

    public List<String> getHotPocket() {
        return hotPocket;
    }

    /**
     * This method is called whenever the observed object is changed. This function is called when an
     * interaction with the graphic component occurs {{@link ImageMatrixGUI}}
     * @param o
     * @param arg
     */

    @Override
    public void update (Observable o, Object arg) {
        Integer keyCode = (Integer) arg;
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();

        Position posS = position.plus(Direction.DOWN.asVector());
        Position posE = position.plus(Direction.RIGHT.asVector());
        Position posW = position.plus(Direction.LEFT.asVector());
        Position posN = position.plus(Direction.UP.asVector());
        Position samePosition = position.plus(new Vector2D(0, 0));

        /**
         *  tal como está no engine, o hero é imortal :/
         */
        /*
        if(hp < 1) {
            Engine.reDead();
        }
        */

        if(hp < 1) {
            System.out.println("You died. Would you kindly close this game?");
        }

        // move

        if (keyCode == KeyEvent.VK_S) {
            if (getPosition().getY() <= 8) {
                position = posS;
            }
        }
        if (keyCode == KeyEvent.VK_W){
            if(getPosition().getY() > 0) {
                position = posN;
            }
        }
        if (keyCode == KeyEvent.VK_A){
            if(getPosition().getX() > 0) {
                position = posW;
            }
        }
        if (keyCode == KeyEvent.VK_D){
            if(getPosition().getX() <= 8) {
                position = posE;
            }
        }

        // interactions
        List<ImageTile> copyTiles = new ArrayList(Engine.tiles);
        for (ImageTile tile : copyTiles) {
            if (tile instanceof Wall) {
                if (position.equals(tile.getPosition())) {
                    position = samePosition;
                    break;
                }
            }
            if (tile instanceof Enemy) {
                if (position.equals(tile.getPosition())) {
                    position = samePosition;
                    ((Enemy) tile).takeDamage(getDmg());
                    System.out.println("Enemy " + ((Enemy) tile).getHp());
                }
                if (((Enemy) tile).getHp() < 1) {
                    gui.removeImage(tile);
                    gui.deleteObserver(((Enemy) tile));
                    Engine.tiles.remove(tile);
                    System.out.println("He's dead, Jim. Bye " + tile.getName() + ".");
                    break;
                }
            }

            if (tile instanceof Item) {
                if (position.equals(tile.getPosition())) {
                    position = ((Item) tile).position;
                    if(inventory.size() < 3) {
                        inventory.add((Item) tile);
                        gui.removeImage(tile);
                        Engine.tiles.remove(tile);
                        System.out.println("Yay " + tile.getName() + "!");
                    }
                    break;
                }

            if (keyCode == KeyEvent.VK_E) {
                if(inventory.size() > 0) {
                    Item lastItem = inventory.get(inventory.size() - 1);
                    lastItem.setPosition(position);
                    gui.addImage(lastItem);
                    Engine.tiles.add(lastItem);
                    inventory.remove(lastItem);
                    }
                }
            }
            if (tile instanceof FireTile) {
                if (position.equals(tile.getPosition())) {
                    position = tile.getPosition();
                    if(hotPocket.size() < 3) {
                        hotPocket.add(tile.getName());
                        gui.removeImage(tile);
                        Engine.tiles.remove(tile);
                        System.out.println("This " + tile.getName() + " is out of control!");
                    }
                    break;
                }
            }
            if (tile instanceof Door) {
                if(position.equals(tile.getPosition())) {
                    Engine.loadRoom();
                }
            }
            if (tile instanceof GoodMeat) {
                if(position.equals(tile.getPosition())) {
                    Engine.loadTheRealGame();
                }
            }
        }

        // fireball
        if(hotPocket.size()>0) {
            if (keyCode == KeyEvent.VK_DOWN) {
                FireBall fireBall = new FireBall(position);
                FireBallThread fireBallThread = new FireBallThread(Direction.DOWN, fireBall);
                fireBallThread.start();
                Engine.tiles.add(fireBall);
                gui.addImage(fireBall);
                hotPocket.remove("Fire");
            }
            if (keyCode == KeyEvent.VK_UP) {
                FireBall fireBall = new FireBall(position);
                FireBallThread fireBallThread = new FireBallThread(Direction.UP, fireBall);
                fireBallThread.start();
                Engine.tiles.add(fireBall);
                gui.addImage(fireBall);
                hotPocket.remove("Fire");
            }
            if (keyCode == KeyEvent.VK_LEFT) {
                FireBall fireBall = new FireBall(position);
                FireBallThread fireBallThread = new FireBallThread(Direction.LEFT, fireBall);
                fireBallThread.start();
                Engine.tiles.add(fireBall);
                gui.addImage(fireBall);
                hotPocket.remove("Fire");
            }
            if (keyCode == KeyEvent.VK_RIGHT) {
                FireBall fireBall = new FireBall(position);
                FireBallThread fireBallThread = new FireBallThread(Direction.RIGHT, fireBall);
                fireBallThread.start();
                Engine.tiles.add(fireBall);
                gui.addImage(fireBall);
                hotPocket.remove("Fire");
            }
        }
        Engine.updateStatus();
    }
}
