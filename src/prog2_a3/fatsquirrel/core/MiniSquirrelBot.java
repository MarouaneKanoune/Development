package prog2_a3.fatsquirrel.core;

import java.util.HashMap;
import prog2_a3.fatsquirrel.botapi.BotControllerMiniImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import prog2_a3.fatsquirrel.botapi.*;
import prog2_a3.interfaces.*;


public class MiniSquirrelBot extends MiniSquirrel{

    //private int energy;
    public MiniSquirrelBot(int id, int energy, int x, int y, int patronId) {
        super(id, energy, x, y, patronId);
        //this.energy = energy;
    }


    @Override
    public void nextStep(EntityContext entCon){
        ControllerContextImpl conConImp = new ControllerContextImpl(entCon);
        BotControllerMiniImpl botCon = new BotControllerMiniImpl();
        botCon.nextStep(conConImp);
    }

    class ControllerContextImpl implements ControllerContext{

        EntityContext entCon;

        public ControllerContextImpl(EntityContext entCon){
            this.entCon = entCon;
        }

        @Override
        public XY getViewLowerLeft() {
            XY currentLocation = MiniSquirrelBot.this.getLocation();
            int currentX = currentLocation.getX();
            int currentY = currentLocation.getY();
            XY fieldSize = entCon.getSize();
            XY upperRight = currentLocation;
            for(int i = 1;i<=10;i++){
                if(currentX-i>=0)
                    upperRight = new XY(new int[]{upperRight.getX()-1,upperRight.getY()});
                if(currentY+i <= fieldSize.getY())
                    upperRight = new XY(new int[]{upperRight.getX(),upperRight.getY()-1});
            }
            return upperRight;
        }
        
        @Override
        public XY getViewUpperRight() {
            XY currentLocation = MiniSquirrelBot.this.getLocation();
            int currentX = currentLocation.getX();
            int currentY = currentLocation.getY();
            XY fieldSize = entCon.getSize();
            XY upperRight = currentLocation;
            for(int i = 1;i<=10;i++){
                if(currentX+i<=fieldSize.getX())
                    upperRight = new XY(new int[]{upperRight.getX()+1,upperRight.getY()});
                if(currentY-i >= 0)
                    upperRight = new XY(new int[]{upperRight.getX(),upperRight.getY()-1});
            }
            return upperRight;
        }
        
        @Override
        public EntityType getEntityAt(XY xy) {
            if(entCon.getEntityType(xy)!=null){
            // Name der Entity auf dem gewünschten Feld abfragen
            String entTypeString = entCon.getEntityType(xy).toUpperCase();
            // Entity Type mit dem Namen zurückgeben
            return EntityType.valueOf(entTypeString);
            }
            return null;
        }

        @Override
        public void move(XY direction) {
                entCon.tryMove(MiniSquirrelBot.this,direction);
        }

        @Override
        public void spawnMiniBot(XY direction, int energy) {
            // fehler logging
        }

        @Override
        public int getEnergy() {
            return MiniSquirrelBot.this.getEnergy();
        }

    }
}