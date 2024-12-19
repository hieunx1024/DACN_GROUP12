package com.laptrinhoop.dao;

import com.laptrinhoop.entity.Partner;
import com.laptrinhoop.entity.Product;

import java.util.Optional;

public interface IPartnerDAO extends IGeneralDAO<Partner, Integer>{
    Optional<Partner> findByCode(String code);
}
