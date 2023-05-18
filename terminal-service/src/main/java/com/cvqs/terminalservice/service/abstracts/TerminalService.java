package com.cvqs.terminalservice.service.abstracts;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.model.Terminal;
import org.springframework.data.domain.Page;

import java.util.List;
/**
 * TerminalService interface performs operations related to Terminal objects.
 *
 * @author Enes Bekkaya
 * @since  18.02.2023
 */
public interface TerminalService {
    /**
     * Returns a list of all active terminals based on the specified status.
     *
     * @param active a boolean value indicating the status of the terminals
     * @return a list of TerminalDto objects representing the terminals
     */
     List<TerminalDto> getActiveTerminals(Boolean active);
    /**
     * Returns a paginated list of terminals according to the specified page size and page number and their active status.
     * @param active Boolean value indicating the activity status of the terminals
     * @param pageSize Page size
     * @param page Page number
     * @return A paginated list of terminals as TerminalDto objects
     */
     Page<TerminalDto> pagination(Boolean active,int pageSize,int page);
    /**
     * Returns the list of terminals belonging to the specified section.
     *
     * @param sectionName the name of the section
     * @return the list of terminals belonging to the specified section as TerminalDto objects
     */

     List<TerminalDto> findTerminalBySection(String sectionName);
    /**
     * Saves or updates terminals according to the given TerminalDto object.
     *
     * @param terminalDto TerminalDto object to be saved or updated
     * @return Saved or updated TerminalDto object
     */
     TerminalDto SaveTerminal(TerminalDto terminalDto);
    /**
     * Returns the terminals sorted by their creation date in ascending order.
     *
     * @return List of terminals sorted by their creation date as TerminalDto objects
     */
      List<TerminalDto> getTerminalSortedByDate();

}
