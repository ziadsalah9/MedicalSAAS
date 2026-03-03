package com.MidecalApp.VideoConsultatntAppDemo.video.repository;

import com.MidecalApp.VideoConsultatntAppDemo.video.Entity.VideoSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface videoRepository extends JpaRepository<VideoSession,Long> {

    Optional<VideoSession> findByRoomName(String roomName);
}
