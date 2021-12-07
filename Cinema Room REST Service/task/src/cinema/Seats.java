package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@AllArgsConstructor
@Getter
@Setter
public class Seats {
    private final static int ROW_MIN = 1;
    private final static int COL_MIN = 1;
    private final static int ROW_MAX = 9;
    private final static int COL_MAX = 9;
    int totalRows;
    int totalColumns;
    private ConcurrentSkipListSet<Seat> availableSeats;
    @JsonIgnore
    private ConcurrentHashMap<String, Seat> purchasedSeats;

    static synchronized Seats getAllEmptySeats(){
        ConcurrentSkipListSet<Seat> availableSeats = new ConcurrentSkipListSet();
        for (int i = ROW_MIN; i <= ROW_MAX; i++) {
            for (int j = COL_MIN; j <= COL_MAX; j++) {
                Seat seat = new Seat(i, j);
                availableSeats.add(seat);
            }
        }
        return new Seats(ROW_MAX, COL_MAX, availableSeats, new ConcurrentHashMap<>());
    }

    int getCurrentIncome(){
        return purchasedSeats.values().stream()
                .map(s -> s.getPrice())
                .reduce(0, Integer::sum);
    }

    int getNumAvailableSeats(){
        return availableSeats.size();
    }

    int getNumPurchasedTickets(){
        return purchasedSeats.size();
    }

    public synchronized Purchase purchase(Seat seat) throws SeatException {
        if (seat.getRow() < ROW_MIN || seat.getRow() > ROW_MAX
            || seat.getColumn() < COL_MIN || seat.getColumn() > COL_MAX) {
            throw new SeatException("The number of a row or a column is out of bounds!");
        }
        Seat seatWithPrice = new Seat(seat.getRow(), seat.getColumn());
        String token = UUID.randomUUID().toString();
        boolean removed = availableSeats.remove(seatWithPrice);
        if (! removed) {
            throw new SeatException("The ticket has been already purchased!");
        }
        Purchase purchase = new Purchase(token, seatWithPrice);
        purchasedSeats.put(token, seatWithPrice);
        return purchase;
    }

    public synchronized Return returnByToken(String token) throws SeatException {
        Seat seat = purchasedSeats.remove(token);
        if (seat == null) {
            throw new SeatException("Wrong token!");
        }
        availableSeats.add(seat);
        return new Return(seat);
    }
}
