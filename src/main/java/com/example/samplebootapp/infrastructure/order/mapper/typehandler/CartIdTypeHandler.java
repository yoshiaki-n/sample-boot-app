package com.example.samplebootapp.infrastructure.order.mapper.typehandler;

import com.example.samplebootapp.domain.order.model.CartId;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(CartId.class)
public class CartIdTypeHandler extends BaseTypeHandler<CartId> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, CartId parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(i, parameter.getValue());
  }

  @Override
  public CartId getNullableResult(ResultSet rs, String columnName) throws SQLException {
    String value = rs.getString(columnName);
    return value == null ? null : new CartId(value);
  }

  @Override
  public CartId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    String value = rs.getString(columnIndex);
    return value == null ? null : new CartId(value);
  }

  @Override
  public CartId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    String value = cs.getString(columnIndex);
    return value == null ? null : new CartId(value);
  }
}
