package ru.sberbank.a19190813.WebfluxAndOpenshiftTestApp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("post")
public class Post {
    @Id
    Long id;
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime creationDate;
    String title;
    String text;
    String img;
    String author;
    Integer likes;
}
