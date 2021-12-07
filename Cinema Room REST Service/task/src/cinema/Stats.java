package cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stats {
    private int currentIncome;
    private int numberOfAvailableSeats;
    private int numberOfPurchasedTickets;
}
