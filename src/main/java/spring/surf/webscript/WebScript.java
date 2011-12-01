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
     * Runs the WebScript
     *
     * @param inputStream
     * @param writer
     * @param model
     * @return
     */
    // TODO better documentation and type safety. Or just migrate everything to pure Clojure
    Object run(Object inputStream, Object writer, Object model);
}
