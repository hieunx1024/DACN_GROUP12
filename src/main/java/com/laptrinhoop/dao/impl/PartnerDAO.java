package com.laptrinhoop.dao.impl;


import com.laptrinhoop.dao.IPartnerDAO;

import com.laptrinhoop.entity.Partner;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.Optional;

@Repository
public class PartnerDAO extends GeneraDAO<Partner, Integer> implements IPartnerDAO {
    @Override
    public Optional<Partner> findByCode(String code) {
        String hql = "FROM Partner as p  WHERE p.code =?0";
        List<Partner> result = this.getResultList(hql,code);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
