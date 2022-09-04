package com.ll.exam.damda.entity.search;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String name;

    @OneToMany(mappedBy = "tag")
    @Builder.Default
    private List<ReviewTag> reviewTagList = new ArrayList<>();
}
