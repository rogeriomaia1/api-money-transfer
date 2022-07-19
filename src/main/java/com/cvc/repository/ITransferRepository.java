package com.cvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cvc.model.Transfer;

@Repository
public interface ITransferRepository extends JpaRepository<Transfer, Long> {

}
