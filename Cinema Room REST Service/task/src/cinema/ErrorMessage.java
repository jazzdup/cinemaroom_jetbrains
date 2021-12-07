package cinema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {
    private String error;

    public ErrorMessage(Exception e) {
        this.error = e.getMessage();
    }
}
