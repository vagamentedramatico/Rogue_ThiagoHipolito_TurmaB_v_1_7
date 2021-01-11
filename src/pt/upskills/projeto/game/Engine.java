package pt.upskills.projeto.game;

import pt.upskills.projeto.gui.*;
import pt.upskills.projeto.objects.*;
import pt.upskills.projeto.objects.characters.*;
import pt.upskills.projeto.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Engine {

    public static List<ImageTile> tiles = new ArrayList<>();
    public static List<ImageTile> statusTiles = new ArrayList<>();
    public static Hero hero;

    public static void init(){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }
        readMapFile(0);
        updateStatus();
        gui.newImages(tiles);
        gui.go();

        while (true){
            gui.update();
        }
    }

    public static void loadRoom() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        tiles.clear();
        gui.clearImages();
        gui.deleteObservers();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }

        /**
         *  Subestimei a fomra de passar de mapa, então acabei por criar um loop em que todas as portas davam ao level 1.
         */
        Engine.readMapFile(1);
        gui.newImages(tiles);

        /**
         *  A posição também acabou por ser escolhida "à mão" por não ter resolvido isto com tempo.
         */
        hero.setPosition(new Position(4,9));
        gui.addImage(hero);
        gui.addObserver(hero);
        for(ImageTile tile : Engine.tiles) {
            if(tile instanceof Enemy) {
                gui.addObserver((Enemy) tile);
                }
            }
        System.out.println(">>> GET THE GOOD MEAT <<<");
        }

    public static void loadTheRealGame() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        tiles.clear();
        gui.clearImages();
        gui.deleteObservers();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }
        Engine.readMapFile(2);
        gui.newImages(tiles);
        hero.setPosition(new Position(4,7));
        gui.addImage(hero);
        gui.addObserver(hero);
        for(ImageTile tile : Engine.tiles) {
            if(tile instanceof Enemy) {
                gui.addObserver((Enemy) tile);
            }
        }
    }

    /**
     *  O código abaixo era suposto ser para a morte do hero, mas, como ele insiste em ser imortal, não funcionava como
     *  deveria. Acho que poderá ter algo a ver com o Observer, mas infelizmente não consegui tratar deste problema
     *  dentro do deadline.
     */
    /*
    public static void reDead() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        tiles.clear();
        gui.clearStatus();
        gui.clearImages();
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                tiles.add(new Floor(new Position(i, j)));
            }
        }
        readMapFile(0);
        gui.newImages(tiles);
        gui.addObserver(hero);
        hero.setPosition(new Position(5,5));
        gui.addImage(hero);
    }
    */

    public static void updateStatus () {
        statusTiles.clear();
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearStatus();

        for(int i = 0; i < 10; i++) {
            statusTiles.add(new BlackTile(new Position(i, 0)));
        }

        // base red
        for(int i = 3; i < 7; i++) {
            statusTiles.add(new RedTile(new Position(i, 0)));
        }

        // health bar
        switch (hero.getHp()) {
            case 8:
                for(int i = 3; i < 7; i++)
                statusTiles.add(new GreenTile(new Position(i,0)));
                break;
            case 7:
                statusTiles.add(new RedGreenTile(new Position(6, 0)));
                for(int i = 3; i < 6; i++)
                statusTiles.add(new GreenTile(new Position(i,0)));
                break;
            case 6:
                for(int i = 3; i < 6; i++)
                statusTiles.add(new GreenTile(new Position(i,0)));
                break;
            case 5:
                statusTiles.add(new RedGreenTile(new Position(5, 0)));
                for(int i = 3; i < 5; i++)
                statusTiles.add(new GreenTile(new Position(i,0)));
                break;
            case 4:
                for(int i = 3; i < 5; i++)
                statusTiles.add(new GreenTile(new Position(i,0)));
                break;
            case 3:
                statusTiles.add(new RedGreenTile(new Position(4, 0)));
                statusTiles.add(new GreenTile(new Position(3,0)));
                break;
            case 2:
                statusTiles.add(new GreenTile(new Position(3,0)));
                break;
            case 1:
                statusTiles.add(new RedGreenTile(new Position(3, 0)));
                break;
        }

        // inventory
        if (hero.getInventory().size() > 0) {
            int i = 0;
            for (Item item: hero.getInventory()) {
                item.setPosition(new Position(i+7, 0));
                statusTiles.add(item);
                i++;
            }
        }

        // hot pocket
        if (hero.getHotPocket().contains("Fire")) {
            int n = hero.getHotPocket().size();
            if (n < 4) {
                for (int i = 0; i < n; i++) {
                    statusTiles.add(new FireBall(new Position(i, 0)));
                }
            }
        }

        gui.newStatusImages(statusTiles);
    }

    public static void readMapFile(int level) {
        try {
            Scanner fileScanner = new Scanner(new File("rooms/room" + level + ".txt"));
            int i = 0;
            while (fileScanner.hasNextLine()) {
                ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
                String nextLine = fileScanner.nextLine();
                String[] separar = nextLine.split("");
                if (separar[0].equals("#")) {
                } else {
                    for (int j = 0; j < separar.length; j++) {
                        Position pos = new Position(j, i);
                        switch (separar[j]) {
                            case "W":
                                tiles.add(new Wall(pos));
                                break;
                            case "D":
                                tiles.add(new Door(pos));
                                break;
                            case "G":
                                tiles.add(new GoodMeat(pos));
                                break;
                            case "K":
                                tiles.add(new Key(pos));
                                break;
                            case "H":
                                tiles.add(new Hammer(pos));
                                break;
                            case "F":
                                tiles.add(new FireBall(pos));
                                break;
                            case "T":
                                Thief thief = new Thief(pos);
                                tiles.add(thief);
                                gui.addObserver(thief);
                                break;
                            case "S":
                                Skeleton skeleton = new Skeleton(pos);
                                tiles.add(skeleton);
                                gui.addObserver(skeleton);
                                break;
                            case "B":
                                Bat bat = new Bat(pos);
                                tiles.add(bat);
                                gui.addObserver(bat);
                                break;
                            case "h":
                                hero = new Hero(pos);
                                tiles.add(hero);
                                gui.addObserver(hero);
                                break;
                        }
                    }
                    i++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível ler o ficheiro " + level);
        }
    }

    public static void main(String[] args){
        Engine engine = new Engine();
        engine.init();
    }
}
