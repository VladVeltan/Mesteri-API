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
    @NonNull
    private double amount;

    private String message;
    @NonNull
    private LocalDateTime creationDate;

    @ManyToOne
    @NonNull
    @JoinColumn(name="user_id")
    private User bidder;

    @ManyToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name="project_id")
    private Project project;

}
