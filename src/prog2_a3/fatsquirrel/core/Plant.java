
package prog2_a3.fatsquirrel.core;

import java.util.logging.Level;

public abstract class Plant extends Entity{
	private static final GameLogger logger = new GameLogger();
    
    public Plant(int id, int energy, int x, int y){
        super(id,energy,x,y);
        logger.log(Level.FINEST, "Objekt der Klasse Plant wurde erstellt");
    }
    
}
