package com.yoon.twicePractice.repository;

import com.yoon.twicePractice.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
