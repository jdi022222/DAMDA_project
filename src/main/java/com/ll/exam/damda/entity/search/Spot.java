package com.ll.exam.damda.entity.search;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spot_name", nullable = false)
    private String name;

    @Column(name = "spot_city", nullable = false)
    private String city;

    @Column(name = "spot_address")
    private String address;

    @Column(name = "spot_description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "spot")
    @Builder.Default
    private List<SpotImage> spotImageURLList = new ArrayList<>();

    @OneToMany(mappedBy = "spot")
    @Builder.Default
    private List<Review> reviewList = new ArrayList<>();

    @Column(name = "spot_review_cnt", nullable = false)
    private int reviewCnt = 0;

    //==조회 로직==//
    public Map<Tag, Integer> getTagMap() {
        Map<Tag, Integer> tagInfo = new HashMap<>();

        for (Review review : this.reviewList) {
            for (Map.Entry<Tag, Integer> entry : review.getTagInfo().entrySet()) {
                if (!tagInfo.containsKey(entry.getKey())) {
                    tagInfo.put(entry.getKey(), entry.getValue());
                } else {
                    tagInfo.put(entry.getKey(), tagInfo.get(entry.getKey()) + 1);
                }
            }
        }

        return tagInfo;
    }

    public List<Tag> getMostTagList() {
        List<Tag> tagList = getTagMap().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<Tag> mostTagList = new ArrayList<>();
        int i = 0;
        while (i < 3 && i < tagList.size()) {
            Tag tag = tagList.get(i);
            mostTagList.add(tag);
            i++;
        }

        return mostTagList;
    }
}
