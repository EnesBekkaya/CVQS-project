package com.cvqs.terminalservice.service.concretes;

import com.cvqs.terminalservice.dto.TerminalDto;
import com.cvqs.terminalservice.model.Section;
import com.cvqs.terminalservice.model.Terminal;
import com.cvqs.terminalservice.repository.TerminalRepository;
import com.cvqs.terminalservice.service.abstracts.SectionService;
import com.cvqs.terminalservice.service.abstracts.TerminalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 *  TerminalManager sınıfı, TerminalService arayüzünden türetilmiştir ve
 *  terminal  işlemlerini yönetir. Bu sınıf, veritabanı işlemleri için TerminalRepository
 *  section işlemleri için SectionService nesnelerini kullanır.
 *
 *  @author Enes Bekkaya
 *  @since  18.02.2023
 */
@Service
@RequiredArgsConstructor
public class TerminalManager implements TerminalService {
    private final ModelMapper modelMapper;
    private final TerminalRepository terminalRepository;
    private final SectionService sectionService;

    /**
     * Aktif durumdaki tüm terminalleri getirir.
     * @param active terminal aktiflik durumu
     * @return active parametresine bağlı olarak filtrelenmiş TerminalDto listesi
     */
    @Override
    public List<TerminalDto> getActiveTerminals(Boolean active) {

       List <Terminal> terminals=terminalRepository.findTerminalByActive(active);
       List<TerminalDto> terminalDtos=terminals.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
       return terminalDtos;
    }
    /**
     * Belirtilen sayfadaki, belirtilen boyutta sayıda aktif terminallerin sayfasını döndürür.
     *
     * @param active aktiflik durumu
     * @param pageSize sayfa boyutu
     * @param page sayfa numarası
     * @return istenen sayfa boyutu ve numarasındaki aktif terminallerin bir sayfası
     */
    @Override
    public Page<TerminalDto> pagination(Boolean active,int pageSize, int page) {
        Pageable pageable= PageRequest.of(page,pageSize);

        Page<Terminal> terminals = terminalRepository.findTerminalByActive(active,pageable);
        Page<TerminalDto> terminalDtos = terminals.map(terminal -> modelMapper.map(terminal, TerminalDto.class));
        return terminalDtos;
    }

    /**
     * Verilen bölüme ait terminalleri döndürür.
     * @param sectionName Terimallerin bulunacağı bölümün ismi
     * @return Bölüme ait terminallerin listesi
     */
    @Override
    public List<TerminalDto> findTerminalBySection(String sectionName) {
        Section section=sectionService.findSectionByName(sectionName);
        List <Terminal> terminals=terminalRepository.findTerminalBySections(section);
        List<TerminalDto> terminalDtos=terminals.stream().map(terminal1 -> modelMapper.map(terminal1,TerminalDto.class)).collect(Collectors.toList());
        return terminalDtos;
    }

    /**
     * Yeni bir terminal oluşturur veya mevcut bir terminali günceller.
     * @param terminalDto Terminal verilerini içeren DTO nesnesi
     * @return Kaydedilen veya güncellenen terminal verilerini içeren DTO nesnesi
     */
    @Override
    public TerminalDto SaveTerminal(TerminalDto terminalDto) {
        List<Section> sections= sectionService.saveSection(terminalDto.getSections());
        Terminal terminal=terminalRepository.findTerminalByName(terminalDto.getName());
        if(terminal!=null){
            terminal.setSections(sections);
            return modelMapper.map(terminalRepository.save(terminal),TerminalDto.class);
        }
        else{
            Terminal newTerminal=new Terminal();
            newTerminal.setName(terminalDto.getName());
            newTerminal.setActive(true);
            newTerminal.setDeleted(false);
            newTerminal.setCreateDate(new Date());
            newTerminal.setSections(sections);
            return modelMapper.map(terminalRepository.save(newTerminal),TerminalDto.class);

        }

    }

    /**
     * Bu metot, tarihe göre sıralanmış aktif terminallerin bir listesini döndürür.
     * @return tarihe göre sıralanmış TerminalDto listesi
     */
    public List<TerminalDto> getTerminalSortedByDate() {
        List<TerminalDto>terminals=getActiveTerminals(true);
        Comparator<TerminalDto> dateComparator = new Comparator<TerminalDto>() {
            @Override
            public int compare(TerminalDto t1, TerminalDto t2) {
                return t2.getCreateDate().compareTo(t1.getCreateDate());            }

        };

        Collections.sort(terminals, dateComparator);

        return terminals;
    }
}
