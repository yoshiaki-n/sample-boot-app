package com.example.samplebootapp.infrastructure.product.datasource.typehandler;

import com.example.samplebootapp.domain.product.model.Price;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/** Price用MyBatis TypeHandler. */
@MappedTypes(Price.class)
public class PriceTypeHandler extends BaseTypeHandler<Price> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, Price parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setBigDecimal(i, parameter.getAmount());
  }

  @Override
  public Price getNullableResult(ResultSet rs, String columnName) throws SQLException {
    BigDecimal amount = rs.getBigDecimal(columnName);
    return amount == null ? null : new Price(amount);
  }

  @Override
  public Price getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    BigDecimal amount = rs.getBigDecimal(columnIndex);
    return amount == null ? null : new Price(amount);
  }

  @Override
  public Price getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    BigDecimal amount = cs.getBigDecimal(columnIndex);
    return amount == null ? null : new Price(amount);
  }
}
