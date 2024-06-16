package com.example.invext.infra.contract;

public interface IDataMapper<TDomain, TEntity> {
  public TDomain toDomain(TEntity entity);
  public TEntity toEntity(TDomain domain);
}
