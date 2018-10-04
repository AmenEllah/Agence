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
 * A Lieu.
 */
@Entity
@Table(name = "lieu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lieu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libele")
    private String libele;

    @OneToMany(mappedBy = "lieuDepart")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Voyage> voyage1S = new HashSet<>();

    @OneToMany(mappedBy = "lieuArrive")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Voyage> voyage2S = new HashSet<>();

    @OneToMany(mappedBy = "lieu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Image> images = new HashSet<>();

    @ManyToMany(mappedBy = "lieus")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rubrique> rubriques = new HashSet<>();

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

    public Lieu libele(String libele) {
        this.libele = libele;
        return this;
    }

    public void setLibele(String libele) {
        this.libele = libele;
    }

    public Set<Voyage> getVoyage1S() {
        return voyage1S;
    }

    public Lieu voyage1S(Set<Voyage> voyages) {
        this.voyage1S = voyages;
        return this;
    }

    public Lieu addVoyage1(Voyage voyage) {
        this.voyage1S.add(voyage);
        voyage.setLieuDepart(this);
        return this;
    }

    public Lieu removeVoyage1(Voyage voyage) {
        this.voyage1S.remove(voyage);
        voyage.setLieuDepart(null);
        return this;
    }

    public void setVoyage1S(Set<Voyage> voyages) {
        this.voyage1S = voyages;
    }

    public Set<Voyage> getVoyage2S() {
        return voyage2S;
    }

    public Lieu voyage2S(Set<Voyage> voyages) {
        this.voyage2S = voyages;
        return this;
    }

    public Lieu addVoyage2(Voyage voyage) {
        this.voyage2S.add(voyage);
        voyage.setLieuArrive(this);
        return this;
    }

    public Lieu removeVoyage2(Voyage voyage) {
        this.voyage2S.remove(voyage);
        voyage.setLieuArrive(null);
        return this;
    }

    public void setVoyage2S(Set<Voyage> voyages) {
        this.voyage2S = voyages;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Lieu images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Lieu addImage(Image image) {
        this.images.add(image);
        image.setLieu(this);
        return this;
    }

    public Lieu removeImage(Image image) {
        this.images.remove(image);
        image.setLieu(null);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Rubrique> getRubriques() {
        return rubriques;
    }

    public Lieu rubriques(Set<Rubrique> rubriques) {
        this.rubriques = rubriques;
        return this;
    }

    public Lieu addRubrique(Rubrique rubrique) {
        this.rubriques.add(rubrique);
        rubrique.getLieus().add(this);
        return this;
    }

    public Lieu removeRubrique(Rubrique rubrique) {
        this.rubriques.remove(rubrique);
        rubrique.getLieus().remove(this);
        return this;
    }

    public void setRubriques(Set<Rubrique> rubriques) {
        this.rubriques = rubriques;
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
        Lieu lieu = (Lieu) o;
        if (lieu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lieu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lieu{" +
            "id=" + getId() +
            ", libele='" + getLibele() + "'" +
            "}";
    }
}
