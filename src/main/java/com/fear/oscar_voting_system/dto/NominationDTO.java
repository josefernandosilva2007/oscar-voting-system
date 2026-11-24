package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.UUID;
public record NominationDTO(@NotNull UUID categoryId, @NotNull UUID movieId,
                            @NotNull UUID personId, int year, @NotNull @Min(0) @Max(1) Integer status) {}
