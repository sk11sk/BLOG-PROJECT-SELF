package com.blog.entity;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table( name  = "Posts")
public class Post {


     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private   long id;
     private String title;
     private String description;
     private String content;


     @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)    // always  mapped by the object of parent entity
     private List<Comment> comments = new ArrayList<>();

}
