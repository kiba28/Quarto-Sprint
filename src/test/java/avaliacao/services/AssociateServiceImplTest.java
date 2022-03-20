package avaliacao.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import avaliacao.entities.enums.PoliticalOffice;
import avaliacao.exceptions.DefaultException;
import avaliacao.repositories.AssociateRepository;
import avaliacao.repositories.PoliticalPartyRepository;
import avaliacao.services.dto.AssociateDto;
import avaliacao.services.dto.AssociateFormDto;
import avaliacao.services.dto.AssociatePartyFormDto;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AssociateServiceImplTest {

	@Autowired
	private AssociateServiceImpl service;

	@MockBean
	private AssociateRepository repository;

	@MockBean
	PoliticalPartyRepository partyRepository;

	@Test
	public void deveriaInserirUmAssociado() {
		Associate associate = AssociateBuilder.getAssociate();

		when(this.partyRepository.findById(anyLong()))
				.thenReturn(Optional.of(PoliticalPartyBuilder.getPoliticalParty()));
		when(this.repository.save(any(Associate.class))).thenReturn(associate);

		AssociateDto associateDto = this.service.insert(AssociateBuilder.getAssociateFormDto());

		assertThat(associateDto.getId()).isNotNull();
		assertThat(associateDto.getName()).isEqualTo(associate.getName());
		assertThat(associateDto.getPoliticalOffice()).isEqualTo(associate.getPoliticalOffice());
		assertThat(associateDto.getBirthDate()).isEqualTo(associate.getBirthDate());
		assertThat(associateDto.getGender()).isEqualTo(associate.getGender());
		assertThat(associateDto.getPoliticalOffice()).isEqualTo(associate.getPoliticalOffice());
	}

	@Test
	public void deveriaAtualizarUmAssociado() {
		Associate associate = AssociateBuilder.getAssociate();
		AssociateFormDto associateFormDto = AssociateBuilder.getAssociateFormDto();
		associateFormDto.setName("Kiba");

		when(this.partyRepository.findById(anyLong()))
				.thenReturn(Optional.of(PoliticalPartyBuilder.getPoliticalParty()));
		when(this.repository.findById(anyLong())).thenReturn(Optional.of(associate));
		when(this.repository.save(any(Associate.class))).thenReturn(associate);

		AssociateDto associateDto = this.service.update(associate.getId(), associateFormDto);

		assertThat(associateDto.getId()).isNotNull();
		assertThat(associateDto.getName()).isEqualTo(associate.getName());
		assertThat(associateDto.getPoliticalOffice()).isEqualTo(associate.getPoliticalOffice());
		assertThat(associateDto.getBirthDate()).isEqualTo(associate.getBirthDate());
		assertThat(associateDto.getGender()).isEqualTo(associate.getGender());
		assertThat(associateDto.getPoliticalParty()).isEqualTo(associate.getPoliticalParty());
	}

	@Test
	public void naoDeveriaAtualizarPoisNaoFoiEncontradoAssociadoComEsteId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(DefaultException.class)
				.isThrownBy(() -> this.service.update(1L, AssociateBuilder.getAssociateFormDto()));
	}

	@Test
	public void deveriaAtualiazarOPartidoDeUmAssociado() {
		Associate associate = AssociateBuilder.getAssociate();
		AssociatePartyFormDto associatePartyForm = AssociateBuilder.getAssociatePartyFormDto();
		PoliticalParty partyModified = PoliticalPartyBuilder.getPoliticalParty();
		partyModified.setId(2L);
		partyModified.setName("PSOL");
		partyModified.setIdeology(Ideology.Esquerda);

		when(this.partyRepository.findById(2L)).thenReturn(Optional.of(partyModified));
		when(this.repository.findById(1L)).thenReturn(Optional.of(associate));
		when(this.repository.save(any(Associate.class))).thenReturn(associate);

		AssociateDto associateDto = this.service.updatePoliticalParty(associatePartyForm);

		assertThat(associateDto.getPoliticalParty()).isEqualTo(partyModified);
	}

	@Test
	public void devariaDeletarUmAssociadoPeloId() {
		Associate associate = AssociateBuilder.getAssociate();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(associate));

		this.service.deleteById(associate.getId());

		verify(this.repository, times(1)).delete(associate);
	}

	@Test
	public void naoDeveriaDeletarPoisNaoFoiEncontratoAssociadoComEsteId() {
		Associate associate = AssociateBuilder.getAssociate();

		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(DefaultException.class).isThrownBy(() -> this.service.deleteById(associate.getId()));
	}

	@Test
	public void deveriaDesassociarOAssociadoDeUmPartido() {
		Associate associate = AssociateBuilder.getAssociate();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(associate));
		when(this.partyRepository.findById(anyLong()))
				.thenReturn(Optional.of(PoliticalPartyBuilder.getPoliticalParty()));

		this.service.deleteAssociateFromParty(1L, 1L);

		assertThat(associate.getPoliticalParty()).isEqualTo(null);
	}

	@Test
	public void naoDeveriaDesasociarOAssociadoDeUmPartidoPoisEleNaoEstaAssociadoAoPartidoInformado() {
		Associate associate = AssociateBuilder.getAssociate();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(associate));

		assertThatExceptionOfType(DefaultException.class)
				.isThrownBy(() -> this.service.deleteAssociateFromParty(1L, 2L));
	}

	@Test
	public void deveriaBuscarUmaListaComTodosOsAssociados() {
		Associate associate = AssociateBuilder.getAssociate();
		List<Associate> list = Arrays.asList(associate);

		when(this.repository.findAll()).thenReturn(list);

		List<AssociateDto> resultList = this.service.findAll(null);

		assertThat(resultList.size()).isGreaterThan(0);
		assertThat(resultList.get(0).getName()).isEqualTo(associate.getName());
	}

	@Test
	public void deveriaBuscarUmaListaComOsAssociadosDeMesmoCargoPolitico() {
		Associate associate = AssociateBuilder.getAssociate();
		List<Associate> list = Arrays.asList(associate);

		when(this.repository.findBypoliticalOffice(PoliticalOffice.Presidente)).thenReturn(list);

		List<AssociateDto> resultList = this.service.findAll("Presidente");

		assertThat(resultList.size()).isGreaterThan(0);
		assertThat(resultList.get(0).getName()).isEqualTo(associate.getName());
	}

	@Test
	public void deveriaBuscarUmAssociadoPeloId() {
		Associate associate = AssociateBuilder.getAssociate();

		when(this.repository.findById(anyLong())).thenReturn(Optional.of(associate));

		AssociateDto associateDto = this.service.findById(1L);

		assertThat(associateDto.getId()).isNotNull();
		assertThat(associateDto.getName()).isEqualTo(associate.getName());
		assertThat(associateDto.getPoliticalOffice()).isEqualTo(associate.getPoliticalOffice());
		assertThat(associateDto.getBirthDate()).isEqualTo(associate.getBirthDate());
		assertThat(associateDto.getGender()).isEqualTo(associate.getGender());
		assertThat(associateDto.getPoliticalOffice()).isEqualTo(associate.getPoliticalOffice());
	}

	@Test
	public void naoDeveriaBuscarUmAssociadoPeloIdPoisNaoExisteAssociadoComEsteId() {
		when(this.repository.findById(anyLong())).thenReturn(Optional.empty());

		assertThatExceptionOfType(DefaultException.class).isThrownBy(() -> this.service.findById(1L));
	}
}
