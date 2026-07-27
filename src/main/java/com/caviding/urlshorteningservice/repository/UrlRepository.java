package com.caviding.urlshorteningservice.repository;

import com.caviding.urlshorteningservice.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    boolean existsByShortCode(String shortCode);
}
