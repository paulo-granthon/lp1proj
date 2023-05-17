package org.openjfx.lpi.db;

public enum Table {
    Person  ("person",  new String[] { "id", "prsn_name", "prsn_gender", "prsn_birth" }),
    Place   ("place",   new String[] { "id", "plce_country", "plce_state", "plce_city" }),
    Vehicle ("vehicle", new String[] { "id", "vhcl_model", "vhcl_year" }),
    Trip    ("trip",    new String[] { "id" }),
    ;

    private String name;
    private String[] fields;

    private Table (String name, String[] fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName() { return this.name; }
    public String[] getfields() { return this.fields; }

    public final String getInsertQuery () {
        StringBuilder sb = new StringBuilder("INSERT INTO ").append(name).append(" (");
        for (int i = 1; i < fields.length; i++) sb.append(fields[i]).append(i < fields.length - 1 ? ", " : ")");
        sb.append(" values (");
        for (int i = 1; i < fields.length; i++) sb.append("?").append(i < fields.length - 1 ? ", " : ")");
        return sb.toString();
    }


}
