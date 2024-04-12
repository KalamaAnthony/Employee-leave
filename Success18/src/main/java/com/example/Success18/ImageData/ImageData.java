package com.example.Success18.ImageData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "imageData")
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileType;
    private String fileName;

    @Lob
    @Column(length = 1000)
    private String file;

}
