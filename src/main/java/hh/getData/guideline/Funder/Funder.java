package hh.getData.guideline.Funder;

import com.fasterxml.jackson.annotation.JsonManagedReference;


import hh.getData.guideline.FunderSource.FunderSource;
import hh.getData.guideline.Image.Image;
import hh.getData.guideline.enumeration.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fund_name", nullable = false)
    private String fundName;

    @Column(name = "description")
    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "logo_id", referencedColumnName = "id")
    @JsonManagedReference
    private Image image;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "required_documents")
    private String requiredDocuments;

    @Column(name = "advantages")
    private String advantages;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "products")
    private String products;

    @Column(name = "funding_limit")
    private String fundingLimit;

    @Column(name = "guarantees")
    private String guarantees;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @OneToMany(mappedBy = "funder", fetch = FetchType.EAGER, cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JsonManagedReference
    private List<FunderSource> funderSources = new ArrayList<>();

    public static Funder from(FunderDto funderDto) {
        Funder funder = new Funder();
        funder.setFundName(funderDto.getFundName());
        funder.setDescription(funderDto.getDescription());
        funder.setImage(funderDto.getImage());
        funder.setEmail(funderDto.getEmail());
        funder.setPhoneNumber(funderDto.getPhoneNumber());
        funder.setRequiredDocuments(funderDto.getRequiredDocuments());
        funder.setAdvantages(funderDto.getAdvantages());
        funder.setConditions(funderDto.getConditions());
        funder.setProducts(funderDto.getProducts());
        funder.setFundingLimit(funderDto.getFundingLimit());
        funder.setGuarantees(funderDto.getGuarantees());
        funder.setStatus(funderDto.getStatus());
        return funder;
    }

    public void addFunderSource(FunderSource funderSource) {funderSources.add(funderSource);}

    public void removeFunderSource(FunderSource funderSource) {funderSources.remove(funderSource);}
}
