package com.example.auctionapp.domain;


import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    private final static String[] DEFAULT_IGNORE_PROPS =
            new String[]{"id", "createdBy", "createdDatetime", "lastModifiedBy", "lastModifiedDatetime", "version"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    protected String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP")
    protected ZonedDateTime createdDatetime;

    @LastModifiedBy
    @Column(nullable = false)
    protected String lastModifiedBy;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    protected ZonedDateTime lastModifiedDatetime;

    @Version
    @Column(nullable = false)
    protected Long version;


    public <T> T copyFields(T source, String... ignoreProperties){
        BeanUtils.copyProperties(source, this, StringUtils.concatenateStringArrays(DEFAULT_IGNORE_PROPS, ignoreProperties));
        return (T)this;
    }

}