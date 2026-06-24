package ch.axa.punchclock.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Long id;

    @NotNull(message = "Von darf nicht leer sein.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "entry_tags", joinColumns = @JoinColumn(name = "entry_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    /**
     * Duration is in minutes.
     */
    @Column
    private int duration;

    @NotBlank(message = "Beschreibung darf nicht leer sein.")
    @Column(length = 5000)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @AssertTrue(message = "Von und die Dauer dürfen Mitternacht nicht überschreiten.")
    @JsonIgnore
    public boolean isDurationBeforeMidnight() {
        if (this.checkIn != null) {
            var checkOut = this.checkIn.plusMinutes(this.duration);
            return this.checkIn.toLocalDate().isEqual(checkOut.toLocalDate());
        }
        return false;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}