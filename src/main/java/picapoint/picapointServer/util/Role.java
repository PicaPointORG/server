package picapoint.picapointServer.util;

public enum Role {
    ADMIN("admin"),
    USER("usuario");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getValue() {
        return role;
    }
}
