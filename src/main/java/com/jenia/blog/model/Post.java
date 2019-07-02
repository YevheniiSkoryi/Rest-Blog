package com.jenia.blog.model;

import com.jenia.blog.dto.PostDTOIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "POST")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_post")
    private Integer id;

    @Column(name = "title")
    @Length(min = 1, message = "Must have at least 1 character")
    @NotNull
    private String title;

    @Column(name = "body")
    @NotNull
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NotNull
    private User user;

    public Post(
            final String title,
            final String body,
            final User user
    ) {
        this.title = title;
        this.body = body;
        this.user = user;
    }

    public void merge(final PostDTOIn postIn) {
        if (postIn.getTitle() != null && !postIn.getTitle().isEmpty()) {
            this.title = postIn.getTitle();
        }
        if (postIn.getBody() != null && !postIn.getTitle().isEmpty()) {
            this.body = postIn.getBody();
        }
    }
}
