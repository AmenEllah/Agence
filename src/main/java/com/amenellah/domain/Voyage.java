package com.amenellah.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Voyage.
 */
@Entity
@Table(name = "voyage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Voyage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_depart")
    private LocalDate dateDepart;

    @Column(name = "duree")
    private Integer duree;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "voyage")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("voyages")
    private Rubrique rubrique;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("voyage1S")
    private Lieu lieuDepart;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("voyage2S")
    private Lieu lieuArrive;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("voyages")
    private Environnement environnement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public Voyage dateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
        return this;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Integer getDuree() {
        return duree;
    }

    public Voyage duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public Voyage description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Voyage reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Voyage addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setVoyage(this);
        return this;
    }

    public Voyage removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setVoyage(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Rubrique getRubrique() {
        return rubrique;
    }

    public Voyage rubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
        return this;
    }

    public void setRubrique(Rubrique rubrique) {
        this.rubrique = rubrique;
    }

    public Lieu getLieuDepart() {
        return lieuDepart;
    }

    public Voyage lieuDepart(Lieu lieu) {
        this.lieuDepart = lieu;
        return this;
    }

    public void setLieuDepart(Lieu lieu) {
        this.lieuDepart = lieu;
    }

    public Lieu getLieuArrive() {
        return lieuArrive;
    }

    public Voyage lieuArrive(Lieu lieu) {
        this.lieuArrive = lieu;
        return this;
    }

    public void setLieuArrive(Lieu lieu) {
        this.lieuArrive = lieu;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public Voyage environnement(Environnement environnement) {
        this.environnement = environnement;
        return this;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
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
        Voyage voyage = (Voyage) o;
        if (voyage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), voyage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Voyage{" +
            "id=" + getId() +
            ", dateDepart='" + getDateDepart() + "'" +
            ", duree=" + getDuree() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
