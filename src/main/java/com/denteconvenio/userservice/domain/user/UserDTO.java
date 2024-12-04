package com.denteconvenio.userservice.domain.user;

import java.util.UUID;

public record UserDTO(

    UUID id,
    String username,
    String email,
    String tipo
) {


}
