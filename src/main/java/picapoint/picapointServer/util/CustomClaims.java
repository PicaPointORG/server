package picapoint.picapointServer.util;

public enum CustomClaims {
    USER_NAME("username"),
    USER_ROLE("role"),
    USER_CIF("cif");

    private final String claim;

    CustomClaims(String claim) {
        this.claim = claim;
    }

    public String getValue() {
        return claim;
    }
}
