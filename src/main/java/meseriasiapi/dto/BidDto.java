package meseriasiapi.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Getter
public class BidDto {
    private UUID id;
    private double amount;
    private String message;
    private LocalDateTime creationDate;
    private String bidderEmail;
    private String bidderFirstName;
    private String bidderLastName;
    private UUID projectId;
}
