package com.amenellah.repository;

import com.amenellah.domain.Hebergement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Hebergement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HebergementRepository extends JpaRepository<Hebergement, Long> {

}
