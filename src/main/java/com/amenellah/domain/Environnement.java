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
 * A Environnement.
 */
@Entity
@Table(name = "environnement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Environnement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @OneToMany(mappedBy = "environnement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Voyage> voyages = new HashSet<>();

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

    public Environnement libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public Set<Voyage> getVoyages() {
        return voyages;
    }

    public Environnement voyages(Set<Voyage> voyages) {
        this.voyages = voyages;
        return this;
    }

    public Environnement addVoyage(Voyage voyage) {
        this.voyages.add(voyage);
        voyage.setEnvironnement(this);
        return this;
    }

    public Environnement removeVoyage(Voyage voyage) {
        this.voyages.remove(voyage);
        voyage.setEnvironnement(null);
        return this;
    }

    public void setVoyages(Set<Voyage> voyages) {
        this.voyages = voyages;
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
        Environnement environnement = (Environnement) o;
        if (environnement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), environnement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Environnement{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            "}";
    }
}
