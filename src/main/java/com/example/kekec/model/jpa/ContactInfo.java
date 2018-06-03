package com.example.kekec.model.jpa;

import org.hibernate.search.annotations.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Riste Stojanov
 */
@Entity
@Table(name = "contact_info")
public class ContactInfo extends BaseEntity {

  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
  @Analyzer(definition = "emtAnalyser")
  @Boost(2f)
  public String firstName;

  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
  @Analyzer(definition = "emtAnalyser")
  @Boost(2f)
  public String lastName;

  @Field(index = Index.YES, store = Store.NO, analyze = Analyze.YES)
  @Analyzer(definition = "emtAnalyser")
  @Boost(2f)
  public String phone;


}
