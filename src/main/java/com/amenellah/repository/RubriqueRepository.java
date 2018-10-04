package com.amenellah.repository;

import com.amenellah.domain.Rubrique;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Rubrique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {

    @Query(value = "select distinct rubrique from Rubrique rubrique left join fetch rubrique.lieus",
        countQuery = "select count(distinct rubrique) from Rubrique rubrique")
    Page<Rubrique> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct rubrique from Rubrique rubrique left join fetch rubrique.lieus")
    List<Rubrique> findAllWithEagerRelationships();

    @Query("select rubrique from Rubrique rubrique left join fetch rubrique.lieus where rubrique.id =:id")
    Optional<Rubrique> findOneWithEagerRelationships(@Param("id") Long id);

}
