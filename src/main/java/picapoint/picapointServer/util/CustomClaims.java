package picapoint.picapointServer.util;

public enum CustomClaims {
    USER_NAME("userName"),
    USER_ROLE("userRole");

    private final String claim;

    CustomClaims(String claim) {
        this.claim = claim;
    }

    public String getClaim() {
        return claim;
    }
}
