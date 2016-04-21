package prog2_a3.fatsquirrel.core;

import prog2_a3.interfaces.*;
import java.util.Hashtable;
import java.util.Random;



public class FlattenedBoard implements BoardView, EntityContext {

 
    private final Entity[][] flattenedBoard;
    XY size;
    EntitySet entSet;
    Hashtable<Integer, XY> vectorList;
    
    public FlattenedBoard(Entity[][] flattenedBoard, XY size, EntitySet entSet){
        this.vectorList = new Hashtable<>();
        this.flattenedBoard = flattenedBoard;
        this.size = size;
        this.entSet = entSet;
    }
    
    public Entity[][] getBoard(){
        return flattenedBoard;
    }
    
    @Override
    public String toString(){
    	String output = "";
    	
        for (int row = 0; row < flattenedBoard.length; row++) {
            for (int column = 0; column < flattenedBoard[row].length; column++) {
                output += flattenedBoard[row][column] + "\n";
            }
        }
            return output;
    }
    
    public Entity getEntity(int x, int y){
        return this.flattenedBoard[x][y];
    }
    
    @Override
    public String getEntityType(int x, int y){
        return flattenedBoard[x][y].getName();
    }
    @Override
    public String getEntityType(XY loc){
        return flattenedBoard[loc.getX()][loc.getY()].getName();
    }
    
    @Override
    public XY getSize(){  
        return size;
    }
    
    @Override
    public void kill(Entity entity){
        entSet.delete(entity.getId());
    }
    @Override
    public void killAndReplace(Entity entity){
        entSet.delete(entity.getId());
        entSet.add(entity.getName(),(int)(Math.random()*(size.getX()-1)),(int)(Math.random()*(size.getY()-1)));
    }
    
    @Override
    public void spawnChild(MasterSquirrel parent, XY direction, int energy){
        parent.createDescendant(entSet.getLatestId(), energy, parent.getLocation().getX() + direction.getX(), parent.getLocation().getY() + direction.getY());
    }
    
    @Override
    public PlayerEntity nearestPlayerEntity(XY position){
        Entity[] entArray = entSet.getEntityArray();
        int minDistance = 30;
        int currentDistance;
        String supName;
        String supName2;
        int k;
        PlayerEntity entityReturn=null;
        for(int i=0; i < entArray.length;i++){
            //Das Array aller Entitys durchgehen und für PlayerEntitys die Distanz zum Objekt mit aktuellem Minimum vergleichen
            if (entSet.isInstance(entArray[i], PlayerEntity.class)){
                //Summe der Beträge, der X und Y Differenzen @_@
                currentDistance = (Math.abs(position.getX() - entArray[i].getLocation().getX()) + Math.abs(position.getY() - entArray[i].getLocation().getY()));
                if (currentDistance < minDistance){
                    minDistance = currentDistance;
                    entityReturn = (PlayerEntity) entArray[i];
                }
            }
        }
        return entityReturn;
    }
    
    @Override
    public void tryMove(MiniSquirrel miniSquirrel, XY moveDirection){
        Entity nextField = getEntity(miniSquirrel.getLocation().getX() + moveDirection.getX(), miniSquirrel.getLocation().getY() + moveDirection.getY());
        //Das Feld betrachten, in das das Squirrel Laufen möchte und falls dort keine Wall steht, kann es sich bewegen.
        if(miniSquirrel.getTimeout()==0){
            if(nextField !=null ){
                if(!"Wall".equals(nextField.getName())){
                    vectorList.put(nextField.getId(), moveDirection);
                    miniSquirrel.updateEnergy(-1);
                }
                else if("Wall".equals(nextField.getName())){
                    miniSquirrel.setTimeout(3);
                    miniSquirrel.updateEnergy(-1);
                }
            }
        }
        else if(miniSquirrel.getTimeout()>0)
            miniSquirrel.setTimeout(miniSquirrel.getTimeout()-1);
    }
    @Override
    public void tryMove(GoodBeast goodBeast, XY moveDirection){
        Entity nextField = getEntity(goodBeast.getLocation().getX() + moveDirection.getX(), goodBeast.getLocation().getY() + moveDirection.getY());
        //Das Feld betrachten, in das das Beast Laufen möchte und falls dort keine Wall steht, kann es sich bewegen.
        if(goodBeast.getTimeout()==0){
            if(nextField !=null ){
                if((!"Wall".equals(nextField.getName()))){
                    vectorList.put(goodBeast.getId(), moveDirection);
                    goodBeast.setTimeout(4);
                }
                else if("Wall".equals(nextField.getName())){
                    Random r = new Random();
                    tryMove(goodBeast, new XY(new int[]{r.nextInt(3)-1,r.nextInt(3)-1}));
                }
            }
            else{
                vectorList.put(goodBeast.getId(), moveDirection);
                goodBeast.setTimeout(4);
            }
        }
            else
                goodBeast.setTimeout(goodBeast.getTimeout()-1);

    }
    @Override
    public void tryMove(BadBeast badBeast, XY moveDirection){
        Entity nextField = getEntity(badBeast.getLocation().getX() + moveDirection.getX(), badBeast.getLocation().getY() + moveDirection.getY());
        //Das Feld betrachten, in das das badBeast Laufen möchte und falls dort keine Wall steht, kann es sich bewegen.
        if(badBeast.getTimeout()==0){
            if(nextField !=null ){
                if((!"Wall".equals(nextField.getName()))){
                    vectorList.put(badBeast.getId(), moveDirection);
                    badBeast.setTimeout(4);
                }
                else if("Wall".equals(nextField.getName())){
                    Random r = new Random();
                    tryMove(badBeast, new XY(new int[]{r.nextInt(3)-1,r.nextInt(3)-1}));
                }
            }
            else{
                vectorList.put(badBeast.getId(), moveDirection);
                badBeast.setTimeout(4);
            }
        }
            else
                badBeast.setTimeout(badBeast.getTimeout()-1);
    }
    @Override
    public void tryMove(MasterSquirrel masterBot, XY moveDirection){
        Entity nextField = getEntity(masterBot.getLocation().getX() + moveDirection.getX(), masterBot.getLocation().getY() + moveDirection.getY());
        //Das Feld betrachten, in das das Squirrel Laufen möchte und falls dort keine Wall steht, kann es sich bewegen.
        if(masterBot.getTimeout()==0){
            if(nextField !=null ){
                if("Wall".equals(nextField.getName())){
                    masterBot.updateEnergy(nextField.getEnergy());
                    masterBot.setTimeout(3);
                }
                else if(!"Wall".equals(nextField.getName()))
                    vectorList.put(masterBot.getId(),moveDirection);
                /*else if(entSet.isInstance(nextField, Plant.class)){
                    masterBot.updateEnergy(nextField.getEnergy());
                    killAndReplace(nextField);
                    vectorList.put(masterBot.getId(), moveDirection);
                }
                else if("GoodBeast".equals(nextField.getName())){
                    masterBot.updateEnergy(nextField.getEnergy());
                    killAndReplace(nextField);
                    vectorList.put(masterBot.getId(), moveDirection);
                    }
                else if("BadBeast".equals(nextField.getName())){
                    masterBot.updateEnergy(nextField.getEnergy());
                    if(nextField.collCount<7)
                        nextField.collCount++;
                    else
                        killAndReplace(nextField);
                    vectorList.put(masterBot.getId(), moveDirection);
                }
                else if("MiniSquirrel".equals(nextField.getName())){
                    if(masterBot.checkDescendant((MiniSquirrel)nextField)){
                        masterBot.updateEnergy(nextField.getEnergy());
                    }
                    else
                        masterBot.updateEnergy(150);
                    kill(nextField);
                    vectorList.put(masterBot.getId(), moveDirection);
                }*/
            }
            else
                vectorList.put(masterBot.getId(), moveDirection);
        }
        else if(masterBot.getTimeout()>0)
            masterBot.setTimeout(masterBot.getTimeout()-1);
    }
    
    @Override
    public Hashtable<Integer,XY> getVectors(){
        return vectorList;
    }
}
