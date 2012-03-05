package spring.surf.webscript;

/**
 * The main interface a WebScript must implement.
 *
 * Alpha: subject to change
 * @author Carlo Sciolla
 * @author Andreas Steffan
 * @since 1.0
 */
public interface WebScript {
    /**
     * Runs the WebScript and stores the result by updating and returning the model
     *
     * @param inputStream A stream to the clojure web script sources
     * @param writer The output writer
     * @param model The model where to put results
     * @return The updated model
     */
    // TODO better documentation and type safety. Or just migrate everything to pure Clojure
    Object run(Object inputStream, Object writer, Object model);
}
