package com.example.samplebootapp.infrastructure.product.datasource.typehandler;

import com.example.samplebootapp.domain.product.model.CategoryId;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

/**
 * CategoryId用MyBatis TypeHandler.
 */
@MappedTypes(CategoryId.class)
public class CategoryIdTypeHandler extends BaseTypeHandler<CategoryId> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CategoryId parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public CategoryId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : new CategoryId(value);
    }

    @Override
    public CategoryId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : new CategoryId(value);
    }

    @Override
    public CategoryId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : new CategoryId(value);
    }
}
