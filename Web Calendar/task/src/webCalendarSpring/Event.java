package webCalendarSpring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

@Validated
@Entity
public class Event {

    @Transient
    static final String datePattern = "[0-9]{4}-([0][1-9]|[1][0-2])-([0-2][0-9]|[3][0-1])";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "INTEGER NOT NULL AUTO_INCREMENT")
    public Long id;

    @NotBlank
    @Column(columnDefinition = "VARCHAR")
    public String event;

    @NotBlank
    @Pattern(regexp = datePattern)
    @Column(nullable = false, columnDefinition = "DATE")
    public String date;

    public String getEvent() {
        return event;
    }

    public String getDate() {
        return date;
    }
}
