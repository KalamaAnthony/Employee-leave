package com.example.Success18.ImageData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDataRepo extends JpaRepository<ImageData, Long> {
}
