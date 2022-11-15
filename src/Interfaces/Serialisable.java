package Interfaces;

/**
 * This interface is for serialisation of objects
 * to the text format used in save/level files.
 */
public interface Serialisable {
    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    public abstract String serialise();
}
