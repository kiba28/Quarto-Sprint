package avaliacao.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import avaliacao.builder.AssociateBuilder;
import avaliacao.builder.PoliticalPartyBuilder;
import avaliacao.entities.Associate;
import avaliacao.entities.PoliticalParty;
import avaliacao.entities.enums.Ideology;
import avaliacao.exceptions.DefaultException;
import avaliacao.repositories.PoliticalPartyRepository;
import avaliacao.services.dto.PoliticalPartyDto;
import avaliacao.services.dto.PoliticalPartyFormDto;
import avaliacao.services.dto.PoliticalPartyWithAssociatesDto;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PoliticalPartyServiceImplTest {

	@Autowired
	private PoliticalPartyServiceImpl service;

	@MockBean
	private PoliticalPartyRepository repository;

	@Test
	public void deveriaBuscarUmaListaComTodosOsPartidos() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();
		List<PoliticalParty> list = Arrays.asList(party);

		when(this.repository.findAll()).thenReturn(list);

		List<PoliticalPartyDto> resultList = this.service.findAll();

		assertThat(resultList.size()).isGreaterThan(0);
		assertThat(resultList.get(0).getName()).isEqualTo(party.getName());
	}

	@Test
	public void deveriaBuscarUmaListaComOsPartidosDeMesmaIdeologia() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();
		List<PoliticalParty> list = Arrays.asList(party);

		when(this.repository.findByIdeology(Ideology.Centro)).thenReturn(list);

		List<PoliticalPartyDto> resultList = this.service.findByIdeology("Centro");

		assertThat(resultList.size()).isGreaterThan(0);
		assertThat(resultList.get(0).getName()).isEqualTo(party.getName());
	}

	@Test
	public void deveriaBuscarUmPartidoPeloId() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(party));

		PoliticalPartyDto partyDto = this.service.findById(1L);

		assertThat(partyDto.getId()).isNotNull();
		assertThat(partyDto.getName()).isEqualTo(party.getName());
		assertThat(partyDto.getAcronym()).isEqualTo(party.getAcronym());
		assertThat(partyDto.getIdeology()).isEqualTo(party.getIdeology());
		assertThat(partyDto.getFoundationDate()).isEqualTo(party.getFoundationDate());
	}

	@Test
	public void deveriaBuscarUmPartidoESeusAssociadosPeloId() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();
		party.setAssociates(Arrays.asList(AssociateBuilder.getAssociate()));

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(party));

		PoliticalPartyWithAssociatesDto partyDto = this.service.findByIdWithAssociates(1L);

		assertThat(partyDto.getId()).isNotNull();
		assertThat(partyDto.getName()).isEqualTo(party.getName());
		assertThat(partyDto.getAcronym()).isEqualTo(party.getAcronym());
		assertThat(partyDto.getIdeology()).isEqualTo(party.getIdeology());
		assertThat(partyDto.getFoundationDate()).isEqualTo(party.getFoundationDate());
		assertThat(partyDto.getAssociates()).isEqualTo(party.getAssociates());
	}

	@Test
	public void deveriaInserirUmPartido() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();

		when(this.repository.save(any(PoliticalParty.class))).thenReturn(party);

		PoliticalPartyDto partyDto = this.service.insert(PoliticalPartyBuilder.getPoliticalPartyFormDto());

		assertThat(partyDto.getId()).isNotNull();
		assertThat(partyDto.getName()).isEqualTo(party.getName());
		assertThat(partyDto.getAcronym()).isEqualTo(party.getAcronym());
		assertThat(partyDto.getIdeology()).isEqualTo(party.getIdeology());
		assertThat(partyDto.getFoundationDate()).isEqualTo(party.getFoundationDate());
	}

	@Test
	public void deveriaAtualizarUmPartido() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();
		PoliticalPartyFormDto partyFormDto = PoliticalPartyBuilder.getPoliticalPartyFormDto();
		partyFormDto.setName("PODEMOS");
		partyFormDto.setIdeology(Ideology.Direita);

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(party));
		when(this.repository.save(any(PoliticalParty.class))).thenReturn(party);
		
		PoliticalPartyDto partyDto = this.service.update(party.getId(), partyFormDto);
		
		assertThat(partyDto.getId()).isNotNull();
		assertThat(partyDto.getName()).isEqualTo(party.getName());
		assertThat(partyDto.getAcronym()).isEqualTo(party.getAcronym());
		assertThat(partyDto.getIdeology()).isEqualTo(party.getIdeology());
		assertThat(partyDto.getFoundationDate()).isEqualTo(party.getFoundationDate());
	}
	
	@Test
	public void naoDeveriaAtualizarPoisNaoFoiEncontradoPartidoComEsteId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());
		
		assertThatExceptionOfType(DefaultException.class)
		.isThrownBy(() -> this.service.update(1L, PoliticalPartyBuilder.getPoliticalPartyFormDto()));
	}
	
	@Test
	public void deveriaDeletarUmPartidoPeloId() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();
		
		when(this.repository.findById(anyLong())).thenReturn(Optional.of(party));
		
		this.service.deleteById(1L);
		
		verify(this.repository, times(1)).delete(party);
	}
	
	@Test
	public void naoDeveriaDeletarPoisAindaExistemAssociadosNoPartido() {
		PoliticalParty party = PoliticalPartyBuilder.getPoliticalParty();
		Associate associate = AssociateBuilder.getAssociate();
		associate.setPoliticalParty(party);
		party.setAssociates(Arrays.asList(associate));
		
		when(this.repository.findById(anyLong())).thenReturn(Optional.of(party));
		doThrow(new DefaultException()).when(this.repository).delete(any(PoliticalParty.class));
		
		assertThatExceptionOfType(DefaultException.class)
		.isThrownBy(() -> this.service.deleteById(1L));
	}
	
	@Test
	public void naoDeveriaDeletarPoisNaoFoiEncontradoPartidoComEsteId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());
		
		assertThatExceptionOfType(DefaultException.class)
		.isThrownBy(() -> this.service.deleteById(1L));
	}

}










