package Code6;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeSet;

/**
 * this class has collection treeSet with elements Organization.
 * @see Organization
 */
public class CollectionOrganization implements Serializable {
    private static final long serialVersionUID = 17L;
    private static TreeSet<Organization> collection;
    private static CollectionOrganization collectionOrganization = new CollectionOrganization();
    private Date date;

    /**
     * this constructor creat new collection TreeSet. elements are sorted in alphabetical order
     */
    private CollectionOrganization() {
        date = new GregorianCalendar().getTime();
        collection = new TreeSet<>(Comparator.comparing(Organization::getName));
    }

    /**
     * this method adds element to the collection
     * @param or this element will be added to the collection
     */
    public void put(Organization or){
        collection.add(or);
    }

    /**
     * this method return collection TreeSet
     * @return return collection TreeSet
     */
    public TreeSet<Organization> getCollection() {
        return collection;
    }

    /**
     * this method return the minimum element collection TreeSet. Minimum element has the minimum hashCode
     * @return return the minimum element collection TreeSet. if collection is empty, then method returns null
     */
    public Organization getMinElement(){
        int min = 0;
        Organization minO = null;
        for (Organization o: collection){
            if(o.hashCode() < min){
                min = o.hashCode();
                minO = o;
            }
        }
        return minO;
    }

    /**
     * this method return the maximum element collection TreeSet.Maximum element has thee maximum hashCode
     * @return return the maximum element collection TreeSet. if collection is empty, then method returns null
     */
    public Organization getMaxElement(){
        int max = 0;
        Organization maxO = null;
        for (Organization o: collection){
            if(o.hashCode() > max){
                max = o.hashCode();
                maxO = o;
            }
        }
        return maxO;
    }

    public void setCollection(TreeSet<Organization> list){
        collection.addAll(list);
    }

    public static CollectionOrganization getCollectionOrganization(){
        return collectionOrganization;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "collection = " + collection.first();
    }
}
