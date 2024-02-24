package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table( name  = "roles")
public class Role {
    @Id

    private long id;

    private String name;

@ManyToMany(mappedBy = "role",cascade = CascadeType.ALL)
private List<User>user;

}
