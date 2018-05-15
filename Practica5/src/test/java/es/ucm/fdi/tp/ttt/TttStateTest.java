package es.ucm.fdi.tp.ttt;

import static org.junit.Assert.*;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

public class TttStateTest {

	/**
	 * Tests to see if save-load actually works
	 * @throws IOException if error while loading
	 */
	@Test
	public void testSaveLoad() throws IOException {
		TttState state = new TttState(3);

		// make a few moves, without winning
		for (int i=0; i<4; i++) {
			state = (TttState)takeRandomAction(state);
		}

		// save it
		File temp = Files.createTempFile("game", ".state").toFile();
		System.out.println("Game saved as " + temp.getAbsolutePath() + " ...");
		state.save(temp);

		// try to load and compare to saved
		TttState loaded = (TttState) GameState.load(temp);
		System.out.println("Saved:\n" + state.toString());
		System.out.println("Loaded:\n" + loaded.toString());
		assertEquals("loaded == saved", loaded.toString(), state.toString());

		// see if we can continue playing
		loaded =  (TttState)takeRandomAction(loaded);
		System.out.println("Loaded (after 1 move):\n" + loaded.toString());
	}

	/**
	 * Plays randomly, once.
	 * @param state
	 * @return the resulting state
	 */
	private static <S extends GameState<S,A>, A extends GameAction<S,A>> S takeRandomAction(S state) {
		List<A> actions = state.validActions(state.getTurn());
		return actions.get(new Random().nextInt(actions.size())).applyTo(state);
	}
}
