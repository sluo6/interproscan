package uk.ac.ebi.interpro.scan.model.raw;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import uk.ac.ebi.interpro.scan.model.Chunker;
import uk.ac.ebi.interpro.scan.model.ChunkerSingleton;
import uk.ac.ebi.interpro.scan.model.SignatureLibrary;

import javax.persistence.*;
import java.util.List;

/**
 * <a href="http://gene3d.biochem.ucl.ac.uk/Gene3D/">Gene3D</a> raw match.
 * <p/>
 * TODO: PJ: Consider if the functionality in this class should be in a second abstract
 * TODO: class for any HMMER3 member database that needs to store the alignment.
 *
 * @author Antony Quinn
 * @author Phil Jones
 * @version $Id$
 */
@Entity
@javax.persistence.Table(name = Gene3dHmmer3RawMatch.TABLE_NAME)
@org.hibernate.annotations.Table(appliesTo = Gene3dHmmer3RawMatch.TABLE_NAME, indexes = {
        @Index(name = "G3D_RW_SEQ_IDX", columnNames = {RawMatch.COL_NAME_SEQUENCE_IDENTIFIER}),
        @Index(name = "G3D_RW_NUM_SEQ_IDX", columnNames = {RawMatch.COL_NAME_NUMERIC_SEQUENCE_ID}),
        @Index(name = "G3D_RW_MODEL_IDX", columnNames = {RawMatch.COL_NAME_MODEL_ID}),
        @Index(name = "G3D_RW_SIGLIB_IDX", columnNames = {RawMatch.COL_NAME_SIGNATURE_LIBRARY}),
        @Index(name = "G3D_RW_SIGLIB_REL_IDX", columnNames = {RawMatch.COL_NAME_SIGNATURE_LIBRARY_RELEASE})
})
public class Gene3dHmmer3RawMatch extends Hmmer3RawMatch {

    @Transient
    private static final Chunker CHUNKER = ChunkerSingleton.getInstance();

    @Transient
    public static final String TABLE_NAME = "GENE3D_HMMER3_RAW_MATCH";

    @ElementCollection(fetch = FetchType.EAGER)
    // Hibernate specific annotation.
    @JoinTable(name = "cigar_align_chunk")
    @IndexColumn(name = "chunk_index")
    @Column(length = Chunker.CHUNK_SIZE, nullable = true)
    private List<String> cigarChunks;

    // TODO - Confirm that the cigar alignment is mandatory.
    @Column(nullable = false, updatable = false, length = Chunker.CHUNK_SIZE)
    private String cigarFirstChunk;

    // Sequence alignment in CIGAR format
    @Transient
    private String cigarAlignment;

    protected Gene3dHmmer3RawMatch() {
    }

    public Gene3dHmmer3RawMatch(String sequenceIdentifier, String model,
                                String signatureLibraryRelease,
                                int locationStart, int locationEnd,
                                double evalue, double score,
                                int hmmStart, int hmmEnd, String hmmBounds,
                                double locationScore,
                                int envelopeStart, int envelopeEnd,
                                double expectedAccuracy, double fullSequenceBias,
                                double domainCeValue, double domainIeValue, double domainBias,
                                String cigarAlignment) {
        super(sequenceIdentifier, model, SignatureLibrary.GENE3D, signatureLibraryRelease, locationStart, locationEnd,
                evalue, score, hmmStart, hmmEnd, hmmBounds, locationScore, envelopeStart, envelopeEnd, expectedAccuracy,
                fullSequenceBias, domainCeValue, domainIeValue, domainBias);
        setCigarAlignment(cigarAlignment);
    }

    public String getCigarAlignment() {
        if (cigarAlignment == null) {
            cigarAlignment = CHUNKER.concatenate(cigarFirstChunk, cigarChunks);
        }
        return cigarAlignment;
    }

    private void setCigarAlignment(String cigarAlignment) {
        this.cigarAlignment = cigarAlignment;
        List<String> chunks = CHUNKER.chunkIntoList(cigarAlignment);
        this.cigarFirstChunk = CHUNKER.firstChunk(chunks);
        this.cigarChunks = CHUNKER.latterChunks(chunks);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Gene3dHmmer3RawMatch))
            return false;
        final Gene3dHmmer3RawMatch m = (Gene3dHmmer3RawMatch) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getCigarAlignment(), m.getCigarAlignment())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(53, 61)
                .appendSuper(super.hashCode())
                .append(getCigarAlignment())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
