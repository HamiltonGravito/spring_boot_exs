package com.hamilton.github.albums1;

import java.time.LocalDate;

public record Player(String id, Integer jerseyNumber, String name, String position, LocalDate dateOfBirth) {}