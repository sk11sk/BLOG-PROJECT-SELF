package com.blog.payload;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {

        private   long id;
        private String title;
        private String description;
        private String content;



}
