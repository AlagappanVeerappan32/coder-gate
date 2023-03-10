package com.github.codergate.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
   @Id
   @Column(name = "tag_url")
    private String tagUrl;

    @ManyToOne
    @JoinColumn(name = "repositoryId")
    private RepositoryEntity repositoryIdInTag;
}
