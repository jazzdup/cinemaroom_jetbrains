package cinema;

import lombok.*;

import java.util.Comparator;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Seat implements Comparable{
    int row;
    int column;
    int price;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        if (row <= 4) {
            this.price = 10;
        }else{
            this.price = 8;
        }
    }

    @Override
    public int compareTo(Object o) {
        Seat other = (Seat) o;
        return Comparator.comparing(Seat::getRow)
                .thenComparing(Seat::getColumn)
                .compare(this, other);
    }
}
