package com.example.educonnect.repository;

import com.example.educonnect.entity.common.Ownable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface OwnableRepository<T extends Ownable, ID extends Serializable> extends JpaRepository<T, ID>{

}
