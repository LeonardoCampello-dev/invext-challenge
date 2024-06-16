package com.example.invext.infra.rest.dto;

import com.example.invext.domain.customerservicecenter.enumeration.Department;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OpenTicketDTO(
    @ApiModelProperty(value = "Departamento responsável pelo atendimento", required = true, example = "LOANS")
    @NotNull(message = "Obrigatório informar setor de atendimento desejado")
    Department department,

    @ApiModelProperty(value = "Título do ticket", required = true, example = "Empréstimo negado")
    @NotEmpty(message = "Título obrigatório") String title,

    @ApiModelProperty(value = "Descrição completa do ticket", required = true, example = "Meu empréstimo foi negado!")
    @NotEmpty(message = "Descrição obrigatória") String description,

    @ApiModelProperty(value = "Id do cliente", required = true, example = "1")
    @NotNull(message = "Identificador do cliente não informado") String customerId
) {
}
