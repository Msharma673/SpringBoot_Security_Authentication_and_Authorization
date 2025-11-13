package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Manager;

//Manager repository


public interface ManagerRepository extends JpaRepository<Manager, Long> {}

