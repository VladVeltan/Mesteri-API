package meseriasiapi.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Table(name="bids")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Bid extends BaseEntity{
    private double amount;

    private String message;

    private LocalDateTime creationDate;

    @ManyToOne
    @NonNull
    @JoinColumn(name="user_id")
    private User bidder;

    @OneToOne
    @JoinColumn(name="project_id")
    private Project project;

}