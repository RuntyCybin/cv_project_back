package com.cybindev.socials.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.socials.domain.Social;

public interface SocialsRepo extends JpaRepository<Social, Long> {

}
