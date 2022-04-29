package com.yoon.twicePractice.service;

import com.yoon.twicePractice.dto.MovieDTO;
import com.yoon.twicePractice.entity.Movie;
import com.yoon.twicePractice.entity.MovieImage;
import com.yoon.twicePractice.repository.MovieImageRepository;
import com.yoon.twicePractice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final MovieImageRepository imageRepository;

    @Override
    @Transactional
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        Movie movie = (Movie) entityMap.get("movie");
        List<MovieImage>movieImageList = (List<MovieImage>) entityMap.get("imgList");

        movieRepository.save(movie);

        movieImageList.forEach(img->{
            imageRepository.save(img);
        });
        return movie.getMno();
    }
}
