package uk.ac.ebi.interpro.scan.precalc.berkeley.model;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.SecondaryKey;

import java.util.HashSet;
import java.util.Set;

import static com.sleepycat.persist.model.Relationship.MANY_TO_ONE;

/**
 * Very simple Match implementation for data transfer &
 * storage in BerkeleyDB.
 * <p/>
 * Holds all of the fields that may appear in any Location
 * implementation (from the main InterProScan 5 data model).
 * <p/>
 * Note that the MD5 of the protein sequence is the key used to
 * access this data from BerkeleyDB,so does not appear in the class.
 *
 * @author Phil Jones
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */

@Entity
public class BK_Match {

    @PrimaryKey(sequence = "match_unique_index_sequence")
    private Long matchId;

    @SecondaryKey(relate = MANY_TO_ONE)
    private String proteinMD5;

    private String signatureLibrary;

    private String signatureLibraryRelease;

    private String signatureAccession;

    private Double sequenceScore;

    private Double sequenceEValue;

    private String graphScan;

    private Set<BK_Location> locations;

    /**
     * Required by BerkeleyDB
     */
    public BK_Match() {

    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getProteinMD5() {
        return proteinMD5;
    }

    public void setProteinMD5(String proteinMD5) {
        this.proteinMD5 = proteinMD5;
    }

    public String getSignatureLibrary() {
        return signatureLibrary;
    }

    public void setSignatureLibrary(String signatureLibrary) {
        this.signatureLibrary = signatureLibrary;
    }

    public String getSignatureLibraryRelease() {
        return signatureLibraryRelease;
    }

    public void setSignatureLibraryRelease(String signatureLibraryRelease) {
        this.signatureLibraryRelease = signatureLibraryRelease;
    }

    public String getSignatureAccession() {
        return signatureAccession;
    }

    public void setSignatureAccession(String signatureAccession) {
        this.signatureAccession = signatureAccession;
    }

    public Double getSequenceScore() {
        return sequenceScore;
    }

    public void setSequenceScore(Double sequenceScore) {
        this.sequenceScore = sequenceScore;
    }

    public Double getSequenceEValue() {
        return sequenceEValue;
    }

    public void setSequenceEValue(Double sequenceEValue) {
        this.sequenceEValue = sequenceEValue;
    }

    public String getGraphScan() {
        return graphScan;
    }

    public void setGraphScan(String graphScan) {
        this.graphScan = graphScan;
    }

    public Set<BK_Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<BK_Location> locations) {
        this.locations = locations;
    }

    public void addLocation(BK_Location location) {
        if (this.locations == null) {
            this.locations = new HashSet<BK_Location>();
        }
        locations.add(location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BK_Match bk_match = (BK_Match) o;

        if (graphScan != null ? !graphScan.equals(bk_match.graphScan) : bk_match.graphScan != null) return false;
        if (locations != null ? !locations.equals(bk_match.locations) : bk_match.locations != null) return false;
        if (sequenceEValue != null ? !sequenceEValue.equals(bk_match.sequenceEValue) : bk_match.sequenceEValue != null)
            return false;
        if (sequenceScore != null ? !sequenceScore.equals(bk_match.sequenceScore) : bk_match.sequenceScore != null)
            return false;
        if (signatureAccession != null ? !signatureAccession.equals(bk_match.signatureAccession) : bk_match.signatureAccession != null)
            return false;
        if (signatureLibrary != null ? !signatureLibrary.equals(bk_match.signatureLibrary) : bk_match.signatureLibrary != null)
            return false;
        if (signatureLibraryRelease != null ? !signatureLibraryRelease.equals(bk_match.signatureLibraryRelease) : bk_match.signatureLibraryRelease != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = signatureLibrary != null ? signatureLibrary.hashCode() : 0;
        result = 31 * result + (signatureLibraryRelease != null ? signatureLibraryRelease.hashCode() : 0);
        result = 31 * result + (signatureAccession != null ? signatureAccession.hashCode() : 0);
        result = 31 * result + (sequenceScore != null ? sequenceScore.hashCode() : 0);
        result = 31 * result + (sequenceEValue != null ? sequenceEValue.hashCode() : 0);
        result = 31 * result + (graphScan != null ? graphScan.hashCode() : 0);
        result = 31 * result + (locations != null ? locations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BK_Match{" +
                "proteinMD5='" + proteinMD5 + '\'' +
                ", locations=" + locations +
                ", matchId=" + matchId +
                ", signatureAccession='" + signatureAccession + '\'' +
                '}';
    }
}
