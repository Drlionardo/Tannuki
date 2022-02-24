package com.drlionardo.registryhub.repo;

import com.drlionardo.registryhub.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepo extends JpaRepository<Image, UUID> {

}
