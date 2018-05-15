package es.ucm.fdi.tp.base.player;

/**
 * A smart player.
 */
public class SmartPlayer extends AiPlayer {
    public SmartPlayer(String name, int depth) {
        super(name, new MinMax(depth));
    }
}
