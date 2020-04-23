package Code6;

import java.io.Serializable;

/**
 * this enum has type Organization. PUBLIC is more than COMMERCIAL. COMMERCIAL is more than TRUST
 */
public enum OrganizationType implements Serializable {

    COMMERCIAL(2, "COMMERCIAL"),
    PUBLIC(3, "PUBLIC"),
    TRUST(1, "TRUST");
    private static final long serialVersionUID = 21L;
    public final int VALUE;
    public final String NAME;

    OrganizationType(int VALUE, String NAME){
        this.VALUE = VALUE;
        this.NAME = NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
