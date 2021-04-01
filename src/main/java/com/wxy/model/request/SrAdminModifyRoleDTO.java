package com.wxy.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * sr_admin
 *
 * @author
 */
@Data
public class SrAdminModifyRoleDTO implements Serializable {

    private Long id;

    /**
     * 要更改的角色id
     */
    private Integer role;

    private static final long serialVersionUID = 1L;


}