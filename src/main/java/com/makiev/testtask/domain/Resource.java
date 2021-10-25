package com.makiev.testtask.domain;

import com.makiev.testtask.configuration.Statuses;
import lombok.*;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"createdOn", "sentOn", "status", "emails", "resource_url"})
@Table(name = "resource")
public class Resource{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "resource_url", nullable = false)
    private String resourceUrl;
    @Column(name = "created_on", nullable = false)
    private Date createdOn;
    @Column(name = "sent_on", nullable = true)
    private Date sentOn;
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy="resource", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Email> emails;

    public Resource(String resourceUrl, Date createdOn, Date sentOn, Statuses status){
        this.resourceUrl = resourceUrl;
        this.createdOn = createdOn;
        this.sentOn = sentOn;
        this.status = status.name();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
