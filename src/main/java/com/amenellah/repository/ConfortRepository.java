package com.amenellah.repository;

import com.amenellah.domain.Confort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Confort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfortRepository extends JpaRepository<Confort, Long> {

}
