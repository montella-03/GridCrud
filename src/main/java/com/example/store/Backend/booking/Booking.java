package com.example.store.Backend.booking;

import com.example.store.Backend.enumerations.BedType;
import com.example.store.Backend.enumerations.BookingChannel;
import com.example.store.Backend.enumerations.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String createdBy;
    private String updatedBy;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String email;
    private String phoneNumber;
    private String nationality;
    @NotBlank(message = "Passport Number is mandatory")
    private String passportNumber;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    @Enumerated(value= EnumType.STRING)
    private RoomType roomType;
    private String roomNumber;
    @Enumerated(value= EnumType.STRING)
    private BedType bedType;
    private int numberOfBeds;
    private String lockNumber;
    @Enumerated(value= EnumType.STRING)
    private BookingChannel bookingChannel;
    private double invoiceAmount;
    private int amountPaid;
    private double balance;
    private int numberOfDays;
    private boolean checkedIn;
}
