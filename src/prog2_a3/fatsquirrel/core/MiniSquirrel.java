package prog2_a3.fatsquirrel.core;

public class MiniSquirrel extends PlayerEntity{
	private int patronId;

    public MiniSquirrel(int id, int energy, int x, int y, int patronId) {
        super(id, energy, x, y);
        this.patronId = patronId;
    }
        
    public int getPatronId(){
        return patronId;
    }

    @Override
    public void nextStep(){
    }
        
    @Override
    public void nextStep(XY vector){
        this.move(vector);
    }
        
}
