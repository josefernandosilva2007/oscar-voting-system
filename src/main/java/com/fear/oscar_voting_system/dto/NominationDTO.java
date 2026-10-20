package com.fear.oscar_voting_system.dto;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
public record NominationDTO(@NotNull UUID categoryId, @NotNull UUID movieId,
                            @NotNull UUID personId, int year, @NotNull Integer status) {}
