package com.lesleyzh.cat_demo.repository;

import com.lesleyzh.cat_demo.model.CatModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatRepository extends JpaRepository<CatModel, Integer> {
    //这个interface会自动帮你生成backend CRUD的方法，不需要自己写
    //JpaRepository<model, primary key type>

    //如果有特殊的查询需求，就在这里自己写方法，比如通过email+id查询一个人
    List<CatModel> findByEmailAndId(String email, int id);
    //pagination
    List<CatModel> findAllById(Pageable pageable);
}
