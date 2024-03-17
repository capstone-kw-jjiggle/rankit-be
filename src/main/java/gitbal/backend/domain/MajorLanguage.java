package gitbal.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MajorLanguage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "major_language_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String majorLanguage;

    private Long languageCount;



}
