package DataTypes;

import Interfaces.Serialisable;

public record GameParams(int startTime, int startScore) implements Serialisable {

    /**
     * Serialises the Object into a String.
     *
     * @return Serialised string for `this` Object.
     */
    @Override
    public String serialise() {
        return String.format(
            "%s %s",
            this.startTime,
            this.startScore
        );
    }
}
