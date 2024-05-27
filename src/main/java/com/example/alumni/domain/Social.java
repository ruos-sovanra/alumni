package com.example.alumni.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "al_socials")
@Setter
@Getter
@NoArgsConstructor
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String caption;

    @OneToMany(mappedBy = "social")
    private List<Thumbnail> thumbnails;

    private Integer likes;
    private Integer shares;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
