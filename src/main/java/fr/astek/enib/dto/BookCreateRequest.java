package fr.astek.enib.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String description;

    @NotEmpty
    private List<@NotBlank String> genre;

    @NotBlank
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}")
    private String releaseDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @DecimalMax(value = "5.0", inclusive = true)
    private Float rating;

    @NotNull
    @Min(0)
    private Integer sales;
}


