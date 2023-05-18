package com.cvqs.terminalservice.repository;

import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.model.Terminal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * The TerminalRepository interface is used to perform database operations on Terminal objects.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface TerminalRepository extends JpaRepository<Terminal,String> {
    /**
     * Returns all terminals with the specified status.
     * @param active The status of the terminals.
     * @return The list of all terminals with the specified status.
     */
    List<Terminal>findTerminalByActive(Boolean active);
    /**
     * Returns a paginated list of terminals with the specified active status.
     *
     * @param active the status of the terminals to be returned (true for active, false for inactive)
     * @param pageable the Pageable object
     * @return a paginated list of terminals
     */
    Page<Terminal> findTerminalByActive(Boolean active, Pageable pageable);
    /**
     * Returns all terminals in the specified section.
     * @param section the specified section
     * @return the list of terminals in the specified section
     */

    List<Terminal> findTerminalBySections(Section section);
    /**
     * gets the terminal with the specified name.
     *
     * @param name name of the terminal
     * @return the terminal with the specified name
     */
    Terminal findTerminalByName(String name);

}
