package com.wxy.config.response;

import avro.shaded.com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author blade
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryResponseResult<T> extends ResponseResult implements Serializable {

    private QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode) {
        super(resultCode);
    }

    public QueryResponseResult(ResultCode resultCode, QueryResult<T> queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

    public QueryResponseResult(ResultCode resultCode, T t) {
        super(resultCode);
        QueryResult<T> queryResult = new QueryResult<>();
        queryResult.setList(Lists.newArrayList(t));
        this.queryResult = queryResult;
    }

    /**
     * 获取当前列表中的第一个值（用于list中只有一个值的情况）
     *
     * @return T
     * @author ClearLi
     * @date 2020/10/24 11:03
     */
    @JsonIgnore
    public T getIndex0() {
        return queryResult.getList().get(0);
    }
    @JsonIgnore
    public List<T> getList(){
        return queryResult.getList();
    }
    @JsonIgnore
    public QueryResponseResult<T> getEmptyList(){
        return new QueryResponseResult<>(CommonCode.SUCCESS,new QueryResult<>(Lists.newArrayList()));
    }
}
