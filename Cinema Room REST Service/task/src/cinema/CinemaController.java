package cinema;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {

    private static Seats availableSeats = Seats.getAllEmptySeats();

    @GetMapping("/seats")
    public ResponseEntity<Seats> getSeats() {
        return ResponseEntity.ok().body(availableSeats);
    }

    @PostMapping("/purchase")
    public ResponseEntity<Purchase> purchase(@RequestBody Seat seat) throws SeatException {
        Purchase purchase = availableSeats.purchase(seat);
        return ResponseEntity.ok().body(purchase);
    }

    @PostMapping("/return")
    public ResponseEntity<Return> returnByToken(@RequestBody Purchase purchase) throws SeatException {
        Return aReturn = availableSeats.returnByToken(purchase.getToken());
        return ResponseEntity.ok().body(aReturn);
    }

    @PostMapping("/stats")
    public ResponseEntity<Stats> stats(@RequestParam(required = false) String password) throws UnauthorizedException {
        if (password == null || "".equals(password)
            || !"super_secret".equals(password)) {
            throw new UnauthorizedException("The password is wrong!");
        }
        return ResponseEntity.ok().body(new Stats(availableSeats.getCurrentIncome(),
                                                        availableSeats.getNumAvailableSeats(),
                                                        availableSeats.getNumPurchasedTickets()));
    }

    //    @todo; investigate singleton bean usage generally in spring
    //    @Bean
    //    Seats seats(){
    //        return Seats.getAllEmptySeats();
    //    }
}
