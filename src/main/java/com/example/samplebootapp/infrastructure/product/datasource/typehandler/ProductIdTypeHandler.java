package com.example.samplebootapp.infrastructure.product.datasource.typehandler;

import com.example.samplebootapp.domain.product.model.ProductId;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * ProductId用MyBatis TypeHandler.
 */
@MappedTypes(ProductId.class)
public class ProductIdTypeHandler extends BaseTypeHandler<ProductId> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ProductId parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public ProductId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : new ProductId(value);
    }

    @Override
    public ProductId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : new ProductId(value);
    }

    @Override
    public ProductId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : new ProductId(value);
    }
}
