package com.amenellah.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Rubrique.
 */
@Entity
@Table(name = "rubrique")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rubrique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @OneToMany(mappedBy = "rubrique")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Voyage> voyages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "rubrique_lieu",
               joinColumns = @JoinColumn(name = "rubriques_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "lieus_id", referencedColumnName = "id"))
    private Set<Lieu> lieus = new HashSet<>();

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

    public Rubrique libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public Set<Voyage> getVoyages() {
        return voyages;
    }

    public Rubrique voyages(Set<Voyage> voyages) {
        this.voyages = voyages;
        return this;
    }

    public Rubrique addVoyage(Voyage voyage) {
        this.voyages.add(voyage);
        voyage.setRubrique(this);
        return this;
    }

    public Rubrique removeVoyage(Voyage voyage) {
        this.voyages.remove(voyage);
        voyage.setRubrique(null);
        return this;
    }

    public void setVoyages(Set<Voyage> voyages) {
        this.voyages = voyages;
    }

    public Set<Lieu> getLieus() {
        return lieus;
    }

    public Rubrique lieus(Set<Lieu> lieus) {
        this.lieus = lieus;
        return this;
    }

    public Rubrique addLieu(Lieu lieu) {
        this.lieus.add(lieu);
        lieu.getRubriques().add(this);
        return this;
    }

    public Rubrique removeLieu(Lieu lieu) {
        this.lieus.remove(lieu);
        lieu.getRubriques().remove(this);
        return this;
    }

    public void setLieus(Set<Lieu> lieus) {
        this.lieus = lieus;
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
        Rubrique rubrique = (Rubrique) o;
        if (rubrique.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rubrique.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rubrique{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            "}";
    }
}
