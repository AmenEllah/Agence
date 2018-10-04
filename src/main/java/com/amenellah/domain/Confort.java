package com.amenellah.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Confort.
 */
@Entity
@Table(name = "confort")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Confort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @Column(name = "valeur")
    private Integer valeur;

    @OneToMany(mappedBy = "confort")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reservation> reservations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibele() {
        return libele;
    }

    public Confort libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public Integer getValeur() {
        return valeur;
    }

    public Confort valeur(Integer valeur) {
        this.valeur = valeur;
        return this;
    }

    public void setValeur(Integer valeur) {
        this.valeur = valeur;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public Confort reservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Confort addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        reservation.setConfort(this);
        return this;
    }

    public Confort removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
        reservation.setConfort(null);
        return this;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
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
        Confort confort = (Confort) o;
        if (confort.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), confort.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Confort{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            ", valeur=" + getValeur() +
            "}";
    }
}
