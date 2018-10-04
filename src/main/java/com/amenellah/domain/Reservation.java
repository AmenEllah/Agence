package com.amenellah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nbr_adultes")
    private Integer nbrAdultes;

    @Column(name = "nbr_enfants")
    private Integer nbrEnfants;

    @Column(name = "etat")
    private String etat;

    @ManyToOne
    @JsonIgnoreProperties("reservations")
    private Voyage voyage;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("reservations")
    private Confort confort;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("reservations")
    private Hebergement hebergement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNbrAdultes() {
        return nbrAdultes;
    }

    public Reservation nbrAdultes(Integer nbrAdultes) {
        this.nbrAdultes = nbrAdultes;
        return this;
    }

    public void setNbrAdultes(Integer nbrAdultes) {
        this.nbrAdultes = nbrAdultes;
    }

    public Integer getNbrEnfants() {
        return nbrEnfants;
    }

    public Reservation nbrEnfants(Integer nbrEnfants) {
        this.nbrEnfants = nbrEnfants;
        return this;
    }

    public void setNbrEnfants(Integer nbrEnfants) {
        this.nbrEnfants = nbrEnfants;
    }

    public String getEtat() {
        return etat;
    }

    public Reservation etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public Reservation voyage(Voyage voyage) {
        this.voyage = voyage;
        return this;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public Confort getConfort() {
        return confort;
    }

    public Reservation confort(Confort confort) {
        this.confort = confort;
        return this;
    }

    public void setConfort(Confort confort) {
        this.confort = confort;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public Reservation hebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
        return this;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        if (reservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", nbrAdultes=" + getNbrAdultes() +
            ", nbrEnfants=" + getNbrEnfants() +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
