package com.hdu.lease.repository.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of base repository.
 *
 * @param <DOMAIN> domain type
 * @param <ID> id type
 * @author Jackson
 * @date 2022/4/30 18:57
 * @description:
 */
public class BaseRepositoryImpl<DOMAIN, ID> extends SimpleJpaRepository<DOMAIN, ID>
        implements BaseRepository<DOMAIN, ID> {

    private final JpaEntityInformation<DOMAIN, ID> entityInformation;

    private final EntityManager entityManager;

    public BaseRepositoryImpl(JpaEntityInformation<DOMAIN, ID> entityInformation,
                              EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
    }

    /**
     * Finds all domain by id list.
     *
     * @param ids  id list of domain must not be null
     * @param sort the specified sort must not be null
     * @return a list of domains
     */
    @Override
    public List<DOMAIN> findAllByIdIn(Collection<ID> ids, Sort sort) {
        return null;
    }
}
