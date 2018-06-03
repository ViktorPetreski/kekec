package com.example.kekec.model.jpa;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "candidates")
@Indexed
@AnalyzerDef(name = "emtAnalyser",
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = LowerCaseFilterFactory.class)
        })

public class Candidate extends BaseEntity {

    @OneToOne
    @IndexedEmbedded
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    public ContactInfo contactInfo;

    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    public PaymentInfo paymentInfo;


    @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
    @Analyzer(definition = "emtAnalyser")
    @Boost(0.5f)
    public String ssn;

    public Boolean isPassed;

    public Double inDebt;

    public String drivingCategory;

//    lessons left
    public Integer numberOfLessons;
}
