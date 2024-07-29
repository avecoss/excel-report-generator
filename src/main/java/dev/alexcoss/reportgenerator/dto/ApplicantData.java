package dev.alexcoss.reportgenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantData {
    private int applicantId;
    private String fullName;
    private LocalDate dateOfBirth;
    private String countryName;
    private int applicationId;
    private String statusName;
    private LocalDate applicationDate;
    private String documentTypeName;
}
