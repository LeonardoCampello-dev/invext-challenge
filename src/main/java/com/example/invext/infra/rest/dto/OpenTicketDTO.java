package com.example.invext.infra.rest.dto;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OpenTicketDTO(
    @NotNull(message = "Obrigatório informar setor de atendimento desejado")
    Department department,
    @NotEmpty(message = "Título obrigatório")
    String title,
    @NotEmpty(message = "Descrição obrigatória")
    String description,
    @NotNull(message = "Identificador do cliente não informado")
    Integer customerId
) {
}
