package com.amenellah.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "name")
    private byte[] name;

    @Column(name = "name_content_type")
    private String nameContentType;

    @ManyToOne
    @JsonIgnoreProperties("images")
    private Lieu lieu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getName() {
        return name;
    }

    public Image name(byte[] name) {
        this.name = name;
        return this;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public String getNameContentType() {
        return nameContentType;
    }

    public Image nameContentType(String nameContentType) {
        this.nameContentType = nameContentType;
        return this;
    }

    public void setNameContentType(String nameContentType) {
        this.nameContentType = nameContentType;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public Image lieu(Lieu lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
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
        Image image = (Image) o;
        if (image.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", nameContentType='" + getNameContentType() + "'" +
            "}";
    }
}
