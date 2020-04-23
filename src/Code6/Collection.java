package Code6;

import java.io.Serializable;
import java.util.TreeSet;

public class Collection implements Serializable {
    private static final long serialVersionUID = 30L;
    TreeSet<Organization> organizations;

    public Collection(TreeSet<Organization> organizations){
        this.organizations = organizations;
    }

    public TreeSet<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(TreeSet<Organization> organizations) {
        this.organizations = organizations;
    }
}
