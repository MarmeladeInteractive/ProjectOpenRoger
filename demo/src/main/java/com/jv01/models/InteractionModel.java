package com.jv01.models;

public enum InteractionModel {
    NPCS,
    BUILDINGS,
    ITEMS,
    TOOLS,
    MERCHANTS,
    ARCADES,
    UNDEFINED;

    public String toStringSingular() {
        return name().toLowerCase().substring(0, name().length() - 1);
    }

    public String toStringPlural() {
        return name().toLowerCase();
    }

    // Generic method to return the path based on enum value
    public String getPath() {
        switch (this) {
            case NPCS:
                return pathToNPCS();
            case BUILDINGS:
                return pathToBUILDINGS();
            case ITEMS:
                return pathToITEMS();
            case TOOLS:
                return pathToTOOLS();
            case MERCHANTS:
                return pathToMERCHANTS();
            case ARCADES:
                return pathToARCADES();
            case UNDEFINED:
                throw new IllegalArgumentException("Interaction not found: " + this);
            default:
                throw new IllegalArgumentException("Unsupported enum constant: " + this);
        }
    }

    public String pathToNPCS() {
        String s;
        s = "characters";
        return s;
    }

    public String pathToBUILDINGS() {
        String s;
        s = "functional/" + name().toLowerCase();
        return s;
    }

    public String pathToITEMS() {
        String s;
        s = "functional/" + name().toLowerCase();
        return s;

    }

    public String pathToTOOLS() {
        String s;
        s = "functional/" + name().toLowerCase();
        return s;
    }

    public String pathToMERCHANTS() {
        String s;
        s = "functional/dealers";
        return s;
    }

    public String pathToARCADES() {
        String s;
        s = "functional/" + name().toLowerCase();
        return s;
    }
}