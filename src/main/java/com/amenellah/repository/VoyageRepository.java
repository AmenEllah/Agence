package com.amenellah.repository;

import com.amenellah.domain.Voyage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Voyage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoyageRepository extends JpaRepository<Voyage, Long> {

}
