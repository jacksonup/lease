package com.hdu.lease.repository.base;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;

/**
 * Base repository interface contains some common methods.
 *
 * @author Jackson
 * @date 2022/4/30 18:51
 * @description:
 */
@NoRepositoryBean
public interface BaseRepository<D, I> extends JpaRepository<D, I> {


    /**
     * Finds all domain by id list.
     *
     * @param ids id list of domain must not be null
     * @param sort the specified sort must not be null
     * @return a list of domains
     */
    @NonNull
    List<D> findAllByIdIn(@NonNull Collection<I> ids, @NonNull Sort sort);

}
