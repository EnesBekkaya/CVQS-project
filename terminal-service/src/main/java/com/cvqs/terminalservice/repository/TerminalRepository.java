package com.cvqs.terminalservice.repository;

import com.cvqs.terminalservice.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerminalRepository extends JpaRepository<Terminal,String> {
    List<Terminal>findTerminalByActive(Boolean active);
}
