package prog2_a3;

import java.util.Scanner;

public interface UI {

	default void move(){
        Scanner input = new Scanner(System.in);
        String in;
        in = input.next();
		
	}
}
