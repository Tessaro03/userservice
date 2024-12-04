package com.denteconvenio.userservice.domain.organizacao.consultorio;

import java.util.List;
import java.util.UUID;

public record DentistasDTO(

    List<UUID> dentistas
) {
}
