package meseriasiapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import meseriasiapi.domain.Category;
import meseriasiapi.domain.Listing;
import meseriasiapi.repository.ListingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static meseriasiapi.exceptions.messages.Messages.LISTING_NOT_FOUND;
import static meseriasiapi.exceptions.messages.Messages.NO_LISTING_WITH_THIS_ID_FOUND;

@Service
@AllArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing findById(UUID listingId) {
        Optional<Listing> listing = listingRepository.findById(listingId);
        if (listing.isEmpty()) {
            throw new EntityNotFoundException(NO_LISTING_WITH_THIS_ID_FOUND);
        }
        return listing.get();
    }

    public boolean checkIfCategoryIsInEnum(String category) {
        try {
            Category.valueOf(category);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Listing createListing(Listing listing) {
        if (!checkIfCategoryIsInEnum(listing.getCategory().name()) || listing.getCategory() == null) {
            throw new EntityNotFoundException(LISTING_NOT_FOUND);
        }

        return listingRepository.save(listing);

    }

    public Listing updateListing(Listing newUser) {
        if(!checkIfCategoryIsInEnum(newUser.getCategory().name())||newUser.getCategory()==null){
            throw new EntityNotFoundException("Category does not exist");
        }
        Optional<Listing> existingListingById=listingRepository.findById(newUser.getId());
        if(existingListingById.isEmpty()){
            throw new EntityNotFoundException(NO_LISTING_WITH_THIS_ID_FOUND);
        }
        Listing existingListing=existingListingById.get();

        Listing updatedListing=Listing.builder()
                .id(existingListing.getId())
                .title(newUser.getTitle())
                .description(newUser.getDescription())
                .category(newUser.getCategory())
                .county(newUser.getCounty())
                .city(newUser.getCity())
                .media(newUser.getMedia())
                .user(newUser.getUser())
                .build();

        return listingRepository.save(updatedListing);
    }
}
