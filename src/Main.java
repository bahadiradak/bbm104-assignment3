import java.io.IOException;

import org.json.simple.parser.ParseException;
/**
 * Main
 * @author Bahadir
 */
public class Main {
	/**
	 * @param args the path way of command.txt .
	 * @throws IOException if file not open .
	 * @throws ParseException Construct a new ParseException with an external cause .
	 * @throws java.text.ParseException is checked exception .
	 */
	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
		Game g = new Game();
		g.game(args);
		g.writer();
	}
}
